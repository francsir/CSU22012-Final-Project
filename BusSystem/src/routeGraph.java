import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class routeGraph {

    int[][] graph;
    int vertices;
    search searcher = new search();

    public routeGraph(int dest){
        graph = new int[dest][dest];
        vertices = dest;
    }
    int[][] unPackGraph(int[] sortedInd){
        int [] array = getArrayTransfers();
        for(int i = 0; i < vertices; i++)
        {
            for(int j = 0; j < vertices; j++)
            {
                graph[i][j] = Integer.MAX_VALUE;
            }
        }
        int v1,v2,v3,v4;
        for(int i = 0; i < array.length; i += 4)
        {
            v1 = array[i];
            v2 = array[i+1];
            v3 = array[i+2];
            v4 = array[i+3];

            int xI = searcher.binarySearch(sortedInd, 0, sortedInd.length -1, v1);
            int yI = searcher.binarySearch(sortedInd, 0, sortedInd.length -1, v2);

            if(v4 == Integer.MAX_VALUE)
            {
                graph[xI][yI] = 2;
            }
            else
            {
                graph[xI][yI] = v4/100;
            }

        }

        return graph;
    }

    void printGraph(int[][] graph)
    {
        for(int i = 0; i <vertices; i++)
            for(int j = 0; j < vertices; j++)
            {
                System.out.print(graph[i][j] + " ");
            }
        System.out.println();
    }

    int[] getArrayTransfers()
    {
        String delimiter = "[,]+";
        String [] token;
        BufferedReader bf = null;
        List<Integer> intList = new ArrayList<Integer>();
        try {
            bf = new BufferedReader(new FileReader("Transfers.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean start = false;
        while(line != null)
        {
            token = line.split(delimiter);
            if(start) {
                for (String s : token) {
                    if (!s.isEmpty()) {
                        intList.add(Integer.parseInt(s));
                    }
                }
                if(token.length == 3) {
                    intList.add(Integer.MAX_VALUE);
                }
            }
            try {
                line = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            start = true;
        }
        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] myArray = new int[intList.size()];
        for(int i = 0; i < myArray.length; i++)
        {
            myArray[i] = intList.get(i);
        }
        return myArray;
    }

    int[] getArrayStops()
    {
        String delimiter = "[,]+";
        String [] token;
        BufferedReader bf = null;
        List<Integer> intList = new ArrayList<Integer>();
        try {
            bf = new BufferedReader(new FileReader("stop_times.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        try {
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean start = false;
        while(line != null)
        {
            token = line.split(delimiter);
            if(start) {
                for (String s : token) {
                    if (!s.isEmpty()) {
                        intList.add(Integer.parseInt(s));
                    }
                }
                if(token.length == 3) {
                    intList.add(Integer.MAX_VALUE);
                }
            }
            try {
                line = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            start = true;
        }
        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] myArray = new int[intList.size()];
        for(int i = 0; i < myArray.length; i++)
        {
            myArray[i] = intList.get(i);
        }
        return myArray;

    }
}
