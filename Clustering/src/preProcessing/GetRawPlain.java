package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GetRawPlain {
	/**
	 * Crea un fichero .arff en formato raw a partir de un conjunto de datos en
	 * .txt, en concreto el conjunto de datos de los "dumps" de wikipedia.
	 * 
	 * @param args
	 *            Parámetros de entrada. Estos incluyen: -Path de entrada: path del
	 *            fichero .txt d que se quieren obtener los datos. -Path de salida:
	 *            path del fichero .arff raw resultante. -Factor: porcentaje (en
	 *            float) del conjunto total que se quiere tomar.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String pathIn = args[0];
		String pathOut = args[1];
		float factor = Float.parseFloat(args[2]);
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathIn)));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(pathOut))));
		writer.println("@relation articles\n\n@attribute article string\n\n@data");

		System.out.println("Creando el fichero .arff...");
		System.out.println("");
		String article = "";
		String lineIterator;
		reader.readLine(); // Para obviar a primera linea
		while ((lineIterator = reader.readLine()) != null) {
			if (lineIterator.contains("[[")) {
				Random r = new Random();
				float k = r.nextFloat();
				// Este if permite escoger de manera aleatoria un porcentaje de un conjunto
				// total de datos.
				if (k <= factor) {
					article = weka.core.Utils.quote(article);
					writer.println(article);
				}
				article = "";
			} else {
				article = article.concat(lineIterator + " ");
			}
		}
		article = weka.core.Utils.quote(article);
		writer.println(article);
		writer.close();
		reader.close();
		System.out.println("Se ha creado el fichero .arff raw");
		System.out.println("");
	}
}
