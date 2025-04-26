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


    public MyProcess(int pid, int arrivalTime, int cpuBurstLength) {
        this.arrivalTime = arrivalTime;
        this.cpuBurstLength = cpuBurstLength;
        this.pid = pid;
        this.completed = false;
    }

    // @Override
    // public String toString() {
    //     return "Arrival: " + arrivalTime + ", Burst: " + cpuBurstLength + 
    //             ", avgTT: " + turnaroundTime + ", Time: " + startTime;
    // }
}

/*
 - Number of process = num o f pid
 - total elapsed time (for the Scheduler)
 - throughput (number of processes executed in one unite of CPU burst time)
 - CPU utilization
 * Avg waiting time (in CPU burst time)
 * average turnaround time (in CPU burst times)
 * avg response time (in CPU burst times)
 */

 /*
  -total elaps time = the amount of time from initiation to termination of the application
  - througput = total burst time (sum of all given burst lenght)/ total num of process
  - CPU utilization = total burst time / total elapse
  - Avg waiting time = tatal waiting time / number of processes
  * TT =  exit time - arrival time
  - Avg TT = total tt / number of processs
  * response time = time at which the process gets the CPU for the first time - arrival time
  * avg response time =  total response time/ num of processes
  */