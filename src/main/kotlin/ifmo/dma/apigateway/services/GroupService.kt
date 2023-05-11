package ifmo.dma.apigateway.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class GroupService( @Autowired private val redisMessageService: RedisMessageService,@Autowired private val responseService: ResponseService) {
    val requestChannel = "md-group-request"
    val responseChannel = "md-group-response"

    fun addUserToGroup(inviteCode: String, userId: Int?): ResponseEntity<String> {
        val request = String.format("""{
                "command": "enterGroup",
                "payload": { 
                    "userId": %s, 
                    "inviteCode": "%s"
                }
            }""".trimMargin(), userId, inviteCode)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            5 -> return ResponseEntity.status(405).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }

    fun deleteUserFromGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format("""{
            "command": "quitGroup",
            "payload": {
                "userId": %s,
                "id": %s
            }
        }""", userId, userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            5 -> return ResponseEntity.status(405).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }

    fun createGroup(userId: Int?, groupName: String): ResponseEntity<String> {
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
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            5 -> return ResponseEntity.status(405).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }

    fun deleteGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "deleteGroup",
                "payload": {
                    "userId": %s
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            5 -> return ResponseEntity.status(405).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }
    fun getGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "getGroup",
                "payload": {
                    "userId": %s
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            5 -> return ResponseEntity.status(405).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }


}
