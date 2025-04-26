import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessSynchronizationDriver {
    /* The max amount of time a single thread can sleep for. */
    public static final int MAX_SLEEP_TIME = 5000; // 5 seconds
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

        // Get command line arguments
        if (args.length != 3) {
            System.err.println("Usage: /.gradlew run --args=\"<sleepTime> <numProducers> <numConsumers>\"");
            System.exit(1);
        }
        sleepTime = Integer.parseInt(args[0]);
        numProducers = Integer.parseInt(args[1]);
        numConsumers = Integer.parseInt(args[2]);

        // Initialize buffer
        buffer = new Buffer(BUFFER_SIZE);

        // Create an outputfile and make sure the threads are printing to it
        try {
            File outputFile = new File("output.txt");
            PrintStream output = new PrintStream(outputFile);
            System.setOut(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create producer threads
        for (int i = 0; i < numProducers; i++) {
            Producer producer = new Producer(buffer);
            producer.start();
        }

        // Create consumer threads
        for (int i = 0; i < numConsumers; i++) {
            Consumer consumer = new Consumer(buffer);
            consumer.start();
        }

        // Sleep
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Stop execution
        System.exit(0);
    }
}
