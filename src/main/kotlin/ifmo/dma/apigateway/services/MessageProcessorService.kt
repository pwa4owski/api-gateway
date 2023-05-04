package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MRequest
import ifmo.dma.apigateway.dto.MResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class MessageProcessorService(
    @Autowired private val redisMessageService: RedisMessageService
) {

    private final val defaultTimeout: Duration = Duration.ofSeconds(100)

    private val mapper = ObjectMapper()

    fun publishAndPop(command: String, payload: Map<String, Any>) : MResponse{
        val strReposnse = redisMessageService.publishAndPop("md-user-request",
                mapper.writeValueAsString(
                    MRequest(
                        command = command,
                        payload = payload
                    )
                ),
            "md-user-response",
            timeout = defaultTimeout
        )
        return mapper.readValue(strReposnse, MResponse::class.java)
    }
}