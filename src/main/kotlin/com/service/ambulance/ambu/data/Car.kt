package com.service.ambulance.ambu.data

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


import java.util.*

@Document(collection = "ambulances")
data class Car(
               @Id var id: String? = null,
               @Indexed(unique=true)
               var carNumber: String,
               @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
               var currentLocation: GeoJsonPoint? = null,
               var status: String?= null,
               var type: String,
               @DBRef
               var driver: Driver? = null,
               var createdOn: Date? = null,
               var updatedOn: Date? = null)

@Repository
interface CarRepository : MongoRepository<Car, String> {

    fun findByTypeAndStatusAndCurrentLocationNear(type:String,
                                           status: String,
                                           currentLocation: Point,
                                           distance: Distance) : Iterable<Car>

    fun findByTypeAndStatus(type:String, status: String) : Iterable<Car>

    fun findByCarNumber(carNumber: String ) : Optional<Car>
}
