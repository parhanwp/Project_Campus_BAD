package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Object.Connect;

public class Register extends JFrame{
	
	JPanel pnTitle,pnMid,pnBottom,pnGender;
	JLabel lbTitle,lbUser,lbPass,lbCpass,lbPhone,lbGender;
	JTextField txUser,txPhone;
	JPasswordField txPass,txCpass;
	JComboBox<String> rGender;
	JCheckBox checkbox;
	JButton btSubmit,btCancel;
	ButtonGroup btGroup;
	String user,pass,cpass,phone,gender,state;
	JRadioButton rMale, rFemale;
	
	public void setFormState() {
		setTitle("Register Form");
		setSize(500,450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	public void setFormComponents() {
		//JPanel
		pnTitle = new JPanel(new FlowLayout());
		pnBottom = new JPanel (new FlowLayout());
		pnGender = new JPanel(new FlowLayout());
		pnMid = new JPanel(new GridLayout(6,2,25,25));	
		//JLabel
		lbTitle = new JLabel("Register");
		lbTitle.setFont(new Font("Times New Roman", Font.BOLD,25));
		lbUser = new JLabel("Username");
		lbPass = new JLabel("Password");
		lbCpass = new JLabel("Confrim password");
		lbPhone = new JLabel("Phone Number");
		lbGender = new JLabel("Gender");		

		txUser = new JTextField(20);
		txPhone = new JTextField(12);		
		txPass = new JPasswordField(20);
		txCpass = new JPasswordField(20);		
		
		rMale = new JRadioButton("Male");
		rFemale = new JRadioButton("Female");

		checkbox = new JCheckBox("I Agree with the term and condition");

		btSubmit = new JButton("Submit");
		btCancel = new JButton("Cancel");
		
		btGroup = new ButtonGroup();
		btGroup.add(rMale);
		btGroup.add(rFemale);
		
		pnTitle.add(lbTitle);
		add(pnTitle,BorderLayout.NORTH);
		
		pnMid.add(lbUser);
		pnMid.add(txUser);
		
		pnMid.add(lbPass);
		pnMid.add(txPass);
		
		pnMid.add(lbCpass);
		pnMid.add(txCpass);
		
		pnMid.add(lbPhone);
		pnMid.add(txPhone);
		
		pnMid.add(lbGender);
		pnGender.add(rGender);
//		pnGender.add(rFemale);
		pnMid.add(pnGender);
		pnMid.add(checkbox);
		add(pnMid, BorderLayout.CENTER);
		
		pnBottom.add(btSubmit);
		pnBottom.add(btCancel);
		add(pnBottom,BorderLayout.SOUTH);
									
	}
	
	public void setComponentAction() {
		btSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user = txUser.getText();
				pass = txPass.getText();
				cpass = txCpass.getText();
				phone = txPhone.getText();
				gender = rGender.getSelectedItem().toString();
				if(rMale.isSelected()) {
					gender = "Male";
				}
				else {
					gender = "Female";
				}
				
				if(user.equals("")) {
					JOptionPane.showMessageDialog(null,  "Username field must be fill!", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(user.length()<5 && user.length()>20) {
					JOptionPane.showMessageDialog(null, "Username length must be at 5 until 20 characters", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Password field must be fill!", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(checkAlfaNumeric(pass)==1) {
					JOptionPane.showMessageDialog(null, "Password should be Alphanumeric", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(cpass.equals("")) {
					JOptionPane.showMessageDialog(null, "Confirm Password field must be fill!", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(!cpass.equals(pass)) {
					JOptionPane.showMessageDialog(null, "Confirm Password must be same with Password!", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				//PHONE NUMBER BELUM GW BUAT
				else if(phone.equals("")) {
					JOptionPane.showMessageDialog(null, "Phone Number cannot be Empty!","Eror Message",JOptionPane.ERROR_MESSAGE);
				}
				else if(phone.length() != 11) {
					JOptionPane.showMessageDialog(null, "Phone Number must be 11 digit","Error Message",JOptionPane.ERROR_MESSAGE);
				}
				else if(gender.equals("")) {
					JOptionPane.showMessageDialog(null, "Gender Should be selected", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(!checkbox.isSelected()){
					JOptionPane.showMessageDialog(null, "User Agreement should be checked!", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						final String QUERY ="insert into user (username,password,phone,gender,role) values (?,?,?,?,?)";
						PreparedStatement ps = Connect.getInstance().con.prepareStatement(QUERY);
						
						ps.setString(1,  user);
						ps.setString(2, pass);
						ps.setString(3, phone);
						ps.setString(4, gender);
						ps.setString(5, "User");
						ps.execute();
						
						JOptionPane.showMessageDialog(null, "Reigster Success!");
						dispose();
						Login log = new Login();
						log.show();
						
					}catch(SQLException el) {
						el.printStackTrace();
					}
				}							
			}
		});
		
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
				login.show();				
			}
		});
		
	}
	
	public int checkAlfaNumeric(String x) {
		int limit=0;
		for (int i = 0; i < x.length(); i++) {
			if(Character.isAlphabetic(x.charAt(i))) {
				limit = 1;
			}
			else {
				if(Character.isDigit(x.charAt(i))) {
					limit = 2;
				}
			}
		}
		return limit;
	}
	
	public Register() {
		setFormState();
		setFormComponents();
		setComponentAction();
	}
	
	

}
