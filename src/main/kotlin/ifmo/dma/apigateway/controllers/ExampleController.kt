package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.MessageDTO
import ifmo.dma.apigateway.services.RedisMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.Duration

@RestController
class ExampleController @Autowired constructor(
    val redisMessageService: RedisMessageService,
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
}