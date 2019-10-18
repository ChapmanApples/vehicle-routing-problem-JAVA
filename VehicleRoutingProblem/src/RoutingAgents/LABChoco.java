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
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.util.tools.ArrayUtils;
import java.util.Arrays;

public class LABChoco {
	
public static void main(String[] args) {
Model model = new Model("lab problem");

int p = 12;
int t = 3;



//IntVar x = model.intVar("X", 0, 5);   
//BoolVar[] Trucks = model.boolVarArray(3); //3 trucks
//BoolVar[] Parcels = model.boolVarArray(4); //4 parcels
IntVar parcels = model.intVar("P", new int[]{2, 3, 4});
BoolVar[][] TruckParcel = model.boolVarMatrix(p, t); 

 
//boolean[][] TruckParcel = new boolean [4][3] {Trucks,Parcels};
for (int i = 0; i < t; i++) {
	//System.out.println(getColumn(TruckParcel, i));
model.sum(getColumn(TruckParcel, i, p), "<=", parcels).post();
model.sum(getColumn(TruckParcel, i, p),">=",1).post();
}

for (int i = 0; i < p; i++) {
	model.sum(TruckParcel[i], "=", 1).post();
}

//model.sum(Trucks, "<=", 2).post();
//model.sum(Parcels, "=", 1).post();
//model.sum(TruckParcel[0], "=", 1).post();
//model.sum(TruckParcel[1], "=", 1).post();
//model.sum(TruckParcel[2], "=", 1).post();


//for(int i =0; i<3; i++) {
//	TruckParcel[i] = Trucks;
//	for (int j = 0; j<4; j++) {
//			TruckParcel[j] = Parcels;
//		
//			
//	}
//}
//model.setObjective(Model.MAXIMIZE, TruckParcel[0][0]);
//model.getSolver().showShortStatistics();
while(model.getSolver().solve()) {
	
System.out.println("Solution:");
for(int i =0; i<p; i++) {
	for (int j = 0; j<t; j++) {
		//System.out.println(Trucks[i]);
		//System.out.println(Parcels[j]);
		
		System.out.print(TruckParcel[i][j].getValue()+"\t");
	}
	System.out.println();
}
}




}
// Attribute :: https://stackoverflow.com/questions/30426909/get-columns-from-two-dimensional-array-in-java
public static IntVar[] getColumn(BoolVar[][] array, int index, int length){
	IntVar[] column = new IntVar[length]; // Here I assume a rectangular 2D array! 
    for(int i=0; i<column.length; i++){
       column[i] = array[i][index];
       //System.out.println(column[i]);
    }
    return column;
}

}
