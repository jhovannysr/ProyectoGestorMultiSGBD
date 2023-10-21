package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	private int id;
	private String nombre;
	private int salario;
	private String departamento;
	
	public Employee(String nombre, int salario, String departamento) {
		super();
		this.nombre = nombre;
		this.salario = salario;
		this.departamento = departamento;
	}
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Salario: %s | Departamento: %s", id, nombre, salario, departamento);
	}

}
