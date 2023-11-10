package dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import io.IO;
import jakarta.persistence.TypedQuery;
import lombok.Builder;
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
//	public boolean update(Departamento d, Integer id) {
//		d = hb.find(Departamento.class, id);
//		d.setNombre(d.getNombre());
//		return true;
//	}
	
	
	/**
	 * Borrar departamento
	 * @param entity
	 * @return
	 */
	public boolean delete(Departamento entity) {
        logger.info("delete()");
        
        //Almacenar los empleados del departamento a eliminar
        TypedQuery<Empleado> query =hb.getManager().createNamedQuery("Empleado.findByEmpleadoDepartamento", Empleado.class)
				.setParameter("departamento", entity);
        List<Empleado> list = query.getResultList();
        for (Empleado empleado : list) {
        	entity.removeEmpleado(empleado);
		}
        try {
        	// Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
        	hb.getTransaction().begin();
            entity = hb.getManager().find(Departamento.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            return true;
        } catch (Exception e) {
        	
        }finally {
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
