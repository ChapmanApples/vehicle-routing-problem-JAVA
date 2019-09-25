package RoutingAgents;

import java.util.Iterator; 
import jade.core.AID; 
import jade.core.Agent; 
import jade.core.behaviours.CyclicBehaviour; 
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class TestSenderAgent extends Agent{
	public int constraint = (int)(Math.random() * 10 + 1);
	protected void setup() {
		// First set-up message receiving behavior      
		CyclicBehaviour messageListeningBehaviour = new CyclicBehaviour(this)    
		{     
			public void action() {     
				ACLMessage msg= receive();     
				if (msg!=null) {      
					System.out.println(getLocalName()+ ": Received coordinates " + 
				msg.getContent() + " from " + msg.getSender().getLocalName());     
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
