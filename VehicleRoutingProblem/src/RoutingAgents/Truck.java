package RoutingAgents;

import java.util.ArrayList;

public class Truck {
	public String TruckID;
	public int holding_capacity = 0;
	public int weight_capacity = 10;
	public ArrayList<Node> locations = new ArrayList<Node>();
	
	public Truck(String ID) {
		TruckID = ID;
	}
}
