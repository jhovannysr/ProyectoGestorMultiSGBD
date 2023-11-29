import java.io.IOException;
import java.util.Date;

public class Main{
    public static void main(String[] args) throws InterruptedException{
        
        Thrd t1=new Thrd();
        Thrd t2=new Thrd();
        Thrd t3=new Thrd();
        Thrd t4=new Thrd();
        Thrd t5=new Thrd();

        long t01=(new Date()).getTime();

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        long t02=(new Date()).getTime();
        long time=t02-t01;

        int n=t1.getCont()+t2.getCont()+t3.getCont()+t4.getCont()+t5.getCont();

        System.out.println("Número de pares es igual a "+n +" calculado en "+time+ " milisegundos");

    }

}
