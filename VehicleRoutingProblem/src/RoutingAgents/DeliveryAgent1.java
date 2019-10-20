package RoutingAgents;

import java.util.ArrayList;
import java.util.Iterator; 
import jade.core.AID; 
import jade.core.Agent; 
import jade.core.behaviours.CyclicBehaviour; 
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class DeliveryAgent1 extends Agent{
	
	//public int constraint = (int)(Math.random() * 20 + 5);
	RoutingWorld world = new RoutingWorld(); 
	ArrayList<Node> locations = new ArrayList<Node>();
	Truck da1 = new Truck(getLocalName());
	
	protected void setup() {
		world.BuildWorld();
		// First set-up message receiving behavior      
		CyclicBehaviour messageListeningBehaviour = new CyclicBehaviour(this)    
		{ 
			public void action() {     
				locations = world.TellMeLocations();
				Node dest = new Node("default",0, 0, 0, 0);
				ACLMessage msg= receive();   
				if (msg!=null) {   
					String loc_name = msg.getContent();
					for(Node a : locations) {
						if(a.name.contains(loc_name)) {
							dest = a;
						}
					}
					System.out.println(getLocalName()+ ": Received coordinates (" + 
							dest.x_pos + ", " + dest.y_pos + ") from " + msg.getSender().getLocalName()); 
					//query for route
					//ACLMessage howmsg = new ACLMessage(ACLMessage.INFORM);
					//howmsg.setContent("Route to " + dest.name + " ?");
				    //howmsg.addReceiver(new AID("MasterAgent", AID.ISLOCALNAME) );  
				    //System.out.println(getLocalName() + ": How do I get to " + dest.name + " ?");
				    //send(howmsg);
					//System.out.println(getLocalName() + ": Route confirmed. Going to "+ dest.name);
					}
				
				block();     
				}   
			};   
			addBehaviour(messageListeningBehaviour);      
					
			// Send message to Master agent (hard-coded)   
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);  
			msg.setPerformative(da1.weight_capacity);         
		    msg.addReceiver(new AID("MasterAgent", AID.ISLOCALNAME) );                   
			// Send Message (only once)       
			System.out.println(getLocalName()+ ": I can carry up to " + msg.getPerformative() + " kilograms of packages ");     
			Iterator<?> receivers = msg.getAllIntendedReceiver();       
			while(receivers.hasNext()) {        
				System.out.println(((AID)receivers.next()).getLocalName());       
			}   
			send(msg); 	
	}
}
