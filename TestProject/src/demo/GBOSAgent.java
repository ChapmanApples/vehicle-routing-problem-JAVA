package demo;

import jade.core.Agent;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class GBOSAgent extends Agent{
	protected void setup() {
		System.out.println(getLocalName()+": I have been created");
		addBehaviour(new MyOneShotBehaviour());
		addBehaviour(new MyMultiStepBehaviour());
		System.out.println(getLocalName() + ": I have added my behaviour");
	}
	
	private class MyOneShotBehaviour extends OneShotBehaviour{
		
		MyOneShotBehaviour(){
			System.out.println(getBehaviourName() + ": I have been created");
		}

		@Override
		public void action() {
			// TODO Auto-generated method stub
			System.out.println(getBehaviourName()+ ": I will be executed only once");
		}
		
	}

	private class MyMultiStepBehaviour extends Behaviour{
		private int step = 1;
		
		MyMultiStepBehaviour(){
			System.out.println(getBehaviourName()+":I have been created");
		}
		public void action() {
			switch(step) {
			case 1: 
				System.out.println(getBehaviourName()+": Operation 1");
				break;
			case 2:
				System.out.println(getBehaviourName()+": Operation 2");
				break;
			case 3:
				System.out.println(getBehaviourName()+": Operation 3");
				break;
			case 4:
				System.out.println(getBehaviourName()+": Operation 4");
				break;
			}
			step++;
		}
		public boolean done() {
			return step == 5;
		}
		public int onEnd() {
			System.out.println(this.getBehaviourName()+ ": I have finished executing");
			return super.onEnd();
		}
	}
}
