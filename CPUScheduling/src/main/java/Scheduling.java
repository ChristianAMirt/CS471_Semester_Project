import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduling {
    public int totalElapseTime = 0;
    public int processInitiation = 0; // when the first process starts running
    public int totalBurstTime = 0;
    public int totalProcesses = 0;
    public int totalWaitTime = 0;
    public int totalTT = 0;
    public int totalResTime = 0;
    public double throughput = 0;
    public double avgWaitTime = 0;
    public double avgTT = 0;
    public double avgResTime = 0;
    public double CPUutilization = 0.0;
    
    
    public List<MyProcess> executeFIFO(List<MyProcess> data){
        List<MyProcess> processes = new ArrayList<>(data);
        processes.sort(Comparator.comparingInt(p->p.arrivalTime));
        int time = 0;
        
        for(MyProcess p : processes){
            if(processInitiation == 0){
                processInitiation = p.arrivalTime;
            }
            
            if (time < p.arrivalTime) time = p.arrivalTime; // wait if CPU is idle

            p.startTime = time;
            p.completionTime = time + p.cpuBurstLength;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.cpuBurstLength;
            p.responseTime = p.startTime - p.arrivalTime;

            time = p.completionTime; // move time forward
            totalElapseTime = time;
            totalProcesses++;
            totalBurstTime += p.cpuBurstLength;
            totalWaitTime += p.waitingTime;
            totalTT += p.turnaroundTime;
            totalResTime += p.responseTime;

        }
        totalElapseTime = totalElapseTime - processInitiation;
        CPUutilization = ((double)totalBurstTime/totalElapseTime)*100;
        avgWaitTime = ((double)totalWaitTime/totalProcesses);
        avgTT = ((double)totalTT/totalProcesses);
        avgResTime =((double)totalResTime/totalProcesses);
        throughput = ((double)totalBurstTime/totalProcesses);
        printMetrics();
        return processes;
    }

    public List<MyProcess> executeSJF(List<MyProcess> data){
        List<MyProcess> processes = new ArrayList<>(data);
        processes.sort(Comparator.comparingInt(p->p.cpuBurstLength));
        int currenttime = 0;
        int completedCount = 0; //for counting how many process are done using CPU
        int n = processes.size(); //total num of processes
        int firstProcess = 0;

        //continue until all processes are done using CPU
        while(completedCount < n){
            //holder for a processes that have arrrived and not complete using CPU
            List<MyProcess> availableProcesses = new ArrayList<>();

            //adding a process into our holder list of Myprocess
            for(MyProcess p : processes){
                if(!p.completed && p.arrivalTime <= currenttime){
                    availableProcesses.add(p);
                    if(firstProcess == 0){
                        firstProcess = p.arrivalTime;
                    }
                    // System.out.println("burst: "+p.cpuBurstLength);
                }
            }

            if(!availableProcesses.isEmpty()){
                MyProcess shortest = availableProcesses.get(0);
                for(MyProcess p : availableProcesses){
                    if(p.cpuBurstLength < shortest.cpuBurstLength){
                        shortest = p;
                    }
                }
                shortest.startTime = currenttime;
                shortest.completionTime = currenttime + shortest.cpuBurstLength;
                shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
                shortest.waitingTime = shortest.turnaroundTime - shortest.cpuBurstLength;
                shortest.responseTime = shortest.startTime - shortest.arrivalTime;
                shortest.completed = true;
                // ********************************
                // System.out.println("\n");
                // System.out.println("Start: " + shortest.startTime);
                // System.out.println("Complt: " + shortest.completionTime);
                // System.out.println("tt: " + shortest.turnaroundTime);
                // System.out.println("wt: " + shortest.waitingTime);
                // System.out.println("res: " + shortest.responseTime);
                // System.out.println("\n");
                // *********************************
                currenttime = shortest.completionTime;
                totalElapseTime = currenttime;
                totalProcesses++; //check this if the right size of process
                totalBurstTime += shortest.cpuBurstLength;
                totalWaitTime += shortest.waitingTime;
                totalTT += shortest.turnaroundTime;
                totalResTime += shortest.responseTime;


                completedCount++;
                // System.out.println("pid: " + shortest.pid);

            }else{
                currenttime++; //no process and keep adding time 
            }
            
        }
        // System.out.println("initialized: " + firstProcess);
        
        totalElapseTime = totalElapseTime - firstProcess;
        CPUutilization = ((double)totalBurstTime/totalElapseTime)*100;
        avgWaitTime = ((double)totalWaitTime/totalProcesses);
        avgTT = ((double)totalTT/totalProcesses);
        avgResTime =((double)totalResTime/totalProcesses);
        throughput = ((double)totalBurstTime/totalProcesses);
        printMetrics();
        return processes;
    }

    public void printMetrics(){
        System.out.println("\nNumber Of Process: " + totalProcesses);
        System.out.println("Total Time: " + totalElapseTime);
        System.out.println("Throughput: " +String.format("%.2f", throughput));
        System.out.println("CPU Utilization: " + String.format("%.2f", CPUutilization)+ "%");
        System.out.println("Average Waiting Time: " + String.format("%.2f",avgWaitTime));
        System.out.println("Average Total Turnaround Time: "+ String.format("%.2f",avgTT));
        System.out.println("Average Response Time: " +String.format("%.2f",avgResTime)+"\n");
    }

}
