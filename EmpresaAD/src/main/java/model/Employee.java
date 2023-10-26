package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
	
	private int id;
	private String nombre;
	private float salario;
	private Department departamento;
	
	public String toString() {
		return String.format("Id: %s | Nombre: %s | Salario: %s | Departamento: %s", id, nombre, salario, departamento.getId());
	}
}
