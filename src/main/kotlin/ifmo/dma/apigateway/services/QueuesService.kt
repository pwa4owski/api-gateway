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
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
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
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(400).body("Очередь $queueId не найдена.")
            4 -> return ResponseEntity.status(400).body("Недостаточно прав для получения очереди $queueId.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }

    fun enterQueue(userId: Int?, queueId: Long): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "enterQueue",
                "payload": {
                    "userId": %d, 
                    "queueId": %d
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(404).body("Очередь $queueId не найдена.")
            4 -> return ResponseEntity.status(400).body("Недостаточно прав для присоединения к очереди $queueId.")
            5 -> return ResponseEntity.status(409).body("Пользователь $userId уже находится в очереди $queueId.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
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
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(404).body("Очередь $queueId не найдена.")
            4 -> return ResponseEntity.status(404).body("Очередь $queueId не найдена в вашей группе.")
            5 -> return ResponseEntity.status(409).body("Пользователь $userId не записан в очередь $queueId.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }
    fun createQueue(userId: Int?, queueName: String): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "createQueue",
                "payload": {
                    "userId": %s, 
                    "queueName": "%s"
                    }
            }""".trimMargin(), userId, queueName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(404).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(400).body("Недостаточно прав на создание очереди.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }
    fun deleteQueue(userId: Int?, queueId: Long): ResponseEntity<String> {
        val request = String.format("""
            {
                "command": "deleteQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        val errorCode=responseService.getErrorCode(response)
        when (errorCode) {
            0 -> return ResponseEntity.ok(responseService.getPayload(response))
            1 -> return ResponseEntity.status(400).body("Пользователь $userId не найден.")
            2 -> return ResponseEntity.status(400).body("Пользователь $userId не находится в группе.")
            3 -> return ResponseEntity.status(400).body("Недостаточно прав на удаление очереди.")
            4 -> return ResponseEntity.status(400).body("Очередь $queueId не найдена.")
            else -> {
                return ResponseEntity.status(500).body("Произошла неизвестная ошибка.")
            }
        }
    }


}
