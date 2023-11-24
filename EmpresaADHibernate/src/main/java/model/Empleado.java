package model;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "hib_empleado")
@NamedQueries({
	@NamedQuery(name = "Empleado.findAll", 
			query = "SELECT e FROM Empleado e"),
	@NamedQuery(name="Empleado.noDepartamento", 
			query="SELECT e FROM Empleado e WHERE e.departamento IS NULL"),
	@NamedQuery(name="Empleado.findByNombre", 
			query="SELECT e FROM Empleado e WHERE e.nombre LIKE :nombre"),
	@NamedQuery(name="Empleado.findById", 
			query="SELECT e FROM Empleado e WHERE e.id = :id"),
	@NamedQuery(name="Empleado.findByEmpleadoDepartamento", 
			query="SELECT e FROM Empleado e WHERE e.departamento = :departamento")
})
public class Empleado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	private Float salario;
	
	@ManyToOne() //Relaciona muchos empleados con un departamento
	@JoinColumn(name="departamento") //@JoinColumn: especificar propiedades, name: nombre con el que se deberá crear la tabla
	private Departamento departamento; //Con la anotación @ManyToOne y la clase genera de forma automática la Clave Foránea departamentos_id
	
	@ManyToMany
	@JoinTable(
		    name = "PROYECTO_EMPLEADO",
		    joinColumns = {@JoinColumn(name = "EMPLEADO_NOMBRE")},
		    inverseJoinColumns = {@JoinColumn(name = "PROYECTO_CODIGO")}
		)
	private final Set<Proyecto> proyectos = new HashSet<>();
	
	public void añadeProyecto (Proyecto proyecto) {
		this.getProyectos().add(proyecto); proyecto.getEmpleados().add(this);
	}
	
	public void quitaProyecto (Proyecto proyecto) {
		this.getProyectos().remove(proyecto);
		proyecto.getEmpleados().remove(this);
	}
	
	@Override
	public String toString() {
		String dep = departamento == null ? "¿?" : departamento.getNombre();
		return String.format("Empleado     [%-2d %-2s %.2f €/hora %-10s %s]", id, nombre, salario, "", dep);
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	public boolean equals(Empleado e) {
		return e != null && e.getId() != null && e.getId() == id;
	}
}
