package Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Object.LoginInfo;

public class Main extends JFrame{
	
	JMenuBar menu;
	JMenu mAkun,mTransaksi,mManage;
	JMenuItem iAkun,iChPass,iLogout,iBuy,iTransaksi,iManageMenu;
	JLabel background;
	
	LoginInfo info;
	
	public void setMainFormAttribute() {
		setTitle("Rowan Martini");
		setSize(765,509);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void SetMainFomComponents() {
		info = new LoginInfo();
		
		menu = new JMenuBar();
		mAkun = new JMenu("User");
		mTransaksi = new JMenu("Transaction");
		
		iAkun = new JMenuItem("View Profile");
		iChPass = new JMenuItem("Change Password");
		iLogout = new JMenuItem("Logout");
		iBuy = new JMenuItem("Buy Drink");
		iTransaksi = new JMenuItem("View Transaction");
		
		mAkun.add(iAkun);
		mAkun.add(iChPass);
		mAkun.addSeparator();
		mAkun.add(iLogout);

		mTransaksi.add(iBuy);
		mTransaksi.add(iTransaksi);
		
		menu.add(mAkun);
		menu.add(mTransaksi);
		
		setJMenuBar(menu);
		
		background = new JLabel(new ImageIcon("Foto.jpg"));
		add(background, BorderLayout.CENTER);
 	
	}
	
	public void renderActionListener() {
		iAkun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewProfile profile = new ViewProfile();
				profile.show();				
			}
		});
		
		iChPass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangePass pass = new ChangePass();
				pass.show();
				
			}
		});
		iLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
		});
		
		iBuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Buy buy = new Buy();
				buy.show();
				
			}
		});
		
		iTransaksi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ViewTransactionUser();
				
			}
		});
		
		
	}
	
	public Main() {
		SetMainFomComponents();
		renderActionListener();
		setMainFormAttribute();
	}

}
