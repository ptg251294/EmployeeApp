package screening.test.employeeapp.service.domain


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "users")
data class User(
    @Id @Column(name = "username") val username: String,
    @Column(name = "password") val password: String,
    @Column(name = "role") val role: ROLE,
) {

    fun toUserDetails(): UserDetails {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .roles(this.role.name)
            .build()
    }
}

enum class ROLE {
    ADMIN, USER
}
