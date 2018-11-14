package clustering;

import java.util.ArrayList;
import java.util.Iterator;;

/**
 * clase que representa una istancia en el sistema
 *
 * @author Frank
 *
 */

public class Instancia {

	private ArrayList<String>	listaAtributos;
	private final int			numInst;

	/**
	 * constructora de la clase -> pNumInst=posición de la instancia en el
	 * fichero pListaAtr=valor de los atributos de la instancia
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
	 * devuelve la distancia de la instancia con otra(pVector), según el tipo de
	 * distancia
	 *
	 * @param pVector
	 * @param pTipoDistancia
	 */
	public double getDistanceTo(ArrayList<String> pVector, String pTipoDistancia) {

		if (pTipoDistancia == "minkowski") {
			// utilizaremos la distancia minkowski de grado 7.5
			double minkowski = 7.5;
			double sum = 0;
			Iterator<String> it1 = this.listaAtributos.iterator();
			Iterator<String> it2 = pVector.iterator();
			while (it1.hasNext() && it2.hasNext()) {
				sum = sum + Math.pow(Math.abs(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next())),
						minkowski);
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
				sum = sum
						+ Math.pow(Math.abs(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next())), euclidea);
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
				sum = sum + Math.pow(Math.abs(Double.parseDouble(it1.next()) - Double.parseDouble(it2.next())),
						manhattan);
			}
			double dist = Math.pow(sum, 1 / manhattan);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;

		}
	}

	/**
	 * devuelve el valor de la instancia-> sus atributos
	 *
	 * @return
	 */
	public ArrayList<String> getLista() {
		return listaAtributos;
	}

	/**
	 * devuelve la posición de la instancia en el fichero
	 *
	 * @return
	 */
	public int getNumInst() {
		return this.numInst;
	}

	/**
	 * escribe por pantalla el numero de instancia y sus atributos
	 */
	public void print() {
		System.out.println(numInst + "- " + listaAtributos);
	}

	/**
	 * modifica el valor de los atributos de la instancia
	 * 
	 * @param lista
	 */
	public void setLista(ArrayList<String> lista) {
		listaAtributos = lista;
	}

}