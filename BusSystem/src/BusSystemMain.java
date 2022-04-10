import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BusSystemMain {

    private static routeGraph routeGraph = new routeGraph(8757);
    private static int[][] adjMatrix;
    private static TST<Integer> netTST = new TST<>();
    private static LinkedList<Integer> stopIdList = new LinkedList<>();
    private static LinkedList<String>  placeNameList = new LinkedList<>();
    private static LinkedList<Integer> arrivalTimeList = new LinkedList<>();
    private static String[][] stopInfo = new String[8757][9];
    private static dijkstra dij = new dijkstra();
    private static Integer[] stopArray;
    private static String[] placeArray;
    private static search bs = new search();
    private static Scanner input = new Scanner(System.in);



    public static void main(String[] args)
    {
        systemInit();
        boolean exit = false;
        System.out.println("Welcome to the Vancouver Bus System Database");
        while(!exit)
        {
            System.out.println("**Menu**\nEnter: \n1 to find shortest route between two stops");
            System.out.println("2 to Search for a bus stop by name");
            System.out.println("3 to Search for all trips with a given arrival time");
            System.out.println("or EXIT to exit System");
            if(input.hasNextInt())
            {
                int currentNumber = input.nextInt();
                if(currentNumber > 0 && currentNumber < 4) {
                    switch (currentNumber)
                    {
                        case 1:
                            case1();
                            break;

                        case 2:
                            break;

                        case 3:
                            break;

                    }

                }
                else{
                    System.out.println("Error - Please enter a valid input");
                }
            }
            else if(!(input.hasNextDouble())) {
                if (input.hasNext("EXIT")) {
                    exit = true;
                    break;
                }
                else{
                    System.out.println("Error - Please enter a valid input");
                    input.next();
                }
            }



        }
     }

     public static void case1()
     {
         String s1 = "";
         String s2 = "";
         int ind1;
         int ind2;
         boolean menu = false;

         while(!menu)
         {
             System.out.println("Enter menu to return to menu");
             System.out.print("Enter Stop One: ");
             if(input.hasNext())
             {
                 input.useDelimiter("\n");
                 s1 = input.next();
                 if(s1.equals("menu"))
                 {
                     menu = true;
                     break;
                 }
                 ind1 = bs.binarySearch(placeArray, 0, stopArray.length -1, s1);
                 if(ind1 == -1)
                 {
                     System.out.println("Sorry that stop does not exist, remember, prefix's nb, wb, sb, eb and flagstop" +
                             " are placed at the end of the stop");

                 }
                 else {
                     ind2 = placeNameList.getInd(ind1);//returns the index to retrieve data from stopInfo, ind will need to decremented

                 }
             }

         }





     }

    //initial setup of the system.
    public static void systemInit()
    {
        getStops();

        //call functions to sort these lists in order
        stopIdList.sort(-1);
        stopArray = stopIdList.getIntArray();
        placeNameList.sort("\0");
        placeArray = placeNameList.getStrArray();


        //make the Ternary Search Tree
        getTST();

        //get the adjMatrix
        Integer[] stopID = stopIdList.getIntArray();
        adjMatrix = routeGraph.unPackGraph(stopID);
    }
    //Function to call the Ternary Search Tree Class, it modifies the tre private tree declared above.
    public static void getTST()
    {
        for(int i = 0; i < 8756; i++)
        {
            String key = placeNameList.getData(i);
            netTST.put(key, i);
        }
    }

    /*
        Method which parses the stops.txt into a matrix, and two LinkedList, one for stop_id and the other for stop names,
        both these lists contain the index for their correlating row in the matrix. It also changes the prefix from the
        stop names, in order for them to be searched for in a more user friendly fashion.
     */
    static void getStops()
    {
        String delimiter = "[,]+";
        String [] token;
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
