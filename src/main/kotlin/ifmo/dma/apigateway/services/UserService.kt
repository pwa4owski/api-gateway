package ifmo.dma.apigateway.services

import ifmo.dma.apigateway.dto.RegisterRequest
import ifmo.dma.apigateway.dto.anyToMap
import org.springframework.stereotype.Service

@Service
class UserService constructor(private var messageProcessorService: MessageProcessorService) {

    fun createAccount(registerRequest: RegisterRequest){
        val mresponse = messageProcessorService.publishAndPop("register", anyToMap(registerRequest))
        if(mresponse.responseCode != 0)
            throw IllegalArgumentException("имя уже занято")
    }



}

