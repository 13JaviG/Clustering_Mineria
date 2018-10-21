package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class KmeansAlgorithm {

	public static HashMap<String, ArrayList<String>> getHashMap() {
		//////////
		HashMap<String, ArrayList<String>> hashmap = new HashMap<String, ArrayList<String>>();

		BufferedReader reader;
		try {
			// leemos el fichero arff
			reader = new BufferedReader(new FileReader(
					"C:/Users/Frank/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"));
			ArffReader arff = new ArffReader(reader);
			// obtenemos las instancias
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes() - 1);
			int numInst = data.numInstances() - 1;
			int numAtr = data.classIndex() - 1;
			for (int i = 0; i <= numAtr; i++) {
				System.out.println(" ");
				ArrayList<String> arraylist = new ArrayList<String>();
				String nomAtr = data.attribute(i).name();
				for (int j = 0; j <= numInst; j++) {
					Instance instance = data.instance(j);
					arraylist.add(instance.toString(i));
				}
				hashmap.put(nomAtr, arraylist);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//////////
		return hashmap;
	}

	public static String getRandomAtribute() {
		ArrayList<Object> nuevo = new ArrayList<>(Arrays.asList(KmeansAlgorithm.getHashMap().keySet().toArray()));
		Random rand = new Random();
		int n = rand.nextInt(nuevo.size() - 1);
		String aleatorio = nuevo.get(n).toString();
		return aleatorio;
	}

	public static ArrayList<String> getWordVectorByString(String x) {
		ArrayList<String> arraylist = new ArrayList<String>();
		HashMap<String, ArrayList<String>> hash = new HashMap<String, ArrayList<String>>();
		hash = KmeansAlgorithm.getHashMap();
		arraylist = hash.get(x);
		return arraylist;
	}

	public static void kmeans() {
		/*
		 * 1ro leer el fichero para conocer las palabras/atributos del mismo
		 */
		/*
		 * 2do asignar un vector aleatorio distinto(¡¡¡sin repetir!!!) cada vez
		 * a los k clusters que se quieran crear
		 */
		/*
		 * 3ro calcular la distancia entre un vector y cada uno de los k
		 * clústers
		 */
		/* 4to asignar a el vector el clúster que más cercano se encuentre */
		/*
		 * 5to calcular el punto medio entre cada uno de los vectores asignados
		 * a los clusters y dar ese nuevo valor al centroide del clúster
		 */
		/*
		 * 6to volver al punto 3ro hasta que la diferencia entre un iteración y
		 * otra sea menor que un valor propuesto(esta diferencia es la distancia
		 * entre el antiguo valor del centride y el nuevo)
		 */
	}

	public static void main(String[] args) {
		// ejemplo que devuelve el vector para la palabra skin
		System.out.println(KmeansAlgorithm.getWordVectorByString("skin"));
		System.out.println(KmeansAlgorithm.getRandomAtribute());

	}

	public static void metodoGeneralPruebas() {
		// ¡¡¡¡¡¡no borrar ni modificar este método!!!!!!
		BufferedReader reader;
		try {
			// leemos el fichero arff
			reader = new BufferedReader(new FileReader(
					"C:/Users/Frank/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"));
			ArffReader arff = new ArffReader(reader);
			// obtenemos las instancias
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes() - 1);
			System.out.println("Numero de atributos " + data.classIndex() + ": desde 0 a " + (data.classIndex() - 1));
			int numInst = data.numInstances() - 1;
			int numAtr = data.classIndex() - 1;
			System.out.println("Instancias del archivo arff: ");
			for (int i = 0; i <= numInst; i++) {
				Instance instance = data.instance(i);
				System.out.println(" ");
				for (int j = 0; j <= numAtr; j++) {
					System.out.print(instance.toString(j) + "; ");
				}

			}
			HashMap<String, ArrayList<String>> hashmap = new HashMap<String, ArrayList<String>>();

			System.out.println("");
			System.out.println("");
			System.out.println("atributo 0/ palabra 0 " + data.attribute(7).name());
			System.out.println("Obtenemos todos los vectores R" + data.numInstances() + "");
			for (int i = 0; i <= numAtr; i++) {
				System.out.println(" ");
				ArrayList<String> arraylist = new ArrayList<String>();
				String nomAtr = data.attribute(i).name();
				for (int j = 0; j <= numInst; j++) {
					Instance instance = data.instance(j);
					System.out.print(instance.toString(i) + "; ");
					arraylist.add(instance.toString(i));
				}
				hashmap.put(nomAtr, arraylist);
			}
			System.out.println("");
			System.out.println("printeando el arraylist del hashmap con clave del atributo 1ro -> preg");
			ArrayList<String> arraylistNuevo = new ArrayList<String>();
			arraylistNuevo = hashmap.get("preg");
			System.out.println(arraylistNuevo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
