package preProcessing;

import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.CSVLoader;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Reorder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import utilities.CommonUtilities;


public class GetRaw {
	/**
	 * Crea un archivo arff a partir de un directorio de ficheros (raw).
	 *
	 * @param args  Par�metros de entrada. En caso de no introducir ninguno se muestra una descripci�n de estos.
	 */
	public static void main(String[] args) throws IOException {

        if (args.length == 3 && args[0].equals("-d")) {
            String pathIn = args[1];
            String pathOut = args[2];
            getRawDirectory(pathIn, pathOut);
        }else if (args.length == 3 && args[0].equals("-c")) {
            String pathIn = args[1];
            String pathOut = args[2];
            getRawCSV(pathIn, pathOut);
        }else if (args.length == 3 && args[0].equals("-p")) {
            String pathIn = args[1];
            String pathOut = args[2];
            getRawPlain(pathIn, pathOut);
        }else if (args.length == 0) {
            System.out.println("=====Get Raw=====");
            System.out.println("Este programa tiene como funci�n obtener un fichero .arff para su uso en miner�a de datos " +
                    "a partir de diversos tipos de archivos.");
            System.out.println("Este programa necesita que introduzcas 3 argumentos para funcionar correctamente.");
            System.out.println("PRECONDICIONES:\nEl primer argumento ser� el tipo de archivo de entrada. El segundo " +
                    "argumento ser� el path del fichero o directorio a convertir. El tercero es el path del fichero de salida.");
            System.out.println("POSTCONDICIONES:\nEl resultado de esta aplicaci�n ser� la creaci�n de un fichero .arff " +
                    "en el path especificado en los argumentos\n");
            System.out.println("Lista de argumentos:\n--Opci�n para el input:\n   -d\tLa estructura de los ficheros es por directorios." +
                    "\n   -c\tEl fichero est� en formato CSV.\n   -p\tEl fichero est� en texto plano.\n" +
                    "-- Path de la ra�z del fichero o �rbol de directorios a convertir." +
                    "\n-- Path del destino donde se guardar� el fichero resultante tras la ejecuci�n");
            System.out.println("Ejemplo de una correcta ejecuci�n: java -jar getRaw.jar -d /path/to/file /path/to/newAfrff.arff");
            System.exit(0);
        }else{
            CommonUtilities.printlnError("Error en el input. Revise su sintaxis.");
            System.exit(1);
	    }

    }

	/**
     * Este m�todo crea un archivo arff en la ruta pathOut a partir de los archivos de texto plano ubicados en el �rbol
     * de directorios de la ruta pathIn. Las carpetas de este �rbol ser�n la clase asignada.
     *
     * @param pathIn        Ruta en la que est� ubicado el directorio de archivos de texto plano.
     * @param pathOut       Ruta en la que se crea el nuevo archivo arff,
     */
    private static void getRawDirectory (String pathIn, String pathOut) throws IOException {
        //Carga y lectura del directorio de archivos de texto plano.
        TextDirectoryLoader loader = new TextDirectoryLoader();
        loader.setDirectory(new File(pathIn));
        Instances instances = loader.getDataSet();

        //Renombramos la relaci�n y definimos el �ndice de la clase
        instances.setRelationName("MOVIES");
        instances.setClassIndex(instances.numAttributes()-1);

        //Creaci�n del archivo arff.
        CommonUtilities.saveArff(instances, pathOut);
    }

    /**
     * Este m�todo crea un archivo arff en la ruta pathOut a partir del archivo CSV que ubicado en la ruta pathIn.
     *
     * @param pathIn    Ruta en la que est� ubicado el directorio de archivos raw.
     * @param pathOut   Ruta en la que se crea el nuevo archivo arff,
     */
    private static void getRawCSV (String pathIn, String pathOut){
        //Este m�todo est� personalizado para el archivo de pruebas "Tweets", por lo que no es �til con otros archivos.
        try{
            int classIndex = 1;
            int tweetIndex = 4;
            String tmpCSV = parseCSV(pathIn);
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(tmpCSV));
            Instances instances = loader.getDataSet();
            instances.setClassIndex(classIndex);
            //Files.delete(Paths.get(tmpCSV));


            //Damos forma a las instancias
            instances.setRelationName("Tweets");
            instances.renameAttribute(classIndex, "@@class@@");//Atributo de la clase.
            instances.renameAttribute(tweetIndex, "tweet");//Atributo en el que se encuentra texto del tweet.

            //Eliminamos atributos que no aportan informaci�n.
            Remove remove = new Remove();
            remove.setAttributeIndicesArray(new int[]{0, 2, 3});
            remove.setInputFormat(instances);
            instances = Filter.useFilter(instances, remove);

            //Hacemos que Weka detecte el texto del Tweet como un String
            NominalToString stringFilter = new NominalToString();
            stringFilter.setAttributeIndexes("last");
            stringFilter.setInputFormat(instances);
            instances = Filter.useFilter(instances, stringFilter);

            //Hacemos que la clase sea el �ltimo atributo
            Reorder reorderFilter = new Reorder();
            reorderFilter.setInputFormat(instances);
            reorderFilter.setOptions(new String[]{"-R","2-last,1"});
            instances = Filter.useFilter(instances, reorderFilter);

            //Renombramos la relaci�n y definimos el �ndice de la clase
            instances.setRelationName("TWEETS");
            instances.setClassIndex(instances.numAttributes()-1);

            CommonUtilities.saveArff(instances, pathOut);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Este m�todo crea un archivo arff en la ruta pathOut a partir del archivo de texto plano ubicado en la ruta pathIn.
     *
     * @param pathIn        Ruta en la que est� ubicado el archivo de texto plano.
     * @param pathOut       Ruta en la que se crea el nuevo archivo arff,
     */
    private static void getRawPlain (String pathIn, String pathOut) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(new File(pathIn)));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(pathOut))));
        writer.println("@relation SMS\n\n@attribute sms string\n@attribute @@class@@ {ham, spam}\n\n@data");
        String lineIterator;
        while ((lineIterator = reader.readLine()) != null){
            String[] line = lineIterator.split("\t");
            if(line.length==2) {
                String instanceClass = line[0];
                String instanceText = line[1];
                instanceText = weka.core.Utils.quote(instanceText);
                writer.println(instanceText + "," + instanceClass);
            }else{
                String instanceText = line[0];
                instanceText = weka.core.Utils.quote(instanceText);
                writer.println(instanceText + "," + "?");
            }
        }
        writer.close();
    }

    /**
     * Este m�todo limpia y corrige el contenido corrupto de un fichero CSV y guarda el resultado en un nuevo archivo
     * ubicado en el directorio original. Devuelve el path del nuevo archivo creado.
     *
     * @param path  Ruta en la que est� ubicado el fichero CSV que se desea limpiar.
     * @return      Ruta en la que est� ubicado el nuevo fichero CSV limpiado.
     */
    private static String parseCSV (String path){
        String newPath = path + ".tmp";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(newPath));
            String result = "";
            String header = reader.readLine();
            String[] headers = getValuesCSV(header);
            int numHeaders = headers.length;
            String lineIterator;
            String[] lineValues;
            while ((lineIterator = reader.readLine()) != null) {
                if (!lineIterator.equals("")) {
                    lineValues = getValuesCSV(lineIterator);
                    if (lineValues.length == numHeaders) {
                        String[] cleanValues = new String[numHeaders];
                        for (int i = 0; i < lineValues.length; i++) {
                            cleanValues[i] = Utils.quote(lineValues[i]);
                            if (!cleanValues[i].startsWith("\'")) {
                                cleanValues[i] = "\'" + cleanValues[i];
                            }
                            if (!cleanValues[i].endsWith("\'")) {
                                cleanValues[i] = cleanValues[i] + "\'";
                            }
                        }
                        result += (String.join(",", cleanValues) + "\n");
                    }
                }
            }
            writer.write(result);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return newPath;
    }

    /**
     * Este m�todo lee los valores que contiene una linea de texto en formato CSV y los devuelve como un array de datos
     * en formato String.
     *
     * @param line  Linea en formato CSV de la que se quieren leer los datos.
     * @return      Array de Strings que contiene los valores de la línea leída.
     */
    private static String[] getValuesCSV (String line){
        String[] splitedLine;
        if (line.charAt(0) == '\"'){
            line = line.substring(1);
        }
        if (line.charAt(line.length()-1) == '\"'){
            line = line.substring(0, line.length()-1);
        }
        splitedLine = line.split("\",\"");
        return splitedLine;
    }
}
