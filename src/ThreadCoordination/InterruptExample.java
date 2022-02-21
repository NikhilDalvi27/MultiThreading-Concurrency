package ThreadCoordination;

import java.math.BigInteger;

public class InterruptExample {
    public static void main(String[] args) {
        Thread thread1 =  new Thread(new BlockingTask());
        thread1.start();
        thread1.interrupt();
        /** Here we are sending interrupt signal
         To the BlockingTask thread (Which has a sleep method, that throws Interrupted Exception)
         From the main thread**/



        Thread thread2 = new Thread(new LongComputationTask(new BigInteger("2"),new BigInteger("1000")));
        thread2.setDaemon(true);    /** Note this indicates that if the Main thread is completed then
                                        JVM will stop execution of thread2, irrespective whether
                                        thread2 is completed or not **/
        thread2.start();

//        thread2.interrupt();

        /** Here we are sending interrupt signal
         To the LongComputationTask thread (Which is doing some calculation
                                            and  doesn't have a  method that throws Interrupted Exception)
         From the main thread
         IMP
         To respect the interrupt
         we need to add some Logic (Check the status of the Interrupt Flag in the run() method )
         to handle interrupt in LongComputationTask thread **/



    }

    private static class BlockingTask implements Runnable{

        @Override
        public void run() {

            try {
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                System.out.println("Exiting Blocking Thread");
            }
        }
    }

    private  static class  LongComputationTask implements Runnable{
        private BigInteger base;
        private  BigInteger pow;

        public  LongComputationTask(BigInteger base,BigInteger pow){
            this.base = base;
            this.pow = pow;
        }


        @Override
        public void run() {
            System.out.println(base+"^"+pow+ " :"+ pow(base,pow));
        }

        private BigInteger pow(BigInteger base, BigInteger pow){

            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(pow)!=0; i = i.add(BigInteger.ONE) ){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted computation");
                    System.exit(0);
                }

                result = result.multiply(base);
            }

            return result;
        }

    }






}
