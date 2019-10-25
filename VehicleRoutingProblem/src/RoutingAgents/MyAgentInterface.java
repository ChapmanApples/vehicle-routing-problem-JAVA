package RoutingAgents;

import java.util.ArrayList;

public interface MyAgentInterface {
	public void recieveLocations(Node[] loc_list);
	public ArrayList<Truck> sendLocations();
}
