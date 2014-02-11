/**
 * 
 * @author luzefu
 * Created on December 3rd, 2012
 * A Model for a Chess Board
 *
 */

public class ChessBoard {
	private ChessPiece[][] data;			//Chess pieces on the board
	private boolean[][] threatOfWhite = new boolean[8][8];
	private boolean[][] threatOfBlack = new boolean[8][8];
	
	//Constructor
	public ChessBoard(){
		data = new ChessPiece[8][8];
		for(int j = 7; j >= 0; j--){
			for(int i = 0; i < 8; i++){
				this.threatOfBlack[i][j] = false;
				this.threatOfWhite[i][j] = false;
			}
		}
	}
	//Getters
	private void clearThreat(boolean[][] threat){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				threat[i][j] = false;
			}
		}
	}
	public void printThreat(boolean[][] threat){
		System.out.println("---------------");
		for(int j = 7; j >= 0; j--){
			for(int i = 0; i < 8; i++){
				if(threat[i][j]) System.out.print("X ");
				else System.out.print("  ");
			}
			System.out.println(j+1);
		}
		System.out.println("1 2 3 4 5 6 7 8 ");
	}
	private void updateThreatFromChessPiece(boolean[][] chess, boolean[][] threat){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(chess[i][j]){
					threat[i][j] = true;
				}
			}
		}
	}
	public boolean[][] getThreatOfWhite(){
		clearThreat(threatOfWhite);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(data[i][j] != null && data[i][j].getColor().equals("white")){
					this.updateThreatFromChessPiece(data[i][j].getThreatMap(this), threatOfWhite);	
				}
			}
		}
		return threatOfWhite;
	}
	public boolean[][] getThreatOfBlack(){
		clearThreat(threatOfBlack);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(data[i][j] != null && data[i][j].getColor().equals("black")){
					this.updateThreatFromChessPiece(data[i][j].getThreatMap(this), threatOfBlack);
				}
			}
		}
		return threatOfBlack;
	}
	
	//Utilities
	public void printChessBoard(){
		System.out.println();
		for (int j = 7; j >= 0; j--){
			for(int i = 0; i < 8; i++){
				if(data[i][j] != null)
					System.out.print(data[i][j].getType() + "  ");
				else
					System.out.print("null" + "  ");
			}
			System.out.println();
		}
	}
	
	public void addChessPiece(ChessPiece piece){
		data[piece.getX()][piece.getY()] = piece;
	}
	
	public ChessPiece getChessPieceAtPosition(int posX, int posY){
		if(posX >= 0 && posX < 8 && posY >= 0 && posY < 8){
			return data[posX][posY];
		}
		return null;
	}
	
	public void emptyWhite(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(data[i][j] != null && data[i][j].getColor().equals("white")){
					data[i][j] = null;
				}
			}
		}
	}
	
	public void emptyBlack(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(data[i][j] != null && data[i][j].getColor().equals("black")){
					data[i][j] = null;
				}
			}
		}
	}
}
