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
	public double getDistanceTo(ArrayList<String> pVector, String pTipoDistancia) {

		if (pTipoDistancia == "minkowski") {
			// utilizaremos la distancia minkowski de grado 7.5
			double minkowski = 7.5;
			double sum = 0;
			Iterator<String> it1 = this.listaAtributos.iterator();
			Iterator<String> it2 = pVector.iterator();
			while (it1.hasNext() && it2.hasNext()) {
				sum = sum + Math.pow(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next()), minkowski);
			}
			double dist = Math.pow(sum, 1 / minkowski);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;

		} else if (pTipoDistancia == "euclidea") {
			// utilizaremos la distancia euclidea de grado 2
			double euclidea = 2.0;
			double sum = 0;
			Iterator<String> it1 = this.listaAtributos.iterator();
			Iterator<String> it2 = pVector.iterator();
			while (it1.hasNext() && it2.hasNext()) {
				sum = sum + Math.pow(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next()), euclidea);
			}
			double dist = Math.pow(sum, 1 / euclidea);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;
		} else {
			// utilizaremos la distancia manhattan de grado 1
			double manhattan = 1.0;
			double sum = 0;
			Iterator<String> it1 = this.listaAtributos.iterator();
			Iterator<String> it2 = pVector.iterator();
			while (it1.hasNext() && it2.hasNext()) {
				sum = sum + Math.pow(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next()), manhattan);
			}
			double dist = Math.pow(sum, 1 / manhattan);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;

		}
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