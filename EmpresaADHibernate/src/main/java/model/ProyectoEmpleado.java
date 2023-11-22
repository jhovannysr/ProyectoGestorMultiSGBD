package model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table(name = "PROYECTO_EMPLEADO")
public class ProyectoEmpleado {
	@Embeddable
	public static class Id implements Serializable {

		@EmbeddedId
		private Id id = new Id();
		
		@ManyToOne
		@JoinColumn(name = "PROYECTO_ID", insertable = false, updatable = false)
		private Proyecto proyecto;
		
		@ManyToOne
		@JoinColumn(name = "EMPLEADO_ID", insertable = false, updatable = false)
		private Empleado empleado;

		@Column(name = "PROYECTO_ID")
		private int proyectoId;

		@Column(name = "EMPLEADO_ID")
		private int empleadoId;

		public Id() {
		}

		@Override
		public int hashCode() {
			return Objects.hash(empleadoId, proyectoId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Id other = (Id) obj;
			return empleadoId == other.empleadoId && proyectoId == other.proyectoId;
		}
	}
}
