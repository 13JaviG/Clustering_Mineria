package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {
		kmeans = new KmeansAlgorithm(5, "singlelink", "aleatorio", 100, 0.0, "manhattan");
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