package prueba;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Persona {

	private int id;
	private String nombre;
	private float salario;
	
	private Payaso payaso;
	
}
