import java.util.Random;

public class Producer extends Thread {
    /* Reference to buffer to call insert function. */
    Buffer buffer;

    /**
     * Producer constructor that must be initialize with a reference to the buffer.
     * 
     * @param buffer
     */
    public Producer(Buffer buffer) {
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
            // Create new buffer item
            item.bufferItem = random.nextInt();

            // Attempt to insert buffer item
            if (buffer.insertItem(item)) {
                System.out.printf("Producer produced %d\n", item.bufferItem);
            } else {
                System.out.printf("report error condition");
            }
        }
    }
}