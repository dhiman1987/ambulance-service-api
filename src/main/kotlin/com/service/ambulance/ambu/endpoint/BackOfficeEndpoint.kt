package com.service.ambulance.ambu.endpoint

import com.service.ambulance.ambu.data.*
import com.service.ambulance.ambu.service.BackOfficeService
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/rest/api/back-office/")
class BackOfficeEndpoint(val backOfficeService: BackOfficeService){

    @GetMapping("request-by-status")
    fun findRequestsByStatus(@RequestParam("status") status: String): Iterable<Request>
            = backOfficeService.findRequestsByStatus(status)

    @GetMapping("find-ambulance")
    fun findCarsForRequest(
            @RequestParam("long") lon : Double,
            @RequestParam("lat") lat : Double,
            @RequestParam("requested-car-type") requestedCarType : String,
            @RequestParam(value = "radius", defaultValue = "3", required = false)distance : Double)
            : Iterable<Car>
    = backOfficeService.findCarsForRequest(lon, lat, requestedCarType, distance)

    @PutMapping("confirm-request")
    fun confirmRequest(@RequestBody request: Request): Request = backOfficeService.confirmRequest(request)

    @PutMapping("complete-request")
    fun completeRequest(@RequestBody request: Request): Request = backOfficeService.completeRequest(request)

    @PostMapping("add-car")
    fun addCar(@RequestBody car:Car, lon : Double, lat : Double):Car = backOfficeService.addCar(car, lon, lat)

    @PutMapping("update-car-location")
    fun updateCarLocation(
            @RequestBody car:Car,
            @RequestParam("long")lon : Double,
            @RequestParam("lat")lat : Double):Car
    = backOfficeService.updateCarLocation(car, lon, lat)

    @GetMapping("find-ambulance-by-number")
    fun findCarByNumber(@RequestParam("carNumber") carNumber : String) : Car
            = backOfficeService.findCarByNumber(carNumber)

    @PutMapping("update-car")
    fun updateCar(@RequestBody car:Car):Car = backOfficeService.updateCar(car)

    @PostMapping("add-driver")
    fun addDriver(driver : Driver) : Driver =  backOfficeService.addDriver(driver)

    @PutMapping("update-driver")
    fun updateDriver(driver : Driver) : Driver =  backOfficeService.updateDriver(driver)

    @GetMapping("find-driver-by-id")
    fun findDriverById(@RequestParam("id") id : String) : Driver
            = backOfficeService.findDriverById(id)
}