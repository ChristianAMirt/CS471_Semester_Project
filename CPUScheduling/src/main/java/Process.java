public class Process {
    public int arrivalTime;
    public int cpuBurstLength;

    public Process(int arrivalTime, int cpuBurstLength) {
        this.arrivalTime = arrivalTime;
        this.cpuBurstLength = cpuBurstLength;
    }

    @Override
    public String toString() {
        return "Arrival: " + arrivalTime + ", Burst: " + cpuBurstLength;
    }
}