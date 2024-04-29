package screening.test.employeeapp.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import screening.test.employeeapp.repository.UserDAO
import screening.test.employeeapp.service.auth.CustomUserDetailsService

@Configuration
@EnableConfigurationProperties(value = [JwtProperties::class, SecurityProperties::class])
class Configuration {

    @Bean
    fun customUserDetailsService(userRepository: UserDAO): UserDetailsService =
        CustomUserDetailsService(userRepository)

    @Bean
    fun authManager(config: AuthenticationConfiguration) : AuthenticationManager =
        config.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(userRepository: UserDAO): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(customUserDetailsService(userRepository))
                it.setPasswordEncoder(passwordEncoder())
            }
}