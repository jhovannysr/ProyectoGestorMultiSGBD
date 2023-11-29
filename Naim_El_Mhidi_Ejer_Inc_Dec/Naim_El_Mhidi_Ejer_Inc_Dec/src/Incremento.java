
public class Incremento extends Thread{
	private Contador cont;
	
	public Incremento(Contador cont,int numH){
		this.cont=cont;
		setName("INC"+numH);
	}
	
	@Override
	public void run(){
		synchronized(cont){
			if(cont.getCont()==0){
				cont.notify();
			}
			
			if(cont.getCont()>00){
				cont.incremento(getName());
			}
			
			if(cont.getCont()==30){
				try{
					System.out.println("El valor "+getName()+" no puede incrementar");
					cont.wait();
				}
				
				catch(InterruptedException e){
					System.out.println("Interrumpido");
				}
			}
		}
		super.run();
	}
	
}
