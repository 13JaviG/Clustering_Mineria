package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class DataBase {

	private static DataBase			miDataBase;
	private final ListaInstancias	instancias;

	private DataBase() {
		instancias = new ListaInstancias();
	}

	public ArrayList<Instancia> getInstancias() {

		return this.instancias.getInstancias();
	}

	public Instancia getRandomVector() {
		return instancias.getRandomVector();
	}

	public void inicializarInstancias() {
		readArff();
		instancias.print();
	}

	public void readArff() {

		BufferedReader reader;
		try {
			// leemos el fichero arff
			reader = new BufferedReader(new FileReader(
					//"C:/Users/Frank/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"
					"C:/Users/User/git/Clustering_Mineria/Clustering/src/arffFiles/diabetestfidf.arff"));
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

	public static DataBase getDataBase() {
		if (DataBase.miDataBase == null) {
			DataBase.miDataBase = new DataBase();
		}
		return DataBase.miDataBase;
	}

}