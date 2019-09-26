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

public class PathFinder {
	
	public static void main(String[] args) {		
		
		//number of delivery agents 3
		int DA = 3;
		//number of locations
		int L = 17;
		//capacity for each delivery agent
		int [] C = new int[] {10, 15, 12};
		int [][] distanceMatrix = {
				// This is a multiple dimension array reflecting distances of each node from each node. The first row is the distance of everyone from the warehouse
		        {0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662},
		        {548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210},
		        {776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754},
		        {696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358},
		        {582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244},
		        {274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708},
		        {502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480},
		        {194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856},
		        {308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514},
		        {194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468},
		        {536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354},
		        {502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844},
		        {388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730},
		        {354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536},
		        {468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194},
		        {776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798},
		        {662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0},
		    		
	};
		
	
	Model model = new Model("RoutingProblem");
	
	//Variables
	
	BoolVar[] HasSpace = model.boolVarArray("Agent", DA);
	
	IntVar[] locations = model.intVarArray("Location", L, 1, DA, false);
	
	//IntVar[] parcels = model.intVarArray("Parcels", L, 1, 10, true);
	
	IntVar[] dist = model.intVarArray("Distance", L, 1, 1552, true);
	
	IntVar tot_dist = model.intVar("tot_dist", 0, 10000, true);
			
	//Constraints
	for(int j = 0; j < L; j++) {
		//a delivery agent has space if it's capacity isn't full
		model.element(model.intVar(1), HasSpace, locations[j], 1).post();
		//compute 'dist' for each location
		model.element(dist[j], distanceMatrix[j], locations[j], 1).post();
	}
	
	for (int i = 0; i < DA; i++) {
		IntVar occ = model.intVar("occur_" + i, 0, C[i], true);
		model.count(i+1, locations, occ).post();
		occ.ge(HasSpace[i]).post();
	}
	
	// Prepare the constraint that maintains 'tot_cost'
	int[] coeffs = new int[DA + L];
	Arrays.fill(coeffs, 0, DA, 20);
	Arrays.fill(coeffs, DA, DA + L, 1);
	// then post it
	model.scalar(ArrayUtils.append(HasSpace, dist), coeffs, "=", tot_dist).post();
	
	model.setObjective(Model.MINIMIZE, tot_dist);
	Solver solver = model.getSolver();
	solver.setSearch(Search.intVarSearch(
	    new VariableSelectorWithTies<>(
	        new FirstFail(model),
	        new Smallest()),
	    new IntDomainMiddle(false),
	    ArrayUtils.append(locations, dist, HasSpace))
	);
	solver.showShortStatistics();
	while(solver.solve()){
	    prettyPrint(model, HasSpace, DA, locations, L, tot_dist);
	}
	
}
	private static void prettyPrint(Model model, IntVar[] HasSpace, int DA, IntVar[] agents, int L, IntVar tot_dist) {
	    StringBuilder st = new StringBuilder();
	    st.append("Solution #").append(model.getSolver().getSolutionCount()).append("\n");
	    for (int i = 0; i < DA; i++) {
	        if (HasSpace[i].getValue() > 0) {
	            st.append(String.format("\tAgent %d supplies to Location : ", (i + 1)));
	            for (int j = 0; j < L; j++) {
	                if (agents[j].getValue() == (i + 1)) {
	                    st.append(String.format("%d ", (j + 1)));
	                }
	            }
	            st.append("\n");
	        }
	    }
	    st.append("\tTotal Distance: ").append(tot_dist.getValue());
	    System.out.println(st.toString());
	}
}
