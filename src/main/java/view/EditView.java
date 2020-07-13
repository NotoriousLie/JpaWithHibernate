package view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import employeeTutorial.Employee;
import service.EmployeeService;

@Named
@ViewScoped
public class EditView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Employee> employees1;
	private List<Employee> employees2;

	@Inject
	private EmployeeService service;

	@PostConstruct
	public void init() {
//		employees1 = service.createCars(10);
//		employees2 = service.createCars(10);
	}

	public List<Employee> getCars1() {
		return employees1;
	}

	public List<Employee> getCars2() {
		return employees2;
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

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}