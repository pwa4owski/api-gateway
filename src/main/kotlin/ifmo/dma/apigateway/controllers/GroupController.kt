package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.CreateGroupDTO
import ifmo.dma.apigateway.dto.EnterGroupDTO
import ifmo.dma.apigateway.services.GroupService
import ifmo.dma.apigateway.services.QueueService
import ifmo.dma.apigateway.services.RedisMessageService
import ifmo.dma.apigateway.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class GroupController @Autowired constructor( @Autowired val groupService: GroupService
) {
    @PostMapping("/users/{user_id}/groups")
    fun addToGroup(@PathVariable("user_id") userId: Long, @RequestBody enterGroupDTO: EnterGroupDTO): String? {
        val inviteCode = enterGroupDTO.inviteCode;
        return groupService.addUserToGroup(inviteCode, userId)
    }

    @DeleteMapping("/users/{user_id}/groups")
    fun deleteFromGroup(@PathVariable("user_id") userId: Long): String? {
        return groupService.deleteUserFromGroup(userId)
    }

    @PostMapping("/groups")
    fun createGroup(@RequestBody createGroupDTO: CreateGroupDTO): String? {
        val userId: Long = createGroupDTO.userId
        val groupName: String = createGroupDTO.groupName
        return groupService.createGroup(userId, groupName)

    }
}