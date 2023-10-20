package view;

import java.util.List;

import dao.BD;
import dao.DepartamentoGestor;
import dao.EmpleadoGestor;
import io.IO;

public class MenuMain {

	public static void main(String[] args) {
		List<String> opciones = List.of("MENU Departamento (d)"
				, "MENU Empleado (e)", "Salir (s)");
		
		while (true) {
			System.out.println(opciones);
			switch (IO.readString().toUpperCase().charAt(0)) {
			case 'D':
				var departGestor = new DepartamentoGestor();
				new MenuDepartamento(departGestor); 
				break;
			case 'E':
				var empleadoGestor = new EmpleadoGestor();
				new MenuEmpleado(empleadoGestor);
				break;
			case 'S':
				closeConnectionDB();
				return;
			default:
			}
		}
	}

	private static void closeConnectionDB() {
		BD.close();
	}
}
