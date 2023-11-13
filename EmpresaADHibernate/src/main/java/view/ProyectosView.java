package view;

import java.util.List;
import java.util.Optional;

import io.IO;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class ProyectosView {

	final List<String> opciones = List.of("buscar por Código", "buscar por Nombre", "Mostrar", "Añadir", "modiFicar",
			"Eliminar", "Salir");

	public Character getOption() {
		IO.println("Proyectos: " + opciones);
		return Character.toUpperCase(IO.readChar());
	}

	public Proyecto create() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		Proyecto p = Proyecto.builder().nombre(nombre).build();
		return p;
	}

	public String buscarPorInicioDelNombre() {
		IO.print("El nombre empieza por ? ");
		return IO.readString();
	}

	public int buscarPorId() {
		IO.print("Código ? ");
		return IO.readInt();
	}

	public void mostrar(List<Proyecto> list) {
		list.forEach(System.out::println);
	}

	public void mostrarPorId(Optional<Proyecto> list) {
		list.stream().forEach(System.out::println);
	}

	public void result(String msg) {
		IO.println(msg);
	}

	/**
	 * (Jhovanny) - Modificar
	 * @param p
	 * @return
	 */
	public Proyecto modificar(Proyecto p) {
		if (p == null) {
			IO.println("No se ha encontrado el proyecto");
			return null;
		}
		IO.printf("Nombre [%s] ? ", p.getNombre());
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			p.setNombre(nombre);
		}
		
		IO.printf("Empleado [%d] ? ", p.getNombre());
		int id_empleado = IO.readInt();
		if (!nombre.isBlank()) {
			p.setNombre(nombre);
		}
		return p;
	}
}
