package utilities;


import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.SerializationHelper;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import clustering.Cluster;

public class CommonUtilities {

	/**
	 * Escribe por consola el texto pTexto en color rojo. NO incluye salto de lÃ­nea.
	 *
	 * @param pText Texto a escribir
	 */
	private static void printError(String pText) {System.out.print(String.format("\33[31m%s\33[0m", pText));}

	/**
	 * Escribe por consola el texto pTexto en color rojo. SIEMPRE incluye salto de lÃ­nea.
	 *
	 * @param pText Texto a escribir
	 */
	public static void printlnError(String pText) {printError(String.format("%s\n", pText));}

	/**
	 * Cargamos las instancias del fichero path y asigna a estas el Ã­ndice de clase classIndex. En caso de ser negativo
	 * se asigna el Ãºltimo atributo como atributo de clase.
	 *
	 * @param path			Instancias a cargar.
	 * @param classIndex	Indice de la clase.
	 */
	public static Instances loadArff(String path, int classIndex){

		FileReader file;

		try {
			file = new FileReader(path);
			file.close();
		} catch (Exception e) {
			printlnError("ERROR CARGANDO LAS INSTANCIAS: Comprueba el path del fichero: " + path);
			System.exit(1);
		}

		Instances data = null;

		try {
			ConverterUtils.DataSource ds = new ConverterUtils.DataSource(path);
			data = ds.getDataSet();
		} catch (Exception e) {
			printlnError("ERROR LEYENDO LAS INSTANCIAS: Comprueba la estructura interna del fichero: " + path);
			System.exit(1);
		}

		if (classIndex < 0){
			data.setClassIndex(data.numAttributes()-1);
		}else{
			data.setClassIndex(classIndex);
		}

		return data;
	}

	/**
	 * Guarda las instancias instances en la ruta pathOut.
	 *
	 * @param instances	Conjunto de instancias a convertir y guardar en el fichero .arff.
	 * @param pathOut	Ruta donde se guardarÃ¡ el fichero resultante de la conversiÃ³n.
	 */
	public static void saveArff(Instances instances, String pathOut) {
		try {
			ArffSaver arffSaver = new ArffSaver();
			arffSaver.setInstances(instances);
			arffSaver.setFile(new File(pathOut));
			arffSaver.writeBatch();
		} catch (Exception e) {
			printlnError("ERROR AL GUARDAR LAS INSTANCIAS: Comprueba el path: " + pathOut);
			e.printStackTrace();
		}
	}

	/**
	 * Carga el clasificador ubicado en la ruta pPath.
	 *
	 * @param pathIn	Ruta del clasificador a cargar.
	 * @return 			Clasificador cargado.
	 */
	public static Classifier loadModel(String pathIn){
		Classifier model = null;
		try {
			model = (Classifier) SerializationHelper.read(pathIn);
		} catch (Exception e) {
			printlnError("ERROR AL CARGAR EL CLASIFICADOR. Comprueba el path: " + pathIn);
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * Guarda el clasificador pClassifier en la ruta pPath.
	 *
	 * @param classifier	Clasificador a guardar.
	 * @param pathOut		Ruta del archivo a crear.
	 */
	public static void saveModel(Classifier classifier, String pathOut){
		try {
			SerializationHelper.write(pathOut, classifier);
		} catch (Exception e) {
			printlnError("ERROR AL GUARDAR EL CLASIFICADOR. Comprueba el path: " + pathOut);
			e.printStackTrace();
		}
	}

	/**
	 * Este mÃ©todo escribe en un archivo la calidad estimada de una evaluaciÃ³n dada.
	 *
	 * @param evaluation    EvaluaciÃ³n cuya calidad se quiere escribir.
	 * @param pathOut       Ruta del fichero que se desea crear/sobreescribir.
	 */
	public static void writeQuality(Evaluation evaluation, String pathOut) {
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(pathOut + "_quality.txt"))));
			writer.println("==========================================================================================================");
			writer.println("CALIDAD ESTIMADA DEL MODELO:");
			writer.println("Se ha usado 8-fold cross-validation sobre el modelo para obtener la calidad estimada.");
			writer.println("==========================================================================================================");
			writer.println(evaluation.toSummaryString());
			writer.println(evaluation.toMatrixString());
			writer.flush();
			writer.close();

		} catch (Exception e) {
			printlnError("ERROR AL ESCRIBIR LA CALIDAD DEL MODELO.");
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el Ã­ndice de la clase minoritaria del conjunto de instancias dado.
	 * 
	 * @param instances	Instancias de las que se obtendrÃ¡ el Ã­ncide de la clase minoritaria.
	 * @return			Ã�ndice de la clase minoritaria.
	 */
	public static int getMinorityClassIndex(Instances instances){
        int[] nomCounts = instances.attributeStats(instances.classIndex()).nominalCounts;
        int minClassAmount = -1;
        int minClassIndex = -1;
        for(int i = 0; i < nomCounts.length; i++) {
            if (minClassAmount < 0 || nomCounts[i] < minClassAmount) {
                minClassAmount = nomCounts[i];
                minClassIndex = i;
            }
        }
        return minClassIndex;
    }

	public static void getResultados(String pathOut, ArrayList<Cluster> clusters, double sil){
		
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(pathOut))));
			writer.println("==========================================================================================================");
			writer.println("COORDENADAS DE LOS CENTROIDES:");
			writer.println("==========================================================================================================");
			writer.println("==========================================================================================================");
			for(int i=0; i<clusters.size() ; i++){
				writer.println("CENTROIDE DEL CLUSTER NÚMERO:	"+(i+1));
				writer.println(clusters.get(i).printDatosCentroide());
				writer.println("==========================================================================================================");
			}
			writer.println("La ejecucion del algoritmo de clustering ha durado: " + clustering.MainClass.getMainClass().getTime() + " ms");
			writer.println("==========================================================================================================");
			writer.println("==========================================================================================================");
			writer.println("      ");
			writer.println("El Sillhouette es un indice de calidad interna que varia de -1 a 1");
			writer.println("Siendo -1 una mala clasificación y 1 una buena clasificación");
			writer.println("      ");
			writer.println("==========================================================================================================");
			
			double silMedio = 0.00;
			int i = 0;
			for(i=0; i<clusters.size() ; i++){
				writer.println("SILHOUETTE MEDIO DEL CLUSTER NÚMERO:	"+(i+1));
				writer.println(Double.toString(clusters.get(i).getSil()));
				silMedio = silMedio + clusters.get(i).getSil();
				writer.println("NUMERO DE INSTANCIAS: " + clusters.get(i).size());
				writer.println("==========================================================================================================");
			}
			silMedio = silMedio/i;
			writer.println("           ");
			writer.println("EL SILHOUETTE MEDIO POR CLUSTERS:	"+silMedio);
			writer.println("           ");
			writer.println("           ");
			writer.println("EL SILHOUETTE MEDIO POR INSTANCIAS:	"+sil);
			writer.println("           ");
			writer.println("==========================================================================================================");
			writer.println("INSTANCIAS:");
			writer.println("           ");
			i = 0;
			for(i=0; i<clusters.size() ; i++){
				for(int j=0; j<clusters.get(i).size() ; j++){
				writer.println("Núm.Instancia:	"+clusters.get(i).getNumInstancia(j)+"	CLUSTER NÚMERO "+(i+1)+"	- "+clusters.get(i).getAtributosInstancia(j));
				}
			}
			writer.println("==========================================================================================================");
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

