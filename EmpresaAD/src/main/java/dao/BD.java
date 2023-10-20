package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BD {

	/**
	 * Conector a la base de datos
	 */
	private static Connection conn = null;

	/**
	 * Tipo de base de datos [sqlite|mariadb|...]
	 */
	public static String typeDB = null;

	/**
	 * Constructor
	 * 
	 * Establece una conexión con la base de datos
	 */
	private BD() {
		try {
			Properties prop = new Properties();
			prop.load(new FileReader("properties.database.prop"));

			typeDB = prop.getProperty("db");
			String driver = prop.getProperty("driver");
			String dsn = prop.getProperty("dsn");
			String user = prop.getProperty("user", "");
			String pass = prop.getProperty("pass", "");

			Class.forName(driver);
			conn = DriverManager.getConnection(dsn, user, pass);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve una conexión a la base de datos
	 * 
	 * @return Conexión a la base de datos
	 */
	public static Connection getConnection() {
		if (conn == null) {
			new BD();
			init();
		}
		return conn;
	}

	/**
	 * Cierra la conexión
	 */
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static void init() {
		String sql1 = null;
		String sql2 = null;
		if (BD.typeDB.equals("sqlite")) {
			sql1 = """
						CREATE TABLE IF NOT EXISTS agenda (
							uuid STRING PRIMARY KEY,
							nombre STRING NOT NULL,
							telefono STRING,
							edad INTEGER
						)
					""";
		}
		if (BD.typeDB.equals("mariadb")) {
			sql1 = """
						CREATE TABLE IF NOT EXISTS departamento (
						id INT PRIMARY KEY AUTO_INCREMENT, 
						nombre VARCHAR(255) NOT NULL,
						jefe VARCHAR(255) NOT NULL
						)
					""";
			sql2 = """
						CREATE TABLE IF NOT EXISTS empleado (
						id INT PRIMARY KEY AUTO_INCREMENT, 
						nombre VARCHAR(255) NOT NULL,
						salario INT, 
						departamento INT
						)
					""";
		}
		try {
			conn.createStatement().executeUpdate(sql1);
			conn.createStatement().executeUpdate(sql2);
		} catch (SQLException e) {
		}
	}
}