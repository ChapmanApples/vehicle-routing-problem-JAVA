package RoutingAgents;

public class Truck {
	public String TruckID;
	public int holding_capacity = 0;
	public int weight_capacity = 10;
	public Node[] locations;
	
	public Truck(String ID) {
		TruckID = ID;
	}
}
