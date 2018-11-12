package preProcessing;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToString;
import weka.filters.unsupervised.attribute.Remove;

public class CSVtoArff {

	 public static void main(String[] args) {
	        String inputPath = null;
	        String outputPath = null;
	        try {
	            inputPath = args[0];
	            outputPath = args[1];
	        } catch (IndexOutOfBoundsException e) {
	            String documentacion = "Este ejecutable convierte el conjunto de datos de autopsias verbales de formato .csv a .arff.\n" +
	                                    "El archivo a convertir debe estar en formato .csv y su primera línea debe ser la cabecera de los valores.\n" +
	                                    "Dos argumetos esperados:\n" +
	                                         "\t1 - Ruta del archivo .csv a leer\n" +
	                                         "\t2 - Ruta del archivo .arff a crear\n" +
	                                    "\nEjemplo: java -jar autopsiesCSVToArff.jar /path/to/input/csv /path/to/output/arff";
	            System.out.println(documentacion);
	            System.exit(1);
	        }
	        autopsiesCSVToArff(inputPath, outputPath, true);
	    }
	 
	 public static Instances loadInstancesCSV(String pPath) {
	        CSVLoader csvLoader = new CSVLoader();
	        Instances instances;
	        try {
	            csvLoader.setSource(new File(pPath));
	            instances = csvLoader.getDataSet();
	        } catch (IOException e) {
	            utilities.CommonUtilities.printlnError(e.getMessage());
	            instances = null;
	        }
	        return instances;
	}

	    /**
	     * Convierte el archivo .csv de autopsias a formato .arff
	     *
	     * @param pInputPath  ruta del arvhico .csv que se quiere transformar
	     * @param pOutputPath ruta del archivo .arff que se quiere generar
	     * @param pVerbose    si true, se imprime más información por consola
	     */
	    private static void autopsiesCSVToArff(String pInputPath, String pOutputPath, boolean pVerbose) {
	        if (pVerbose)
	            System.out.println(String.format("Procediendo a la conversión de %s a .arff", pInputPath));

	        Instances instances = loadInstancesCSV(pInputPath);
	        if (instances != null) {
	            // ponemos el cuarto atributo (gs_text34) como clase
	            instances.setClassIndex(3);
	            try {
	                // convertimos el atributo open_response a tipo String
	                NominalToString toStringFilter = new NominalToString();
	                toStringFilter.setAttributeIndexes("last");
	                toStringFilter.setInputFormat(instances);
	                instances = Filter.useFilter(instances, toStringFilter);
	                
	                //Eliminamos atributos que no aportan informaci�n.
	                Remove remove = new Remove();
	                remove.setAttributeIndicesArray(new int[]{0, 1, 2, 4, 5, 6, 7});
	                remove.setInputFormat(instances);
	                instances = Filter.useFilter(instances, remove);

	                // ponemos el nombre de la relación y los atributos
	                instances.renameAttribute(instances.classIndex(), "@@class@@");
	                instances.setRelationName("verbal_autopsies");
	            } catch (Exception e) {
	                e.printStackTrace();
	                System.exit(1);
	            }

	            utilities.CommonUtilities.saveArff(instances, pOutputPath);
	            if (pVerbose)
	                System.out.println(String.format("Conversión completa. Nuevo archivo: %s", pOutputPath));
	        } else {
	            if (pVerbose)
	            	 utilities.CommonUtilities.printlnError("Error en la conversión");
	        }
	}

}