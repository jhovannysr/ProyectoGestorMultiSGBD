
import java.util.Date;

class ContadorPares implements Runnable {
    private int inicio;
    private int fin;
    private int contador;

    public ContadorPares(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
        this.contador = 0;
    }

    public int getContador() {
        return contador;
    }

    @Override
    public void run() {
        for (int i = inicio; i <= fin; i++) {
            if (i % 2 == 0) {
                contador++;
                //System.out.println(i + " es par");
            }
        }
    }
}

public class CuentaPares {
    public static void main(String[] args) throws InterruptedException {
        int x = 1;
        int y = 100000000;
        int rango = (y - x + 1) / 5;

        Thread[] threads = new Thread[5];
        ContadorPares[] contadores = new ContadorPares[5];

        long t0 = (new Date()).getTime();

        for (int i = 0; i < 5; i++) {
            int inicio = x + i * rango;
            int fin = (i == 4) ? y : (x + (i + 1) * rango - 1);

            contadores[i] = new ContadorPares(inicio, fin);
            threads[i] = new Thread(contadores[i]);
            threads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            threads[i].join();
        }

        int totalPares = 0;
        for (int i = 0; i < 5; i++) {
            totalPares += contadores[i].getContador();
        }

        long t1 = (new Date()).getTime();
        long time = t1 - t0;

        System.out.println("Número de pares en el intervalo: " + x + " - " + y +
                " es igual a " + totalPares + " calculado en " + time + " milisegundos");
    }
}

