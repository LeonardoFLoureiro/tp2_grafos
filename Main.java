import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int escolha;

        System.out.println("1 para testar o metodo exato\n2 para o aproximado");
        Scanner sc = new Scanner(System.in);
        escolha = Integer.parseInt(sc.nextLine());

        switch (escolha) {
            case 1:
                for (int i = 1; i <= 5; i++) {
                    String file = "exato/Exato" + i + ".txt";
                    GraphFromFile gfl = new GraphFromFile();
                    Graph graph = gfl.read_graph_file(file);
                    System.out.println("    ARQUIVO :   " + file);
                    kcenter_exato(graph, gfl);

                }
                break;

            case 2:
                for (int i = 30; i <= 40; i++) {
                    String file = "pmed/pmed" + i + ".txt";
                    GraphFromFile gfl = new GraphFromFile();
                    Graph graph = gfl.read_graph_file(file);
                    System.out.println("    ARQUIVO :   " + file);
                    kcenter_kmeansplus(graph, gfl);

                }
                break;

            default:
                break;
        }

        

    }

    public static void kcenter_exato(Graph graph, GraphFromFile gfl) {
        Kcenter solver = new Kcenter(graph);
        long startTime = System.currentTimeMillis();
        List<Integer> centers = solver.findKCenters(gfl.getK());
        long finalTime = System.currentTimeMillis();
        System.out.println("TEMPO: " + (finalTime - startTime) + " ms");
        int radius = solver.calculateMaxDistance(centers);

        /*
         * System.out.println("Centros:");
         * for (int center : centers) {
         * System.out.println(center+1);
         * }
         */

        System.out.println("Raio: " + radius);

    }

    public static void kcenter_kmeans(Graph graph, GraphFromFile gfl) {
        Kmeans solver = new Kmeans(graph, gfl.getK());
        long startTime = System.currentTimeMillis();
        solver.solveKCenters();
        long finalTime = System.currentTimeMillis();
        System.out.println("TEMPO: " + (finalTime - startTime) + " ms");
        List<Integer> centers = solver.getCenters();
        double radius = solver.calculateApproximateRadius();
        /*
         * System.out.println("Centros:");
         * for (int center : centers) {
         * System.out.println(center+1);
         * }
         */

        System.out.println("Raio: " + radius);

    }

    public static void kcenter_kmeansplus(Graph graph, GraphFromFile gfl) {
        KmeansPlus solver = new KmeansPlus(graph, gfl.getK());
        long startTime = System.currentTimeMillis();
        solver.solveKCenters();
        long finalTime = System.currentTimeMillis();
        System.out.println("TEMPO: " + (finalTime - startTime) + " ms");
        List<Integer> centers = solver.getCenters();
        double radius = solver.calculateApproximateRadius();
        /*
         * System.out.println("Centros:");
         * for (int center : centers) {
         * // System.out.println(center+1);
         * }
         */

        System.out.println("Raio: " + radius);

    }
}
