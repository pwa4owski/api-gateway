package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.CreateGroupDTO
import ifmo.dma.apigateway.dto.EnterGroupDTO
import ifmo.dma.apigateway.security.UserPrincipal
import ifmo.dma.apigateway.services.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class GroupController @Autowired constructor( @Autowired val groupService: GroupService
) {
    @PatchMapping("/group")
    fun addToGroup(authentication: Authentication, @RequestBody enterGroupDTO: EnterGroupDTO): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        val userId = userPrincipal.userId
        val inviteCode = enterGroupDTO.inviteCode;
        return groupService.addUserToGroup(inviteCode, userId)
    }

    @PatchMapping("/group/quit")
    fun deleteFromGroup(authentication: Authentication): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        val userId = userPrincipal.userId
        return groupService.deleteUserFromGroup(userId)
    }

    @PostMapping("/group")
    fun createGroup(authentication: Authentication, @RequestBody createGroupDTO: CreateGroupDTO): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
        val userId: Int? = userPrincipal.userId
        val groupName: String = createGroupDTO.groupName
        return groupService.createGroup(userId, groupName)
    }


    @DeleteMapping("/group")
    fun deleteGroup(authentication: Authentication): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return groupService.deleteGroup(userId);
    }
    @GetMapping("/group")
    fun getGroup(authentication: Authentication): ResponseEntity<String> {
        val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal;
        val userId = userPrincipal.userId
        return groupService.getGroup(userId);
    }
}
