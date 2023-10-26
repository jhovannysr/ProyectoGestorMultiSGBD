package view;

import java.util.List;

import dao.DepartmentDAO;
import io.IO;
import model.Department;
import model.Employee;

public class MenuDepartment {

	/**
	 * Clase a gestionar
	 */
	private DepartmentDAO departmentDAO;

	/**
	 * Constructor
	 * 
	 * @param departmentDAO
	 */
	public MenuDepartment(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
		while (menu());
	}

	/**
	 * (Jhovanny) - Crear objeto de tipo Departamento
	 * 
	 * @param departmentDAO
	 */
	private static void addDepartament(DepartmentDAO departmentDAO) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		departmentDAO.listDepartments();
		IO.print("jefe ? ");
		String idEmpleadoJefeS = IO.readStringWithout0();
		
		Department d;
		Boolean sinJefe = false;
		if (idEmpleadoJefeS.equals("")) { //Si no hay jefe
			d = Department.builder()
					.nombre(nombre).build();
			sinJefe = true;
		} else { //Si hay jefe
			int idEmpleadoJefe = Integer.parseInt(idEmpleadoJefeS);
			d = Department.builder()
					.nombre(nombre)
					.empleadoJefe(Employee.builder()
							.id(idEmpleadoJefe).build()
							).build();
		}

		boolean added = departmentDAO.add(d, sinJefe);
		IO.println(added ? "Añadido" : "No ha sido posible añadir el departamento");
	}

	/**
	 * (Jhovanny) - Mostrar departamentos
	 * 
	 * @param departmentDAO
	 */
	private static void show(DepartmentDAO departmentDAO) {
		departmentDAO.show().forEach(System.out::println);
	}

	/**
	 * (Jhovanny) - Consulta por id
	 */
	private void queryByID() {
		IO.print("ID del departamento a buscar:");
		int id = IO.readInt();
		System.out.println(departmentDAO.queryByID(id));
	}

	/**
	 * (Jhovanny) - Modificar departamento 
	 * 
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

		departmentDAO.listDepartments();
		IO.print("Jefe ID[" + d.getEmpleadoJefe().getId() + "] ? ");
		String empleadoJefe = IO.readStringWithout0();
		try {
			d.setEmpleadoJefe(Employee.builder().id(Integer.parseInt(empleadoJefe)).build());
		} catch (Exception e2) {
			d.setEmpleadoJefe(d.getEmpleadoJefe()); // pondrá el valor por defecto
		}

		boolean modificado = departmentDAO.modifyDepartment(d);
		System.out.println(modificado == false ? "Error al modificar" : "modificado exitosamente");
		return modificado;
	}

	/**
	 * (Naim) - Consulta por nombre
	 */
	private void queryByName() {
		IO.print("Nombre del departamento a buscar:");
		String nombre = IO.readString();
		System.out.println(departmentDAO.queryByName(nombre));

	}

	/**
	 * Borrar departamento
	 * @param departmentDAO
	 */
	private static void deleteDepartment(DepartmentDAO departmentDAO) {
	    IO.print("Id ?");
	    int id = IO.readInt();

	    boolean deleted = departmentDAO.delete(id);
	    IO.println(deleted ? "Eliminado" : "No ha sido posible eliminar el departamento");
	    
	}
	
	/**
	 * (Naim) - Menu Departamento
	 * 
	 * @return
	 */
	public boolean menu() {
		List<String> opciones = List.of("Add (A)", "Delete (D)", "Query by id (I)", "Query by name (N)", "Show (S)",
				"Modify (M)", "Back (B)");

		System.out.println(opciones);
		switch (IO.readUpperChar()) {
		case 'A':
			addDepartament(departmentDAO);
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
