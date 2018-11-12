package preProcessing;


import utilities.CommonUtilities;

import java.io.File;

import weka.core.Instances;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class TransformRaw {

	/**
	 * Transforma el espacio de atributos del conjunto de entrenamiento a TF-IDF Sparse.
	 * args[0]: es el path donde se encuentra el Raw a transformar.
	 * args[1]: es el path donde vas a depositar el Raw ya transformado.
	 * @param args ParÃ¡metros de entrada. 
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
			
			//Indicamos que tiene que pasar a minï¿½sculas todas las letras del texto
			filter.setLowerCaseTokens(true);		
			
			//Creamos un Tokenizer y le indicamos quï¿½ sï¿½mbolos tiene que excluir
			WordTokenizer tokenizer = new WordTokenizer();
			tokenizer.setDelimiters("\r\t\n .,;:\'\"()?¿!¡¬-><#$€%&*+/@^_=[]{}|\\`~0123456789");
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
			 * Hacemos que la clase sea el ï¿½ltimo atributo
			 */
			Reorder reorderFilter = new Reorder();
			reorderFilter.setInputFormat(dataFiltered);
			reorderFilter.setOptions(new String[]{"-R","2-last,1"});
			dataFiltered = Filter.useFilter(dataFiltered, reorderFilter);
			dataFiltered.setClassIndex(dataFiltered.numAttributes()-1);

			/*
			 * Damos a la relaciï¿½n su nombre original
			 */
			dataFiltered.setRelationName(relationName);

			/*
			 * guardamos los datos en el path especificado
			 */
            CommonUtilities.saveArff(dataFiltered, pathOut);
           
		}





	}