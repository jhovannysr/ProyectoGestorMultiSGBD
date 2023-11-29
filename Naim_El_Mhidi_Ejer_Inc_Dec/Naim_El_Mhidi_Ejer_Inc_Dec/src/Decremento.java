
public class Decremento extends Thread{
	private Contador con;
	
	public Decremento(Contador cont,int numH) {
		this.con=cont;
		setName("DEC"+numH);
	}
	
	@Override
	public void run() {
		synchronized(con){
			if(con.getCont()==30){
				con.notify();
			}
			
			if(con.getCont()>0){
				con.decremento(getName());
			}
			
			if(con.getCont()==0){
				try{
					System.out.println("El valor " +getName()+" no puede decrementar");
					con.wait();
				}
				
				catch(InterruptedException e){
					System.out.println("Interrumpido");
				}
			}
		}
		super.run();
	}
}
