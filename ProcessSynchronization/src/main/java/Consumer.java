import java.util.Random;

public class Consumer extends Thread {
    /* Reference to buffer to call remove function. */
    Buffer buffer;

    /**
     * Consumer constructor that must be initialize with a reference to the buffer.
     * 
     * @param buffer
     */
    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Overridden function to run threads in Java.
     */
    @Override
    public void run() {
        Random random = new Random();
        int sleepTime = random.nextInt(ProcessSynchronizationDriver.MAX_SLEEP_TIME);
        Item item = new Item();

        // Continuously executing until program terminates
        while (true) {
            try {
                sleep(sleepTime);
            } catch (InterruptedException exception) {
                break;
            }
            // Attempt to consume a buffer item
            if (buffer.removeItem(item)) {
                System.out.printf("Consumer consumed %d\n", item.bufferItem);
            } else {
                System.out.printf("report error condition");
            }
        }
    }
}