package com.service.ambulance.ambu.service

import com.service.ambulance.ambu.data.CarRepository
import com.service.ambulance.ambu.data.Request
import com.service.ambulance.ambu.data.RequestRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(val requestRepo: RequestRepository, val carRepo: CarRepository){

    fun addRequest(newRequest: Request): Request{
        newRequest.bookingTime = Date()
        newRequest.createdOn = newRequest.bookingTime
        newRequest.status = "INITIATED"

        val addedRequest = requestRepo.insert(newRequest)
        if(null == addedRequest.id){
            throw Exception("Failed to add new request")
        }
        return addedRequest.copy()
    }

    fun cancelRequest(request: Request): Request{
        request.completionTime = Date()
        request.status = "CANCELLED"
        val cancelled = requestRepo.insert(request)
        return cancelled
    }

    fun getRequest(id: String) : Request{
        val request: Optional<Request> = requestRepo.findById(id);
        if(request.isPresent){
            return request.get().copy()
        }
        throw Exception("No request found with id $id")
    }

    fun getRequestByStatus(status: String, customerId: String) : Iterable<Request>{
        val requests : Iterable<Request> ?= requestRepo.findByStatusAndCustomer_MobileOrderByBookingTimeDesc(status, customerId)
        if(null != requests ){
            return ArrayList(requests.map { it.copy() })
        }
        throw Exception("No request found with status $status and customer id $customerId")
    }


}