package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class KmeansAlgorithm {
	// número de clusters
	private final int					k;

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
	public KmeansAlgorithm(int pK, String pInic, int pIter, double pUmbr, String pTipoDist) {

		// Inicializaciones
		k = pK;
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

	public void asignarKClustersMasAlejados(ArrayList<Cluster> pClusters) {
		while (pClusters.size() < k) {
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
		asignarKClustersMasAlejados(recalcular2KClusters());

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

		// Inicializaciones de variables y asignaciones de clusters
		DataBase.getDataBase().inicializarInstancias();
		asignarVectorClusters();
		asignarInstanciasClusters();
		int i = 0;

		System.out.println("===========================================");
		System.out.println("Iniciando el cáculo del clústering k-means.");
		System.out.println("===========================================");
		System.out.println("	");

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

		System.out.println("=================================================");
		System.out.println("Se ha terminado el cáculo del clústering k-means.");
		System.out.println("=================================================");
		System.out.println("	");
	}

	public double getMinimo(ArrayList<Double> pArray) {
		double min = Double.MAX_VALUE;

		for (Double tmp : pArray) {
			if (tmp < min) {
				min = tmp;
			}
		}

		return min;
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
		double sumaPorCluster = 0;
		Iterator<Cluster> it = resultado.iterator();

		System.out.println("==============================================");
		System.out.println("Iniciando el cálculo de los índices silhouette");
		System.out.println("==============================================");
		System.out.println("	");

		while (it.hasNext()) {
			Cluster c = it.next();
			sumaPorCluster = 0;
			Iterator<Instancia> it2 = c.getInstancias().getIterator();
			while (it2.hasNext()) {
				Instancia inst = it2.next();
				i = i + 1;
				double sh = this.getSilhouette(inst, c);
				shilhouette = shilhouette + sh;
				sumaPorCluster = sumaPorCluster + sh;
				this.getSilhouette(inst, c);
			}

			System.out.println("======================================================");
			System.out.println("Se ha terminado de calcular los índices de silhouette.");
			System.out.println("======================================================");
			System.out.println("	");

		}

		return shilhouette / i;
	}

	/**
	 * Método para obtener un Vector Aleatorio
	 *
	 * @return
	 */
	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}

	public void reasignarResultado(ArrayList<Cluster> pClusters) {
		/*
		 * for(Cluster tmp : resultado){ if(!pClusters.contains(tmp)){
		 * resultado.remove(tmp); } }
		 */
		for (int i = 0; i < resultado.size(); i++) {
			if (!pClusters.contains(resultado.get(i))) {
				resultado.remove(resultado.get(i));
			}
		}
	}

	/**
	 * Recalcula los Clusters en la variación 2K del K-Means
	 *
	 * @param pClusters
	 * @return
	 */
	public ArrayList<Cluster> recalcular2KClusters() {

		int filycol = resultado.size();
		double[][] matriz = new double[filycol][filycol];
		int i = 0;
		int j = 0;
		ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
		Iterator<Cluster> it = resultado.iterator();

		while (it.hasNext()) {

			Cluster c = it.next();
			Iterator<Cluster> it2 = resultado.iterator();
			while (it2.hasNext()) {
				Cluster c2 = it2.next();
				// if(matriz[i][j]!=null)
				double dis = c.getVector().getDistanceTo(c2.getVector().getLista(), tipoDistancia);
				matriz[i][j] = dis;
				// matriz[j][i] = dis;
				j = j + 1;
			}
			i = i + 1;
			j = 0;
		}

		i = j = 0;
		int idef = 0;
		int jdef = 0;
		double disdef = Double.MIN_VALUE;

		while (i < filycol) {
			while (j < filycol) {

				if (matriz[i][j] > disdef) {
					idef = i;
					jdef = j;
					disdef = matriz[i][j];
				}
				j++;
			}
			i++;
		}

		Clusters.add(resultado.get(idef));
		Clusters.add(resultado.get(jdef));
		return Clusters;
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