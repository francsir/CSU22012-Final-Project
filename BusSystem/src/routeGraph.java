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

        getArrayStops(sortedInd);
        return graph;
    }

    void printGraph(int[][] graph)
    {
        for(int i = 0; i <vertices; i++)
            for(int j = 0; j < vertices; j++)
            {
                if(graph[i][j] != Integer.MAX_VALUE) {
                    System.out.print(graph[i][j] + " ");
                }
            }
        System.out.println();
    }

    int[] getArrayTransfers()
    {
        String delimiter = "[,]+";
        String [] token;
        BufferedReader bf = null;
        List<Integer> intList = new ArrayList<>();
        try {
            bf = new BufferedReader(new FileReader("transfers.txt"));
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

    void getArrayStops(int []sortedInd)
    {
        String delimiter = "[,]+";
        String[] token;
        String[] nextToken;
        BufferedReader bf = null;

        List<Integer> intList = new ArrayList<>();

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
        token = line.split(delimiter);
        try {
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nextToken = line.split(delimiter);

        boolean start = false;

        while(line != null)
        {
            if(start)
            {
              if(token[0].equals(nextToken[0]))
              {
                  intList.add(Integer.parseInt(token[3]));
                  intList.add(Integer.parseInt(nextToken[3]));
              }
            }

            try
            {
                line = bf.readLine();
            }
            catch (IOException e)
            {
                
                e.printStackTrace();
            }
            token = nextToken;
            if(line != null) {
                nextToken = line.split(delimiter);
            }
            start = true;
        }
        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int xI;
        int yI;
        for(int i = 0; i < intList.size()-1; i += 2)
        {
            xI = searcher.binarySearch(sortedInd, 0, sortedInd.length -1, intList.get(i));
            yI = searcher.binarySearch(sortedInd, 0, sortedInd.length -1, intList.get(i+1));

            graph[xI][yI] = 1;

        }
    }
}
