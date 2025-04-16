import java.util.Scanner;
import  java.util.ArrayList;


public class CPUSchedulingDriver {
    /**
     * The main function used to demonstrate the TemperatureParser class.
     *
     * @param args used to pass in a single filename
     */
    public static void main(String[] args) {
        String filePath = "src/main/data/testdata.txt";

        ArrayList<Process> processes = readData.readProcessData(filePath);

        Scanner scanner  = new Scanner(System.in);
        System.out.println("Choose the Scheduling");
        System.out.println("1. FIFO");
        System.out.println("2. SJF");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                fifoScheduling FIFO = new fifoScheduling();
                FIFO.execute(processes);
                break;
            case 2:
                System.out.println("using SJF");
                break;
            default:
                throw new AssertionError();
        }
    }

}
