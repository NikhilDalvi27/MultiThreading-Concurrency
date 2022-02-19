package ThreadCoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoining {

    public static void main(String[] args) throws InterruptedException {

        /** Try adjusting the size of this inputNumbers **/

        List<Long> inputNumbers = Arrays.asList(0L,3435L,2324L,3442L,21432L,513L,0L,3435L,2324L,3442L,21432L,513L,0L,3435L,2324L,3442L,21432L,513L);

        List<FactorialThread>threads = new ArrayList<>();

        /** Here we will calculate the Factorial for each number in a seperate thread **/
        for(long inputNumber: inputNumbers){
            threads.add(new FactorialThread(inputNumber));  /** Here we are creating Threads **/
        }

        long startTime = System.currentTimeMillis();

        for(Thread thread: threads){
            thread.start();     /**  Race condition start  **/
        }

        /** When we are checking for the result of each thread here,
         *  There's a race condition between the Main thread and the Threads whose result we are checking
         *  Since Main Thread and all the other Factorial threads are running independently
         * **/


        /** To address the above issue we need to add JOIN between the Race condition start and Race condition end
         *  JOIN will make the Main Thread wait until all the Factorial threads are finished
         *  JOIN method of each Thread returns only when that particular thread is Terminated
         *
         * **/

        for(Thread thread : threads){

            /** NOTE always use TIMEOUT (in ms) when using JOIN **/
            thread.join( 2000);  /** JOIN Method for each thread--> will return only when that particular thread has COMPLETED Execution **/
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for calculation with Multithreading  : "+ (endTime-startTime));



        /** Without Multithreading Start **/
        startTime = System.currentTimeMillis();
        List<BigInteger> al = new ArrayList<>();
        for(int i=0;i<inputNumbers.size();i++)
        {
            Factorial factorial = new Factorial(inputNumbers.get(i));
            al.add(factorial.calculateFactorial());
        }

        endTime = System.currentTimeMillis();
        /** Without Multithreading End **/

        System.out.println("Time taken for calculation without Multithreading  : "+ (endTime-startTime));




        for(int i=0;i<inputNumbers.size();i++)
        {
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.getFinished()){          /**  Race condition end  **/
                if(factorialThread.getResult().compareTo(al.get(i))!=0){
                    System.out.println("ERROR!!!!");
                }
                System.out.println("Factorial of the number "+ inputNumbers.get(i)+" is "+factorialThread.getResult());
            }else{
                System.out.println("The Calculation for "+inputNumbers.get(i)+" is still in progress");
            }

        }


    }

    public static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private Boolean isFinished = false;


        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }


        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long inputNumber){
            BigInteger tempResult = BigInteger.ONE;
            for(long i=inputNumber;i>0;i--)
            {
                tempResult= tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }


        public Boolean getFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }

    public static class Factorial{
        private BigInteger result = BigInteger.ZERO;
        private long inputNumber;

        public Factorial(long inputNumber){
            this.inputNumber = inputNumber;
        }

        public BigInteger calculateFactorial(){
            BigInteger tempResult = BigInteger.ONE;
            for(long i=this.inputNumber;i>0;i--)
            {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

    }



}
