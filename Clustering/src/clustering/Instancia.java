package clustering;

import java.util.Arrays;

import org.apache.commons.math3.analysis.function.Power;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;;

/**
 * clase que representa una istancia en el sistema
 *
 * @author Frank
 *
 */

public class Instancia {

	private double[]	listaAtributos;
	private final int	numInst;

	/**
	 * constructora de la clase -> pNumInst=posición de la instancia en el
	 * fichero pListaAtr=valor de los atributos de la instancia
	 *
	 * @param pNumInst
	 * @param plistaAtr
	 */
	public Instancia(int pNumInst, double[] plistaAtr) {
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
	public double getDistanceTo(double[] pVector, String pTipoDistancia) {

		if (pTipoDistancia == "minkowski") {
			// utilizaremos la distancia minkowski de grado 7.5
			double[] matrixData = this.listaAtributos;
			RealMatrix m = new Array2DRowRealMatrix(matrixData);
			double[] matrixData2 = pVector;
			RealMatrix n = new Array2DRowRealMatrix(matrixData2);
			// convertimos en vectores
			RealVector realvector1 = m.getColumnVector(0);
			RealVector realvector2 = n.getColumnVector(0);
			// pasos para obtener minkowski
			RealVector negativo = realvector2.mapMultiply(-1);
			RealVector negativo1 = negativo.add(realvector1);
			Power pow = new Power(0.133333);
			RealVector minko75 = negativo1.mapToSelf(pow);

			// obtenemos la distancia minkowski
			double dist = Math.pow(minko75.getL1Norm(), 0.1333333);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;

		} else if (pTipoDistancia == "euclidea") {
			// utilizaremos la distancia euclidea de grado 2
			double[] matrixData = this.listaAtributos;
			RealMatrix m = new Array2DRowRealMatrix(matrixData);
			double[] matrixData2 = pVector;
			RealMatrix n = new Array2DRowRealMatrix(matrixData2);
			// convertimos en vectores
			RealVector realvector1 = m.getColumnVector(0);
			RealVector realvector2 = n.getColumnVector(0);

			// pasos para obtener euclidea
			RealVector negativo = realvector2.mapMultiply(-1);
			RealVector negativo1 = negativo.add(realvector1);
			RealVector negativo2 = negativo1.ebeMultiply(negativo1);
			double dist = Math.pow(negativo2.getL1Norm(), 0.5);
			if (Double.isNaN(dist))
				return 0.0;
			else
				return dist;
		} else {
			// utilizaremos la distancia manhattan de grado 1
			double[] matrixData = this.listaAtributos;
			RealMatrix m = new Array2DRowRealMatrix(matrixData);
			double[] matrixData2 = pVector;
			RealMatrix n = new Array2DRowRealMatrix(matrixData2);
			// convertimos en vectores
			RealVector realvector1 = m.getColumnVector(0);
			RealVector realvector2 = n.getColumnVector(0);
			// obtenemos mediante la regla L1 la distancia manhattan
			double dist = realvector1.getL1Distance(realvector2);
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
	public double[] getLista() {
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
		System.out.println(numInst + "- " + Arrays.toString(listaAtributos));
	}

	/**
	 * modifica el valor de los atributos de la instancia
	 *
	 * @param lista
	 */
	public void setLista(double[] lista) {
		listaAtributos = lista;
	}

}