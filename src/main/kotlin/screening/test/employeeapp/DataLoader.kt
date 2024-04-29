package screening.test.employeeapp

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import screening.test.employeeapp.repository.EmployeeDAO
import screening.test.employeeapp.repository.UserDAO
import screening.test.employeeapp.service.domain.Employee
import screening.test.employeeapp.service.domain.ROLE
import screening.test.employeeapp.service.domain.User
import java.util.*

/**
 * A class to load some dummy data for app demonstration
 */
@Component
class DataLoader(
    private val employeeDAO: EmployeeDAO,
    private val userDAO: UserDAO,
    private val encoder: PasswordEncoder,
) : CommandLineRunner {

    companion object {
        val log = getLogger(DataLoader::class.java)
    }

    override fun run(vararg args: String?) {
        val employee1 = Employee(
            id = UUID.fromString("d7bba830-67b8-4a4a-a317-e977336893b9"),
            firstName = "Vriti",
            lastName = "Kewlani",
            salary = 999999
        )
        val employee2 = Employee(
            id = UUID.fromString("4afedfdc-6aac-4a5f-bceb-d54eb282acc5"),
            firstName = "Pulkit",
            lastName = "Kewlani",
            salary = 99999
        )
        val user1 = User(username = "ptg-admin", password = encoder.encode("admin-password"), role = ROLE.ADMIN)
        val user2 = User(username = "ptg-user", password = encoder.encode("user-password"), role = ROLE.USER)

        employeeDAO.employees.add(employee1)
        log.info("Vriti added in employee repository")
        employeeDAO.employees.add(employee2)
        log.info("Pulkit added in employee repository")
        userDAO.users.add(user1)
        log.info("ptg-admin added in user repository")
        userDAO.users.add(user2)
        log.info("ptg-user added in user repository")
    }
}