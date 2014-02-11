/**
 * 
 * @author luzefu
 * Created on December 12th, 2012
 * A Model for queen chess piece
 *
 */

public class QueenChess extends ChessPiece {

	public QueenChess(int posX, int posY, String color) {
		super(posX, posY, color);
		this.type = "Queen";
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
		this.calculateValidRankForward(ref, y+1, 8);
		this.calculateValidRankBackward(ref, y-1, 8);
		this.calculateValidFilesLeft(ref, x-1, 8);
		this.calculateValidFilesRight(ref, x+1, 8);
		this.calculateValidDiagnolPosKUp(ref, x+1, y+1, 8);
		this.calculateValidDiagnolPosKDown(ref, x-1, y-1, 8);
		this.calculateValidDiagnolNegKUp(ref, x-1, y+1, 8);
		this.calculateValidDiagnolNegKDown(ref, x+1, y-1, 8);
		this.validMove[x][y] = false;
	}
	
	public void generateThreatMap(ChessBoard ref){
		this.calculateThreatRankForward(ref, y+1, 8);
		this.calculateThreatRankBackward(ref, y-1, 8);
		this.calculateThreatFilesLeft(ref, x-1, 8);
		this.calculateThreatFilesRight(ref, x+1, 8);
		this.calculateThreatDiagnolPosKUp(ref, x+1, y+1, 8);
		this.calculateThreatDiagnolPosKDown(ref, x-1, y-1, 8);
		this.calculateThreatDiagnolNegKUp(ref, x-1, y+1, 8);
		this.calculateThreatDiagnolNegKDown(ref, x+1, y-1, 8);
	}
	
	public void updateState(ChessBoard ref){
		super.updateState(ref);
	}
}
