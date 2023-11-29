
public class Cuenta {
	
	
	private double saldo;
	
	public Cuenta() {
		saldo = 0.0;
	}
	
	public double getSaldo() {
		 return saldo;
	}
	
	public synchronized void ingresarDinero(double cantidad, String s) {
		saldo += cantidad;
		System.out.println("["+s+"] Ingreso completado [+1000.00]. [saldo = "+getSaldo()+"]");
	}

	
	public synchronized void retirarDinero(double cantidad, String s) {
		if (saldo < cantidad) {
			System.out.println("[ERROR] ["+s+"] La cantidad (" +cantidad+ ") no puede ser retirada porque no hay saldo suficiente [SALDO: "+getSaldo()+"]");
		} else {
			saldo -= cantidad;
			System.out.println("El retiro fue exitoso. [" +s+ "] El saldo ahora es de: " +getSaldo());
		}
	}
	
}