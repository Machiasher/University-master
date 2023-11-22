package com.example.universityTest.demo;

import com.example.universityTest.demo.exception.NotFoundEntityException;
import com.example.universityTest.demo.service.UniversityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class UniversityApp {

	private static final String WHO_IS_HEAD = "Who is head of department";
	private static final String STAT = "Show statistic";
	private static final String STATISTIC = " Statistic";
	private static final String AVERAGE_SALARY = "Average salary for the department";
	private static final String EMPLOYEE_COUNT = "Count employee";
	private static final String GLOBAL_SEARCH = "Global search by";


	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(UniversityApp.class, args);
		UniversityService service = context.getBean(UniversityService.class);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter command (or 'exit' for exit):");
		while (true) {
			String userInput = scanner.nextLine();

			if (userInput.equals("exit")) {
				System.out.println("Application is stopped.");
				break;
			}
			try {
				if (isCommandStartsWith(userInput, WHO_IS_HEAD)) {
					String departmentName = userInput.substring(WHO_IS_HEAD.length());
					System.out.printf("Head of %s department is %s" + System.lineSeparator(),
							departmentName,
							service.getDepartmentHead(departmentName));

				} else if (isCommandStartsWith(userInput, STAT) && userInput.endsWith(STATISTIC)) {
					String departmentName =
							userInput.substring(STAT.length(), userInput.indexOf(STATISTIC));
					System.out.println(service.getStatistic(departmentName));

				} else if (isCommandStartsWith(userInput, AVERAGE_SALARY)) {
					String departmentName = userInput.substring(AVERAGE_SALARY.length());
					System.out.printf("The average salary for the department %s is %s"
									+ System.lineSeparator(),
							departmentName,
							service.getAverageSalary(departmentName));

				} else if (isCommandStartsWith(userInput, EMPLOYEE_COUNT)) {
					String departmentName = userInput.substring(EMPLOYEE_COUNT.length());
					System.out.println(service.getCountOfEmployee(departmentName));

				} else if (isCommandStartsWith(userInput, GLOBAL_SEARCH)) {
					String template = userInput.substring(GLOBAL_SEARCH.length());
					String response = service.searchEverywhere(template);
					System.out.println(Objects.isNull(response) ? "Not any matches there" : response);

				} else {
					System.out.println("Not such a command: " + userInput);
				}
			} catch (NotFoundEntityException e) {
				System.out.println(e.getMessage());
			}
		}
		scanner.close();
	}
	private static boolean isCommandStartsWith(String userInput, String command) {
		return userInput.startsWith(command) && userInput.length() >= command.length();
	}
}
