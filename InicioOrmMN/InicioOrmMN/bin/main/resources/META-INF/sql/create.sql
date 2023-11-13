CREATE TABLE empleado (id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, nif VARCHAR(9) UNIQUE, nombre VARCHAR(100) NOT NULL);
CREATE TABLE departamento (id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(50) NOT NULL UNIQUE);
CREATE TABLE departamento_empleado (departamento INT(11) NOT NULL, empleado INT(11) NOT NULL, PRIMARY KEY (departamento, empleado), FOREIGN KEY (departamento) REFERENCES departamento(id), FOREIGN KEY (empleado) REFERENCES empleado(id));
