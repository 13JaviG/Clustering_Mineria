package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class Cluster {

	private final Instancia	clusterVector;
	private ListaInstancias	instancias;
	private double			distancia;

	public Cluster(Instancia pInst) {
		clusterVector = pInst;
		instancias = new ListaInstancias();
	}

	/**
	 *
	 * @param pInstancia
	 */
	public void addInstancia(Instancia pInstancia) {
		this.instancias.add(pInstancia);
	}

	public double calcularDistancia(ArrayList<String> pVector) {
		return distancia = this.clusterVector.getDistanceTo(pVector);
	}

	/**
	 *
	 * @param pInstancia
	 */
	public void deleteInstancia(Instancia pInstancia) {
		// TODO - implement Cluster.deleteInstancia
		throw new UnsupportedOperationException();
	}

	public void eliminarDistancia() {
		distancia = -1.0;
	}

	public ArrayList<String> getCentroide() {
		return this.clusterVector.getLista();
	}

	public Instancia getPuntoMedio() {
		// TODO - implement Cluster.getPuntoMedio
		throw new UnsupportedOperationException();
	}

	public void printCentroide() {
		this.clusterVector.print();
	}

	public void printCluster() {
		System.out.println("***********************************");
		System.out.println("***Centroide del cluster***");
		System.out.println("***********************************");
		this.clusterVector.print();
		System.out.println("***********************************");
		System.out.println("***Instancias del cluster***" + this.instancias.getInstancias().size());
		System.out.println("***********************************");
		this.instancias.print();
		System.out.println("***********************************");
		System.out.println("***********************************");
	}

	public void recalcularCentroide() {
		// recalcula el centroide haciendo el vector medio
		ArrayList<String> sumaVectores = null;
		this.instancias.getInstancias().get(0).getLista();
		Iterator<Instancia> it = this.instancias.getInstancias().iterator();
		while (it.hasNext()) {
			if (sumaVectores == null) {
				sumaVectores = it.next().getLista();
			} else {
				sumaVectores = sumarVectores(sumaVectores, it.next().getLista());
			}
		}
		// dividimos cada valor entre el total de instancias en el cluster
		clusterVector.setLista(mediaVectores(sumaVectores, this.instancias.getInstancias().size()));
		// volvemos a vaciar las instancias del vector
		this.instancias = new ListaInstancias();
	}

	private ArrayList<String> mediaVectores(ArrayList<String> sumaVectores, int size) {
		ArrayList<String> nuevo = new ArrayList<String>();
		Iterator<String> it = sumaVectores.iterator();
		while (it.hasNext()) {
			nuevo.add(Double.toString(Double.parseDouble(it.next()) / size));
		}
		return nuevo;
	}

	private ArrayList<String> sumarVectores(ArrayList<String> sumaVectores, ArrayList<String> lista) {
		ArrayList<String> nuevo = new ArrayList<String>();
		Iterator<String> it1 = sumaVectores.iterator();
		Iterator<String> it2 = lista.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			double value = Double.parseDouble(it1.next()) + Double.parseDouble(it2.next());
			nuevo.add(Double.toString(value));
		}
		return nuevo;
	}

}