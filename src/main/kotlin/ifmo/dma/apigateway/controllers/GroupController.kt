package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.EnterGroupDTO
import ifmo.dma.apigateway.services.GroupService
import ifmo.dma.apigateway.services.QueueService
import ifmo.dma.apigateway.services.RedisMessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GroupController @Autowired constructor( @Autowired val groupService: GroupService
) {
    @PostMapping("/users/{user_id}/groups")
    fun addToGroup(@PathVariable("user_id") userId: Long, @RequestBody enterGroupDTO: EnterGroupDTO): String? {
        val inviteCode = enterGroupDTO.inviteCode;
        return groupService.addUserToGroup(inviteCode, userId)
    }
}