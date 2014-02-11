import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 
 * @author luzefu
 * Created on December 11th, 2012
 * Chess Controller
 *
 */

public class ChessSet {
	String color;
	PawnChess[] pawns;
	RookChess[] rooks;
	BishopChess[] bishops;
	KnightChess[] knights;
	QueenChess[] queens;
	KingChess king;
	ChessSet opponent;
	ChessBoard board;
	//For Promotion
	public static int i = 0;
	
	//Constructor
	ChessSet(String color, ChessBoard bd) {
		this.color = color;
		this.board = bd;
		
		//Set Pawns
		pawns = new PawnChess[8];
		if (color.equals("white")) {
			for (int i = 0; i < 8; i++) {
				pawns[i] = new PawnChess(i, 1, color);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				pawns[i] = new PawnChess(i, 6, color);
			}
		}
		//Set Rooks
		rooks = new RookChess[2];
		if (color.equals("white")){
			rooks[0] = new RookChess(0, 0, color);
			rooks[1] = new RookChess(7, 0, color);
		}else{
			rooks[0] = new RookChess(0, 7, color);
			rooks[1] = new RookChess(7, 7, color);
		}
		//Set Bishops
		bishops = new BishopChess[2];
		if (color.equals("white")){
			bishops[0] = new BishopChess(2, 0, color);
			bishops[1] = new BishopChess(5, 0, color);
		}else{
			bishops[0] = new BishopChess(2, 7, color);
			bishops[1] = new BishopChess(5, 7, color);
		}
		//Set Knight
		knights = new KnightChess[2];
		if (color.equals("white")){
			knights[0] = new KnightChess(1, 0, color);
			knights[1] = new KnightChess(6, 0, color);
		}else{
			knights[0] = new KnightChess(1, 7, color);
			knights[1] = new KnightChess(6, 7, color);
		}
		//Set Queen
		queens = new QueenChess[1];
		if (color.equals("white")){
			queens[0] = new QueenChess(3, 0, color);
		}else{
			queens[0] = new QueenChess(3, 7, color);
		}
		//Set King
		if (color.equals("white")){
			king = new KingChess(4, 0, color);
		}else{
			king = new KingChess(4, 7, color);
		}
	}
	
	//Setter
	public void setOpponent(ChessSet ref){
		this.opponent = ref;
	}
	
	//Chess Piece Administration
	public void removeChessAtPosition(int posX, int posY){
		if(getChessPieceAtPosition(posX,posY) == null)
			return;
		if(getChessPieceAtPosition(posX,posY).getType().equals("Pawn")){
			if(pawns.length-1 == 0){
				pawns = new PawnChess[0];
			}else{
				PawnChess[] tmp = new PawnChess[pawns.length-1];
				int k = 0;
				for(int i = 0; i < pawns.length; i++){
					if(!(pawns[i].getX() == posX && pawns[i].getY() == posY)){
						tmp[k++] = pawns[i];
					}
				}
				pawns = tmp;
			}
		}else if(getChessPieceAtPosition(posX,posY).getType().equals("Rook")){
			if(rooks.length-1 == 0){
				rooks = new RookChess[0];
			}else{
				RookChess[] tmp = new RookChess[rooks.length-1];
				int k = 0;
				for(int i = 0; i < rooks.length; i++){
					if(!(rooks[i].getX() == posX && rooks[i].getY() == posY)){
						tmp[k++] = rooks[i];
					}
				}
				rooks = tmp;
			}
		}else if(getChessPieceAtPosition(posX,posY).getType().equals("Bishop")){
			if(bishops.length-1 == 0){
				bishops = new BishopChess[0];
			}else{
				BishopChess[] tmp = new BishopChess[bishops.length-1];
				int k = 0;
				for(int i = 0; i < bishops.length; i++){
					if(!(bishops[i].getX() == posX && bishops[i].getY() == posY)){
						tmp[k++] = bishops[i];
					}
				}
				bishops = tmp;
			}
		}else if(getChessPieceAtPosition(posX,posY).getType().equals("Knight")){
			if(knights.length-1 == 0){
				knights = new KnightChess[0];
			}else{
				KnightChess[] tmp = new KnightChess[knights.length-1];
				int k = 0;
				for(int i = 0; i < knights.length; i++){
					if(!(knights[i].getX() == posX && knights[i].getY() == posY)){
						tmp[k++] = knights[i];
					}
				}
				knights = tmp;
			}
		}else if(getChessPieceAtPosition(posX,posY).getType().equals("Queen")){
			if(queens.length-1 == 0){
				queens = new QueenChess[0];
			}else{
				QueenChess[] tmp = new QueenChess[queens.length-1];
				int k = 0;
				for(int i = 0; i < queens.length; i++){
					if(!(queens[i].getX() == posX && queens[i].getY() == posY)){
						tmp[k++] = queens[i];
					}
				}
				queens = tmp;
			}
		}
		updateChessBoard();
	}
	
	public void updateChessPieceState(){
		for(int i = 0; i < pawns.length; i++){
			pawns[i].updateState(board);
		}
		for(int i = 0; i < rooks.length; i++){
			rooks[i].updateState(board);
		}
		for(int i = 0; i < bishops.length; i++){
			bishops[i].updateState(board);
		}
		for(int i = 0; i < knights.length; i++){
			knights[i].updateState(board);
		}
		for(int i = 0; i < queens.length; i++){
			queens[i].updateState(board);
		}
		king.updateState(board);
	}
	
	//Utilities
	public ChessPiece getChessPieceAtPosition(int posX, int posY){
		for(int i = 0; i < pawns.length; i++){
			if(pawns[i].getX() == posX && pawns[i].getY() == posY)
				return pawns[i];
		}
		for(int i = 0; i < rooks.length; i++){
			if(rooks[i].getX() == posX && rooks[i].getY() == posY)
				return rooks[i];
		}
		for(int i = 0; i < bishops.length; i++){
			if(bishops[i].getX() == posX && bishops[i].getY() == posY)
				return bishops[i];
		}
		for(int i = 0; i < knights.length; i++){
			if(knights[i].getX() == posX && knights[i].getY() == posY)
				return knights[i];
		}
		for(int i = 0; i < queens.length; i++){
			if(queens[i].getX() == posX && queens[i].getY() == posY)
				return queens[i];
		}
		if(king.getX() == posX && king.getY() == posY)
			return king;
		return null;
	}
	
	public boolean moveChessPieceToPosition(int orgnX, int orgnY, int desX, int desY){
		ChessPiece object = getChessPieceAtPosition(orgnX, orgnY);
		if(object == null || object.getValidMove()[desX][desY] == false){
			return false;
		}
		//En Passant
		if (opponent.getChessPieceAtPosition(desX, desY) == null && Math.abs(desX - orgnX) == 1 && object.getType().equals("Pawn")){
			opponent.removeChessAtPosition(desX, orgnY);
			object.move(desX, desY);
			object.calculateValidMove(board);
			updateChessBoard();
			return true;
		}
		//Castling!!!!!!!!!!!!!!!!!
		//Normal Cases
		opponent.removeChessAtPosition(desX, desY);
		object.move(desX, desY);
		object.calculateValidMove(board);
		//Check King here!!!!!!!!!!!!!!!!(Proceed or restore)
		//Promotion
		if(object.getType().equals("Pawn")){
			PawnChess pawn = (PawnChess)object;
			if(pawn.isPromotable()){
				JFrame window = new JFrame("Promotion");
				JPanel content = new JPanel();
				PromotionPanel promotionpanel = new PromotionPanel();
				promotionpanel.color = color;
				
				content.setLayout(new BorderLayout());
				content.add(promotionpanel, BorderLayout.CENTER);
				content.addMouseListener(new Chooser());

				window.setContentPane(content);
				window.setSize(340, 110);
				window.setLocation(300, 200);
				window.setVisible(true);
				window.setAlwaysOnTop(true);
			}
		}
		
		updateChessBoard();
		return true;
	}
	
	public void updateChessBoard(){
		if (this.color == "white"){
			board.emptyWhite();
		}else{
			board.emptyBlack();
		}
		for(int i = 0; i < pawns.length; i++){
			board.addChessPiece(pawns[i]);
		}
		for(int i = 0; i < rooks.length; i++){
			board.addChessPiece(rooks[i]);
		}
		for(int i = 0; i < bishops.length; i++){
			board.addChessPiece(bishops[i]);
		}
		for(int i = 0; i < queens.length; i++){
			board.addChessPiece(queens[i]);
		}
		for(int i = 0; i < knights.length; i++){
			board.addChessPiece(knights[i]);
		}
		board.addChessPiece(king);
	}
	
	private static class PromotionPanel extends JPanel{
		public String color;
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Frame
			g.setColor(new Color(139,69,19));  //SaddleBrown
			g.fillRect(0, 0, 340, 120);
			
			// Display words
			g.setColor(new Color(245,222,179));  //Wheat
			g.drawString("Please select a chess piece", 20, 20);

			// Display chess pieces
			String name = color + "queen" + ".png";
			BufferedImage img1 = null;
			try {
			    img1 = ImageIO.read(new File(name));
			} catch (IOException e) {
			}
			g.drawImage(img1, 20, 30, 60, 60, null);
			name = color + "rook" + ".png";
			BufferedImage img2 = null;
			try {
			    img2 = ImageIO.read(new File(name));
			} catch (IOException e) {
			}
			g.drawImage(img2, 100, 30, 60, 60, null);
			name = color + "bishop" + ".png";
			BufferedImage img3 = null;
			try {
			    img3 = ImageIO.read(new File(name));
			} catch (IOException e) {
			}
			g.drawImage(img3, 180, 30, 60, 60, null);
			name = color + "knight" + ".png";
			BufferedImage img4 = null;
			try {
			    img4 = ImageIO.read(new File(name));
			} catch (IOException e) {
			}
			g.drawImage(img4, 260, 30, 60, 60, null);
		}
	}
	private static class Chooser implements MouseListener {
		public void mousePressed(MouseEvent evt) {
			int x = evt.getX();
			int y = evt.getY();
			if(x >= 20 && x <= 80 && y >= 30 && y <= 90){
				i = 1;
			}
			if(x >= 100 && x <= 160 && y >= 30 && y <= 90){
				i = 2;
			}
			if(x >= 180 && x <= 240 && y >= 30 && y <= 90){
				i = 3;
			}
			if(x >= 260 && x <= 320 && y >= 30 && y <= 90){
				i = 4;
			}
		}
		public void mouseClicked(MouseEvent evt) { } 
		public void mouseReleased(MouseEvent evt) { } 
		public void mouseEntered(MouseEvent evt) { } 
		public void mouseExited(MouseEvent evt) { } 
	}

}


