package preProcessing;


import utilities.CommonUtilities;

import java.io.File;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

public class TransformRaw {

	/**
	 * Transforma el espacio de atributos del conjunto de entrenamiento a TF-IDF Sparse.
	 * args[0]: es el path donde se encuentra el Raw a transformar.
	 * args[1]: es el path donde vas a depositar el Raw ya transformado.
	 * args[2]: es el path donde se va a guardar el diccionario.
	 * @param args Parámetros de entrada. 
	 */
	public static void main(String[] args) throws Exception {

			String pathIn = "";
			String pathOut = "";
			String pathDictionary = "";

			pathIn = args[0];
			pathOut = args[1];
			pathDictionary = args[2];

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
			filter = new StringToWordVector(99999);
			filter.setDictionaryFileToSaveTo(new File(pathDictionary));
			filter.setTFTransform(true);
			filter.setIDFTransform(true);
			filter.setOutputWordCounts(true);
			filter.setLowerCaseTokens(true);
			filter.setInputFormat(data);
			dataFiltered = Filter.useFilter(data, filter);


			/*
			 * Hacemos que la clase sea el último atributo
			 */
			Reorder reorderFilter = new Reorder();
			reorderFilter.setInputFormat(dataFiltered);
			reorderFilter.setOptions(new String[]{"-R","2-last,1"});
			dataFiltered = Filter.useFilter(dataFiltered, reorderFilter);
			dataFiltered.setClassIndex(dataFiltered.numAttributes()-1);

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
