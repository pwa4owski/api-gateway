package ifmo.dma.apigateway.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableMethodSecurity
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(conf: AuthenticationConfiguration): AuthenticationManager {
        return conf.authenticationManager
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

         http.httpBasic().and().csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/users/register", "/signin", "/api/**", "/users/**", "/groups/**").permitAll()
            .requestMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .disable()

        return http.build()
    }
}
