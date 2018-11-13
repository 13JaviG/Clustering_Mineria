package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class KmeansAlgorithm {
	// número de clusters
	private final int					k;

	// completelink o singlelink-->singlelink
	private final String				distInter;

	// aleatorio o division o 2kclusters-->aleatorio
	private final String				inicializacion;
	private final int					iteraciones;
	private final double				umbral;
	private final ArrayList<Cluster>	resultado;
	private final String				tipoDistancia;

	/**
	 * Constructora para la clase
	 *
	 * @param pK
	 * @param pDist
	 *            //singlelink o completelink
	 * @param pInic
	 * @param pIter
	 * @param pUmbr
	 */
	public KmeansAlgorithm(int pK, String pDist, String pInic, int pIter, double pUmbr, String pTipoDist) {

		// Inicializaciones
		k = pK;
		distInter = pDist;
		inicializacion = pInic;
		iteraciones = pIter;
		resultado = new ArrayList<Cluster>();
		umbral = pUmbr;
		tipoDistancia = pTipoDist;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// MÉTODOS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	/////////////////////////////// PARA CALCULAR
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	/////////////////////////////// EL K-MEANS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
				nuevo.printCentroide();
				i++;
			}
		}
	}

	/**
	 * Inicializa los centroides iniciales
	 */
	public void asignarVectorAleatorioClusters() {

		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");

		while (i < k) {

			Cluster nuevo = new Cluster(getVectorAleatorio());

			if (!resultado.contains(nuevo)) {

				resultado.add(nuevo);
				nuevo.printCentroide();
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
		case "division":
			asignarVectorDivisionClusters();
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

		// Inicializaciones de variables y asignaciones de clusters
		DataBase.getDataBase().inicializarInstancias();
		asignarVectorClusters();
		asignarInstanciasClusters();
		int i = 0;

		// Imprimimos por pantalla los valores iniciales seleccionados
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("Valores iniciales clusters");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		imprimirClusters();

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

		// Imprimimos los datos de los Clusters
		System.out.println("***********************************");
		System.out.println("***********************************");
		System.out.println("Instancias totales de la muestra: " + DataBase.getDataBase().getInstancias().size());
		imprimirClusters();
	}

	public void compararClustersInstancias() {
		// TODO - implement KmeansAlgorithm.compararClustersInstancias
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param pK
	 */
	public void crearClusters(int pK) {
		// TODO - implement KmeansAlgorithm.crearClusters
		throw new UnsupportedOperationException();
	}

	public ArrayList<Cluster> getKClustersMasAlejados(ArrayList<Cluster> pClusters) {
		/////////////////////// falta partir el metodo grande y seguir con el
		/////////////////////// calculo
		/////////////////////// los 2 primeros clusters hechos bien falta seguir
		/////////////////////// en funcion de ellos
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		return null;
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

		// Inicializaciones del método
		double shilhouette = 0;
		int i = 0;
		@SuppressWarnings("unused")
		int iporCluster = 0;
		int ClusterID = 0;
		double sumaPorCluster = 0;
		Iterator<Cluster> it = resultado.iterator();
		
		System.out.println("////////////////////////////////////////////////////////////////////");
		System.out.println("El Sillhouette es un indice de calidad interna que varia de -1 a 1");
		System.out.println("Siendo -1 una mala clasificación y 1 una buena clasificación");
		System.out.println("////////////////////////////////////////////////////////////////////");
		
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
			
			System.out.println("Cluster " + ClusterID + " :");
			System.out.println("	Indice Sillhouette : " + sumaPorCluster/iporCluster);
			System.out.println("	Número de Instancias : " + iporCluster);
			System.out.println();

		}

		return shilhouette / i;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// MÉTODOS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	////////////////////////////// PARA CALCULAR
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	///////////////////////////// EL SILHOUETTE
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método para obtener un Vector Aleatorio
	 *
	 * @return
	 */
	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}

	/**
	 * Recalcula los Clusters en la variación 2K del K-Means
	 *
	 * @param pClusters
	 * @return
	 */
	public ArrayList<Cluster> recalcular2KClusters(ArrayList<Cluster> pClusters) {

		int filycol = pClusters.size();
		float[][] matriz = new float[filycol][filycol];
		int i = 0;
		int j = 0;
		ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
		Iterator<Cluster> it = pClusters.iterator();
		Iterator<Cluster> it2 = pClusters.iterator();

		while (it.hasNext()) {

			Cluster c = it.next();

			while (it2.hasNext()) {

				// if(matriz[i][j]!=null)
				float dis = (float) c.getVector().getDistanceTo(it2.next().getVector().getLista(), tipoDistancia);
				matriz[i][j] = dis;
				// matriz[j][i] = dis;
				j = j + 1;
			}
			i = i + 1;
		}

		i = j = 0;
		int idef = 0;
		int jdef = 0;
		float disdef = 0;

		while (i <= filycol) {
			while (j <= filycol) {

				if (matriz[i][j] > disdef) {
					idef = i;
					jdef = j;
					disdef = matriz[i][j];
				}
				j++;
			}
			i++;
		}

		Clusters.add(pClusters.get(idef));
		Clusters.add(pClusters.get(jdef));
		return Clusters;
	}

	/**
	 * Asigna vectores en la variación de División del K-Means
	 */
	private void asignarVectorDivisionClusters() {
		// vamos a dividir las instancias en tantos grupos como clusters haya
		// el orden de la división será segun viene en el .arff
		// y escogeremos un vector aleatorio de cada grupo y se lo asignaremos a
		// cada cluster
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		while (i < k) {
			Cluster nuevo = new Cluster(getVectorAleatorioDivision(k, i));
			if (!resultado.contains(nuevo)) {
				resultado.add(nuevo);
				nuevo.printCentroide();
				i++;
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////// MÉTODOS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	/////////////////////////////// PARA CALCULAR
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	/////////////////////////////// LAS VARIACIONES
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////
	/////////////////////////////// DEL K-MEANS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	private Cluster getClusterMasDistante(Cluster pCluster) {
		Iterator<Cluster> it = resultado.iterator();
		ArrayList<Double> distancias = new ArrayList<Double>();
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		Cluster resultado = null;
		int i = 0;
		Double comp;
		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)) {
				distancias.add(pCluster.getVector().getDistanceTo(x.getVector().getLista(), tipoDistancia));
				clusters.add(x);
			}
		}
		comp = distancias.get(0);
		for (Double dis : distancias) {
			if (comp <= dis) {
				comp = dis;
				resultado = clusters.get(i);
			}
			i = i + 1;
		}
		return resultado;
	}

	/**
	 * Devuelve un vector aleatorio para la variación de División del K-Means
	 *
	 * @param k2
	 * @param i
	 * @return
	 */
	private Instancia getVectorAleatorioDivision(int k2, int i) {
		// TODO Auto-generated method stub
		return DataBase.getDataBase().getRandomVectorDivision(k2, i);
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