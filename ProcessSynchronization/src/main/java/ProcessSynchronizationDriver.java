import java.util.ArrayList;
import java.util.List;

public class ProcessSynchronizationDriver {
    /* The max amount of time a single thread can sleep for. */
    public static final int MAX_SLEEP_TIME = 5000; // 5 second
    /* The size of the buffer. */
    public static final int BUFFER_SIZE = 10;

    /**
     * The main function used to demonstrate the ProcessSynchronization class.
     *
     * @param args used to pass in three parameters:
     *             - How long to sleep before terminating
     *             - The number of producer threads
     *             - The number of consumer threads
     */
    public static void main(String[] args) {
        int sleepTime, numProducers, numConsumers;
        Buffer buffer;
        List<Thread> threads = new ArrayList<>();

        // Get command line arguments
        // TODO: Argument validation (e.g. make sure sleep time is more than thread max
        // sleep)
        sleepTime = Integer.parseInt(args[0]);
        numProducers = Integer.parseInt(args[1]);
        numConsumers = Integer.parseInt(args[2]);

        // Initialize buffer
        buffer = new Buffer(BUFFER_SIZE);

        // Create producer threads
        for (int i = 0; i < numProducers; i++) {
            Producer producer = new Producer(buffer);
            producer.start();
            System.out.println("Started thread " + i);
            threads.add(producer);
        }

        // Create consumer threads
        for (int i = 0; i < numConsumers; i++) {
            Consumer consumer = new Consumer(buffer);
            consumer.start();
            threads.add(consumer);
        }

        // Sleep
        try {
            System.out.println("Start");
            Thread.sleep(sleepTime);
            // Stop all threads
            for (Thread t : threads) {
                t.interrupt();
            }
            System.out.println("Done");
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}
