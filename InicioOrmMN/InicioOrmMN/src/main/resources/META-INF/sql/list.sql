SELECT *
    FROM departamento d
    LEFT JOIN departamento_empleado de ON de.departamento = d.id
    LEFT JOIN empleado e ON de.empleado = e.id;
