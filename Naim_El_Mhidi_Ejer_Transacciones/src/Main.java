
public class Main {
	
	public static void main (String []args) throws InterruptedException {
		
		Cuenta c1 = new Cuenta();
		
		Persona p1 = new Persona("Naim", c1);
		Persona p2 = new Persona("Pepe", c1);
		Persona p3 = new Persona("Charly", c1);
		
		p1.start();
		p2.start();
		p3.start();
		
		try {
			p1.join();
			p2.join();
			p3.join();
			
		} catch (InterruptedException e) {
			
		}
		
		System.out.println("La cuenta tiene un saldo de " +c1.getSaldo());
		
	}
	
	
}