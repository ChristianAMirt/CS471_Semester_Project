import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readData {
    public static ArrayList<Process> readProcessData(String filePath) {
        ArrayList<Process> processes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    int arrival = Integer.parseInt(parts[0]);
                    int burst = Integer.parseInt(parts[1]);
                    processes.add(new Process(arrival, burst));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return processes;
    }
}