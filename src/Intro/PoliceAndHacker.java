package Intro;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliceAndHacker {

    /** This example shows the use case of extending the Thread class  **/

    public final static int MAX_PASSWORD = 10000;
    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        /** Note here we are creating List of THREADS **/
        List<Thread> threads = new ArrayList<>();


        /** Note here we are passing the same Vault Object to the Ascending  and Descending Hacker Thread **/
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for(Thread thread : threads){
            thread.start();
        }

    }

    public static class Vault{
        private int password;

        public Vault(int password){
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
            return this.password==guess;
        }
    }

    private static abstract class  HackerThread extends Thread{

        protected  Vault vault;

        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public synchronized void start() {
            System.out.println("Starting thread "+ this.getName());
            super.start();
        }

    }

    public static class AscendingHackerThread extends HackerThread{

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess=0; guess<MAX_PASSWORD;guess++){

                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName()+" guessed the password "+guess);
                    System.exit(0);
                }
            }
        }
    }


    public static class DescendingHackerThread extends HackerThread{

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int guess= MAX_PASSWORD;guess>=0;guess--){

                if(vault.isCorrectPassword(guess)){
                    System.out.println(this.getName()+" guessed the password "+guess);
                    System.exit(0);
                }
            }

        }
    }


    public static class PoliceThread extends Thread{
        @Override
        public void run() {
           for(int i=10;i>0;i--){
               try{
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println(i);
           }

            System.out.println("Game Over Hackers!! ");
            System.exit(0);
        }
    }

}
