package model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
