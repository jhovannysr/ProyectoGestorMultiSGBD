package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.IO;
import model.Department;
import model.Department.DepartmentBuilder;
import model.Employee;

public class DepartmentDAO {

	/**
	 * Conexion a la base de datos
	 */
	private Connection conn = null;

	/**
	 * Constructor
	 * 
	 * @param conn
	 */
	public DepartmentDAO() {
		this.conn = BD.getConnection();
	}

	/**
	 * (Jhovanny) - Añadir departamento en la base de datos
	 * 
	 * @param department
	 * @return
	 */
	public boolean add(Department department, Boolean sinJefeNULL) {
		String sql = """
				INSERT INTO departamento (nombre, jefe)
				VALUES (?, ?)
				""";
		String sqlSinJefe = """
				INSERT INTO departamento (nombre)
				VALUES (?)
				""";

		try {
			PreparedStatement ps;
			if (!sinJefeNULL) { //Si hay jefe pide todo sus atributos
				ps = conn.prepareStatement(sql);
				ps.setString(1, department.getNombre());
				ps.setInt(2, department.getEmpleadoJefe().getId());
			} else { //Si no hay jefe solo pide nombre
				ps = conn.prepareStatement(sqlSinJefe);
				ps.setString(1, department.getNombre());
			}
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * (Jhovanny) - Devuele un List de todos los departamentos
	 * 
	 * @return
	 */
	public List<Department> show() {
		List<Department> listaDepartamentos = new ArrayList<>();
		String sql = """
				SELECT id, nombre, jefe
				FROM departamento
				""";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Department d = read(rs);
				listaDepartamentos.add(d);
			}
			return listaDepartamentos;
		} catch (SQLException e) {
		}
		return listaDepartamentos;
	}

	/**
	 * (Jhovanny) - Lee registro de cada departamento
	 * 
	 * @param rs
	 * @return
	 */
	private Department read(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			int jefe = rs.getInt("jefe");
			return Department.builder()
					.id(id)
					.nombre(nombre)
					.empleadoJefe(Employee.builder().id(jefe).build())
					.build();
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Mostar el id y nombre de los departamentos
	 */
	public void listDepartments() {
		String sql = """
				SELECT id, nombre
				FROM empleado
				""";
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				IO.println("\t[Id: " + id + " - Empleado: " + nombre + "]");
			}
		} catch (SQLException e) {
		}
	}
	
	/**
	 * (Naim) - Consulta por id
	 * 
	 * @param id
	 * @return
	 */
	public Department queryByID(int id) {
		String sql = """
				SELECT id, nombre, jefe
				FROM departamento
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
	 * (Jhovanny) - Actualizar departamento
	 * 
	 * @param d
	 * @return
	 */
	public boolean modifyDepartment(Department d) {
		String sql = """
				UPDATE departamento
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getNombre());
			ps.setInt(2, d.getEmpleadoJefe().getId());
			ps.setInt(3, d.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException ex) {
		}
		return false;
	}
	
	/**
	 * (Naim) - Consulta por nombre
	 * @param nombre
	 * @return
	 */
	public Department queryByName(String nombre) {
		String sql = """
				SELECT id, nombre, jefe
				FROM departamento
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
	 * (Naim) Borrar departamento
	 * @param id
	 * @return
	 */
	public boolean delete(int id) {
	    String sqlDelete = """
	            DELETE FROM departamento
	            WHERE id = ?
	            """;
	    
	    String sqlUpdateEmployee = """
	            UPDATE empleado
	            SET departamento = NULL
	            WHERE departamento = ?
	            """;
	    var d = queryByID(id);
	    
	    try {
	        PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
	        psDelete.setInt(1, id);
	        int filasEliminadas = psDelete.executeUpdate();
	        
	        if (filasEliminadas > 0) {
	            PreparedStatement psUpdateEmployee = conn.prepareStatement(sqlUpdateEmployee);
//	            psUpdateEmployee.setInt(1, 0);
	            psUpdateEmployee.setInt(1, d.getId());
	            psUpdateEmployee.executeUpdate();
	        }
	        
	        return filasEliminadas > 0;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

}
