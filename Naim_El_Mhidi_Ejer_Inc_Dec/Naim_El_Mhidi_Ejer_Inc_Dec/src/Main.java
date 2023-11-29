
public class Main{
	public static void main(String args[]) throws InterruptedException{
		int hilo=30;
		Contador c=new Contador();
		Thread[]hiloInc=new Thread[hilo];
		Thread[]hiloDec=new Thread[hilo];
		
		while(true){
			for(int num=0;num<hilo;num++){
				hiloInc[num]=new Incremento(c,num);
				hiloDec[num]=new Decremento(c,num);
			}
			
			for(int i=0;i<hilo;i++){
				hiloInc[i].start();
				hiloDec[i].start();
			}
			
			Thread.sleep(3000);
		}
	}
}
