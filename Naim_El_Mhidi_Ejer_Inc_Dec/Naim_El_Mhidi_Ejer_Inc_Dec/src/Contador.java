
public class Contador {
	private int cont;
	
	public Contador(){
		cont=29;
	}
	
	public int getCont(){
		return cont;
	}
	
	public void setCont(int i){
		cont=i;
	}
	
	public synchronized void incremento(String s){
		cont++;
		System.out.println("El hilo "+s+" incrementa, valor contador: "+cont);
	}
	
	public void decremento(String s){
		cont--;
		System.out.println("El hilo "+s+" decrementa, valor contador: "+cont);
	}
}
