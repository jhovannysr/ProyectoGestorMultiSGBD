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
	 * (JeanPaul) - Metodo para borrar
	 * 
	 * @param proyecto
	 * @return
	 */

	public boolean delete(Proyecto entity) {
	    logger.info("delete()");
	    try {
	        hb.getTransaction().begin();
	        
	        // Realiza las operaciones necesarias para eliminar un proyecto.
	        // Por ejemplo, puedes quitar el proyecto de cualquier empleado que esté asignado a él
	        // y luego eliminar el proyecto.
	        
	        hb.getManager().remove(entity);
	        hb.getTransaction().commit();
	        return true;
	    } catch (Exception e) {
	        IO.printlnError(e + "Error al eliminar proyecto.\n");
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	    } 
	    return false;
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
			//whb.close();
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

	public boolean update(Integer id, String nuevoNombre) {
        logger.info("update()");
        hb.getTransaction().begin();
        try {
            Proyecto proyecto = hb.getManager().find(Proyecto.class, id);
            if (proyecto != null) {
                proyecto.setNombre(nuevoNombre);
                hb.getTransaction().commit();
                return true;
            } else {
                IO.printlnError("No se encontró el proyecto con ID: " + id);
            }
        } catch (Exception e) {
            IO.printlnError(e + "Error al actualizar el nombre del proyecto. \n");
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
        return false;
    }
	
	
	
	public void cerrarHibernate() {
		hb.close();
	}
	
	
}
