package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.IO;
import model.Department;
import model.Employee;

public class EmployeeDAO {

	/**
	 * Conexion a la base de datos
	 */
	private Connection conn = null;

	/**
	 * Constructor
	 * 
	 * @param conn
	 */
	public EmployeeDAO() {
		this.conn = BD.getConnection();
	}

	/**
	 * (Jhovanny) - Añadir empleado en la base de datos
	 * 
	 * @param employee
	 * @return
	 */
	public boolean add(Employee employee, Boolean sinDepartamentoNULL) {
		String sql = """
				INSERT INTO empleado (nombre, salario, departamento)
				VALUES (?, ?, ?)
				""";
		String sqlSinDepartamento = """
				INSERT INTO empleado (nombre, salario)
				VALUES (?, ?)
				""";

		try {
			PreparedStatement ps;
			if (!sinDepartamentoNULL) { //Si hay departamento pide todo sus atributos
				ps = conn.prepareStatement(sql);
				ps.setString(1, employee.getNombre());
				ps.setFloat(2, employee.getSalario());
				ps.setInt(3, employee.getDepartamento().getId());
				return ps.executeUpdate() > 0;
			} else { //Si no hay departamento solo pide nombre y salario
				ps = conn.prepareStatement(sqlSinDepartamento);
				ps.setString(1, employee.getNombre());
				ps.setFloat(2, employee.getSalario());
				return ps.executeUpdate() > 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * (Jhovanny) - Lee registro de cada empleado
	 * 
	 * @param rs
	 * @return
	 */
	private Employee read(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			float salario = rs.getInt("salario");
			int departamento = rs.getInt("departamento");
			return Employee.builder()
					.id(id)
					.nombre(nombre)
					.salario(salario)
					.departamento(Department.builder().id(departamento).build())
					.build();
		} catch (SQLException e) {
		}
		return null;
	}
	
	/**
	 * (Jhovanny) - Devuelve un Sring de todos los empleados
	 * 
	 * @return
	 */
	public List<Employee> show() {
		List<Employee> listaEmpleados = new ArrayList<>();
		String sql = """
				SELECT id, nombre, salario, departamento
				FROM empleado
				""";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Employee e = read(rs);
				listaEmpleados.add(e);
			}
			return listaEmpleados;
		} catch (SQLException e) {
		}
		return listaEmpleados;
	}
	
	/**
	 * Mostar el id y nombre de los departamentos
	 */
	public void listDepartments() {
		String sql = """
				SELECT id, nombre
				FROM departamento
				""";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				IO.println("\t[Id: " + id + " - Departamento: " + nombre + "]");
			}
		} catch (SQLException e) {
		}
	}
	
	/**
	 * (Jhovanny) - Consulta por id
	 * @param id
	 * @return
	 */
	public Employee queryByID(int id) {
		String sql = """
				SELECT id, nombre, salario, departamento
				FROM empleado
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return read(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * (JeanPaul) - Consulta empleado por nombre
	 * 
	 * @param nombre
	 * @return
	 */
	public Employee queryByName(String nombre) {
	    String sql = """
	            SELECT id, nombre, salario, departamento
	            FROM empleado
	            WHERE nombre = ?
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, nombre);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return read(rs);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * (JeanPaul) - Delete empleado
	 * 
	 * @param nombre
	 * @return
	 */
	public boolean deleteEmployee(int id) {
	    String sqlDelete = """
	            DELETE FROM empleado
	            WHERE id = ?
	            """;

	    String sqlUpdateDepartment = """
	            UPDATE departamento
	            SET jefe = NULL
	            WHERE jefe = ?
	            """;
	    var d = queryByID(id);

	    try {
	        PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
	        psDelete.setInt(1, id);
	        int filasEliminadas = psDelete.executeUpdate();

	        if (filasEliminadas > 0) {
	            PreparedStatement psUpdateDepartment = conn.prepareStatement(sqlUpdateDepartment);
	            psUpdateDepartment.setInt(1, d.getId());
	            psUpdateDepartment.executeUpdate();
	        }

	        return filasEliminadas > 0;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * (Jhovanny) - Actualizar Empleado
	 * @param e
	 * @param nuevoDepartamento
	 * @return
	 */
	public boolean modifyEmployee(Employee e) {
		String sql = """
				UPDATE empleado
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getNombre());
			ps.setFloat(2, e.getSalario());
			ps.setInt(3, e.getDepartamento().getId());
			ps.setInt(4, e.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException ex) {
		}
		return false;
	}
}
