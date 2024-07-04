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

public class MainFormAdmin extends JFrame {
	
	LoginInfo info;
	JMenuBar menu;
	JMenu mAkun, mTransaksi, mManage;
	JMenuItem iLogout, iView, iManage;
	
	JLabel background;
	
	public MainFormAdmin() {
		info = new LoginInfo();
		
		menu = new JMenuBar();
		mAkun = new JMenu("User");
		mTransaksi = new JMenu("Transaction");
		mManage = new JMenu("Manage");

		iLogout = new JMenuItem("Logout");
		iView = new JMenuItem("View Transaction");
		iManage = new JMenuItem("Manage Menu");
		
		mAkun.add(iLogout);

		mTransaksi.add(iView);

		mManage.add(iManage);
		
		menu.add(mAkun);
		menu.add(mTransaksi);
		menu.add(mManage);
		
		setJMenuBar(menu);
		
		background = new JLabel(new ImageIcon("Foto.jpg"));
		add(background, BorderLayout.CENTER);
 	
		
		iManage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageMenu menu = new ManageMenu();
				menu.show();
				
			}
		});
		
		
	}
	
	

}
