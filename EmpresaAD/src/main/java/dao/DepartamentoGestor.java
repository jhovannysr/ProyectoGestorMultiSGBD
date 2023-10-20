package dao;

import java.sql.Connection;

public class DepartamentoGestor {

	/**
	 * Conexion a la base de datos 
	 */
	private Connection conn = null;

	/**
	 * Constructor
	 * @param conn
	 */
	public DepartamentoGestor() {
		this.conn = BD.getConnection();
	}
	
}
