package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.MessageDTO
import ifmo.dma.apigateway.services.QueueService
import ifmo.dma.apigateway.services.RedisMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
class ExampleController @Autowired constructor(
    val redisMessageService: RedisMessageService,
    @Autowired private val queueService: QueueService
) {

    @PostMapping("/api/hello")
    fun hello(): String {
        return "post-hello";
    }

    @GetMapping("/ping-auth")
    fun ping1(): String {
        return "pong!"
    }


    @GetMapping("/admin/ping")
    fun ping2(): String {
        return "admin-pong!"
    }


    @GetMapping("/student/ping")
    fun ping3(): String {
        return "student-pong!"
    }
//    @PostMapping("/api/groups/{group_id}/queues/{queues_id}")
//    fun updateQueue(@PathVariable group_id: Long, @PathVariable queues_id: Long, @Valid @RequestBody queue: Queue): Queue {
//        return gavno
//    }
//
//    @DeleteMapping("/api/groups/{group_id}/queues/{queues_id}")
//    fun deleteQueue(@PathVariable group_id: Long, @PathVariable queues_id: Long) {
//        return gavno
//    }
    @PostMapping("/api/groups/{group_id}/queues")
    fun getQueues(@PathVariable("group_id") groupId: Long): String {
        println(queueService.getAllQueues(groupId))
        return "asdas";
    }
}
