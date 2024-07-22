package Task4;

public class BallThread extends Thread {
    private final Ball b;

    public BallThread(Ball ball){
        b = ball;
    }
    @Override
    public void run(){
        try{
            for(int i=1; i<200; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch(InterruptedException ex){
            System.out.println("Interrupted!!!");
        }
    }
}