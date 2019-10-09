package RoutingAgents;

import java.util.Arrays;
import java.util.Collections;

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
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.util.tools.ArrayUtils;

public class Choco {
	
		/**
		 * parcels {parcel1, co-ordinate1, parcel2, co-ordinate2, parcel3, co-ordinate3}
		 * vehicle has a limit
		 */
	
		/**
		 * 
		 */
		public static void trialmethod() {
			Model mod = new Model("Contraint Solving example");
			int parcels = 6;			
			//int da=3; 
			int da[] = {3,8,3};
			
			 BoolVar[][] assignment =mod.boolVarMatrix(parcels, da.length);
			 	
			 

				// Each package should be assigned exactly once
			        for (int i = 0 ; i < parcels ; i ++) {
			            BoolVar b =mod.sum(assignment[i],"=",1).reify();
			            System.out.println(b);
			        }     			       
			    	
			 
			// Each car should have three or less packages
		        for (int i = 0; i < da.length; i++) {
		      
		            BoolVar bool = mod.sum(getColumn(assignment, i),"<=",3).reify();
		            
		        }
		        
			 
			        Solver s = mod.getSolver();
			        s.solve();
			       

				// Print out the solution'
			        if (s.solve()){
			        print(parcels,da.length, assignment);			
			        }
		}
		// Attribute :: https://stackoverflow.com/questions/30426909/get-columns-from-two-dimensional-array-in-java
	    public static BoolVar[] getColumn(BoolVar[][] array, int index){
	        BoolVar[] column = new BoolVar[array[0].length]; // Here I assume a rectangular 2D array!
	        for(int i=0; i<column.length; i++){
	            column[i] = array[i][index];
	        }
	        return column;
	    }
	    
	    public static void print(int parcels,int da, BoolVar[][] assignment) {
		    for(int i = 0; i < parcels; i++) {
	            for (int j = 0; j < da; j++) {
	                System.out.print(assignment[i][j].getValue()+"\t");
	            }
	            System.out.print("\n");
	        }
	        System.out.println("------------------------------*--------------------------");
	    }
		

	    
	    public static void HelloWorld() {

	    		
	           // The model is the main component of Choco Solver
	           Model model = new Model("Choco Solver Hello World");
	           IntVar one = model.intVar(1);
	           BoolVar two = model.boolVar(false);
	           System.out.println(two.getValue());
	           // Integer variables
	           IntVar a = model.intVar("a", new int[]{4, 6, 8}); // takes value in { 4, 6, 8 }
	           IntVar b = model.intVar("b", 0, 3); // takes value in [0, 2]
	           System.out.println(b);
	           // Add an arithmetic constraint between a and b
	           // BEWARE : do not forget to call post() to force this constraint to be satisfied
	           
	           model.arithm(a,"+",b,"<",9).post();

	           int i = 1;
	           // Computes all solutions : Solver.solve() returns true whenever a new feasible solution has been found
	           while (model.getSolver().solve()) {
	              System.out.println("Solution " + i++ + " found : " + a + ", " + b);
	           }
	       
	    }
	    
	    public static void ChocSet() {
	    	 Model newmodel = new Model("Choco");
	    	 int capacity = 15;
	    	 
	    	 IntVar a = newmodel.intVar("a", new int[]{4, 6, 8}); // takes value in { 4, 6, 8 }
	    	 IntVar b = newmodel.intVar("b", 0, 2); // takes value in [0, 2]
	    	 
	    	 
	    	 newmodel.arithm(a, "+", b ,"<", capacity).post();
	    	 
	    	 Solver Solv = newmodel.getSolver();
	    	 
	    	 int i = 0;
	    	 while(Solv.solve()) {
	    		 System.out.println(++i +". "+a+" "+b);
	    	 }
	    	 
	    	
	    }
	    
	    public static void EightQ() {
	    	int n = 8;
	    	Model model = new Model(n+ "q queens prob");
	    	IntVar[] vars = new IntVar[n];
	    	
	    	for(int q= 0;q<n;q++) {
	    		vars[q] = model.intVar("Q_"+q, 1, n);
	    	}
	    	
	    	for(int i = 0;i<n-1;i++) {
	    		for(int j =i+1;j<n;j++) {
	    			model.arithm(vars[i],"!=", vars[j]).post();
	    			model.arithm(vars[i],"!=",vars[j],"-",j-i).post();
	    			model.arithm(vars[i],"!=",vars[j],"+",j-i).post();
	    		}
	    	}
	    	
	    	//model.getSolver().setSearch(Search.domOverWDegSearch(vars));
	    	
	    	Solution sol = model.getSolver().findSolution();
	    	if (sol!=null) {
	    		System.out.println(sol.toString());
	    	}
	    }
	    
	    
	  
		
		
		public static Integer Sum(Integer[] x) {
			Integer sum=0;
			for	(int y: x) {
				sum+=y; 
			}
			return sum;
		}
		
		
		public static void without_choc() {
			boolean return_val = true;
			int agentnum=0;
			
			Integer[] parcels = new Integer[] {5,6,8,9,5,3,5,4};
			Integer[] agentcap= new Integer[] {30,45,32};
			System.out.println(Sum(parcels));
			System.out.println(Sum(agentcap));
			
			if(Sum(parcels)>Sum(agentcap)) {
				return_val = false;
			}
			
			Arrays.sort(agentcap,Collections.reverseOrder());
			Integer newint = Sum(parcels);
			
			do{
				System.out.println("print");
			}while (newint-agentcap[agentnum++]<=0);
			
			
			
			System.out.println(agentnum);
			
			if(return_val)
				System.out.println("Number of Agents required "+agentnum);
			else
				System.out.println("Given conditions are wrong");
		}
		
		
		
		
		public static void warehouse_prob()
		{
			// load parameters
			// number of warehouses
			int W = 5;
			// number of stores
			int S = 10;
			// maintenance cost
			int C = 30;
			// capacity of each warehouse
			int[] K = new int[]{1, 4, 2, 1, 3};
			// matrix of supply costs, store x warehouse
			int[][] P = new int[][]{
			    {20, 24, 11, 25, 30},
			    {28, 27, 82, 83, 74},
			    {74, 97, 71, 96, 70},
			    {2, 55, 73, 69, 61},
			    {46, 96, 59, 83, 4},
			    {42, 22, 29, 67, 59},
			    {1, 5, 73, 59, 56},
			    {10, 73, 13, 43, 96},
			    {93, 35, 63, 85, 46},
			    {47, 65, 55, 71, 95}};

			// A new model instance
			Model model = new Model("WarehouseLocation");

			// VARIABLES
			// a warehouse is either open or closed
			BoolVar[] open = model.boolVarArray("o", W);		//open[5] = boolVarArray{0,1} 
			// which warehouse supplies a store
			IntVar[] supplier = model.intVarArray("supplier", S, 1, W, false);	//supplier[0-10] = []			intVarArray(String name,int size,int lb,int ub)
			// supplying cost per store
			IntVar[] cost = model.intVarArray("cost", S, 1, 96, true);	//
			// Total of all costs
			IntVar tot_cost = model.intVar("tot_cost", 0, 99999, true);
			
			
			// CONSTRAINTS
			for (int j = 0; j < S; j++) {
			    // a warehouse is 'open', if it supplies to a store
				IntVar sl = model.intVar(1);
				System.out.println(supplier[j].getValue());
				
			    model.element(sl, open, supplier[j], 1).post();
			    System.out.println(sl);
			    // Compute 'cost' for each store
			    System.out.println(cost[j].getValue());
			    
			    System.out.println("supplier "+supplier[j].getValue());
			    
			    System.out.println(supplier[j].getValue());
			   // System.out.println("P "+P[j][1]);
			    
			    model.element(cost[j], P[j], supplier[j], 1).post();
			    //System.out.println("sl: "+sl);
			}
			System.out.println("sdf"+cost[0]);
			for (int i = 0; i < W; i++) {
			    // additional variable 'occ' is created on the fly
			    // its domain includes the constraint on capacity
			    IntVar occ = model.intVar("occur_" + i, 0, K[i], true);
			    // for-loop starts at 0, warehouse index starts at 1
			    // => we count occurrences of (i+1) in 'supplier'
			    model.count(i+1, supplier, occ).post();
			    // redundant link between 'occ' and 'open' for better propagation
			    occ.ge(open[i]).post();
			}
			// Prepare the constraint that maintains 'tot_cost'
			int[] coeffs = new int[W + S];
			Arrays.fill(coeffs, 0, W, C);
			Arrays.fill(coeffs, W, W + S, 1);
			
			// then post it
			model.scalar(ArrayUtils.append(open, cost), coeffs, "=", tot_cost).post();

			model.setObjective(Model.MINIMIZE, tot_cost);
			Solver solver = model.getSolver();
			solver.setSearch(Search.intVarSearch(
			    new VariableSelectorWithTies<>(
			        new FirstFail(model),
			        new Smallest()),
			    new IntDomainMiddle(false),
			    ArrayUtils.append(supplier, cost, open))
			);
			solver.showShortStatistics();
			while(solver.solve()){
			    //prettyPrint(model, open, W, supplier, S, tot_cost);
			}

		}
		

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public static void warehouse_1(){
	int W = 5;
	int S = 10;
	int C = 30;

	int[] K = new int[] {1,4,2,1,3};
	 	
	int[][] P = new int[][]{
			    {20, 24, 11, 25, 30},
			    {28, 27, 82, 83, 74},
			    {74, 97, 71, 96, 70},
			    {2, 55, 73, 69, 61},
			    {46, 96, 59, 83, 4},
			    {42, 22, 29, 67, 59},
			    {1, 5, 73, 59, 56},
			    {10, 73, 13, 43, 96},
			    {93, 35, 63, 85, 46},
			    {47, 65, 55, 71, 95}};

	Model model = new Model("location");
	BoolVar[] open = model.boolVarArray("o",W);
	IntVar[] supplier = model.intVarArray("supplier", S, 1, W, false);	//supplier[0-10] = []			intVarArray(String name,int size,int lb,int ub)
	IntVar[] cost = model.intVarArray("cost", S, 1, 96, true);	//
	IntVar tot_cost = model.intVar("tot_cost", 0, 99999, true);
	
	
	for(int j=0;j<S;j++) {
		IntVar s1 = model.intVar(1);
		
		model.element(s1,open, supplier[j],1).post();
	
		
		model.element(cost[j], P[j],supplier[j],1).post();
		//supplierj -1=2 
		
		
	}
	
	model.getSolver().solve();
	for(int j=0;j<W;j++) {
		System.out.println(open[j]);
		//System.out.println();		
	}
	
	
	//System.out.println("S1"+s1);
	System.out.println(open[0]);
	System.out.println(supplier[0]);
	System.out.println(cost[0]);
	System.out.println(tot_cost);
}

	
		 /**
		 * @param args
		 */
		/**
		 * @param args
		 */
		public static void main(String[] args) {
		        // 1. Create a Model
		        Model model = new Model("my first problem");
		        // 2. Create variables
		        IntVar x = model.intVar("X", new int[] {1,2,3,4,5,6,7});                 // x in [0,5]
		        IntVar y = model.intVar("Y", new int[]{2, 3, 8});   // y in {2, 3, 8}
		        // 3. Post constraints
		        //model.arithm(x,"+",y,"<", 5).post(); // x + y < 5
		       
		        //model.times(x,y,4).post();              // x * y = 4
		        
		        model.arithm(x,"<", 5).post(); 
		        
		        // 4. Solve the problem
		        //System.out.print(model.getSolver().solve());
		        Solver sol = model.getSolver();
		        
		       while(sol.solve()==false) {
		        // 5. Print the solution
		        System.out.println(x); // Prints X = 2
		       }
		        
		        //without_choc();
		        //trialmethod();
		       //HelloWorld();
		        //ChocSet();
		        warehouse_prob();
		       //EightQ();
		       //warehouse_1();
		    }

	}


