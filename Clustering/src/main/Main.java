package main;

import clustering.MainClass;
import preProcessing.GetRawPlain;
import preProcessing.TransformRaw;

public class Main {

	Double time;

	/**
	 * Este es el programa principal de todo el paquete, que permite, primero
	 * convertir un conjunto de datos en TFIDF para luego realizar un algoritmo
	 * de clustering kmeans y obtener un fichero de texto que contiene los
	 * resultados además de un índice de calidad Silhouette.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String factor = "0.0015";
		if (args.length == 7) {
			if (args[3].equals("-ma") && args[4].equals("-2k")) {

				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
				*/
				MainClass.getMainClass().ejecutar(clusters, "2kclusters", it, conv, "manhattan", args[1]);
			} else if (args[3].equals("-ma") && args[4].equals("-a")) {
				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
*/
				MainClass.getMainClass().ejecutar(clusters, "aleatorio", it, conv, "manhattan", args[1]);
			} else if (args[3].equals("-mi") && args[4].equals("-2k")) {
				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
*/
				MainClass.getMainClass().ejecutar(clusters, "2kclusters", it, conv, "minkowski", args[1]);

			} else if (args[3].equals("-mi") && args[4].equals("-a")) {
				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
				*/
				MainClass.getMainClass().ejecutar(clusters, "aleatorio", it, conv, "minkowski", args[1]);
			} else if (args[3].equals("-e") && args[4].equals("-2k")) {
				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
				*/
				MainClass.getMainClass().ejecutar(clusters, "2kclusters", it, conv, "euclidea", args[1]);

			} else if (args[3].equals("-e") && args[4].equals("-a")) {
				int clusters = Integer.parseInt(args[2]);
				int it = Integer.parseInt(args[5]);
				float conv = Float.parseFloat(args[6]);
/*
				String[] params = new String[3];
				params[0] = args[0];
				params[1] = "ArticlesRaw.arff";
				params[2] = factor;
				GetRawPlain.main(params);
				String[] params2 = new String[2];
				params2[0] = "ArticlesRaw.arff";
				params2[1] = "ArticlesTFIDF.arff";
				TransformRaw.main(params2);
*/
				MainClass.getMainClass().ejecutar(clusters, "aleatorio", it, conv, "euclidea", args[1]);
			}
		} else if (args.length == 0) {
			System.out.println("****Clustering semántico****\n");
			System.out.println("Este programa tiene como función realizar un algoritmo de clustering semántico,"
					+ "en concreto el k-means, a partir de un conjunto de artículos de Wikipedia en español, que produce un fichero con los resultados obtenidos.");
			System.out.println("Este programa necesita que introduzcas 7 argumentos para funcionar correctamente.");
			System.out
					.println("PRECONDICIONES:\nEl primer argumento será el path del fichero o directorio a convertir.\n"
							+ "El segundo es el path del fichero de salida.\nEl tercero el numero de clusters.\n"
							+ "El cuarto la métrica de distancia.\nEl quinto la inicialización. \nEL sexto el numer de iteraciones.\nEl último el umbral de convergencia.");
			System.out.println("POSTCONDICIONES:\nEl resultado de esta aplicación será la creación de un fichero .txt "
					+ "en el path especificado en los argumentos que contiene los resultados del clustering.\n");
			System.out.println("Lista de argumentos :\n" + "-- Path de la raíz del fichero .txt a analizar."
					+ "\n-- Path del destino donde se guardará el fichero resultante tras la ejecución.\n"
					+ "--Numero de clusters (en integer).\n--Tipo de distancia:\n -ma\tManhattan."
					+ "\n -mi\tMinkowski.\n -e\tEuclídea.\n" + "--Inicializacion:\n -2k\t2kclusters\n -a\taleatoria"
					+ "\n--Número de iteraciones (En integer)." + "\n--Criterio de convergencia (en float).");
			System.out.println(
					"Ejemplo de una correcta ejecución: java -jar Clustering.jar /path/to/file.txt /path/to/results.txt 4 -ma -2k 50 0.0005");
			System.exit(0);
		} else {
			utilities.CommonUtilities.printlnError("Numero de parametros incorrecto");
			System.exit(1);
		}
		System.out.println("Se ha terminado y se ha generado el fichero de resultados.");
		System.exit(0);
	}
}
