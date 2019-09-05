package com.service.ambulance.ambu.endpoint

import com.service.ambulance.ambu.data.Request
import com.service.ambulance.ambu.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/rest/api/customer/")
class CustomerEndpoint(val customerService: CustomerService){

    @PostMapping("add-request")
    fun addRequest(@RequestBody newRequest: Request): Request = customerService.addRequest(newRequest)

    @PutMapping("cancel-request")
    fun cancelRequest(@RequestBody request: Request): Request = customerService.cancelRequest(request)

    @GetMapping("request-by-id")
    fun getRequest(@RequestParam("id") id: String) : Request = customerService.getRequest(id)

    fun getRequestByStatus(
            @RequestParam("status") status: String,
            @RequestParam("customerId") customerId: String) :
            Iterable<Request>
            = customerService.getRequestByStatus(status, customerId)
}