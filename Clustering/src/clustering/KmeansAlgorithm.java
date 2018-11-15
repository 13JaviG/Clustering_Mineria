package clustering;

import java.util.ArrayList;
import java.util.Iterator;

import utilities.CommonUtilities;

public class KmeansAlgorithm {
	// número de clusters
	private final int					k;

	// aleatorio o division o 2kclusters-->aleatorio
	private final String				inicializacion;
	private final int					iteraciones;
	private final double				umbral;
	private final ArrayList<Cluster>	resultado;
	private final String				tipoDistancia;
	private double						silhouette;

	/**
	 * Constructora para la clase
	 *
	 * @param pK
	 * @param pDist
	 * @param pInic
	 * @param pIter
	 * @param pUmbr
	 */
	public KmeansAlgorithm(int pK, String pInic, int pIter, double pUmbr, String pTipoDist) {

		// Inicializaciones
		k = pK;
		inicializacion = pInic;
		iteraciones = pIter;
		resultado = new ArrayList<Cluster>();
		umbral = pUmbr;
		tipoDistancia = pTipoDist;
		silhouette = 0.00;
	}

	/**
	 * Asigna a una instancia el Clúster que esté más cerca
	 *
	 * @param pInst
	 */
	public void asignarInstancia(Instancia pInst) {

		// Inicializaciones del método
		int i = 0;
		int pos = 0;
		double distancia = this.resultado.get(0).calcularDistancia(pInst.getLista(), tipoDistancia);
		Iterator<Cluster> it = this.resultado.iterator();

		while (it.hasNext()) {
			Cluster nuevo = it.next();
			if (nuevo.calcularDistancia(pInst.getLista(), tipoDistancia) < distancia) {
				pos = i;
			}
			i++;
		}
		this.resultado.get(pos).addInstancia(pInst);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// MÉTODOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// PARA
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CALCULAR/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// EL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// K-MEANS////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Asigna todas las instancias a sus correspondientes Clústers más cercanos
	 *
	 */
	public void asignarInstanciasClusters() {

		Iterator<Instancia> it = DataBase.getDataBase().getInstancias().iterator();

		while (it.hasNext()) {
			Instancia inst = it.next();
			asignarInstancia(inst);
		}
	}

	/**
	 * Asigna los K clusters más alejados para el 2K
	 *
	 * @param pClusters
	 */
	public void asignarKClustersMasAlejados() {

		ArrayList<Cluster> pClusters = new ArrayList<Cluster>();
		while (pClusters.size() < k) {

			// Inicializaciones
			Iterator<Cluster> it = resultado.iterator();
			Iterator<Cluster> it2 = resultado.iterator();
			Iterator<Cluster> it3 = pClusters.iterator();
			int i = 0;
			int j = 0;
			int index = 0;
			double[][] matriz = new double[resultado.size()][resultado.size()];
			double dis = 0;

			while (it.hasNext()) {
				Cluster c = it.next();

				if (!pClusters.contains(c)) {

					while (it2.hasNext()) {

						Cluster c2 = it2.next();

						if (pClusters.contains(c2)) {

							ArrayList<Double> aDis = new ArrayList<Double>();

							while (it3.hasNext()) {

								Cluster c3 = it3.next();
								aDis.add(c.getVector().getDistanceTo(c3.getVector().getLista(), tipoDistancia));
							}

							matriz[i][j] = getMinimo(aDis);
						} else {
							matriz[i][j] = c.getVector().getDistanceTo(c2.getVector().getLista(), tipoDistancia);
						}
						j++;
					}
					i++;
				}
			}

			dis = matriz[0][0];
			i = j = 0;

			while (i < resultado.size()) {
				while (j < resultado.size()) {

					if (dis < matriz[i][j]) {
						dis = matriz[i][j];
						index = i;
					}
					j++;
				}
				i++;
			}
			pClusters.add(resultado.get(index));
		}
		reasignarResultado(pClusters);
	}

	// 2K CLUSTERING
	/**
	 * Asigna vectores iniciales en la variación 2k del K-Means
	 */
	public void asignarVector2kClusters() {
		// TODO Auto-generated method stub
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		while (i < 2 * k) {
			Cluster nuevo = new Cluster(getVectorAleatorio());
			if (!resultado.contains(nuevo)) {
				resultado.add(nuevo);
				i++;
			}
		}
		asignarKClustersMasAlejados();

	}

	/**
	 * Inicializa los centroides iniciales
	 */
	public void asignarVectorAleatorioClusters() {

		int i = 0;
		while (i < k) {

			Cluster nuevo = new Cluster(getVectorAleatorio());

			if (!resultado.contains(nuevo)) {

				resultado.add(nuevo);
				i++;
			}
		}
	}

	/**
	 * Asignamos un vector a un Cluster dependiendo del tipo de inicialización
	 */
	public void asignarVectorClusters() {
		switch (inicializacion) {
		case "aleatorio":
			asignarVectorAleatorioClusters();
			break;
		case "2kclusters":
			asignarVector2kClusters();
			break;
		default:
			System.out.println("Error al asignar clusters");
			break;
		}
	}

	/**
	 * Método principal para calcular el K-Means Clustering
	 */
	public void calcularKmeans() {
		System.out.println(" ");
		System.out.println("================================");
		System.out.println("Iniciando cálculo del k-means");
		System.out.println("================================");
		System.out.println(" ");
		// Inicializaciones de variables y asignaciones de clusters
		DataBase.getDataBase().inicializarInstancias();
		asignarVectorClusters();
		asignarInstanciasClusters();
		int i = 0;

		while (i < iteraciones) {
			int j = 0;
			Centroides oldCentroides = new Centroides();
			while (j < this.resultado.size()) {
				oldCentroides.add(this.resultado.get(j).getCentroide());
				j++;
			}
			recalcularCentroides();
			asignarInstanciasClusters();
			double dist = 0;
			int z = 0;
			while (z < this.resultado.size()) {
				dist = dist + this.resultado.get(z).calcularDistancia(oldCentroides.get(z), tipoDistancia);
				z++;
			}
			dist = dist / resultado.size();
			System.out.println("Distancia iteración No" + i + ":  " + dist);
			if (dist <= umbral) {
				break;
			}
			i++;
		}
		System.out.println(" ");
		System.out.println("================================");
		System.out.println("Terminado el cálculo del k-means");
		System.out.println("================================");
		System.out.println(" ");
	}

	/**
	 * Devuelve el valor mínimo de un arraylist de Doubles
	 *
	 * @param pArray
	 * @return double
	 */
	public double getMinimo(ArrayList<Double> pArray) {
		double min = Double.MAX_VALUE;

		for (Double tmp : pArray) {
			if (tmp < min) {
				min = tmp;
			}
		}

		return min;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// MÉTODOS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// PARA
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CALCULAR/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// EL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// SILHOUETTE/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void getResultados(String pathOut) {

		CommonUtilities.getResultados(pathOut, resultado, silhouette);
	}

	/**
	 * Método para calcular el Silhouette
	 *
	 * @param pInstancia
	 * @param pCluster
	 * @return double
	 */
	public double getSilhouette(Instancia pInstancia, Cluster pCluster) {

		double Silhouette = 0;
		// Calculamos la cohesión
		double cohexion = pCluster.getDistanciaMedia(pInstancia, tipoDistancia);

		// Calculamos la separación
		Cluster masCercano = getClusterMasCercano(pCluster);
		double separacion = masCercano.getDistanciaMedia(pInstancia, tipoDistancia);

		if (cohexion >= separacion) {
			Silhouette = (separacion - cohexion) / cohexion;
		} else {
			Silhouette = (separacion - cohexion) / separacion;
		}
		if (Double.isNaN(Silhouette)) {
			Silhouette = 0.0;
		}
		// Comparamos la cohesión y la separación
		return Silhouette;
	}

	/**
	 * Método principal para realizar el Silhouette
	 *
	 * @return double
	 */
	public double getSilhouetteAgrupamiento() {
		System.out.println(" ");
		System.out.println("==================================================");
		System.out.println("Iniciando cálculo del índice de calidad silhouette");
		System.out.println("==================================================");
		System.out.println(" ");
		// Inicializaciones del método
		double shilhouette = 0;
		int i = 0;
		@SuppressWarnings("unused")
		int iporCluster = 0;
		int ClusterID = 0;
		double sumaPorCluster = 0;

		Iterator<Cluster> it = resultado.iterator();

		while (it.hasNext()) {

			Cluster c = it.next();
			ClusterID++;
			iporCluster = 0;
			sumaPorCluster = 0;
			Iterator<Instancia> it2 = c.getInstancias().getIterator();

			while (it2.hasNext()) {

				Instancia inst = it2.next();
				i = i + 1;
				iporCluster++;
				double sh = this.getSilhouette(inst, c);
				shilhouette = shilhouette + sh;
				sumaPorCluster = sumaPorCluster + sh;
				this.getSilhouette(inst, c);
			}

			resultado.get(ClusterID - 1).setSil(sumaPorCluster / iporCluster);

		}
		System.out.println("======================================================");
		System.out.println("Termminado el cálculo del índice de calidad silhouette");
		System.out.println("======================================================");
		System.out.println(" ");
		this.silhouette = shilhouette / i;
		return shilhouette / i;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// MÉTODOS////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////// PARA
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// CALCULAR////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// LAS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// VARIACIONES///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// DEL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// K-MEANS/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método para obtener un Vector Aleatorio
	 *
	 * @return
	 */
	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}

	/**
	 * Elimina los clusters de resultado que no estén en pClusters
	 *
	 * @param pClusters
	 */
	public void reasignarResultado(ArrayList<Cluster> pClusters) {

		for (int i = 0; i < resultado.size(); i++) {
			if (!pClusters.contains(resultado.get(i))) {
				resultado.remove(resultado.get(i));
			}
		}
	}

	/**
	 * Halla el clúster más cercano a pCluster
	 *
	 * @param pCluster
	 * @return
	 */
	private Cluster getClusterMasCercano(Cluster pCluster) {

		Iterator<Cluster> it = resultado.iterator();
		double min = Integer.MAX_VALUE;
		double dis = 0;
		Cluster resultado = null;

		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)) {
				dis = pCluster.getVector().getDistanceTo(x.getVector().getLista(), tipoDistancia);
				if (dis < min) {
					resultado = x;
					min = dis;
				}
			}
		}

		return resultado;
	}

	/**
	 * Imprime los datos de los clusters por pantalla
	 */
	private void imprimirClusters() {

		Iterator<Cluster> it = resultado.iterator();
		Cluster act;
		while (it.hasNext()) {
			act = it.next();
			act.printCentroide();
			act.printCluster();
		}
	}

	/**
	 * Recalcula los centroides para la siguiente iteración.
	 */
	private void recalcularCentroides() {
		Iterator<Cluster> it = resultado.iterator();
		while (it.hasNext()) {
			it.next().recalcularCentroide();
		}

	}

}