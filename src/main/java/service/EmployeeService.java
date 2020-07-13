package service;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employeeTutorial.Employee;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;
import employeeTutorial.SportGroup;

@Stateless
public class EmployeeService {
	private Session session;
	private Transaction txn;

	private Logger logger;

	@Inject
	private HibernateUtil hibernateUtil;

	public EmployeeService() {
		this.logger = LoggerFactory.getLogger(EmployeeService.class);
	}

	public List<Employee> getEmployees() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			return session.createQuery("FROM Employee E", Employee.class).getResultList();
		} finally {
			cleanup();
		}
	}

	public Employee getEmployeeById(long id) throws ImpossibleActionException {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("FROM Employee E WHERE E.id = :param1");
			query.setParameter("param1", id);
			List<Employee> resultList = query.getResultList();
			if (resultList.size() == 0) {
				logger.error("Employee with ID {} does not exist.", id);
				throw new ImpossibleActionException("Cannot find employee with ID: " + id + ".");
			} else {
				return resultList.get(0);
			}
		} finally {
			cleanup();
		}
	}

	public Employee getEmployeeByName(String name) throws ImpossibleActionException {
		Employee employee = new Employee();
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("FROM Employee E WHERE E.name = :param1");
			query.setParameter("param1", name);
			List<Employee> resultList = query.getResultList();
			if (resultList.size() > 0) {
				employee = resultList.get(0);
				return employee;
			} else {
				return employee;
			}
		} catch (NoResultException exc) {
			logger.error("Employee with name {} does not exist.", name);
			throw new ImpossibleActionException("Cannot find employee '" + name + "'.");
		} finally {
			cleanup();
		}
	}

	public void addEmployeeToDb(Employee employee) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Transaction txn = session.beginTransaction();
			session.save(employee);
			txn.commit();
		} finally {
			cleanup();
		}
	}

	public int deleteEmployee(String employeeName) throws ImpossibleActionException {
		if (!employeeExists(employeeName)) {
			logger.warn("Trying to delete a non-existing employee.");
			throw new ImpossibleActionException("There is no employee with name '" + employeeName + "' !");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				this.txn = session.beginTransaction();
				Query deleteQuery = session.createQuery("DELETE Employee E WHERE E.name = :params1");
				deleteQuery.setParameter("params1", employeeName);
				int entitiesAfftected = deleteQuery.executeUpdate();
				txn.commit();
				return entitiesAfftected;
			} finally {
				cleanup();
			}
		}
	}

	public boolean employeeExists(String employeeName) throws ImpossibleActionException {
		Employee employee = getEmployeeByName(employeeName);

		if (employee != null && employee.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public Set<SportGroup> getSportgroupsFromMemberName(String name) throws ImpossibleActionException {
		return getEmployeeByName(name).getSportGroups();
	}

	void cleanup() {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

}
