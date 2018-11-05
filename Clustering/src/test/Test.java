package test;
import java.io.IOException;

import preProcessing.*;
public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] o = new String[3];
		o[0] = "-d";
		o[1] = "C:/Users/Javi/Desktop/movies_reviews/train";
		o[2] = "C:/Users/Javi/Desktop/train.arff";

		String[] j = new String[5];
		j[0] = "C:/Users/Javi/Desktop/train.arff";
		j[1] = "C:/Users/Javi/Desktop/trainTFIDF.arff";
		j[2] = "C:/Users/Javi/Desktop/dicc";

//		String[] k = new String[5];
//		k[0] = "C:/Users/olizy/Desktop/dicc";
//		k[1] = "C:/Users/olizy/Desktop/trainTFIDF.arff";
//		k[2] = "C:/Users/olizy/Desktop/trainTFIDFCOMP.arff";
		
		GetRaw.main(o);
		TransformRaw.main(j);
	}

}
