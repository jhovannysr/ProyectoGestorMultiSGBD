package view;

import java.util.List;

import dao.BD;
import dao.DepartmentDAO;
import io.IO;
import model.Department;
import model.Employee;

public class MenuDepartment {

	/**
	 * Clase a gestionar
	 */
	private DepartmentDAO departmentDAO;

//	private static final String MRK_NULL = "null";
	
	/**
	 * Constructor
	 * 
	 * @param departGestor
	 */
	public MenuDepartment(DepartmentDAO departGestor) {
		this.departmentDAO = departGestor;
		while (menu());
	}
	
	/**
	 * (Jhovanny) - Crear objeto de tipo Departamento
	 * @param departmentDAO
	 */
	private static void addDepartment(DepartmentDAO departmentDAO) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("jefe ? ");
		String jefe = IO.readString();
		if (jefe.trim().equals("")) {
			jefe = BD.MRK_NULL;
		}
		
		boolean added = departmentDAO.add(new Department(nombre, jefe));
		IO.println(added ? "Añadido" : "No ha sido posible añadir el departamento");
	}
	
	
	private static void deleteDepartment(DepartmentDAO departmentDAO) {
	    IO.print("Id ?");
	    int id = IO.readInt();

	    boolean deleted = departmentDAO.delete(id);
	    IO.println(deleted ? "Eliminado" : "No ha sido posible eliminar el departamento");
	    
	}


	/**
	 * (Jhovanny) - Mostrar departamentos
	 * @param departmentDAO
	 */
	private static void show(DepartmentDAO departmentDAO) {
		departmentDAO.show().forEach(System.out::println);
	}
	
	/**
	 * (Jhovanny) - Consulta por ID
	 */
	private void queryByID() {
		IO.print("ID del departamento a buscar:");
		int id = IO.readInt();
		System.out.println(departmentDAO.queryByID(id));
	}
	
	private void queryByName() {
		IO.print("Nombre del departamento a buscar:");
		String nombre = IO.readString();
		System.out.println(departmentDAO.queryByName(nombre));
		
	}
	
	/**
	 * (Jhovanny) - Modificar departamento
	 * @return
	 */
	private boolean modify() {
		IO.println("ID del departamento a modificar ? ");
		int id = IO.readInt();
		Department d = departmentDAO.queryByID(id);
		if (d == null) {
			IO.println("No existe el id");
			return false;
		}
		
		IO.print("Nombre [" + d.getNombre() + "] ? ");
		String nombre = IO.readString();
		d.setNombre(nombre.isBlank() ? d.getNombre() : nombre);

		IO.print("Jefe[" + d.getJefe() + "] ? ");
		String jefe = IO.readString();
		d.setJefe(jefe.isBlank() ? d.getJefe() : jefe);
		
		boolean modificado = departmentDAO.updateDepartment(d);
		System.out.println(modificado == false ? "Error al modificar" : "modificado exitosamente");
		return modificado;
	}
	
	/**
	 * (Jhovanny) - Menu Departamento
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
			addDepartment(departmentDAO);
			break;
		case 'D':
			deleteDepartment(departmentDAO);
			break;
		case 'I':
			queryByID();
			break;
		case 'N':
			queryByName();
			break;
		case 'S':
			show(departmentDAO);
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
