package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Getter;

/**
 * Controlador de Entidades de Hibernate JPA
 */
@Getter
public class HibernateManager {

	/**
	 * Conector a la base de datos
	 */
	private static Connection conn = null;
	
	private static HibernateManager controller;

    // Creamos las EntityManagerFactory para manejar las entidades y transacciones
	private EntityManagerFactory entityManagerFactory;
    private EntityManager manager;
    private EntityTransaction transaction;

    private HibernateManager() {
    }

    public static HibernateManager getInstance() {
        if (controller == null)
            controller = new HibernateManager();
        return controller;
    }

    public void open() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        manager = entityManagerFactory.createEntityManager();
        transaction = manager.getTransaction();
    }

    public void close() {
        manager.close();
        entityManagerFactory.close();
    }

	/**
	 * Borrar toda la base de datos
	 */
	public static void deleteDB() {
		String sql1 = null;
		String sql2 = null;
		String sql3 = null;

		sql1 = """
				DROP TABLE hib_departamento
				""";
		sql2 = """
				DROP TABLE hib_empleado
				""";
		sql3 = """
				DROP TABLE hib_proyecto
				""";

		try {
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3307/hib_empresabd"
					, "root", "root");
			conn.createStatement().executeUpdate(sql3);
			conn.createStatement().executeUpdate(sql2);
			conn.createStatement().executeUpdate(sql1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}