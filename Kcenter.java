import java.util.ArrayList;
import java.util.List;

public class Kcenter {
    private Graph graph;

    public Kcenter(Graph graph) {
        this.graph = graph;
    }

    public List<Integer> findKCenters(int k) {
        List<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < graph.getVertices(); i++) {
            vertices.add(i);
        }

        List<Integer> centers = new ArrayList<>();
        findKCentersUtil(vertices, new ArrayList<>(), centers, k);
        return centers;
    }

    private void findKCentersUtil(List<Integer> vertices, List<Integer> currentCenters, List<Integer> centers, int k) {
        if (currentCenters.size() == k) {
            if (calculateMaxDistance(currentCenters) < calculateMaxDistance(centers)) {
                centers.clear();
                centers.addAll(currentCenters);
            }
            return;
        }

        for (int i = 0; i < vertices.size(); i++) {
            int vertex = vertices.get(i);
            currentCenters.add(vertex);
            vertices.remove(i);

            findKCentersUtil(vertices, currentCenters, centers, k);

            vertices.add(i, vertex);
            currentCenters.remove(currentCenters.size() - 1);
        }
    }

    public int calculateMaxDistance(List<Integer> centers) {
        int maxDistance = 0;
        for (int i = 0; i < graph.getVertices(); i++) {
            int minDistance = Integer.MAX_VALUE;
            for (int center : centers) {
                int distance = graph.findShortestDistance(i, center);
                minDistance = Math.min(minDistance, distance);
            }
            maxDistance = Math.max(maxDistance, minDistance);
        }
        return maxDistance;
    }
}