package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageProcessorService(
    @Autowired private val redisMessageService: RedisMessageService
) {

    private val mapper = ObjectMapper()
    fun push(queue: String, payload: Any) {
        redisMessageService.push(
            queue, mapper.writeValueAsString(
                MResponse(
                    true,
                    "",
                    0,
                    payload
                )
            )
        )
    }

    fun pushError(queue: String, errorMessage: String, responseCode: Int) {
        redisMessageService.push(
            queue, mapper.writeValueAsString(
                MResponse(
                    false,
                    errorMessage,
                    responseCode,
                    object {}
                )
            )
        )
    }
}