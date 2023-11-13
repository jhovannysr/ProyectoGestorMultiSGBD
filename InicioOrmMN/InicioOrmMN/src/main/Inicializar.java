package main;

import java.util.logging.*;

import javax.persistence.Persistence;


public class Inicializar {

	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.ALL);
		
		// Crea el schema
		Persistence.generateSchema("departamentos-unit", null);
	}

}
