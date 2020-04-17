package fr.eno.launcher;

import java.util.ArrayList;

import fr.eno.launcher.auth.AuthenticationException;
import fr.eno.launcher.main.Launcher;
import fr.eno.launcher.utils.ObjectsUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherApp extends Application
{
	public static CheckBox crack = new CheckBox();
	public static DialogPane warning = new DialogPane();
	public static Button login = new Button();
	public static Button close = new Button();
	public static Button reduce = new Button();
	public static Button maximize = new Button();
	public static Text warningTitle = new Text();
	public static Text usernameTitle = new Text();
	public static TextField username = new TextField();
	public static Text passwordTitle = new Text();
	public static PasswordField password = new PasswordField();
	public static TextArea log = new TextArea();
	public static Label checkLogs = new Label("");
	public static String labelText = "N/A";
	public static WebView browser = new WebView();
	public static WebEngine webEngine = browser.getEngine();
	public static ProgressBar bar = new ProgressBar(0);
	public static double progress = 0;
	public static boolean isCrack = false;
	public static boolean setClosed = false;
	private static Launcher launcher = null;
	public static Button cursor = new Button();
	public static Alert alert = new Alert(AlertType.WARNING);
	
	private static final int textFieldFront = 40 + 25 / 2;
	private static final int buttonFront = 40;
	
	public static void main(String [] args) throws Exception
	{
        launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		Pane root = new Pane();
		root.setPrefSize(960, 600);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Pyrion Launcher");
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(ResourceHelper.loadImage("icon.png"));
		
		final ImageView backgroundImage = new ImageView();
		Image image = ResourceHelper.loadImage("wallpaper.png");
        backgroundImage.setImage(image);
        backgroundImage.fitHeightProperty().bind(root.prefHeightProperty().add(10));
        backgroundImage.fitWidthProperty().bind(root.prefWidthProperty().add(10));
        
        root.getChildren().add(backgroundImage);
        	
		
		 /*
		 * Warning Text
		 */
		warningTitle.setText("/!\\ Email ou mot de passe invalides /!\\");
		warningTitle.setFont(new Font(20));
		warningTitle.setTranslateX(buttonFront);
		warningTitle.setStyle("-fx-font-weight: bold");
		warningTitle.setTranslateY(320);
		warningTitle.setFill(Color.DARKRED);
		warningTitle.setVisible(false);
		
		/*
		 * 
		 */
		warning.setTranslateX(10);
		warning.setTranslateY(10);
		warning.setPrefSize(root.getPrefWidth() - 10, root.getPrefHeight() - 10);
		//root.getChildren().add(warning);
		
		/*
		 * Login Button
		 */
		login.setTranslateX(buttonFront);
		login.setTranslateY(340);
		login.setBackground(Background.EMPTY);
		login.setPrefSize(300, 40);
		
		final ImageView loginImageView = new ImageView();
        Image loginImage = ResourceHelper.loadImage("button.png");
        loginImageView.setImage(loginImage);
        loginImageView.fitHeightProperty().bind(login.prefHeightProperty().add(10));
        loginImageView.fitWidthProperty().bind(login.prefWidthProperty().add(10));
		
		login.setGraphic(loginImageView);
		login.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent event)
            {
            	Thread t = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						fr.eno.launcher.ProgressBar bar = new fr.eno.launcher.ProgressBar();
		            	
		            	usernameTitle.setOpacity(0.5);
		            	username.setDisable(true);
		            	passwordTitle.setOpacity(0.5);
		            	password.setDisable(true);
		            	crack.setDisable(true);
		            	login.setDisable(true);
		            	LauncherApp.bar.setVisible(true);
		            	LauncherApp.checkLogs.setVisible(true);
		            	
		            	launcher = new Launcher();
						
		            	if(!isCrack)
		            	{
		            		try
		    				{
		    					launcher.auth();
		    					
		    					ObjectsUtils.getObjectsFile();
		    					bar.objectsProgressBar();
		    					
		    				}
		    				catch (AuthenticationException e)
		    				{
		    					System.out.println("/!\\ Invalids credentials or password /!\\");
		    					warningTitle.setVisible(true);
		    					usernameTitle.setOpacity(1);
		    					username.setDisable(false);
		    					passwordTitle.setOpacity(1);
		    					password.setDisable(false);
		    					crack.setDisable(false);
		    					login.setDisable(false);
		    					LauncherApp.bar.setVisible(false);
		    					LauncherApp.checkLogs.setVisible(false);
		    					alert.setContentText("Erreur : " + e.getErrorModel().getErrorMessage());
		    					alert.showAndWait();
		    				}
		            	}
		            	else
		            	{
							ObjectsUtils.getObjectsFile();
							bar.objectsProgressBar();
		            	}
					}
				});
				t.start();
            }
        });
		
		/*
		 * Username Title
		 */
		usernameTitle.setText("E-mail :");
		usernameTitle.setTranslateX(53);
		usernameTitle.setFont(new Font(32));
		usernameTitle.setFill(Color.WHITE);
		usernameTitle.setTranslateY(91);
		
		/*
		 * Username Text Field
		 */		
		username.setTooltip(new Tooltip("Username ou E-mail"));
		username.setTranslateX(textFieldFront);
		username.setFont(new Font(24));
		username.setTranslateY(100);
		username.setPrefSize(300, 40);
		
		
		
		alert.setTitle("kdjgiusogjniskn");
		
		/*
		 * Password Title
		 */
		passwordTitle.setText("Mot de Passe :");
		passwordTitle.setTranslateX(53);
		passwordTitle.setFont(new Font(32));
		passwordTitle.setFill(Color.WHITE);
		passwordTitle.setTranslateY(191);
		
		/*
		 * Password Text Field
		 */
		password.setTooltip(new Tooltip("Mot de Passe"));
		password.setTranslateX(textFieldFront);
		password.setFont(new Font(24));
		password.setTranslateY(200);
		password.setPrefSize(300, 40);
		
		
		crack.setText("Pas de compte Premium ?");
		crack.setTranslateX(55);
		crack.setFont(new Font(20));
		crack.setTextFill(Color.WHITE);
		crack.setTranslateY(265);
		crack.setPrefSize(300, 20);
		crack.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				isCrack = !isCrack;
				System.out.println("Is Crack : " + isCrack);
				
				if(isCrack)
				{
					usernameTitle.setText("Username :");
					passwordTitle.setVisible(false);
					password.setVisible(false);
					
					crack.setTranslateY(crack.getTranslateY() - 100);
					login.setTranslateY(login.getTranslateY() - 100);
				}
				else
				{
					usernameTitle.setText("E-mail :");
					passwordTitle.setVisible(true);
					password.setVisible(true);
					
					crack.setTranslateY(crack.getTranslateY() + 100);
					login.setTranslateY(login.getTranslateY() + 100);
				}
			}
		});        
         
        // Load page
        webEngine.load("pyrion-corp.github.io");
        webEngine.setJavaScriptEnabled(true);
        browser.setTranslateX(420);
        browser.setTranslateY(50);
        browser.setPrefSize(500, 510);
        
        
        final ImageView closeImageView = new ImageView();
        Image closeImage = ResourceHelper.loadImage("close.png");
        closeImageView.setImage(closeImage);
        closeImageView.fitHeightProperty().bind(close.prefHeightProperty());
        closeImageView.fitWidthProperty().bind(close.prefWidthProperty());
        
        final ImageView reduceImageView = new ImageView();
        Image reduceImage = ResourceHelper.loadImage("reduce.png");
        reduceImageView.setImage(reduceImage);
        reduceImageView.fitHeightProperty().bind(close.prefHeightProperty());
        reduceImageView.fitWidthProperty().bind(close.prefWidthProperty());
        
        
        Font checkLogsFont = new Font(20);
        checkLogs.setTranslateY(root.getPrefHeight() - 40d);
        checkLogs.setTextFill(Color.WHITE);
        checkLogs.setFont(checkLogsFont);
        checkLogs.setText("error");
        double width = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(checkLogs.getText(), checkLogs.getFont());
        checkLogs.setVisible(false);
        checkLogs.setTranslateX((root.getPrefWidth() / 2d) - (width / 2d));
        
        root.getChildren().add(checkLogs);
        /*
        Thread tCheckLogs = new Thread(new Runnable()
        {
        	@Override
			public void run()
			{
				boolean run = true;
				while(run)
				{
					checkLogs.setText(labelText);
				}
			}
		});
        //tCheckLogs.start();
        */
        
        bar = new javafx.scene.control.ProgressBar(0);
		bar.setTranslateX(0);
		bar.setTranslateY(582);
		bar.setPrefSize(root.getPrefWidth(), 15);
		bar.setProgress(0);
		bar.setVisible(false);
		root.getChildren().add(bar);
		
		
		/*
         * Reduce button
         */
        reduce.setGraphic(reduceImageView);
        reduce.setText("");
        reduce.setBackground(Background.EMPTY);
        reduce.setPrefSize(40, 40);
        reduce.setTranslateX(864);
        reduce.setTranslateY(0);
        reduce.setOnAction(new EventHandler<ActionEvent>()
        {
			@Override
			public void handle(ActionEvent event)
			{
				stage.setIconified(true);
			}
		});
        
		
        /*
         * Close button
         */
        close.setGraphic(closeImageView);
        close.setText("");
        close.setBackground(Background.EMPTY);
        close.setPrefSize(40, 40);
        close.setTranslateX(908);
        close.setTranslateY(0);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
			@Override
			public void handle(ActionEvent event)
			{
				stage.close();
				System.exit(0);
			}
		});
        
        Thread t1 = new Thread(new Runnable()
        {
        	@Override
			public void run()
			{
				boolean run = true;
				
				while(run)
				{
					if(setClosed)
					{
						stage.close();
					}
				}
			}
		});
        t1.start();
        
        /*
		 * Custom Cursor
		 */
        ImageCursor cursor = new ImageCursor(ResourceHelper.loadImage("cursor.png"));
		root.setCursor(cursor);
		username.setCursor(cursor);
		password.setCursor(cursor);
		browser.setCursor(cursor);
        
        root.getChildren().add(warningTitle);
        root.getChildren().add(close);
        root.getChildren().add(reduce);
		root.getChildren().add(login);
		root.getChildren().add(usernameTitle);
		root.getChildren().add(username);
		root.getChildren().add(passwordTitle);
		root.getChildren().add(password);
		root.getChildren().add(crack);
		root.getChildren().add(browser);
		
		//moveObjectOnScene(root, reduce);
		
		stage.show();		
	}
	
	public static void moveObjectOnScene(Pane root, Node object)
	{
		ArrayList<Button> list = new ArrayList<Button>();
		Button up = new Button("up");
		Button down = new Button("down");
		Button left = new Button("left");
		Button right = new Button("right");
		
		up.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent event)
            {
            	object.setTranslateY(object.getTranslateY() - 1);
            	
            	System.out.println("Y : " + object.getTranslateY());
            }
        });
		
		down.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent event)
            {
            	object.setTranslateY(object.getTranslateY() + 1);
            	
            	System.out.println("Y : " + object.getTranslateY());
            }
        });
		
		left.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent event)
            {
            	object.setTranslateX(object.getTranslateX() - 1);
            	
            	System.out.println("X : " + object.getTranslateX());
            }
        });
		
		right.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent event)
            {
            	object.setTranslateX(object.getTranslateX() + 1);
            	
            	System.out.println("X : " + object.getTranslateX());
            }
        });
		
		list.add(up);
		list.add(down);
		list.add(left);
		list.add(right);
		int i = 10;
		for(Button button : list)
		{
			i = i + 20;
			button.setTranslateX(buttonFront);
			button.setTranslateY(460 + i);
			button.setPrefSize(100, 20);
		}		
		
		root.getChildren().add(up);
		root.getChildren().add(down);
		root.getChildren().add(left);
		root.getChildren().add(right);
	}
	
	public static void closePane(boolean setClosed)
	{
		LauncherApp.setClosed = setClosed;
	}
	
	public static void setLabelText(String text)
	{
		labelText = text;
	}
	
	public static String getUsername()
	{
		return username.getText();
	}
	
	public static String getPassword()
	{
		return password.getText();
	}
}
