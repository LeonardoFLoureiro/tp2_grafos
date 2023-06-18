import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Kmeans {
    private Graph graph;
    private int k;
    private List<Integer> centers;
    public List<Integer> getCenters() {
        return centers;
    }

    private List<List<Integer>> clusters;

    public Kmeans(Graph graph, int k) {
        this.graph = graph;
        this.k = k;
        centers = new ArrayList<>();
        clusters = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
    }

    public void solveKCenters() {
        initializeCenters();
        boolean converged = false;

        while (!converged) {
            assignVerticesToCenters();
            List<Integer> newCenters = calculateNewCenters();

            if (centers.equals(newCenters)) {
                converged = true;
            } else {
                centers = newCenters;
                clearClusters();
            }
        }
    }

    private void initializeCenters() {
        Random random = new Random();
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < graph.getVertices(); i++) {
            vertices.add(i);
        }
        for (int i = 0; i < k; i++) {
            int randomIndex = random.nextInt(vertices.size());
            int center = vertices.get(randomIndex);
            centers.add(center);
            vertices.remove(randomIndex);
        }
    }

    private void assignVerticesToCenters() {
        for (int vertex = 0; vertex < graph.getVertices(); vertex++) {
            int closestCenter = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int center : centers) {
                int distance = graph.findShortestDistance(vertex, center);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCenter = center;
                }
            }

            clusters.get(centers.indexOf(closestCenter)).add(vertex);
        }
    }

    private List<Integer> calculateNewCenters() {
        List<Integer> newCenters = new ArrayList<>();

        for (List<Integer> cluster : clusters) {
            int clusterSize = cluster.size();
            int bestCenter = -1;
            int minMaxDistance = Integer.MAX_VALUE;

            for (int vertex : cluster) {
                int maxDistance = 0;

                for (int otherVertex : cluster) {
                    if (vertex != otherVertex) {
                        int distance = graph.findShortestDistance(vertex, otherVertex);
                        if (distance > maxDistance) {
                            maxDistance = distance;
                        }
                    }
                }

                if (maxDistance < minMaxDistance) {
                    minMaxDistance = maxDistance;
                    bestCenter = vertex;
                }
            }

            newCenters.add(bestCenter);
        }

        return newCenters;
    }

    private void clearClusters() {
        for (List<Integer> cluster : clusters) {
            cluster.clear();
        }
    }

    public double calculateApproximateRadius() {
        double maxRadius = 0;

        for (int vertex = 0; vertex < graph.getVertices(); vertex++) {
            double vertexRadius = Double.POSITIVE_INFINITY;

            for (int center : centers) {
                int distance = graph.findShortestDistance(vertex, center);
                if (distance < vertexRadius) {
                    vertexRadius = distance;
                }
            }

            if (vertexRadius > maxRadius) {
                maxRadius = vertexRadius;
            }
        }

        return maxRadius;
    }
}
