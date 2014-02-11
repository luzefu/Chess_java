/**
 * 
 * @author luzefu
 * Created on December 12th, 2012
 * A Model for rook chess piece
 *
 */

public class RookChess extends ChessPiece {
	private boolean isNotMoved;			//Check if it is the first move
	
	//Constructor
	public RookChess(int posX, int posY, String color) {
		super(posX, posY, color);
		this.type = "Rook";
		isNotMoved = true;
	}
	
	//Getters
	public boolean isNotMoved() {
		return isNotMoved;
	}
	
	//Movements & Calculations
	public boolean move(int desX, int desY){
		if (desX >= 0 && desX < 8 && desY >= 0 && desY < 8){
			if (validMove[desX][desY]){
				x = desX;
				y = desY;
				isNotMoved = false;
				return true;
			}
		}
		return false;
	}
	
	public void calculateValidMove(ChessBoard ref){
		this.calculateValidRankForward(ref, y+1, 8);
		this.calculateValidRankBackward(ref, y-1, 8);
		this.calculateValidFilesLeft(ref, x-1, 8);
		this.calculateValidFilesRight(ref, x+1, 8);
		this.validMove[x][y] = false;
	}
	
	public void generateThreatMap(ChessBoard ref){
		this.calculateThreatRankForward(ref, y+1, 8);
		this.calculateThreatRankBackward(ref, y-1, 8);
		this.calculateThreatFilesLeft(ref, x-1, 8);
		this.calculateThreatFilesRight(ref, x+1, 8);
	}
	
	public void updateState(ChessBoard ref){
		super.updateState(ref);
	}
}
