package dao;

import java.sql.Connection;

public class EmpleadoGestor {

	/**
	 * Conexion a la base de datos 
	 */
	private Connection conn = null;

	/**
	 * Constructor
	 * @param conn
	 */
	public EmpleadoGestor() {
		this.conn = BD.getConnection();
	}

}
