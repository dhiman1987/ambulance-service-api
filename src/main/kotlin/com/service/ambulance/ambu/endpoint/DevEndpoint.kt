package com.service.ambulance.ambu.endpoint

import com.service.ambulance.ambu.data.*
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("v1/rest/api/dev/")
class DevEndpoint( val carRepo: CarRepository, val driverRepo: DriverRepository, val requestRepo: RequestRepository) {

    val sdf = SimpleDateFormat("DD-mm-YYYY")
    val driverOne  = Driver(
            name = "Driver One",
            address = "Address One",
            licenseNumber = "WB1111111111",
            dob = sdf.parse("02-05-1980"),
            gender = "M",
            mobile = "1111111111",
            email = "driverOne@gmail.com",
            createdOn = Date(),
            updatedOn = Date()
    )

    val driverTwo  = Driver(
            name = "Driver Two",
            address = "Address Two",
            licenseNumber = "WB1111111112",
            dob = sdf.parse("15-10-1985"),
            gender = "M",
            mobile = "1111111112",
            email = "driverTwo@gmail.com",
            createdOn = Date(),
            updatedOn = Date()
    )

    @GetMapping("load-drivers")
    fun loadDriverData(): String{
        driverRepo.deleteAll();
        driverRepo.insert(driverOne)
        driverRepo.insert(driverTwo)
        return "Loaded driver data ${driverOne.id} , ${driverTwo.id}"
    }

    @GetMapping("load-cars")
    fun loadCarData(): String{

        val pointWirelessPara = GeoJsonPoint(88.38403,22.767195)
        val pointMangalPandey = GeoJsonPoint(88.3552,22.767195)

        val carOne = Car(carNumber = "WB02A1212",
                type = "REGULAR",
                driver = driverOne,
                currentLocation = pointWirelessPara ,
                status = "IDLE",
                createdOn = Date(),
                updatedOn = Date())

        val carTwo = Car(carNumber = "WB02B2365",
                type = "REGULAR",
                driver = driverTwo,
                currentLocation = pointMangalPandey ,
                status = "IDLE",
                createdOn = Date(),
                updatedOn = Date())

        carRepo.deleteAll()
        carRepo.insert(carOne)
        carRepo.insert(carTwo)

        return "Loaded car data ${carOne.id}, ${carTwo.id}"
    }


}