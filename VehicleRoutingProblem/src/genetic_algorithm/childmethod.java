package genetic_algorithm;
import java.util.Random;

public class childmethod {
	
	
	private int[] Parent1;
	private int[] Parent2;
	private int[] Parentret;	
	private static int depot = 0;
	
	public childmethod(int[] Parent1, int[] Parent2) {	
		this.Parent1= Parent1;
		this.Parent2 =Parent2;
	}
	
	//RUN THE PROGRAM FROM ANOTHER SET
	public int[] childrun() {
		Parentret = Parent1;
		
		for(int i=0;i<25;i++) {
			int[][] Parentreturn = run(Parent1,Parent2);
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
		// TODO Auto-generated method stub
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
		
		/*************************************************************/
//		int[] Childcrossover1= getChild1(Parent1,Parent2); //a1 b2 
//		
//		for(int child:Childcrossover1) {
//			System.out.print(child + "\t");
//		}
		
		
		
		//TODO Bro on binay's demand
		//calculate fitness scores for each child on the basis of distances between corresponsing locations
		//then get the two childs with the least distance.
		//make them parent1 and parent2 for next gen
		// keep doing this for 10 secs or whatever.
		// I do realise that it is difficult to understand but give it some time to sink in
		//
		
	}
	public static int[][] run(int[] Parent1, int[] Parent2) {
		int[] Childcrossover1= getChild1(Parent1,Parent2); //a1 b2 
		int[] Childcrossover2= getChild2(Parent1,Parent2);//b1 a2
		int[] Childcrossover3= getChild3(Parent1,Parent2);//a1 b1 
		int[] Childcrossover4 = getChild4(Parent1,Parent2);//a2 b2
		
//		for(int child:Childcrossover1) {
//			System.out.print(child + "\t");
//		}
//		System.out.println();
//		for(int child:Childcrossover2) {
//			System.out.print(child + "\t");
//		}
//		System.out.println();
//		for(int child:Childcrossover3) {
//			System.out.print(child + "\t");
//		}
//		System.out.println();
//		for(int child:Childcrossover4) {
//			System.out.print(child + "\t");
//		}
		
		int getsum1 = getSum(Childcrossover1);
		int getsum2 = getSum(Childcrossover2);
		int getsum3 = getSum(Childcrossover3);
		int getsum4 = getSum(Childcrossover4);
		
//		System.out.print("Sum1 "+getsum1);
//		System.out.print("Sum2 "+getsum2);
//		System.out.print("Sum3 "+getsum3);
//		System.out.print("Sum4 "+getsum4);
//		
		int array_sum[] = new int[]{getsum1,getsum2,getsum3,getsum4};
		
		int sumval[]=print2Smallest(array_sum);
		
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
//		System.out.println();
//		System.out.println("Next gen Parent1");
//		for(int child:Parent1) {
//			System.out.print(child+"\t");
//		}
//		System.out.println("");
//		System.out.println("Next gen Parent2");
//		for(int child:Parent2) {
//			System.out.print(child+"\t");
//		}
//		System.out.println("--- ");
//		System.out.println();
		
		int[][] parents= new int[][] {Parent1,Parent2};
		return parents;
	}
	
	
	
	
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
	
	public static int[] getChild1(int[] P1, int[] P2) {
		 Random rand = new Random();
		int length = P1.length;
		int[] Child= new int [length];
		int crsspoint= (length/2)-1;						//could be randomized????
		//System.out.println(crsspoint);
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) {
				Child[i]=P1[i];
			}
			else {
				if(!randomcontains(Child,P2[i])) {
					Child[i]=P2[i];
					
				}
				else {
								
					while(true) {
						int randomA=P2[rand.nextInt(length)]; 
						if(!randomcontains(Child,randomA)) {
							Child[i] = randomA;
							break;		
						}
					}
					
					}
				}
					
			}
		
		
		return Child;
	}
	
	
	

	public static int[] getChild2(int[] P1, int[] P2) {
		
		int length = P1.length;
		int[] Child= new int [length];
		int crsspoint= (length/2)-1;
		Random rand = new Random();
		//System.out.println(crsspoint);
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) {
				Child[i]=P2[i];
			}
			else {
				if(!randomcontains(Child,P1[i])) {
					Child[i]=P1[i];
					//System.out.println(P2[i]);
				}
				else {
					while(true) {
						int randomA=P2[rand.nextInt(length)]; 
						if(!randomcontains(Child,randomA)) {
							Child[i] = randomA;
							break;		
						}
					}
					
				}
		
			}
		}
		return Child;
	}
	
	

	public static int[] getChild3(int[] P1, int[] P2) {
		
		Random rand = new Random();
		int length = P1.length;
		int[] Child= new int [length];
		int crsspoint= (length/2)-1;
		int k=0;
		
		
		//System.out.println(crsspoint);
		for(int i=0;i<length;i++) {
			
			if(i<=crsspoint) {
				Child[i]=P1[i];
			}
			else {
				
				if(!randomcontains(Child,P2[k])) {
					Child[i]=P2[k];
					k++;
				}
				else {
					
					while(true) {
						int randomA=P2[rand.nextInt(length)]; 
						if(!randomcontains(Child,randomA)) {
							Child[i] = randomA;
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
		
		int length = P1.length;
		int[] Child= new int [length];
		int crsspoint= (length/2)-1;
		int k=crsspoint+1;
		Random rand = new Random();
		//System.out.println(crsspoint);
		for(int i=0;i<length;i++) {
			if(i<=crsspoint) {
				Child[i]=P1[k];
				k++;
			}
			
			else {
				if(!randomcontains(Child,P2[i])) {
					Child[i]=P2[i];
					
				}
				else {
					while(true) {
						int randomA=P2[rand.nextInt(length)]; 
						if(!randomcontains(Child,randomA)) {
							Child[i] = randomA;
							break;		
						}
					}
				}
					
			}
		}
		
		return Child;
	}
	
	
	  public static boolean randomcontains(int[] array, int key) {
	    	boolean ret= false; 
	    	for(int ar:array){
	    		 if (ar==key) {
	    			 ret = true;
	    		 }
	    	 }
	    	return ret;
	    }
	  
	  public static int getSum(int[] array) {
		  Optimum_pathfinder cls= new Optimum_pathfinder();
		  int Sum = cls.find_distance(depot,array[0]);
		  
		  
		  for(int i=0;i<array.length-1;i++) {
			  Sum = Sum+cls.find_distance(array[i], array[i+1]);
		  }
		  return Sum;
	  }

}
