package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public boolean add(Employee employee) {
		DepartmentDAO departmentDAO = new DepartmentDAO();
		String sql = """
				INSERT INTO empleado (nombre, salario, departamento)
				VALUES (?, ?, ?)
				""";

		try {
			//Si el departamento del empleado es = null
			if (employee.getDepartamento().equals(BD.MRK_NULL)) {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, employee.getNombre());
				ps.setInt(2, employee.getSalario());
				ps.setString(3, employee.getDepartamento());
				return ps.executeUpdate() > 0;
			} else {
				for (Department d : departmentDAO.show()) {
					//Si el departamento existe
					if (d.getNombre().equalsIgnoreCase(employee.getDepartamento())) {
						try {
							PreparedStatement ps = conn.prepareStatement(sql);
							ps.setString(1, employee.getNombre());
							ps.setInt(2, employee.getSalario());
							ps.setString(3, employee.getDepartamento());
							return ps.executeUpdate() > 0;
						} catch (SQLException ex) {
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("No existe el departamento introducido");
		return false;
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
	 * (Jhovanny) - Lee registro de cada empleado
	 * 
	 * @param rs
	 * @return
	 */
	private Employee read(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			int salario = rs.getInt("salario");
			String departamento = rs.getString("departamento");
			return new Employee(id, nombre, salario, departamento);
		} catch (SQLException e) {
		}
		return null;
	}
	
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
	
	public boolean updateEmployee(Employee e, boolean nuevoDepartamento) {
		String sql = """
				UPDATE empleado
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		
		//Verificar si el departamento introducido existe para la actualizacion
		DepartmentDAO departmentDAO = new DepartmentDAO();
		boolean departamentoExiste = false;
		//Si no es la creacion de un nuevo departamento
		if (!nuevoDepartamento) {
			for (Department d : departmentDAO.show()) {
				if (d.getNombre().equalsIgnoreCase(e.getDepartamento())) {
					departamentoExiste = true;
				}
			}
		}
		
		//Actualiza si el departamento introducido existe o el es la creacion de un nuevo departamento
		if (departamentoExiste || nuevoDepartamento) {
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, e.getNombre());
				ps.setInt(2, e.getSalario());
				ps.setString(3, e.getDepartamento());
				ps.setInt(4, e.getId());
				return ps.executeUpdate() > 0;
			} catch (SQLException ex) {
			}
		}
		return false;
	}
}
