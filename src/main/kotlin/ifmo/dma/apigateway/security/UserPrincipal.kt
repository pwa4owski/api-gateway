package ifmo.dma.apigateway.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal : UserDetails {

    var login: String? = null
    var passwrd: String? = null
    var userRoles: List<UserRole> = emptyList()
    var userId: Int? = null
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return (userRoles.map { userRole -> SimpleGrantedAuthority(userRole.name) })
    }

    override fun getPassword(): String {
        return passwrd!!
    }

    override fun getUsername(): String {
        return login!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

enum class UserRole {
    ROLE_STUDENT,
    ROLE_ADMIN
}
