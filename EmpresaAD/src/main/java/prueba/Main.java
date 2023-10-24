package prueba;

import java.util.Scanner;

import io.IO;

public class Main {

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		builderContrsuctor();
	}
	
	public static void pedirInt() {
		IO.print("valor:");
		String valor = IO.readString();
		int v = 0;
		
		if (valor.equals("")) {
			System.out.println("valor - " + valor);
		} else {
			v = Integer.parseInt(valor);
			System.out.println("valor - " + v);
		}
		
		
	}
	
	public static void builderContrsuctor() {
		Persona persona1 = Persona.builder()
				.id(1)
				.nombre("pepe")
				.salario(20.5f)
				.payaso(Payaso.builder().id(3).nombre("payaso").build())
				.build();
		
		Persona persona2 = Persona.builder()
				.id(1)
				.nombre("pepe")
				.salario(20.5f)
				.payaso(new Payaso(5, "payaso"))
				.build();
		
		Persona persona3 = Persona.builder()
				.id(1)
				.nombre("pepe")
				.salario(20.5f).build();
		
		Persona persona4 = Persona.builder()
				.id(1).build();
		
		System.out.println(persona1);
		System.out.println(persona2);	
	}
	
	public static void prueba() {
		int valor;
		String leer;
		
		leer = IO.readString();
		try {
			valor = Integer.parseInt(leer.trim());
		}catch (Exception e) {
			valor = 0;
		}
		
		System.out.println("valor: " + valor);
	}

}
