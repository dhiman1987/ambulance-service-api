package com.service.ambulance.ambu.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "drivers")
data class Driver(
        @Id var id: String? = null,
        var  name : String,
        var  address:String,
        @Indexed(unique=true)
        var  mobile: String,
        @Indexed(unique=true)
        var  email: String,
        @Indexed(unique=true)
        var  licenseNumber: String,
        var  gender: String,
        var dob: Date,
        var createdOn: Date,
        var updatedOn: Date)