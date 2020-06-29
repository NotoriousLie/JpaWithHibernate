package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import employeeTutorial.Department;
import employeeTutorial.Employee;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;
import employeeTutorial.Sport;
import employeeTutorial.SportGroup;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks // subject under test
	EmployeeService sut = new EmployeeService();

	@InjectMocks
	CompanyDataManagementService service = new CompanyDataManagementService();

	@InjectMocks
	DepartmentService depService = new DepartmentService();

	@Spy
	private HibernateUtil hut = new HibernateUtil();

	private static final String TESTUSER_SIR_DOUCHE_NAME = "Sir Douche";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// leerer Stand, jetzt neu bef�llen
		Set<SportGroup> sportGroups = new HashSet<SportGroup>();
		Sport sport = new Sport("Football");
		service.addSportToDb(sport);
		SportGroup sportGroup = new SportGroup("FC Koelle", sport);
		service.addSportGroupToDb(sportGroup);
		sportGroups.add(sportGroup);

		Department department1 = new Department("Department 1");
		Department department2 = new Department("Department 2");
		depService.addDepartmentToDb(department1);
		depService.addDepartmentToDb(department2);

		Employee employeeDouche = new Employee();
		employeeDouche.setName(TESTUSER_SIR_DOUCHE_NAME);
		employeeDouche.setSportGroups(sportGroups);
		employeeDouche.setDepartment(department2);
		sut.addEmployeeToDb(employeeDouche);

		Employee employeeElrond = new Employee();
		employeeElrond.setName("Elrond");
		employeeElrond.setDepartment(department1);
		sut.addEmployeeToDb(employeeElrond);

		Employee employeeGipsz = new Employee();
		employeeGipsz.setName("Jakab Gipsz");
		employeeGipsz.setId(48L);
		employeeGipsz.setSportGroups(sportGroups);
		employeeGipsz.setDepartment(department1);
		sut.addEmployeeToDb(employeeGipsz);

		Employee employeeHatheway = new Employee();
		employeeHatheway.setName("Ann Hatheway");
		employeeHatheway.setSportGroups(sportGroups);
		employeeHatheway.setDepartment(department2);
		sut.addEmployeeToDb(employeeHatheway);
	}

	@AfterEach
	void tearDown() throws Exception {
		// aufr�umen
		if (sut.employeeExists(TESTUSER_SIR_DOUCHE_NAME)) {
			sut.deleteEmployee(TESTUSER_SIR_DOUCHE_NAME);
		}

		if (sut.employeeExists("Elrond")) {
			sut.deleteEmployee("Elrond");
		}

		if (sut.employeeExists("Jakab Gipsz")) {
			sut.deleteEmployee("Jakab Gipsz");
		}

		if (sut.employeeExists("Ann Hatheway")) {
			sut.deleteEmployee("Ann Hatheway");
		}

		if (depService.departmentExists("Department 1")) {
			depService.deleteDepartmentFromDb("Department 1");
		}

		if (depService.departmentExists("Department 2")) {
			depService.deleteDepartmentFromDb("Department 2");
		}

		if (service.sportGroupExists("FC Koelle")) {
			service.deleteSportGroup("FC Koelle");
		}

		if (service.sportExists("Football")) {
			service.deleteSport("Football");
		}
	}

	@Test
	void getEmployeesTest() {
		List<Employee> employees = sut.getEmployees();
		assertNotNull(employees);
		assertThat(employees).extracting(Employee::getName).contains(TESTUSER_SIR_DOUCHE_NAME, "Elrond");
	}

	// TODO: momentan nicht m�glich, da id nicht bekannt ist. Fixen? wie?
//	@Test
//	void getEmployeeByIdTest() throws ImpossibleActionException {
//		Employee employee = sut.getEmployeeById(48L);
//		assertNotNull(employee);
//		assertThat(employee.getName()).isEqualTo("Jakab Gipsz");
//	}

	@Test
	void getEmployeeByNameTest() throws ImpossibleActionException {
		Employee employee = sut.getEmployeeByName(TESTUSER_SIR_DOUCHE_NAME);
		assertNotNull(employee);
		assertThat(employee.getName()).isEqualTo(TESTUSER_SIR_DOUCHE_NAME);
		assertNotNull(employee.getId());
	}

	@Test
	void getMembersInMySportgroupTest() throws ImpossibleActionException {
		Set<SportGroup> sportgroups = sut.getEmployeeByName("Sir Douche").getSportGroups();
		SportGroup douchesSportGroup = sportgroups.stream().findFirst().get();
		assertNotNull(douchesSportGroup);
		assertThat(douchesSportGroup.getName()).isEqualTo("FC Koelle");

		Set<Employee> empSet = douchesSportGroup.getEmployees();
		assertThat(empSet).extracting(Employee::getName).contains("Ann Hatheway", "Jakab Gipsz");
	}

	@Test
	void deleteNonExistentEmployeeTest() throws Exception {
		assertThrows(ImpossibleActionException.class, () -> {
			sut.deleteEmployee("Cool Employee");
		});
	}
	
	@Test
	void getEmployeeByIdTest() throws Exception {
		assertThrows(ImpossibleActionException.class,() -> {
			sut.getEmployeeById(0L);
		});
	}

}
