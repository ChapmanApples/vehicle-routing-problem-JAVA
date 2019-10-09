package RoutingAgents;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelectorWithTies;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;
import java.util.Arrays;

public class TestChoco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
// number of delivery agents
		int DA = 3;
		// number of locations
		int L = 16;
		// Model object
		Model model = new Model("RoutingProblem");
		//Defining BoolVar Variable.
		BoolVar[] HasSpace = model.boolVarArray("HasSpace", 3); //creates a boolean array of length 3
		IntVar[] Capacity = model.intVarArray("Capacities",DA, 10,20); // creates an integer array of length 3 with numbers between 1 and 20
		IntVar[] Parcels = model.intVarArray("Parcels",L,1,20); // creates an integer array of length 16 between 1 and 20
		
		 
		//Modeling
		
		for(int i = 0;i<3;i++) {
			for(int j=0;j<=i;j++) {
				model.arithm(Capacity[i],">",Parcels[i]).post();
			}
		}
			
			
			
			
		
		Solver s = model.getSolver();
		s.solve();
		
		System.out.println(Capacity);
		System.out.println(Parcels);	
	}
}
