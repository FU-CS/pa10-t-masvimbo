/*
 * This source file was generated by the Gradle 'init' task
 */

package pa10;
/** 
 * Graph interface for a directed graph with vertices numbered from 0 to n-1.
 */
import java.util.*;
interface Graph {

    /**
     *  Add an edge between two vertices.
     *  @param v vertex 1 (0-indexed)
     *  @param w vertex 2 (0-indexed)
     * 
     */
    public void addEdge(int v, int w);

    /**
     * Topological sort of the graph, using DFS.
     * 
     * @return an array of vertices in topological order
     * 
     */
    public String topologicalSort();

    /**
     * Topological sort of the graph, using Kahn's algorithm.
     * 
     * @return an array of vertices in topological order
     * 
     */
    public String kahn();

}

    class DirectedGraph implements Graph{
        private int vertices;
        private List<List<Edge>> adjacencyList;

        public DirectedGraph(int vertices){
            this.vertices = vertices;
            this.adjacencyList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i ++){
                adjacencyList.add(new ArrayList<>());
            }
        }
        private static class Edge implements Comparable <Edge>{
            
            int source;
            int destination;
            int weight;
            
            public Edge(int source, int destination,int weight){
                this.source = source;
                this.destination = destination;
                this.weight = weight;  
            }
            @Override
            public int compareTo(Edge other){
                return Integer.compare(this.weight, other.weight);
            }
        }
        public void addEdge(int v, int w){
            adjacencyList.get(v).add(new Edge(v, w,1));
        }
        public String topologicalSort(){
            boolean [] visited = new boolean [vertices];
            Stack<Integer> stack = new Stack<>();

            for (int i = 0; i < vertices; i ++){
                if (!visited[i]){
                    dfs(i, visited, stack);
                }
            }
            String result = "";

            while (!stack.isEmpty()){
                result = result + stack.pop() + " ";
            }
            return result.trim();
        }
        public void dfs(int vertex, boolean[] visited, Stack<Integer> stack){
            visited[vertex] = true;
            for (Edge edge: adjacencyList.get(vertex)){
                if (!visited[edge.destination]){
                    dfs(edge.destination, visited, stack);
                }
            }
            stack.push(vertex);
        }
        public String kahn(){
            int [] indegree = new int[vertices];
            List <Integer> sorted = new ArrayList<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < vertices; i ++) {
                for(Edge edge: adjacencyList.get(i)){
                    indegree[edge.destination]++;
                }
            }
            for(int i = 0; i < vertices; i ++){
                if(indegree[i]==0)
                    queue.add(i);    
            }

            while (!queue.isEmpty()){
                int u = queue.poll();
                sorted.add(u);

                for(Edge edge: adjacencyList.get(u)){
                    indegree[edge.destination]--;
                    if(indegree[edge.destination] == 0)
                        queue.add(edge.destination);
                }
            }
            if (sorted.size() == vertices){
                String result = " ";
                for(int vertex: sorted){
                    result = result + vertex + " ";
                }
                return result.trim();
            }
            else
                return "Graph has cycle";
        }
    public class Main{
        public static void main(String[] args) {
            DirectedGraph graph = new DirectedGraph(6);

            graph.addEdge(5,2);
            graph.addEdge(5, 0);
            graph.addEdge(4, 0);
            graph.addEdge(4, 1);
            graph.addEdge(2, 3);
            graph.addEdge(3, 1);

            String dfsResult = graph.topologicalSort();
            System.out.println("Topological Sort (DFS): " + dfsResult);

            String kahnResult = graph.kahn();
            System.out.println("Topological Sort (Kahn's Algorithm): " + kahnResult);
        }
        
    }
    
}