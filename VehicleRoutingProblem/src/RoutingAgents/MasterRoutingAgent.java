package RoutingAgents;

import java.io.IOException;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;

@SuppressWarnings("serial")
public class MasterRoutingAgent extends Agent implements MyAgentInterface{
	int agentCount = 0;
	RoutingWorld world = new RoutingWorld();
	ArrayList<Node> locations = new ArrayList<Node>();
	Node[] Selected_Locations;
	Location_Assign search = new Location_Assign();
	ArrayList<Package> package_list = new ArrayList<Package>();
	ArrayList<Truck> trucks = new ArrayList<Truck>();
	ArrayList<Truck> assigned_trucks = new ArrayList<Truck>();
	
	public MasterRoutingAgent() {
		registerO2AInterface(MyAgentInterface.class, this);
		
	}
	
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
				//System.out.println(agentCount);
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
					for(int n = 0; n < trucks.size(); n++) {
						Truck not_first;
						Truck not_second;
						if(n == 0) {
							while(trucks.get(n).TruckID != 1) {
								not_first = trucks.get(n);
								trucks.remove(n);
								trucks.add(not_first);
							}
						}
						if(n == 1) {
							while(trucks.get(n).TruckID != 2) {
								not_second = trucks.get(n);
								trucks.remove(not_second);
								trucks.add(not_second);
							}
						}
						if(n == 2) {
							System.out.println(getLocalName()+ ": Recieved all capcity constraints");
						}
					}

					System.out.println("You have 3 delivery trucks with a combined capacity of 30kg");
					assigned_trucks = search.run(trucks, Selected_Locations);
				
					locations = world.TellMeLocations();
					System.out.println("There are: " + Selected_Locations.length + " Locations");
				
				
				for(Truck t: assigned_trucks) {
					if(t.TruckID == 1) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);
							String da1_route = "(";
							for(Node l: t.Locations) {
								if(l == t.Locations.get(t.Locations.size() - 1)) {
									da1_route += l.ID;
								}
								else {
									da1_route += l.ID + ", ";
								}
							}
							System.out.println("Sending route: " + da1_route + ") to DA1");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA1", AID.ISLOCALNAME) );	  
						send(msg_pos);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					if(t.TruckID == 2) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);						
							String da2_route = "(";
							for(Node l: t.Locations) {
								if(l == t.Locations.get(t.Locations.size() - 1)) {
									da2_route += l.ID;
								}
								else {
									da2_route += l.ID + ", ";
								}
							}
							System.out.println("Sending route: " + da2_route + ") to DA2");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA2", AID.ISLOCALNAME) );	  
						send(msg_pos);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(t.TruckID == 3) {
						ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
						try {
							msg_pos.setContentObject(t);						
							String da3_route = "(";
							for(Node l: t.Locations) {
								if(l == t.Locations.get(t.Locations.size() - 1)) {
									da3_route += l.ID;
								}
								else {
									da3_route += l.ID + ", ";
								}
							}
							System.out.println("Sending route: " + da3_route + ") to DA3");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						msg_pos.addReceiver(new AID("DA3", AID.ISLOCALNAME) );	  
						send(msg_pos);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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

	@Override
	public void recieveLocations(Node[] loc_list) {	
		System.out.println("Got Locations");
		Selected_Locations = loc_list;
		
	}
	
	public ArrayList<Truck> sendLocations(){
		return assigned_trucks;
		
	}

	
}
