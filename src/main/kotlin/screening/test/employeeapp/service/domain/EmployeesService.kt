package screening.test.employeeapp.service.domain

import org.springframework.stereotype.Service
import screening.test.employeeapp.repository.EmployeeDAO
import java.util.UUID

@Service
class EmployeesService(
    private val employeeDAO: EmployeeDAO
) {

    /**
     * Fetch all the employees
     */
    fun getAll(): List<Employee> = employeeDAO.findAll()

    /**
     * Method to get employee with provided username a.k.a. id
     */
    fun findEmployeeById(id: UUID): Employee? = employeeDAO.findById(id)
}