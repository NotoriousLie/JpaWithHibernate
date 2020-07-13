package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import employeeTutorial.Employee;
import service.EmployeeService;

@Named
@ViewScoped
public class AddRowView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Employee> employees;

	@Inject
	private EmployeeService service;

	@PostConstruct
	public void init() {
		employees = new ArrayList<Employee>();
	}

	public List<Employee> getCars1() {
		return employees;
	}

	public void setService(EmployeeService service) {
		this.service = service;
	}

	public void onRowEdit(RowEditEvent<Employee> event) {
		FacesMessage msg = new FacesMessage("Employee Edited", event.getObject().getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent<Employee> event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onAddNew() {
		// Add one new car to the table:
//		Car car2Add = service.createCars(1).get(0);
//		cars1.add(car2Add);
//		FacesMessage msg = new FacesMessage("New Car added", car2Add.getId());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}