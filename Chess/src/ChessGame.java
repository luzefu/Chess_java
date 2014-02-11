/**
 * 
 * @author luzefu 
 * Created on December 4th, 2012 
 * The Chess Game
 * 
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ChessGame {
	private static ChessBoard board = new ChessBoard();
	private static ChessSet set1 = new ChessSet("white", board);
	private static ChessSet set2 = new ChessSet("black", board);
	private static ChessSet currentSet;
	
	public static void main(String[] args) {
		//Chess Set
		set1.setOpponent(set2);
		set2.setOpponent(set1);
		set1.updateChessBoard();
		set2.updateChessBoard();
		changeSet();
		
		//GUI Components
		JFrame window = new JFrame("Chess");
		JPanel content = new JPanel();
		ChessBoardBG boardBG = new ChessBoardBG();
		MoveSelector listener1= new MoveSelector();
		
		//GUI Display
		boardBG.addMouseListener(listener1);
		content.setLayout(new BorderLayout());
		content.add(boardBG, BorderLayout.CENTER);
		
		window.setContentPane(content);
		window.setSize(900, 560);
		window.setLocation(200, 100);
		window.setVisible(true);
		window.setResizable(false);
	}
	
	// Set Changer
	private static void changeSet(){
		if(currentSet == null){
			currentSet = set1;
		}else if(currentSet == set1){
			currentSet = set2;
		}else if(currentSet == set2){
			currentSet = set1;
		}
		currentSet.updateChessPieceState();
	}
	
	// MouseListener for move selection
	private static class MoveSelector implements MouseListener {
		private int desX = -1;
		private int desY = -1;
		private int orgnX = -1;
		private int orgnY = -1;
		
		private void refreshOrgn(){
			orgnX = -1;
			orgnY = -1;
		}
		
		private void refreshDes(){
			desX = -1;
			desY = -1;
		}
		
		private boolean orgnIsInitialized(){
			if (orgnX != -1 && orgnY != -1)
				return true;
			return false;
		}
		
		private boolean desIsInitialized(){
			if (desX != -1 && desY != -1)
				return true;
			return false;
		}
		
		public void mousePressed(MouseEvent evt) {
			Component source = (Component)evt.getSource();
			Graphics g = source.getGraphics();
			
			if (!orgnIsInitialized()){
				orgnX = (evt.getX() - 30)/60;
				orgnY = 7 - (evt.getY() - 30)/60;
				if(currentSet.getChessPieceAtPosition(orgnX, orgnY)!=null){
					g.setColor(Color.GREEN);
					g.drawRect(orgnX*60 + 31, (7- orgnY)*60 + 31, 58, 58);
				}else{
					refreshOrgn();
				}
			}else{
				desX = (evt.getX() - 30)/60;
				desY = 7 - (evt.getY() - 30)/60;
				if(currentSet.getChessPieceAtPosition(desX, desY)!=null){
					source.repaint(orgnX*60 + 30, (7- orgnY)*60 + 30, 60, 60);
					orgnX = desX;
					orgnY = desY;
					refreshDes();
					g.setColor(Color.GREEN);
					g.drawRect(orgnX*60 + 31, (7- orgnY)*60 + 31, 58, 58);
				}else if(desX == orgnX && desY == orgnY){
					source.repaint(orgnX*60 + 30, (7- orgnY)*60 + 30, 60, 60);
					refreshDes();
					refreshOrgn();
				}else{
					boolean valid = currentSet.moveChessPieceToPosition(orgnX, orgnY, desX, desY);
					if(valid){
						//board.printChessBoard();		//for testing
						source.repaint();
						changeSet();
						refreshOrgn();
						refreshDes();
					}else{
						g.setColor(Color.GREEN);
						g.drawRect(orgnX*60 + 31, (7- orgnY)*60 + 31, 58, 58);
						refreshDes();
					}
				}
			}
			g.dispose();
		}
		public void mouseClicked(MouseEvent evt) { } 
		public void mouseReleased(MouseEvent evt) { } 
		public void mouseEntered(MouseEvent evt) { } 
		public void mouseExited(MouseEvent evt) { } 
	}
	
	// Chess Board Panel
	private static class ChessBoardBG extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// Frame
			g.setColor(new Color(139,69,19));  //SaddleBrown
			g.fillRect(0, 0, 540, 540);
			
			// File and Rank numbers
			g.setColor(new Color(245,245,220)); //Beige
			for(int i = 1; i <= 8; i++){
				g.drawString(new Integer(9-i).toString(), 10, i*60+5);
			}
			for(int i = 1; i <= 8; i++){
				g.drawString(new Integer(9-i).toString(), 520, i*60+5);
			}
			for(int i = 0; i < 8; i++){
				String set = "ABCDEFGH";
				g.drawString(set.substring(i, i+1), 55 + i*60, 530);
			}
			for(int i = 0; i < 8; i++){
				String set = "ABCDEFGH";
				g.drawString(set.substring(i, i+1), 55 + i*60, 20);
			}
			
			// Cells
			g.setColor(new Color(245,222,179));  //Wheat
			g.fillRect(30, 30, 480, 480);
			g.setColor(new Color(205, 133, 63)); //Peru
			for (int i = 30; i < 480;i += 120){
				for (int j = 90; j < 480; j+= 120){
					g.fillRect(i, j, 60, 60);
				}
			}
			for (int i = 90; i < 480;i += 120){
				for (int j = 30; j < 480; j+= 120){
					g.fillRect(i, j, 60, 60);
				}
			}
			
			// Display Chess Pieces
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					if (board.getChessPieceAtPosition(i, j) != null){
						int x = 30 + i * 60 + 8;
						int y = 30 + 60 * (7 -j) + 8;
						String name = board.getChessPieceAtPosition(i, j).getColor() + 
								board.getChessPieceAtPosition(i, j).getType().toLowerCase() + ".png";
						BufferedImage img = null;
						try {
						    img = ImageIO.read(new File(name));
						} catch (IOException e) {
						}
						g.drawImage(img, x, y, 45, 45, null);
					}
				}
			}
			
			
		}
		
	}
	
}