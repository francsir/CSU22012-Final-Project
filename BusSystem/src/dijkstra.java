import java.util.LinkedList;

public class dijkstra {
    public static LinkedList<Integer> path = new LinkedList<>();
    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation

    public int[] getPath()
    {
        int [] pathArray = new int[path.size()];
        for(int i = 0; i < pathArray.length; i++)
        {
            pathArray[i] = path.get(i);
        }
        return pathArray;
    }
    void dijkstra(int graph[][], int src, int V, int dest) {
        int[] dist = new int[V]; // The output array. dist[i] will hold
        int[] parents = new int[V]; //Parent array to store shortest path tree


        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // The starting vertex does not
        // have a parent
        parents[src] = -1;
        // Distance of source vertex from itself is always 0
        dist[src] = 0;
        // Find shortest path for all vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet, V);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    parents[v] = u;
                    dist[v] = dist[u] + graph[u][v];
                }

        }
        findPath(src, dist, parents, dest);
    }
    int minDistance(int[] dist, Boolean[] sptSet, int V)
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }
    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static void findPath(int startVertex, int[] distances, int[] parents, int dest)
    {
        path.add(startVertex);
        path.add(dest);
        path.add(distances[dest]);
        findPath(dest, parents);
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void findPath(int currentVertex,
                                  int[] parents)
    {
        // Base case : Source node has
        // been processed
        if (currentVertex == -1)
        {
            return;
        }
        findPath(parents[currentVertex], parents);
        path.add(currentVertex);
    }

}
