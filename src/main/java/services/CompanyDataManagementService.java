package services;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import employeeTutorial.Employee;
import employeeTutorial.HibernateUtil;
import employeeTutorial.ImpossibleActionException;
import employeeTutorial.Project;
import employeeTutorial.Sport;
import employeeTutorial.SportGroup;

public class CompanyDataManagementService {

	Session session;
	Transaction txn;
	Logger logger;

//	@Inject
	private HibernateUtil hibernateUtil;

	public CompanyDataManagementService() {
		this.hibernateUtil = new HibernateUtil();
		this.logger = LoggerFactory.getLogger(CompanyDataManagementService.class);
	}

	public Set<Employee> getSportmembersFromGroup(Long id) throws ImpossibleActionException {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup SG WHERE SG.id = :param1", SportGroup.class);
			query.setParameter("param1", id);
			List<SportGroup> sportGroupList = query.getResultList();
			if (sportGroupList.size() == 0) {
				throw new ImpossibleActionException("No Sportsgroup found with given ID (ID = " + id + ").");
			} else {
				SportGroup sportGroup = sportGroupList.get(0);
				return sportGroup.getEmployees();
			}

		} finally {
			cleanup();
		}
	}

	public List<SportGroup> getSportGroups() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup S", SportGroup.class);
			return query.getResultList();
		} finally {
			cleanup();
		}
	}

	public List<Project> getProjects() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Project> query = session.createQuery("FROM Project P", Project.class);
			return query.getResultList();
		} finally {
			cleanup();
		}
	}

	public SportGroup getSportGroupById(long id) throws ImpossibleActionException {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup SG WHERE SG.id = :param1", SportGroup.class);
			query.setParameter("param1", id);
			List<SportGroup> resultList = query.getResultList();
			if (resultList.size() == 0 ) {
				throw new ImpossibleActionException("There is no Sportgroup with ID " + id + ".");
			} else {
				return resultList.get(0);
			}
		} finally {
			cleanup();
		}
	}

	public SportGroup getSportGroupByName(String name) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<SportGroup> query = session.createQuery("FROM SportGroup SG WHERE SG.name = :param1",
					SportGroup.class);
			query.setParameter("param1", name);
			List<SportGroup> resultList = query.getResultList();
			if (resultList.size() == 0) {
				return null;
			}
			return resultList.get(0);
		} finally {
			cleanup();
		}
	}

	public long addSportGroupToDb(SportGroup sportGroup) throws ImpossibleActionException {
		String name = sportGroup.getName();
		if (sportGroupExists(name)) {
			logger.error("Trying to add a sportgroup {} which name was already taken.", name);
			throw new ImpossibleActionException("There already exists a sportgroup with the given name!");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				txn = session.beginTransaction();
				session.save(sportGroup);
				txn.commit();
				return getSportGroupByName(name).getId();

			} finally {
				cleanup();
			}
		}
	}

	public int deleteSportGroup(String name) throws ImpossibleActionException {
		if (!sportGroupExists(name)) {
			logger.warn("Trying to delete a non-existing sportgroup.");
			throw new ImpossibleActionException("There is no sportgroup with name '" + name + "' !");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				this.txn = session.beginTransaction();
				Query<?> deleteQuery = session.createQuery("DELETE SportGroup SG WHERE SG.name = :params1");
				deleteQuery.setParameter("params1", name);
				int entitiesAfftected = deleteQuery.executeUpdate();
				txn.commit();
				return entitiesAfftected;
			} finally {
				cleanup();
			}
		}
	}

	public boolean sportGroupExists(String name) {
		SportGroup sportGroup = getSportGroupByName(name);

		if (sportGroup != null && sportGroup.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public long addSportToDb(Sport sport) throws ImpossibleActionException {
		String name = sport.getName();
		if (sportExists(name)) {
			logger.error("Trying to add a sport {} which name was already taken.", name);
			throw new ImpossibleActionException("There already exists a sport with the given name!");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				txn = session.beginTransaction();
				session.save(sport);
				txn.commit();
				return getSportByName(name).getId();

			} finally {
				cleanup();
			}
		}
	}

	public int deleteSport(String name) throws ImpossibleActionException {
		if (!sportExists(name)) {
			logger.warn("Trying to delete a non-existing sport.");
			throw new ImpossibleActionException("There is no sport with name '" + name + "' !");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				this.txn = session.beginTransaction();
				Query<?> deleteQuery = session.createQuery("DELETE Sport S WHERE S.name = :params1");
				deleteQuery.setParameter("params1", name);
				int entitiesAfftected = deleteQuery.executeUpdate();
				txn.commit();
				return entitiesAfftected;
			} finally {
				cleanup();
			}
		}
	}

	public boolean sportExists(String name) {
		Sport sport = getSportByName(name);

		if (sport != null && sport.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	private Sport getSportByName(String name) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Sport> query = session.createQuery("FROM Sport S WHERE S.name = :param1", Sport.class);
			query.setParameter("param1", name);
			List<Sport> resultList = query.getResultList();
			if (resultList.size() == 0) {
				return null;
			}
			return resultList.get(0);
		} finally {
			cleanup();
		}
	}

	public Long addProjectToDb(Project project) throws ImpossibleActionException {
		String title = project.getTitle();
		if (projectExists(title)) {
			logger.error("Trying to add a sport {} which name was already taken.", title);
			throw new ImpossibleActionException("There already exists a sport with the given name!");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				txn = session.beginTransaction();
				session.save(project);
				txn.commit();
				return getProjectByTitle(title).getId();

			} finally {
				cleanup();
			}
		}
	}

	private boolean projectExists(String title) {
		Project project = getProjectByTitle(title);

		if (project != null && project.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	private Project getProjectByTitle(String title) {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Project> query = session.createQuery("FROM Project P WHERE P.title = :param1", Project.class);
			query.setParameter("param1", title);
			List<Project> resultList = query.getResultList();
			if (resultList.size() == 0) {
				return null;
			}
			return resultList.get(0);
		} finally {
			cleanup();
		}
	}

	public int deleteProject(String title) throws ImpossibleActionException {
		if (!projectExists(title)) {
			logger.warn("Trying to delete a non-existing project.");
			throw new ImpossibleActionException("There is no project with name '" + title + "' !");
		} else {
			try {
				this.session = hibernateUtil.getSessionFactory().openSession();
				this.txn = session.beginTransaction();
				Query<?> deleteQuery = session.createQuery("DELETE Project P WHERE P.title = :params1");
				deleteQuery.setParameter("params1", title);
				int entitiesAfftected = deleteQuery.executeUpdate();
				txn.commit();
				return entitiesAfftected;
			} finally {
				cleanup();
			}
		}
	}

	public List<Sport> getSports() {
		try {
			this.session = hibernateUtil.getSessionFactory().openSession();
			Query<Sport> query = session.createQuery("FROM Sport S", Sport.class);
			return query.getResultList();
		} finally {
			cleanup();
		}
	}

	private void cleanup() {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}
}