package dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import io.IO;
import jakarta.persistence.TypedQuery;
import model.Empleado;
import model.Proyecto;

public class ProyectoDAO {
	
	private HibernateManager hb = null;

	/**
	 * Logger
	 */
	private final Logger logger = Logger.getLogger(ProyectoDAO.class.getName());
	
	/**
	 * Constructor
	 * 
	 * @param hb
	 */
	public ProyectoDAO() {
		this.hb = HibernateManager.getInstance();
		hb.open();
	}

	/**
	 * (Naim) - Añadir proyecto en la base de datos
	 * 
	 * @param proyecto
	 * @return
	 */
	public boolean add(Proyecto entity) {
		logger.info("add()");
//		hb.open();
		hb.getTransaction().begin();
		try {
			hb.getManager().merge(entity);
			hb.getTransaction().commit();
			hb.close();
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
	 * (Naim) - Muestra el contenido del EntityManager Proyecto
	 * @param msg
	 */
	public List<Proyecto> findAll() {
		logger.info("findAll()");
//		hb.open();
		List<Proyecto> list = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class).getResultList();
		return list;
	}
	
	/**
	 * (Naim) - Consulta por id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Proyecto> findById(Integer id) {
	    logger.info("findById()");
	    Optional<Proyecto> proyecto = Optional.ofNullable(hb.getManager().find(Proyecto.class, id));
	    return proyecto;
	}
	
	/**
	 * (Naim) - Consulta por nombre
	 * @param nombre
	 * @return
	 */
	public List<Proyecto> findByName(String nombre) {
		logger.info("findByName()");
		TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findByNombre", Proyecto.class)
				.setParameter("nombre", nombre);
		List<Proyecto> list = query.getResultList();
		return list;
	}
	
	public void cerrarHibernate() {
		hb.close();
	}
	
	
}
