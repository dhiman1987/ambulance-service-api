package com.service.ambulance.ambu.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Document(collection = "requests")
data class Request(
        @Id var id: String? = null,
        var  fromPlaceName: String,
        @Indexed(name = "2dsphereFromLocation")
        var fromLocation: GeoJsonPoint,
        var requestedCarType: String,
        var bookingTime: Date? = null,
        var confirmationTime: Date? = null,
        var completionTime: Date? = null,
        var assignedCar: Car? = null,
        var customer: Customer,
        var status: String? = null,
        var createdOn: Date? = null,
        var updatedOn: Date? = null)

data class Customer(
        var  name : String,
        var  address:String,
        var  mobile: String)

@Repository
interface RequestRepository : MongoRepository<Request, String> {

        fun findByStatusOrderByBookingTimeDesc(status: String) : Iterable<Request>
        fun findByStatusAndCustomer_MobileOrderByBookingTimeDesc(status: String, mobile: String) : Iterable<Request>
}