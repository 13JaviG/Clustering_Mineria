package test;
import java.io.IOException;

import preProcessing.*;
public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] p = new String[3];
		p[2] = "TRUE";
		p[0] = "C:/Users/olizy/Desktop/autopsiasv.csv";
		p[1] = "C:/Users/olizy/Desktop/train.arff";
		
		String[] o = new String[3];
		o[0] = "-c";
		o[1] = "C:/Users/Javi/Desktop/tweet_sentiment_eGela/tweetSentiment.train.csv";
		o[2] = "C:/Users/Javi/Desktop/train.arff";
		

		String[] j = new String[2];
		j[0] = "C:/Users/Javi/Desktop/train.arff";
		j[1] = "C:/Users/Javi/Desktop/trainTFIDFAlphabetic.arff";

		
		//CSVtoArff.main(p);
		GetRaw.main(o);
		TransformRaw.main(j);
	}

}
