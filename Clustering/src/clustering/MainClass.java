package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar(int clusters, String init, int it, float conv, String dist, String res) {

		kmeans = new KmeansAlgorithm(clusters, init, it, conv, dist);
		kmeans.calcularKmeans();
		kmeans.getSilhouetteAgrupamiento();
		kmeans.getResultados(res);

	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}