package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class KmeansAlgorithm {

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
	private double						umbral;
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

			i++;
		}
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

	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}

	private void asignarVectorDivisionClusters() {
		// TODO Auto-generated method stub

	}

	private void recalcularCetroides() {
		Iterator<Cluster> it = resultado.iterator();
		while (it.hasNext()) {
			it.next().recalcularCentroide();
		}

	}
	
	public double getSilhouetteAgrupamiento(){
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
		return (shilhouette/i);
	}
	
	public double getSilhouette(Instancia pInstancia, Cluster pCluster) {
		double cohexion = pCluster.getCohexion(pInstancia);
		double separacion = this.getSeparacion(pCluster, pInstancia);
		if(cohexion >= separacion){
			return ((separacion - cohexion)/cohexion);
		}else {return ((separacion - cohexion)/separacion);} 
	}
	
	private double getSeparacion (Cluster pCluster, Instancia pInstancia){
		int i = 0;
		double resultado = 0;
		Cluster masCercano = getClusterMasCercano(pCluster);
		Iterator<Instancia> it = masCercano.getInstancias().getIterator();
		while (it.hasNext()) {
			resultado = resultado + it.next().getDistanceTo(pInstancia.getLista());
			i=i+1;
		}		
		return resultado/i;	
	}
	
	private Cluster getClusterMasCercano (Cluster pCluster){
		Iterator<Cluster> it = resultado.iterator();
		ArrayList<Double> distancias = new ArrayList<Double>();
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		Cluster resultado = null;
		int i = 0;
		Double comp;
		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)){
				distancias.add(pCluster.getVector().getDistanceTo(x.getVector().getLista()));
				clusters.add(x);
			}
		}
		comp = distancias.get(0);
		for(Double dis : distancias){
			if (comp >= dis){
				comp=dis;
				resultado = clusters.get(i);
			}
			i=i+1;
		}
		return resultado;
	}

}