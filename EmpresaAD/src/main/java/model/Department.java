package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	
	private int id;
	private String nombre;
	private Employee empleadoJefe; //Id del empleado
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Jefe: %s" , id, nombre, empleadoJefe.getId());
	}

}
