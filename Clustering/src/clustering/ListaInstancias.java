package clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ListaInstancias {

	private ArrayList<Instancia> lista;

	public ListaInstancias() {
		lista = new ArrayList<Instancia>();
	}

	/**
	 *
	 * @param pInst
	 */
	public void add(Instancia pInst) {
		lista.add(pInst);
	}

	/**
	 *
	 * @param pPos
	 */
	public void getInstancia(int pPos) {
		// TODO - implement ListaInstancias.getInstancia
		throw new UnsupportedOperationException();
	}

	public ArrayList<Instancia> getInstancias() {
		return this.lista;
	}

	public Instancia getRandomVector() {
		Random rand = new Random();
		int n = rand.nextInt(lista.size() - 1);
		Instancia aleatorio = new Instancia(n, lista.get(n).getLista());
		return aleatorio;
	}

	public void print() {
		Iterator<Instancia> it = lista.iterator();
		while (it.hasNext()) {
			it.next().print();
		}

	}

	public void vaciarLista() {
		lista = new ArrayList<Instancia>();
	}
	
	public Iterator<Instancia> getIterator(){
		return lista.iterator();
	}
}