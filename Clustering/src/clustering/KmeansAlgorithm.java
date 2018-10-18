package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

public class KmeansAlgorithm {

	public static void kmeansAlgorithm() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("../arffFiles/diabetestfidf.arff"));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			data.setClassIndex(data.numAttributes() - 1);

			for (int i = 0; i <= data.numInstances() - 1; i++) {
				Instance instance = data.instance(i);
				System.out.println(instance.stringValue(0)); // get Attribute 0
																// as String
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		KmeansAlgorithm.kmeansAlgorithm();
	}

}
