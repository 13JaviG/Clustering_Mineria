package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {

		kmeans = new KmeansAlgorithm(2, "aleatorio", 100, 0.0, "euclidea");
		kmeans.calcularKmeans();
		kmeans.getSilhouetteAgrupamiento();
		kmeans.getResultados("resultados");

	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}