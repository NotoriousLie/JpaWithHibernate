package employeeTutorial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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

@ExtendWith(MockitoExtension.class)
class CompanyDataManagementServiceTest {
	
	@InjectMocks // subject under test
	CompanyDataManagementService sut = new CompanyDataManagementService();

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
	}

	@AfterEach
	void tearDown() throws Exception {
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
	void getSportGroupByIdTest() {
		SportGroup sportgroup = sut.getSportGroupById(3);
		assertNotNull(sportgroup);
		assertThat(sportgroup.getName()).isEqualTo("Whole in 1");
	}

	@Test
	void getSportGroupByNameTest() {
		SportGroup sportgroup = sut.getSportGroupByName("Whole in 1");
		assertNotNull(sportgroup);
		assertThat(sportgroup.getId()).isEqualTo(3);

	}

	@Test
	void getAllSportmembersTest() {
		List<Employee> employees = sut.getSportmembersFromGroup(44L);
		assertNotNull(employees);
		assertThat(employees).extracting(Employee::getName).contains(TESTUSER_SIR_DOUCHE_NAME);
	}


	/**
	 * Zu 1: � Es muss die Id des Mitarbeiters herausgefunden werden, der die
	 * Anfrage stellt. Dies wird via Namen des anfragenden Mitarbeiters erledigt. �
	 * Es muss eine Id/Name der Sportgruppe �bergeben werden, dessen Mitglieder der
	 * anfragende Mitarbeiter in Erfahrung bringen m�chte. � Es muss eine Liste der
	 * Mitarbeiter mit Id der angefragten Sportgruppe ohne den anfragenden
	 * Mitarbeiter ausgegeben werden.
	 * 
	 * Zu 2: � Es muss eine Ausgabe aller Sportgruppen erledigt werden. (Nach
	 * welchen Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 3: � Es muss eine Ausgabe aller Abteilungen erledigt werden. (Nach welchen
	 * Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 4: � Es muss eine Ausgabe aller Projekte erledigt werden. (Nach welchen
	 * Kriterien soll diese sortiert werden?)
	 * 
	 * Zu 5: � Es muss eine Id oder ein Name der Sportgruppe �bergeben werden. � Mit
	 * dieser m�ssen Mitarbeiter gefiltert werden, sodass nur Mitarbeiter mit der
	 * �bergeben Id ausgegeben werden.
	 */

}
