package ifmo.dma.apigateway.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class QueueService( @Autowired private val redisMessageService: RedisMessageService) {

    val requestChannel = "md-queue-request"
    val responseChannel = "md-queue-response"

    fun getAllQueues(group_id: Long):String{
        println("1")
        val queuesJson:String = redisMessageService.publishAndPop(
            "md-user-request",
            String.format("""{json{"command": "findAllByGroupId","payload":{"group_id":%s}}}""" , group_id),
            "md-user-response",
            Duration.ofSeconds(10)
        ) ?: "Message sent, but no response received yet. :("
        println("2")
        return queuesJson;
    }

    fun createQueue(userId: Int?, queueName: String): String? {
        val request = String.format("""
            {
                "command": "createQueue",
                "payload": {
                    "userId": %s,
                    "queueName": "%s"
                }
            }
        """.trimIndent(), userId, queueName)
        val response = redisMessageService.publishAndPop(requestChannel, request, responseChannel, Duration.ofSeconds(10))
        return response
    }


}
