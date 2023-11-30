
import java.util.Random;

class CCC {
    private double saldoDeCuenta;
    private String nombreDelTitular;
    private long numeroDeCuenta;


    public CCC(String nombreDelTitular, double saldoDeCuenta) {
        this.nombreDelTitular = nombreDelTitular;
        this.saldoDeCuenta = saldoDeCuenta;
        this.numeroDeCuenta = generarNumeroDeCuenta();
    }


    public void setIngreso(double cantidad) {
        saldoDeCuenta += cantidad;
        System.out.println("\nIngreso de " + cantidad + " realizado. Saldo actual: " + saldoDeCuenta);
    }

    public void setReintegro(double cantidad) {
        if (cantidad <= saldoDeCuenta) {
            saldoDeCuenta -= cantidad;
            System.out.println("\nReintegro de " + cantidad + " realizado. Saldo actual: " + saldoDeCuenta);
        } else {
            System.out.println("\nSaldo insuficiente para realizar el reintegro.");
        }
    }


    public double getSaldoCuenta() {
        return saldoDeCuenta;
    }

    public String getDatosCuenta() {
        return "Titular: " + nombreDelTitular + "\nNúmero de cuenta: " + numeroDeCuenta + "\nSaldo actual: " + saldoDeCuenta;
    }


    public void realizarTransferenciaEntreCuentas(CCC cuentaDestino, double cantidad) {
        if (cantidad <= saldoDeCuenta) {
            setReintegro(cantidad);
            cuentaDestino.setIngreso(cantidad);
            System.out.println("Transferencia realizada con éxito.");
        } else {
            System.out.println("Saldo insuficiente para realizar la transferencia.");
        }
    }


    private long generarNumeroDeCuenta() {
        Random random = new Random();
        return Math.abs(random.nextLong());
    }
}

public class OperacionesBanco {
    public static void main(String[] args) {
 
        CCC CCC_1 = new CCC("Titular_1", 1000.0);
        CCC CCC_2 = new CCC("Titular_2", 500.0);

        System.out.println("Datos de CCC_1:\n" + CCC_1.getDatosCuenta());
        System.out.println("\nDatos de CCC_2:\n" + CCC_2.getDatosCuenta());
        
        CCC_1.realizarTransferenciaEntreCuentas(CCC_2, 300.0);


        System.out.println("\nDatos de CCC_1:\n" + CCC_1.getDatosCuenta());
        System.out.println("\nDatos de CCC_2:\n" + CCC_2.getDatosCuenta());
        
    }
}

