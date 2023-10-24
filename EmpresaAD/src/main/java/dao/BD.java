package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import io.IO;
import model.Department;
import model.Employee;

public class BD {

	/**
	 * Conector a la base de datos
	 */
	private static Connection conn = null;

	/**
	 * Tipo de base de datos [sqlite|mariadb|...]
	 */
	public static String typeDB = null;

	public static final String MRK_NULL = "NULL";
	
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
			initTables();
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

	/**
	 * Crea las tablas si no existen
	 */
	private static void initTables() {
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
						jefe INTEGER
						)
					""";
			sql2 = """
						CREATE TABLE IF NOT EXISTS empleado (
						id INT PRIMARY KEY AUTO_INCREMENT, 
						nombre VARCHAR(255) NOT NULL,
						salario DECIMAL (10,2) DEFAULT 0.0, 
						departamento INTEGER
						)
					""";
		}
		try {
			conn.createStatement().executeUpdate(sql1);
			conn.createStatement().executeUpdate(sql2);
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Actualiza la base de datos, si un jefe cambia de departamento, el jefe quedará a null
	 */
//	public static void updateDB() {
//		IO.println("Actualizar base de datos");
//		DepartmentDAO departmentDAO = new DepartmentDAO(); 
//		List<Department> listaDepartamentos = departmentDAO.show();
//		
//		EmployeeDAO employeeDAO = new EmployeeDAO();
//		List<Employee> listaEmpleados = employeeDAO.show();
//		
//		boolean hayJefe = false;
//		var d = new Department();
//		for (Department department : listaDepartamentos) { //Recorre departamentos
//			for (Employee employee : listaEmpleados) { //Recorre empleados
//				if (department.getJefe().equalsIgnoreCase(employee.getNombre())){ //Existe el nombre del jefe en empleados
//					if (!department.getNombre().equalsIgnoreCase(employee.getDepartamento())) { //Si es distinto el nombre de departamento es igual al nombre de departamento de empleado
//						d = department;
//					}
//				}
//			}
//		}
//		
//		if (!hayJefe) {
//			d.setJefe(MRK_NULL);
//			departmentDAO.modifyDepartment(d);
//		}
//		
//		String sql = """
//				UPDATE departamento
//				SET nombre = ?, jefe = ?
//				WHERE id = ?
//				""";
//		
//		PreparedStatement ps;
//		try {
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, d.getNombre());
//			ps.setString(2, d.getJefe());
//			ps.setInt(3, d.getId());
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	/**
	 * Borrar toda la base de datos
	 */
	public static void deleteDB() {
	String sql1 = null;
	String sql2 = null;
	
	sql1 = """
			DROP TABLE departamento
			""";
	sql2 = """
			DROP TABLE empleado
			""";
	
	try {
		conn.createStatement().executeUpdate(sql1);
		conn.createStatement().executeUpdate(sql2);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
}