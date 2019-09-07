package com.service.ambulance.ambu.endpoint

import com.service.ambulance.ambu.data.Car
import com.service.ambulance.ambu.data.CarRepository
import com.service.ambulance.ambu.data.Request
import com.service.ambulance.ambu.data.RequestRepository
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
            @RequestBody request: Request,
            @RequestParam(value = "radius", defaultValue = "3", required = false)distance : Double): Iterable<Car>
    = backOfficeService.findCarsForRequest(request, distance)

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

    @PutMapping("update-car")
    fun updateCar(@RequestBody car:Car):Car = updateCar(car)
}