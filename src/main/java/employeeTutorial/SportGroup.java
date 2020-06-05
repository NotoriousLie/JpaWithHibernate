package employeeTutorial;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sportgroup")
public class SportGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sportgroup_id;

	private String name;
	private int groupsize;

	@OneToOne
	private Sport sporttype;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "sportGroups", cascade = CascadeType.PERSIST)
	private Set<Employee> employees = new HashSet<>();

	public SportGroup() {
	}
	
	public SportGroup(String name, Sport sporttype) {
		this.name = name;
		this.sporttype = sporttype;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getGroupsize() {
		return groupsize;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		this.employees.add(employee);
		this.groupsize++;
	}

	public long getId() {
		return sportgroup_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		result = prime * result + groupsize;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (sportgroup_id ^ (sportgroup_id >>> 32));
		result = prime * result + ((sporttype == null) ? 0 : sporttype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SportGroup other = (SportGroup) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (groupsize != other.groupsize)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sportgroup_id != other.sportgroup_id)
			return false;
		if (sporttype == null) {
			if (other.sporttype != null)
				return false;
		} else if (!sporttype.equals(other.sporttype))
			return false;
		return true;
	}

	public void setId(long id) {
		this.sportgroup_id = id;
	}

	
	
}