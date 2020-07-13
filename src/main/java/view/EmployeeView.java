package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import employeeTutorial.Department;
import employeeTutorial.Employee;
import employeeTutorial.Project;
import employeeTutorial.Sport;
import employeeTutorial.SportGroup;
import service.EmployeeService;

@Named
@ViewScoped
public class EmployeeView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee employee = new Employee();
	private List<Employee> employees;

	@Inject
	private EmployeeService employeeService;

	@PostConstruct
	public void init() {

		employees = new ArrayList<Employee>();
		Department departmentOne = new Department("Department 1");
		Department departmentTwo = new Department("Department 2");

		Set<SportGroup> sportGroup = new HashSet<SportGroup>();
		SportGroup sportgr = new SportGroup();
		Sport sport = new Sport("football");
		sportgr.setId(123L);
		sportgr.setName("Koelle");
		sportgr.setSport(sport);
		sportGroup.add(sportgr);

		Set<Project> projectsOne = new HashSet<Project>();
		Set<Project> projectsTwo = new HashSet<Project>();
		Project tollesProject = new Project();
		Project kackProject = new Project();
		tollesProject.setTitle("tolles Projekt");
		kackProject.setTitle("kack Projekt");
		projectsOne.add(tollesProject);
		projectsOne.add(kackProject);
		projectsTwo.add(tollesProject);

		Employee empDieter = new Employee("Dieter", departmentOne, sportGroup, projectsOne);
		Employee empHans = new Employee("Hans", departmentTwo, sportGroup, projectsTwo);

		empDieter.setId(5L);
		empHans.setId(6L);

		employees.add(empDieter);
		employees.add(empHans);
	}

	public void addEmployee() {
		employeeService.addEmployeeToDb(employee);
		employee = new Employee();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void onRowSelect(SelectEvent<Employee> event) {
		FacesMessage msg = new FacesMessage("Employee Selected", event.getObject().getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent<Employee> event) {
		FacesMessage msg = new FacesMessage("Employee Unselected", event.getObject().getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
