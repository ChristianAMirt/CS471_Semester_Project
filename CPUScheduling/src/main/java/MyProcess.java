public class MyProcess {
    public int pid;
    public int arrivalTime;
    public int cpuBurstLength;
    public int startTime;
    public int completionTime;
    public int waitingTime;
    public int turnaroundTime;
    public int responseTime;
    public boolean completed;

    /**
     * Construct a new MyProcess
     * @param pid
     * @param arrivalTime
     * @param cpuBurstLength
     */
    public MyProcess(int pid, int arrivalTime, int cpuBurstLength) {
        this.arrivalTime = arrivalTime;
        this.cpuBurstLength = cpuBurstLength;
        this.pid = pid;
        this.completed = false;
    }
    /**
     * To output My process for debugging purposes
     */
    @Override
    public String toString() {
        return "Process ID: " + pid +
               ", Arrival Time: " + arrivalTime +
               ", CPU Burst: " + cpuBurstLength +
               ", Start Time: " + startTime +
               ", Completion Time: " + completionTime +
               ", Turnaround Time: " + turnaroundTime +
               ", Waiting Time: " + waitingTime +
               ", Response Time: " + responseTime;
    }

}

