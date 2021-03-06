import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class RegisterFrame extends JFrame{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 350;
	private static final int FRAME_HEIGHT = 360;
	private JTextField nameTextField;
	private JTextField lastTextField;
	private JTextField emailTextField;
	private JTextField passwordTextField;
	private JRadioButton userButton;
	private JRadioButton coachButton;
	private JRadioButton treasurerButton;
	private int total = 0;
	
	public RegisterFrame(){
		setTitle("Enter details"); 
		createRegister();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void createRegister(){
	//call methods to create panels	
	JPanel textPanel = createTextPanel();	
	JPanel inputPanel = createInputPanel();
	JPanel emailPanel = createEmailInput();
	JPanel passwordPanel = createPasswordInput();
	JPanel radioPanel = createRadio();
	JPanel buttonPanel = createButtons();
	JPanel regpanel = new JPanel();
	regpanel.setLayout(new GridLayout(6, 2));
	regpanel.add(textPanel);
	regpanel.add(inputPanel);
	regpanel.add(emailPanel);
	regpanel.add(passwordPanel);
	regpanel.add(radioPanel);
	regpanel.add(buttonPanel);
	add(regpanel, BorderLayout.NORTH);
	}
	
	public JPanel createTextPanel(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><b><u>Register an Account</u></b></html>");
		label.setFont(new Font("Serif", Font.PLAIN, 32));
		panel.add(label);
		return panel;
	}
	
	public JPanel createInputPanel(){
		JPanel panel = new JPanel();
		JTextField namefield = new JTextField("Enter first name");
		namefield.setPreferredSize(new Dimension(100, 20));
		JTextField lastfield = new JTextField("Enter last name");
		lastfield.setPreferredSize(new Dimension(100, 20));
		panel.add(namefield);
		panel.add(lastfield);
		this.nameTextField = namefield;
		this.lastTextField = lastfield;
		return panel;
	}
	
	public JPanel createEmailInput(){
		JPanel panel = new JPanel();
		JTextField emailfield = new JTextField("Enter an email");
		emailfield.setPreferredSize(new Dimension(200, 20));
		panel.add(emailfield);
		this.emailTextField = emailfield;
		return panel;
	}
	
	public JPanel createPasswordInput(){
		JPanel panel = new JPanel();
		JTextField passwordfield = new JTextField("Enter a password");
		passwordfield.setPreferredSize(new Dimension(200, 20));
		panel.add(passwordfield);
		this.passwordTextField = passwordfield;
		return panel;
	}
	
	public JPanel createRadio(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Select a user type:");
		JRadioButton userradio = new JRadioButton("Member");
		JRadioButton coachradio = new JRadioButton("Coach");
		JRadioButton treasurerradio = new JRadioButton("Treasurer");
		panel.add(label, BorderLayout.NORTH);
		panel.add(userradio, BorderLayout.SOUTH);
		panel.add(coachradio);
		panel.add(treasurerradio);
		ButtonGroup group = new ButtonGroup();
		group.add(userradio);
		group.add(coachradio);
		group.add(treasurerradio);
		this.userButton = userradio;
		this.coachButton = coachradio;
		this.treasurerButton = treasurerradio;
		
		return panel;
	}
	
	public JPanel createButtons(){
		JPanel panel = new JPanel();
		JButton submit = new JButton("Submit");
		//button functionality
		submit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		       //System.out.println("Commencing Parsing");
		       //Parse everything to file
		       //call method with each jtextfield
		      
		       parseString(nameTextField, lastTextField, emailTextField, passwordTextField);  
		       
		      
			
		    }
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	//close the GUI
		    	//System.out.println("Canceled");
		    	
		    	dispose();
		    	new Login();
			       
		    }
		});
		panel.add(submit);
		panel.add(cancel);
		return panel;
	}
	
	public void parseString(JTextField nameParse, JTextField lastParse, JTextField emailParse, JTextField passwordParse){
		try {
			//int totallines = 0;
			String name = nameParse.getText();	
			String lastname = lastParse.getText();
			String email = emailParse.getText();
			String password = passwordParse.getText();
			String writeTo="";
			
			if ((name.matches("[a-zA-Z]+")) && (name.length() >= 2 )) { 
				writeTo+=name+"\n";
			}
			else{
				JOptionPane.showMessageDialog(null, "Error: Name must not contain numbers or special characters and must be at least 2 letters long.");
				throw new IOException("NAME_ERROR");
			}
			
			if (( lastname.matches("[a-zA-Z]+")) && (lastname.length() >= 2)){  
				writeTo+=lastname+"\n";
			}
			else{
				JOptionPane.showMessageDialog(null, "Error: Lastname must not contain numbers or special characters.");
				throw new IOException("LASTNAME_ERROR");
			}
			
			//if ((email.indexOf('@') != -1) || (email.indexOf('.') != -1)){
			if (email.matches("[a-zA-Z][a-zA-Z0-9]*[[\\.\\-_][a-zA-Z0-9]]*@[a-zA-z]+.[a-zA-Z]+")){
				writeTo+=email+"\n";
				
			}
			else{
				JOptionPane.showMessageDialog(null, "Error: Invalid email, please enter email in this format: example@web.com");
				throw new IOException("EMAIL_ERROR");
			}
			
			if (( password.length() > 4 && password.indexOf(' ') == -1)){
				writeTo+=password+"\n";
			}
			else{
				JOptionPane.showMessageDialog(null, "Error: Password must be greater than 4 characters and must not contain any spaces.");
				throw new IOException("PASSWORD_ERROR");
			}
			
			if (userButton.isSelected()){
				writeTo+="Member";
			}
			else if (coachButton.isSelected()){
				writeTo+="Coach";
			}
			else if (treasurerButton.isSelected()){
				writeTo+="Treasurer";
			}
			else{
				JOptionPane.showMessageDialog(null, "Error: Select account type.");
				throw new IOException("SELECTION_ERROR");
			}
			
			PrintWriter writer = new PrintWriter(new FileWriter("Accounts.txt", true));
			writer.println(writeTo);
			writer.close();
			countLines();
			new Login();
		    dispose();
		       
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block 
			//e.printStackTrace();
		}
		
		
			
	}
	
	public void countLines() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("Accounts.txt"));
		int lines = 0;
		while (reader.readLine() != null) lines++;
		reader.close();
		total = lines/5 ;
		//System.out.println(total);
	}
	
	public int getTotal(){
		return total;
	}
	
}