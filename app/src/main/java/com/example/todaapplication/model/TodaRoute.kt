package com.example.todaapplication.model

import java.io.Serializable
import kotlin.math.roundToInt

enum class PassengerType(val label: String, val discountPercent: Double) {
    REGULAR("Regular Commuter", 0.0),
    STUDENT("Student (Valid ID)", 0.20),
    SENIOR_CITIZEN("Senior Citizen (60+)", 0.20),
    PERSON_WITH_DISABILITY("Person with Disability (PWD)", 0.20)
}

enum class TripType(val label: String, val description: String, val charterMultiplier: Int) {
    STANDARD_SHARED(
        "Standard (Shared Ride)",
        "Standard per-passenger regulated seat rate",
        1
    ),
    SPECIAL_CHARTER(
        "Special Trip (Pakyaw / Solo)",
        "Exclusive solo ride / direct destination charter (standard 3-seat reservation)",
        3
    )
}

data class SanJoseRoute(
    val id: String,
    val origin: String,
    val destination: String,
    val distanceKm: Double,
    val regularFare: Double,
    val discountedFare: Double,
    val todaName: String,
    val applicableOrdinance: String,
    val landmarks: List<String> = emptyList()
) : Serializable

data class FareCalculationResult(
    val origin: String,
    val destination: String,
    val distanceKm: Double,
    val passengerType: PassengerType,
    val passengerCount: Int,
    val tripType: TripType,
    val baseRatePerPassenger: Double,
    val discountAmount: Double,
    val rateAfterDiscount: Double,
    val totalFare: Double,
    val applicableOrdinance: String,
    val todaAssociation: String,
    val isSpecialTrip: Boolean,
    val notes: String
) : Serializable

class SanJoseFareRepository {

    private val routes: List<SanJoseRoute> = listOf(
        SanJoseRoute(
            id = "R01",
            origin = "Poblacion (Town Proper)",
            destination = "Tradetown / Public Market",
            distanceKm = 1.0,
            regularFare = 15.0,
            discountedFare = 12.0,
            todaName = "San Jose de Buenavista TODA - Route 1",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Municipal Hall", "San Jose Public Market", "Barangay 3")
        ),
        SanJoseRoute(
            id = "R02",
            origin = "Poblacion (Town Proper)",
            destination = "St. Anthony's College (SAC)",
            distanceKm = 1.5,
            regularFare = 15.0,
            discountedFare = 12.0,
            todaName = "St. Joseph TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("SAC Main Gate", "St. Anthony Cathedral", "San Angel")
        ),
        SanJoseRoute(
            id = "R03",
            origin = "Poblacion (Town Proper)",
            destination = "Antique National School / EBJ Gym",
            distanceKm = 1.5,
            regularFare = 15.0,
            discountedFare = 12.0,
            todaName = "San Jose de Buenavista TODA - Route 1",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("ANS Campus", "EBJ Memorial Gym", "Barangay 4")
        ),
        SanJoseRoute(
            id = "R04",
            origin = "Poblacion (Town Proper)",
            destination = "Ceres Bus Terminal (Bantayan)",
            distanceKm = 2.2,
            regularFare = 18.0,
            discountedFare = 14.40,
            todaName = "Alliance of Concerned TODA (ACTODA)",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Bantayan Ceres Terminal", "Provincial Highway")
        ),
        SanJoseRoute(
            id = "R05",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Funda-Dalipe",
            distanceKm = 2.8,
            regularFare = 20.0,
            discountedFare = 16.0,
            todaName = "Funda-Dalipe TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Funda-Dalipe Plaza", "Coastal Road")
        ),
        SanJoseRoute(
            id = "R06",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay San Pedro",
            distanceKm = 3.0,
            regularFare = 20.0,
            discountedFare = 16.0,
            todaName = "San Pedro TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("San Pedro Parish", "San Pedro Brgy Hall")
        ),
        SanJoseRoute(
            id = "R07",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Maybato",
            distanceKm = 3.5,
            regularFare = 20.0,
            discountedFare = 16.0,
            todaName = "Maybato TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Maybato North", "Maybato South", "Resorts")
        ),
        SanJoseRoute(
            id = "R08",
            origin = "Poblacion (Town Proper)",
            destination = "Evelio B. Javier (EBJ) Airport",
            distanceKm = 4.2,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "San Jose Airport TODA Service",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Antique Airport Terminal", "Fundan Bridge")
        ),
        SanJoseRoute(
            id = "R09",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Atabay",
            distanceKm = 4.5,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "Atabay TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Atabay Elementary School", "Provincial Hospital area")
        ),
        SanJoseRoute(
            id = "R10",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Badiang",
            distanceKm = 4.8,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "Badiang TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Badiang Barangay Center", "Agricultural belt")
        ),
        SanJoseRoute(
            id = "R11",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Mojon",
            distanceKm = 5.0,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "Mojon TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Mojon Crossroad", "National Highway")
        ),
        SanJoseRoute(
            id = "R12",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Malandog",
            distanceKm = 5.5,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "Malandog Historical TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Malandog Landing Landmark", "Historic Riverbed")
        ),
        SanJoseRoute(
            id = "R13",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Igbonglo",
            distanceKm = 7.5,
            regularFare = 30.0,
            discountedFare = 24.0,
            todaName = "So-ong Pantao Igbonglo Lugutan TODA",
            applicableOrdinance = "Municipal Ordinance No. 2024-01",
            landmarks = listOf("Igbonglo Proper", "Lugutan Junction")
        ),
        SanJoseRoute(
            id = "R14",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Pantao",
            distanceKm = 8.2,
            regularFare = 30.0,
            discountedFare = 24.0,
            todaName = "So-ong Pantao Igbonglo Lugutan TODA",
            applicableOrdinance = "Municipal Ordinance No. 2024-01",
            landmarks = listOf("Pantao Barangay Hall", "So-ong Cross")
        ),
        SanJoseRoute(
            id = "R15",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Lugutan",
            distanceKm = 7.0,
            regularFare = 30.0,
            discountedFare = 24.0,
            todaName = "So-ong Pantao Igbonglo Lugutan TODA",
            applicableOrdinance = "Municipal Ordinance No. 2024-01",
            landmarks = listOf("Lugutan Primary School")
        ),
        SanJoseRoute(
            id = "R16",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Malaiba",
            distanceKm = 6.5,
            regularFare = 28.0,
            discountedFare = 22.40,
            todaName = "Malaiba TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Malaiba Bridge")
        ),
        SanJoseRoute(
            id = "R17",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Supa",
            distanceKm = 7.8,
            regularFare = 30.0,
            discountedFare = 24.0,
            todaName = "Supa TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Supa Barangay Plaza")
        ),
        SanJoseRoute(
            id = "R18",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Bariri",
            distanceKm = 5.2,
            regularFare = 25.0,
            discountedFare = 20.0,
            todaName = "Bariri TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Bariri River Bridge")
        ),
        SanJoseRoute(
            id = "R19",
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Magcalon",
            distanceKm = 6.0,
            regularFare = 28.0,
            discountedFare = 22.40,
            todaName = "Magcalon TODA",
            applicableOrdinance = "Municipal Ordinance No. 2022-18",
            landmarks = listOf("Magcalon Road")
        )
    )

    fun getAllRoutes(): List<SanJoseRoute> = routes

    fun getLocations(): List<String> {
        val set = mutableSetOf<String>()
        routes.forEach {
            set.add(it.origin)
            set.add(it.destination)
        }
        return set.toList().sorted()
    }

    fun findDirectRoute(loc1: String, loc2: String): SanJoseRoute? {
        if (loc1.equals(loc2, ignoreCase = true)) return null

        return routes.firstOrNull {
            (it.origin.equals(loc1, ignoreCase = true) && it.destination.equals(loc2, ignoreCase = true)) ||
            (it.origin.equals(loc2, ignoreCase = true) && it.destination.equals(loc1, ignoreCase = true))
        }
    }
}

class FareCalculator(private val repository: SanJoseFareRepository = SanJoseFareRepository()) {

    fun calculateFare(
        origin: String,
        destination: String,
        passengerType: PassengerType,
        passengerCount: Int = 1,
        tripType: TripType = TripType.STANDARD_SHARED
    ): FareCalculationResult {
        val safeCount = if (passengerCount < 1) 1 else passengerCount
        val directRoute = repository.findDirectRoute(origin, destination)

        val baseRate: Double
        val distance: Double
        val ordinance: String
        val association: String

        if (origin.equals(destination, ignoreCase = true)) {
            // Same zone / minimal trip inside same barangay
            baseRate = 15.0
            distance = 1.0
            ordinance = "Municipal Ordinance No. 2022-18 (Base Fare)"
            association = "Local Barangay TODA"
        } else if (directRoute != null) {
            baseRate = directRoute.regularFare
            distance = directRoute.distanceKm
            ordinance = directRoute.applicableOrdinance
            association = directRoute.todaName
        } else {
            // Inter-barangay transit passing through Poblacion
            val fromPoblacion1 = repository.findDirectRoute("Poblacion (Town Proper)", origin)
            val fromPoblacion2 = repository.findDirectRoute("Poblacion (Town Proper)", destination)

            val rate1 = fromPoblacion1?.regularFare ?: 15.0
            val rate2 = fromPoblacion2?.regularFare ?: 15.0
            val dist1 = fromPoblacion1?.distanceKm ?: 1.5
            val dist2 = fromPoblacion2?.distanceKm ?: 1.5

            baseRate = (rate1 + rate2) * 0.85 // Combined route transfer adjustment
            distance = dist1 + dist2
            ordinance = "Municipal Ordinance No. 2022-18 (Cross-Barangay Route)"
            association = "San Jose Unified TODA Network"
        }

        val discountPerPerson = baseRate * passengerType.discountPercent
        val discountedRatePerPerson = baseRate - discountPerPerson

        val totalFare: Double = if (tripType == TripType.SPECIAL_CHARTER) {
            // Special trip / Pakyaw reserves all passenger seats (3 seats)
            baseRate * tripType.charterMultiplier
        } else {
            discountedRatePerPerson * safeCount
        }

        val notes = when {
            tripType == TripType.SPECIAL_CHARTER ->
                "Special Trip (Pakyaw): Reserved solo charter rate for the sidecar."
            passengerType != PassengerType.REGULAR ->
                "Mandatory 20% discount applied under Philippine Law (RA 9994/RA 7277/RA 11314) & San Jose Traffic Code."
            else ->
                "Standard regulated fare under approved municipal tariff."
        }

        return FareCalculationResult(
            origin = origin,
            destination = destination,
            distanceKm = distance,
            passengerType = passengerType,
            passengerCount = safeCount,
            tripType = tripType,
            baseRatePerPassenger = baseRate,
            discountAmount = discountPerPerson * safeCount,
            rateAfterDiscount = discountedRatePerPerson,
            totalFare = totalFare,
            applicableOrdinance = ordinance,
            todaAssociation = association,
            isSpecialTrip = tripType == TripType.SPECIAL_CHARTER,
            notes = notes
        )
    }
}
