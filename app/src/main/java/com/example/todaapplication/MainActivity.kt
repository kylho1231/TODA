package com.example.todaapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todaapplication.adapter.RouteFareAdapter
import com.example.todaapplication.databinding.ActivityMainBinding
import com.example.todaapplication.databinding.DialogReportOverchargingBinding
import com.example.todaapplication.model.FareCalculationResult
import com.example.todaapplication.model.FareCalculator
import com.example.todaapplication.model.PassengerType
import com.example.todaapplication.model.SanJoseFareRepository
import com.example.todaapplication.model.TripType
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository = SanJoseFareRepository()
    private val calculator = FareCalculator(repository)

    private var currentPassengerCount = 1
    private var currentPassengerType = PassengerType.REGULAR
    private var isSpecialTrip = false
    private var latestCalculation: FareCalculationResult? = null

    private lateinit var routeAdapter: RouteFareAdapter
    private lateinit var locations: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        setupDropdowns()
        setupPassengerControls()
        setupMatrixRecyclerView()
        setupHotlineButtons()
        setupReportButton()

        // Initial live calculation
        recalculateFare()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_calculator -> {
                    binding.layoutCalculator.visibility = View.VISIBLE
                    binding.layoutMatrix.visibility = View.GONE
                    binding.layoutHelp.visibility = View.GONE
                    true
                }
                R.id.nav_matrix -> {
                    binding.layoutCalculator.visibility = View.GONE
                    binding.layoutMatrix.visibility = View.VISIBLE
                    binding.layoutHelp.visibility = View.GONE
                    true
                }
                R.id.nav_help -> {
                    binding.layoutCalculator.visibility = View.GONE
                    binding.layoutMatrix.visibility = View.GONE
                    binding.layoutHelp.visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

    private fun setupDropdowns() {
        locations = repository.getLocations()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, locations)

        binding.actvOrigin.setAdapter(adapter)
        binding.actvDestination.setAdapter(adapter)

        val defaultOrigin = locations.firstOrNull { it.contains("Poblacion", ignoreCase = true) } ?: locations.first()
        val defaultDest = locations.firstOrNull { it.contains("Igbonglo", ignoreCase = true) } ?: locations.last()

        binding.actvOrigin.setText(defaultOrigin, false)
        binding.actvDestination.setText(defaultDest, false)

        binding.actvOrigin.setOnItemClickListener { _, _, _, _ ->
            recalculateFare()
        }

        binding.actvDestination.setOnItemClickListener { _, _, _, _ ->
            recalculateFare()
        }

        binding.btnSwapLocations.setOnClickListener {
            val originText = binding.actvOrigin.text.toString()
            val destText = binding.actvDestination.text.toString()

            binding.actvOrigin.setText(destText, false)
            binding.actvDestination.setText(originText, false)

            binding.btnSwapLocations.animate().rotationBy(180f).setDuration(250).start()
            recalculateFare()
        }
    }

    private fun setupPassengerControls() {
        binding.chipGroupPassenger.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull() ?: R.id.chipRegular
            currentPassengerType = when (checkedId) {
                R.id.chipStudent -> PassengerType.STUDENT
                R.id.chipSenior -> PassengerType.SENIOR_CITIZEN
                R.id.chipPwd -> PassengerType.PERSON_WITH_DISABILITY
                else -> PassengerType.REGULAR
            }
            recalculateFare()
        }

        binding.btnPassengerMinus.setOnClickListener {
            if (currentPassengerCount > 1) {
                currentPassengerCount--
                binding.tvPassengerCount.text = currentPassengerCount.toString()
                recalculateFare()
            }
        }

        binding.btnPassengerPlus.setOnClickListener {
            if (currentPassengerCount < 4) {
                currentPassengerCount++
                binding.tvPassengerCount.text = currentPassengerCount.toString()
                recalculateFare()
            } else {
                Toast.makeText(
                    this,
                    "Maximum regulated tricycle capacity is 4 passengers.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.cardSpecialTrip.setOnClickListener {
            binding.switchSpecialTrip.isChecked = !binding.switchSpecialTrip.isChecked
        }

        binding.switchSpecialTrip.setOnCheckedChangeListener { _, isChecked ->
            isSpecialTrip = isChecked
            recalculateFare()
        }
    }

    private fun recalculateFare() {
        val origin = binding.actvOrigin.text?.toString()?.trim() ?: return
        val destination = binding.actvDestination.text?.toString()?.trim() ?: return

        if (origin.isEmpty() || destination.isEmpty()) return

        val tripType = if (isSpecialTrip) TripType.SPECIAL_CHARTER else TripType.STANDARD_SHARED

        val result = calculator.calculateFare(
            origin = origin,
            destination = destination,
            passengerType = currentPassengerType,
            passengerCount = currentPassengerCount,
            tripType = tripType
        )

        latestCalculation = result

        // Update UI
        binding.tvTotalFare.text = String.format(Locale.US, "₱%.2f", result.totalFare)

        val breakdownText = if (result.isSpecialTrip) {
            String.format(
                Locale.US,
                "Special Trip (Pakyaw): ₱%.2f flat tariff (Full vehicle reserved)",
                result.totalFare
            )
        } else if (result.discountAmount > 0.0) {
            String.format(
                Locale.US,
                "Base: ₱%.2f • 20%% Discount: -₱%.2f • %d pax",
                result.baseRatePerPassenger,
                result.discountAmount,
                result.passengerCount
            )
        } else {
            String.format(
                Locale.US,
                "Regulated Base: ₱%.2f/pax • %d Passenger(s)",
                result.baseRatePerPassenger,
                result.passengerCount
            )
        }
        binding.tvFareBreakdown.text = breakdownText

        binding.tvDistanceDisplay.text = String.format(
            Locale.US,
            "Distance: Approx. %.1f km",
            result.distanceKm
        )
        binding.tvServingToda.text = "Serving TODA: " + result.todaAssociation
        binding.tvOrdinanceRef.text = "Governed by " + result.applicableOrdinance
        binding.tvFareNotes.text = result.notes
    }

    private fun setupMatrixRecyclerView() {
        val allRoutes = repository.getAllRoutes()
        routeAdapter = RouteFareAdapter(allRoutes) { clickedRoute ->
            binding.actvOrigin.setText(clickedRoute.origin, false)
            binding.actvDestination.setText(clickedRoute.destination, false)

            binding.bottomNavigation.selectedItemId = R.id.nav_calculator
            recalculateFare()

            Toast.makeText(
                this,
                "Selected: ${clickedRoute.destination}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvRoutes.layoutManager = LinearLayoutManager(this)
        binding.rvRoutes.adapter = routeAdapter

        binding.etSearchMatrix.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                routeAdapter.filter(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupHotlineButtons() {
        binding.btnCallTmo.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0365408000"))
            startActivity(intent)
        }

        binding.btnCallPnp.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:09985986228"))
            startActivity(intent)
        }
    }

    private fun setupReportButton() {
        binding.btnReportOvercharging.setOnClickListener {
            showReportDialog()
        }
    }

    private fun showReportDialog() {
        val calc = latestCalculation
        val dialogBinding = DialogReportOverchargingBinding.inflate(LayoutInflater.from(this))

        if (calc != null) {
            dialogBinding.tvDialogRouteInfo.text = String.format(
                Locale.US,
                "Route: %s → %s\nApproved Regulated Fare: ₱%.2f",
                calc.origin,
                calc.destination,
                calc.totalFare
            )
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.report_title)
            .setView(dialogBinding.root)
            .setPositiveButton("Submit Report") { _, _ ->
                val bodyNum = dialogBinding.etBodyNumber.text?.toString()?.trim() ?: ""

                if (bodyNum.isEmpty()) {
                    Toast.makeText(this, "Please enter the Tricycle Body Number.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val ticketId = "SJ-TODA-" + Random.nextInt(1000, 9999)
                val message = "Report submitted! Ticket #$ticketId logged for Body #$bodyNum. The Municipal Traffic Office will be notified."

                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
                    .setAction("Call TMO") {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0365408000"))
                        startActivity(intent)
                    }
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
