package ifmo.dma.apigateway.security

import ifmo.dma.apigateway.exception.MicroDbServiceException
import ifmo.dma.apigateway.services.MessageProcessorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService @Autowired constructor(
     private val messageProcessorService: MessageProcessorService
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val mResponse = messageProcessorService.publishAndPop("login", mapOf("login" to username))
        if(!mResponse.successful){
            throw MicroDbServiceException(mResponse.errorMessage)
        }

        val payloadMap = mResponse.payload as Map<*, *>
        if(mResponse.responseCode == 1) {
            if(!payloadMap.containsKey("login")){
                throw MicroDbServiceException("Unexpected payload of response. Must contain `login`");
            }
            val login = payloadMap["login"] as String
            throw UsernameNotFoundException("login: $login not found")
        }
        if(mResponse.responseCode != 0){
            throw MicroDbServiceException("Unexpected responseCode")
        }
        if (!(payloadMap.contains("login") && payloadMap.contains("admin")
                    && payloadMap.contains("password") && payloadMap.contains("id"))){
            throw MicroDbServiceException("Unexpected payload of response. Must contain all required field")
        }
        val userPrincipal = UserPrincipal()
        userPrincipal.login = username
        userPrincipal.passwrd = payloadMap["password"] as String
        userPrincipal.userRoles = listOf(UserRole.ROLE_STUDENT)
        if(payloadMap["admin"] != null && (payloadMap["admin"] as Boolean))
               userPrincipal.userRoles = userPrincipal.userRoles.plus(UserRole.ROLE_ADMIN)
        userPrincipal.userId = payloadMap["id"] as Int


        return userPrincipal
    }

    @get:Bean
    val passwordEncoder: PasswordEncoder
        get() = NoOpPasswordEncoder.getInstance()
}
