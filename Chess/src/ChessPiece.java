/**
 * 
 * @author luzefu
 * Created on December 3rd, 2012
 * A Model for a Chess Piece
 * 
 */

public abstract class ChessPiece {
	protected int x, y;	    			//Position of the chess piece
	protected String type;				//The type of the chess piece
	protected String color;				//The color of the chess
	protected boolean[][] validMove;    //Valid Positions which the chess piece can move to
	protected boolean[][] threatMap;	//Threat Map for the chess
	protected int id;     				//The id of the chess
	protected static int total = 0;     //total pieces created
	
	//Constructor
	public ChessPiece(int posX, int posY, String color){
		this.x = posX;
		this.y = posY;
		this.color = color;
		this.validMove = new boolean[8][8];
		this.threatMap = new boolean[8][8];
		id = total++;
	}
	
	//Getters
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public String getType() {
		return type;
	}

	public String getColor() {
		return color;
	}
	
	public int getID(){
		return this.id;
	}
	
	public boolean[][] getValidMove(){
		return this.validMove;
	}
	
	public boolean[][] getThreatMap(ChessBoard ref){
		this.updateThreat(ref);
		return this.threatMap;
	}
	
	//Movements & Calculations
	/**
	 * 
	 * @param desX
	 * @param desY
	 * @return if the move is valid (position will not change if move is invalid)
	 */
	public boolean move(int desX, int desY){
		if (desX >= 0 && desX < 8 && desY >= 0 && desY < 8){
			if (validMove[desX][desY]){
				x = desX;
				y = desY;
				return true;
			}
		}
		return false;
	}
	
	protected void emptyValidMove(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				this.validMove[i][j] = false;
			}
		}
	}
	
	protected void emptyThreatMap(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				this.threatMap[i][j] = false;
			}
		}
	}
	
	abstract public void calculateValidMove(ChessBoard ref);
	
	abstract public void generateThreatMap(ChessBoard ref);
	
	public void updateThreat(ChessBoard ref){
		emptyThreatMap();
		generateThreatMap(ref);
		this.threatMap[x][y] = false;
	}
	
	public void updateState(ChessBoard ref){
		emptyValidMove();
		calculateValidMove(ref);
		this.validMove[x][y] = false;
	}
	
	//Useful functions for calculating valid moves recursively with a depth control
	protected void calculateValidRankForward(ChessBoard ref, int y, int level){
		if(level != 0 && y < 8){
			if (ref.getChessPieceAtPosition(this.x, y) == null){
				this.validMove[this.x][y] = true;
				calculateValidRankForward(ref, y+1, level-1);
			}else if(!ref.getChessPieceAtPosition(this.x, y).getColor().equals(this.color)){
				this.validMove[this.x][y] = true;
			}else
				this.validMove[this.x][y] = false;
		}
	}
	
	protected void calculateValidRankBackward(ChessBoard ref, int y, int level){
		if(level != 0 && y >= 0){
			if (ref.getChessPieceAtPosition(this.x, y) == null){
				this.validMove[this.x][y] = true;
				calculateValidRankBackward(ref, y-1, level-1);
			}else if(!ref.getChessPieceAtPosition(this.x, y).getColor().equals(this.color)){
				this.validMove[this.x][y] = true;
			}else
				this.validMove[this.x][y] = false;
		}
	}
	
	protected void calculateValidFilesLeft(ChessBoard ref, int x, int level){
		if(level != 0 && x >= 0){
			if (ref.getChessPieceAtPosition(x, this.y) == null){
				this.validMove[x][this.y] = true;
				calculateValidFilesLeft(ref, x-1, level-1);
			}else if(!ref.getChessPieceAtPosition(x, this.y).getColor().equals(this.color)){
				this.validMove[x][this.y] = true;
			}else
				this.validMove[x][this.y] = false;
		}
	}
	
	protected void calculateValidFilesRight(ChessBoard ref, int x, int level){
		if(level != 0 && x < 8){
			if (ref.getChessPieceAtPosition(x, this.y) == null){
				this.validMove[x][this.y] = true;
				calculateValidFilesRight(ref, x+1, level-1);
			}else if(!ref.getChessPieceAtPosition(x, this.y).getColor().equals(this.color)){
				this.validMove[x][this.y] = true;
			}else
				this.validMove[x][this.y] = false;
		}
	}
	
	protected void calculateValidDiagnolPosKUp(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x < 8 && y < 8){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.validMove[x][y] = true;
				calculateValidDiagnolPosKUp(ref,x+1,y+1,level-1);
			}else if(!ref.getChessPieceAtPosition(x, y).getColor().equals(this.color)){
				this.validMove[x][y] = true;
			}else
				this.validMove[x][y] = false;
		}
	}
	
	protected void calculateValidDiagnolPosKDown(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x >= 0 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.validMove[x][y] = true;
				calculateValidDiagnolPosKDown(ref,x-1,y-1,level-1);
			}else if(!ref.getChessPieceAtPosition(x, y).getColor().equals(this.color)){
				this.validMove[x][y] = true;
			}else
				this.validMove[x][y] = false;
		}
	}
	
	protected void calculateValidDiagnolNegKUp(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x >= 0 && y < 8){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.validMove[x][y] = true;
				calculateValidDiagnolNegKUp(ref,x-1,y+1,level-1);
			}else if(!ref.getChessPieceAtPosition(x, y).getColor().equals(this.color)){
				this.validMove[x][y] = true;
			}else
				this.validMove[x][y] = false;
		}
	}
	
	protected void calculateValidDiagnolNegKDown(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x < 8 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.validMove[x][y] = true;
				calculateValidDiagnolNegKDown(ref,x+1,y-1,level-1);
			}else if(!ref.getChessPieceAtPosition(x, y).getColor().equals(this.color)){
				this.validMove[x][y] = true;
			}else
				this.validMove[x][y] = false;
		}
	}
	
	protected void calculateValidAtPosition(ChessBoard ref, int x, int y){
		if(x < 8 && x >= 0 && y < 8 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.validMove[x][y] = true;
			}else if(!ref.getChessPieceAtPosition(x, y).getColor().equals(this.color)){
				this.validMove[x][y] = true;
			}else
				this.validMove[x][y] = false;
		}
	}

	//Useful functions for calculating threat map recursively
	protected void calculateThreatRankForward(ChessBoard ref, int y, int level){
		if(level != 0 && y < 8){
			if (ref.getChessPieceAtPosition(this.x, y) == null){
				this.threatMap[this.x][y] = true;
				calculateThreatRankForward(ref, y+1, level-1);
			}else{
				this.threatMap[this.x][y] = true;
			}
		}
	}
	
	protected void calculateThreatRankBackward(ChessBoard ref, int y, int level){
		if(level != 0 && y >= 0){
			if (ref.getChessPieceAtPosition(this.x, y) == null){
				this.threatMap[this.x][y] = true;
				calculateThreatRankBackward(ref, y-1, level-1);
			}else{
				this.threatMap[this.x][y] = true;
			}
		}
	}
	
	protected void calculateThreatFilesLeft(ChessBoard ref, int x, int level){
		if(level != 0 && x >= 0){
			if (ref.getChessPieceAtPosition(x, this.y) == null){
				this.threatMap[x][this.y] = true;
				calculateThreatFilesLeft(ref, x-1, level-1);
			}else
				this.threatMap[x][this.y] = true;
		}
	}
	
	protected void calculateThreatFilesRight(ChessBoard ref, int x, int level){
		if(level != 0 && x < 8){
			if (ref.getChessPieceAtPosition(x, this.y) == null){
				this.threatMap[x][this.y] = true;
				calculateThreatFilesRight(ref, x+1, level-1);
			}else
				this.threatMap[x][this.y] = true;
		}
	}
	
	protected void calculateThreatDiagnolPosKUp(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x < 8 && y < 8){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.threatMap[x][y] = true;
				calculateThreatDiagnolPosKUp(ref,x+1,y+1,level-1);
			}else 
				this.threatMap[x][y] = true;
		}
	}
	
	protected void calculateThreatDiagnolPosKDown(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x >= 0 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.threatMap[x][y] = true;
				calculateThreatDiagnolPosKDown(ref,x-1,y-1,level-1);
			}else
				this.threatMap[x][y] = true;
		}
	}
	
	protected void calculateThreatDiagnolNegKUp(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x >= 0 && y < 8){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.threatMap[x][y] = true;
				calculateThreatDiagnolNegKUp(ref,x-1,y+1,level-1);
			}else
				this.threatMap[x][y] = true;
		}
	}
	
	protected void calculateThreatDiagnolNegKDown(ChessBoard ref, int x, int y, int level){
		if(level != 0 && x < 8 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.threatMap[x][y] = true;
				calculateThreatDiagnolNegKDown(ref,x+1,y-1,level-1);
			}else
				this.threatMap[x][y] = true;
		}
	}
	
	protected void calculateThreatAtPosition(ChessBoard ref, int x, int y){
		if(x < 8 && x >= 0 && y < 8 && y >= 0){
			if (ref.getChessPieceAtPosition(x, y) == null){
				this.threatMap[x][y] = true;
			}else
				this.threatMap[x][y] = true;
		}
	}
}