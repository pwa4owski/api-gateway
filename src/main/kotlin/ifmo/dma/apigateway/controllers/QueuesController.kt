package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.CreateGroupDTO
import ifmo.dma.apigateway.dto.CreateQueueDTO
import ifmo.dma.apigateway.dto.EnterGroupDTO
import ifmo.dma.apigateway.security.UserPrincipal
import ifmo.dma.apigateway.services.GroupService
import ifmo.dma.apigateway.services.QueuesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class QueuesController @Autowired constructor(@Autowired val queuesService: QueuesService
) {


    @GetMapping("/group/queues")
    fun groupAllQueues(authentication: Authentication): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return queuesService.getAllQueues(userId);
    }

    @GetMapping("/group/queues/{queueId}")
    fun groupQueues(authentication: Authentication,@PathVariable queueId: Long): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return queuesService.getQueue(userId,queueId);
    }
    @PatchMapping("/group/queues/{queueId}")
    fun enterQueue(authentication: Authentication,@PathVariable queueId: Long): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return queuesService.enterQueue(userId,queueId);
    }
    @PatchMapping("/group/queues/{queueId}/quit")
    fun quitQueue(authentication: Authentication,@PathVariable queueId: Long): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return queuesService.quitQueue(userId,queueId);
    }

    @PostMapping("/group/queues")
    fun createQueue(authentication: Authentication,@RequestBody createQueueDTO: CreateQueueDTO): String? {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        val queueName: String = createQueueDTO.queueName
        return queuesService.createQueue(userId,queueName);
    }

    @DeleteMapping("/group/queues/{queueId}")
    fun deleteQueue(authentication: Authentication,@PathVariable queueId: Long): String? {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return queuesService.deleteQueue(userId,queueId);
    }

}
