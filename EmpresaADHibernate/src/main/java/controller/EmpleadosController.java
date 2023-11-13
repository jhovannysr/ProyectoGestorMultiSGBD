package controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.EmpleadoDAO;
import io.IO;
import model.Empleado;

import view.EmpleadosView;

public class EmpleadosController {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final EmpleadoDAO dao;
	private final EmpleadosView view;

	public EmpleadosController() {
		dao = new EmpleadoDAO();
		view = new EmpleadosView();
	}
	
	public void menu() {
		while (true) {
			Character opt = view.getOption();
			switch (opt) {
			case 'C':
				getById();
				break;
			case 'N':
				getByStartsName();
				break;
			case 'M':
				getAll();
				break;
			case 'A':
				create();
				break;
			case 'F':
				update();
				break;
			case 'E':
				delete();
				break;
			case 'S':
				dao.cerrarHibernate();
				return;
			default:
			}
		}
	}

	private void delete() {
	    boolean borrado = false;
	    Integer id = view.buscarPorId();
	    logger.info("Eliminando Empleado con id: " + id);
	    Optional<Empleado> entity = dao.findById(id);
	    if (entity.isPresent()) {
	        borrado = dao.delete(entity.get());
	    }
	    view.result(borrado ? "Borrado" : "No se ha podido borrar");
	}
	
	private void getByStartsName() {
		String inicio = view.buscarPorInicioDelNombre();
		logger.info("Obteniendo Empleados que empiezan por " + inicio);
		List<Empleado> list = dao.findByName(inicio + "%");
		view.mostrar(list);		
	}

	private void getById() {
		Integer id = view.buscarPorId();
		logger.info("Obteniendo Empleado con id: " + id);
		Optional<Empleado> entity = dao.findById(id);
		if (entity != null) {
			view.mostrarPorId(entity);
		}
	}
	
	private void getAll() {
		logger.info("Obteniendo Empleados");
		List<Empleado> list = dao.findAll();
		view.mostrar(list);
	}

	private void create() {
		logger.info("Creando Empleado");
		Empleado entity = view.create();
		boolean anadido = dao.add(entity);
		view.result(anadido ? "Añadido" : "No se ha podido añadir");
	}


	private void update() {
	    Empleado empleado = view.update();
	    if (empleado != null) {
	        boolean actualizado = dao.update(empleado);
	        view.result(actualizado ? "Empleado actualizado" : "No se pudo actualizar el empleado");
	    }
	}
}
//
//	private void delete() {
//		boolean borrado = false;
//		Integer id = view.buscarPorCodigo();
//		logger.info("Eliminando Empleado con id: " + id);
//		Empleado entity = dao.findById(id);
//		if (entity != null) {
//			borrado = dao.delete(id);
//		}
//		view.result(borrado ? "Borrado" : "No se ha podido borrar");
//	}

