package net.voidinteractive.safariTD.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {
	private static int mX = -1;
	private static int mY = -1;
	private static int mB = -1;
	
	public static int getX() {
		return mX;
	}
	
	public static int getY() {
		return mY;
	}
	
	public static int getB() {
		return mB;
	}
	
	public void mouseDragged(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		mB = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mB = -1;
	}
}