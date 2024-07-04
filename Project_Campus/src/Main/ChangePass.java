package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import Object.Connect;
import Object.LoginInfo;

public class ChangePass extends JFrame {
	
	JLabel lbTitle,lbOldPass,lbNewPass,lbConPass;
	JPasswordField txoldPass,txnewPass,txconPass;
	JButton bSave;
	JPanel pnTitle,pnBottom,pnMid;
	
	String newPass,oldPass,conPass;
	
	LoginInfo info;
	
	public void setFrame() {
		setTitle("Change Pasword ");
		setSize(500,200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void Components() {	
		//JLabel
		lbTitle = new JLabel("Change Password");
		lbTitle.setFont(new Font("Times New Roman", Font.BOLD,20));
		lbOldPass = new JLabel("Old Password");
		lbNewPass = new JLabel("New Password");
		lbConPass = new JLabel("Confrim Password");
		bSave = new JButton("SAVE");
		//JPasswordField
		txoldPass = new JPasswordField();
		txnewPass = new JPasswordField(); 
		txconPass = new JPasswordField();
		//JPanel
		pnTitle = new JPanel(new FlowLayout());
		pnMid = new JPanel(new GridLayout(3,1,5,5));
		pnBottom = new JPanel(new BorderLayout());
		
		pnTitle.add(lbTitle);
		pnMid.add(lbOldPass);
		pnMid.add(txoldPass);
		pnMid.add(lbNewPass);
		pnMid.add(txnewPass);
		pnMid.add(lbConPass);
		pnMid.add(txconPass);
		pnBottom.add(bSave);
		
		add(pnTitle,BorderLayout.NORTH);
		add(pnMid,BorderLayout.CENTER);
		add(pnBottom,BorderLayout.SOUTH);		
	}
	
	public void ComponentsAction() {
		bSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				oldPass = txoldPass.getText();
				newPass = txnewPass.getText();
				conPass = txconPass.getText();
				
				if(oldPass.equals("")) {
					JOptionPane.showMessageDialog(null, "Old Password Cant be Empty!", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(!oldPass.equals(info.getPassword())) {
					JOptionPane.showMessageDialog(null, "Old Password is incorrect", "Error Message", JOptionPane.ERROR_MESSAGE);	
				}
				else if(newPass.equals("")) {
					JOptionPane.showMessageDialog(null, "New Password Cant be Empty!", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(newPass.length() < 5 && newPass.length() > 20) {
					JOptionPane.showMessageDialog(null, "Password length should be 5 until 20 characters", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(conPass.equals("")) {
					JOptionPane.showMessageDialog(null, "Confirm Password cant be Empty!", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(!conPass.equals(newPass)) {
					JOptionPane.showMessageDialog(null, "Confirm Password and New Password should be same", "Error Message", JOptionPane.ERROR_MESSAGE);
				}else if(checkAlfaNumeric(newPass)==1) {
					JOptionPane.showMessageDialog(null, "Password should be Alphanumeric", "Error Message", JOptionPane.ERROR_MESSAGE);
				}
				else {
					String Query = "update user SET password = ? Where userID=?";
					try {
						PreparedStatement ps = Connect.getInstance().con.prepareStatement(Query);
						ps.setString(1, newPass);
						ps.setString(2, info.getUserID());
						ps.execute();
						
						JOptionPane.showMessageDialog(null, "Update Success");
						dispose();
						new Main();
					}
					catch (SQLException e1) {
						e1.printStackTrace();
						
					}
				}
				
			}
		});
	}
	
	public int checkAlfaNumeric(String x) {
		int limit =0;
		for (int i=0;i>x.length();i++) {
			if(Character.isAlphabetic(x.charAt(i))) {
				limit =1;
			}
			else {
				if(Character.isDigit(x.charAt(i))) {
					limit = 2;
				}
			}
		}
		return limit;
	}
	public ChangePass() {
		setFrame();
		Components();
		ComponentsAction();
	}
	
	
	
	

}
