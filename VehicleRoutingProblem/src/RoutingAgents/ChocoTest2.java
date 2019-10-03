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


public class ChocoTest2 {
	
	

    public static void main(String[] args) {
        // 1. Create a Model
        Model model = new Model("my first problem");
        // 2. Create variables
        IntVar x = model.intVar("X", 0, 5);                 // x in [0,5]
        IntVar y = model.intVar("Y", new int[]{2, 3, 8});   // y in {2, 3, 8}
        // 3. Post constraints
        model.arithm(x, "+", y, "<=", 8).post(); // x + y < 5
        model.times(x,y,4).post();              // x * y = 4
        // 4. Solve the problem
        model.getSolver().solve();
        // 5. Print the solution
        System.out.println(x); // Prints X = 2
        System.out.println(y); // Prints Y = 2
        
        //our shit
        IntVar capacity = model.intVar("bla", 8);
        IntVar parcels = model.intVar("TotalParcels", 20);
        IntVar[] locations = model.intVarArray("location",3,1,10,false);
        model.post(model.allDifferent(locations,"Default"));
      //  model.allDifferent(parcels,"BC").post();
        for(int i = 0; i <3; i++) {
        System.out.println(locations[i].getValue());
        	
        }
      
        IntVar bestLoc = model.intVar(locations[0].getValue());
        //IntVar bestLoc = parcels[0];
        for(IntVar loc : locations) {
        	if(loc.getValue() <= capacity.getValue()) {
        		
        	}
        	System.out.println(bestLoc);
        	model.arithm(bestLoc, ">=", capacity).post();
        	model.arithm(parcels, "<=", capacity).post();
        	model.arithm(parcels, "=", 0).post();
        	for(int i = 0; i < 3; i++) {
        		model.arithm(locations[i], "-", capacity, "=", 0).post();
        	}
        	
        	//model.arithm(capacity, op, cste)
        	//model.arithm(capacity, "<=", locations.get);
        	
        }
        
        
      while(  model.getSolver().solve()) {
        
       // System.out.println(capacity);
       System.out.println(locations);
        System.out.println(bestLoc);}
        
    }
}
