package employeeTutorial;

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

import services.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks // subject under test
	EmployeeService sut = new EmployeeService();

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
		SportGroup sportGroup = new SportGroup("FC Koelle", new Sport("Football"));
		Set<SportGroup> sportGroups = new HashSet<SportGroup>();
		sportGroups.add(sportGroup);
		
		Employee employeeDouche = new Employee();
		employeeDouche.setName(TESTUSER_SIR_DOUCHE_NAME);
		employeeDouche.setId(44L);
		Employee employeeElrond = new Employee();
		employeeElrond.setName("Elrond");
		Employee employeeGipsz = new Employee();
		employeeGipsz.setName("Jakab Gipsz");
		employeeGipsz.setId(48L);
		Employee employeeHatheway = new Employee();
		employeeHatheway.setName("Ann Hatheway");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getEmployeesTest() {
		List<Employee> employees = sut.getEmployees();
		assertNotNull(employees);
		assertThat(employees).extracting(Employee::getName).contains(TESTUSER_SIR_DOUCHE_NAME, "Elrond");
	}

	@Test
	void getEmployeeByIdTest() {
		Employee employee = sut.getEmployeeById(48L);
		assertNotNull(employee);
		assertThat(employee.getName()).isEqualTo("Jakab Gipsz");
	}

	@Test
	void getEmployeeByNameTest() throws ImpossibleActionException {
		Employee employee = sut.getEmployeeByName(TESTUSER_SIR_DOUCHE_NAME);
		assertNotNull(employee);
		assertThat(employee.getId()).isEqualTo(44L);
	}

	@Test
	void addAndDeleteEmployeeToDbTest() throws Exception {
		Set<SportGroup> sportGroups = new HashSet<SportGroup>();
		SportGroup sg = new SportGroup();
		sg.setId(1L);
		sportGroups.add(sg);

		Set<Project> projects = new HashSet<Project>();
		Project project1 = new Project();
		Project project2 = new Project();
		project1.setId(1L);
		project2.setId(2L);
		projects.add(project1);
		projects.add(project2);

		Department dp = new Department();
		dp.setId(4L);

		Employee employee = new Employee("Deletable Employee", dp, sportGroups, projects);
		sut.addEmployeeToDb(employee);
		assertThat(sut.getEmployeeByName("Deletable Employee")).isNotNull();
		int deletedEmployees = sut.deleteEmployee("Deletable Employee");
		assertThat(deletedEmployees).isEqualTo(1);
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

}
