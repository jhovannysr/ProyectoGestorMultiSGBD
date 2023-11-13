package classes;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nif;
	
	private String nombre;

	@ManyToMany
	@JoinTable(name = "departamento_empleado",
			joinColumns = @JoinColumn(name = "empleado"),
			inverseJoinColumns = @JoinColumn(name = "departamento")
	)
	private Set<Departamento> departamentos = new HashSet<>();
	
	public Empleado() {}
	
	public Empleado(int id, String nif, String nombre) {
		this.id = id;
		this.nif = nif;
		this.nombre = nombre;
	}

	public Empleado(String nif, String nombre) {
		this(0, nif, nombre);
	}
	
	public Empleado(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Set<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Set<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
	public void addDepartamento(Departamento departamento) {
		this.departamentos.add(departamento);
	}
	
	public void removeDepartamento(Departamento departamento) {
		this.departamentos.remove(departamento);
	}
	
	@Override
	public String toString() {
		String deps = this.departamentos.stream()
				.map(d -> d.getNombre())
				.sorted((a, b) -> a.compareTo(b))
				.toList()
				.toString();
		return String.format("Emp#%d\t[%s]\t%-50s € a los departamentos %s", id, nif, nombre, deps);
	}
	
}
