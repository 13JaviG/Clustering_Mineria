package preProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GetRawPlain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String pathIn = args[0];
		String pathOut = args[1];
		BufferedReader reader = new BufferedReader(new FileReader(new File(pathIn)));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(pathOut))));
		writer.println("@relation articles\n\n@attribute article string\n\n@data");

		String article = "";
		String lineIterator;
		reader.readLine(); // Para obviar a primera linea
		while ((lineIterator = reader.readLine()) != null) {
			if (lineIterator.contains("[[")) {
				Random r = new Random();
				float k = r.nextFloat();
				if (k <= 0.10f) {
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
	}
}
