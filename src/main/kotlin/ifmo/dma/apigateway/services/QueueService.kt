package ifmo.dma.apigateway.services

import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QueueServiceService( @Autowired private val redisMessageService: RedisMessageService) {


    fun getAllQueues(group_id: Long):String{
        val queuesJson:String = redisMessageService.publishAndPop(
            "md-user-request",
            String.format("""{json{"command": "findAllByGroupId","payload":{"group_id":%s}}}""" , group_id),
            "md-user-response",
            defaultTimeout
        ) ?: "Message sent, but no response received yet. :("
        return queuesJson;
    }


}
