package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Object.Connect;
import Object.LoginInfo;

public class ViewProfile extends JFrame{
	
	JLabel lbTitle,lbUser,lbPhone,lbGender;
	JTextField txPhone,txUser;
	JRadioButton rMale,rFemale;
	JButton btEdit,btUpdate,btCancel;
	JPanel pnTitle,pnBottom,pnMid,pnGender;
	ButtonGroup btGroup;
	
	String username,phone,gender;
	LoginInfo info;
	public void setFrame() {
		setTitle("Profile");
		setSize(500,400);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void Component() {
		info=new LoginInfo();
		
		pnTitle = new JPanel(new FlowLayout());
		pnMid = new JPanel(new GridLayout(3,1,5,5));
		pnBottom = new JPanel(new FlowLayout());
		pnGender = new JPanel(new FlowLayout()); 

		lbTitle = new JLabel("Profile");
		lbTitle.setFont(new Font("Times New Roman", Font.BOLD,20));
		lbUser = new JLabel("Username");
		lbPhone = new JLabel("Phone Number");
		lbGender = new JLabel("Gender");
	
		txUser = new JTextField();
		txUser.setText(info.getUsername());
		txUser.setEnabled(false);

		txPhone = new JTextField();
		txPhone.setText(info.getPhone());
		txPhone.setEnabled(false);

		rMale = new JRadioButton("Male");
		rMale.setEnabled(false);
		rFemale = new JRadioButton("Female");
		rFemale.setEnabled(false);
		
		if(info.getGender().equals("Male")) {
			rMale.setSelected(true);
		}
		else {
			rFemale.setSelected(true);
		}
		btUpdate= new JButton("Update");
		btEdit = new JButton("Edit");
		btEdit.setEnabled(false);
		btCancel=new JButton("Cancel");
		btCancel.setEnabled(false);
		
		btGroup = new ButtonGroup();
		btGroup.add(rFemale);
		btGroup.add(rMale);
		
		pnTitle.add(lbTitle);
		pnMid.add(lbUser);
		pnMid.add(txUser);
		pnMid.add(lbPhone);
		pnMid.add(txPhone);
		pnMid.add(lbGender);
		pnMid.add(pnGender);
		pnBottom.add(btUpdate);
		pnBottom.add(btEdit);
		pnBottom.add(btCancel);
		pnGender.add(rFemale);
		pnGender.add(rMale);
		
		add(pnTitle, BorderLayout.NORTH);
		add(pnMid, BorderLayout.CENTER);
		add(pnBottom, BorderLayout.SOUTH);
	}
	
	public void Action() {
		btUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btUpdate.setEnabled(false);
				btCancel.setEnabled(true);
				btEdit.setEnabled(true);
				txUser.setEnabled(false);
				txPhone.setEnabled(true);
				rMale.setEnabled(true);
				rFemale.setEnabled(true);
			}
		});
		
		btEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username = txUser.getText();
				phone = txPhone.getText();
				if(rMale.isSelected()) {
					gender = "Male";
				}
				else {
					gender = "Female";
				}

				if(phone.length()<5 && phone.length()>20) {
					JOptionPane.showMessageDialog(null, "Phone Number must be 11 digit","Error message", JOptionPane.ERROR_MESSAGE);
				}
				else if(gender.equals("")) {
					JOptionPane.showMessageDialog(null, "Gender must be selected","Error message", JOptionPane.ERROR_MESSAGE);
				}else {
					String Query = "UPDATE user SET username = ? , phone = ? , gender = ? WHERE userID = ?";
					try {
						PreparedStatement ps = Connect.getInstance().con.prepareStatement(Query);
						System.out.println(username);
						ps.setString(1, username);
						ps.executeUpdate(Query);
						
						if(ps.executeUpdate(Query) == 0) {
							JOptionPane.showMessageDialog(null, "Update Failed !", "Error message",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null, "Update Success !");
							txUser.setText(username);
							txPhone.setText(phone);
							if(gender.equals("Male")) {
								rMale.setSelected(true);
							}else {
								rFemale.setSelected(true);
							}
							dispose();
							new Main();
//							btUpdate.setEnabled(true);
//							btCancel.setEnabled(false);
//							btEdit.setEnabled(false);
//							txUser.setEnabled(false);
//							txPhone.setEnabled(false);
//							rMale.setEnabled(false);
//							rFemale.setEnabled(false);		
						
						};
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btUpdate.setEnabled(true);
				btCancel.setEnabled(false);
				btEdit.setEnabled(false);
				txUser.setEnabled(false);
				txPhone.setEnabled(false);
				rMale.setEnabled(false);
				rFemale.setEnabled(false);		
			}
		});
		
	}

	public ViewProfile() {
		setFrame();
		Component();
		Action();
	}

}
