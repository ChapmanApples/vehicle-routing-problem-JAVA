package RoutingAgents;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class GridClass extends Application {
	
	public Line getLine(float X1,float Y1, float X2,float Y2) {
		Line line = new Line();
		line.setStartX(X1); 
	 	line.setStartY(Y1); 
 		line.setEndX(X2); 
		line.setEndY(Y2);
		
		return line;
	}
	
	
	public ArrayList<Line> drawLine(int x,int y, int squaresize){
		
		
		ArrayList<Line> newline = new ArrayList<Line>();
		
		Line line1 = getLine(x,y,x+squaresize,y);						
		Line line2 = getLine(x,y,x,y+squaresize);						
		Line line3 = getLine(x,y+squaresize,x+squaresize,y+squaresize);	
		Line line4 = getLine(x+squaresize,y,x+squaresize,y+squaresize);	

		
		newline.add(line1);
		newline.add(line2);
		newline.add(line3);
		newline.add(line4);
		
		
		return newline;	
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			
			
			//make grid for the x positions
			
			//set a base value for each distance
			//get line value
			
			
			
			
//			
//			public final long[][] distanceMatrix = {
//			        (0,0),(0,548),(0,776)
//					{0, 548, 776, 696, 582, 274, 502, 194, 308, 194, 536, 502, 388, 354, 468, 776, 662},
//					(548,0),(0,0),(	
//			        {548, 0, 684, 308, 194, 502, 730, 354, 696, 742, 1084, 594, 480, 674, 1016, 868, 1210},
//			        {776, 684, 0, 992, 878, 502, 274, 810, 468, 742, 400, 1278, 1164, 1130, 788, 1552, 754},
//			        {696, 308, 992, 0, 114, 650, 878, 502, 844, 890, 1232, 514, 628, 822, 1164, 560, 1358},
//			        {582, 194, 878, 114, 0, 536, 764, 388, 730, 776, 1118, 400, 514, 708, 1050, 674, 1244},
//			        {274, 502, 502, 650, 536, 0, 228, 308, 194, 240, 582, 776, 662, 628, 514, 1050, 708},
//			        {502, 730, 274, 878, 764, 228, 0, 536, 194, 468, 354, 1004, 890, 856, 514, 1278, 480},
//			        {194, 354, 810, 502, 388, 308, 536, 0, 342, 388, 730, 468, 354, 320, 662, 742, 856},
//			        {308, 696, 468, 844, 730, 194, 194, 342, 0, 274, 388, 810, 696, 662, 320, 1084, 514},
//			        {194, 742, 742, 890, 776, 240, 468, 388, 274, 0, 342, 536, 422, 388, 274, 810, 468},
//			        {536, 1084, 400, 1232, 1118, 582, 354, 730, 388, 342, 0, 878, 764, 730, 388, 1152, 354},
//			        {502, 594, 1278, 514, 400, 776, 1004, 468, 810, 536, 878, 0, 114, 308, 650, 274, 844},
//			        {388, 480, 1164, 628, 514, 662, 890, 354, 696, 422, 764, 114, 0, 194, 536, 388, 730},
//			        {354, 674, 1130, 822, 708, 628, 856, 320, 662, 388, 730, 308, 194, 0, 342, 422, 536},
//			        {468, 1016, 788, 1164, 1050, 514, 514, 662, 320, 274, 388, 650, 536, 342, 0, 764, 194},
//			        {776, 868, 1552, 560, 674, 1050, 1278, 742, 1084, 810, 1152, 274, 388, 422, 764, 0, 798},
//			        {662, 1210, 754, 1358, 1244, 708, 480, 856, 514, 468, 354, 844, 730, 536, 194, 798, 0},
//			    };
//		
			Group root = new Group();
			for(int j=0;j<10;j++) {
				for(int i=0;i<10;i++) {
				
					root.getChildren().addAll(drawLine(i*60,j*60, 60));
					
				}
			}
			
					
		      
		      Scene scene = new Scene(root, 600,600);
		      
		      primaryStage.setTitle("Sample application"); 
		         
		      //Adding the scene to the stage 
		      primaryStage.setScene(scene); 
		         
		      //Displaying the contents of a scene 
		      primaryStage.show(); 
			
			
		}catch(Exception e) {
			System.out.println("Error found in exception: "+e);
		}
	}
	
	public static void main(String args[]) {
		launch(args);
	}

}
