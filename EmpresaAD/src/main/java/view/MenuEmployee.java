package view;

import java.util.List;

import dao.BD;
import dao.EmployeeDAO;
import io.IO;
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
		int salario = IO.readInt();
		IO.print("Departamento ? ");
		String departamento = IO.readString();
		if (departamento.trim().equals("")) {
			departamento = BD.MRK_NULL;
		}
		
		boolean added = employeeDAO.add(new Employee(nombre, salario, departamento));
		IO.println(added ? "Añadido" : "No ha sido posible añadir el empleado");
	}
	
	
	/**
	 * (Jhovanny) - Mostrar empleados
	 * @param employeeDAO
	 */
	private void show() {
		employeeDAO.show().forEach(System.out::println);
	}
	/*
	 * (JeanPaul)Borrar empleado
	 */
	private static void deleteEmpleado(EmployeeDAO employeeDAO) {
	    IO.print("id?");
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
			e.setSalario(Integer.parseInt(salario));
		} catch (Exception e2) {
			e.setSalario(e.getSalario());
		}

		IO.print("Departamento [" + e.getDepartamento() + "] ? ");
		String departamento = IO.readString();
		e.setDepartamento(departamento.isBlank() ? e.getDepartamento() : departamento);
		
		boolean modificado = employeeDAO.updateEmployee(e, false);
		System.out.println(modificado == false ? "Error al modificar" : "modificado exitosamente");
		return modificado;
	}
	/*
	 * (JeanPaul) - Consulta por nombre
	 */
	private void queryByName() {
		IO.print("Nombre del departamento a buscar:");
		String nombre = IO.readString();
		System.out.println(employeeDAO.queryByName(nombre));
		
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
			deleteEmpleado(employeeDAO);
			break;
		case 'I':
			queryByID();
			break;
		case 'N':
			queryByName();
			break;
		case 'S':
			show();
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
