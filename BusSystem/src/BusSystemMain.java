import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusSystemMain {

    private static table<String> newTable = new table<>();
    private static routeGraph test = new routeGraph(8757);


    public static void main(String[] args)
    {
        getStops();
        newTable.sort();
        test.unPackGraph(newTable.getIndArray());
    }

    static void getStops()
    {
        String delimiter = "[,]+";
        String [] token;

        List<String> stringList = new ArrayList<>();
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader("stops.txt"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try
        {
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i =0;
        int j;
        while (line != null) {
            j = 0;
            token = line.split(delimiter);
            for (String s : token) {
                if(i > 0 && !s.isEmpty()) {
                    if (j % 10 == 0) {
                        newTable.insertInd(Integer.parseInt(s));
                    } else {
                        newTable.insertNode(i, s);
                    }
                    j++;
                }
            }

            i++;

            try {
                line = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bf.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
