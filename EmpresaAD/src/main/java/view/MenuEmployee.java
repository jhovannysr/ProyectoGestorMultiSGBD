package view;

import java.util.List;

import dao.BD;
import dao.DepartmentDAO;
import dao.EmployeeDAO;
import io.IO;
import model.Department;
import model.Employee;

public class MenuEmployee {

	/**
	 * Clase a gestionar
	 */
	private EmployeeDAO employeeDAO;

	/**
	 * Constructor
	 * @param empleGestor
	 */
	public MenuEmployee(EmployeeDAO empleGestor) {
		this.employeeDAO = empleGestor;
		while (menu());
	}
	
	/**
	 * (Jhovanny) - Crear objeto de tipo Empleado
	 * @param employeeDAO
	 */
	private void addEmployee() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		float salario = IO.readInt();
		employeeDAO.listDepartments();
		IO.print("Departamento ? ");
		String departamento = IO.readStringWithout0();

		Employee e;
		Boolean sinDepartamento = false;
		if (departamento.equals("")) { //Si no hay departamento
			e = Employee.builder()
					.nombre(nombre)
					.salario(salario)
					.build();
			sinDepartamento = true;
		} else { //Si hay departamento
			int idDepartamento = Integer.parseInt(departamento);
			e = Employee.builder()
					.nombre(nombre)
					.salario(salario)
					.departamento(Department.builder().id(idDepartamento).build())
					.build();
		}
		
		boolean added = employeeDAO.add(e, sinDepartamento);
		IO.println(added ? "Añadido" : "No ha sido posible añadir el empleado");
	}
	
	/**
	 * (Jhovanny) - Mostrar empleados
	 * @param employeeDAO
	 */
	private void showEmployees() {
		employeeDAO.show().forEach(System.out::println);
	}
	
	/**
	 * (Jhovanny) - Consulta por id
	 */
	private void queryByID() {
		IO.print("ID del empleado a buscar:");
		int id = IO.readInt();
		System.out.println(employeeDAO.queryByID(id));
	}
	
	/**
	 * (Jean Paul) - Consulta por nombre
	 */
	private void queryByName() {
		IO.print("Nombre del departamento a buscar:");
		String nombre = IO.readString();
		System.out.println(employeeDAO.queryByName(nombre));
	}
	/**
	 * (Jean Paul) - Borrar
	 */
	private static void deleteEmployee(EmployeeDAO employeeDAO) {
	    IO.print("Id ?");
	    int id = IO.readInt();

	    boolean deleted = employeeDAO.deleteEmployee(id);
	    IO.println(deleted ? "Eliminado" : "No ha sido posible eliminar el departamento");
	    
	}
	
	/**
	 * (Jhovanny) - Modificar Empleado
	 * @return
	 */
	private boolean modify() {
		IO.println("ID del empleado a modificar ? ");
		int id = IO.readInt();
		Employee e = employeeDAO.queryByID(id);
		if (e == null) {
			IO.println("No existe el id");
			return false;
		}
		
		IO.print("Nombre [" + e.getNombre() + "] ? ");
		String nombre = IO.readString();
		e.setNombre(nombre.isBlank() ? e.getNombre() : nombre);
		
		IO.print("Salario [" + e.getSalario() + "] ? ");
		String salario = IO.readString();
		try {
			e.setSalario(Float.parseFloat(salario));
		} catch (Exception e2) {
			e.setSalario(e.getSalario());
		}
		employeeDAO.listDepartments();
		IO.print("Departamento ID[" + e.getDepartamento().getId() + "] ? ");
		String departamento = IO.readStringWithout0();
		try {
			e.setDepartamento(Department.builder().id(Integer.parseInt(departamento)).build());
		} catch (Exception e2) {
			e.setDepartamento(e.getDepartamento()); // pondrá el valor por defecto
		}
		
		boolean modificado = employeeDAO.modifyEmployee(e);
		System.out.println(modificado == false ? "Error al modificar" : "modificado exitosamente");
		return modificado;
	}
	
	/**
	 * (Jhovanny) - Menu Empleado
	 * @return
	 */
	public boolean menu() {
		List<String> opciones = List.of("Add (A)"
				, "Delete (D)"
				, "Query by id (I)"
				, "Query by name (N)"
				, "Show (S)"
				, "Modify (M)"
				, "Back (B)");

		System.out.println(opciones);
		switch (IO.readUpperChar()) {
		case 'A':
			addEmployee();
			break;
		case 'D':
			deleteEmployee(employeeDAO);
			break;
		case 'I':
			queryByID();
			break;
		case 'N':
			queryByName();
			break;
		case 'S':
			showEmployees();
			break;
		case 'M':
			modify();
			break;
		case 'B':
			return false;
		default:
			break;
		}
		return true;
	}
	
}
