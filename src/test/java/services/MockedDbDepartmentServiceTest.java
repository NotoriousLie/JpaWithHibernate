package employeeTutorial;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import services.DepartmentService;

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
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testMockitoStuff() throws ImpossibleActionException {
		when(hut.getSessionFactory()).thenReturn(sf);
		when(sf.openSession()).thenReturn(mockSession);
		when(mockSession.createQuery(anyString(), any(Class.class))).thenReturn(mockDepartmentQuery);
		when(mockSession.beginTransaction()).thenReturn(tx);
		List<Department> resultListEmpty = new ArrayList<>();
		List<Department> resultList = new ArrayList<>();
		Department dep1 = new Department("gnarr");
		dep1.setId(123L);
		resultList.add(dep1);
		when(mockDepartmentQuery.getResultList()).thenReturn(resultListEmpty).thenReturn(resultList);
		Department department = new Department("some department");
		Assertions.assertThrows(ImpossibleActionException.class, () -> {
			service.addDepartmentToDb(department);
		});
	}

}
