package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	
	private int id;
	private String nombre;
	private int salario;
	private int departamento;
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Salario: %s | Departamento: %s", id, nombre, salario, departamento);
	}
}
