package dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import io.IO;
import jakarta.persistence.TypedQuery;
import model.Departamento;
import model.Empleado;

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
		hb.getManager().clear();
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
	
	public Departamento findById2(Integer id) {
        logger.info("findById()");
        Departamento departamento = hb.getManager().find(Departamento.class, id);
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
	public boolean update(Departamento d, Integer id) {
		logger.info("update()");
		hb.getTransaction().begin(); 
		d = hb.getManager().find(Departamento.class, id);
		d.setNombre(d.getNombre());
		d.setJefe(d.getJefe());
		hb.getTransaction().commit();
		return true;
	}
	
	
	/**
	 * Borrar departamento
	 * @param entity
	 * @return
	 */
	public boolean delete(Departamento entity) {
        logger.info("delete()");
        try {
            hb.getTransaction().begin();

            // Actualizar empleados para que el departamento sea nulo
            Set<Empleado> empleados = entity.getEmpleados();
            for (Empleado empleado : empleados) {
                empleado.setDepartamento(null);
            }

            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            return true;
        } catch (Exception e) {
            IO.printlnError(e + "Error al eliminar departamento.\n");
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        } finally {
//            hb.close();
        }
        return false;
    }

	public void cerrarHibernate() {
		hb.close();
	}
	
}
