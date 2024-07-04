package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Object.Connect;
import Object.LoginInfo;
 
public class Login extends JFrame {
	
	
	JLabel lbTitle,lbUser,lbPass,lbRegister,lbHeaderRegister;
	JTextField txUser;
	JPasswordField txPass;
	JButton btLogin;
	JPanel pnTitle,pnBottom,pnMid;
	
	String user,pass;
	
	LoginInfo info;
	
	public void setLoginFormState() {
		setTitle("ROWAN MARTINI"); //JUDUL
		setSize(400,300); // UKURAN
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	}
	
	public void setLoginComponents() {
		//PANEL
		pnTitle = new JPanel(new FlowLayout());
		pnBottom = new JPanel(new FlowLayout());
		pnMid = new JPanel(new GridLayout(5,1,5,5));
		
		//Tampilan Awal
		lbTitle = new JLabel("LOGIN");
		lbTitle.setFont(new Font ("Times new Roman", Font.BOLD,20));
		lbUser = new JLabel("Username");
		lbPass = new JLabel("Password");
		lbHeaderRegister = new JLabel("Dont Have an account ?");
		lbRegister = new JLabel("Click here to register !");
		txUser = new JTextField();
		txPass = new JPasswordField();
		btLogin = new JButton("Login");
		
		
//		lbRegister.setForeground(Color.blue); // warnain Tulisan 
		
		// Components Panel
		pnTitle.add(lbTitle);
		pnMid.add(lbUser);
		pnMid.add(txUser);
		pnMid.add(lbPass);
		pnMid.add(txPass);
		pnMid.add(btLogin);
		pnBottom.add(lbHeaderRegister);
		pnBottom.add(lbRegister);
		
		add(pnTitle, BorderLayout.NORTH);
		add(pnBottom, BorderLayout.SOUTH);
		add(pnMid, BorderLayout.CENTER);
	
	}
	
	public void renderActionListener() {
		//Button Login
		btLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user = txUser.getText().toString();
				pass = txPass.getText().toString();	
				if(user.equals("")) {
					JOptionPane.showMessageDialog(null, "Username Harus diisi");
				}
				else if (pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Password Harus diisi");
				}
				else { 
					final String QUERY ="SELECT * FROM user WHERE username =? AND password =?";
					
					try {
						PreparedStatement ps = Connect.getInstance().con.prepareStatement(QUERY);
						ps.setString(1, user);
						ps.setString(2, pass);
						
						ResultSet rs = ps.executeQuery();
						
						if(rs.next()) {
							//sett LoginInfo
							info = new LoginInfo();
							info.setUserID(rs.getString("userID"));
							info.setUsername(rs.getString("username"));
							info.setPassword(rs.getString("password"));
							info.setPhone(rs.getString("phoneNumber"));
							info.setRole(rs.getString("role"));
							info.setGender(rs.getString("gender"));
							
							//Main Form
							JOptionPane.showConfirmDialog(null, "Welcome "+info.getUsername()+" !!", "Messages", JOptionPane.OK_CANCEL_OPTION);
							dispose();
							if(info.getRole().equals("User")){
								new Main();
							}else{
								new MainFormAdmin();
							}
						}
						else {
							JOptionPane.showConfirmDialog(null,"Username or Password are wrong!");
						}
						
					} catch (SQLException el) {
						el.printStackTrace();
					}
				}	
			}
		});
		
		//button regis
		lbRegister.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lbRegister.setForeground(null);
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				lbRegister.setForeground(Color.blue);
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Register regis = new Register();
				dispose();
				regis.show();
			}
		});
		
		
	}
	
	public Login() {
		setLoginFormState();
		setLoginComponents();
		renderActionListener();
	}
	public static void main (String args[]) {
		new Login().setVisible(true);
	}

}
