package com.service.ambulance.ambu.data

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Document(collection = "cars")
data class Car(@Id var id: String? = null,
               var number: String,
               @Indexed(name = "2dsphereCurrentLocation")
               var currentLocation: GeoJsonPoint,
               var status: String,
               var type: String,
               var driver: Driver,
               var onBoarderOn: Date,
               var updatedOn: Date)

@Repository
interface CarRepository : MongoRepository<Car, String> {

    fun findByTypeAndStatusAndCurrentLocationNear(type:String,
                                           status: String,
                                           currentLocation: Point,
                                           distance: Distance) : Iterable<Car>
}