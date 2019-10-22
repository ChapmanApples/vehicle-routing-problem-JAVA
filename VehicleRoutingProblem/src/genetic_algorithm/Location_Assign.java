package genetic_algorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Location_Assign {
	
	private static int location[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	private static int location_optimized[]  = {};
	private static int Total_Sum=999999999;
	
	
//	public Location_Assign(int[] loc1, int[] loc2, int[] loc3) {
//		Truck1 = loc1;
//		Truck2 = loc2;
//		Truck3 = loc3;		
//		}
	
//	public void Start() {
//		optimize1= new Optimum_pathfinder(Truck1);
//		optimize2= new Optimum_pathfinder(Truck2);
//		optimize3= new Optimum_pathfinder(Truck3);
//		
//		Truck1return = optimize1.run();
//		Truck2return = optimize2.run();
//		Truck3return = optimize3.run();
//		
//		  
//	}
	


	public static void main(String[] args) {
		
		
		Node[] Selected_Locations = {new Node(1,2), new Node(2,3), new Node(3,2), new Node(4,1), new Node(5,1), new Node(6,3),new Node(7,2),new Node(8,2),new Node(9,2),new Node(10,2),new Node(11,2),new Node(12,2),new Node(13,2),new Node(14,2),new Node(15,2)};
		List<Node> Selected_Locations_List = new ArrayList<Node>();
			
		List<Integer> Location = new ArrayList<Integer>();
		
		for(Node i:Selected_Locations) {
			Location.add(i.id);
			Selected_Locations_List.add(i);
		}
		int[] Location_array =Location.stream().mapToInt(i->i).toArray();
		
		//optimize
		
		Optimum_pathfinder optimize = new Optimum_pathfinder(Location_array);
		location_optimized = optimize.run();
	
		
		Truck_l truck1 = new Truck_l(1,15);
		Truck_l truck2 = new Truck_l(2,10);
		Truck_l truck3 = new Truck_l(3,25);
		System.out.println();
		for(int s:location_optimized) {
			System.out.print(s+" ");
		}
		
		
		for(int s:location_optimized) {
			int j = 0;
			for(Node select:Selected_Locations) {
				
				if (select.id==s) {
					
					if (truck1.weight_capacity>=select.weight) {
						truck1.addNode(select);
						Selected_Locations_List.remove(j);	
					}				
					else
						if(truck2.weight_capacity>=select.weight) {
							truck2.addNode(select);
							Selected_Locations_List.remove(j);
					}
					else
						if(truck3.weight_capacity>=select.weight) {
							truck3.addNode(select);
							Selected_Locations_List.remove(j);
					}
				}
			}
			j++;
		}
		int[] Truck1= new int[truck1.Locations.size()];
		int[] Truck2= new int[truck2.Locations.size()];
		int[] Truck3= new int[truck3.Locations.size()];
		
		
		
		
		System.out.println();
		
		System.out.println("Truck 1");
		int i=0;
		for(Node s:truck1.Locations) {
			Truck1[i]=s.id;
			System.out.print(s.id+" ");
			i++;
		};

		System.out.println();
		i=0;
		System.out.println("Truck 2");
		for(Node s:truck2.Locations) {
			Truck2[i]=s.id;
			System.out.print(s.id+" ");
			i++;
		}

		System.out.println();
		i=0;
		System.out.println("Truck 3");
		for(Node s:truck3.Locations) {
			Truck3[i]=s.id;
			System.out.print(s.id+" ");
			i++;
		}
		System.out.println();
		System.out.println("Truck 1");
		for(int trk:Truck1) {
			System.out.print(trk+" ");
		}
		
		System.out.println();
		System.out.println("Truck 2");
		for(int trk:Truck2) {
			System.out.print(trk+" ");
		}
		
		System.out.println();
		System.out.println("Truck 3");
		for(int trk:Truck3) {
			System.out.print(trk+" ");
		}
		
		
		int Total_running_sum=optimize.getSum(Truck1)+optimize.getSum(Truck2)+optimize.getSum(Truck3);
		System.out.println("Total:"+Total_Sum);
		if (Total_running_sum<Total_Sum) {
			Total_Sum = Total_running_sum;
		}
		System.out.println("Total:"+Total_Sum);
		
		Optimum_pathfinder findA = new Optimum_pathfinder(Truck1);
		Truck1 = findA.run();
		
		Optimum_pathfinder findB = new Optimum_pathfinder(Truck2);
		Truck2 = findB.run();
		
		Optimum_pathfinder findC = new Optimum_pathfinder(Truck3);
		Truck3 = findC.run();
		
		Total_running_sum = optimize.getSum(Truck1)+optimize.getSum(Truck2)+optimize.getSum(Truck3);
		
		if (Total_running_sum<Total_Sum) {
			Total_Sum = Total_running_sum;
		}
		System.out.println("Total:"+Total_Sum);
		
		
		
		
		
		
	}

}
class Truck_l {
	public int TruckID;
	public int holding_capacity = 0;
	public int weight_capacity = 10;
	public Node[] locations;
	public List<Node> Locations;
	
	public Truck_l(int ID, Integer Weight) {
		TruckID = ID;
		weight_capacity = Weight;
		Locations = new ArrayList<Node>();
	}
	
	public void addNode(Node addNode) {
		Locations.add(addNode);
		weight_capacity = weight_capacity-addNode.weight;
	}
	
}

class Node{
	
	public Integer id;
	public Integer weight;
	
	public Node(Integer id, Integer weight) {
		this.id = id;
		this.weight = weight;
	}
	
	
}
