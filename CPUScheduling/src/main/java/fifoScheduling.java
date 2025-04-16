import java.util.*;
import java.util.ArrayList;


public class fifoScheduling {
    public void execute(ArrayList<Process> processes){
        System.out.println("Running FIFO Scheduler...");
        for (Process p : processes) {
            System.out.println(p);
        }
    }
}
