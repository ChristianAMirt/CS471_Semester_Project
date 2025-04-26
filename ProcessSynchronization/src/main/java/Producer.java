import java.util.Random;

public class Producer extends Thread {
    /* Reference to buffer to call insert function. */
    private Buffer buffer;

    /**
     * Producer constructor that must be initialize with a reference to the buffer.
     * 
     * @param buffer
     */
    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Overridden function to run threads in Java. Function attempts to produce an
     * item to the buffer after sleeping for a random interval.
     */
    @Override
    public void run() {
        Random random = new Random();
        int sleepTime = random.nextInt(ProcessSynchronizationDriver.MAX_SLEEP_TIME);

        // Continuously executing until thread terminates
        while (true) {
            try {
                sleep(sleepTime);

                // Create new buffer item
                Item item = new Item();
                item.bufferItem = random.nextInt();

                // Attempt to insert buffer item
                if (buffer.insertItem(item)) {
                    System.out.printf("Producer produced %d\n", item.bufferItem);
                } else {
                    System.out.printf("report error condition Producer\n");
                }
            } catch (InterruptedException exception) {
                break;
            }
        }
    }
}