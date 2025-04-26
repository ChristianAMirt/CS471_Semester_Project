import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    /* Property to maintain buffer items. */
    private Item[] bufferObject;
    /* Index that points to next insertion index */
    private int in = 0;
    /* Index that points to next removal index */
    private int out = 0;

    // Synchronization Semaphores
    /* Mutex lock so only 1 thread can access critical section at a time */
    private ReentrantLock mutex;
    /* Semaphore so consumers know when buffer is empty */
    private Semaphore empty;
    /* Semaphore so producers know when buffer is full */
    private Semaphore full;

    /**
     * Buffer constructor that must be initialize with a fixed size.
     * 
     * @param bufferSize The size of the buffer.
     */
    public Buffer(int bufferSize) {
        this.bufferObject = new Item[bufferSize];
        this.mutex = new ReentrantLock(true);
        this.empty = new Semaphore(bufferSize, true);
        this.full = new Semaphore(0, true);
    }

    /**
     * Attempts to add an item to the buffer.
     * 
     * @param item The item to add.
     * @return If the insert was successful.
     */
    public boolean insertItem(Item item) {
        try {
            // Aquire empty semaphore and mutex
            this.empty.acquire();
            this.mutex.lock();

            // Enter critical section: Insert item
            this.bufferObject[this.in] = item;
            this.in = (this.in + 1) % this.bufferObject.length;

            // Leave critical section and release resources
            this.mutex.unlock();
            this.full.release(); // Signal that there is one more full slot

            // printBuffer(); // Uncomment to show buffer in file

            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * Attempts to remove an item from the buffer.
     * 
     * @param item The item to remove.
     * @return If the removal was successful.
     */
    public boolean removeItem(Item item) {
        try {
            // Aquire full semaphore and mutex
            this.full.acquire();
            this.mutex.lock();

            // Enter critical section: Remove item
            Item removedItem = this.bufferObject[this.out];
            item.bufferItem = removedItem.bufferItem; // copy value to reference variable
            this.out = (this.out + 1) % this.bufferObject.length;

            // Leave critical section and release resources
            this.mutex.unlock();
            this.empty.release(); // Signal that there is one more empty slot

            // printBuffer(); // Uncomment to show buffer in file

            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * Print function used to display buffer for debugging.
     */
    public void printBuffer() {
        System.out.print("[");
        for (int i = 0; i < bufferObject.length; i++) {
            if (bufferObject[i] == null) {
                System.out.print("null");
            } else {
                System.out.print(bufferObject[i].bufferItem);
            }
            if (i != bufferObject.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

}
