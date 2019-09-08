package com.service.ambulance.ambu.service

import com.service.ambulance.ambu.data.Car
import com.service.ambulance.ambu.data.CarRepository
import com.service.ambulance.ambu.data.Driver
import com.service.ambulance.ambu.data.DriverRepository
import com.service.ambulance.ambu.data.Request
import com.service.ambulance.ambu.data.RequestRepository
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import java.util.*

@Service
class BackOfficeService(val requestRepo: RequestRepository, val carRepo: CarRepository, val driverRepo: DriverRepository){

    fun findRequestsByStatus(status: String): Iterable<Request>{
        val requests : Iterable<Request> ?= requestRepo.findByStatusOrderByBookingTimeDesc(status)

        if(null != requests ){
            return ArrayList(requests.map { it.copy() })
        }
        throw Exception("No request found with status $status")
    }

    fun findCarsForRequest(lon: Double, lat: Double, carType: String, distance : Double): Iterable<Car>{
        val fromLocation = GeoJsonPoint(lon, lat)
        val cars: Iterable<Car> ?=  carRepo
                .findByTypeAndStatusAndCurrentLocationNear(
                        carType,
                        "AVAILABLE",
                        fromLocation,
                        Distance(distance*1000))
        if(null != cars){
            return ArrayList(cars.map { it.copy() })
        }

        throw Exception("No ambulance found from location [$lon , $lat] of type $carType")
    }

    fun confirmRequest(request: Request): Request{

        request.assignedCar?.status = "TRANSIT"
        request.assignedCar?.updatedOn = Date()

        request.status = "BOOKED"
        request.confirmationTime = Date()
        carRepo.insert(request.assignedCar as Car)
        return requestRepo.insert(request).copy()
    }

    fun completeRequest(request: Request): Request{

        request.assignedCar?.status = "AVAILABLE"
        request.assignedCar?.updatedOn = Date()
        request.status = "COMPLETE"
        request.completionTime = Date()

        carRepo.insert(request.assignedCar as Car)
        return requestRepo.insert(request).copy()
    }

    fun addCar(car:Car, lon : Double, lat : Double):Car{
        car.createdOn = Date()
        car.updatedOn = car.createdOn
        car.status = "INACTIVE"
        car.currentLocation = GeoJsonPoint(lon, lat)
        return carRepo.insert(car).copy()
    }

    fun updateCarLocation(car:Car, lon : Double, lat : Double):Car{
        car.updatedOn = Date()
        car.currentLocation = GeoJsonPoint(lon, lat)
        return carRepo.save(car).copy()
    }

    fun updateCar(car:Car):Car{
        car.updatedOn = Date()
        return carRepo.save(car).copy()
    }

    fun findCarByNumber(carNumber: String) : Car{
        val carOpt : Optional<Car> = carRepo.findByCarNumber(carNumber)
        if(carOpt.isPresent){
            return carOpt.get().copy()
        }
        throw Exception("No Car found with number $carNumber ")
    }

    fun addDriver(driver: Driver) : Driver{
        driver.createdOn = Date()
        driver.updatedOn = driver.createdOn
        return driverRepo.insert(driver).copy()
    }

    fun updateDriver(driver: Driver) : Driver{
        driver.updatedOn = Date()
        return driverRepo.save(driver).copy()
    }

    fun findDriverById(driverId: String) : Driver{
        val driverOpt : Optional<Driver> = driverRepo.findById(driverId)
        if(driverOpt.isPresent){
            return driverOpt.get().copy()
        }
        throw Exception("No Driver found with id $driverId ")
    }




}