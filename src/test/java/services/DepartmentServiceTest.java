package services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import employeeTutorial.Department;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

	@InjectMocks // subject under test
	DepartmentService sut = new DepartmentService();

	@Spy
	private HibernateUtil hut = new HibernateUtil();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		Department department1 = new Department("Department 1");
		Department department3 = new Department("Department 3");
		Department departmentJava = new Department("java");

		sut.addDepartmentToDb(department1);
		sut.addDepartmentToDb(department3);
		sut.addDepartmentToDb(departmentJava);
	}

	@AfterEach
	void tearDown() throws Exception {
		sut.deleteDepartmentFromDb("Department 1");
		sut.deleteDepartmentFromDb("Department 3");
		sut.deleteDepartmentFromDb("java");
	}

	@Test
	void getDepartmentsTest() {
		List<Department> departments = sut.getDepartments();

		assertNotNull(departments);
		assertThat(departments).extracting(Department::getName).contains("Department 1", "Department 3", "java");
	}

	@Test
	void getDepartmentByNameTest() throws ImpossibleActionException {
		Department department = sut.getDepartmentByName("Department 1");

		assertNotNull(department);
		assertThat(department.getName()).isEqualTo("Department 1");
		assertNotNull(department.getId());
	}

	@Test
	void addDepartmentToDbTest() throws ImpossibleActionException {
		Department department = new Department("Cool Department!");
		long departmentId = sut.addDepartmentToDb(department);

		assertNotNull(departmentId);
	}

	@Test
	void deleteDepartmentTest() throws ImpossibleActionException {
		Department departmentCool = new Department("Cool Department!");
		sut.addDepartmentToDb(departmentCool);
		assertThat(sut.deleteDepartmentFromDb("Cool Department!")).isEqualTo(1);
		assertThrows(ImpossibleActionException.class, () -> {
			sut.deleteDepartmentFromDb("Cool Department!");
		});
	}

	@Test
	void deleteDepartmentWithUserTest() throws ImpossibleActionException {
		Department newDepartment = new Department("Full House");
		long departmentId = sut.addDepartmentToDb(newDepartment);
		Department addedDepartment = sut.getDepartmentByName("Full House");
		assertThat(addedDepartment.getId()).isEqualTo(departmentId);
		assertThat(sut.deleteDepartmentFromDb("Full House")).isEqualTo(1);
	}

}