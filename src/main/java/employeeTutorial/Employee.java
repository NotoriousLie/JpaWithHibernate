package employeeTutorial;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employee_id;

	@Column(unique = true)
	private String name;

	@Column(nullable = false)
	private double salary;

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "employee_project", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = {
			@JoinColumn(name = "project_id") })
	private Set<Project> projects = new HashSet<>();

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "employee_sportGroup", joinColumns = { @JoinColumn(name = "employee_id") }, inverseJoinColumns = {
			@JoinColumn(name = "sportgroup_id") })
	private Set<SportGroup> sportGroups = new HashSet<>();

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	public Employee() {
	}

	public Employee(String name, Department department, Set<SportGroup> sportGroups, Set<Project> projects) {
		this.name = name;
		this.department = department;
		this.sportGroups = sportGroups;
		this.projects = projects;
	}

	public Employee(String name, Department department, double salary) {
		this.name = name;
		this.department = department;
		this.salary = salary;
	}

	public Employee(String name) {
		this.name = name;
	}

	public Long getId() {
		return employee_id;
	}

	public void setId(Long id) {
		this.employee_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + employee_id + ", name=" + name + ", department=" + department.getName() + "]";
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Set<SportGroup> getSports() {
		return sportGroups;
	}

	public void setSports(Set<SportGroup> sports) {
		this.sportGroups = sports;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public Set<SportGroup> getSportGroups() {
		return sportGroups;
	}

	public void setSportGroups(Set<SportGroup> sportGroups) {
		this.sportGroups = sportGroups;
	}

}