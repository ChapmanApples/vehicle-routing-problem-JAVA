package RoutingAgents;

import java.io.Serializable;
import java.util.ArrayList;



public class Truck implements Serializable {
	public int TruckID;
	public int weight_capacity;
	public ArrayList <Node> Locations;


	public Truck(int ID,int w) {
		TruckID = ID;
		weight_capacity = w;
		Locations = new ArrayList<Node>();
	}

	public void addNode(Node aN) {
		Locations.add(aN);
		weight_capacity = weight_capacity-aN.weight;
	}
	
	public void getLocations(int[] locationID, Node[] loc) {
		Locations.clear();
		for(int i = 0; i < locationID.length; i++) {
			for(Node n: loc) {
				if(n.ID == locationID[i]) {
					Locations.add(n);
				}
			}
		}
	}


}
