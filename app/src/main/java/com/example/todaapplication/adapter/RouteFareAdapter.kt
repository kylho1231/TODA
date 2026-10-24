package com.example.todaapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todaapplication.databinding.ItemRouteFareBinding
import com.example.todaapplication.model.SanJoseRoute
import java.util.Locale

class RouteFareAdapter(
    private val allRoutes: List<SanJoseRoute>,
    private val onRouteClicked: (SanJoseRoute) -> Unit
) : RecyclerView.Adapter<RouteFareAdapter.RouteViewHolder>() {

    private var filteredList: List<SanJoseRoute> = allRoutes

    inner class RouteViewHolder(private val binding: ItemRouteFareBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(route: SanJoseRoute) {
            binding.tvDestination.text = route.destination
            binding.tvDistance.text = String.format(
                Locale.US,
                "From %s • Approx. %.1f km",
                route.origin,
                route.distanceKm
            )
            binding.tvTodaName.text = route.todaName
            binding.tvOrdinance.text = route.applicableOrdinance
            binding.tvRegularFare.text = String.format(Locale.US, "₱%.2f", route.regularFare)
            binding.tvDiscountedFare.text = String.format(Locale.US, "Disc: ₱%.2f", route.discountedFare)

            binding.root.setOnClickListener {
                onRouteClicked(route)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val binding = ItemRouteFareBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RouteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        val clean = query.trim().lowercase(Locale.ROOT)
        filteredList = if (clean.isEmpty()) {
            allRoutes
        } else {
            allRoutes.filter {
                it.destination.lowercase(Locale.ROOT).contains(clean) ||
                it.origin.lowercase(Locale.ROOT).contains(clean) ||
                it.todaName.lowercase(Locale.ROOT).contains(clean) ||
                it.landmarks.any { landmark -> landmark.lowercase(Locale.ROOT).contains(clean) }
            }
        }
        notifyDataSetChanged()
    }
}
