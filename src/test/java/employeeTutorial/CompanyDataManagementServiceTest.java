package employeeTutorial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

import services.CompanyDataManagementService;
import services.DepartmentService;
import services.EmployeeService;

@ExtendWith(MockitoExtension.class)
class CompanyDataManagementServiceTest {

	@InjectMocks // subject under test
	static CompanyDataManagementService sut = new CompanyDataManagementService();

	static EmployeeService empService = new EmployeeService();
	static DepartmentService depService = new DepartmentService();

	@Spy
	private HibernateUtil hut = new HibernateUtil();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Project projectSweet = new Project();
		Project projectDull = new Project();
		Project projectAwesome = new Project();
		projectSweet.setTitle("Sweet project");
		projectDull.setTitle("Dull project");
		projectAwesome.setTitle("Awesome project");
		sut.addProjectToDb(projectSweet);
		sut.addProjectToDb(projectDull);
		sut.addProjectToDb(projectAwesome);

		Sport sportGolf = new Sport();
		Sport sportFootball = new Sport();
		sportGolf.setName("Golf");
		sportFootball.setName("Football");
		sut.addSportToDb(sportGolf);
		sut.addSportToDb(sportFootball);

		SportGroup sportGroupGolf = new SportGroup();
		SportGroup sportGroupFootball = new SportGroup();
		sportGroupGolf.setName("Whole in 1");
		sportGroupGolf.setSport(sportGolf);
		sportGroupFootball.setName("FC Koelle");
		sportGroupFootball.setSport(sportFootball);
		sut.addSportGroupToDb(sportGroupFootball);
		sut.addSportGroupToDb(sportGroupGolf);
		Set<SportGroup> sportGroups = new HashSet<SportGroup>();
		sportGroups.add(sportGroupFootball);

		Department department1 = new Department();
		department1.setName("Department 1");
		depService.addDepartmentToDb(department1);

		Employee employeeDieter = new Employee();
		Employee employeeAnke = new Employee();
		employeeDieter.setName("Dieter");
		employeeDieter.setDepartment(department1);
		employeeDieter.setSportGroups(sportGroups);
		employeeAnke.setName("Anke");
		employeeAnke.setDepartment(department1);
		employeeAnke.setSportGroups(sportGroups);
		empService.addEmployeeToDb(employeeDieter);
		empService.addEmployeeToDb(employeeAnke);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

		sut.deleteProject("Sweet project");
		sut.deleteProject("Dull project");
		sut.deleteProject("Awesome project");

		sut.deleteSportGroup("Whole in 1");
		sut.deleteSportGroup("FC Koelle");

		sut.deleteSport("Golf");
		sut.deleteSport("Football");

		empService.deleteEmployee("Dieter");
		empService.deleteEmployee("Anke");

		depService.deleteDepartmentFromDb("Department 1");

	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getSportsTest() throws Exception {
		List<Sport> sports = sut.getSports();
		assertNotNull(sports);
		assertThat(sports).extracting(Sport::getName).contains("Golf", "Football");
	}

	@Test
	void getSportGroupsTest() {
		List<SportGroup> sportGroups = sut.getSportGroups();
		assertNotNull(sportGroups);
		assertThat(sportGroups).extracting(SportGroup::getName).contains("Whole in 1", "FC Koelle");
	}

	@Test
	void getProjectsTest() {
		List<Project> projects = sut.getProjects();
		assertNotNull(projects);
		assertThat(projects).extracting(Project::getTitle).contains("Sweet project", "Dull project");
	}

	@Test
	void getSportGroupByNameTest() {
		SportGroup sportgroup = sut.getSportGroupByName("Whole in 1");
		assertNotNull(sportgroup);
		assertThat(sportgroup.getName()).isEqualTo("Whole in 1");
		assertNotNull(sportgroup.getId());
		assertThat(sportgroup.getId()).isGreaterThan(1L);

	}

	@Test
	void getSportmembersFromGroupTest() throws Exception {
		SportGroup sportGroup = sut.getSportGroupByName("FC Koelle");
		assertNotNull(sportGroup);
		Long sportGroupId = sportGroup.getId();
		assertThat(sportGroupId).isGreaterThan(0L);
		assertNotNull(sut.getSportmembersFromGroup(sportGroupId));
		assertThat(sut.getSportmembersFromGroup(sportGroupId)).extracting(Employee::getName).contains("Dieter", "Anke");
	}

	@Test
	void generateErrorsTest() throws Exception {
		SportGroup sg = new SportGroup();
		sg.setName("FC Koelle");

		Sport sport = new Sport();
		sport.setName("Football");

		Project project = new Project();
		project.setTitle("Sweet project");

		assertThrows(ImpossibleActionException.class, () -> {
			sut.getSportmembersFromGroup(0L);
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.addSportGroupToDb(sg);
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.deleteSportGroup("Imaginary Sportgroup");
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.addSportToDb(sport);
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.deleteSport("Imaginary Sport");
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.addProjectToDb(project);
		});

		assertThrows(ImpossibleActionException.class, () -> {
			sut.deleteProject("Imaginary project");
		});

	}

	// TODO: momentan nicht möglich, etwas via ID zu finden. Fixen? Wie?
//	@Test
//	void getSportGroupByIdTest() {
//		SportGroup sportgroup = sut.getSportGroupById(3);
//		assertNotNull(sportgroup);
//		assertThat(sportgroup.getName()).isEqualTo("Whole in 1");
//	}

	// TODO: momentan nicht möglich, etwas via ID zu finden. Fixen? Wie?
//	@Test
//	void getAllSportmembersTest() {
//		List<Employee> employees = sut.getSportmembersFromGroup(44L);
//		assertNotNull(employees);
//		assertThat(employees).extracting(Employee::getName).contains(TESTUSER_SIR_DOUCHE_NAME);
//	}

	/**
	 * Zu 1: • Es muss die Id des Mitarbeiters herausgefunden werden, der die
	 * Anfrage stellt. Dies wird via Namen des anfragenden Mitarbeiters erledigt. •
	 * Es muss eine Id/Name der Sportgruppe übergeben werden, dessen Mitglieder der
	 * anfragende Mitarbeiter in Erfahrung bringen möchte. • Es muss eine Liste der
	 * Mitarbeiter mit Id der angefragten Sportgruppe ohne den anfragenden
	 * Mitarbeiter ausgegeben werden.
	 * 
	 * Zu 2: • Es muss eine Ausgabe aller Sportgruppen erledigt werden. (Nach
	 * welchen Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 3: • Es muss eine Ausgabe aller Abteilungen erledigt werden. (Nach welchen
	 * Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 4: • Es muss eine Ausgabe aller Projekte erledigt werden. (Nach welchen
	 * Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 5: • Es muss eine Id oder ein Name der Sportgruppe übergeben werden. • Mit
	 * dieser müssen Mitarbeiter gefiltert werden, sodass nur Mitarbeiter mit der
	 * übergeben Id ausgegeben werden.
	 */

}
