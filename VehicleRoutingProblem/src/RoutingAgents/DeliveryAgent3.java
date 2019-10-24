package RoutingAgents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator; 
import jade.core.AID; 
import jade.core.Agent; 
import jade.core.behaviours.CyclicBehaviour; 
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class DeliveryAgent3 extends Agent{
	
	Truck da3 = new Truck(3, 15);
	
	protected void setup() {
		// First set-up message receiving behavior      
		CyclicBehaviour messageListeningBehaviour = new CyclicBehaviour(this)    
		{ 
			public void action() {     
				ACLMessage msg= receive();   
				if (msg!=null) {   
					System.out.println(getLocalName()+": Recieved Route");
					try {
						Truck da3 = ((Truck)msg.getContentObject());
						System.out.println(getLocalName()+": Going to locations: ");
						for(Node l: da3.Locations) {
							System.out.print(l.ID + " ");
						}
						System.out.println();
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				
				block();     
				}   
			};   
			addBehaviour(messageListeningBehaviour);      
					
			// Send message to Master agent (hard-coded)   
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);  
			try {
				msg.setContentObject(da3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}         
		    msg.addReceiver(new AID("MasterAgent", AID.ISLOCALNAME) );                   
			// Send Message (only once)       
			try {
				System.out.println(getLocalName()+ ": I can carry up to " + ((Truck)msg.getContentObject()).weight_capacity + " kilograms of packages ");
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			Iterator<?> receivers = msg.getAllIntendedReceiver();       
			while(receivers.hasNext()) {        
				System.out.println(((AID)receivers.next()).getLocalName());       
			}   
			send(msg); 	
	}
}
