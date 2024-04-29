package screening.test.employeeapp.service.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService,
): OncePerRequestFilter() {

    companion object{
        const val JWT_HEADER_NAME = "Authorization"
        const val JWT_TOKEN_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader(JWT_HEADER_NAME)
        if (authHeader != null && authHeader.startsWith(JWT_TOKEN_PREFIX)) {
            val jwtToken = authHeader.substringAfter(JWT_TOKEN_PREFIX)
            val userName = tokenService.getUsernameFromToken(jwtToken)

            if (SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(userName)

                if (tokenService.isValid(jwtToken, foundUser)) {
                    updateContext(foundUser, request)
                }

                filterChain.doFilter(request, response)
            }
        } else {
            filterChain.doFilter(request, response)
            return
        }
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }
}