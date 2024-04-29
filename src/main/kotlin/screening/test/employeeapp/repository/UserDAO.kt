package screening.test.employeeapp.repository

import org.springframework.stereotype.Repository
import screening.test.employeeapp.service.domain.User

@Repository
class UserDAO() {
    val users: MutableSet<User> = mutableSetOf()

    fun findByUsername(username: String): User? =
        users.find { user -> user.username == username }
}