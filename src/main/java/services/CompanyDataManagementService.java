package services;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employeeTutorial.Employee;
import employeeTutorial.HibernateUtil;
import employeeTutorial.Project;
import employeeTutorial.SportGroup;

public class CompanyDataManagementService {

	Session session;
	Transaction txn;
	Logger logger;

	@Inject
	private HibernateUtil hibernateUtil;

	public CompanyDataManagementService() {
		this.logger = LoggerFactory.getLogger(CompanyDataManagementService.class);
	}

	public List<Employee> getSportmembersFromGroup(Long id) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Employee> query = session.createQuery("FROM Employee E WHERE E.id = :param1", Employee.class);
			query.setParameter("param1", id);
			return query.getResultList();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public List<SportGroup> getSportGroups() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup S", SportGroup.class);
			return query.getResultList();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public List<Project> getProjects() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Project> query = session.createQuery("FROM Project P", Project.class);
			return query.getResultList();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public SportGroup getSportGroupById(long id) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup SG WHERE SG.id = :param1", SportGroup.class);
			query.setParameter("param1", id);
			return query.getResultList().get(0);
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

	public SportGroup getSportGroupByName(String name) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup SG WHERE SG.name = :param1",
					SportGroup.class);
			query.setParameter("param1", name);
			return query.getResultList().get(0);
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
}
