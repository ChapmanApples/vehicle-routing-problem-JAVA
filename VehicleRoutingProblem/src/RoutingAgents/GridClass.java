package RoutingAgents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;



public class GridClass extends Application {



	public ArrayList<Line> drawLine(int x,int y, int squaresize){


		ArrayList<Line> newline = new ArrayList<Line>();

		Line line1 = new Line(x,y,x+squaresize,y);
		Line line2 = new Line(x,y,x,y+squaresize);
		Line line3 = new Line(x,y+squaresize,x+squaresize,y+squaresize);
		Line line4 = new Line(x+squaresize,y,x+squaresize,y+squaresize);


		newline.add(line1);
		newline.add(line2);
		newline.add(line3);
		newline.add(line4);


		return newline;
	}

	public Circle MakeCircle(int x, int y, int radius) {
		Circle newCircle = new Circle();
		newCircle.setCenterX(x);
		newCircle.setCenterY(y);
		newCircle.setRadius(radius);

		return newCircle;
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {

			Group root = new Group();
			Circle newcircle = new Circle();

			// add circles in the gridlines
			RoutingWorld newworld = new RoutingWorld();
			newworld.BuildWorld();

			ArrayList<Node> MNode = newworld.TellMeLocations();

			for(Node a : MNode) {

				Circle circle1 = new Circle(a.x_pos*6+10,a.y_pos*6+10,10);				//shifts origin by 10 units and expands the scale by 6
				if (a.name=="depot") {
					circle1.setFill(Color.DARKRED);
				}
				root.getChildren().add(circle1);	//scale the size of the maps by 6 ie 1:6 and shift origin by 10 for the values
			}

			Node Depotnode = newworld.Depot();
			Circle depot = new Circle(Depotnode.x_pos*6+10,Depotnode.y_pos*6+10,10);
			depot.setFill(Color.DARKGREEN);
			root.getChildren().add(depot);

			for(int j=0;j<10;j++) {
				for(int i=0;i<10;i++) {
					root.getChildren().addAll(drawLine(i*60,j*60, 60));

				}
			}

			root.getChildren().add(new Circle(10,10,10));
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

		RoutingWorld newworld = new RoutingWorld();
		newworld.BuildWorld();
		ArrayList<Node> MNode = newworld.TellMeLocations();

		for(Node a : MNode) {
			System.out.print(a.name);
			System.out.println(a.x_pos+" "+a.y_pos);

		}

		launch(args);
	}

}
