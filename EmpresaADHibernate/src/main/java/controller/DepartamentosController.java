package controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import dao.DepartamentoDAO;
import io.IO;
import model.Departamento;

import view.DepartamentosView;

public class DepartamentosController {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private final DepartamentoDAO dao;
	private final DepartamentosView view;

	public DepartamentosController() {
		dao = new DepartamentoDAO();
		view = new DepartamentosView();
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

	private void getByStartsName() {
		String inicio = view.buscarPorInicioDelNombre();
		logger.info("Obteniendo Departamentos que empiezan por " + inicio);
		List<Departamento> list = dao.findByName(inicio + "%");
		view.mostrar(list);		
	}

	private void getById() {
		Integer id = view.buscarPorId();
		logger.info("Obteniendo Departamento con id: " + id);
		Optional<Departamento> entity = dao.findById(id);
		if (entity != null) {
			view.mostrarPorId(entity);
		}
	}
	
	private void getAll() {
		logger.info("Obteniendo Departamentos");
		List<Departamento> list = dao.findAll();
		view.mostrar(list);
	}

	private void create() {
		logger.info("Creando Departamento");
		Departamento entity = view.create();
		boolean anadido  = dao.add(entity);
		view.result(anadido ? "Añadido" : "No se ha podido añadir");
	}

	private void update() {
		boolean actualizado = false;
		Integer id = view.buscarPorId();
		logger.info("Actualizando Departamento con id: " + id);
		Departamento entity = dao.findByIdDepartamento(id);
		Departamento d = null;
		IO.println("departamento id: " + entity.getId() + ", id: " + id);
		if (entity != null) {
			d = view.modificar(entity);
			actualizado = dao.update(d);
		}
		view.result(actualizado ? "Modificado" : "No se ha podido modificar");
	}

	private void delete() {
		boolean borrado = false;
		Integer id = view.buscarPorId();
		logger.info("Eliminando Departamento con id: " + id);
		Departamento entity = dao.findByIdDepartamento(id);
		if (entity != null) {
			borrado = dao.delete(entity);
		}
		view.result(borrado ? "Borrado" : "No se ha podido borrar");
	}
}
