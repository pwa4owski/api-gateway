package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.CreateQueueDTO
import ifmo.dma.apigateway.security.UserPrincipal
import ifmo.dma.apigateway.services.QueueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class QueueController @Autowired constructor(
        @Autowired val queueService: QueueService
) {

    @PostMapping("/group/queues")
    fun createQueue(authentication: Authentication, @RequestBody createQueueDTO: CreateQueueDTO): String? {
        val userPrincipal = authentication.principal as UserPrincipal
        val userId = userPrincipal.userId
        val queueName = createQueueDTO.queueName
        return queueService.createQueue(userId, queueName)
    }

}