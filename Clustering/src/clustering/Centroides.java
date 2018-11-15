package clustering;

import java.util.ArrayList;

/**
 * clase para guardar los centroides
 *
 * @author Frank
 *
 */
public class Centroides {

	private final ArrayList<double[]> centroides = new ArrayList<double[]>();

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
	public void add(double[] pCentroide) {
		centroides.add(pCentroide);
	}

	/**
	 * obtener el centroide en la posición i
	 *
	 * @param i
	 * @return
	 */
	public double[] get(int i) {
		return centroides.get(i);
	}

}