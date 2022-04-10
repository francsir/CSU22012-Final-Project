import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BusSystemMain {

    private static table<String> newTable = new table<>();
    private static routeGraph test = new routeGraph(8757);
    private static int[][] adjMatrix;
    private static TST<Integer> netTST = new TST<>();
    private static LinkedList<Integer> stopIdList = new LinkedList<>();
    private static LinkedList<String>  placeNameList = new LinkedList<>();
    private static LinkedList<Integer> arrivalTimeList = new LinkedList<>();
    private static String[][] stopInfo = new String[8757][9];
    private static search bs = new search();


    public static void main(String[] args)
    {


        getStops();
        /*
        for(int i = 0; i < 8757; i++)
        stopIdList.sort(-1);
        int[] array = stopIdList.getArray();
        for(int i = 0; i < 8657; i ++)
        {
            System.out.println(Integer.toString(array[i]));
        }
        int ind = bs.binarySearch(array, 0, 8656, 12252);
        System.out.print(Integer.toString(ind));



         */

        //stopIdList.sort(-1);
        for(int i = 0; i < stopIdList.getIndPosition() - 1; i++)
        {
            System.out.println(stopIdList.getData(i));
        }

        /*
        adjMatrix = test.unPackGraph(stopIdList.getArray());
        arrivalTimeList = test.getTime();
        arrivalTimeList.sort(-1);]

         */


    }

    public static void getTST()
    {
        for(int i = 0; i < 8757; i++)
        {
            String key = newTable.getNodeData(i);
            netTST.put(key, i);
        }
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
                    if(i < 8758 && j < 9) {
                        stopInfo[i-1][j] = s;
                    }
                    if (j % 10 == 0) {
                        stopIdList.insertNode(Integer.parseInt(s), i);
                    } else if(j % 10 == 2) {
                        String[] modify = s.split(" ", 2);

                        if(modify[0].equals("NB") || modify[0].equals("WB") || modify[0].equals("SB") || modify[0].equals("EB") || modify[0].equals("FLAGSTOP"))
                        {
                            s = modify[1] +" " + modify[0];
                        }
                        modify = s.split(" ", 2);
                        if(modify[0].equals("NB") || modify[0].equals("WB") || modify[0].equals("SB") || modify[0].equals("EB") || modify[0].equals("FLAGSTOP"))
                        {
                            s = modify[1] +" " + modify[0];
                        }
                        placeNameList.insertNode(s, i);
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
