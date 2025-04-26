import java.util.Random;

public class Consumer extends Thread {
    /* Reference to buffer to call remove function. */
    private Buffer buffer;

    /**
     * Consumer constructor that must be initialize with a reference to the buffer.
     * 
     * @param buffer
     */
    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Overridden function to run threads in Java. Function attempts to consume an
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

                // Attempt to consume a buffer item
                Item item = new Item();
                if (buffer.removeItem(item)) {
                    System.out.printf("Consumer consumed %d\n", item.bufferItem);
                } else {
                    System.out.printf("report error condition Consumer\n");
                }
            } catch (InterruptedException exception) {
                break;
            }
        }
    }
}