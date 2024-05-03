package screening.test.employeeapp.service.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import screening.test.employeeapp.repository.EmployeeDAO
import java.util.UUID

@Service
class EmployeesService(
    private val employeeDAO: EmployeeDAO
) {

    @Value("\${file.path}")
    private lateinit var filePath: String

    /**
     * Fetch all the employees
     */
    fun getAll(): List<Employee> = employeeDAO.findAll()

    /**
     * Method to get employee with provided username a.k.a. id
     */
    fun findEmployeeById(id: UUID): Employee? = employeeDAO.findById(id)

    /**
     * An async function to read file and load it data in repository
     */
    suspend fun readFile() {
        coroutineScope {
            val employees = mutableListOf<Employee>()

            val fileInputStream = EmployeesService::class.java.getResource(filePath)?.openStream()
            fileInputStream?.let { InputStreamReader(it) }?.let {
                BufferedReader(it).use { reader ->
                    var line: String?
                    reader.readLine()

                    while (reader.readLine().also { line = it } != null) {
                        line?.split(",")?.let { columns ->
                            val employee = Employee(UUID.randomUUID(), columns[1], columns[2], Integer.parseInt(columns[3]))
                            employees.add(employee)

                        }
                    }
                }
            }

            val deferredResults = employees.map { employee ->
                async {
                    employeeDAO.employees.add(employee)
                }
            }

            deferredResults.awaitAll()
        }
    }
}