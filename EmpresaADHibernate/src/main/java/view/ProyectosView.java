package view;

import java.util.List;
import java.util.Optional;

import dao.DepartamentoDAO;
import io.IO;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class ProyectosView {
	
	final List<String> opciones = List.of( 
			"buscar por Código", 
			"buscar por Nombre", 
			"Mostrar", 
			"Añadir",
			"modiFicar",
			"Eliminar",
			"Salir");
	
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

	public Proyecto update() {
	    IO.print("Código del Proyecto a actualizar ? ");
	    int id = IO.readInt();
	    IO.print("Nuevo nombre ? ");
	    String nuevoNombre = IO.readString();
	    Proyecto p = Proyecto.builder().id(id).nombre(nuevoNombre).build();
	    return p;
	}
}
