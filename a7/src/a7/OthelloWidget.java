package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class OthelloWidget extends JPanel implements ActionListener, SpotListener{
	private enum Player {BLACK,WHITE};

	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
	private JSpotBoard this_board;		
	private JLabel _message;		    
	private boolean game_won;
	private boolean tempo;
	private Player next_player;	
	private int count_spot;
	private int score_black;
	private int score_white;
	private ArrayList<Spot> my;
	public OthelloWidget() {

		this_board = new JSpotBoard(8,8);
		my = new ArrayList<Spot>();
		setLayout(new BorderLayout());
		for(int i = 0; i <= 6; i+=2) {
			for(int j = 0; j <= 6; j+=2) {
				this_board.getSpotAt(i,j).setBackground(DEFAULT_BACKGROUND_LIGHT);
				this_board.getSpotAt(i+1,j).setBackground(DEFAULT_BACKGROUND_DARK);
			}
		}
		for(int a = 0; a <=6 ; a+=2) {
			for(int b = 1; b <= 7 ; b+=2) {
				this_board.getSpotAt(a,b).setBackground(DEFAULT_BACKGROUND_DARK);
				this_board.getSpotAt(a+1,b).setBackground(DEFAULT_BACKGROUND_LIGHT);
			}
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
		this_board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
		this_board.getSpotAt(3, 3).toggleSpot();
		this_board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
		this_board.getSpotAt(4, 4).toggleSpot();
		this_board.getSpotAt(3, 4).setSpotColor(Color.BLACK);
		this_board.getSpotAt(3, 4).toggleSpot();
		this_board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
		this_board.getSpotAt(4, 3).toggleSpot();
//		my_test_spots = new ArrayList<Spot>();
//		flip_spots = new ArrayList<Spot>();
		resetGame();
	}



	private void resetGame() {
		for (Spot s : this_board) {
			s.clearSpot();
		}
		game_won = false;
		tempo = false;
		next_player = Player.BLACK;
		count_spot = 0;
		score_black = 0;
		score_white = 0;
		this_board.getSpotAt(3, 3).setSpotColor(Color.WHITE);
		this_board.getSpotAt(3, 3).toggleSpot();
		this_board.getSpotAt(4, 4).setSpotColor(Color.WHITE);
		this_board.getSpotAt(4, 4).toggleSpot();
		this_board.getSpotAt(3, 4).setSpotColor(Color.BLACK);
		this_board.getSpotAt(3, 4).toggleSpot();
		this_board.getSpotAt(4, 3).setSpotColor(Color.BLACK);
		this_board.getSpotAt(4, 3).toggleSpot();
		_message.setText("Welcome to Othello. Black to play.");
		my.removeAll(my);
	}


	@Override
	public void spotClicked(Spot spot) {

		if (game_won) {
			return;
		}

		if(spot.isEmpty()&&tempo) {
			String player_name = null;
			String next_player_name = null;
			Color player_color = null;

			if (next_player == Player.WHITE) {
				player_color = Color.WHITE;
				player_name = "White";
				next_player_name = "Black";
				next_player = Player.BLACK;
				_message.setText("  " + next_player_name + " to play.");
			} 
			else  {
				player_color = Color.BLACK;
				player_name = "Black";
				next_player_name = "White";
				next_player = Player.WHITE;		
				_message.setText("  " + next_player_name + " to play.");
			}
			spot.setSpotColor(player_color);
			spot.setSpot();

			for(Spot sss: my) {
				sss.setSpotColor(player_color);
				sss.setSpot();
			}
			my.removeAll(my);
		}
		if(!spot.isEmpty()) {
		for(Spot _spots: this_board) {
			if(!_spots.isEmpty()) {
				count_spot++;
				if(_spots.getSpotColor().equals(Color.BLACK)) {
					score_black++;
				}
				else {
					score_white++;
				}
			}
		}
		if(count_spot==64) {
			if(score_black>score_white) {
				_message.setText("Game Over. Black Wins. Score: " + score_white + " to "+score_black);
			}
			else {
				_message.setText("Game Over. White Wins. Score: " + score_white + " to "+score_black);
			}
		}
		if(count_spot==64&&(!tempo)) {
			_message.setText("There are no valid moves.");
		}
		}
	}
	@Override
	public void spotEntered(Spot spot1) {
		// only highlight on valid spots
		if (game_won) {
			return;
		}
		tempo = validMove(spot1);
		if(spot1.isEmpty()&&tempo) {
			spot1.highlightSpot(); 
		}
	}

	@Override
	public void spotExited(Spot spot) {
		spot.unhighlightSpot();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();
	}

	public boolean validMove(Spot s){
		my.removeAll(my);
		boolean valid=false;
		Color cc;
		if(next_player.equals(Player.BLACK)) {
			cc=Color.BLACK;
		} else {
			cc=Color.WHITE;
		}

		if(s.isEmpty()) {

			for (int x = -1; x <= 1; x++){
				for (int y = -1; y <= 1; y++){
					int newx=s.getSpotX()+x;
					int newy=s.getSpotY()+y;
					Spot adj;

					if((newx<0||newx>7)||
							(newy<0||newy>7)){
						continue;
					} else {
						adj=this_board.getSpotAt(newx, newy);
					}

					if((adj.isEmpty())||
							(adj.getSpotColor().equals(cc))) {
						continue;
					}



					newx+=x;
					newy+=y;
					int count=1;
					Spot end;
					Spot mine=s;
					Spot ss=s;

					while((newx>=0&&newx<=7)&&
							(newy>=0&&newy<=7)) {

						Spot next=this_board.getSpotAt(newx, newy);

						if(((next.isEmpty())||(next.getSpotX()>7)||(next.getSpotX()<0)||
								(next.getSpotY()<0)||(next.getSpotY()>7))&&(!next.getSpotColor().equals(cc))) {
							if((next.isEmpty())||(!next.getSpotColor().equals(cc))) {
								while(!mine.equals(next)) {

									mine =this_board.getSpotAt(s.getSpotX()+x*count, s.getSpotY()+y*count);
									my.remove(mine);
									count++;
								
								}
								break;
							}


							

						} 

						else if(next.getSpotColor().equals(cc)) {
							my.add(adj);
							valid = true;
							end=this_board.getSpotAt(newx, newy);

							//								
							my.remove(end);
							my.add(this_board.getSpotAt(end.getSpotX()-x, end.getSpotY()-y));
							break;





						}else if(!next.getSpotColor().equals(cc)) {
				
							newx+=x;
							newy+=y;
							if((newx>=0&&newx<=7)&&
									(newy>=0&&newy<=7)&&(!this_board.getSpotAt(newx, newy).isEmpty())&&
									(!this_board.getSpotAt(newx, newy).getSpotColor().equals(cc))){
								my.add(next);
							}
						}

					}

					if(!valid) {
						my.removeAll(my);
					}


				}


			}
		}
		return valid;
	}


}

