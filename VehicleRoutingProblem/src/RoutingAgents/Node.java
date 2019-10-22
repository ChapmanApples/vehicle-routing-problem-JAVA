package RoutingAgents;

public class Node {
	public String name;
	public int ID;
	public int parcels_weight;
	public int x_pos;
	public int y_pos;
	
	public Node(String Name, int id, int Parcels, int X_pos, int Y_pos) {
		name = Name;
		ID = id;
		parcels_weight = Parcels;
		x_pos = X_pos;
		y_pos = Y_pos;
	}
	
}
