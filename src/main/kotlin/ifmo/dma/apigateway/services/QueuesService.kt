package ifmo.dma.apigateway.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class QueuesService(@Autowired private val redisMessageService: RedisMessageService) {
    val requestChannel = "md-group-request"
    val responseChannel = "md-group-response"

    fun getAllQueues(userId: Int?): String? {
        val request = String.format("""
            {
                "command": "getAllQueues",
                "payload": {
                    "userId": %s
                }
            }
        """.trimIndent(), userId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;

    }
    fun getQueue(userId: Int?, queueId: Long): String? {
        val request = String.format("""
            {
                "command": "getQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;
    }

    fun enterQueue(userId: Int?, queueId: Long): String? {
        val request = String.format("""
            {
                "command": "enterQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;
    }
    fun quitQueue(userId: Int?, queueId: Long): String? {
        val request = String.format("""
            {
                "command": "quitQueue",
                "payload": {
                    "userId": %s, 
                    "queueId": "%s"
                    }
            }""".trimMargin(), userId, queueId)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response;
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
