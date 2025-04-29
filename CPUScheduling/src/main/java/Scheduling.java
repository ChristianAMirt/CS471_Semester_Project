import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    
    /**
     * Executes the FIFO scheduling on a list of processes
     * @param data list of MyProcess object to schedule
     * @return a list of MyProcess with updated information
     */
    public List<MyProcess> executeFIFO(List<MyProcess> data){
        List<MyProcess> processes = new ArrayList<>(data);
        processes.sort(Comparator.comparingInt(p->p.arrivalTime));
        int time = 0;
        
        for(MyProcess p : processes){
            if(processInitiation == 0){
                processInitiation = p.arrivalTime;
            }
            
            if (time < p.arrivalTime) time = p.arrivalTime; 

            p.startTime = time;
            p.completionTime = time + p.cpuBurstLength;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.cpuBurstLength;
            p.responseTime = p.startTime - p.arrivalTime;

            time = p.completionTime; // move time forward
        }
        String outFile = "FIFO.txt";
        for(MyProcess p : processes){
            System.out.println(p);
        }
        calculateMetrics(processes, outFile);
        return processes;
    }


    /**
     * create an output file for the metrics for the completed scheduling
     * @param outputFile the name of the file to be created
     */
    public void printMetrics(String outputFile){
        String resultPath = "src/main/data/results/";
        String fullPath = resultPath + outputFile; 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {

        String format = "%-30s%-30s%n";  

        writer.write(String.format(format, "Number Of Process:", totalProcesses));
        writer.write(String.format(format, "Total Time:", totalElapseTime));
        writer.write(String.format(format, "Throughput:", String.format("%.2f", throughput)));
        writer.write(String.format(format, "CPU Utilization:", String.format("%.2f", CPUutilization) + "%"));
        writer.write(String.format(format, "Average Waiting Time:", String.format("%.2f", avgWaitTime)));
        writer.write(String.format(format, "Average Total Turnaround Time:", String.format("%.2f", avgTT)));
        writer.write(String.format(format, "Average Response Time:", String.format("%.2f", avgResTime)));
        
    } catch (IOException e) {
        System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    /**
     * sort the arrival time with the burst time
     * @param MyData
     */
    public void executeSJF(List<MyProcess> MyData){
        List<MyProcess> data = new ArrayList<>(MyData);
        //sort data by arrival time
        data.sort(Comparator.comparingInt(p->p.arrivalTime));
        List<MyProcess> sorted_AT_BT = new ArrayList<>();
        
        
        //creating the gnatt chart for later use for calculations (sorting it by burst time)
        while(!data.isEmpty()){
            MyProcess process = data.get(0);
            
            boolean hasSameArrivalTime = false;
            for(int i = 0; i< sorted_AT_BT.size();i++){
                MyProcess p = sorted_AT_BT.get(i);
                //compare if same arrival time
                if(p.arrivalTime == process.arrivalTime){
                    //swap the the process if it has a high burst lengt
                    if(p.cpuBurstLength > process.cpuBurstLength){
                        sorted_AT_BT.add(i, process); // Insert the process before the one with the higher burst time
                    }else{
                        sorted_AT_BT.add(i+1,process);
                    }
                    hasSameArrivalTime = true;
                    break;
                }
            }
            
            if(!hasSameArrivalTime){
               sorted_AT_BT.add(process);
            }
            data.remove(0);
        }

        // System.out.println("--------------------------------------------------");
        // for(MyProcess p : sorted_AT_BT){
        //     System.out.println(p);
        // }
        // System.out.println("----------------------------------------------------");
        
        // System.out.println("gnatt Chart after calculation: ");
        calculateSJF(sorted_AT_BT);
    }

    /**
     * calculate the metrics for the SJF scheduling
     * @param data are list of procesess
     * @return
     */
    public List<MyProcess> calculateSJF(List<MyProcess> data){
        List<MyProcess> completedProcesses = new ArrayList<>();
        List<MyProcess> readyQueue = new ArrayList<>(data);
        int currentTime = 0;
        while(!readyQueue.isEmpty()){
            List<MyProcess> available = new ArrayList<>();
            System.out.println("time: " + currentTime);
            // Create a list to hold processes that have arrived by the current time
            for (MyProcess p : readyQueue) {
                if (p.arrivalTime <= currentTime) {
                    available.add(p);
                }
            }
    
            MyProcess nextProcess;
    
            if (!available.isEmpty()) {
                System.out.println("insite of if statement*********************");
                available.sort(Comparator.comparingInt(p->p.cpuBurstLength));
                // Pick the process with the shortest CPU burst time
                nextProcess = available.get(0);
                // for (MyProcess p : available) {
                //     // if (p.cpuBurstLength < nextProcess.cpuBurstLength) {
                //     //     nextProcess = p;
                //     // }
                //     System.out.println(p);
                // }
            } else {
                // No process has arrived yet, pick the one that will arrive the soonest
                nextProcess = readyQueue.get(0);
                for (MyProcess p : readyQueue) {
                    if (p.arrivalTime < nextProcess.arrivalTime) {
                        nextProcess = p;
                    }
                }
                // Jump time to when that process arrives
                currentTime = nextProcess.arrivalTime;
            }
    
            // Now, "run" the nextProcess
            currentTime += nextProcess.cpuBurstLength;
    
            // Update process times
            nextProcess.completionTime = currentTime;
            nextProcess.turnaroundTime = nextProcess.completionTime - nextProcess.arrivalTime;
            nextProcess.waitingTime = nextProcess.turnaroundTime - nextProcess.cpuBurstLength;
            nextProcess.responseTime = nextProcess.waitingTime; //since its non preemtive WT = RT
    
            // Add to completed list
            completedProcesses.add(nextProcess);
    
            // Remove from ready queue
            readyQueue.remove(nextProcess);
    
            
    
        }
        String filePath = "SJF.txt";
        calculateMetrics(completedProcesses,filePath);
    
        return completedProcesses; // Return completed processes
    }
    /**
     * calculate the metrics
     * @param data, list of process
     * @param filePath name of the output file
     */
    public void calculateMetrics(List<MyProcess> data, String filePath){
        for(MyProcess p : data){
            totalElapseTime = p.completionTime;
            totalProcesses++;
            totalBurstTime+= p.cpuBurstLength;
            totalWaitTime += p.waitingTime;
            totalTT+= p.turnaroundTime;
            totalResTime += p.responseTime;            

        }
        CPUutilization = ((double)totalBurstTime/totalElapseTime)*100;
        avgWaitTime = ((double)totalWaitTime/totalProcesses);
        avgTT = ((double)totalTT/totalProcesses);
        avgResTime =((double)totalResTime/totalProcesses);
        throughput = ((double)totalBurstTime/totalProcesses);
        printMetrics(filePath);
    }
}
