
public class Persona extends Thread{
	
	private String nombre;
	private Cuenta cuenta;
	
	public Persona(String nombre, Cuenta cuenta) {
		this.nombre = nombre;
		this.cuenta = cuenta;
	}
	
	@Override
	public void run() {
		
		System.out.println(getNombre() +" accede a su cuenta mediante un cajero. [Saldo = "+getCuenta().getSaldo()+"]\n");
		
			
		getCuenta().ingresarDinero(1000.00, nombre);
		getCuenta().retirarDinero(2000.00, nombre);
		
		System.out.println(getNombre()+ " ha terminado sus acciones en la cuenta. [Saldo = " +getCuenta().getSaldo()+"]\n");
	
		}
	
	
	public String getNombre() {
		return nombre;
	}
	
	public Cuenta getCuenta() {
		return cuenta;
	}
	
	

}