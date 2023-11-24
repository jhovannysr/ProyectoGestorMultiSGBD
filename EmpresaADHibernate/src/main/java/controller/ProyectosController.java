package controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.ProyectoDAO;
import io.IO;
import model.Proyecto;
import view.ProyectosView;

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
		Optional<Proyecto> entity = dao.findById(id);
		if (entity.isPresent()) {
			borrado = dao.delete(entity.get());
		}
		view.result(borrado ? "Borrado" : "No se ha podido borrar");
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

	private void update() {
		boolean actualizado = false;
		Integer idProyecto = view.buscarPorId();
		logger.info("Actualizando Proyecto con id: " + idProyecto);
		Proyecto entity = dao.findByIdProyecto(idProyecto);
		if (entity != null) {
			var proyecto = view.modificar(entity);
			
			IO.print("Añadir empleado S/N: ");
			if (IO.readStringSorN().equalsIgnoreCase("S")) {
				var empleado = view.addEmpleadoToProyecto(entity);
				actualizado = dao.update(proyecto, idProyecto, empleado, false);
			} else {
				actualizado = dao.update(proyecto, idProyecto, null, false);
			}
			
			IO.print("Eliminar empleado S/N: ");
			if (IO.readStringSorN().equalsIgnoreCase("S")) {
				var empleado = view.addEmpleadoToProyecto(entity);
				actualizado = dao.update(proyecto, idProyecto, empleado, true);
			} else {
				actualizado = dao.update(proyecto, idProyecto, null, false);
			}
		}
		view.result(actualizado ? "Modificado" : "No se ha podido modificar");
	}
}
