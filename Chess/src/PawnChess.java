/**
 * 
 * @author luzefu
 * Created on December 3rd, 2012
 * A Model for pawn chess piece
 *
 */

public class PawnChess extends ChessPiece {
	private boolean isNotMoved;			//Check if it is the first move
	private boolean isPromotable;		//Check if it is promotable
	private boolean EnPassantable;		//Check if opponent pawns can perform En Passant on this pawn...
	
	//Constructor
	public PawnChess(int posX, int posY, String color) {
		super(posX, posY, color);
		this.type = "Pawn";
		isNotMoved = true;
	}
	
	//Getters
	public boolean isNotMoved() {
		return isNotMoved;
	}
	
	public boolean isPromotable() {
		return isPromotable;
	}

	public boolean isEnPassantable() {
		return EnPassantable;
	}
	
	//Movements & Calculations
	public boolean move(int desX, int desY){
		if (desX >= 0 && desX < 8 && desY >= 0 && desY < 8){
			if (validMove[desX][desY]){
				if(isNotMoved = true && Math.abs(desY - y) == 2){
					EnPassantable = true;
				}
				if(desY == 7 || desY == 0){
					isPromotable = true;
				}
				x = desX;
				y = desY;
				isNotMoved = false;
				return true;
			}
		}
		return false;
	}
	
	public void calculateValidMove(ChessBoard ref){
		if(this.color == "white" && y != 7){
			if(ref.getChessPieceAtPosition(x, y+1) == null){
				this.validMove[x][y+1] = true;
				if(isNotMoved && ref.getChessPieceAtPosition(x, y+2) == null){
					this.validMove[x][y+2] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y+1) != null){
				if(!ref.getChessPieceAtPosition(x+1, y+1).getColor().equals(this.color)){
					this.validMove[x+1][y+1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x-1, y+1) != null){
				if(!ref.getChessPieceAtPosition(x-1, y+1).getColor().equals(this.color)){
					this.validMove[x-1][y+1] = true;
				}
			}
			//En Passant
			if(ref.getChessPieceAtPosition(x-1, y) != null){
				if(!ref.getChessPieceAtPosition(x-1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x-1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x-1, y)).isEnPassantable())
							this.validMove[x-1][y+1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y) != null){
				if(!ref.getChessPieceAtPosition(x+1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x+1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x+1, y)).isEnPassantable())
							this.validMove[x+1][y+1] = true;
				}
			}
		}
		if(this.color == "black" && y != 0){
			if(ref.getChessPieceAtPosition(x, y-1) == null){
				this.validMove[x][y-1] = true;
				if(isNotMoved && ref.getChessPieceAtPosition(x, y-2) == null){
					this.validMove[x][y-2] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y-1) != null){
				if(!ref.getChessPieceAtPosition(x+1, y-1).getColor().equals(this.color)){
					this.validMove[x+1][y-1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x-1, y-1) != null){
				if(!ref.getChessPieceAtPosition(x-1, y-1).getColor().equals(this.color)){
					this.validMove[x-1][y-1] = true;
				}
			}
			//En Passant
			if(ref.getChessPieceAtPosition(x-1, y) != null){
				if(!ref.getChessPieceAtPosition(x-1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x-1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x-1, y)).isEnPassantable())
							this.validMove[x-1][y-1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y) != null){
				if(!ref.getChessPieceAtPosition(x+1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x+1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x+1, y)).isEnPassantable())
							this.validMove[x+1][y-1] = true;
				}
			}
		}
		this.validMove[x][y] = false;
	}
	
	public void generateThreatMap(ChessBoard ref){
		if(this.color.equals("white")){
			if(x+1>=0 && x+1<8 && y+1>=0 && y+1<8)
				this.threatMap[x+1][y+1] = true;
			if(x-1>=0 && x+1<8 && y+1>=0 && y+1<8)
				this.threatMap[x-1][y+1] = true;
			//En Passant
			if(ref.getChessPieceAtPosition(x-1, y) != null){
				if(!ref.getChessPieceAtPosition(x-1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x-1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x-1, y)).isEnPassantable())
							this.threatMap[x-1][y+1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y) != null){
				if(!ref.getChessPieceAtPosition(x+1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x+1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x+1, y)).isEnPassantable())
							this.threatMap[x+1][y+1] = true;
				}
			}
		}else{
			if(x+1>=0 && x+1<8 && y-1>=0 && y-1<8)
				this.threatMap[x+1][y-1] = true;
			if(x-1>=0 && x-1<8 && y-1>=0 && y-1<8)
				this.threatMap[x-1][y-1] = true;
			//En Passant
			if(ref.getChessPieceAtPosition(x-1, y) != null){
				if(!ref.getChessPieceAtPosition(x-1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x-1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x-1, y)).isEnPassantable())
							this.threatMap[x-1][y-1] = true;
				}
			}
			if(ref.getChessPieceAtPosition(x+1, y) != null){
				if(!ref.getChessPieceAtPosition(x+1, y).getColor().equals(this.color)){
					if(ref.getChessPieceAtPosition(x+1, y).getType().equals("Pawn"))
						if(((PawnChess)ref.getChessPieceAtPosition(x+1, y)).isEnPassantable())
							this.threatMap[x+1][y-1] = true;
				}
			}
		}
	}
	
	public void updateState(ChessBoard ref){
		this.EnPassantable = false;
		super.updateState(ref);
	}
}
