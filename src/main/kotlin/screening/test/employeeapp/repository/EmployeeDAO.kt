package screening.test.employeeapp.repository

import org.springframework.stereotype.Repository
import screening.test.employeeapp.service.domain.Employee
import java.util.UUID

@Repository
class EmployeeDAO(
) {
    val employees: MutableList<Employee> = mutableListOf()

    fun findAll(): List<Employee> =
        employees

    fun findById(id: UUID): Employee? = employees.find {
        it.id == id
    }
}
