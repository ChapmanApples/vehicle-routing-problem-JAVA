package RoutingAgents;
import java.util.Random;

public class CrossoverChild {	
	private int[] Parent1; //Declaring Parent 1
	private int[] Parent2; //Declaring Parent 2
	private int[] Parentret; //Declaring Parentret 
	private static int depot = 0; // declaring the depot location ID
	// Creating a constructor	
	public CrossoverChild(int[] Parent1, int[] Parent2) { 
		this.Parent1= Parent1; // Assigning the parameter from the constructor to the variable defined above.
		this.Parent2 =Parent2; // Assigning the parameter from the constructor to the variable defined above.
	}
	//RUN THE PROGRAM FROM ANOTHER SET
	public int[] childrun() { 
		Parentret = Parent1; // assign parent 1 to parentret
		// running the algorithm 2000 times.
		for(int i=0;i<2000;i++) { 
			int[][] Parentreturn = run(Parent1,Parent2); // calling the method run and assigning it to a new 2D array parentreturn
			Parent1 = Parentreturn[0]; 
			Parent2 = Parentreturn[1];
			if (getSum(Parent1)<getSum(Parentret))
				Parentret = Parent1;
			if (getSum(Parent1)<getSum(Parentret))
				Parentret= Parent2;
		}
		return Parentret;
	}	
	public static void main(String[] args) {
		int[] p1, p2;
		int[] Parent1= new int[] {3,14,9,1,5,13};
		int[] Parent2= new int[] {5,1,9,13,14,3};		
		p1 =Parent1;
		p2 =Parent2;
		for(int i=0;i<900;i++) {
			int[][] Parentreturn = run(Parent1,Parent2);
			Parent1 = Parentreturn[0];
			Parent2 = Parentreturn[1];
		}
		System.out.println("Sum Parent1 initial "+getSum(p1));
		System.out.println("Sum Parent2 initial "+getSum(p2));
		System.out.println("Sum Parent1 updated "+getSum(Parent1));
		System.out.println("Sum Parent2 updated "+getSum(Parent2));		
	}
	//Main run method
	public static int[][] run(int[] Parent1, int[] Parent2) {
		int[] Childcrossover1= getChild1(Parent1,Parent2); //a1 b2: 1st part of parent 1 and 2nd part of parent 2 
		int[] Childcrossover2= getChild2(Parent1,Parent2);//b1 a2: 1st part of parent 2 and 2nd part of parent 1
		int[] Childcrossover3= getChild3(Parent1,Parent2);//a1 b1:1st part of both parents
		int[] Childcrossover4 = getChild4(Parent1,Parent2);//a2 b2:2nd part of both parents		
		int getsum1 = getSum(Childcrossover1); // sum of child1
		int getsum2 = getSum(Childcrossover2); // sum of child2
		int getsum3 = getSum(Childcrossover3);// sum of child3
		int getsum4 = getSum(Childcrossover4);// sum of child4
		int array_sum[] = new int[]{getsum1,getsum2,getsum3,getsum4}; // make an array of sum values
		int sumval[]=print2Smallest(array_sum);// get the smallest sum of the two children and make them parent for next iteration
		// switch case for 1st parent
		switch (sumval[0]) {
			case 0:
				Parent1=Childcrossover1;
				break;
			case 1:
				Parent1=Childcrossover2;
				break;
			
			case 2:
				Parent1=Childcrossover3;
				break;
			case 3:
				Parent1=Childcrossover4;
				break;
		}
		// switch case for 2nd parent
		switch (sumval[1]) {
		case 0:
			Parent2=Childcrossover1;
			break;
		case 1:
			Parent2=Childcrossover2;
			break;
		
		case 2:
			Parent2=Childcrossover3;
			break;
		case 3:
			Parent2=Childcrossover4;
			break;
	}		
		int[][] parents= new int[][] {Parent1,Parent2}; // initializing 2 parent
		return parents; // returning 2 parents
	}
	// 
		static int[] print2Smallest(int arr[]) 
    { 
        int first, second, arr_size = arr.length; 
        int findex=0;
        int sindex=0;
        /* There should be atleast two elements */
        first = second = Integer.MAX_VALUE; 
        for (int i = 0; i < arr_size ; i ++) 
        { 
            /* If current element is smaller than first 
              then update both first and second */
            if (arr[i] < first) 
            { 
                second = first; 
                first = arr[i]; 
             
            } 
            /* If arr[i] is in between first and second 
               then update second  */
            else if (arr[i] < second && arr[i] != first) 
                second = arr[i]; 
        } 
        int l=0;
        for(int x:arr) {
        	if (x==first)
        		findex = l;
        	l++;
        }
        l=0;
        for(int x:arr) {
        	if (x==second)
        		sindex = l;
        	l++;
        }
        int fns[] = new int[]{findex,sindex};        
        return fns;
    } 
	// Method to get the first child
	public static int[] getChild1(int[] P1, int[] P2) { 
		 Random rand = new Random(); // generating a random number
		int length = P1.length; // getting length of parent 1
		int[] Child= new int [length]; // Defining a new Child array with the same length as that of parent 1
		int crsspoint= rand.nextInt(length); // getting the random crosspoint by dividing the parent length by 1
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) { 
				Child[i]=P1[i]; // assigning the first part of parent 1 to child
			}
			else {
				if(!randomcontains(Child,P2[i])) {
					Child[i]=P2[i]; // assigning the second part of parent 2 to child
					
				}
				else {
								
					while(true) {
						int randomA=P2[rand.nextInt(length)]; // taking a random number from the parent 2
						if(!randomcontains(Child,randomA)) { // checking that random number to see if it is already in our child
							Child[i] = randomA; // assigning it to our child if it is not there. (Mutation)
							break;		
						}
					}
					
					}
				}
					
			}
		
		
		return Child;
	}
	
	
	

	public static int[] getChild2(int[] P1, int[] P2) {
		
		int length = P1.length; // getting length of parent 1
		int[] Child= new int [length]; // making a new child array of same length
		Random rand = new Random(); // generating a random number
		int crsspoint= rand.nextInt(length); // getting the random crosspoint value
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) {
				Child[i]=P2[i]; // assigning the first part of parent 2 to first part of child
			}
			else {
				if(!randomcontains(Child,P1[i])) {
					Child[i]=P1[i]; // assigning the second part of parent 1 to second part of child.
					//System.out.println(P2[i]);
				}
				else {
					while(true) {
						int randomA=P2[rand.nextInt(length)]; //taking a random number from the parent 2
						if(!randomcontains(Child,randomA)) { //checking that random number to see if it is already in our child
							Child[i] = randomA; //assigning it to our child if it is not there. (Mutation)
							break;		
						}
					}
					
				}
		
			}
		}
		return Child;
	}
	
	

	public static int[] getChild3(int[] P1, int[] P2) {
		
		Random rand = new Random(); // generating a new random variable
		int length = P1.length; // assigning the variable length to length of parent
		int[] Child= new int [length]; // making a new child array
		int crsspoint= (length/2)-1; // making the crosspoint variable
		int k=0;
		for(int i=0;i<length;i++) {
			
			if(i<=crsspoint) {
				Child[i]=P1[i]; // assigning first part of parent 1 to child
			}
			else {
				
				if(!randomcontains(Child,P2[k])) {
					Child[i]=P2[k]; // assigning first part of parent 2 to child
					k++;
				}
				else {
					
					while(true) {
						int randomA=P2[rand.nextInt(length)]; // generating a random number
						if(!randomcontains(Child,randomA)) { // checking if the random number index in parent 2 is present in the child
							Child[i] = randomA; // assigning random number index of parent 2 to child (mutation)
							break;		
						}
					}
						
					}
					k++;
				}
					
			}
		
		
		return Child;
	}
	

	public static int[] getChild4(int[] P1, int[] P2) {
		
		int length = P1.length; // assigning the variable length to length of parent
		int[] Child= new int [length]; // making a new child array
		int crsspoint= (length/2)-1; //making the crosspoint variable
		int k=crsspoint+1;
		Random rand = new Random();// generating a new random variable
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) {
				Child[i]=P1[k];// assigning 2nd part of parent 1 to first part of child
				k++;
			}
			
			else {
				if(!randomcontains(Child,P2[i])) {
					Child[i]=P2[i];// assigning 2nd part of parent 2 to 2nd part of chuld
					
				}
				else {
					while(true) {
						int randomA=P2[rand.nextInt(length)]; // generating random number
						if(!randomcontains(Child,randomA)) { // checking if the random number index in parent 2 is present in the child
							Child[i] = randomA; // assigning random number indexof parent 2 to child (mutation)
							break;		
						}
					}
				}
					
			}
		}
		
		return Child; 
	}
	
	// method that checks the array indexes with a number and returns a boolean.
	  public static boolean randomcontains(int[] array, int key) {
	    	boolean ret= false; 
	    	for(int ar:array){
	    		 if (ar==key) {
	    			 ret = true;
	    		 }
	    	 }
	    	return ret;
	    }
	  // Gets the sum of distances between depot and array's first element, between array indexes and between last element of array and depot
	  public static int getSum(int[] array) {
		  Optimum_pathfinder cls= new Optimum_pathfinder();
		  int Sum = 0;
		  if(array.length>0) {
		   Sum = cls.find_distance(depot,array[0]);
		  }
		  
		  
		  for(int i=0;i<array.length-1;i++) {
			  Sum = Sum+cls.find_distance(array[i], array[i+1]);
		  }
		  return Sum;
	  }

}
