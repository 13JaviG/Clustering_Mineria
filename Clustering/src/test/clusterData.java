package test;

import utilities.CommonUtilities;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class clusterData{
	
public static void clusterkmeans(String[] args){	
	
		String pathIn = "";
		int[] assignments = null;
		
		pathIn = args[0];
		
		Instances data = CommonUtilities.loadArff(pathIn, -1);
		
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(10);
	
		kmeans.setPreserveInstancesOrder(true);
		
		try {
			kmeans.setNumClusters(10);
			kmeans.buildClusterer(data);
			assignments = kmeans.getAssignments();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		for(int clusterNum : assignments) {
			System.out.printf("Instance %d -> Cluster %d\n", i, clusterNum);
			i++;
		}
	}
}