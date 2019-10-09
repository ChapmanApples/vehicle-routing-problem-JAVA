package RoutingAgents;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.selectors.values.IntDomainMiddle;
import org.chocosolver.solver.search.strategy.selectors.variables.FirstFail;
import org.chocosolver.solver.search.strategy.selectors.variables.Smallest;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelectorWithTies;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;
import java.util.Arrays;

public class TruckLocation {
/**Model model = new Model("lab problem");

int p = 5;
int t = 5;
//IntVar x = model.intVar("X", 0, 5);   
//BoolVar[] Trucks = model.boolVarArray(3); //3 trucks
//BoolVar[] Parcels = model.boolVarArray(4); //4 parcels
BoolVar[][] TruckParcel = model.boolVarMatrix(t, p);
 
//boolean[][] TruckParcel = new boolean [4][3] {Trucks,Parcels};
for (int i = 0; i < t; i++) {
	model.sum(TruckParcel[i], "=", 1).post();
}
for (int i = 0; i < p; i++) {
	model.sum(getColumn(TruckParcel, i),"<=",2).post();
}
public static BoolVar[] getColumn(BoolVar[][] array, int index){
	BoolVar[] column = new BoolVar[array[0].length]; // Here I assume a rectangular 2D array! 
    for(int i=0; i<column.length; i++){
       column[i] = array[i][index];
    }
    return column;
}
*/
	public static BoolVar[] getColumn(BoolVar[][] array, int index) {
		BoolVar[]column = new BoolVar[array[0].length];
		for(int i = 0; i <column.length;i++) {
			column [i] = array[i][index];
		}
		return column;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model m = new Model ("Assigning trucks to Locations");
int loc = 15;
int trucks = 3;

BoolVar[][] TruckLocation = m.boolVarMatrix(loc,trucks);

for(int i = 0; i< loc;i++) {
	//ensuring each location gets only one car
	m.sum(TruckLocation[i], "=", 1).post();
}
for(int j = 0; j<trucks;j++) {
	//ensuring each truck can carry at most 5 parcels
	m.sum((getColumn(TruckLocation, j)), "<", 6).post();
}

m.getSolver().solve();


for(int i =0; i<loc; i++) {
	for (int j = 0; j<trucks; j++) {
		//System.out.println(Trucks[i]);
		//System.out.println(Parcels[j]);
		
		System.out.print(TruckLocation[i][j].getValue()+"\t");
	}
	System.out.println();
}

	}

}
