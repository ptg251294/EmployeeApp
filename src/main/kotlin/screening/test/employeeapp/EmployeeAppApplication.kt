package screening.test.employeeapp

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class EmployeeAppApplication

fun main(args: Array<String>) {
	runApplication<EmployeeAppApplication>(*args)
}

fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)
