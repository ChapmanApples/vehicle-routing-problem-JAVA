package RoutingAgents;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.stage.Stage;
public class Optimum_pathfinder {
	public static int[]  Selected_locations = {1,6,5,9,14,12};
	
	
	public Optimum_pathfinder(int[] Location_set) {
		Selected_locations= Location_set;
	}
	
	public Optimum_pathfinder() {
		
	}
	
    private static int[][] location = {{456,320},
    									{228,0},
										{912,0},	//2
										{0,80},
										{114,80},
										{570,160},
										{798,160},
										{342,240},	//7
										{570,400},
										{912,400},
										{114,480},
										{228,480},
										{342,560},
										{684,560},
										{0,640},
										{798,640}};
    
    
        public static int[][] distanceMatrix = {
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
        
        //public static int[] Selected_locations = {1,6,5,9,14,12};
    public int[] run() {
    	int[] Parent_X;
    	int[] array_contain= new int[Selected_locations.length];
    	
		
		int size = Selected_locations.length;
		int[] ParentX =new int[size];
		int[] ParentY = new int[size];
		ArrayList<Integer> list = new ArrayList<Integer>(size); 
       
		int[] Array= new int[size];
       for(int i = 1; i <= size; i++) {
           list.add(i);
       }

       Random rand = new Random();
       int ab = 0;
       while(list.size() > 0) {
           int index = rand.nextInt(list.size());
           Array[ab]=list.get(index);
           list.remove(index);
           ab++;
       }
       
       int[] Parent1index = generateRandomIndex(size);							
       int[] Parent2index = generateRandomIndex(size);
       
       int[] Parent1 = new int [size];
       int[] Parent2 = new int [size];
       
       
       //GENERATE POPULATION
       
       //generate Parent 1
       
       
	      for (int i =0; i<size;i++) {
	    	  int index = Parent1index[i];
	    	  Parent1[i]= Selected_locations[index-1];
	      }
      
      //generate Parent 2
      
	      for (int i =0; i<size;i++) {
	    	  int index = Parent2index[i];
	    	  Parent2[i]= Selected_locations[index-1];
	      }
		      
	      
//	      System.out.println("Parent 1");
//	      for(int arr:Parent1) {
//	    	  System.out.print(arr);
//	      }
//	      System.out.println();
//	      System.out.println("Parent 2");
//	       
//	      for(int arr:Parent2) {
//	    	  System.out.print(arr);
//	      }
//	      System.out.println();
	      childmethod newmethod = new childmethod(Parent1,Parent2);
	      
	      int[] final_parent= newmethod.childrun();
	    
	      return final_parent;
    }
    
    
    public static void main(String[] args) {
    	System.out.println("Node:"+ location[0][0]+"\t"+location[0][1]);
    	System.out.println("X\t"+"Y\t"+"Distance");
		System.out.println("-------------------");
		
    	for(int loc:Selected_locations){
    		
    		System.out.println(location[loc][0]+"\t"+location[loc][1]+"\t"+distanceMatrix[0][loc]);
    	}
    	
    	int[] Parent_X;
    	int[] array_contain= new int[Selected_locations.length];
    	
		
		int size = Selected_locations.length;
		int[] ParentX =new int[size];
		int[] ParentY = new int[size];
		ArrayList<Integer> list = new ArrayList<Integer>(size); 
       
		int[] Array= new int[size];
       for(int i = 1; i <= size; i++) {
           list.add(i);
       }

       Random rand = new Random();
       int ab = 0;
       while(list.size() > 0) {
           int index = rand.nextInt(list.size());
           Array[ab]=list.get(index);
           list.remove(index);
           ab++;
       }
       
       int[] Parent1index = generateRandomIndex(size);							
       int[] Parent2index = generateRandomIndex(size);
       
       int[] Parent1 = new int [size];
       int[] Parent2 = new int [size];
       
       
       //GENERATE POPULATION
       
       //generate Parent 1
       
       
	      for (int i =0; i<size;i++) {
	    	  int index = Parent1index[i];
	    	  Parent1[i]= Selected_locations[index-1];
	      }
      
      //generate Parent 2
      
	      for (int i =0; i<size;i++) {
	    	  int index = Parent2index[i];
	    	  Parent2[i]= Selected_locations[index-1];
	      }
		      
	      
	      System.out.println("Parent 1");
	      for(int arr:Parent1) {
	    	  System.out.print(arr);
	      }
	      System.out.println();
	      System.out.println("Parent 2");
	       
	      for(int arr:Parent2) {
	    	  System.out.print(arr);
	      }
	      System.out.println();
	      childmethod newmethod = new childmethod(Parent1,Parent2);
	      
	      int[] final_parent= newmethod.childrun();
	      System.out.println("Optimized set for the given set of location");
	      System.out.println("First Sum: "+newmethod.getSum(Selected_locations));
	      System.out.println("Final Sum "+newmethod.getSum(final_parent));
	      
	      for(int arr:final_parent) {
	    	  System.out.print(arr);
	      }
	      
	  
    
    }
    
    
    
    //distance between two points
    public static int find_distance(int from, int to) {
		return	distanceMatrix[from][to];
	}
    
    public static int[] generateRandomIndex(int size) {
    	ArrayList<Integer> list = new ArrayList<Integer>(size);
    	int[] Array= new int[size];
		 for(int i = 1; i <= size; i++) {
	         list.add(i);
	     }
		 
		 Random rand = new Random();
	       int ab = 0;
	       while(list.size() > 0) {
	           int index = rand.nextInt(list.size());
	           Array[ab]=list.get(index);
	           //Array[ab]=Selected_locations[index];
	           list.remove(index);
	           //System.out.println("Selected: "+);
	           ab++;
	       }
		 
		 
    	return Array;
    }
    
    public static int getSum(int[] array) {
		  int Sum = 0;
		  Optimum_pathfinder cls= new Optimum_pathfinder();
		  Sum +=cls.find_distance(0, array[0]);
		  for(int i=0;i<array.length-1;i++) {
			  Sum = Sum+cls.find_distance(array[i], array[i+1]);
		  }
		  Sum +=cls.find_distance(array.length-1, 0);
		  return Sum;
	  }
    
  
    
}
