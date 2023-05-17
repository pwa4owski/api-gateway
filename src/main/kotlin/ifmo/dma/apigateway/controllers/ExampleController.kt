package ifmo.dma.apigateway.controllers

import ifmo.dma.apigateway.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController // нахуя нам это? Го удалим ( © Артем Артемкин )
class ExampleController @Autowired constructor(
    val userService: UserService,
){

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
