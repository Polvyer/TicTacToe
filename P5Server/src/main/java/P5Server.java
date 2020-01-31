import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class P5Server extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	Server serverConnection;
	int choose_PortNum;
	Button connect_Btn, server_start_Btn, exit_Btn;
	Label enter_Lbl, portNum_Lbl, server_info_Lbl;
	ListView<String> server_info_LV;
	TextField port_num_TF;
	Scene start_scene, info_scene;

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		// BORDER PANES
		BorderPane enterPane = new BorderPane();
		BorderPane validPane = new BorderPane();
		BorderPane connectPane = new BorderPane();
		BorderPane listVPane = new BorderPane();
		BorderPane labelPane = new BorderPane();
		BorderPane exitPane = new BorderPane();

		// LIST VIEW
		server_info_LV = new ListView<>();
		listVPane.setCenter(server_info_LV);

		// LABELS
		enter_Lbl = new Label("ENTER");
		enterPane.setCenter(enter_Lbl);
		enter_Lbl.setStyle("-fx-font-size: 25;" + "-fx-text-fill: whitesmoke;" + "-fx-font-weight:bold;");

		portNum_Lbl = new Label("A VALID PORT");
		validPane.setCenter(portNum_Lbl);
		portNum_Lbl.setStyle("-fx-font-size: 25;" + "-fx-text-fill: whitesmoke;" + "-fx-font-weight:bold;");

		server_info_Lbl = new Label("Game Information");
		labelPane.setCenter(server_info_Lbl);
		server_info_Lbl.setStyle("-fx-font-size: 30;" + "-fx-text-fill: peru;" + "-fx-font-weight:bold;");

		// TEXT FIELD
		port_num_TF = new TextField("5555");
		port_num_TF.setStyle("-fx-pref-width: 150px;" + "-fx-pref-height: 40px;");

		// BUTTONS
		connect_Btn = new Button("START SERVER");
		connectPane.setCenter(connect_Btn);
		connect_Btn.setStyle("-fx-background-color:\n" +
				"        linear-gradient(#f0ff35, #a9ff00),\n" +
				"        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n" +
				"    -fx-background-radius: 6, 5;\n" +
				"    -fx-background-insets: 0, 1;\n" +
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
				"    -fx-text-fill: #395306;" + "-fx-font-weight: bold;"
				+ "-fx-pref-width: 150px;" + "-fx-font-size: 18;");

		exit_Btn = new Button("EXIT");
		exitPane.setCenter(exit_Btn);
		exit_Btn.setStyle("-fx-padding: 8 15 15 15;\n" +
				"    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
				"    -fx-background-radius: 8;\n" +
				"    -fx-background-color: \n" +
				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
				"        #9d4024,\n" +
				"        #d86e3a,\n" +
				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-font-size: 1.1em;");
		// IMPLEMENTATION
		connect_Btn.setOnAction(event -> {
			int port = Integer.parseInt(port_num_TF.getText()); // Extract Port Number
			primaryStage.setScene(info_scene);
			primaryStage.setTitle("SERVER");
			serverConnection = new Server(data -> {
				Platform.runLater(() -> {
					server_info_LV.getItems().add(data.toString());
				});
			}, port);
		});

		exit_Btn.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});

		// DESIGN BOXES
		VBox centerLbl = new VBox(10);
		centerLbl.getChildren().addAll(enterPane,validPane);

		HBox centerTF = new HBox(10);
		centerTF.getChildren().addAll(port_num_TF);

		VBox intro_screen = new VBox(5);
		intro_screen.getChildren().addAll(centerLbl,centerTF,connectPane);
		intro_screen.setMargin(centerLbl, new Insets(30,0,0,0));
		intro_screen.setMargin(centerTF, new Insets(20,0,0,125));
		intro_screen.setMargin(connectPane, new Insets(20,0,0,0));

		VBox server_screen = new VBox(20);
		server_screen.getChildren().addAll(labelPane,listVPane,exitPane);
		server_screen.setMargin(labelPane, new Insets(20,0,0,0));
		server_screen.setMargin(listVPane, new Insets(0,50,0,50));
		server_screen.setMargin(exitPane, new Insets(0,0,10,0));

		// BACK GROUND
		intro_screen.setStyle("-fx-background-color:linear-gradient(#232323,#3f87a6);");
		server_screen.setStyle("-fx-background-color:linear-gradient(#123456,#3f87a6);");
		// SCENES
		start_scene = new Scene(intro_screen,400,300);
		info_scene = new Scene(server_screen, 500,500);

		primaryStage.setTitle("Connecting...");
		primaryStage.setScene(start_scene);
		primaryStage.show();
	}

}
