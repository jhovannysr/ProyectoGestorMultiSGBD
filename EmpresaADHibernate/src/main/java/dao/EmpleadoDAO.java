package dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import io.IO;
import jakarta.persistence.TypedQuery;
import model.Empleado;

public class EmpleadoDAO {
	
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
	public EmpleadoDAO() {
		this.hb = HibernateManager.getInstance();
		hb.open();
	}

	/**
	 * (Jhovanny) - Añadir empleado en la base de datos
	 * 
	 * @param empleado
	 * @return
	 */
	public boolean add(Empleado entity) {
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
	public List<Empleado> findAll() {
		logger.info("findAll()");
		List<Empleado> list = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class).getResultList();
		return list;
	}
	
	/**
	 * (Jhovanny) - Consulta por id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Empleado> findById(Integer id) {
	    logger.info("findById()");
	    Optional<Empleado> empleado = Optional.ofNullable(hb.getManager().find(Empleado.class, id));
	    return empleado;
	}
	
	/**
	 * (Jhovanny) - Consulta por nombre
	 * @param nombre
	 * @return
	 */
	public List<Empleado> findByName(String nombre) {
		logger.info("findByName()");
		TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findByNombre", Empleado.class)
				.setParameter("nombre", nombre);
		List<Empleado> list = query.getResultList();
		return list;
	}

    /**
     * (Naim) - Actualizar empleado
     * 
     * @param id
     * @param nuevoNombre
     * @param nuevoSalario
     * @param nuevoIdDepartamento
     * @return
     */
	
	public boolean update(Empleado entity) {
	    logger.info("update()");
	    hb.getTransaction().begin();
	    try {
	        Empleado empleado = hb.getManager().find(Empleado.class, entity.getId());
	        if (empleado != null) {
	            empleado.setNombre(entity.getNombre());
	            empleado.setSalario(entity.getSalario());
	            empleado.setDepartamento(entity.getDepartamento());
	            hb.getTransaction().commit();
	            return true;
	        } else {
	            IO.printlnError("El empleado no existe.");
	        }
	    } catch (Exception e) {
	        IO.printlnError(e + "Error al actualizar el empleado. \n");
	    } finally {
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	    }
	    return false;
	}


	
	/**
	 * (JeanPaul) - Metodo para borrar
	 * 
	 * @param proyecto
	 * @return
	 */
	public boolean delete(Empleado entity) {
	    logger.info("delete()");
	    try {
	        hb.getTransaction().begin();       
	        hb.getManager().remove(entity);
	        hb.getTransaction().commit();
	        return true;
	    } catch (Exception e) {
	        IO.printlnError(e + "Error al eliminar empleado.\n");
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	    } finally {
	        hb.close();
	    }
	    return false;
	}

	public void cerrarHibernate() {
		hb.close();
	}
}

