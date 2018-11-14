package clustering;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * clase que representa un cluster con su centroide e instancias
 *
 * @author Frank
 *
 */
public class Cluster {

	private final Instancia	clusterVector;
	private ListaInstancias	instancias;
	private double			distancia;

	public Cluster(Instancia pInst) {
		clusterVector = pInst;
		instancias = new ListaInstancias();
	}

	/**
	 * añade una instancia al cluster
	 *
	 * @param pInstancia
	 */
	public void addInstancia(Instancia pInstancia) {
		this.instancias.add(pInstancia);
	}

	/**
	 * devuelve la distancia entre el centroide y el vestor pVector
	 *
	 * @param pVector
	 * @param tipoDistancia
	 * @return
	 */
	public double calcularDistancia(ArrayList<String> pVector, String tipoDistancia) {
		return distancia = this.clusterVector.getDistanceTo(pVector, tipoDistancia);
	}

	/**
	 * método que elimina el valor de la distancia de cluster->-1.0
	 */
	public void eliminarDistancia() {
		distancia = -1.0;
	}

	/**
	 * devuelve el centroide del cluster
	 *
	 * @return
	 */
	public ArrayList<String> getCentroide() {
		return this.clusterVector.getLista();
	}

	/**
	 * devuelve la distancia promedio entre una instancia y todas las instancias
	 * del cluster cohexion de un cluster
	 *
	 * @param pInstancia
	 * @param pTipoDistancia
	 * @return
	 */
	public double getDistanciaMedia(Instancia pInstancia, String pTipoDistancia) {

		double resultado = 0;
		int i = 0;
		Iterator<Instancia> it = instancias.getIterator();

		while (it.hasNext()) {

			Instancia act = it.next();
			if (pInstancia.getNumInst() != act.getNumInst()) {
				resultado = resultado + getDistancia(act, pInstancia, pTipoDistancia);
				i++;
			}
		}
		return resultado / (i - 1);
	}

	/**
	 * devuelve las instancias del cluster
	 *
	 * @return
	 */
	public ListaInstancias getInstancias() {
		return this.instancias;
	}

	/**
	 * devuelve el centroide en forma de Instancia
	 *
	 * @return
	 */
	public Instancia getVector() {
		return this.clusterVector;
	}

	/**
	 * escribe por pantalla el centroide del cluster
	 */
	public void printCentroide() {
		this.clusterVector.print();
	}

	/**
	 * escribe el centroide del cluster y el número de instancias
	 */
	public void printCluster() {
		System.out.println("***********************************");
		System.out.println("***Centroide del cluster***");
		System.out.println("***********************************");
		this.clusterVector.print();
		System.out.println("***********************************");
		System.out.println("***Instancias del cluster***" + this.instancias.getInstancias().size());
		System.out.println("***********************************");
		// this.instancias.print();
		// System.out.println("***********************************");
		// System.out.println("***********************************");
	}

	/**
	 * calcula el centroide del cluster en función de las instancias del mismo
	 */
	public void recalcularCentroide() {
		if (!this.instancias.getInstancias().isEmpty()) {
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
	}

	/**
	 * devuelve la distancia entre dos instancias
	 *
	 * @param inst1
	 * @param inst2
	 * @param tipoDistancia
	 * @return
	 */
	private double getDistancia(Instancia inst1, Instancia inst2, String tipoDistancia) {
		// metodo que devuelve distancia entre dos instancias
		return inst1.getDistanceTo(inst2.getLista(), tipoDistancia);
	}

	/**
	 * nos devuelve la media de los vectores sumaVectores(suma de todas las
	 * instancias del cluster)
	 *
	 * @param sumaVectores
	 * @param size
	 * @return
	 */
	private ArrayList<String> mediaVectores(ArrayList<String> sumaVectores, int size) {
		ArrayList<String> nuevo = new ArrayList<String>();
		Iterator<String> it = sumaVectores.iterator();
		while (it.hasNext()) {
			nuevo.add(Double.toString(Double.parseDouble(it.next()) / size));
		}
		return nuevo;
	}

	/**
	 * nos devuelve la suma de dos vectores
	 * 
	 * @param sumaVectores
	 * @param lista
	 * @return
	 */
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