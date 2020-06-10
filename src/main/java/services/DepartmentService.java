package services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employeeTutorial.Department;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;

public class DepartmentService {
	private Session session;
	private Transaction txn;

	private Logger logger;

//	@Inject
	private HibernateUtil hibernateUtil;

	public DepartmentService() {
		this.hibernateUtil = new HibernateUtil();
		this.logger = LoggerFactory.getLogger(DepartmentService.class);
	}

	public List<Department> getDepartments() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			List<Department> departments = session.createQuery("FROM Department D", Department.class).getResultList();
			return departments;
		} finally {
			cleanup();
		}

	}

	/**
	 * Adds a department to the database and returns the id of the newly created
	 * department
	 * 
	 * @param departmentName
	 * @return department id
	 * @throws ImpossibleActionException
	 */
	public long addDepartmentToDb(Department department) throws ImpossibleActionException {

		if (departmentExists(department.getName())) {
			logger.error("Trying to add a department which name was already taken.");
			throw new ImpossibleActionException("There already exists a department with the given name!");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				txn = session.beginTransaction();
				session.save(department);
				txn.commit();
				return getDepartmentByName(department.getName()).getId();

			} finally {
				cleanup();
			}
		}
	}

	public boolean departmentExists(String departmentName) {
		Department department = getDepartmentByName(departmentName);

		if (department != null && department.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Deletes a department from the database and returns the number of entities
	 * affected by the deletion
	 * 
	 * @param departmentName
	 * @return number of entities affected
	 * @throws ImpossibleActionException
	 */
	public int deleteDepartmentFromDb(String departmentName) throws ImpossibleActionException {

		if (!departmentExists(departmentName)) {
			logger.warn("Trying to delete a non-existing department {}.", departmentName);
			throw new ImpossibleActionException("There is no department with name '" + departmentName + "' !");
		} else {
			if (getDepartmentByName(departmentName).getEmployees().size() > 0) {
				logger.warn("Trying to delete a department with employees inside.");
				throw new ImpossibleActionException("Cannot delete department with employees still assigned to it.");
			}
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				this.txn = session.beginTransaction();
				Query deleteQuery = session.createQuery("DELETE Department D WHERE D.name = :params1");
				deleteQuery.setParameter("params1", departmentName);
				int entitiesAfftected = deleteQuery.executeUpdate();
				txn.commit();
				return entitiesAfftected;
			} finally {
				cleanup();
			}
		}
	}

	public Department getDepartmentByName(String departmentName) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Department D WHERE D.name = :param1";
			Query query = session.createQuery(hql, Department.class);
			query.setParameter("param1", departmentName);
			List<?> department = query.getResultList();
			if (department.size() == 0) {
				return null;
			}
			return (Department) department.get(0);
		} finally {
			cleanup();
		}
	}

	void cleanup() {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
}