package screening.test.employeeapp.controller.employee

import java.util.*

data class EmployeeResponse(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val salary: Int,
)
