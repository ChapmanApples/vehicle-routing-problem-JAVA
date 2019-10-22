package RoutingAgents;

import java.util.ArrayList;

public class Truck {
	public int TruckID;
	public int weight_capacity = 10;
	public ArrayList <Node> Locations;
	
	
	public Truck(int ID,int w) {
		TruckID = ID;
		w = weight_capacity;
		Locations = new ArrayList<Node>();
	}

	public void addNode(Node aN) {
		Locations.add(aN);
		weight_capacity = weight_capacity-aN.weight;
	}

	
}
