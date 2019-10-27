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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import jade.core.Runtime;
import jade.wrapper.*;
import jade.core.Profile;
import jade.core.ProfileImpl;




public class GridClass extends Application {
	public File selectedFile = new File("");
	public GridPane view ;
	public List<Node> Nodes;
	public ArrayList<Truck> DeliveryAgents;
	public Stage primaryStage;
	public Group root;
	public Scene scene;
	public Path pathline1 = new Path();
	public Path pathline2 = new Path();
	public Path pathline3 = new Path();
	
	public int[] trialpathlines = {1,2,3,9,8,7,6};
	public int[] agent1;
	public int[] agent2;
	public int[] agent3;
	
	
	
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
	
	public GridClass(/*Stage stg*/) {
		Nodes =new ArrayList<Node>();
		view= new GridPane();
		root = new Group();
		Path pathline = new Path();
	}
	// returns Node Lists
	public Node[] NodeLists() {
		Node[] returnarray = new Node[Nodes.size()];
		int i =0;
		for(Node n:Nodes) {
			returnarray[i]= n;
			i++;
		}
		return returnarray;		
	}
	
	public void stopStage() {
		primaryStage.close();
	}
	
	public void startStage() {
		
		//primaryStage = new Stage();
		//primaryStage.show();
		launch();
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
	public void start(Stage stg) throws Exception {
		// TODO Auto-generated method stub
		primaryStage = new Stage();
		try {

			Group root = new Group();
			Circle newcircle = new Circle();

			// add circles in the gridlines
			RoutingWorld newworld = new RoutingWorld();
			newworld.BuildWorld();

			ArrayList<Node> MNode = newworld.TellMeLocations();
			int l =0;
			int node=0;
			for(int[] loc:location) {
				Circle circle1 = new Circle((loc[0]/10)*6+10,(loc[1]/10)*6+10,5);
				if(node==0) {
					System.out.println((loc[1]/10)*6+10+" "+((loc[1]/10)*6+10));
					circle1.setFill(Color.DARKRED);
				}
				Label labels = new Label(""+node);
				labels.setLayoutX((loc[0]/10)*6+20);
				labels.setLayoutY((loc[1]/10)*6+10);
				root.getChildren().add(labels);
				root.getChildren().add(circle1);
				node++;
			}
			
		      scene = new Scene(root, 600,600);
		      primaryStage.setTitle("Sample application");
		      primaryStage.setScene(scene);
		      primaryStage.show();
		      inputScreen();

		}catch(Exception e) {
			System.out.println("Error found in exception: "+e);
		}
	}
	
	public void drawonScreen(int[] locationlist,int pathlineval) {			//assign a variable for initalization drawonScreen int trialpathlines to draw
		clearPathlines();
		Path pathline;
		
		switch(pathlineval) {
			case 1:
				 pathline = pathline1;
			case 2:
				 pathline = pathline2;
			case 3:
				 pathline = pathline3;
			default:
				pathline = pathline1;
		}
		
	
		MoveTo moveTo = new MoveTo((location[0][0]/10)*6+10,(location[0][1]/10)*6+10);		//start at depot
		pathline.getElements().add(moveTo);
		for(int r:locationlist) {
			LineTo line1 = new LineTo((location[r][0]/10)*6+10,(location[r][1]/10)*6+10);
			pathline.getElements().add(line1);
		}
		LineTo endLine = new LineTo((location[0][0]/10)*6+10,(location[0][1]/10)*6+10);		//end at depot
		pathline.getElements().add(endLine);
		
		Group root = (Group) scene.getRoot();		
		if(!root.getChildren().contains(pathline))
		{
			root.getChildren().add(pathline);
		}else {
			root.getChildren().remove(pathline);
			root.getChildren().add(pathline);
			
		}
		scene.setRoot(root);
	}
	
	public void clearPathlines() {
		Group root = (Group) scene.getRoot();
		
		root.getChildren().remove(pathline1);
		root.getChildren().remove(pathline2);
		root.getChildren().remove(pathline3);
		pathline1= new Path();
		pathline2= new Path();
		pathline3= new Path();		
		
		scene.setRoot(root);
	}
	
	
	
	public void inputScreen()  {
		Stage stage = new Stage();
		Button btnSource = new Button("Choose Source");
		Label lblSource = new Label("");
		Label SumLabel = new Label("Sum"); 
		Label Cost = new Label("NA");
		
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
		Button Agent1 = new Button("Agent 1");
		Button Agent2 = new Button("Agent 2");
		Button Agent3 = new Button("Agent 3");
		Button Clear = new Button("Clear");
		
		 
		Agent1.setOnAction(e->{
			clearPathlines();
			drawonScreen(agent1,1);
			int getSum = getSum(agent1);
			Cost.setText(Integer.toString(getSum));
				
		});
		
		Agent2.setOnAction(e->{
			clearPathlines();
			drawonScreen(agent2,2);
			int getSum = getSum(agent2);
			Cost.setText(Integer.toString(getSum));
			
		});
		
		Agent3.setOnAction(e->{
			clearPathlines();
			drawonScreen(agent3,3);
			int getSum = getSum(agent3);
			Cost.setText(Integer.toString(getSum));
			
		});
		
		Clear.setOnAction(e->{
			clearPathlines();
			Cost.setText("N/A");
		});
		
		
		
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
					if(weight != 0) {
						Nodes.add(new Node(j+1,weight));
					}
				}
				
				for(Node N:Nodes) {
					System.out.println(N.ID+" "+N.weight);
				}
				
			}catch(Exception er)
			{
				er.printStackTrace();				
			}
			
			
		});
		
		
		
		Button Submit = new Button("Submit");
		Submit.setMinWidth(100);
			
		
		Submit.setOnAction( e-> { 
			Node[] ArrayNode = NodeLists();
			Runtime rt = Runtime.instance();
			Profile pMain = new ProfileImpl(null, 8888, null);
			ContainerController mainCtrl = rt.createMainContainer(pMain);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			AgentController MA;
			AgentController DA1;
			AgentController DA2;
			AgentController DA3;
			
			try {
				MA = mainCtrl.createNewAgent("MasterAgent", MasterRoutingAgent.class.getName(), new Object[0]);
				DA1 = mainCtrl.createNewAgent("DA1", DeliveryAgent1.class.getName(), new Object[0]);
				DA2 = mainCtrl.createNewAgent("DA2", DeliveryAgent2.class.getName(), new Object[0]);
				DA3 = mainCtrl.createNewAgent("DA3", DeliveryAgent3.class.getName(), new Object[0]); 
				
				MyAgentInterface o2a = MA.getO2AInterface(MyAgentInterface.class);
				for(Node n:ArrayNode) {
					System.out.println(n.ID+" "+n.weight);
				}
				o2a.recieveLocations(ArrayNode);
				
				MA.start();	
				DA1.start();
				DA2.start();
				DA3.start();
				
				
				try {
					Thread.sleep(10000);
					
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				DeliveryAgents = o2a.sendLocations();
				for(Truck da: DeliveryAgents) {
					System.out.print("DA" + da.TruckID + ": I'm off! Delivering packages to");
					
					for(Node loc: da.Locations) {
						
						System.out.print(" Location "+ loc.ID);
					}
					System.out.println();
					
				}
				
				//Assign Truck locations into the array
				for(int j=0;j<DeliveryAgents.size();j++) {
					
					Truck CrntTruck = DeliveryAgents.get(j);
					int CrntTrucksize = CrntTruck.Locations.size();
					int[] crntagent =new int[CrntTrucksize];
					
					for(int k=0; k<CrntTrucksize;k++) {
						crntagent[k] = CrntTruck.Locations.get(k).ID;					
					}
					
					switch(j) {
						case 0:
							agent1 = crntagent;
							
						case 1:
							agent2 = crntagent;
							
						case 2:
							agent3 = crntagent;
					}
					
				}
				System.out.println("Agent 1");
				for(int ag:agent1) {
					System.out.print(ag);
					//drawonScreen(agent1);
				}
				System.out.println();
				
				System.out.println("Agent 2");
				for(int ag:agent2) {
					System.out.println(ag);
					//drawonScreen(agent2);
				}
				System.out.println();
				
				System.out.println("Agent 3");
				for(int ag:agent3) {
					System.out.println(ag);
					//drawonScreen(agent3);
				}
				System.out.println();
				
			} catch (StaleProxyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			Node[] ArrayNode = NodeLists();
//			System.out.println("Array Node ");
//			for(Node Arr:NodeLists()) {
//				System.out.println(Arr.ID+" "+Arr.weight);
//			}
			
		}) ;
		
		
		
		

		HBox hboxChart = new HBox(10, Submit);
		hboxChart.setAlignment(Pos.CENTER); 
		
		HBox DrawnClear = new HBox(Agent1,Agent2,Agent3);
		DrawnClear.setSpacing(30);
		DrawnClear.setAlignment(Pos.CENTER);
		
		HBox Sum = new HBox(SumLabel,Cost);
		Sum.setAlignment(Pos.CENTER);
		
		
		HBox ClearPathline = new HBox(Clear);
		ClearPathline.setAlignment(Pos.CENTER);
		
		
		
		grdPane.addRow(3, hboxChart);
		grdPane.addRow(4, DrawnClear);
		grdPane.addRow(5, Sum);
		grdPane.addRow(6, ClearPathline);
		
		Scene scene = new Scene(grdPane,300,400);
		stage.setScene(scene);
		stage.setTitle("Open File System");
		stage.show();
		
	}
	
	public static int getSum(int[] array) {
		  Optimum_pathfinder cls= new Optimum_pathfinder();
		  int Sum = cls.find_distance(0,array[0]);
		  
		  
		  for(int i=0;i<array.length-1;i++) {
			  Sum = Sum+cls.find_distance(array[i], array[i+1]);
		  }
		  return Sum;
	  }
	
	public void DrawLine() {
		
	}

	public static void main(String args[]) {

//		RoutingWorld newworld = new RoutingWorld();
//		newworld.BuildWorld();
//		ArrayList<Node> MNode = newworld.TellMeLocations();
//
//		for(Node a : MNode) {
//			System.out.print(a.name);
//			System.out.println(a.x_pos+" "+a.y_pos);
//
//		}

		launch(args);
	}

}



