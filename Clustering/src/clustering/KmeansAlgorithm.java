package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class KmeansAlgorithm {
	// número de clusters
	private final int					k;
	/**
	 * //completelink o singlelink-->singlelink
	 */
	private final String				distInter;
	/**
	 * //aleatorio o division o 2kclusters-->aleatorio
	 */
	private final String				inicializacion;
	private final int					iteraciones;
	private final double				umbral;
	private final ArrayList<Cluster>	resultado;

	/**
	 *
	 * @param pK
	 * @param pDist
	 *            //singlelink o completelink
	 * @param pInic
	 * @param pIter
	 * @param pUmbr
	 */
	public KmeansAlgorithm(int pK, String pDist, String pInic, int pIter, double pUmbr) {
		k = pK;
		distInter = pDist;
		inicializacion = pInic;
		iteraciones = pIter;
		resultado = new ArrayList<Cluster>();
		umbral = pUmbr;
	}

	public void asignarInstancia(Instancia pInst) {
		int i = 0;
		int pos = 0;
		double distancia = this.resultado.get(0).calcularDistancia(pInst.getLista());
		Iterator<Cluster> it = this.resultado.iterator();
		while (it.hasNext()) {
			Cluster nuevo = it.next();
			if (nuevo.calcularDistancia(pInst.getLista()) < distancia) {
				pos = i;
			}
			i++;
		}
		this.resultado.get(pos).addInstancia(pInst);
	}

	public void asignarInstanciasClusters() {

		Iterator<Instancia> it = DataBase.getDataBase().getInstancias().iterator();
		while (it.hasNext()) {
			Instancia inst = it.next();
			asignarInstancia(inst);
		}
	}
	
	public void asignarVector2kClusters() {
		// TODO Auto-generated method stub
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		while (i < (2*k)) {
			Cluster nuevo = new Cluster(getVectorAleatorio());
			if (!resultado.contains(nuevo)) {
				resultado.add(nuevo);
				nuevo.printCentroide();
				i++;
			}
		}
	}
	
	public ArrayList<Cluster> getKClustersMasAlejados(ArrayList<Cluster> pClusters){
		///////////////////////falta partir el metodo grande y seguir con el calculo
		///////////////////////los 2 primeros clusters hechos bien falta seguir en funcion de ellos
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		return null;
	}
	
	public ArrayList<Cluster> recalcular2KClusters(ArrayList<Cluster> pClusters){
		int filycol = pClusters.size();
		float [][] matriz = new float[filycol][filycol];
		int i = 0;
		int j = 0;
		ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
		Iterator<Cluster> it = pClusters.iterator();
		Iterator<Cluster> it2 = pClusters.iterator();
		while (it.hasNext()) {
			Cluster c = it.next();
			while (it2.hasNext()) {
				//if(matriz[i][j]!=null)
				float dis = (float) c.getVector().getDistanceTo(it2.next().getVector().getLista());
				matriz[i][j] = dis;
				//matriz[j][i] = dis;
				j = j+1;
			}
			i = i+1;
		}		
		i = j = 0;
		int idef = 0;
		int jdef = 0;
		float disdef = 0;
		while (i <= filycol){
			while (j <= filycol){
					if (matriz[i][j] > disdef){
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

	public void calcularKmeans() {
		DataBase.getDataBase().inicializarInstancias();
		asignarVectorClusters();
		asignarInstanciasClusters();
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("Valores iniciales clusters");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		this.resultado.get(0).printCentroide();
		this.resultado.get(0).printCluster();
		this.resultado.get(1).printCentroide();
		this.resultado.get(1).printCluster();
		int i = 0;
		while (i < iteraciones) {
			ArrayList<String> list1 = this.resultado.get(0).getCentroide();
			recalcularCetroides();
			asignarInstanciasClusters();
			double dist = this.resultado.get(0).calcularDistancia(list1);
			System.out.println("Distancia iteración No" + i + ":  " + dist);
			if (dist <= umbral) {
				break;
			}
			i++;
		}
		System.out.println("***********************************");
		System.out.println("***********************************");
		System.out.println("Instancias totales de la muestra: " + DataBase.getDataBase().getInstancias().size());
		Iterator<Cluster> it = this.resultado.iterator();
		while (it.hasNext()) {
			it.next().printCluster();
		}
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

	public double getSilhouette(Instancia pInstancia, Cluster pCluster) {
		double cohexion = pCluster.getCohexion(pInstancia);
		double separacion = this.getSeparacion(pCluster, pInstancia);
		if (cohexion >= separacion)
			return (separacion - cohexion) / cohexion;
		else
			return (separacion - cohexion) / separacion;
	}

	public double getSilhouetteAgrupamiento() {
		double shilhouette = 0;
		int i = 0;
		Iterator<Cluster> it = resultado.iterator();
		while (it.hasNext()) {
			Cluster c = it.next();
			Iterator<Instancia> it2 = c.getInstancias().getIterator();
			while (it2.hasNext()) {
				Instancia inst = it2.next();
				i = i + 1;
				shilhouette = shilhouette + this.getSilhouette(inst, c);
			}
		}
		return shilhouette / i;
	}

	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}

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

	private Cluster getClusterMasCercano(Cluster pCluster) {
		Iterator<Cluster> it = resultado.iterator();
		double min = Integer.MAX_VALUE;
		double dis = 0;
		Cluster resultado = null;
		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)) {
				dis = pCluster.getVector().getDistanceTo(x.getVector().getLista());
				if(dis < min){
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
				distancias.add(pCluster.getVector().getDistanceTo(x.getVector().getLista()));
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

	private double getSeparacion(Cluster pCluster, Instancia pInstancia) {
		int i = 0;
		double resultado = 0;
		Cluster masCercano = getClusterMasCercano(pCluster);
		Iterator<Instancia> it = masCercano.getInstancias().getIterator();
		while (it.hasNext()) {
			resultado = resultado + it.next().getDistanceTo(pInstancia.getLista());
			i = i + 1;
		}
		return resultado / i;
	}

	private Instancia getVectorAleatorioDivision(int k2, int i) {
		// TODO Auto-generated method stub
		return DataBase.getDataBase().getRandomVectorDivision(k2, i);
	}

	private void recalcularCetroides() {
		Iterator<Cluster> it = resultado.iterator();
		while (it.hasNext()) {
			it.next().recalcularCentroide();
		}

	}

}