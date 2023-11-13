package model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hib_proyecto")
@NamedQueries({
	@NamedQuery(name = "Proyecto.findAll", 
			query = "SELECT p FROM Proyecto p"),
	@NamedQuery(name="Proyecto.findByNombre", 
	query="SELECT p FROM Proyecto p WHERE p.nombre LIKE :nombre"),
	@NamedQuery(name="Proyecto.findById", 
	query="SELECT p FROM Proyecto p WHERE p.id = :id")
})
public class Proyecto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	
	@ManyToMany(mappedBy="proyectos") 
	private final Set<Empleado> empleados = new HashSet<>();
	
	public void añadeEmpleado (Empleado empleado) {
		empleado.getProyectos().add(this);
		this.getEmpleados().add(empleado);
	}
	public void quitaEmpleado (Empleado empleado) {
		empleado.getProyectos().remove(this);
		this.getEmpleados().remove(empleado);
	}
}
