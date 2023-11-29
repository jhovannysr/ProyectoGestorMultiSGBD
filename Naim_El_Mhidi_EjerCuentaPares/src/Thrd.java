public class Thrd extends Thread{
    private int cont=0;
    private int i;
    private int j=100000000;

    public void run(){
        j=j/5;

        for(i=1;i<=j;i++){
            if(i%2==0){
                cont++;
            }
        }
    }

    public int getCont(){
        return cont;
    }

}