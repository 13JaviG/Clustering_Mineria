package test;

import preProcessing.*;

public class TestCSVAutopsies {

	public static void main(String[] args) throws Exception {
		String[] p = new String[2];
		p[0] = "C:/Users/olizy/Desktop/autopsiasv.csv";
		p[1] = "C:/Users/olizy/Desktop/train.arff";
		
		String[] o = new String[3];
		o[0] = "-d";
		o[1] = "C:/Users/Javi/Desktop/movies_reviews/train";
		o[2] = "C:/Users/Javi/Desktop/train.arff";
		

		String[] j = new String[2];
		j[0] = "C:/Users/olizy/Desktop/train.arff";
		j[1] = "C:/Users/olizy/Desktop/trainTFIDF.arff";

		
		//CSVtoArff.main(p);
		//GetRaw.main(o);
		TransformRaw.main(j);
	}

}
