package controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.EmpleadoDAO;
import dao.ProyectoDAO;
import model.Empleado;
import model.Proyecto;
import view.EmpleadosView;
import view.ProyectosView;
import java.util.logging.Logger;

public class ProyectosController {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final ProyectoDAO dao;
	private final ProyectosView view;

	public ProyectosController() {
		dao = new ProyectoDAO();
		view = new ProyectosView();
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
//				update();
				break;
			case 'B':
//				delete();
				break;
			case 'S':
				dao.cerrarHibernate();
				return;
			default:
			}
		}
	}
	
	private void getByStartsName() {
		String inicio = view.buscarPorInicioDelNombre();
		logger.info("Obteniendo Proyectos que empiezan por " + inicio);
		List<Proyecto> list = dao.findByName(inicio + "%");
		view.mostrar(list);		
	}

	private void getById() {
		Integer id = view.buscarPorId();
		logger.info("Obteniendo Proyecto con id: " + id);
		Optional<Proyecto> entity = dao.findById(id);
		if (entity != null) {
			view.mostrarPorId(entity);
		}
	}
	
	private void getAll() {
		logger.info("Obteniendo Proyectos");
		List<Proyecto> list = dao.findAll();
		view.mostrar(list);
	}

	private void create() {
		logger.info("Creando Proyecto");
		Proyecto entity = view.create();
		boolean anadido = dao.add(entity);
		view.result(anadido ? "Añadido" : "No se ha podido añadir");
	}
	
//	private void update() {
//	boolean actualizado = false;
//	Integer id = view.buscarPorCodigo();
//	logger.info("Actualizando Departamento con id: " + id);
//	Departamento entity = dao.findById(id);
//	Departamento d = null;
//	if (entity != null) {
//		d = view.modificar(entity);
//		actualizado = dao.update(d, id);
//	}
//	view.result(actualizado ? "Modificado" : "No se ha podido modificar");
//}
//
//private void delete() {
//	boolean borrado = false;
//	Integer id = view.buscarPorCodigo();
//	logger.info("Eliminando Departamento con id: " + id);
//	Departamento entity = dao.findById(id);
//	if (entity != null) {
//		borrado = dao.delete(id);
//	}
//	view.result(borrado ? "Borrado" : "No se ha podido borrar");
//}
}
