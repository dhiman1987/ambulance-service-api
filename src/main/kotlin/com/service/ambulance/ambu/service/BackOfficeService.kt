package com.service.ambulance.ambu.service

import com.service.ambulance.ambu.data.Car
import com.service.ambulance.ambu.data.CarRepository
import com.service.ambulance.ambu.data.Request
import com.service.ambulance.ambu.data.RequestRepository
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import java.util.*

@Service
class BackOfficeService(val requestRepo: RequestRepository, val carRepo: CarRepository){

    fun findRequestsByStatus(status: String): Iterable<Request>{
        val requests : Iterable<Request> ?= requestRepo.findByStatusOrderByBookingTimeDesc(status)
        if(null != requests ){
            return requests
        }
        throw Exception("No request found with status $status")
    }

    fun findCarsForRequest(request: Request,  distance : Double): Iterable<Car>{
        val cars: Iterable<Car> ?=  carRepo
                .findByTypeAndStatusAndCurrentLocationNear(
                        request.requestedCarType,
                        "AVAILABLE",
                        request.fromLocation,
                        Distance(distance, Metrics.KILOMETERS))
        if(null != cars){
            return cars
        }

        throw Exception("No ambulance found from ${request.fromPlaceName} to ${request.toPlaceName} of type ${request.requestedCarType}")
    }

    fun confirmRequest(request: Request): Request{

        request.assignedCar.status = "TRANSIT"
        request.assignedCar.updatedOn = Date()

        request.status = "BOOKED"
        request.confirmationTime = Date()

        carRepo.insert(request.assignedCar)
        return requestRepo.insert(request)
    }

    fun completeRequest(request: Request): Request{

        request.assignedCar.status = "AVAILABLE"
        request.assignedCar.updatedOn = Date()
        request.status = "COMPLETE"
        request.completionTime = Date()

        carRepo.insert(request.assignedCar)
        return requestRepo.insert(request)
    }

    fun addCar(car:Car, lon : Double, lat : Double):Car{
        car.onBoarderOn = Date()
        car.updatedOn = car.onBoarderOn
        car.status = "INACTIVE"
        car.currentLocation = GeoJsonPoint(lon, lat)
        return carRepo.insert(car)
    }

    fun updateCarLocation(car:Car, lon : Double, lat : Double):Car{
        car.updatedOn = Date()
        car.currentLocation = GeoJsonPoint(lon, lat)
        return carRepo.insert(car)
    }

    fun updateCar(car:Car):Car{
        car.updatedOn = Date()
        return carRepo.insert(car)
    }
}