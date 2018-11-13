package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {
		kmeans = new KmeansAlgorithm(2, "singlelink", "aleatorio", 100, 0.00000000000000, "euclidea");
		kmeans.calcularKmeans();
		System.out.println("////////////////////////////////////////////////////////////////////////////");
		System.out.println("Shilhouette del agrupamiento total :" + kmeans.getSilhouetteAgrupamiento());

	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}