package a7;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

/*
 * JSpot
 * 
 * A custom user interface component that implements a spot on a spot board
 * as an extension of JPanel.
 * 
 * A JSpot acts as its own mouse listener and will translate mouse events into
 * notifications to registered SpotListener objects when the spot is entered,
 * exited, or clicked using the appropriate methods (see SpotListener).
 *
 */

public class JSpot extends JPanel implements MouseListener, Spot {
	//we are creating a Jpanel.
	private Color _spot_color;
	private Color _highlight_color;
	//we don't have a filled for background color since Jpanel will provide a background color.
	private boolean _is_empty;
	private boolean _is_highlighted;
	private SpotBoard _board;
	private int _x;
	private int _y;
	//x & y position on that board.
	private List<SpotListener> _spot_listeners;

	public JSpot(Color background, Color spot_color, Color highlight, 
			SpotBoard board, int x, int y) {

		// Background color inherited from JPanel
		setBackground(background);

		_spot_color = spot_color;
		_highlight_color = highlight;
		_is_empty = true;
		_is_highlighted = false;
		_board = board;
		_x = x;
		_y = y;

		_spot_listeners = new ArrayList<SpotListener>();

		addMouseListener(this);
		//turn internal behavior into external: add ourselves to mouselistener
	}

	// Getters for X, Y, and Board properties

	@Override
	public int getSpotX() {
		return _x;
	}

	@Override
	public int getSpotY() {
		return _y;
	}

	@Override
	public SpotBoard getBoard() {
		return _board;
	}

	// Empty / Filled status methods

	@Override
	public boolean isEmpty() {
		return _is_empty;
	}

	@Override
	public void setSpot() {
		_is_empty = false;
		trigger_update();//we are responsible to tell Jpanel the change(when it's repainted).
	}

	@Override
	public void clearSpot() {
		_is_empty = true;
		trigger_update();
	}

	// Highlight status methods

	@Override
	public boolean isHighlighted() {
		return _is_highlighted;
	}

	@Override
	public void highlightSpot() {
		_is_highlighted = true;
		trigger_update();
	}

	@Override
	public void unhighlightSpot() {
		_is_highlighted = false;
		trigger_update();
	}

	// Getters / Setters for color properties Highlight and SpotColor.
	// getBackground and setBackground are inherited from JPanel.

	@Override
	public void setSpotColor(Color c) {		
		if (c == null) throw new IllegalArgumentException("null color");

		_spot_color = c;
		trigger_update();
	}

	@Override
	public Color getSpotColor() {
		return _spot_color;
	}

	@Override
	public void setHighlight(Color c) {
		if (c == null) throw new IllegalArgumentException("null color");

		_highlight_color = c;
		trigger_update();
	}

	@Override
	public Color getHighlight() {
		return _highlight_color;
	}

	// Spot listener (de)registration methods.

	@Override
	public void addSpotListener(SpotListener l) {
		_spot_listeners.add(l);
	}

	@Override
	public void removeSpotListener(SpotListener l) {
		_spot_listeners.remove(l);
	}

	// Overriding paintComponent from JPanel to paint ourselves
	// the way we want.

	@Override
	public void paintComponent(Graphics g) {
		// Super class paintComponent will take care of 
		// painting the background.
		
		//Jpanel usually paint the background color.
		//can not directly call paintcomponent()---go back to start
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create(); 
		//create() is a factory method.
		//----make your own copy of Graphics object
		if (isHighlighted()) {
			g2d.setColor(getHighlight());
			g2d.setStroke(new BasicStroke(4));
			g2d.drawRect(0, 0, getWidth(), getHeight());
			//getWidth() and getHeight() are methods of Jpanel(it knows how tall and wide is a pixel)
		}
		if (!isEmpty()) {
			g2d.setColor(getSpotColor());
			g2d.fillOval(0, 0, this.getWidth()-1, this.getHeight()-1);
		}
		//This method is gonna paint myself. 
	}

	// Mouse listener implementation below for translating
	// mouse events into spot listener events.

	@Override
	public void mouseClicked(MouseEvent e) {
		for (SpotListener s : _spot_listeners) {
			s.spotClicked(this);
			//We are interested in clicks.
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}//We are not interested in press but still need it.

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}//We are not interested in mouse release but still need it.

	@Override
	public void mouseEntered(MouseEvent e) {
		for (SpotListener s : _spot_listeners) {
			s.spotEntered(this);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (SpotListener s : _spot_listeners) {
			s.spotExited(this);
		}
	}
	
	private void trigger_update() {		
		//Go ahead and repaint yourself.
		repaint();

		// Not sure why, but need to schedule a call
		// to repaint for a little bit into the future
		// as well as the one we just did above
		// in order to make sure that we don't end up
		// with visual artifacts due to race conditions.
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}).start();

	}
}
