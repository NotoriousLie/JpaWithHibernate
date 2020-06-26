package employeeTutorial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Sport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sport_id;
	
	private String name;
	
	public Sport() {
	}
	
	public Sport(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.sport_id = id;
	}

	public Long getId() {
		return this.sport_id;
	}
}
