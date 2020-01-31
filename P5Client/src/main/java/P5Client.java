import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class P5Client extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	Client clientConnection;
	Button connect_Btn, playAgain_Btn, quit_Btn;
	Label enter_Lbl, portNum_Lbl, ip_add_Lbl, top_pl_Lbl, select_diff_Lbl, instruction;
	ListView<String> leader_board_LV;
	TextField port_num_TF, ip_add_TF;
	Scene start_scene, game_scene, TTT_scene;

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		// BORDER PANES
		BorderPane enterPane = new BorderPane();
		BorderPane connectPane = new BorderPane();
		BorderPane leaderBoardPane = new BorderPane();
		BorderPane topPlayersPane = new BorderPane();
		BorderPane selectDiffPane = new BorderPane();
		BorderPane instructPane = new BorderPane();

		// RADIO BUTTONS
		RadioButton easyRB = new RadioButton("EASY");
		RadioButton mediumRB = new RadioButton("MEDIUM");
		RadioButton expertRB = new RadioButton("EXPERT");

		easyRB.setStyle("-fx-font-size: 15;" + "-fx-text-fill: lime;");
		mediumRB.setStyle("-fx-font-size: 15;" + "-fx-text-fill: orange;");
		expertRB.setStyle("-fx-font-size: 15;" + "-fx-text-fill: red;");

		ToggleGroup difficultiesTG = new ToggleGroup();
		easyRB.setToggleGroup(difficultiesTG);
		mediumRB.setToggleGroup(difficultiesTG);
		expertRB.setToggleGroup(difficultiesTG);

		// LIST VIEW
		leader_board_LV = new ListView<>();
		leaderBoardPane.setCenter(leader_board_LV);

		// LABELS
		enter_Lbl = new Label("ENTER");
		enterPane.setCenter(enter_Lbl);
		enter_Lbl.setStyle("-fx-font-size: 25;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");
		enterPane.setCenter(enter_Lbl);
		enterPane.setPadding(new Insets(70,0,0,0));

		portNum_Lbl = new Label("Port Number: ");
		portNum_Lbl.setStyle("-fx-font-size: 18;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");

		ip_add_Lbl = new Label("IP Address: ");
		ip_add_Lbl.setStyle("-fx-font-size: 18;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");

		top_pl_Lbl = new Label("TOP PLAYERS");
		topPlayersPane.setCenter(top_pl_Lbl);
		top_pl_Lbl.setStyle("-fx-font-size: 22;" + "-fx-text-fill: cyan;" + "-fx-font-weight:bold;");

		select_diff_Lbl = new Label("SELECT DIFFICULTY");
		selectDiffPane.setCenter(select_diff_Lbl);
		select_diff_Lbl.setStyle("-fx-font-size: 18;" + "-fx-text-fill: gold;" + "-fx-font-weight:bold;");

		instruction = new Label("First, Select the difficult you want to play against");
		instructPane.setCenter(instruction);
		instruction.setStyle("-fx-font-size: 18;" + "-fx-text-fill: khaki;" + "-fx-font-weight:bold;");

		Label temp1= new Label("This is where the tic tac toe tiles are going to be");
		temp1.setStyle("-fx-font-size: 16;" + "-fx-text-fill: white;" + "-fx-font-weight:bold;");

		// TEXT FIELD
		port_num_TF = new TextField("5555");
		ip_add_TF = new TextField("127.0.0.1");


		// BUTTONS
		//source: http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
		connect_Btn = new Button("CONNECT");
		connectPane.setCenter(connect_Btn);
		connect_Btn.setStyle("-fx-background-color: \n" +
				"        linear-gradient(#ffd65b, #e68400),\n" +
				"        linear-gradient(#ffef84, #f2ba44),\n" +
				"        linear-gradient(#ffea6a, #efaa22),\n" +
				"        linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),\n" +
				"        linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));\n" +
				"    -fx-background-radius: 30;\n" +
				"    -fx-background-insets: 0,1,2,3,0;\n" +
				"    -fx-text-fill: #654b00;\n" +
				"    -fx-font-weight: bold;\n" +
				"    -fx-font-size: 14px;\n" +
				"    -fx-padding: 10 20 10 20;");

		playAgain_Btn = new Button("PLAY AGAIN");
		playAgain_Btn.setStyle("-fx-background-color: \n" +
				"        #000000,\n" +
				"        linear-gradient(#7ebcea, #2f4b8f),\n" +
				"        linear-gradient(#426ab7, #263e75),\n" +
				"        linear-gradient(#395cab, #223768);" + "-fx-background-radius: 30;"
				+ "-fx-background-radius: 30;" + "-fx-text-fill: white;" + "-fx-pref-width: 130px;" + "-fx-font-size: 17;");

		quit_Btn = new Button("QUIT");
		quit_Btn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);" + "-fx-background-radius: 30;"
				+ "-fx-background-radius: 30;" + "-fx-text-fill: white;" + "-fx-pref-width: 130px;" + "-fx-font-size: 17;");

		Button button0 = new Button();
		button0.setPrefHeight(100.0);
		button0.setPrefWidth(100.0);

		Button button1 = new Button();
		button1.setPrefHeight(100.0);
		button1.setPrefWidth(100.0);

		Button button2 = new Button();
		button2.setPrefHeight(100.0);
		button2.setPrefWidth(100.0);

		Button button3 = new Button();
		button3.setPrefHeight(100.0);
		button3.setPrefWidth(100.0);

		Button button4 = new Button();
		button4.setPrefHeight(100.0);
		button4.setPrefWidth(100.0);

		Button button5 = new Button();
		button5.setPrefHeight(100.0);
		button5.setPrefWidth(100.0);

		Button button6 = new Button();
		button6.setPrefHeight(100.0);
		button6.setPrefWidth(100.0);

		Button button7 = new Button();
		button7.setPrefHeight(100.0);
		button7.setPrefWidth(100.0);

		Button button8 = new Button();
		button8.setPrefHeight(100.0);
		button8.setPrefWidth(100.0);

		button0.setOnAction(event -> {
			button0.setPrefHeight(100.0);
			button0.setPrefWidth(100.0);
			this.clientConnection.getButtonClicked(1);
			button0.setDisable(true);
			button0.setStyle("-fx-font-size:40");
			button0.setText("O");

		});

		button1.setOnAction(event -> {
			this.clientConnection.getButtonClicked(2);
			button1.setDisable(true);
			button1.setStyle("-fx-font-size:40");
			button1.setText("O");

		});

		button2.setOnAction(event -> {
			this.clientConnection.getButtonClicked(3);
			button2.setDisable(true);
			button2.setStyle("-fx-font-size:40");
			button2.setText("O");

		});

		button3.setOnAction(event -> {
			this.clientConnection.getButtonClicked(4);
			button3.setDisable(true);
			button3.setStyle("-fx-font-size:40");
			button3.setText("O");

		});

		button4.setOnAction(event -> {
			this.clientConnection.getButtonClicked(5);
			button4.setDisable(true);
			button4.setStyle("-fx-font-size:40");
			button4.setText("O");

		});

		button5.setOnAction(event -> {
			this.clientConnection.getButtonClicked(6);
			button5.setDisable(true);
			button5.setStyle("-fx-font-size:40");
			button5.setText("O");

		});

		button6.setOnAction(event -> {
			this.clientConnection.getButtonClicked(7);
			button6.setDisable(true);
			button6.setStyle("-fx-font-size:40");
			button6.setText("O");

		});

		button7.setOnAction(event -> {
			this.clientConnection.getButtonClicked(8);
			button7.setDisable(true);
			button7.setStyle("-fx-font-size:40");
			button7.setText("O");

		});

		button8.setOnAction(event -> {
			this.clientConnection.getButtonClicked(9);
			button8.setDisable(true);
			button8.setStyle("-fx-font-size:40");
			button8.setText("O");

		});

		// IMPLEMENTATION
		connect_Btn.setOnAction(event -> {
			primaryStage.setScene(game_scene);
			primaryStage.setTitle("Let's Play Tic Tac Toe");
			button0.setDisable(true);
			button1.setDisable(true);
			button2.setDisable(true);
			button3.setDisable(true);
			button4.setDisable(true);
			button5.setDisable(true);
			button6.setDisable(true);
			button7.setDisable(true);
			button8.setDisable(true);
			playAgain_Btn.setDisable(true);
			quit_Btn.setDisable(true);
			String ip = ip_add_TF.getText(); // Extract IP Address
			int port = Integer.parseInt(port_num_TF.getText()); // Extract Port Number
			clientConnection = new Client(data -> {
				Platform.runLater(() -> {
					String s = data.toString();

					if (s.equals("#")) {
						playAgain_Btn.setDisable(false);
						button0.setDisable(true);
						button1.setDisable(true);
						button2.setDisable(true);
						button3.setDisable(true);
						button4.setDisable(true);
						button5.setDisable(true);
						button6.setDisable(true);
						button7.setDisable(true);
						button8.setDisable(true);
					}
					else if (s.equals("1")) {
						button0.setMaxHeight(100.0);
						button0.setMaxWidth(100.0);
						button0.setDisable(true);
						button0.setStyle("-fx-font-size:40");
						button0.setText("X");

					}
					else if (s.equals("2")) {
						button1.setDisable(true);
						button1.setStyle("-fx-font-size:40");
						button1.setText("X");
					}
					else if (s.equals("3")) {
						button2.setDisable(true);
						button2.setStyle("-fx-font-size:40");
						button2.setText("X");
					}
					else if (s.equals("4")) {
						button3.setDisable(true);
						button3.setStyle("-fx-font-size:40");
						button3.setText("X");
					}
					else if (s.equals("5")) {
						button4.setDisable(true);
						button4.setStyle("-fx-font-size:40");
						button4.setText("X");
					}
					else if (s.equals("6")) {
						button5.setDisable(true);
						button5.setStyle("-fx-font-size:40");
						button5.setText("X");
					}
					else if (s.equals("7")) {
						button6.setDisable(true);
						button6.setStyle("-fx-font-size:40");
						button6.setText("X");
					}
					else if (s.equals("8")) {
						button7.setDisable(true);
						button7.setStyle("-fx-font-size:40");
						button7.setText("X");
					}
					else if (s.equals("9")) {
						button8.setDisable(true);
						button8.setStyle("-fx-font-size:40");
						button8.setText("X");
					}
					else if (s.equals("&")) {
						leader_board_LV.getItems().clear();
					}
					else {
						leader_board_LV.getItems().add(data.toString());
					}
					//leader_board_LV.getItems().add(data.toString());
				});
			}, ip, port);

			clientConnection.start();

		});

		easyRB.setOnAction(event -> {
			// for now
			instruction.setText("Now, you can play the game. Have Fun!");
			quit_Btn.setDisable(false);
			easyRB.setDisable(true);
			mediumRB.setDisable(true);
			expertRB.setDisable(true);

			button0.setText("");
			button1.setText("");
			button2.setText("");
			button3.setText("");
			button4.setText("");
			button5.setText("");
			button6.setText("");
			button7.setText("");
			button8.setText("");

			button0.setDisable(false);
			button1.setDisable(false);
			button2.setDisable(false);
			button3.setDisable(false);
			button4.setDisable(false);
			button5.setDisable(false);
			button6.setDisable(false);
			button7.setDisable(false);
			button8.setDisable(false);
			this.clientConnection.getDifficulty("Easy");
		});
		mediumRB.setOnAction(event -> {
			// for now
			instruction.setText("Now, you can play the game. Have Fun!");
			quit_Btn.setDisable(false);
			easyRB.setDisable(true);
			mediumRB.setDisable(true);
			expertRB.setDisable(true);

			button0.setText("");
			button1.setText("");
			button2.setText("");
			button3.setText("");
			button4.setText("");
			button5.setText("");
			button6.setText("");
			button7.setText("");
			button8.setText("");

			button0.setDisable(false);
			button1.setDisable(false);
			button2.setDisable(false);
			button3.setDisable(false);
			button4.setDisable(false);
			button5.setDisable(false);
			button6.setDisable(false);
			button7.setDisable(false);
			button8.setDisable(false);
			this.clientConnection.getDifficulty("Medium");
		});

		expertRB.setOnAction(event -> {
			// for now
			instruction.setText("Now, you can play the game. Have Fun!");
			quit_Btn.setDisable(false);
			easyRB.setDisable(true);
			mediumRB.setDisable(true);
			expertRB.setDisable(true);

			button0.setText("");
			button1.setText("");
			button2.setText("");
			button3.setText("");
			button4.setText("");
			button5.setText("");
			button6.setText("");
			button7.setText("");
			button8.setText("");

			button0.setDisable(false);
			button1.setDisable(false);
			button2.setDisable(false);
			button3.setDisable(false);
			button4.setDisable(false);
			button5.setDisable(false);
			button6.setDisable(false);
			button7.setDisable(false);
			button8.setDisable(false);
			this.clientConnection.getDifficulty("Expert");
		});

		playAgain_Btn.setOnAction(event -> {
			instruction.setText("First, Select the difficult you want to play against");
			playAgain_Btn.setDisable(true);
			quit_Btn.setDisable(true);
			easyRB.setSelected(false);
			mediumRB.setSelected(false);
			expertRB.setSelected(false);
			easyRB.setDisable(false);
			mediumRB.setDisable(false);
			expertRB.setDisable(false);
			button0.setText("");
			button1.setText("");
			button2.setText("");
			button3.setText("");
			button4.setText("");
			button5.setText("");
			button6.setText("");
			button7.setText("");
			button8.setText("");
		});

		quit_Btn.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});

		// DESIGN BOXES

		VBox centerLbl = new VBox(10);
		centerLbl.getChildren().addAll(enterPane);

		HBox portArea = new HBox(10);
		portArea.getChildren().addAll(portNum_Lbl,port_num_TF);
		portArea.setMargin(portNum_Lbl, new Insets(30,0,0,30));
		portArea.setMargin(port_num_TF, new Insets(25,0,0,0));

		HBox ipAddArea = new HBox(10);
		ipAddArea.getChildren().addAll(ip_add_Lbl,ip_add_TF);
		ipAddArea.setMargin(ip_add_Lbl, new Insets(10,0,0,50));
		ipAddArea.setMargin(ip_add_TF, new Insets(5,0,0,0));

		VBox intro_screen = new VBox(20);
		intro_screen.getChildren().addAll(centerLbl,portArea,ipAddArea,connectPane);
		intro_screen.setMargin(connectPane, new Insets(30,0,0,0));

		VBox leader_board_box = new VBox(10);
		leader_board_box.getChildren().addAll(topPlayersPane,leader_board_LV);
		leader_board_box.setMargin(leader_board_LV,new Insets(0,0,10,20));
		leader_board_box.setMargin(topPlayersPane,new Insets(10,0,0,0));
		HBox select_diff_box = new HBox(20);

		select_diff_box.getChildren().addAll(easyRB,mediumRB,expertRB);
		select_diff_box.setMargin(easyRB,new Insets(5,0,0,40));
		select_diff_box.setMargin(mediumRB,new Insets(5,0,0,0));
		select_diff_box.setMargin(expertRB,new Insets(5,0,0,0));

		HBox game_over_box = new HBox(10);
		game_over_box.getChildren().addAll(playAgain_Btn,quit_Btn);
		game_over_box.setMargin(playAgain_Btn,new Insets(10,0,0,40));
		game_over_box.setMargin(quit_Btn,new Insets(10,0,0,0));

		HBox row1 = new HBox(10);
		row1.getChildren().addAll(button0,button1,button2);
		row1.setMargin(button0,new Insets(0,0,0,10));

		HBox row2 = new HBox(10);
		row2.getChildren().addAll(button3,button4,button5);
		row2.setMargin(button3,new Insets(0,0,0,10));

		HBox row3 = new HBox(10);
		row3.getChildren().addAll(button6,button7,button8);
		row3.setMargin(button6,new Insets(0,0,0,10));

		VBox tic_tac_toe_box = new VBox(10);
		tic_tac_toe_box.getChildren().addAll(row1,row2,row3);

		VBox game_side = new VBox(10);
		game_side.getChildren().addAll(selectDiffPane,select_diff_box,tic_tac_toe_box,game_over_box);
		game_side.setMargin(selectDiffPane,new Insets(10,0,0,0));

		HBox main_screen = new HBox(30);
		main_screen.getChildren().addAll(leader_board_box,game_side);

		VBox game_screen = new VBox(10);
		game_screen.getChildren().addAll(main_screen, instructPane);
		// BACK GROUND
		intro_screen.setStyle("-fx-background-image: url(\"connect.jpg\");" + "-fx-background-size:cover,auto;");
		game_screen.setStyle("-fx-background-image: url(\"back2.jpg\");" + "-fx-background-size:cover,auto;");
		// SCENES
		start_scene = new Scene(intro_screen,500,400);
		game_scene = new Scene(game_screen, 700,520);


		primaryStage.setTitle("CONNECTING");
		primaryStage.setScene(start_scene);
		primaryStage.show();
	}

}