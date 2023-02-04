package Intro;

/** Method 1  **/
class Multi extends Thread{         /** Note this class extends the Thread class **/
    public void run(){              /**  Hence we can Override the run of the parent class
                                        which is thread class in this case **/
        System.out.println("thread is running...");
    }
    public static void main(String args[]){
        Multi t1=new Multi();
        t1.start();
    }
}

/** Method 2 **/
class Multi3 implements Runnable{
    public void run(){
        System.out.println("thread is running...");
    }

    public static void main(String args[]){
        Multi3 m1=new Multi3();
        Thread t1 =new Thread(m1);   /** Using constructor of thread class which accepts runnable as an argument **/
        t1.start();
    }
}


public class Intro {
    public static void main(String[] args) throws InterruptedException {

        /** Creating a thread
         * class A extends Thread
         * Now make Object of class A
         *
         * Other way
         * Implement Runnable Interface
         * and pass it to a new Thread Object
         * as done below
         *
         * **/


        /** Here we are creating a new Thread
         * which when started using thread.start()
         * will start executing the Run method
         *
         * **/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are in the new thread "+ Thread.currentThread().getName());
                System.out.println("Current thread priority is "+ Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);    /** Here we are setting up the Static Component of the Dynamic Priority **/

        System.out.println("We are in thread: "+ Thread.currentThread().getName() + " before starting a new thread");
        thread.start(); /** This will call the run method of the Thread **/

        /** New thread scheduling [thread.start()] will take some time and hence the below line will be printed first and
         *  then the run method of the declared thread is called
         * **/

        System.out.println("We are in thread: "+ Thread.currentThread().getName() + " after starting a new thread");

        Thread.sleep(1000);



        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("We are in the new thread "+ Thread.currentThread().getName());
                System.out.println("Current thread priority is "+ Thread.currentThread().getPriority());
            }
        });




        /**  Other way of creating a Thread **/


        Thread thread2 = new CustomThread();
        thread2.setName("Custom Thread");
        thread2.start();



    }

    public static class CustomThread extends Thread{
        @Override
        public void run() {
            System.out.println("Hello From "+ this.getName());
        }
    }


}


