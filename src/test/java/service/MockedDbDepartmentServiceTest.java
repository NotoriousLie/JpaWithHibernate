package services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import employeeTutorial.Department;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;

@ExtendWith(MockitoExtension.class)
class MockedDbDepartmentServiceTest {

	@InjectMocks
	DepartmentService service = new DepartmentService();

	@Mock
	private HibernateUtil hut;

	@Mock
	private SessionFactory sf;

	@Mock
	private Session mockSession;

	@Mock
	private Query<Department> mockDepartmentQuery;

	@Mock
	private Transaction tx;

	@Mock
	private Serializable ser;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddExistingDepartmentToDbWithMockito() throws ImpossibleActionException {
		when(hut.getSessionFactory()).thenReturn(sf);
		when(sf.openSession()).thenReturn(mockSession);
		when(mockSession.createQuery(anyString(), any(Class.class))).thenReturn(mockDepartmentQuery);
		List<Department> resultList = new ArrayList<>();
		Department dep1 = new Department("gnarr");
		dep1.setId(123L);
		resultList.add(dep1);
		when(mockDepartmentQuery.getResultList()).thenReturn(resultList);

		Assertions.assertThrows(ImpossibleActionException.class, () -> {
			service.addDepartmentToDb(dep1);
		});

	}

	@Test
	void testGetDepartmentsWithMockito() throws Exception {
		when(hut.getSessionFactory()).thenReturn(sf);
		when(sf.openSession()).thenReturn(mockSession);
		when(mockSession.createQuery(anyString(), any(Class.class))).thenReturn(mockDepartmentQuery);
		List<Department> resultList = new ArrayList<Department>();
		Department dep1 = new Department("gnarr");
		dep1.setId(123L);
		resultList.add(dep1);
		when(mockDepartmentQuery.getResultList()).thenReturn(resultList);
		assertThat(service.getDepartments()).isEqualTo(resultList);
	}

	@Test
	void testAddNewDepartmentWithMockito() throws Exception {
		when(hut.getSessionFactory()).thenReturn(sf);
		when(sf.openSession()).thenReturn(mockSession);
		when(mockSession.beginTransaction()).thenReturn(tx);
		when(mockSession.createQuery(anyString(), any(Class.class))).thenReturn(mockDepartmentQuery);

		List<Department> resultList = new ArrayList<>();
		List<Department> emptyList = new ArrayList<>();
		Department dep1 = new Department();
		dep1.setId(5L);
		resultList.add(dep1);

		when(mockDepartmentQuery.getResultList()).thenReturn(emptyList).thenReturn(resultList);

		assertThat(service.addDepartmentToDb(dep1)).isEqualTo(5L);

		ArgumentCaptor<Department> capturedDepartment = ArgumentCaptor.forClass(Department.class);
		verify(mockSession, times(1)).save(capturedDepartment.capture());
		assertEquals(dep1, capturedDepartment.getValue());
	}

	@Test
	void testCleanup() throws Exception {
		when(mockSession.isOpen()).thenReturn(true);
		service.cleanup();
		verify(mockSession, times(1)).close();
	}

	@Test
	void testDeleteDepartment() throws Exception {
		when(hut.getSessionFactory()).thenReturn(sf);
		when(sf.openSession()).thenReturn(mockSession);
		when(mockSession.beginTransaction()).thenReturn(tx);
		when(mockSession.createQuery(anyString(), any(Class.class))).thenReturn(mockDepartmentQuery)
				.thenReturn(mockDepartmentQuery).thenReturn(mockDepartmentQuery).thenReturn(mockDepartmentQuery);

		List<Department> resultList = new ArrayList<>();
		List<Department> emptyList = new ArrayList<>();
		Department dep1 = new Department();
		dep1.setId(5L);
		dep1.setName("fubar");
		resultList.add(dep1);

		when(mockSession.createQuery(anyString())).thenReturn(mockDepartmentQuery);
		when(mockDepartmentQuery.getResultList()).thenReturn(resultList).thenReturn(resultList);
		when(mockDepartmentQuery.setParameter(anyString(), any())).thenReturn(mockDepartmentQuery);
		when(mockDepartmentQuery.executeUpdate()).thenReturn(1);
		
		assertThat(service.deleteDepartmentFromDb("fubar")).isEqualTo(1);
		verify(mockSession, times(4)).createQuery(anyString(), any(Class.class));
	}
}
