package employeeTutorial;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long department_id;

	@Column(unique = true)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "department", cascade = CascadeType.PERSIST)
	private Set<Employee> employees = new HashSet<>();

	public Department() {
		super();
	}

	public Department(String name) {
		this.name = name;
	}

	public Long getId() {
		return department_id;
	}

	public void setId(Long id) {
		this.department_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}