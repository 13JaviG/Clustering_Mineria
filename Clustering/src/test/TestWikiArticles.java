package test;

import preProcessing.GetRawPlain;
import preProcessing.TransformRaw;

public class TestWikiArticles {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] p = new String[2];
		p[0] = "C:/Users/olizy/Desktop/es/full.txt";
		p[1] = "C:/Users/olizy/Desktop/ArticlesRaw10.arff";

		String[] o = new String[3];
		o[0] = "-d";
		o[1] = "C:/Users/Javi/Desktop/movies_reviews/train";
		o[2] = "C:/Users/Javi/Desktop/train.arff";

		String[] j = new String[2];
		j[0] = "C:/Users/olizy/Desktop/ArticlesRaw.arff";
		j[1] = "C:/Users/olizy/Desktop/ArticlesTFIDFv4.arff";

		// CSVtoArff.main(p);
		// GetRaw.main(o);
		//GetRawPlain.main(p);
		TransformRaw.main(j);
	}

}
