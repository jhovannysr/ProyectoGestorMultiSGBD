package classes;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "departamento")
public class Departamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@ManyToMany(mappedBy = "departamentos")
	private Set<Empleado> empleados = new HashSet<>();
	
	public Departamento() {}
	
	public Departamento(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Departamento(String nombre) {
		this(0, nombre);
	}
	
	public Departamento(int id) {
		this(id, "");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	@Override
	public String toString() {
		String emps = empleados.stream()
				.map(e -> e.getNombre())
				.sorted()
				.toList()
				.toString();
		return String.format("Dep#%d\t%-50s\tEmpleados %s", id, nombre, emps);
	}

}
