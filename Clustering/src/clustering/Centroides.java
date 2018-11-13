package clustering;

import java.util.ArrayList;

public class Centroides {
	private final ArrayList<ArrayList<String>> centroides = new ArrayList<ArrayList<String>>();

	public Centroides() {

	}

	public void add(ArrayList<String> pCentroide) {
		centroides.add(pCentroide);
	}

	public ArrayList<String> get(int i) {
		return centroides.get(i);
	}

}