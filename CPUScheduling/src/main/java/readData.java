import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readData {

    /**
     * Reads processes from a data file and return a list of MyProcess objects
     * 
     * @param filePath the path to theinput file containing the process  data
     * @return a lits of MyProcess objects created fromt the file data
     * @throws IOException if any I/O error occurs while reading the file
     */
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