package ifmo.dma.apigateway.services

import ifmo.dma.apigateway.exception.MicroDbServiceException
import ifmo.dma.apigateway.dto.RegisterRequest
import ifmo.dma.apigateway.dto.anyToMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private var messageProcessorService: MessageProcessorService) {

    fun createAccount(registerRequest: RegisterRequest){
        val mResponse = messageProcessorService.publishAndPop("register", anyToMap(registerRequest))
        if(!mResponse.successful){
            throw MicroDbServiceException(mResponse.errorMessage)
        }
        val payloadMap = mResponse.payload as Map<*, *>
        if(mResponse.responseCode == 1) {
            if(!payloadMap.containsKey("login")){
                throw MicroDbServiceException("Unexpected payload of response. Must contain `login`")
            }
            val login = payloadMap["login"] as String
            throw IllegalArgumentException("login: $login is used by another customer")
        }
        if(mResponse.responseCode != 0){
            throw MicroDbServiceException("Unexpected responseCode")
        }
    }



}

