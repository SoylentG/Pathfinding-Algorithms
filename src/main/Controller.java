package main;
import de.ur.mi.graphicsapp.*;
import map.Chessboard;
import thymio.Thymio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;

import pathfinding.Dijkstra;
import pathfinding.Node;


public class Controller extends GraphicsApp implements KeyListener {

	// Fixed, don't change, adjustments in Settings Class
		private static final int FIELD_HEIGHT = Settings.getFieldHeight();
		private static final int CANVAS_HEIGHT = Settings.getCanvasHeight();
		private static final int CANVAS_WIDTH = Settings.getCanvasWidth();
		private static int THYMIO_STARTFIELD_X = Settings.getStartX();
		private static int THYMIO_STARTFIELD_Y = Settings.getStartY();;
		private static Chessboard board = Settings.getBoard();
		public static Thymio thymio ; 
		
		
	// Basic setup
    public void setup() {
      	size(CANVAS_WIDTH,CANVAS_HEIGHT);   	 	
      	thymio = new Thymio(THYMIO_STARTFIELD_X, THYMIO_STARTFIELD_Y, FIELD_HEIGHT, FIELD_HEIGHT, Settings.getThymioImg(),Settings.getThymioRotation());
      	String[][] boardstr =board.boardAsArray();
      	board.createNodes();
      	Dijkstra.addToVisited(board.getNodes().get(thymio.getPosAsID()),thymio.getOrientation());
    }
    
    
   // Draws the Views 
	public void draw(){
		Views.draw();
		thymio.draw();
    }
    

	
	 
//	Very basic test to automate Thymios movement. Moves Thymio to a given coordinate. See @keyPressed case't' to see an example. 
	
	private void moveToPos(Thymio t, int x, int y){
		int current_x = (int) thymio.getXPosAsField();
		int current_y = (int) thymio.getYPosAsField();
		
		if(current_y < y){
			while(current_y < y){
				int old = current_y;
				thymio.moveDown();
				current_y = (int) thymio.getYPosAsField();
				if(current_y == old){
					break;
				}
			}
		}else if(current_y > y){
			while(current_y > y){
				int old = current_y;
				thymio.moveUp();
				current_y = (int) thymio.getYPosAsField();
				if(current_y == old){
					break;
				}
			}
		}
		if(current_x > x){
			while(current_x > x){
				int old = current_x;
				thymio.moveLeft();
				current_x = (int) thymio.getXPosAsField();
				if(current_x == old){
					break;
				}
			}
		}else if(current_x < x){
			while(current_x < x){
				int old = current_x;
				thymio.moveRight();
				current_x = (int) thymio.getXPosAsField();
				if(current_x == old){
					break;
				}
			}
		}
	}
    
    // Handles the keypresses to move Thymio accordingly
	@Override
	public void keyPressed(KeyEvent e) {
    	
    	switch (e.getKeyChar()) {
		case 'd':
			thymio.moveRight();		
			break;
		case 'w':
			thymio.moveUp();			
			break;
		case 'a':
			thymio.moveLeft();			
			break;
		case 's':
			thymio.moveDown();
			break;
		case 'g': // Only for testing/debugging purposes
			char[][] test = new char[20][8];
			int x = 0;
			for(String[] entry : Settings.getCsv()){
				
				for (int i = 0; i < entry.length; i++) {
					String t = entry[i];
					char[] arr = t.toCharArray();
					for (int j = 0; j < arr.length; j++) {
						test[i][x] = arr[j];
						System.out.print(arr[j]);
					}
					
				}
				x++;
				
			}
			
			break;
		case 't': // Only for testing/debugging purposes
			Node current = board.getNodes().get(thymio.getPosAsID());
			HashMap<String,Node> neighbours = board.getNeighbourNodes(current);
			System.out.print("Possible movements: ");
			for(String neighbour : neighbours.keySet()){
				System.out.print("{"+neighbour+"}");
			}
			System.out.println("");
			System.out.print("Possible Destinations:");
			for(Node neighbour : neighbours.values()){
				System.out.print("["+neighbour.getChessCoord()+"]");
			}
			System.out.println("");
			break;
		case '1': // Only for testing/debugging purposes
//			int cost = board.calculateAirDistance(thymio.getXPosAsField(), thymio.getYPosAsField(), Settings.getThymioEndField_X(), Settings.getThymioEndField_Y());
//			System.out.println("Airdistance to goal:"+cost);
			String visitedNodes = "";
	    	for (int i = 0; i < Dijkstra.getVisitedList().size(); i++) {
	    		visitedNodes += Dijkstra.getVisitedList().get(i).getChessCoord()+",";
			}
			System.out.println("Visited: "+visitedNodes);
			break;
		
    	}
    	Views.draw();
		System.out.println("Current Position: ["+board.getNodes().get(thymio.getPosAsID()).getChessCoord()+"]");
		Node current = board.getNodes().get(thymio.getPosAsID());
		HashMap<String,Node> neighbours = board.getNeighbourNodes(current);
		System.out.print("Possible movements: ");
		for(String neighbour : neighbours.keySet()){
			System.out.print("{"+neighbour+"}");
		}
		System.out.println("");
		System.out.print("Possible Destinations:");
		for(Node neighbour : neighbours.values()){
			System.out.print("["+neighbour.getChessCoord()+"]");
		}
		System.out.println("");;
//		System.out.println("Current Coordinates: ["+board.getCoord(thymio.getXPosAsField(),thymio.getYPosAsField())+"]");
		System.out.println("Should go to Position: ["+Dijkstra.getCheapestNeighbourChess(thymio.getPosAsNode())+"]");
//		System.out.println("Should go to Coordinates: "+Dijkstra.getCheapestNeighbour(thymio.getPosAsNode()));
		System.out.println("Reached destinatio: "+reachedDest());
		System.out.println("_____________________________________________");
		
		
	}


//	Checks if Thymio has reached the destination
	private boolean reachedDest() {
		if(thymio.getPosAsNode() == Settings.getEndNode()) {
			return true;
		}
		return false;
	}

}