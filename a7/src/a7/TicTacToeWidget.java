package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToeWidget extends JPanel implements ActionListener, SpotListener {

	private enum Player {
		BLACK, WHITE
	};

	private JSpotBoard this_board;
	private JLabel _message;
	private boolean game_won;
	private Player _next_player;
	private int count;

	public TicTacToeWidget() {

		this_board = new JSpotBoard(3, 3);
		setLayout(new BorderLayout());
		for (Spot s : this_board) {
			s.setBackground(getBackground());
		}
		_message = new JLabel();

		add(this_board, BorderLayout.CENTER);

		JPanel reset_message_panel = new JPanel();
		reset_message_panel.setLayout(new BorderLayout());

		JButton reset_button = new JButton("Restart");
		reset_button.addActionListener(this);
		reset_message_panel.add(reset_button, BorderLayout.EAST);
		reset_message_panel.add(_message, BorderLayout.CENTER);

		add(reset_message_panel, BorderLayout.SOUTH);

		this_board.addSpotListener(this);

		resetGame();
		count = 0;
	}

	private void resetGame() {
		for (Spot s : this_board) {
			s.clearSpot();
		}
		count = 0;
		game_won = false;
		_next_player = Player.WHITE;

		_message.setText("Welcome to Tic Tac Toe. White to play.");
	}

	@Override
	public void spotClicked(Spot spot) {
		if (game_won)
			return;

		if (spot.isEmpty()) {
			count++;
			String player_name = null;
			String next_player_name = null;
			Color player_color = null;

			if (_next_player == Player.WHITE) {
				player_name = "White";
				player_color = Color.WHITE;
				next_player_name = "Black";
				_next_player = Player.BLACK;
				_message.setText(next_player_name + " to play.");
			}

			else if (_next_player == Player.BLACK) {
				player_name = "Black";
				player_color = Color.BLACK;
				next_player_name = "White";
				_next_player = Player.WHITE;
				_message.setText(next_player_name + " to play.");
			}

			spot.setSpotColor(player_color);
			spot.toggleSpot();

			if (this_board.getSpotAt(0,0).getSpotColor().equals(this_board.getSpotAt(0,1).getSpotColor())
					&& (!this_board.getSpotAt(0,0).isEmpty())
					&& this_board.getSpotAt(0,1).getSpotColor().equals(this_board.getSpotAt(0,2).getSpotColor())
					&& (!this_board.getSpotAt(0,1).isEmpty()) && (!this_board.getSpotAt(0,2).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(0,0).getSpotColor().equals(this_board.getSpotAt(1,0).getSpotColor())
					&& this_board.getSpotAt(1,0).getSpotColor().equals(this_board.getSpotAt(2,0).getSpotColor())
					&& (!this_board.getSpotAt(1,0).isEmpty()) && (!this_board.getSpotAt(1,0).isEmpty())
					&& (!this_board.getSpotAt(2,0).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(1,0).getSpotColor().equals(this_board.getSpotAt(1,1).getSpotColor())
					&& this_board.getSpotAt(1,1).getSpotColor().equals(this_board.getSpotAt(1,2).getSpotColor())
					&& (!this_board.getSpotAt(1,0).isEmpty()) && (!this_board.getSpotAt(1,1).isEmpty())
					&& (!this_board.getSpotAt(1,2).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(2,0).getSpotColor().equals(this_board.getSpotAt(2,1).getSpotColor())
					&& this_board.getSpotAt(2,1).getSpotColor().equals(this_board.getSpotAt(2,2).getSpotColor())
					&& (!this_board.getSpotAt(2,0).isEmpty()) && (!this_board.getSpotAt(2,1).isEmpty())
					&& (!this_board.getSpotAt(2,2).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(0,1).getSpotColor().equals(this_board.getSpotAt(1,1).getSpotColor())
					&& this_board.getSpotAt(1,1).getSpotColor().equals(this_board.getSpotAt(2,1).getSpotColor())
					&& (!this_board.getSpotAt(0,1).isEmpty()) && (!this_board.getSpotAt(1,1).isEmpty())
					&& (!this_board.getSpotAt(2,1).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(0,2).getSpotColor().equals(this_board.getSpotAt(1,2).getSpotColor())
					&& this_board.getSpotAt(1,2).getSpotColor().equals(this_board.getSpotAt(2,2).getSpotColor())
					&& (!this_board.getSpotAt(0,2).isEmpty()) && (!this_board.getSpotAt(1,2).isEmpty())
					&& (!this_board.getSpotAt(2,2).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			else if (this_board.getSpotAt(0,0).getSpotColor().equals(this_board.getSpotAt(1,1).getSpotColor())
					&& this_board.getSpotAt(1,1).getSpotColor().equals(this_board.getSpotAt(2,2).getSpotColor())
					&& (!this_board.getSpotAt(0,0).isEmpty()) && (!this_board.getSpotAt(1,1).isEmpty())
					&& (!this_board.getSpotAt(2,2).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			} else if (this_board.getSpotAt(0,2).getSpotColor().equals(this_board.getSpotAt(1,1).getSpotColor())
					&& this_board.getSpotAt(1,1).getSpotColor().equals(this_board.getSpotAt(2,0).getSpotColor())
					&& (!this_board.getSpotAt(0,2).isEmpty()) && (!this_board.getSpotAt(1,1).isEmpty())
					&& (!this_board.getSpotAt(2,0).isEmpty())) {
				game_won = true;
				spot.toggleHighlight();
			}

			if (game_won) {
				_message.setText(player_name + " Wins!");
				for(Spot s : this_board) {
					
					if(!s.isEmpty()) {
						spot.toggleHighlight();
					}
				}
			}
			if ((count == 9) && (!game_won)) {
				_message.setText("Draw game.");
				spot.toggleHighlight();
				for(Spot s : this_board) {
					
					if(!s.isEmpty()) {
						spot.toggleHighlight();
					}
				}
			}
			
		}
		

	}

	@Override
	public void spotEntered(Spot spot) {
		if (game_won) {
			return;
		}
		if(spot.isEmpty())
		spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();

	}

}
