package main;

import java.util.logging.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import classes.Departamento;
import classes.Empleado;

public class Principal {
	
	static EntityManager em = null;

	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

		em = Persistence.createEntityManagerFactory("departamentos-unit").createEntityManager();
				
		showResult();
		em.getTransaction().begin();
		
		addDepartamento();
		addEmpleadoToDepartamento();
		updateDepartamento();
		deleteEmpleado();
		deleteDepartamento();

		em.getTransaction().commit();		
		showResult();
	}
	
	@SuppressWarnings("unchecked")
	private static void showResult() {
		em.clear();
		em.createQuery("FROM Departamento").getResultList().forEach(System.out::println);
		em.createQuery("FROM Empleado").getResultList().forEach(System.out::println);		
		System.out.println("-".repeat(80));
	}

	@SuppressWarnings("unused")
	private static void deleteDepartamento() {
		// Borrar (3, Producción)
		Departamento d = em.find(Departamento.class, 3);
		if (d.getEmpleados().size() > 0) {
			return;
		}
		
		em.remove(d);
	}

	@SuppressWarnings("unused")
	private static void deleteEmpleado() {
		// Borrar (2, Luis Maillo)
		Empleado e = em.find(Empleado.class, 2);
		Object[] deps = e.getDepartamentos().toArray();
		for (int i = 0; i < deps.length; i++) {
			e.removeDepartamento((Departamento) deps[i]);
		}
		em.remove(e);
	}

	@SuppressWarnings("unused")
	private static void updateDepartamento() {
		// Modificar (3, Producción a Desarrollo)
		Departamento d = em.find(Departamento.class, 3);
		d.setNombre("Desarrollo");		
	}

	@SuppressWarnings("unused")
	private static void addEmpleadoToDepartamento() {
		// Añadir (Empleado 1 Ana a Departamento 2 Linux)
		Empleado e = em.find(Empleado.class, 1);
		Departamento d1 = em.find(Departamento.class, 4);
		e.addDepartamento(d1);		
		Departamento d2 = em.find(Departamento.class, 2);
		e.addDepartamento(d2);		
	}

	@SuppressWarnings("unused")
	private static void addDepartamento() {
		// Añadir (Departamento Innovación)
		Departamento d = new Departamento();
		d.setNombre("Innovación");
		
		em.persist(d);
	}

}
