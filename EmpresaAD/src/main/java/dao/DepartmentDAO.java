package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Department;
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
	 * @param d
	 * @return
	 * @throws SQLException
	 */
	public boolean add(Department d) {
		EmployeeDAO employeeDAO = new EmployeeDAO();
		String sql = """
				INSERT INTO departamento (nombre, jefe)
				VALUES (?, ?)
				""";

		try {
			if (d.getJefe().equals(BD.MRK_NULL)) {
				PreparedStatement ps;
				ps = conn.prepareStatement(sql);
				ps.setString(1, d.getNombre());
				ps.setString(2, d.getJefe());
				return ps.executeUpdate() > 0;
			} else {
				for (Employee e : employeeDAO.show()) {
					if (e.getNombre().equalsIgnoreCase(d.getJefe()) && //Si el empleado existe
							( e.getDepartamento().equals(d.getNombre())) || //Y si el departamento del empleado es el mismo
							e.getNombre().equalsIgnoreCase(d.getJefe()) && //Si el empleado existe
							( e.getDepartamento().equals(BD.MRK_NULL)) )//Y si el departamento del empleado es null
					{
						e.setDepartamento(d.getNombre());
						employeeDAO.updateEmployee(e, true);
						PreparedStatement ps;
						ps = conn.prepareStatement(sql);
						ps.setString(1, d.getNombre());
						ps.setString(2, d.getJefe());
						return ps.executeUpdate() > 0;
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	
	public boolean delete(int id) {
	    String sqlDelete = """
	            DELETE FROM departamento
	            WHERE id = ?
	            """;
	    
	    String sqlUpdateEmployee = """
	            UPDATE empleado
	            SET departamento = ?
	            WHERE departamento = ?
	            """;
	    var d = queryByID(id);
	    
	    try {
	        PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
	        psDelete.setInt(1, id);
	        int filasEliminadas = psDelete.executeUpdate();
	        
	        if (filasEliminadas > 0) {
	            PreparedStatement psUpdateEmployee = conn.prepareStatement(sqlUpdateEmployee);
	            psUpdateEmployee.setString(1,BD.MRK_NULL);
	            psUpdateEmployee.setString(2, d.getNombre());
	            psUpdateEmployee.executeUpdate();
	        }
	        
	        return filasEliminadas > 0;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
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
			String jefe = rs.getString("jefe");
			return new Department(id, nombre, jefe);
		} catch (SQLException e) {
		}
		return null;
	}
	
	/**
	 * (Jhovanny) - Consulta por id
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
	 * (Jhovanny) - Actualizar departamento
	 * @param d
	 * @return
	 */
	public boolean updateDepartment(Department d) {
		String sql = """
				UPDATE departamento
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		
		//Verificar si el jefe introducido existe para la actualizacion
		EmployeeDAO employeeDAO = new EmployeeDAO();
		boolean departamentoExiste = false;
		for (Employee e : employeeDAO.show()) {
			if (e.getNombre().equalsIgnoreCase(d.getJefe())) {
				if (e.getNombre().equalsIgnoreCase(d.getJefe()) && //Si el empleado existe
						( e.getDepartamento().equals(d.getNombre())) || //Y si el departamento del empleado es el mismo
						e.getNombre().equalsIgnoreCase(d.getJefe()) && //O Si el empleado existe
						( e.getDepartamento().equals(BD.MRK_NULL)) )//Y si el departamento del empleado es null
				{
					departamentoExiste = true;
					e.setDepartamento(d.getNombre());
					employeeDAO.updateEmployee(e, false);
				}
			}
		}
		
		if (departamentoExiste) {
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, d.getNombre());
				ps.setString(2, d.getJefe());
				ps.setInt(3, d.getId());
				return ps.executeUpdate() > 0;
			} catch (SQLException ex) {
			}
		}
		return false;
	}
}

