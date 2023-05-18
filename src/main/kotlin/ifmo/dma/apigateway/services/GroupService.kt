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
                    "userId": %d, 
                    "inviteCode": "%s"
                }
            }""".trimMargin(), userId, inviteCode)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId уже находится в группе.")
            3 -> return ResponseEntity.status(400).body("Группа с кодом $inviteCode не найдена.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }

    fun deleteUserFromGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format(
            """{
            "command": "quitGroup",
            "payload": {
                "userId": %d
            }
        }""", userId, userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(400).body("Пользователь $userId является админом группы.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }

    fun createGroup(userId: Int?, groupName: String): ResponseEntity<String> {
        print("create group service invoked")
        val request = String.format(
            """
            {
                "command": "createGroup",
                "payload": {
                    "userId": %d, 
                    "groupName": "%s"
                    }
            }""".trimMargin(), userId, groupName, groupName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10));
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId уже находится в группе.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }

    fun deleteGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format(
            """
            {
                "command": "deleteGroup",
                "payload": {
                    "userId": %d
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится группе.")
            3 -> return ResponseEntity.status(403).body("Недостаточно прав для удаления группы.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }
    fun getGroup(userId: Int?): ResponseEntity<String> {
        val request = String.format(
            """
            {
                "command": "getGroup",
                "payload": {
                    "userId": %d
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }


}
