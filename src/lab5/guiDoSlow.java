package lab5;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;


public class guiDoSlow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JButton startButton = new JButton("start");
	private JButton startButton2 = new JButton("start");	
	private JButton cancelButton = new JButton("cancel"); 
	private JTextArea textArea = new JTextArea(); 
	JScrollPane jp = new JScrollPane(textArea);
	private int userInput;
	boolean validInput = false;
	
	private volatile boolean cancel = false;
	
	
	public guiDoSlow() {
		
		createFrame();
		
		startButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				getUserInput(); 
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel = true;
			}
		});	
		
		startButton2.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				new guiDoSlow(); 
				
			}
		});
		
	}

	
	public void createFrame() { 
		setTitle("Find Primes");
		setLocationRelativeTo(null);
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		
		textArea.setWrapStyleWord(true); 
		textArea.setLineWrap(true);
		textArea.setMargin(new Insets(10,10,10,10));
		textArea.setText("\n\n\nThis program will find all prime numbers from 1 to your user inputted number. \n\n\nPress start, then input a large integer.");
		panel.add(jp, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(startButton);
		bottomPanel.add(cancelButton);
		bottomPanel.add(startButton2);
		startButton2.setVisible(false);
		bottomPanel.setLayout(new GridLayout(0,3));
		panel.add(bottomPanel,BorderLayout.SOUTH);
		
		add(panel);
		
		cancelButton.setEnabled(false);
		setVisible(true);
		
	}
	
	
	public void getUserInput() {
		
		cancelButton.setEnabled(true);
		
		while (!validInput) { 
			String userInputStr = JOptionPane.showInputDialog("Enter an integer to parse: ");
			
			if (userInputStr != null) {
				
				try {
					userInput = Integer.parseInt(userInputStr);
					validInput = true;
					  
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else {
				startButton.setEnabled(true);
				break;
			}
			
			
			String inputThreads = JOptionPane.showInputDialog("Enter the number of threads to run: ");
			int numThreads = Integer.parseInt(inputThreads);
			
			Semaphore s = new Semaphore(numThreads);
			
			try {
				s.acquire();
			} 
			
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			new Thread ( new primeWorker(userInput, s) ).start();
			
		}

	}	

	
	public class primeWorker implements Runnable {
		
		private final int maxNum;
		private final Semaphore s;
		private double startTime;
		List<Integer> primeNums  = Collections.synchronizedList(new ArrayList<Integer>());
		private int i;
		
		public primeWorker(int maxNum, Semaphore s) {
			
			this.maxNum = maxNum;
			this.s = s;

		}
		
		private void findPrimes() {
			
			startTime = System.currentTimeMillis();
	        boolean isPrime = true; 
	        
	        for(i = 1; i <= maxNum; i++) {
	            isPrime = true;
	            
	            for(int j = 2; j < i; j++){
	                if (i % j == 0) { 
	                    isPrime = false;  
	                    break;
	                }
	            }
	            
	            if(isPrime) {          
	                primeNums.add(i);  
	            } 
	        }
		}
		
		private final List<Integer> getAllPrimes() {
			return primeNums;
		}

		private final int getNumberPrimes() {
			return primeNums.size(); 
		}

		private final double getTimer() {
			return (System.currentTimeMillis()- startTime) / 1000f;
		}
		
		private final int getCurrentNum() {
			return i;
		}
		
		private final int getInput() {
			return maxNum;
		}
		
		public void run() {
			
			try {
				
				try {
					s.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				
				final primeWorker worker = new primeWorker(maxNum, s);
				
				Thread t = new Thread(worker) {
				     @Override
				     public void run() {
				          worker.findPrimes();
				}
				};
				
				t.start();
				
				Thread.sleep(100);
								
				while (t.isAlive() && cancel == false) {
					textArea.setText("Program running...\n\n");
					textArea.setText("Maximum number: " + worker.getInput() + "\n\n");
					textArea.append(worker.getNumberPrimes() + " primes found out of " + worker.getCurrentNum() + " total values in "+ worker.getTimer() + " seconds...");
					
					Thread.sleep(1500);
				}
				
				if (cancel) {
					t.interrupt();
					textArea.setText("PROGRAM CANCELLED \n\n" + worker.getNumberPrimes() + " primes found out of " + worker.getCurrentNum() + " total values in "+ worker.getTimer() + " seconds.");

				}
				else {
					textArea.setText("Found "+ worker.getNumberPrimes() + " primes in " + worker.getTimer() + " seconds.\n\n\n");
					startButton.setVisible(false);
					startButton2.setVisible(true);
					
				}
				
				
				for (Integer prime : worker.getAllPrimes()) {
					textArea.append(Integer.toString(prime) + "\n");
	
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			s.release();
		}
	}
	
	
	public static void main(String[] args) {	

		new guiDoSlow();
	}
}