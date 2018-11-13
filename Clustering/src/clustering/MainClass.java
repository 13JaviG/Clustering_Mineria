package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {
		kmeans = new KmeansAlgorithm(42, "singlelink", "aleatorio", 25, 0.00000000000000, "minkowski");
		kmeans.calcularKmeans();
		System.out.println("Indicie de calidad interna Shilhouette :" + kmeans.getSilhouetteAgrupamiento());

	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}