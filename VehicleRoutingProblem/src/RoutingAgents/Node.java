package RoutingAgents;

import java.io.Serializable;

public class Node implements Serializable{
	public String name;
	public Integer ID;
	public int x_pos;
	public int y_pos;
	public int weight;


	public Node(String Name, Integer id, int Weight, int X_pos, int Y_pos) {
		name = Name;
		ID = id;
		weight = Weight;
		x_pos = X_pos;
		y_pos = Y_pos;
	}
	
	public Node(Integer id, int Weight) {
		ID = id;
		weight = Weight;
	}

}
