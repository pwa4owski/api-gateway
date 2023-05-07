package ifmo.dma.apigateway.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class QueuesService(@Autowired private val redisMessageService: RedisMessageService,
@Autowired private val responseService: ResponseService) {
    val requestChannel = "md-queue-request"
    val responseChannel = "md-queue-response"

    fun getAllQueues(userId: Int?): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "getAllQueues",
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
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
    }
    }
    fun getQueue(userId: Int?, queueId: Long): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "getQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(401).body(responseService.getErrorMessage(response))
            2 -> return ResponseEntity.status(402).body(responseService.getErrorMessage(response))
            3 -> return ResponseEntity.status(403).body(responseService.getErrorMessage(response))
            4 -> return ResponseEntity.status(404).body(responseService.getErrorMessage(response))
            else -> {
                return ResponseEntity.status(500).body("неизвестная ошибка")
            }
        }
    }

    fun enterQueue(userId: Int?, queueId: Long): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "enterQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
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
    fun quitQueue(userId: Int?, queueId: Long): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "quitQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
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
    fun createQueue(userId: Int?, queueName: String): String? {
        val request = String.format("""
            {
                "command": "createQueue",
                "payload": {
                    "userId": %s, 
                    "queueName": "%s"
                    }
            }""".trimMargin(), userId, queueName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;
    }
    fun deleteQueue(userId: Int?, queueId: Long): String? {
        val request = String.format("""
            {
                "command": "deleteQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;
    }


}
