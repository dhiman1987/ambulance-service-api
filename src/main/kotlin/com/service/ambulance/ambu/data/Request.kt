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
        var  toPlaceName:String,
        @Indexed(name = "2dsphere")
        var fromLocation: GeoJsonPoint,
        @Indexed(name = "2dsphere")
        var toLocation: GeoJsonPoint,
        var requestedCarType: String,
        var bookingTime: Date,
        var confirmationTime: Date,
        var completionTime: Date,
        var assignedCar: Car,
        var customer: Customer,
        var status: String,
        var createdOn: Date,
        var updatedOn: Date)

@Repository
interface RequestRepository : MongoRepository<Request, String> {

        fun findByStatusOrderByBookingTimeDesc(status: String) : Iterable<Request>
        fun findByStatusAndCustomer_idOrderByBookingTimeDesc(status: String, customerId: String) : Iterable<Request>
}