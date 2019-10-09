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


class Parcel{
	public int wt;
}

class Agent{
	public int cap;
}




public class Choco3 {
	
		
	
	
	/**
	 *  public static void main(String[] args) {
        // 1. Create a Model
        Model model = new Model("my first problem");
        // 2. Create variables
        IntVar x = model.intVar("X", 0, 5);                 // x in [0,5]
        IntVar y = model.intVar("Y", new int[]{2, 3, 8});   // y in {2, 3, 8}
        // 3. Post constraints
        model.arithm(x, "+", y, "<=", 2).post(); // x + y < 5
        model.times(x,y,3).post();              // x * y = 4
        // 4. Solve the problem
        model.getSolver().solve();
        // 5. Print the solution
        System.out.println(x); // Prints X = 2
        System.out.println(y); // Prints Y = 2
    }
	 * @param args
	 */
	
	public static int sum(int x, int y, int z) {
		return x+y+z;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model model = new Model("asshole");
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		Agent a3 = new Agent();
		
		a1.cap = 5;
		a2.cap = 17;
		a3.cap = 13;
		
		Parcel p1 = new Parcel();
		Parcel p2 = new Parcel();
		Parcel p3 = new Parcel();
		p1.wt = 10;
		p2.wt = 3;
		p3.wt = 15;
		
	//	IntVar Weightsum = model.intvarsum(p1.wt,p2.wt,p3.wt);
		int Capacitysum = sum(a1.cap,a2.cap,a3.cap);
		
		
		IntVar parcelWeight = model.intVar("parcel weight", new int[]{p1.wt,p2.wt,p3.wt});
		IntVar AgentCapacity = model.intVar("agent capacity", new int[] {a1.cap,a2.cap,a3.cap});
		IntVar[] parcelWeightArray = model.intVarArray(3, 1, 20); // an array of length 3 between 1 and 20
		IntVar[] AgentCapacityArray = model.intVarArray(3, 1, 20); // an array of length 3 between 1 and 20
		System.out.println("somthing"+parcelWeightArray[0]);
		model.arithm(parcelWeightArray[0], "-", 20).post();
	//	model.sum(parcelWeightArray,"<",model.sum(AgentWeightArray));
		//model.arithm(var1, op, var2);
		
		System.out.println(parcelWeightArray[0]);
		for(int i = 0; i<4; i ++) {
			model.arithm(parcelWeightArray[i], "-", AgentCapacityArray[i]);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
