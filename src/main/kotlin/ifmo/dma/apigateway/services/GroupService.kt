package ifmo.dma.apigateway.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GroupService( @Autowired private val redisMessageService: RedisMessageService) {
    val requestChannel = "md-group-request"
    val responseChannel = "md-group-response"

    fun addUserToGroup(inviteCode: String, userId: Int?): String? {
        val request = String.format("""{
                "command": "enterGroup",
                "payload": { 
                    "userId": %s, 
                    "inviteCode": "%s"
                }
            }""".trimMargin(), userId, inviteCode)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response;
    }

    fun deleteUserFromGroup(userId: Int?): String? {
        val request = String.format("""{
            "command": "quitGroup",
            "payload": {
                "userId": %s,
                "id": %s
            }
        }""", userId, userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response;
    }

    fun createGroup(userId: Int?, groupName: String): String? {
        print("create group service invoked")
        val request = String.format("""
            {
                "command": "createGroup",
                "payload": {
                    "userId": %s, 
                    "string": "%s", 
                    "groupName": "%s"
                    }
            }""".trimMargin(), userId, groupName, groupName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        return response
    }

    fun deleteGroup(userId: Int?): String? {
        val request = String.format("""
            {
                "command": "deleteGroup",
                "payload": {
                    "userId": %s
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response
    }


}
