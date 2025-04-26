import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readData {

    public static List<MyProcess> readDataFromFile(String filePath) throws IOException{
        List<MyProcess> processes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        int pid = 1;

        while((line = br.readLine()) != null){
            String[] parts = line.trim().split("\\s+");
            int arrival = Integer.parseInt(parts[0]);
            int burst = Integer.parseInt(parts[1]);
            
            processes.add(new MyProcess(pid++, arrival, burst));
        }
        br.close();

        return  processes;
    }
}