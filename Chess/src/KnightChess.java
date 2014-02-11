/**
 * 
 * @author luzefu
 * Created on December 12th, 2012
 * A Model for knight chess piece
 *
 */
public class KnightChess extends ChessPiece {

	public KnightChess(int posX, int posY, String color) {
		super(posX, posY, color);
		this.type = "Knight";
	}
	
	//Movements & Calculations
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
	
	public void calculateValidMove(ChessBoard ref){
		this.calculateValidAtPosition(ref, x+1, y+2);
		this.calculateValidAtPosition(ref, x+1, y-2);
		this.calculateValidAtPosition(ref, x-1, y+2);
		this.calculateValidAtPosition(ref, x-1, y-2);
		this.calculateValidAtPosition(ref, x+2, y+1);
		this.calculateValidAtPosition(ref, x+2, y-1);
		this.calculateValidAtPosition(ref, x-2, y+1);
		this.calculateValidAtPosition(ref, x-2, y-1);
		this.validMove[x][y] = false;
	}
	
	public void generateThreatMap(ChessBoard ref){
		this.calculateThreatAtPosition(ref, x+1, y+2);
		this.calculateThreatAtPosition(ref, x+1, y-2);
		this.calculateThreatAtPosition(ref, x-1, y+2);
		this.calculateThreatAtPosition(ref, x-1, y-2);
		this.calculateThreatAtPosition(ref, x+2, y+1);
		this.calculateThreatAtPosition(ref, x+2, y-1);
		this.calculateThreatAtPosition(ref, x-2, y+1);
		this.calculateThreatAtPosition(ref, x-2, y-1);
	}
	
	public void updateState(ChessBoard ref){
		super.updateState(ref);
	}}
