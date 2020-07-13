package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import employeeTutorial.Department;
import employeeTutorial.Employee;
import employeeTutorial.Project;
import employeeTutorial.Sport;
import employeeTutorial.SportGroup;
import model.Message;
import service.EmployeeService;
import service.MessageService;

@Named
@RequestScoped
public class Bean {

	private Message message = new Message();
	
	private List<Message> messages;

	private Employee employee = new Employee();
	
	private List<Employee> employees;

	@Inject
	private MessageService messageService;
	
	@Inject
	private EmployeeService employeeService;

	@PostConstruct
	public void init() {
		messages = messageService.list();
		
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

	public void submit() {
		messageService.create(message);
		messages.add(message);
		message = new Message();
	}

	public Message getMessage() {
		return message;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}