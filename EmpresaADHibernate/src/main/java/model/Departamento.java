package model;

import java.util.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hib_departamento")
@NamedQueries({
	@NamedQuery(name = "Departamento.findAll", 
			query = "SELECT d FROM Departamento d"),
	@NamedQuery(name="Departamento.findByNombre", 
			query="SELECT d FROM Departamento d WHERE d.nombre LIKE :nombre"),
	@NamedQuery(name="Departamento.findByEmpleado", 
			query="SELECT DISTINCT d FROM Empleado e JOIN e.departamento d WHERE e.id = :id"),
	@NamedQuery(name="Departamento.findById", 
			query="SELECT d FROM Departamento d WHERE d.id = :id")
})
public class Departamento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	private int jefe;
	
//	@OneToMany(mappedBy = "departamento", orphanRemoval = true)
	@OneToMany(mappedBy = "departamento")  //mappedBy, define el objeto al que pertenece la relación //nos permite acceder a la información de empleados desde Departamentos
    private final Set<Empleado> empleados = new HashSet<>();
	
	/**
	 * Modificar el departamento del empleado
	 * @param e
	 */
	public void addEmpleado(Empleado e) {
		if (e.getDepartamento() != null) {
			e.getDepartamento().getEmpleados().remove(e);
		}
		e.setDepartamento(this);
		empleados.add(e);
	}
	
	/**
	 * Eliminar Empleado
	 * @param e
	 */
	public void removeEmpleado(Empleado e) {
		e.setDepartamento(null);
		empleados.remove(e);
	}
	
	/**
	 * Devuelve representación de un empleado
	 * 
	 * @return string
	 */
	public String show() {
		if (id == 0) {
			return "no empleado!!!";
		}
		return "";
	}
	
	@Override
	public String toString() {
		List<String> emps = empleados.stream()
				.map(e -> e.getNombre())
				.sorted()
				.toList();
		return String.format("Departamento [%-2d %-25s %s]", id, nombre, jefe, emps);
	}
}
