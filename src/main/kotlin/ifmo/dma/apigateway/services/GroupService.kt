package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GroupService( @Autowired private val redisMessageService: RedisMessageService) {
    fun addUserToGroup(inviteCode: String, userId: Long): String? {
        val request = String.format("""{"command": "enterGroup","payload":{"userId": %s, "id":%s, "inviteCode":"%s"}}""", userId, userId, inviteCode)
        val response = redisMessageService.publishAndPop("md-group-request", request, "md-group-response", Duration.ofSeconds(10));
        return response;
    }


}
