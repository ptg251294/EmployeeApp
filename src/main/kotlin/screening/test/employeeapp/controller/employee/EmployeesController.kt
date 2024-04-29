package screening.test.employeeapp.controller.employee

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import screening.test.employeeapp.service.domain.Employee
import screening.test.employeeapp.service.domain.EmployeesService
import java.util.*

@RestController
@RequestMapping("/api")
class EmployeesController(
    private val employeesService: EmployeesService,
) {

    @GetMapping("/employees")
    fun getEmployees(): ResponseEntity<List<EmployeeResponse?>> {
        val employees = employeesService
            .getAll()
            .map { it.toEmployeeResponse() }
            .map { it }
        return ResponseEntity.ok(employees)
    }

    @GetMapping("/employees/{id}")
    fun getEmployeeById(@PathVariable id: UUID): ResponseEntity<EmployeeResponse?> {
        val employee: EmployeeResponse = employeesService.findEmployeeById(id)?.toEmployeeResponse()!!

        return ResponseEntity.ok(employee)
    }
}

private fun Employee.toEmployeeResponse(): EmployeeResponse {
    return this.let {
        EmployeeResponse(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            salary = it.salary,
        )
    }
}
