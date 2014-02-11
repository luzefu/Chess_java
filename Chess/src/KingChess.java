/**
 * 
 * @author luzefu
 * Created on December 12th, 2012
 * A Model for rook chess piece
 *
 */

public class KingChess extends ChessPiece {
	private boolean isNotMoved;
	private boolean isBeingChecked;
	private boolean cannotMove;
	
	public KingChess(int posX, int posY, String color) {
		super(posX, posY, color);
		this.type = "King";
		this.isNotMoved = true;
		this.isBeingChecked = false;
		this.cannotMove = true;
	}
	
	//Movements & Calculations
	public boolean move(int desX, int desY){
		if (desX >= 0 && desX < 8 && desY >= 0 && desY < 8){
			if (validMove[desX][desY]){
				x = desX;
				y = desY;
				isNotMoved = true;
				return true;
			}
		}
		return false;
	}
	
	public void calculateValidMove(ChessBoard ref){
		this.calculateValidRankForward(ref, y+1, 1);
		this.calculateValidRankBackward(ref, y-1, 1);
		this.calculateValidFilesLeft(ref, x-1, 1);
		this.calculateValidFilesRight(ref, x+1, 1);
		this.calculateValidDiagnolPosKUp(ref, x+1, y+1, 1);
		this.calculateValidDiagnolPosKDown(ref, x-1, y-1, 1);
		this.calculateValidDiagnolNegKUp(ref, x-1, y+1, 1);
		this.calculateValidDiagnolNegKDown(ref, x+1, y-1, 1);
		this.validMove[x][y] = false;
		//If the tile is threatened by the opponent, it is invalid
		if(getColor().equals("white")){
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(ref.getThreatOfBlack()[i][j])
						this.validMove[i][j] = false;
				}
			}
			ref.printThreat(ref.getThreatOfWhite());
		}else{
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					if(ref.getThreatOfWhite()[i][j])
						this.validMove[i][j] = false;
				}
			}
			ref.printThreat(ref.getThreatOfBlack());
		}
		//Castling
		
	}
	
	public void generateThreatMap(ChessBoard ref){
		this.calculateThreatRankForward(ref, y+1, 1);
		this.calculateThreatRankBackward(ref, y-1, 1);
		this.calculateThreatFilesLeft(ref, x-1, 1);
		this.calculateThreatFilesRight(ref, x+1, 1);
		this.calculateThreatDiagnolPosKUp(ref, x+1, y+1, 1);
		this.calculateThreatDiagnolPosKDown(ref, x-1, y-1, 1);
		this.calculateThreatDiagnolNegKUp(ref, x-1, y+1, 1);
		this.calculateThreatDiagnolNegKDown(ref, x+1, y-1, 1);
	}
	
	public void updateState(ChessBoard ref){
		super.updateState(ref);
	}
	
}
