public class Buffer {
    /* Private property to maintain buffer */
    private Item[] bufferObject;

    /**
     * Buffer constructor that must be initialize with a fixed size.
     * 
     * @param bufferSize The size of the buffer.
     */
    public Buffer(int bufferSize) {
        this.bufferObject = new Item[bufferSize];
        // Initialize mutex
        // Initial empty and full semaphores
    }

    /**
     * Attempts to add an item to the buffer.
     * 
     * @param bufferItem The item to add.
     * @return If the insert was successful.
     */
    public boolean insertItem(Item bufferItem) {
        // Insert item into buffer
        // return true if success
        // return false indicating an error condition
        return true;
    }

    /**
     * Attempts to remove an item from the buffer.
     * 
     * @param bufferItem The item to remove.
     * @return If the removal was successful.
     */
    public boolean removeItem(Item bufferItem) {
        // Remove an object from the buffer placing it in item
        // return true if successful.
        // return false indicating an error conditon.
        return true;
    }
}
