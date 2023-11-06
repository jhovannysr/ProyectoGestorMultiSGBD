package dao;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Departamento;
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
		//hb.open();
		hb.getTransaction().begin();
		try {
			hb.getManager().merge(entity);
			hb.getTransaction().commit();
			//hb.close();
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
		//hb.open();
		List<Empleado> list = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class).getResultList();
		//hb.close();
		return list;
		
	}
	
	/**
	 * Muestra el contenido del EntityManager Empleado
	 * @param msg
	 */
//	@SuppressWarnings("unchecked")
//	public void show(String msg) {
//		hb.getTransaction().begin(); //Inicia la transacción de recursos
//		hb.getTransaction().commit(); //Escribe en la base de datos los cambios que no se hayan volcado.
//		hb.clear();
//		IO.println("* " + msg);
//		hb.createQuery("FROM Empleado").getResultList().forEach(System.out::println);
//		IO.println("-".repeat(80));
//	}
	
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
	
//	public boolean delete(Empleado entity) {
//        logger.info("delete()");
//        HibernateManager hb = HibernateManager.getInstance();
//        //hb.open();
//        try {
//            hb.getTransaction().begin();
//            entity = hb.getManager().find(Empleado.class, entity.getId());
//            hb.getManager().remove(entity);
//            hb.getTransaction().commit();
//           //hb.close();
//            return true;
//        } catch (Exception e) {
//        	IO.printlnError(e + "Error al borrar el empleado. \n");
//        } finally {
//            if (hb.getTransaction().isActive()) {
//                hb.getTransaction().rollback();
//            }
//        }
//        return false;
//    }
	
	/**
	 * Actualizar empleado
	 * 
	 * @param e
	 * @return
	 */
//	public boolean modifyEmployee(Empleado e, Integer idEmpleado) {
//		int idDepartamentoNew = e.getDepartamento().getId();
//		e = hb.find(Empleado.class, idEmpleado);
//		e.setNombre(e.getNombre());
//		e.setSalario(e.getSalario());
//		
//		//Añadir campo departamento del empleado
//		if (idDepartamentoNew != 0) {
//			var d = hb.find(Departamento.class, idDepartamentoNew);
//			d.addEmpleado(e);
//		} else { //Eliminar campo departamento del empleado
//			deleteDepartamentoDeEmpleado(idEmpleado);
//		}
//		return true;
//	}
	public void cerrarHibernate() {
		hb.close();
	}
}
