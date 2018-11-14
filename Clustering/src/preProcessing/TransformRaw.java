package preProcessing;

import utilities.CommonUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weka.core.Instances;
import weka.core.Stopwords;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class TransformRaw {

	/**
	 * Transforma el espacio de atributos del conjunto de entrenamiento a TF-IDF
	 * Sparse. args[0]: es el path donde se encuentra el Raw a transformar. args[1]:
	 * es el path donde vas a depositar el Raw ya transformado.
	 * 
	 * @param args
	 *            ParÃ¡metros de entrada.
	 */

	public static void main(String[] args) throws Exception {

		String pathIn = "";
		String pathOut = "";

		pathIn = args[0];
		pathOut = args[1];

		/*
		 * Indicamos la clase y cargamos las instancias
		 */
		Instances data = CommonUtilities.loadArff(pathIn, -1);
		data.setClassIndex(data.numAttributes() - 1);
		StringToWordVector filter;
		Instances dataFiltered = null;
		String relationName = data.relationName();

		/*
		 * Transformamos el arff raw a TF-IDF
		 */
		filter = new StringToWordVector();
		// Indicamos que tiene que pasar a minï¿½sculas todas las letras del texto
		filter.setLowerCaseTokens(true);

		// Creamos un Tokenizer y le indicamos quï¿½ sï¿½mbolos tiene que excluir
		AlphabeticTokenizer tokenizer = new AlphabeticTokenizer();
		filter.setTokenizer(tokenizer);

		filter.setTFTransform(true);
		filter.setIDFTransform(true);
		filter.setOutputWordCounts(true);
		filter.setInputFormat(data);
		dataFiltered = Filter.useFilter(data, filter);

		/*
		 * Convertimos a formato Non Sparse
		 * 
		 */

		SparseToNonSparse sparseFilter = new SparseToNonSparse();
		sparseFilter.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, sparseFilter);

		/*
		 * Eliminamos Outliers
		 */
		InterquartileRange Outliersfilter = new InterquartileRange();
		Outliersfilter.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, Outliersfilter);

		/*
		 * Eliminamos atributos que varían muy poco
		 */
		RemoveUseless filtroUseless = new RemoveUseless();
		filtroUseless.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, filtroUseless);
		/*
		 * Eliminamos instancias outliers
		 */
		RemoveWithValues removevFilter = new RemoveWithValues();
		removevFilter.setAttributeIndex(Integer.toString(dataFiltered.numAttributes() - 1));
		removevFilter.setNominalIndices(Integer.toString(2));
		removevFilter.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, removevFilter);

		/*
		 * RemoveWithValues removevFilter2 = new RemoveWithValues();
		 * removevFilter2.setAttributeIndex(Integer.toString(dataFiltered.numAttributes(
		 * ))); removevFilter2.setNominalIndices(Integer.toString(2));
		 * removevFilter2.setInputFormat(dataFiltered); dataFiltered =
		 * Filter.useFilter(dataFiltered, removevFilter2);
		 */
		/*
		 * Obtener Stopwords de nuestro conjunto de datos
		 */
		Stopwords stopFilter = new Stopwords();
		stopFilter.read(new File("C:/Users/olizy/Clustering_Mineria/Clustering/stopwords-es.txt"));
		List<Integer> words = new ArrayList<Integer>();
		System.out.println(stopFilter.elements());
		for (int i = 0; i < dataFiltered.numAttributes(); i++) {
			if (stopFilter.is(dataFiltered.attribute(i).toString())) {
				words.add(i);
				System.out.println("SE elemina");
			}
		}
		int[] wordsStop = new int[words.size()];
		Iterator<Integer> iterator = words.iterator();
		for (int i = 0; i < wordsStop.length; i++) {
			wordsStop[i] = iterator.next().intValue();
		}
		
		/*
		 * Eliminar atributos que son Stopwords
		 */
		
		Remove filtroEliminar = new Remove();
		filtroEliminar.setAttributeIndicesArray(wordsStop);
		filtroEliminar.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, filtroEliminar);
		
		/*
		 * Normalizamos los vectores
		 */
		Normalize NormalizeFilter = new Normalize();
		NormalizeFilter.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, NormalizeFilter);

		/*
		 * Hacemos que la clase sea el último atributo
		 * 
		 * Reorder reorderFilter = new Reorder();
		 * reorderFilter.setInputFormat(dataFiltered); reorderFilter.setOptions(new
		 * String[]{"-R","2-last,1"}); dataFiltered = Filter.useFilter(dataFiltered,
		 * reorderFilter); dataFiltered.setClassIndex(dataFiltered.numAttributes()-1);
		 */

		/*
		 * Damos a la relación su nombre original
		 */
		dataFiltered.setRelationName(relationName);

		/*
		 * guardamos los datos en el path especificado
		 */
		CommonUtilities.saveArff(dataFiltered, pathOut);

	}

}
