package controller;

import java.util.logging.Logger;

import dao.HibernateManager;
import view.View;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class.getName());

	public Controller() {
		
		while (true) {
			Character opt = View.getOption();
			logger.info("Menu");
			switch (opt) {
				case 'E':
					new EmpleadosController().menu();
					break;
				case 'D':
					new DepartamentosController().menu();
					break;
				case 'P':
					new ProyectosController().menu();
					break;
				case 'S':
					var hb = HibernateManager.getInstance();
					hb.close();
					return;
				default:
			}
		}
	}
	
}
