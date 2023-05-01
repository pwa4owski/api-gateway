package ifmo.dma.apigateway.security


import com.fasterxml.jackson.databind.ObjectMapper
import ifmo.dma.apigateway.dto.MResponse
import ifmo.dma.apigateway.services.RedisMessageService
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class MyUserDetailsService(redisMessageService: RedisMessageService) : UserDetailsService {
    private val redisMessageService: RedisMessageService

    private val mapper = ObjectMapper()

    private final val defaultTimeout: Duration = Duration.ofSeconds(100)
    init {
        this.redisMessageService = redisMessageService
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val userJson:String = redisMessageService.publishAndPop(
            "md-user-request",
            String.format("""{"command":"login","""+""""payload":{"login":"%s"}}""" , username),
            "md-user-response",
            defaultTimeout
        ) ?: "Message sent, but no response received yet. :("

        val resp = mapper.readValue(userJson, MResponse::class.java)
        if(!(resp.payload as Map<*, *>).contains("user")){
            throw Exception("сервис БД сломался")
        }
        val userMap: LinkedHashMap<*, *> = resp.payload["user"] as LinkedHashMap<*, *>

        if (!(userMap.contains("login") && userMap.contains("admin")// userMap.contains("username")
                    && userMap.contains("password") && userMap.contains("id"))){
            throw Exception("сервис БД сломался")
        }
        val userPrincipal = UserPrincipal()
        userPrincipal.login = username
        userPrincipal.passwrd = userMap["password"] as String
        userPrincipal.userRoles = listOf(UserRole.ROLE_STUDENT)
        if(userMap["isAdmin"] != null && (userMap["admin"] as Boolean))
               userPrincipal.userRoles = userPrincipal.userRoles.plus(UserRole.ROLE_ADMIN)
        userPrincipal.userId = userMap["id"] as Int

        return userPrincipal
    }

    @get:Bean
    val passwordEncoder: PasswordEncoder
        get() = NoOpPasswordEncoder.getInstance()
}
