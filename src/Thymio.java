import de.ur.mi.graphics.Image;



public class Thymio extends Image{
	
	// Fixed, don't change, adjustments in Settings Class
	private static final int DISTANCE = Settings.getFieldWidth();
	private static final int FIELD_WIDTH = Settings.getFieldWidth();;
	private static final int FIELD_HEIGHT = Settings.getFieldHeight();
	private static final int CANVAS_HEIGHT = Settings.getCanvasHeight();
	private static final int CANVAS_WIDTH = Settings.getCanvasWidth();
	private static int CURRENT_ROTATION = 0;
	
//	Constructor
	public Thymio(double xPos, double yPos,double width, double height, String src){
		super(xPos,yPos,width,height,src);
	}
	
/* 	Sets rotation based on command sent to Thymio
* 	getPixelArray turns the Image into an array, flips it X-times and setPixelArray turns it back into an image
*	North / Up = 0
*	East / Right = 90
*	South / Down = 180
*	West / Left = 270
*/
	private void setRotatation(String movement) {
		
		System.out.println(movement);
		switch (movement) {
		case "left":
			if(CURRENT_ROTATION == 0){
				super.setPixelArray(rotateMatrixLeft(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 90){
				super.setPixelArray(rotateMatrixLeft(rotateMatrixLeft(super.getPixelArray())));
			}else if(CURRENT_ROTATION == 180){
				super.setPixelArray(rotateMatrixRight(super.getPixelArray()));
			}
			CURRENT_ROTATION = 270 ;
			break;
		case "right":
			if(CURRENT_ROTATION == 0){
				super.setPixelArray(rotateMatrixRight(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 270){
				super.setPixelArray(rotateMatrixRight(rotateMatrixRight(super.getPixelArray())));
			}else if(CURRENT_ROTATION == 180){
				super.setPixelArray(rotateMatrixLeft(super.getPixelArray()));
			}
			CURRENT_ROTATION = 90;
			break;
		case "up":
			if(CURRENT_ROTATION == 90){
				super.setPixelArray(rotateMatrixLeft(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 270){
				super.setPixelArray(rotateMatrixRight(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 180){
				super.setPixelArray(rotateMatrixLeft(rotateMatrixLeft(super.getPixelArray())));
			}
			CURRENT_ROTATION = 0;
			break;
		case "down":
			if(CURRENT_ROTATION == 90){
				super.setPixelArray(rotateMatrixRight(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 270){
				super.setPixelArray(rotateMatrixLeft(super.getPixelArray()));
			}else if(CURRENT_ROTATION == 0){
				super.setPixelArray(rotateMatrixLeft(rotateMatrixLeft(super.getPixelArray())));
			}
			CURRENT_ROTATION = 180;
			break;
		default:
			break;
		}
		
	}
	
	// Moves Thymio left
	public void moveLeft() {	
		if(super.getX()>0  && collision("Left") == false) {
			super.move(-DISTANCE, 0);
			setRotatation("left");	
			try {
				Thread.sleep(Settings.getDelay());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	// Moves Thymio right
	public void moveRight() {
		if(super.getX() < CANVAS_WIDTH && collision("Right") == false){
			super.move(DISTANCE, 0);
			setRotatation("right");
			try {
				Thread.sleep(Settings.getDelay());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	// Moves Thymio up
	public void moveUp() {
		if(super.getY()>0 && collision("Up") == false){
			super.move(0, -DISTANCE);
			setRotatation("up");
			try {
				Thread.sleep(Settings.getDelay());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Moves Thymio down
	public void moveDown() {
		if(super.getY() < CANVAS_HEIGHT && collision("Down") == false){
			super.move(0, DISTANCE);
			setRotatation("down");
			try {
				Thread.sleep(Settings.getDelay());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	
	// check for Collisions/Obstacles
	private boolean collision(String direction) {
		Obstacles obs = Settings.getObstacles();
		
			// Calculate surrounding Fields
			int x2 = (int) (super.getX()/FIELD_WIDTH);
			int y2 = (int) (super.getY()/FIELD_HEIGHT);
			
			int left = x2-1;
			int right = x2+1;
			int above = y2-1;
			int below = y2+1;
			
			//  Check if surrounding contains an obstacle
			switch (direction) {
				case "Up":
					if(obs.isObstacle(x2, above)){
						System.out.println("Collision");
						return true;
					}else{
						return false;
					}
				case "Down":
					if(obs.isObstacle(x2, below)){
						System.out.println("Collision");
						return true;
					}else{
						return false;
					}
					
				case "Left":
					if(obs.isObstacle(left, y2)){
						System.out.println("Collision");
						return true;
					}else{
						return false;
					}
							
					
				case "Right":
					if(obs.isObstacle(right, y2)){
						System.out.println("Collision");
						return true;
					}else{
						return false;
					}
					
				default:
					return false;
			}

	
		
	}
	
	
	// rotate Image to the right
	public int[][] rotateMatrixRight(int[][] matrix)
	{

	    int w = matrix.length;
	    int h = matrix[0].length;
	    int[][] ret = new int[h][w];
	    for (int i = 0; i < h; ++i) {
	        for (int j = 0; j < w; ++j) {
	            ret[i][j] = matrix[w - j - 1][i];
	        }
	    }
	    return ret;
	}


	// rotate Image to the left
	public int[][] rotateMatrixLeft(int[][] matrix)
	{
	    int w = matrix.length;
	    int h = matrix[0].length;   
	    int[][] ret = new int[h][w];
	    for (int i = 0; i < h; ++i) {
	        for (int j = 0; j < w; ++j) {
	            ret[i][j] = matrix[j][h - i - 1];
	        }
	    }
	    return ret;
	}
	
	// Draws Thymio
	public void draw() {
		super.draw();
	}
	
	// Return Thymios X Coord
	public double getXPos(){
		return super.getX();
	}
	// Return Thymios Y Coord
	public double getYPos(){
		return super.getY();
	}
	// Return Thymios X Coord as Field  (0, 1, 2 etc)
	public int getXPosAsField(){
		return (int)super.getX()/Settings.getFieldHeight();
	}
	// Return Thymios Y Coord as Field
	public int getYPosAsField(){
		return (int)super.getY()/Settings.getFieldHeight();
	}
}