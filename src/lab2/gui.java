package lab2;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Random;


public class gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static String[] SHORT_NAMES = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };

	public static String[] FULL_NAMES = 
		{ "alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	
	private JButton startButton = new JButton("start");
	private JButton stopButton = new JButton("stop"); 
	private JTextField userInput = new JTextField();
	private JLabel countdown = new JLabel();
	private JLabel result = new JLabel();
	private JTextArea prompt = new JTextArea();
	
	int correctResponses = 0;
	int incorrectResponses = 0; 
	Random random = new Random();
	private int n;
	private String ans;
	
	
	public gui() {
			
			createFrame();
			
			startButton.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e) {
					startButton.setEnabled(false);
					stopButton.setEnabled(true);
					start();
				}
			});
			
			stopButton.addActionListener(new ActionListener()
			{
	
				@Override
				public void actionPerformed(ActionEvent e) {
					startButton.setEnabled(true);
					stopButton.setEnabled(false);	
					stop();
				}
			});
			
		}
	
	
	public void createFrame() { 
		setTitle("Amino Acid Quiz");
		setLocationRelativeTo(null);
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		
		prompt.setWrapStyleWord(true); 
		prompt.setLineWrap(true);
		prompt.setText(introMessage());
		prompt.setMargin(new Insets(10,10,10,10));
		panel.add(prompt, BorderLayout.CENTER);
		add(panel);
		
		// bottom panel 
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(startButton);
		bottomPanel.add(stopButton);
		bottomPanel.setLayout(new GridLayout(0,2));
		panel.add(bottomPanel,BorderLayout.SOUTH);
	
		
		// top panel 
		JPanel topPanel = new JPanel();
		topPanel.add(countdown);
		topPanel.add(result);
		topPanel.setLayout(new GridLayout(0,2));
		panel.add(topPanel,BorderLayout.NORTH);
		
		
//		// center panel 
//		JPanel centerPanel = new JPanel();
//		centerPanel.add(prompt);
//		centerPanel.add(userInput);
//		userInput.setVisible(false);
//		prompt.setVisible(false);
//		centerPanel.setLayout(new GridLayout(0,2));
//		panel.add(centerPanel,BorderLayout.CENTER);
		

		stopButton.setEnabled(false);
		add(userInput, BorderLayout.AFTER_LAST_LINE);
		setVisible(true);
		
	}
	
	public String introMessage() {
		
		StringBuilder message = new StringBuilder();
		message.append("\nAmino Acid Quiz\n\n");
		message.append("Rules of the Game:\n\n- Type in the one letter code for the given amino acid and press the enter key to submit your answer .\n");
		message.append("You will have 30 seconds to answer as many as you can.");
		message.append("\n\nPress 'Start' to begin - Good Luck!");
		String introMessage = message.toString();
		
		return introMessage;
	}
	
	
	// timer runnable 
	public class timerRunnable implements Runnable
	{
		public void run()
		{
			for (int i=30; i>=0; i--) {
				countdown.setText("time remaining: " + i);
				try {
					Thread.sleep(1000);	
				}
				catch(Exception ex) {
					countdown.setText("Error" + ex);
				}
				if (i == 0) {
					countdown.setText("Game over!");
					endScreen();
				}
				
			}
		}
	}

	
	
	// start timer 
	private void start() {
		
		//textField.setVisible(false);
		userInput.setVisible(true);
		prompt.setVisible(true);
		userInput.requestFocus();
		result.setText("Score: " + correctResponses);
		
		
		new Thread (new timerRunnable()).start();
		next();
	
	}
	
	// iterate to next question 
	private void next() 
	{

		n = random.nextInt(FULL_NAMES.length);
	    prompt.setText("\n\n\n\nAmino Acid: " + FULL_NAMES[n]);
	    
	    userInput.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            // Not used in this context
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                
	            	ans = userInput.getText();
	                
	                if (!ans.equals("")) {
	                	
		        		if ( ans.equalsIgnoreCase(SHORT_NAMES[n]) ) {
		        			correctResponses++; 
		        		}
		        		else {
		        			incorrectResponses++;
		        		}
		        			
		        		result.setText("Score: " + correctResponses);
		        			
		        		userInput.setText("");
		        		
		        		next();
	                }  
	            }
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            // Not used in this context
	        }
	    });
	}
	
	// game over : display score, option to restart
	private void endScreen() {
		DecimalFormat df = new DecimalFormat("##.#");
		userInput.setVisible(false);
		prompt.setVisible(false);
		double score;
		int total = correctResponses + incorrectResponses;
		if (total == 0){
			score = (double)0;
		}
		else {
			score = ((double)correctResponses/ (double)total)*100;
		}
		
		// display results 
		prompt.setText("\n\n\nGame over\n\nYour score: " + df.format(score) + "%");
		prompt.setVisible(true);


		// initialize for next game
		correctResponses = 0;
		incorrectResponses = 0;
		startButton.setText("Play Again");
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		
	}
	
	private void stop() {
		new gui();
	}

	
	
	
	public static void main(String[] args)
	{
		new gui();
	}
	
	
	
}