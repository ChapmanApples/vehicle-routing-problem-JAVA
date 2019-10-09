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
	RoutingWorld world = new RoutingWorld(); 
	ArrayList<Node> locations = new ArrayList<Node>();
	
	protected void setup() {
		world.BuildWorld();
		System.out.println("Enter number of parcels to be delivered: ");
		Scanner input = new Scanner(System.in);
		int parcels = input.nextInt();
		input.close();
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
			
			AID da1 = getAID("DeliveryAgent1");
			AID da2 = getAID("DeliveryAgent2");
			AID da3 = getAID("DeliveryAgent3");
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
					// Print out message content      
					System.out.println(getLocalName()+ ": Received Capacity Constraints: " + msg.getSender().getLocalName() + " can carry " + msg.getPerformative() 
					+ " parcels");      
					}  
				locations = world.TellMeLocations();
				System.out.println("There are: " + locations.size() + " Locations");
				System.out.println("There are: " + parcels + " parcels");
				Node dest = new Node("loc", 0, 0, 0);
				
				for(Node a : locations) {
					if(a.parcels < msg.getPerformative() ) {
						dest = a;
						System.out.println(getLocalName() + ": Sending " + msg.getSender().getLocalName() + " to " + a.name + " with "
					+ a.parcels + " parcels to be delivered");
						break;
					}
				}
				ACLMessage msg_pos = new ACLMessage(ACLMessage.INFORM);
				msg_pos.setContent(dest.name);
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
