package RoutingAgents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;

@SuppressWarnings("serial")
public class MasterRoutingAgent extends Agent{
	int agentCount = 0;
	int loc_numb;
	//List<int> all_loc = new List<int>();
	int packages;
	int weight;
	int totalWeight = 30;
	Node location; 
	RoutingWorld world = new RoutingWorld();
	ArrayList<Node> locations = new ArrayList<Node>();
	Node[] Selected_Locations = {new Node(1,2), new Node(2,3), new Node(3,2), new Node(4,1), new Node(5,1), new Node(6,3),new Node(7,2),new Node(8,2),new Node(9,2),new Node(10,2),new Node(11,2),new Node(12,2),new Node(13,2),new Node(14,2),new Node(15,2)};
	Location_Assign search = new Location_Assign();
	ArrayList<Package> package_list = new ArrayList<Package>();
	public ArrayList<Truck> trucks = new ArrayList<Truck>();
	ArrayList<Truck> assigned_trucks = new ArrayList<Truck>();
	
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
						try {
							System.out.println(getLocalName()+ ": Received Capacity Constraints: " + msg.getSender().getLocalName() + " can carry up to " + ((Truck)msg.getContentObject()).weight_capacity 
							+ " kg of parcels");
							trucks.add((Truck)msg.getContentObject());
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}     
				}  
				
				for(Truck t: trucks) {
					System.out.println(t.weight_capacity);
				}
				if(trucks.size() == 3) {
					System.out.println("You have 3 delivery trucks with a combined capacity of 30kg");
					assigned_trucks = search.run(trucks, Selected_Locations);
					
					
					for(Truck t: assigned_trucks) {
						System.out.println(t.TruckID);
					}
				
				
				locations = world.TellMeLocations();
				System.out.println("There are: " + locations.size() + " Locations");
				//System.out.println("There are: " + packages + " parcels");
				
				
				
				/*
				for(Package p : package_list) {
					if(p.weight < msg.getPerformative() ) {
						
						System.out.println(getLocalName() + ": Sending " + msg.getSender().getLocalName() + " to " + p.location.name + " with "
								+ packages + " parcels to be delivered");
					}
				}
				*/
				
				for(Truck t: assigned_trucks) {
					if(t.TruckID == 1) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);						
							System.out.println("Sending route to DA1");
							for(Node l: t.Locations) {
								System.out.println(l.ID);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA1", AID.ISLOCALNAME) );	  
						send(msg_pos);
						}
					
					if(t.TruckID == 2) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);						
							System.out.println("Sending route to DA2");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA2", AID.ISLOCALNAME) );	  
						send(msg_pos);
						}
					if(t.TruckID == 3) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);						
							System.out.println("Sending route to DA3");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA3", AID.ISLOCALNAME) );	  
						send(msg_pos);
						}
					
					}
				}
					
				}
				// Continue listening //    
				block();  
				
				}
			} );
			
			addBehaviour(msgReceivingBehaviour);
	}
	
}
