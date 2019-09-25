package RoutingAgents;

import java.util.ArrayList;

public class RoutingWorld {

	public Node [][] world = new Node [100][100];
	public void BuildWorld() {
		// TODO Auto-generated method stub
		
		 // total number of vehicles working in the world
		int vehicleNumber = 1;
		int locationNumber = 17; // the total number of locations in the world
		int [] loc_x = new int[locationNumber];
		int [] loc_y = new int[locationNumber];
		
		loc_x[0] = 45;
		loc_y[0] = 32;
		loc_x[1] = 22;
		loc_y[1] = 0;
		loc_x[2] = 91;
		loc_y[2] = 0;
		loc_x[3] = 0;
		loc_y[3] = 80;
		loc_x[4] = 11;
		loc_y[4] = 80;
		loc_x[5] = 57;
		loc_y[5] = 16;
		loc_x[6] = 79;
		loc_y[6] = 16;
		loc_x[7] = 34;
		loc_y[7] = 24;
		loc_x[8] = 68;
		loc_y[8] = 24;
		loc_x[9] = 57;
		loc_y[9] = 40;
		loc_x[10] = 91;
		loc_y[10] = 40;
		loc_x[11] = 11;
		loc_y[11] = 48;
		loc_x[12] = 22;
		loc_y[12] = 48;
		loc_x[13] = 34;
		loc_y[13] = 56;
		loc_x[14] = 68;
		loc_y[14] = 56;
		loc_x[15] = 0;
		loc_y[15] = 64;
		loc_x[16] = 79;
		loc_y[16] = 64;
		
		/*
		Node depot = world[456][320];
		Node l1 = world[228][0];
		Node l2 = world[912][0];
		Node l3 = world[0][80];
		Node l4 = world[114][80];
		Node l5 = world[570][160];
		Node l6 = world[798][160];
		Node l7 = world[342][240];
		Node l8 = world[684][240];
		Node l9 = world[570][400];
		Node l10 = world[912][400];
		Node l11= world[114][480];
		Node l12= world[228][480];
		Node l13= world[342][560];
		Node l14= world[684][560];
		Node l15= world[0][640];
		Node l16= world[798][640];
		*/
		for(int y = 0; y < 100; y++) {
			for(int x = 0; x < 100; x++) {				
				for(int i = 0; i < locationNumber; i++) {
					if(x == 45 && y == 32) {
						world[x][y] = new Node("Depot", 0, x, y);
					}
					else if(x == loc_x[i] && y == loc_y[i]) {
						world[x][y] = new Node("Location " + i, (int)(Math.random() * 10 + 1), x, y);
					}
				}
				if(world[x][y] == null) {
					world[x][y] = new Node("Space", 0, x, y);
				}
			}
		}
//		
//		final int [][] distanceMatrix = {
//				// This is a multiple dimension array reflecting distances of each node from each node. The first row is the distance of everyone from the warehouse
//		        {0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662},
//		        {548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210},
//		        {776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754},
//		        {696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358},
//		        {582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244},
//		        {274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708},
//		        {502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480},
//		        {194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856},
//		        {308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514},
//		        {194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468},
//		        {536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354},
//		        {502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844},
//		        {388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730},
//		        {354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536},
//		        {468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194},
//		        {776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798},
//		        {662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0},
//		    
//		
//
//	};
		//System.out.println(l2);
		/*
		for (int i =0; i<100; i++) {
			for(int j = 0; j<100; j++) {
				if(world[i][j].name == "Location") {
					System.out.print(world[i][j].parcels);
				}
				else if(world[i][j].name == "Depot") {
					System.out.print("|DP|");
				}
				else {
					System.out.print("|_| ");
				}
				
			}
			System.out.println("");
		}
		
		int dist = TellMeDistance(world[57][40], world[11][48]);
		ArrayList <Node> Locations = TellMeLocations();
		
		System.out.println(dist);
		System.out.println(Locations);
		for(Node a : Locations) {
			System.out.println(a.parcels);
		}
		*/
		
		
		
		 /*final int transitCallbackIndex =
			        routing.registerTransitCallback((long fromIndex, long toIndex) -> {
			          // Convert from routing variable Index to user NodeIndex.
			          int fromNode = manager.indexToNode(fromIndex);
			          int toNode = manager.indexToNode(toIndex);
			          return data.distanceMatrix[fromNode][toNode];
			        });
			    routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);*/
		
				
}
	public int TellMeDistance (Node a, Node b) {
		int x1 =a.x_pos;
		int x2 =b.x_pos;
		int y1 =a.y_pos;
		int y2 =b.y_pos;
		int dis = Math.abs((x2-x1)+(y2-y1));
		return dis;
	}
	
	public ArrayList<Node> TellMeLocations() {
		ArrayList<Node> Locations = new ArrayList<Node>();
		for (int i = 0; i<world.length;i++) {
			for(int j = 0; j<world.length;j++) {
			Node n = world[i][j];
				if(n.name.contains("Location")) {
					Locations.add(n);	
				}
			}
			
		}
		 return Locations;
	}
	
	public Node Depot(){
		//Node nod = null;
		for (int i = 0; i<world.length;i++) {
			for(int j = 0; j<world.length;j++) {
			Node n = world[i][j];
				if(n.name.contains("Depot")) {
					return n;	
				}
			}
		}
		return null;
	}
	
	
	}
	
