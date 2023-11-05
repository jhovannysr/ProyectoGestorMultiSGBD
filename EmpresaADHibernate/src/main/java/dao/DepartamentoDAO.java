package dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Departamento;

/**
 * 
 */
/**
 * 
 */
public class DepartamentoDAO {

	/**
	 * Conexion a la base de datos
	 */
	private HibernateManager hb = null;

	/**
	 * Logger
	 */
	private final Logger logger = Logger.getLogger(DepartamentoDAO.class.getName());
	
	/**
	 * Constructor
	 * 
	 * @param hb
	 */
	public DepartamentoDAO() {
		this.hb = HibernateManager.getInstance();
		hb.open();
	}

	/**
	 * (Jhovanny) - Añadir departamento en la base de datos
	 * 
	 * @param entity
	 * @return
	 */
	public boolean add(Departamento entity) {
		logger.info("add()");
		hb.getTransaction().begin();
		try {
			hb.getManager().merge(entity);
			hb.getTransaction().commit();
			return true;
        } catch (Exception e) {
        	IO.printlnError(e + "Error al añadir departamento. \n");
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
		return false;
	}

	/**
	 * (Jhovanny) - Muestra el contenido del EntityManager Departamento
	 * @param msg
	 */
	public List<Departamento> findAll() {
		logger.info("findAll()");
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
		List<Departamento> list = query.getResultList();
		return list;
	}
	
	/**
	 * (Jhovanny) - Consulta por id
	 * @param id
	 * @return
	 */
	public Optional<Departamento> findById(Integer id) {
        logger.info("findById()");
        Optional<Departamento> departamento = Optional.ofNullable(hb.getManager().find(Departamento.class, id));
        return departamento;
    }

	/**
	 * (Jhovanny) - Consulta por nombre
	 * @param nombre
	 * @return
	 */
	public List<Departamento> findByName(String nombre) {
		logger.info("findByName()");
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findByNombre", Departamento.class)
				.setParameter("nombre", nombre);
		List<Departamento> list = query.getResultList();
		return list;
	}
	
	/**
	 * Actualizar departamento
	 * 
	 * @param d
	 * @return
	 */
//	public boolean update(Departamento d, Integer id) {
//		d = hb.find(Departamento.class, id);
//		d.setNombre(d.getNombre());
//		return true;
//	}
	
	
	/**
	 * Borrar departamento
	 * @param id
	 * @return
	 */
//	public boolean delete(int id) {
//		var d = hb.find(Departamento.class, id);
//		if (d == null) {
//			return false;
//		}
//		hb.remove(d);									//<-----------
//	    return true;
//	}

	public void cerrarHibernate() {
		hb.close();
	}
	
}
