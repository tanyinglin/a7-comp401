package a7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectFourWidget extends JPanel implements ActionListener, SpotListener {

	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
	private enum Player {
		RED, BLACK
	};
	private JSpotBoard this_board;
	private JLabel _message;
	private boolean game_won;
	private boolean game_draw;
	private boolean do_not_switch;
	private Player _next_player;
	//private ArrayList<Spot> my_spots;
	private Spot[] my_spots;

	public ConnectFourWidget() {
		this_board = new JSpotBoard(7,6);
		setLayout(new BorderLayout());
		for(Spot s: this_board) {
			if(s.getSpotX()%2==0) {
				s.setBackground(DEFAULT_BACKGROUND_LIGHT);
			}
			else
				s.setBackground(DEFAULT_BACKGROUND_DARK);
		}
		//my_spots = new ArrayList<Spot>();
		my_spots = new Spot[4];
		_message = new JLabel();
		game_won = false;
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
	}
	public JSpotBoard getBoard() {
		return this_board;
	}
	private void resetGame() {
		for (Spot s : this_board) {
			s.clearSpot();
			s.unhighlightSpot();
		}
		my_spots = new Spot[4];
		game_won = false;
		_next_player = Player.RED;
		do_not_switch = false;
		_message.setText("Welcome to Connect Four. Red to play.");
	}
	@Override
	public void spotClicked(Spot spot) {
		if(game_won) {
			return;
		}
		//		if(do_not_switch) {
		//			return;
		//		}
		String player_name = null;
		String next_player_name = null;
		Color player_color = null;
		if (_next_player == Player.RED&&!do_not_switch) {
			player_name = "Red";
			player_color = Color.RED;
			next_player_name = "Black";
			_next_player = Player.BLACK;
			_message.setText(next_player_name + " to play.");
		}

		else if (_next_player == Player.BLACK&&!do_not_switch) {
			player_name = "Black";
			player_color = Color.BLACK;
			next_player_name = "Red";
			_next_player = Player.RED;
			_message.setText(next_player_name + " to play.");
		}
		for(int j = 5 ; j >= 0; j--) {
			if(this_board.getSpotAt(spot.getSpotX(), j).isEmpty()) {
				this_board.getSpotAt(spot.getSpotX(), j).setSpotColor(player_color);
				this_board.getSpotAt(spot.getSpotX(), j).toggleSpot();
				if(j==0) {
					do_not_switch = true;
				}

				break;
			}}


		for(Spot s:this_board) {
			//horizontal check
			if(s.getSpotX()>=2&&(!s.isEmpty())) {
				if(s.getSpotColor().
						equals(this_board.getSpotAt(s.getSpotX()-2, s.getSpotY()).getSpotColor())
						&&!this_board.getSpotAt(s.getSpotX()-2, s.getSpotY()).isEmpty()) {
					if(s.getSpotColor().
							equals(this_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).getSpotColor())
							&&!this_board.getSpotAt(s.getSpotX()-1, s.getSpotY()).isEmpty()) {
						if(s.getSpotX()<6) {
							if(s.getSpotColor().
									equals(this_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).
											getSpotColor())
									&&!this_board.getSpotAt(s.getSpotX()+1, s.getSpotY()).isEmpty()) {
								game_won = true;
								my_spots[0] = s;
								my_spots[1] = this_board.getSpotAt(s.getSpotX()-2, s.getSpotY());
								my_spots[2] = this_board.getSpotAt(s.getSpotX()-1, s.getSpotY());
								my_spots[3] = this_board.getSpotAt(s.getSpotX()+1, s.getSpotY());
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
									//my_spots.remove(ss);
									//System.out.println(ss.getSpotColor());
								}

								break;}
						}
						else {
							if((s.getSpotX()>=3&&!s.isEmpty())&&s.getSpotColor().
									equals(this_board.getSpotAt(s.getSpotX()-3, s.getSpotY()).getSpotColor())
									&&!this_board.getSpotAt(s.getSpotX()-3, s.getSpotY()).isEmpty()) {
								game_won = true;
								my_spots[0] = s;
								my_spots[1] = this_board.getSpotAt(s.getSpotX()-2, s.getSpotY());
								my_spots[2] = this_board.getSpotAt(s.getSpotX()-1, s.getSpotY());
								my_spots[3] = this_board.getSpotAt(s.getSpotX()-3, s.getSpotY());
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
									//my_spots.remove(ss);
									//System.out.println(ss.getSpotColor());
								}
								break;}
						}

					}
				}
			}}
		//vertical check
		for(Spot s1:this_board) {
			if(s1.getSpotY()>=2&&(!s1.isEmpty())) {
				if(s1.getSpotColor().
						equals(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-2).getSpotColor())
						&&!this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-2).isEmpty()) {
					if(s1.getSpotColor().
							equals(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-1).getSpotColor())
							&&!this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-1).isEmpty()) {
						if(s1.getSpotY()< 5) {
							if(s1.getSpotColor().
									equals(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()+1).
											getSpotColor())
									&&!this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()+1).isEmpty()) {
								game_won = true;
								//										my_spots.add(s1);
								//										my_spots.add(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-2));
								//										my_spots.add(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-1));
								//										my_spots.add(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()+1));
								my_spots[0] = s1;
								my_spots[1] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-2);
								my_spots[2] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-1);
								my_spots[3] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()+1);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
									//System.out.println(ss.getSpotColor());
								}
								break; }
						}
						else {
							if(s1.getSpotY()>=3&&s1.getSpotColor().
									equals(this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-3).getSpotColor())
									&&!this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-3).isEmpty()) {
								game_won = true;
								my_spots[0] = s1;
								my_spots[1] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-2);
								my_spots[2] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-1);
								my_spots[3] = this_board.getSpotAt(s1.getSpotX(), s1.getSpotY()-3);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
									//System.out.println(ss.getSpotColor());
								}
								break; }
						}
					}
				}
			}
		}
		// diagonal with increasing slope
		for(Spot s2: this_board) {
			if((!s2.isEmpty())&&s2.getSpotX()<=4&&s2.getSpotY()>=2) {
				if(s2.getSpotColor().
						equals(this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2).getSpotColor())
						&&!this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2).isEmpty()) {
					if(s2.getSpotColor().
							equals(this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1).getSpotColor())
							&&!this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1).isEmpty()) {
						if(s2.getSpotX()>0 && s2.getSpotY()<5) {
							if(s2.getSpotColor().equals
									(this_board.getSpotAt(s2.getSpotX()-1, s2.getSpotY()+1).getSpotColor())
									&&!this_board.getSpotAt(s2.getSpotX()-1, s2.getSpotY()+1).isEmpty()) {
								game_won = true;
								//											my_spots.add(s2);
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2));
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1));
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()-1, s2.getSpotY()+1));
								my_spots[0] = s2;
								my_spots[1] = this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2);
								my_spots[2] = this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1);
								my_spots[3] = this_board.getSpotAt(s2.getSpotX()-1, s2.getSpotY()+1);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();

									//System.out.println(ss.getSpotColor());
								}
								break;
							}
						}
						else {
							if(s2.getSpotX()<=3&&s2.getSpotY()>2&&
									s2.getSpotColor().
									equals(this_board.getSpotAt(s2.getSpotX()+3, s2.getSpotY()-3).getSpotColor())
									&&!this_board.getSpotAt(s2.getSpotX()+3, s2.getSpotY()-3).isEmpty()) {
								game_won = true;
								//											my_spots.add(s2);
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2));
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1));
								//											my_spots.add(this_board.getSpotAt(s2.getSpotX()-3, s2.getSpotY()+3));
								my_spots[0] = s2;
								my_spots[1] = this_board.getSpotAt(s2.getSpotX()+2, s2.getSpotY()-2);
								my_spots[2] = this_board.getSpotAt(s2.getSpotX()+1, s2.getSpotY()-1);
								my_spots[3] = this_board.getSpotAt(s2.getSpotX()+3, s2.getSpotY()-3);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
									//System.out.println(ss.getSpotColor());
								}
								break;
							}
						}
					}
				}
			}
		}
		//diagonal with decreasing slope
		for(Spot s3: this_board) {
			if(!s3.isEmpty() && s3.getSpotX()<=4 && s3.getSpotY()<=3) {
				if(s3.getSpotColor().
						equals(this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2).getSpotColor())
						&&!this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2).isEmpty()) {
					if(s3.getSpotColor().
							equals(this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1).getSpotColor())
							&&!this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1).isEmpty()) {
						if(s3.getSpotX()>0 && s3.getSpotY()>0) {
							if(s3.getSpotColor().
									equals(this_board.getSpotAt(s3.getSpotX()-1, s3.getSpotY()-1).getSpotColor())
									&&!this_board.getSpotAt(s3.getSpotX()-1, s3.getSpotY()-1).isEmpty()) {
								game_won = true;
								//											my_spots.add(s3);
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2));
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1));
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()-1, s3.getSpotY()-1));
								my_spots[0] = s3;
								my_spots[1] = this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2);
								my_spots[2] = this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1);
								my_spots[3] = this_board.getSpotAt(s3.getSpotX()-1, s3.getSpotY()-1);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
								}
								break;
							}
						}
						else {
							if(s3.getSpotX()<=3&&s3.getSpotY()<=2&&
									s3.getSpotColor().
									equals(this_board.getSpotAt(s3.getSpotX()+3, s3.getSpotY()+3).getSpotColor())
									&&!this_board.getSpotAt(s3.getSpotX()+3, s3.getSpotY()+3).isEmpty()) {
								game_won = true;
								//											my_spots.add(s3);
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2));
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1));
								//											my_spots.add(this_board.getSpotAt(s3.getSpotX()+3, s3.getSpotY()+3));
								my_spots[0] = s3;
								my_spots[1] = this_board.getSpotAt(s3.getSpotX()+2, s3.getSpotY()+2);
								my_spots[2] = this_board.getSpotAt(s3.getSpotX()+1, s3.getSpotY()+1);
								my_spots[3] = this_board.getSpotAt(s3.getSpotX()+3, s3.getSpotY()+3);
								for(Spot ss: this_board) {
									ss.unhighlightSpot();
								}

								for(Spot ss: my_spots) {
									ss.highlightSpot();
								}
								break;
							}
						}
					}
				}
			}
		}

		if (game_won) {
			_message.setText(player_name + " Wins!");
		}

		int count = 0; 
		for(Spot s: this_board) {
			if(!s.isEmpty()) {
				count ++;
			}
		}
		if(count==42 && (!game_won)) {
			game_draw = true;
			_message.setText("Draw Game.");
		}
	}

	@Override
	public void spotEntered(Spot spot) {
		if(game_won) {
			return;
		}
		else if(game_draw) {
			return;
		}
		if (!this_board.getSpotAt(spot.getSpotX(), 0).isEmpty()) {
			do_not_switch = true;
		} 
		else {
			do_not_switch = false;
		}
		for(int i = 0; i < 6; i++) {
			if(this_board.getSpotAt(spot.getSpotX(), i).isEmpty()) {
				this_board.getSpotAt(spot.getSpotX(), i).highlightSpot();
			} 
		}
	}

	@Override
	public void spotExited(Spot spot) {
		if(!game_won) {
			for(int i = 0; i < 6; i++) {
				this_board.getSpotAt(spot.getSpotX(), i).unhighlightSpot();
			}

		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		resetGame();
	}

}
