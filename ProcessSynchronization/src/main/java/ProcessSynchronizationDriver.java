import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessSynchronizationDriver {
    /* The max amount of time a single thread can sleep for. */
    public static final int MAX_SLEEP_TIME = 1000; // 5 seconds
    /* The size of the buffer. */
    public static final int BUFFER_SIZE = 10;

    /**
     * The main function used to demonstrate the ProcessSynchronization class.
     *
     * @param args used to pass in three parameters:
     *             - How long to sleep before terminating
     *             - The number of producer threads
     *             - The number of consumer
     * 
     *             or, used to pass two parameters:
     *             - How long to sleep before terminating
     *             - The name of the test case input file
     */
    public static void main(String[] args) {
        // Get command line arguments
        if (args.length == 1) {
            // Case of multiple tests in input file
            Scanner scanner = null;
            String inFileName = args[0];

            try {
                File inFile = new File(inFileName);
                scanner = new Scanner(inFile);
                scanner.nextLine(); // Throw out headers
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
                System.exit(1);
            }

            // Create an output file
            String outFileName = inFileName.replaceAll("^.*[\\\\/](.*)\\..*$", "$1") + "_output.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(outFileName))) {
                writer.println("Sleep Time (ms):\tNumber of Producers:\tNumber of Consumers:\tTurnaround Time (ms):");

                // Run each test case seperately from file
                while (scanner.hasNextLine()) {
                    String[] params = scanner.nextLine().split("\t");

                    float startTime = System.nanoTime();

                    testProducerConsumer(Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]));

                    float endTime = System.nanoTime();
                    float turnAroudTime = (endTime - startTime) / 1_000_000;

                    writer.printf("%s\t%s\t%s\t%.5f\n", params[1], params[2], params[3], turnAroudTime);

                    System.out.println("End test case " + params[0]);
                }
            } catch (IOException e) {
                System.err.println("Error while writing to output file.");
            }
        } else if (args.length == 3) {
            // Case of single test given in arguments
            int sleepTime = Integer.parseInt(args[0]);
            int numProducers = Integer.parseInt(args[1]);
            int numConsumers = Integer.parseInt(args[2]);

            testProducerConsumer(sleepTime, numProducers, numConsumers);
        } else {
            // Case of incorrect number of arguments
            System.err.println(
                    "Usage for single test: /.gradlew run --args=\"<sleepTime> <numProducers> <numConsumers>\"");
            System.err.println("Usage for multiple test cases: /.gradlew run --args=\"<sleepTime> <inputFileName>\"");
            System.exit(1);
        }

        // Exit program
        System.exit(0);
    }

    /**
     * Initializes a buffer, creates an output file, and creates a specified number
     * of producer and consumer threads. Those threads access the buffer at random
     * times (sometimes at the same time) to read/write data. The function returns
     * after a specified amount of time and terminates all threads.
     * 
     * @param sleepTime    The amount of time in miliseconds to sleep for.
     * @param numProducers The number of producer threads to create.
     * @param numConsumers The number of consumer threads to create.
     */
    public static void testProducerConsumer(int sleepTime, int numProducers, int numConsumers) {
        Buffer buffer;
        List<Thread> threads = new ArrayList<>();

        // Initialize buffer
        buffer = new Buffer(BUFFER_SIZE);

        // Create producer threads
        for (int i = 0; i < numProducers; i++) {
            Producer producer = new Producer(buffer);
            producer.start();
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
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Stop execution
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}
