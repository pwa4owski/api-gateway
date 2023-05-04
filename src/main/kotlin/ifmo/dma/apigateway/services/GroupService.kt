package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GroupService( @Autowired private val redisMessageService: RedisMessageService) {
    val requestChannel = "md-group-request"
    val responseChannel = "md-group-response"

    fun addUserToGroup(inviteCode: String, userId: Long): String? {
        val request = String.format("""{
                "command": "enterGroup",
                "payload": { 
                    "userId": %s, 
                    "inviteCode":"%s"
                }
            }""".trimMargin(), userId, userId, inviteCode)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response;
    }

    fun deleteUserFromGroup(userId: Long): String? {
        val request = String.format("""{
            "command": "quitGroup",
            "payload": {
                "userId": %s
            }
        }""", userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response;
    }

    fun createGroup(userId: Long, groupName: String): String? {
        val request = String.format("""{
            "command": "createGroup",
            "payload": {
                "userId": %s,
                "groupName": "%s"
            }
        }""", userId, groupName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response;
    }


}
