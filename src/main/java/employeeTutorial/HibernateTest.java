package employeeTutorial;

import java.util.List;

import services.EmployeeService;

public class HibernateTest {

	private HibernateUtil hibernateUtil = new HibernateUtil();

	public static void main(String[] args) {
		EmployeeService empService = new EmployeeService();
		List<Employee> employees = empService.getEmployees();
		for (Employee employee : employees) {
			System.out.println(employee.getName());
		}
	}

	public HibernateUtil getHibernateUtil() {
		return hibernateUtil;
	}

	public void setHibernateUtil(HibernateUtil hibernateUtil) {
		this.hibernateUtil = hibernateUtil;
	}

}