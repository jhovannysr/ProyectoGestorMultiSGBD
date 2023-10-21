package prueba;

import java.util.Scanner;

import io.IO;

public class Main {

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
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
