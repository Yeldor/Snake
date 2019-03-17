/* CREATED BY YELDOR
 * VISIT GITHUB.COM/YELDOR FOR MORE
 * THIS CODE IS ONLY USUABLE FOR NON-PROFIT & EDUCATIONAL USE
 */

package snake;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class snakemain extends Application {
	Random random = new Random();
	YeldorGrid yg = new YeldorGrid(10,10,20);
	int posX = random.nextInt(yg.columns), posY = random.nextInt(yg.rows), foodposX = random.nextInt(yg.columns), foodposY = random.nextInt(yg.rows);
	Stack snakeBody = new Stack();
	int[] oldRow = new int[100];
	int[] oldCol = new int[100];
	int lastBodyCol, lastBodyRow;
	
	public static void main(String... args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		
		stage.setTitle("Snake by Yeldor");
		Random random = new Random();
		Scene scene = new Scene(yg, 10*yg.cellSize, 10*yg.cellSize);
		scene.setFill(Color.BLACK);	
		Rectangle head = new Rectangle(20,20,Color.GREEN.brighter().brighter());
		Rectangle food = new Rectangle(20,20, Color.YELLOW.brighter().brighter());
		yg.add(food, foodposX, foodposY);
		yg.add(head, posX, posY);
		snakeBody.add(head);
		scene.setOnKeyPressed(e -> {
		String string = e.getText();
		try {
			switch (string){
				case "w": moveSnake('w');
					break;
				case "a": moveSnake('a');
					break;
				case "s": moveSnake('s');
					break;
				case "d": moveSnake('d');
					break;
			}
		int foodPrex, foodPrey;
				if (posX == foodposX && posY == foodposY) {
					foodPrex = foodposX;
					foodPrey = foodposY;
					foodposX = random.nextInt(yg.columns);
					foodposY = random.nextInt(yg.rows);
					for (int ycheck : oldRow) {
						if (ycheck == foodposY)
							for (int xcheck : oldCol) {
								if (xcheck == foodposX)
									foodposX = random.nextInt(yg.columns);
									foodposY = random.nextInt(yg.rows);
								}
					if (foodposX == foodPrex && foodposY == foodPrey)
						foodposX = random.nextInt(yg.columns);
						foodposY = random.nextInt(yg.rows);
					}
				yg.moveNode(food, foodposX, foodposY);
				addBody();
				}
		}
		catch (ArrayIndexOutOfBoundsException fail) {
			if (posX < 0 || posY < 0 || posX >= yg.columns || posY >= yg.rows)
				missionFailed();
		}
		
		});

		stage.setScene(scene);
		stage.show();	

	}
	
	void missionFailed() {
		Stage failedPopup = new Stage();
		failedPopup.setTitle("You Died!");
		failedPopup.initModality(Modality.APPLICATION_MODAL);
		Button OK = new Button("OK");
		Group group = new Group();
		Scene miniScene = new Scene(group, 150, 100);
		group.getChildren().add(OK);
		failedPopup.setScene(miniScene);
		failedPopup.show();
		OK.setOnMouseClicked(q -> {
		failedPopup.close();
		System.exit(0);
		});
	}
	
	void addBody() {
		Rectangle bodyPart = new Rectangle(20,20,Color.BLUE.brighter().brighter());
		snakeBody.add(bodyPart);
		updateSnake();
	}
	
	void moveSnake(char alpha){
		getCord();
		switch (alpha) {
		case 'w':	yg.moveNode((Node) snakeBody.elementAt(0), posX, posY=posY-1);
			break;
		case 'a':	yg.moveNode((Node) snakeBody.elementAt(0), posX=posX-1, posY);
			break;
		case 's':	yg.moveNode((Node) snakeBody.elementAt(0), posX, posY=posY+1);
			break;
		case 'd':	yg.moveNode((Node) snakeBody.elementAt(0), posX=posX+1, posY);
			break;
		}
		updateSnake();
		snakeCheck();
	}
	
	void updateSnake() {
		for (int i = 1; i<snakeBody.size(); i++) {
			yg.moveNode((Node) snakeBody.elementAt(i), oldCol[i-1], oldRow[i-1]);
		}
	}

	void snakeCheck() {
		for (int i = 1; i < snakeBody.size(); i++) {
			System.out.println("Node # " + i + " Row: " + yg.getRow((Node) snakeBody.elementAt(i)) + " Col: " + yg.getColoumn((Node) snakeBody.elementAt(i)));
			if (yg.getRow((Node) snakeBody.elementAt(i)) == posY && yg.getColoumn((Node) snakeBody.elementAt(i)) == posX) {
				missionFailed();
			}
			if (lastBodyRow == posY && lastBodyCol == posX) {
				missionFailed();
			}
		}
	}

	void getCord() {
		lastBodyRow = yg.getRow((Node) snakeBody.lastElement());
		lastBodyCol = yg.getColoumn((Node) snakeBody.lastElement());
		for(int i = 0; i < snakeBody.size(); i++) {
			oldRow[i] = yg.getRow((Node) snakeBody.elementAt(i));
		}
		for(int i = 0; i < snakeBody.size(); i++) {
			oldCol[i] = yg.getColoumn((Node) snakeBody.elementAt(i));
		}
	}
}

//updated version as of 3/17/2019
