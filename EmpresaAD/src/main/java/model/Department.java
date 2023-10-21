package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	
	private int id;
	private String nombre;
	private String jefe;
	
	public Department(String nombre, String jefe) {
		this.nombre = nombre;
		this.jefe = jefe;
	}
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Jefe: %s", id, nombre, jefe);
	}

}
