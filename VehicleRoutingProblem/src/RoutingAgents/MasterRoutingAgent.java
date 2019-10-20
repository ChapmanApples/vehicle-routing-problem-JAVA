package RoutingAgents;

import java.util.ArrayList;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;

@SuppressWarnings("serial")
public class MasterRoutingAgent extends Agent{
	int agentCount = 0;
	int loc_numb;
	int packages;
	int weight;
	int totalWeight = 30;
	Node location;
	RoutingWorld world = new RoutingWorld();
	ArrayList<Node> locations = new ArrayList<Node>();
	ArrayList<Package> package_list = new ArrayList<Package>();
	ArrayList<Truck> trucks = new ArrayList<Truck>();
	
	protected void setup() {
		
		world.BuildWorld();		
		AMSAgentDescription [] agents = null;        
		try {             
			SearchConstraints c = new SearchConstraints();             
			c.setMaxResults ((long) -1);   
				agents = AMSService.search( this, new AMSAgentDescription(), c );   
				}   
				catch (Exception e) {             
				System.out.println( "Problem searching AMS: " + e );             
				e.printStackTrace();   
				} 
			
			AID da1 = getAID("DA1");
			AID da2 = getAID("DA2");
			AID da3 = getAID("DA3");
			
			for (int i=0; i<agents.length;i++)   {    
				AID agentID = agents[i].getName(); 
				if(agentID.equals(da1) || agentID.equals(da2) || agentID.equals(da3)) {
					agentCount ++;
				}
				//agentCount ++;
				System.out.println(     
						( agentID.equals(da1) || agentID.equals(da2) || agentID.equals(da3) ? "*** " : "    ")        
						+ i + ": " + agentID.getName());
				}
			if(agentCount > 0) {
				System.out.println("We have " + agentCount + " Delivery Agents");
			}
			
			CyclicBehaviour msgReceivingBehaviour =  (new CyclicBehaviour(this)    {  
			public void action() {       
				System.out.println(getLocalName() + ": Waiting for Capacity Constraints");  
				for(int i = 0; i < agentCount; i++) {	
				ACLMessage msg= receive();     
				if (msg!=null) {      
						System.out.println(getLocalName()+ ": Received Capacity Constraints: " + msg.getSender().getLocalName() + " can carry up to " + msg.getPerformative() 
						+ " kg of parcels");     
				}  
				System.out.println("You have 3 delivery trucks with a combined capacity of 30kg");
				
				String again = "yes";
				Scanner input = new Scanner(System.in);
				Truck DA1 = new Truck("DeliveryAgent1");
				trucks.add(DA1);
				Truck current_truck = trucks.get(0);
				
				while(again.contains("yes")) {
					int t = 0;
					System.out.println("Enter location number to recieve packages: ");
					loc_numb = input.nextInt();
					
					for(Node a: locations) {
						if(a.ID == loc_numb) {
							location = a;
						}
					}
					
					System.out.println("Enter number of packages to be delivered: ");
					packages = input.nextInt();
					
					System.out.println("Enter weight for each package in kg: ");
					weight = input.nextInt();
					
					int total_weight = weight * packages;
					System.out.println("Total weight: " + total_weight);
					
					Package parcel = new Package(location, total_weight);
					package_list.add(parcel);
					
					if(parcel.weight <= current_truck.weight_capacity) {
						current_truck.locations[0] = parcel.location;
						current_truck.holding_capacity = parcel.weight;
						current_truck.weight_capacity -= parcel.weight;
						System.out.println("Your packages have been added to a delivery truck!");
					}else {
						
					}
					
					System.out.println("Do you wish to deliver to another location? (yes/no)");
					
					again = input.next().toLowerCase(); 
				}
				
				input.close();
				locations = world.TellMeLocations();
				System.out.println("There are: " + locations.size() + " Locations");
				System.out.println("There are: " + packages + " parcels");
				
				for(Package p : package_list) {
					if(p.weight < msg.getPerformative() ) {
						
						System.out.println(getLocalName() + ": Sending " + msg.getSender().getLocalName() + " to " + p.location.name + " with "
								+ packages + " parcels to be delivered");
					}
				}
				ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
				msg_pos.setContent();
				msg_pos.addReceiver(new AID(msg.getSender().getLocalName(), AID.ISLOCALNAME) );	  
				send(msg_pos);
				}
				// Continue listening //    
				block();  
				
				}
			} );
			
			addBehaviour(msgReceivingBehaviour);
	}
	
}
