import java.util.*;

class Graph {
    private int vertices;
    public int getVertices() {
        return vertices;
    }

    private List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList.get(source).add(edge);

        // Since it's an undirected graph, we add an edge from destination to source as well
        edge = new Edge(destination, source, weight);
        adjacencyList.get(destination).add(edge);
    }

    public int findShortestDistance(int source, int destination) {
        PriorityQueue<Vertex> minHeap = new PriorityQueue<>(vertices, Comparator.comparingInt(v -> v.distance));
        boolean[] visited = new boolean[vertices];
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);

        minHeap.add(new Vertex(source, 0));
        distance[source] = 0;

        while (!minHeap.isEmpty()) {
            Vertex current = minHeap.poll();
            int vertex = current.vertex;
            visited[vertex] = true;

            List<Edge> edges = adjacencyList.get(vertex);
            for (Edge edge : edges) {
                int neighbor = edge.destination;
                int weight = edge.weight;
                if (!visited[neighbor] && distance[vertex] + weight < distance[neighbor]) {
                    distance[neighbor] = distance[vertex] + weight;
                    minHeap.add(new Vertex(neighbor, distance[neighbor]));
                }
            }
        }

        return distance[destination];
    }

    // Edge class representing an edge between two vertices
    private class Edge {
        private int source;
        private int destination;
        private int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Vertex class representing a vertex with its distance from the source vertex
    private class Vertex {
        private int vertex;
        private int distance;

        public Vertex(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}