package RoutingAgents;

public class Node {
	public String name;
	public int ID;
	public int x_pos;
	public int y_pos;
	public int weight;


	public Node(String Name, int id, int Weight, int X_pos, int Y_pos) {
		name = Name;
		ID = id;
		weight = Weight;
		x_pos = X_pos;
		y_pos = Y_pos;
	}

}
