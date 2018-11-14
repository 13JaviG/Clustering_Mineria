package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

/**
 * clase que nos permite guardar todos los datos del sistema
 * 
 * @author Frank
 *
 */
public class DataBase {

	private static DataBase			miDataBase;
	private final ListaInstancias	instancias;

	/**
	 * constructora
	 */
	private DataBase() {
		instancias = new ListaInstancias();
	}

	/**
	 * devuelve las instancias de la base de datos
	 * 
	 * @return
	 */
	public ArrayList<Instancia> getInstancias() {

		return this.instancias.getInstancias();
	}

	/**
	 * devuelve una instancia random de la base de datos
	 * 
	 * @return
	 */
	public Instancia getRandomVector() {
		return instancias.getRandomVector();
	}


	/**
	 * lee las instancias del fichero y las inicializa en el sistema
	 */
	public void inicializarInstancias() {
		readArff();
		instancias.print();
	}

	/**
	 * método que lee todas las instancias del fichero
	 */
	public void readArff() {

		BufferedReader reader;
		try {
			// leemos el fichero arff
			reader = new BufferedReader(new FileReader(
					// "C:/Users/803145/git/Clustering_Mineria/Clustering/src/arffFiles/diabetesTFIDF.arff"));
					 "C:/Users/Javi/git/Clustering_Mineria/Clustering/src/arffFiles/diabetesTFIDF.arff"));
					// "C:/Users/docenciaeib/Downloads/autopsiaTFIDF.arff"));
					//"C:/Users/User/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"));
			// "C:/Users/docenciaeib/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"));
					//"C:\Users\Javi\git\Clustering_Mineria\Clustering\src\arffFiles\diabetestfidf.arff"
			ArffReader arff = new ArffReader(reader);
			// obtenemos las instancias
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes() - 1);
			int numInst = data.numInstances() - 1;
			int numAtr = data.classIndex() - 1;
			for (int i = 0; i <= numInst; i++) {
				Instance instance = data.instance(i);
				System.out.println(" ");
				ArrayList<String> arraylist = new ArrayList<String>();
				for (int j = 0; j <= numAtr; j++) {
					arraylist.add(instance.toString(j));
				}
				Instancia nueva = new Instancia(i, arraylist);
				instancias.add(nueva);
			}

		} catch (IOException e) {

		}

	}

	/**
	 * método que nos devuelbe la base de datos del sistema
	 * 
	 * @return
	 */
	public static DataBase getDataBase() {
		if (DataBase.miDataBase == null) {
			DataBase.miDataBase = new DataBase();
		}
		return DataBase.miDataBase;
	}

}