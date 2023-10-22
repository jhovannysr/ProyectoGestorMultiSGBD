package view;

import java.util.List;

import dao.BD;
import dao.DepartmentDAO;
import dao.EmployeeDAO;
import io.IO;


/**
 * Gestor de departamentos y empleados
 * 
 * @since 23-10-2023
 * Apellidos temporales, no se los reales
 * @author Henry Jhovanny Aucapiña Tapia, Paul Elver galarga, Naim El Mostafa
 */
public class MenuMain {

	public static void main(String[] args) {
		List<String> opciones = List.of("MENU Departamento (D)"
				, "MENU Empleado (E)", "Salir (S)");
		
		while (true) {
			System.out.println(opciones);
			switch (IO.readUpperChar()) {
			case 'D':
				var departGestor = new DepartmentDAO();
				new MenuDepartment(departGestor); 
				break;
			case 'E':
				var empleadoGestor = new EmployeeDAO();
				new MenuEmployee(empleadoGestor);
				break;
			case 'S':
				closeConnectionDB();
				return;
			case 'X':
				BD.getConnection();
				BD.deleteDB();;
				BD.close();
				System.err.println("Base de datos eliminada");
				return;
			default:
			}
		}
	}

	private static void closeConnectionDB() {
		BD.close();
	}
}
