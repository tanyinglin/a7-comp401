package a7;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TicTacToeGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Welcome to Tic Tac Toe!");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Create panel for content. Uses BorderLayout. */
		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);

		TicTacToeWidget ttt = new TicTacToeWidget();
		top_panel.add(ttt, BorderLayout.CENTER);
		
		main_frame.pack();
		main_frame.setVisible(true);	
	}

}
