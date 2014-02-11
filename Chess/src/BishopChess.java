/**
 * 
 * @author luzefu
 * Created on December 12th, 2012
 * A Model for bishop chess piece
 *
 */

public class BishopChess extends ChessPiece {
	//Constructor
		public BishopChess(int posX, int posY, String color) {
			super(posX, posY, color);
			this.type = "Bishop";
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
			this.calculateValidDiagnolPosKUp(ref, x+1, y+1, 8);
			this.calculateValidDiagnolPosKDown(ref, x-1, y-1, 8);
			this.calculateValidDiagnolNegKUp(ref, x-1, y+1, 8);
			this.calculateValidDiagnolNegKDown(ref, x+1, y-1, 8);
			this.validMove[x][y] = false;
		}
		
		public void generateThreatMap(ChessBoard ref){
			this.calculateThreatDiagnolPosKUp(ref, x+1, y+1, 8);
			this.calculateThreatDiagnolPosKDown(ref, x-1, y-1, 8);
			this.calculateThreatDiagnolNegKUp(ref, x-1, y+1, 8);
			this.calculateThreatDiagnolNegKDown(ref, x+1, y-1, 8);
		}
		
		public void updateState(ChessBoard ref){
			super.updateState(ref);
		}
}
