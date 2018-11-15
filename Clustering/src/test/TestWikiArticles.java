package test;

import main.Main;

public class TestWikiArticles {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] p = new String[3];
		p[0] = "C:/Users/olizy/Desktop/es/full.txt";
		p[1] = "ArticlesRaw.arff";
		p[2] = "0.0015";

		String[] o = new String[3];
		o[0] = "-d";
		o[1] = "C:/Users/Javi/Desktop/movies_reviews/train";
		o[2] = "C:/Users/Javi/Desktop/train.arff";

		String[] j = new String[2];
		j[0] = "ArticlesRaw.arff";
		j[1] = "ArticlesTFIDF.arff";

		// GetRawPlain.main(p);
		// TransformRaw.main(j);
		String[] pp = new String[7];
		pp[0] = "C:/Users/olizy/Desktop/es/full.txt";
		pp[1] = "C:/Users/Frank/Desktop/resultado.txt";
		pp[2] = "10";
		pp[3] = "-mi";
		pp[4] = "-a";
		pp[5] = "100";
		pp[6] = "0.0";
		Main.main(pp);
		System.out.println("He terminado cruck");

	}

}
