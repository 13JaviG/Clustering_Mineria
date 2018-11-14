package clustering;

import java.util.ArrayList;

/**
 * clase para guardar los centroides
 * 
 * @author Frank
 *
 */
public class Centroides {

	private final ArrayList<ArrayList<String>> centroides = new ArrayList<ArrayList<String>>();

	/**
	 * constructora
	 */
	public Centroides() {

	}

	/**
	 * añadir centroide
	 *
	 * @param pCentroide
	 */
	public void add(ArrayList<String> pCentroide) {
		centroides.add(pCentroide);
	}

	/**
	 * obtener el centroide en la posición i
	 *
	 * @param i
	 * @return
	 */
	public ArrayList<String> get(int i) {
		return centroides.get(i);
	}

}