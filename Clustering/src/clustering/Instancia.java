package clustering;

import java.util.ArrayList;
import java.util.Iterator;;

public class Instancia {

	private ArrayList<String>	listaAtributos;
	private final int			numInst;

	/**
	 *
	 * @param pNumInst
	 * @param plistaAtr
	 */
	public Instancia(int pNumInst, ArrayList<String> plistaAtr) {
		listaAtributos = new ArrayList<String>();
		listaAtributos = plistaAtr;
		numInst = pNumInst;
	}

	/**
	 *
	 * @param pVector
	 */
	public double getDistanceTo(ArrayList<String> pVector) {
		// utilizaremos la distancia minkowski de grado 2
		double sum = 0;
		Iterator<String> it1 = this.listaAtributos.iterator();
		Iterator<String> it2 = pVector.iterator();
		while (it1.hasNext() && it2.hasNext()) {
			sum = sum + Math.pow(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next()), 2);
		}
		return Math.pow(sum, 0.5);
	}

	public ArrayList<String> getLista() {
		return listaAtributos;
	}

	public int getNumInst() {
		return this.numInst;
	}

	public void print() {
		System.out.println(numInst + "- " + listaAtributos);
	}

	public void setLista(ArrayList<String> lista) {
		listaAtributos = lista;
	}

}