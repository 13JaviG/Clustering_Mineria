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
	
	public int getNumInstancia(int i){
		return lista.get(i).getNumInst();
	}
	
	public String atrInstancia(int i){
		return lista.get(i).atrInstancia();
	}
	
	public int size(){
		return lista.size();
	}
}