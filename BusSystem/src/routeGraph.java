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
    LinkedList<Integer> times = new LinkedList<>();

    public routeGraph(int dest){
        graph = new int[dest][dest];
        vertices = dest;
    }

    public LinkedList<Integer> getTime()
    {
        return times;
    }



    // converts the transfers and stop_times .txt files into an adjacency matrix, it also creates a LinkedList that
    //holds the arrival times. Takes in an array of sorted stopID's so it can perform binary search to find the proper
    //index int the matrix

    int[][] unPackGraph(Integer[] sortedInd){
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

    //test function to print the adjacency matrix
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


    //unpacks the transfers.txt
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

    //unpacks the stop_times.txt, puts the edges in the adjacency matrix, and creates the arrivalTime linked list
    void getArrayStops(Integer []sortedInd)
    {
        String delimiter = "[,]+";
        String timeDelimiter = "[:]";
        String [] token;
        String [] timeToken;
        String[] nextToken;
        int tempTime;
        BufferedReader bf = null;

        List<Integer> timeList = new ArrayList<>();
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
        StringBuilder time;
        int j = 0;
        while(line != null)
        {
            if(start)
            {

              if(token[0].equals(nextToken[0]))
              {
                  intList.add(Integer.parseInt(token[3]));
                  intList.add(Integer.parseInt(nextToken[3]));
              }
              timeToken = token[1].split(timeDelimiter);
              time = new StringBuilder();

              for(String s : timeToken)
              {
               time.append(s);
              }
              tempTime = Integer.parseInt(String.valueOf(time).replaceAll(" ", ""));
              if(tempTime > 0 && tempTime < 235959)
                {
                    times.insertNode(tempTime, j);
                }
                j++;
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
