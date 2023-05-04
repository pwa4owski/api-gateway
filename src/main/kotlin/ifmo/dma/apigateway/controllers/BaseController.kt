package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.dto.RegisterRequest
import ifmo.dma.apigateway.services.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class BaseController @Autowired constructor(
    private val userService: UserService
) {

    @PostMapping("/users/register")
    private fun registerUser(@RequestBody @Valid registerRequest: RegisterRequest)
    {
        userService.createAccount(registerRequest)
    }

}
