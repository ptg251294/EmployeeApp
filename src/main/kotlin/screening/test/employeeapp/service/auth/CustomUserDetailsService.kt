package screening.test.employeeapp.service.auth

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import screening.test.employeeapp.repository.UserDAO

class CustomUserDetailsService(
    private val userRepository: UserDAO,
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username)
            ?.toUserDetails()
            ?: throw UsernameNotFoundException("Username not found: $username")
}