import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BusSystemMain {

    private static routeGraph routeGraph = new routeGraph(8757);
    private static int[][] adjMatrix;
    private static TST<Integer> netTST = new TST<>();
    private static LinkedListCustom<Integer> stopIdList = new LinkedListCustom<>();
    private static LinkedListCustom<String>  placeNameList = new LinkedListCustom<>();
    private static LinkedListCustom<Integer> arrivalTimeList = new LinkedListCustom<>();
    private static String[][] stopInfo = new String[8757][9];
    private static dijkstra dij = new dijkstra();
    private static Integer[] stopArray;
    private static String[] placeArray;
    private static Integer[] timeArray;
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
                            case2();
                            break;

                        case 3:
                            case3();
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
         String stop;
         int ind1;
         int ind2;
         int stopInd;
         int[] route;
         boolean menu = false;

         while(!menu)
         {
             System.out.println("\nEnter menu to return to menu");
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
                 ind1 = bs.binarySearch(placeArray, 0, placeArray.length -1, s1);
                 if(ind1 == -1)
                 {
                     System.out.println("Sorry that stop does not exist, remember, prefix's nb, wb, sb, eb and flagstop" +
                             " are placed at the end of the stop");
                 }
                 else {
                     ind2 = placeNameList.getInd(ind1);//returns the index to retrieve data from stopInfo, ind will need to decremented

                     System.out.print("Enter Stop 2: ");
                     s2 = input.next();
                     if(s2.equals("menu"))
                     {
                         menu = true;
                         break;
                     }
                     ind1 = bs.binarySearch(placeArray, 0, placeArray.length -1, s2);
                     if(ind1 == -1)
                     {
                         System.out.println("Sorry that stop does not exist, remember, prefix's nb, wb, sb, eb and flagstop" +
                                 " are placed at the end of the stop");
                     }
                     else
                     {
                         ind1 = placeNameList.getInd(ind1);

                         dij.dijkstra(adjMatrix, ind2-2, 8757, ind1-2);
                         route = dij.getPath();
                         System.out.println(s1 + " to "+ s2 +"\nCost " + route[3]);
                         for(int i = 3; i < route.length; i++)
                         {
                             System.out.print(stopArray[route[i]]+" -> ");
                         }
                         System.out.println(" End");
                     }
                 }
             }

         }
     }
     public static void case2()
     {
         String s1;
         int ind;
         boolean isFound;
         boolean menu = false;
         while(!menu)
         {
             System.out.println("\nEnter menu to return to menu");
             System.out.print("The stop you want information on: ");
             if(input.hasNext())
             {
                 input.useDelimiter("\n");
                 s1 = input.next();
                 if(s1.equals("menu"))
                 {
                     menu = true;
                     break;
                 }
                 Iterable<String> queue = netTST.keysWithPrefix(s1);
                 isFound = false;
                 for(String s : queue)
                 {
                     isFound = true;
                    ind = bs.binarySearch(placeArray, 0, placeArray.length -1, s );
                    ind = placeNameList.getInd(ind);
                    for(int i = 0; i < 9; i++) {
                       System.out.println(stopInfo[ind - 1][i]);
                    }
                 }
                 if(!isFound)
                 {
                     System.out.print("No Station Found, remember, prefix's nb, wb, sb, eb and flagstop are placed at" +
                             " the end of the stop");
                 }
             }
         }
     }
     public static void case3()
     {
         String s1;
         String delimiter = (":");
         String[] token;
         String timeStr = "";
         Integer time;
         int ind;
         boolean isValid;
         int temp;
         boolean menu = false;
         while(!menu)
         {
             System.out.println("\nEnter menu to return to menu");
             System.out.print("Enter the time you wish to arrive at, in the format hh:mm:ss: ");
             if(input.hasNext())
             {
                 isValid = false;
                 input.useDelimiter("\n");
                 s1 = input.next();
                 if(s1.equals("menu"))
                 {
                     menu = true;
                     break;
                 }
                 token = s1.split(delimiter);
                 if(token.length == 3)
                 {
                     temp = Integer.parseInt(token[0]);
                     if(temp >= 0 && temp < 24)
                     {
                         temp = Integer.parseInt(token[1]);
                         if(temp >= 0 && temp <= 60)
                         {
                             temp = Integer.parseInt(token[2]);
                             if(temp >= 0 && temp <= 60)
                             {
                                 isValid = true;
                                 timeStr = token[0] + token[1] + token[2];
                             }
                         }
                     }
                 }
                 if(isValid)
                 {
                     time = Integer.parseInt(timeStr);
                     ind = bs.binarySearch(timeArray, 0, timeArray.length-1, time);
                     if(ind != -1) {


                         ind = arrivalTimeList.getInd(ind);

                         ind = bs.binarySearch(stopArray, 0, stopArray.length - 1, ind);
                         for (int i = 0; i < 9; i++) {
                             System.out.println(stopInfo[ind - 1][i]);
                         }
                     }
                     else{
                         System.out.print("No times found");
                     }
                 }
                 else{
                     System.out.print("Error: Date isn't valid");
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
        adjMatrix = routeGraph.unPackGraph(stopArray);

        arrivalTimeList = routeGraph.getTime();
        arrivalTimeList.sort(-1);
        timeArray = arrivalTimeList.getIntArray();
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
