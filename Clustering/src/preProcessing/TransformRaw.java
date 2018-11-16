package preProcessing;

import utilities.CommonUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weka.core.Instances;
import weka.core.Stopwords;
import weka.core.stemmers.SnowballStemmer;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class TransformRaw {

	/**
	 * Transforma el espacio de atributos del conjunto de entrenamiento a TF-IDF en
	 * formato Non Sparse y realiza un proceso de filtrado para eliminar atributos e
	 * instancias que no sean relevantes para el clustering.
	 * 
	 * @param args
	 *            Parámetros de entrada: args[0]: es el path donde se encuentra el
	 *            Raw a transformar. args[1]: es el path donde vas a depositar el
	 *            Raw ya transformado en TFIDF.
	 */

	public static void main(String[] args) throws Exception {

		String pathIn = "";
		String pathOut = "";

		pathIn = args[0];
		pathOut = args[1];

		System.out.println("Transformando el .arff a TFIDF...");
		System.out.println("");

		/*
		 * Indicamos la clase y cargamos las instancias
		 */
		Instances data = CommonUtilities.loadArff(pathIn, -1);
		data.setClassIndex(data.numAttributes() - 1);
		StringToWordVector filter;
		Instances dataFiltered = null;
		String relationName = data.relationName();

		// Creamos un stemmer en su versión en español
		SnowballStemmer ss = new SnowballStemmer();
		ss.setStemmer("spanish");
		/*
		 * Creamos el filtro StringToWordVector
		 */
		filter = new StringToWordVector();
		// Indicamos que tiene que pasar a minúsculas todas las letras del texto
		filter.setLowerCaseTokens(true);

		// Creamos un Tokenizer y le indicamos qué símbolos tiene que excluir
		AlphabeticTokenizer tokenizer = new AlphabeticTokenizer();
		filter.setTokenizer(tokenizer);

		// Especificamos todas las opciones indicadas anteriormente y aplicamos el
		// filtro.
		filter.setStemmer(ss);
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
		 * A partir de una lista de stopwords predeterminada, eliminamos los atributos
		 * que coincidan con estos.
		 */
		Stopwords stopFilter = new Stopwords();
		stopFilter.read(new File("stopwords-es.txt"));
		List<Integer> words = new ArrayList<Integer>();
		for (int i = 0; i < dataFiltered.numAttributes(); i++) {
			if (stopFilter.is(dataFiltered.attribute(i).name())) {
				words.add(i);
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
		 * 
		 * Eliminamos los atributos Outlier y Extreme
		 */
		Remove filtroEliminar2 = new Remove();
		int[] iu = new int[2];
		iu[0] = (dataFiltered.numAttributes() - 2);
		iu[1] = (dataFiltered.numAttributes() - 1);
		filtroEliminar2.setAttributeIndicesArray(iu);
		filtroEliminar2.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, filtroEliminar2);

		/*
		 * Normalizamos los vectores
		 */
		Normalize NormalizeFilter = new Normalize();
		NormalizeFilter.setInputFormat(dataFiltered);
		dataFiltered = Filter.useFilter(dataFiltered, NormalizeFilter);

		System.out.println("Aplicando filtros...");
		System.out.println("");

		/*
		 * Damos a la relación su nombre original
		 */
		dataFiltered.setRelationName(relationName);

		/*
		 * guardamos los datos en el path especificado
		 */
		CommonUtilities.saveArff(dataFiltered, pathOut);

		System.out.println("Se ha convertido el .arff raw a formato TFIDF non sparse y se ha filtrado.");
		System.out.println("");
	}

}
