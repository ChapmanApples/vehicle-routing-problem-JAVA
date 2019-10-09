package RoutingAgents;

import java.util.ArrayList;
import java.util.Iterator; 
import jade.core.AID; 
import jade.core.Agent; 
import jade.core.behaviours.CyclicBehaviour; 
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class TestSenderAgent extends Agent{
	public int constraint = (int)(Math.random() * 10 + 1);
	RoutingWorld world = new RoutingWorld(); 
	ArrayList<Node> locations = new ArrayList<Node>();
	
	protected void setup() {
		world.BuildWorld();
		// First set-up message receiving behavior      
		CyclicBehaviour messageListeningBehaviour = new CyclicBehaviour(this)    
		{ 
			public void action() {     
				locations = world.TellMeLocations();
				Node dest = new Node("loc", 0, 0, 0);
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
					}     
				block();     
				}   
			};   
			addBehaviour(messageListeningBehaviour);      
					
			// Send message to Master agent (hard-coded)   
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);  
			msg.setPerformative(constraint);         
		    msg.addReceiver(new AID("MasterAgent", AID.ISLOCALNAME) );                   
			// Send Message (only once)       
			System.out.println(getLocalName()+ ": Sending message: I can carry " + msg.getPerformative() + "parcels to ");     
			Iterator<?> receivers = msg.getAllIntendedReceiver();       
			while(receivers.hasNext()) {        
				System.out.println(((AID)receivers.next()).getLocalName());       
			}   
			send(msg); 	
	}
}
