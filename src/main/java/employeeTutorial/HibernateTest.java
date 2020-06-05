package employeeTutorial;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.query.Query;

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