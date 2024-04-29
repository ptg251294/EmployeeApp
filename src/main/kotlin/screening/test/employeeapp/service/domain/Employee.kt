package screening.test.employeeapp.service.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "employees", schema = "public")
data class Employee(
    @Id @Column(name = "id") val id: UUID,
    @Column(name = "first_name") val firstName: String,
    @Column(name = "last_name") val lastName: String,
    @Column(name = "salary") val salary: Int,
)
