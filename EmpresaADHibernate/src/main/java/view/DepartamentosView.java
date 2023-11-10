package view;

import java.util.List;
import java.util.Optional;

import io.IO;
import model.Departamento;
import model.Empleado;

public class DepartamentosView {

	final List<String> opciones = List.of("buscar por Código", "buscar por Nombre", "Mostrar", "Añadir", "modiFicar",
			"Eliminar", "Salir");

	public Character getOption() {
		IO.println("Departamentos: " + opciones);
		return Character.toUpperCase(IO.readChar());
	}

	public Departamento create() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Jefe ? ");
		Departamento d = Departamento.builder().nombre(nombre).build();
		return d;
	}

//	public Departamento modificar(Departamento d) {
//		if (d == null) {
//			IO.println("No se ha encontrado el departamento");
//			return null;
//		}
//		IO.printf("Nombre [%s] ? ", d.getNombre());
//		String nombre = IO.readString();
//		if (!nombre.isBlank()) {
//			d.setNombre(nombre);
//		}
//		IO.printf("Jefe [%s] ? ", d.getJefe() == null ? "sin jefe!!!" : d.getJefe());
//		Integer jefe = IO.readIntOrNull();
//		if (jefe != null) {
//			d.setJefe(Empleado.builder().id(jefe).build());
//		}
//		return d;
//	}

	public String buscarPorInicioDelNombre() {
		IO.print("El nombre empieza por ? ");
		return IO.readString();
	}

	public int buscarPorId() {
		IO.print("Código ? ");
		return IO.readInt();
	}

	public void mostrar(List<Departamento> list) {
		list.forEach(System.out::println);
	}
	
	public void mostrarPorId(Optional<Departamento> list) {
		list.stream().forEach(System.out::println);
	}

	public void result(String msg) {
		IO.println(msg);
	}

}
