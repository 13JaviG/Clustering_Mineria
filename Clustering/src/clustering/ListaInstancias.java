package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * clase que representa una lista de instancias
 *
 * @author Frank
 *
 */
public class ListaInstancias {

	private ArrayList<Instancia> lista;

	public ListaInstancias() {
		lista = new ArrayList<Instancia>();
	}

	/**
	 * añade una istancia a la lista
	 *
	 * @param pInst
	 */
	public void add(Instancia pInst) {
		lista.add(pInst);
	}

	/**
	 * devuelve todas las instancias de lalista
	 *
	 * @return
	 */
	public ArrayList<Instancia> getInstancias() {
		return this.lista;
	}

	/**
	 * devuelve el iterador de la lista
	 *
	 * @return
	 */
	public Iterator<Instancia> getIterator() {
		return lista.iterator();
	}

	/**
	 * devuelve una instancia aleatoria
	 *
	 * @return
	 */
	public Instancia getRandomVector() {
		Random rand = new Random();
		System.out.println("size" + lista.size());
		int n = rand.nextInt(lista.size() - 1);
		Instancia aleatorio = new Instancia(n, lista.get(n).getLista());
		return aleatorio;
	}

	/**
	 * devuelve una instancia aleatoria por división
	 *
	 * @param k2
	 * @param i
	 * @return
	 */
	public Instancia getRandomVectorDivision(int k2, int i) {
		// k es el número de particiones(clusters)
		// i será la partición correspondiente al cluster
		if (k2 < lista.size()) {
			Random rand = new Random();
			int elemParticion = lista.size() / k2;
			int maxIndex = elemParticion * i - 1;
			int minIndex = maxIndex - elemParticion + 1;
			int n = rand.nextInt(maxIndex + 1) + minIndex;
			Instancia aleatorio = new Instancia(n, lista.get(n).getLista());

			return aleatorio;
		} else {
			System.out.println("Error al asignar centroides");
			return null;
		}
	}

	/**
	 * escribe todas las instancias de la lista
	 */
	public void print() {
		Iterator<Instancia> it = lista.iterator();
		while (it.hasNext()) {
			it.next().print();
		}

	}

	/**
	 * vacía todas las instancias de la lista
	 */
	public void vaciarLista() {
		lista = new ArrayList<Instancia>();
	}
}