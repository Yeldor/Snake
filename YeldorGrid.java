package snake;

import java.util.Random;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class YeldorGrid extends Pane {
	final public int columns, rows, cellSize;
	public Node[][] cell;
	
	YeldorGrid(int columns, int rows, int unitSize) {
		this.columns = columns;
		this.rows = rows;
		this.cellSize = unitSize;
		cell = new Node[rows][columns];
	
	}
	
	public int getRandomColumn() {
		Random random = new Random();
		int randomcol = random.nextInt(columns);
		return randomcol;
		
	}
	
	public int getRandomRow() {
		Random random = new Random();
		int randomrow = random.nextInt(rows);
		return randomrow;
		
	}
	
	void add(Node node, int x, int y) {
		getChildren().remove(node);
		cell[x][y] = node;
		node.setTranslateX(x*cellSize);
		node.setTranslateY(y*cellSize);
		getChildren().add(node);
		
	}
	
	Object getCell(int x, int y) {
		return cell[x][y];
		
	}
	
	void remove(int x, int y) {
		Node remove = cell[x][y];
		cell[x][y] = null;
		getChildren().remove(remove);
		
	}
	
	void remove(Node node) {
		for (Node[] n : cell) {
			for (int i = 0; i < n.length; i++) {
				if (n[i] == node) {
					n[i] = null;
					getChildren().remove(node);
				}
			}
		}
		
	}
	
	void moveNode(Node node, int x, int y) {
		remove(node);
		add(node, x, y);
	}
	
	int getColoumn(Node node) {
		int columnNo=-1;
		for (Node[] n : cell) {
			columnNo++;
			for (int i = 0; i < n.length; i++) {
				if (n[i] == node) {
					return columnNo;
				}
			}
		}
		return -1;
	}
	
	int getRow(Node node) {
		for (Node[] n : cell) {
			int rowNo=-1;
			for (int i = 0; i < n.length; i++) {
				rowNo++;
				if (n[i] == node) {
					return rowNo++;
				}
			}
		}
		return -1;
	}
	
	void printCells() {
		for (Node[] x : cell)
		{
		   for (Node y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
	}
	
}
