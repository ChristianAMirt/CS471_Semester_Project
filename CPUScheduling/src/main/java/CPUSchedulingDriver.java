import java.io.IOException;
import  java.util.List;
import java.util.Scanner;


public class CPUSchedulingDriver {
   /**
    * The entry point of the scheduling processing simulation 
    * @param args
    */
    public static void main(String[] args) {
        if(args.length < 1){
            System.out.println("error: No file path provided");
            System.exit(1);
        }
        String filePath = args[0];
        List<MyProcess> processes;
        try {
        processes = readData.readDataFromFile(filePath);
            while (true) { 
                Scanner scanner  = new Scanner(System.in);
                System.out.println("Choose the Scheduling");
                System.out.println("1. FIFO");
                System.out.println("2. SJF");
                System.out.println("3. Exit");

                int choice = scanner.nextInt();

                //types of Scheduling
                switch (choice) {
                    case 1:
                        System.out.println("using FIFO");
                        Scheduling FIFO = new Scheduling();
                        FIFO.executeFIFO(processes);
                        break;
                    case 2:
                        System.out.println("using SJF");
                        Scheduling SJF = new Scheduling();
                        SJF.executeSJF(processes);
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Try again");
                }
            }
        
            
        } catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }
        
    }

}

