package RoutingAgents;

public class Truck {
	public String TruckID;
	public int parcels = 0;
	public int weight_capacity = (int)(Math.random() * 10 + 1);
	public Node[] locations;
	
	public Truck(String ID) {
		TruckID = ID;
	}
}
