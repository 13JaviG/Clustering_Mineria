package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {
		kmeans = new KmeansAlgorithm(2, "singlelink", "2kclusters", 100, 0.0, "minkowski");
		kmeans.calcularKmeans();
		System.out.println("////////////////////////////////////////////////////////////////////////////");
		System.out.println("Shilhouette del agrupamiento total :" + kmeans.getSilhouetteAgrupamiento());
		kmeans.getResultados("C:/Users/Javi/git/Clustering_Mineria/Clustering/src/arffFiles/resultados");
		
	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}