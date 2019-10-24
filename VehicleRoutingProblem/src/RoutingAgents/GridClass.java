package RoutingAgents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;




public class GridClass extends Application {
	File selectedFile = new File("");
	List<N_ode> Nodes;
	MasterRoutingAgent Master = new MasterRoutingAgent();
	DeliveryAgent1 agent1 = new DeliveryAgent1();
	
	public GridClass() {
		Nodes =new ArrayList<N_ode>();
	}
	
	public N_ode[] NodeLists() {
		N_ode[] returnarray = new N_ode[16];
		int i =0;
		for(N_ode n:Nodes) {
			returnarray[i]= n;
			i++;
		}
		return returnarray;		
	}


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
			int l =0;
			for(Node a : MNode) {
				l++;
				Circle circle1 = new Circle(a.x_pos*6+10,a.y_pos*6+10,10);				//shifts origin by 10 units and expands the scale by 6
				if (a.name=="depot") {
					circle1.setFill(Color.DARKRED);
				}
				Label labels = new Label(""+l);
				labels.setLayoutX(a.x_pos*6+20);
				labels.setLayoutY(a.y_pos*6+10);
				root.getChildren().add(labels);
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

			//sroot.getChildren().add(new Circle(10,10,10));
		      Scene scene = new Scene(root, 600,600);

		      primaryStage.setTitle("Sample application");

		      //Adding the scene to the stage
		      primaryStage.setScene(scene);

		      //Displaying the contents of a scene
		      primaryStage.show();
		      inputScreen();
		      
		      

		}catch(Exception e) {
			System.out.println("Error found in exception: "+e);
		}
	}
	
	public void inputScreen() {
		Stage stage = new Stage();
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");
		HBox hboxSource = new HBox(btnSource,lblSource );
		
		
		btnSource.setOnAction(e -> { 
		FileChooser  file = new FileChooser();
		file.setTitle("Open File");		
		selectedFile = file.showOpenDialog(stage); 
		lblSource.setText(selectedFile.getName()); 
		});
		
		Button btnSearch = new Button("Load Text");
		btnSearch.setMinWidth(100);
		HBox hbox = new HBox(btnSearch);
		hbox.setAlignment(Pos.CENTER); 
		
		TextArea txtR = new TextArea();
		txtR.setText(selectedFile.getName());
		GridPane grdPane = new GridPane();
		
		grdPane.setPadding(new Insets(10,10,10,10));
		//grdPane.setMinSize(10, 10);
		grdPane.setHgap(10);
		grdPane.setVgap(10);
		grdPane.setAlignment(Pos.CENTER);
		
		grdPane.addRow(0,hboxSource);
		grdPane.addRow(1,hbox);
		grdPane.addRow(2, txtR);
		
		btnSearch.setOnAction(e -> {
			BufferedReader reader = null;
			try
			{
				txtR.setText("");
				reader = new BufferedReader(new FileReader(selectedFile.getPath()));
				String line = reader.readLine();
				List<Integer> NodeId = new ArrayList<Integer>();
				List<Integer> no_of_packages = new ArrayList<Integer>();
				List<Integer> weight_of_each= new ArrayList<Integer>();
			
				while(line != null ) {					
					txtR.setText(txtR.getText() + line + "\n");	
					String[] newString = line.split(",");
					NodeId.add(Integer.parseInt(newString[0]));
					no_of_packages.add(Integer.parseInt(newString[1]));
					weight_of_each.add(Integer.parseInt(newString[2]));
					line = reader.readLine();	
				}
				reader.close();
				
				for(int j=0;j<16;j++) {
					int weight=0; int k = 0;
					for(Integer nod:NodeId) {
						
						if(nod==(j+1)) {
							weight += (no_of_packages.get(k)*weight_of_each.get(k));
						}
						
						k++;
					}
					Nodes.add(new N_ode(j+1,weight));
				}
				
				
				for(N_ode N:Nodes) {
					System.out.println(N.id+" "+N.weight);
				}
				
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			
			
		});
		
		Button Submit = new Button("Submit");
		Submit.setMinWidth(100);
//		Button btnPieChart = new Button("Pie Chart");
//		btnPieChart.setMinWidth(100);
			
		
		Submit.setOnAction( e-> {
			
			
			N_ode[] ArrayNode = NodeLists();
			System.out.println("Array Node ");
			for(N_ode Arr:NodeLists()) {
				System.out.println(Arr.id+" "+Arr.weight);
			}
			
		});
		
		
		
		HBox hboxChart = new HBox(10, Submit);
		hboxChart.setAlignment(Pos.CENTER); 
		
		grdPane.addRow(3, hboxChart);
		
		Scene scene = new Scene(grdPane,300,400);
		stage.setScene(scene);
		stage.setTitle("Open File System");
		stage.show();
		
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


class N_ode{
	
	public Integer id;
	public Integer weight;
	public List<Integer> packageId;
	
	public N_ode(Integer id,Integer weight) {
		this.id = id;
		this.weight = weight;
	}
	
//	public addpackage(int package) {
//		
//	}

}

class xPackage{
	private int weight;
	private int NodeId;
	public xPackage(int weight, int NodeId) {
		this.weight = weight;
		this.NodeId = NodeId;
	}
}

