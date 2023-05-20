package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.time.Duration
@Service
class ResponseService {
    val objectMapper = ObjectMapper()


    fun getPayload(jsonString: String?): String? {
        val jsonNode = objectMapper.readTree(jsonString)
        val payload = jsonNode.get("payload")
        return payload.toString()
    }

    fun getErrorCode(jsonString: String?): Int? {
        val jsonNode = objectMapper.readTree(jsonString)
        val errorCode = jsonNode.get("responseCode").asInt()
        return errorCode
    }

    fun getErrorMessage(jsonString: String?): String? {
        val jsonNode = objectMapper.readTree(jsonString)
        val errorCode = jsonNode.get("errorMessage").asText()
        return errorCode.toString()
    }
}
