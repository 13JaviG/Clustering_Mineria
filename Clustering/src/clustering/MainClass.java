package clustering;

public class MainClass {

	@SuppressWarnings("unused")
	private long time;
	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {
		
	}

	public void ejecutar(int clusters, String init, int it, float conv, String dist, String res) {

		kmeans = new KmeansAlgorithm(clusters, init, it, conv, dist);
		long start = System.currentTimeMillis();
		kmeans.calcularKmeans();
		long stop = System.currentTimeMillis();
		this.time= stop-start;
		kmeans.getSilhouetteAgrupamiento();
		kmeans.getResultados(res);

	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

	public long getTime() {
		return time;
	}
	
	

}