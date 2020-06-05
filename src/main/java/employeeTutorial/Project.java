package employeeTutorial;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long project_id;

	@ManyToMany(mappedBy = "projects")
	private Set<Employee> employees = new HashSet<>();

	private String title;
	private Date startDate;
	private Date endDate;

	public Project() {
	}
	
	public Project(String title, Date startDate, Date endDate) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}

	public void removeEmployee(Employee employee) {
		this.employees.remove(employee);
	}

	public void setId(long id) {
		this.project_id = id;
	}
}
