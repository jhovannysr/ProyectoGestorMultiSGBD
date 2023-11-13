package view;

import java.util.List;
import java.util.Optional;

import dao.DepartamentoDAO;
import dao.EmpleadoDAO;
import io.IO;
import model.Departamento;
import model.Empleado;

public class EmpleadosView {
	
	final List<String> opciones = List.of( 
			"buscar por Código", 
			"buscar por Nombre", 
			"Mostrar", 
			"Añadir",
			"modiFicar",
			"Eliminar",
			"Salir");
	
	public Character getOption() {
		IO.println("Empleados: " + opciones);
		return Character.toUpperCase(IO.readChar());
	}

	public Empleado create() {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		Float salario = IO.readFloatOrNull();
		new DepartamentoDAO().findAll().forEach(System.out::println);
		IO.print("Departamento ? ");
		Integer idDepartamento = IO.readInt();
		Empleado e = Empleado.builder()
				.nombre(nombre)
				.salario(salario)
				.departamento(Departamento.builder()
						.id(idDepartamento)
						.build())
				.build();
		return e;
	}

	public Empleado update() {
	    Integer id = buscarPorId();
	    Optional<Empleado> entity = new EmpleadoDAO().findById(id);
	    if (entity.isPresent()) {
	        Empleado empleado = entity.get();
	        
	        IO.print("Nuevo nombre [" + empleado.getNombre() + "] ? ");
	        String nuevoNombre = IO.readString();
	        if (nuevoNombre.isEmpty()) {
	            nuevoNombre = empleado.getNombre();
	        }

	        IO.print("Nuevo salario [" + empleado.getSalario() + "] ? ");
	        Float nuevoSalario = IO.readFloatOrNull();
	        if (nuevoSalario == null) {
	            nuevoSalario = empleado.getSalario();
	        }

	        new DepartamentoDAO().findAll().forEach(System.out::println);
	        IO.print("Nuevo ID de Departamento [" + empleado.getDepartamento().getId() + "] ? ");
	        Integer nuevoIdDepartamento = IO.readInt();
	        
	        Empleado e = Empleado.builder()
	                .id(empleado.getId())
	                .nombre(nuevoNombre)
	                .salario(nuevoSalario)
	                .departamento(Departamento.builder().id(nuevoIdDepartamento).build())
	                .build();
	        return e;
	    } else {
	        IO.println("No se encontró un empleado con el código proporcionado.");
	        return null;
	    }
	}
	
	
	public String buscarPorInicioDelNombre() {
		IO.print("El nombre empieza por ? ");
		return IO.readString();
	}

	public int buscarPorId() {		
		IO.print("Código ? ");
		return IO.readInt();
	}

	public void mostrar(List<Empleado> list) {
		list.forEach(System.out::println);
	}
	
	public void mostrarPorId(Optional<Empleado> list) {
		list.stream().forEach(System.out::println);
	}
//	
//	public void mostrar(Empleado e) {
//		if (e == null) {
//			return;
//		}
//		IO.println(e.show());
//	}
//	
	public void result(String msg) {
		IO.println(msg);
	}

}

