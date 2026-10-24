package com.example.todaapplication

import com.example.todaapplication.model.FareCalculator
import com.example.todaapplication.model.PassengerType
import com.example.todaapplication.model.SanJoseFareRepository
import com.example.todaapplication.model.TripType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FareCalculatorTest {

    private val repository = SanJoseFareRepository()
    private val calculator = FareCalculator(repository)

    @Test
    fun testPoblacionTownProperBaseFare() {
        val result = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Tradetown / Public Market",
            passengerType = PassengerType.REGULAR,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )

        assertNotNull(result)
        assertEquals(15.0, result.baseRatePerPassenger, 0.01)
        assertEquals(0.0, result.discountAmount, 0.01)
        assertEquals(15.0, result.totalFare, 0.01)
        assertTrue(result.applicableOrdinance.contains("2022-18"))
    }

    @Test
    fun testMandated20PercentDiscountForStudentSeniorAndPwd() {
        // Test Student Discount
        val studentResult = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Tradetown / Public Market",
            passengerType = PassengerType.STUDENT,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(15.0, studentResult.baseRatePerPassenger, 0.01)
        assertEquals(3.0, studentResult.discountAmount, 0.01)
        assertEquals(12.0, studentResult.totalFare, 0.01)

        // Test Senior Citizen Discount
        val seniorResult = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Tradetown / Public Market",
            passengerType = PassengerType.SENIOR_CITIZEN,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(12.0, seniorResult.totalFare, 0.01)

        // Test PWD Discount
        val pwdResult = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Tradetown / Public Market",
            passengerType = PassengerType.PERSON_WITH_DISABILITY,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(12.0, pwdResult.totalFare, 0.01)
    }

    @Test
    fun testIgbongloPantaoRouteUnderOrdinance202401() {
        // Under Municipal Ordinance 2024-01 for So-ong Pantao Igbonglo Lugutan
        val resultRegular = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Igbonglo",
            passengerType = PassengerType.REGULAR,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(30.0, resultRegular.baseRatePerPassenger, 0.01)
        assertEquals(30.0, resultRegular.totalFare, 0.01)
        assertTrue(resultRegular.applicableOrdinance.contains("2024-01"))

        val resultDiscounted = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Igbonglo",
            passengerType = PassengerType.STUDENT,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(30.0, resultDiscounted.baseRatePerPassenger, 0.01)
        assertEquals(6.0, resultDiscounted.discountAmount, 0.01)
        assertEquals(24.0, resultDiscounted.totalFare, 0.01)
    }

    @Test
    fun testMultiplePassengersCalculation() {
        val result = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Funda-Dalipe",
            passengerType = PassengerType.REGULAR,
            passengerCount = 3,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(20.0, result.baseRatePerPassenger, 0.01)
        assertEquals(60.0, result.totalFare, 0.01)
    }

    @Test
    fun testSpecialCharterPakyawTrip() {
        // A special solo ride / charter ("Pakyaw") reserves the vehicle
        val result = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Evelio B. Javier (EBJ) Airport",
            passengerType = PassengerType.REGULAR,
            passengerCount = 1,
            tripType = TripType.SPECIAL_CHARTER
        )
        // Standard charter rate is equivalent to 3 passenger seats
        val singleRate = 25.0
        assertEquals(singleRate * 3, result.totalFare, 0.01)
        assertTrue(result.isSpecialTrip)
    }

    @Test
    fun testBidirectionalRouteSymmetry() {
        val outbound = calculator.calculateFare(
            origin = "Poblacion (Town Proper)",
            destination = "Barangay Pantao",
            passengerType = PassengerType.REGULAR,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        val inbound = calculator.calculateFare(
            origin = "Barangay Pantao",
            destination = "Poblacion (Town Proper)",
            passengerType = PassengerType.REGULAR,
            passengerCount = 1,
            tripType = TripType.STANDARD_SHARED
        )
        assertEquals(outbound.totalFare, inbound.totalFare, 0.01)
    }

    @Test
    fun testRepositoryContainsOfficialSanJoseRoutes() {
        val routes = repository.getAllRoutes()
        assertTrue("Should have routes defined for San Jose Antique", routes.isNotEmpty())
        assertTrue("Should contain Funda-Dalipe", routes.any { it.destination.contains("Funda-Dalipe") })
        assertTrue("Should contain San Pedro", routes.any { it.destination.contains("San Pedro") })
        assertTrue("Should contain Igbonglo", routes.any { it.destination.contains("Igbonglo") })
        assertTrue("Should contain Pantao", routes.any { it.destination.contains("Pantao") })
        assertTrue("Should contain EBJ Airport", routes.any { it.destination.contains("EBJ") || it.destination.contains("Airport") })
    }
}
