package DataSharing;

public class InventoryExample {

    public static void main(String[] args) throws InterruptedException {
//        InventoryCounter inventoryCounter = new InventoryCounter();
        InventoryCounter1 inventoryCounter = new InventoryCounter1();


        /** Note same inventory Object is passed to both Incrementing and Decrementing Thread **/
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);


        /** Here the issue is bcoz
         *  We are sharing the same Object of InventoryCounter between 2 Threads (so Items variable will be shared between the threads)
         *
         *  Items++ and Items-- are happening at the same time
         *  and They are not Atomic Operations
         *
         * **/
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
    }

    public static class DecrementingThread extends Thread {

        private InventoryCounter1 inventoryCounter;

        public DecrementingThread(InventoryCounter1 inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter1 inventoryCounter;

        public IncrementingThread(InventoryCounter1 inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        /** IF the below methods are not Synchronized then it will result in race condition **/

        public synchronized void increment() {
            items++;
        }

        public synchronized void decrement() {
            items--;
        }

        public synchronized int getItems() {
            return items;
        }
    }

    private static class InventoryCounter1 {
        private int items = 0;/** Instance variable belongs to the class, will live on heap, hence shared between threads **/

        /** NOTE the usage of synchronized blocks  **/

        Object lock = new Object();

        public  void increment() {
            synchronized( this.lock) {
                items++;
            }
        }

        public  void decrement() {
            synchronized (this.lock) {
                items--;
            }
        }

        public  int getItems() {
            synchronized (this.lock) {
                return items;
            }
        }
    }


}

