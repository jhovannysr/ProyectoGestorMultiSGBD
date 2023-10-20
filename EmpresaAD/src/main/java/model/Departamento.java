package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	
	private int id;
	private String nombre;
	private String jefe;
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Jefe: %s\n", id, nombre, jefe);
	}
}
