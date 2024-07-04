package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.MenuPeer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Object.Connect;

public class ManageMenu extends JFrame{
	
	JLabel lbTitle,lbMenuid,lbMenuname,lbPrice,lbQty;
	JTextField txMenuID,txMenuname,txPrice;
	JSpinner txQty;
	JPanel pnHeader,pnBody,pnMain,pnDetail,pnButton;
	JButton btInsert,btUpdate,btSubmit,btCancel,btDelete;
	JTable tbMenu;
	
	DefaultTableModel dtMenu;
	public Vector<Object> tHeader,tRow;
	PreparedStatement ps;
	ResultSet rs;
	
	String btState="",menuName,Price;
	int choose=0,stock=0,newPrice=0,newQty=0;
	
	public void Attribute() {
		setTitle("Manage Menu");
		setSize(500,400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setVisible(true);
	}
	
	
	public void Components() {
		//JLabel
		lbTitle = new JLabel("Manage Menu");
		lbTitle.setFont(new Font("Times New Roman", Font.BOLD,20));
		lbMenuid = new JLabel("Menu ID");
		lbMenuname = new JLabel("Menu Name");
		lbQty = new JLabel("Quantity");
		lbPrice = new JLabel("Price");
		//JTextField
		txMenuID = new JTextField();
		txMenuID.setEnabled(false);
		txMenuname = new JTextField();
		txMenuname.setEnabled(false);
		txQty = new JSpinner();
		txQty.setEnabled(false);
		txPrice = new JTextField();
		txPrice.setEnabled(false);
		//JButton
		btInsert = new JButton("Insert");
		btCancel = new JButton("Cancel");
		btSubmit = new JButton("Submit");
		btUpdate = new JButton("Update");
		btDelete  = new JButton("Delete");
		btState(true);
		//JPanel
		pnHeader = new JPanel(new FlowLayout());
		pnHeader.add(lbTitle);
		add(pnHeader,BorderLayout.NORTH);
		pnBody = new JPanel(new GridLayout(2,1,10,10));
		
		//Load Menu
		loadMenu();
		
		pnBody.add(tbMenu);
		pnMain = new JPanel(new BorderLayout());
		pnDetail = new JPanel(new GridLayout(4,2,20,20));
		pnDetail.add(lbMenuid);
		pnDetail.add(txMenuID);
		pnDetail.add(lbMenuname);
		pnDetail.add(txMenuname);
		pnDetail.add(lbQty);
		pnDetail.add(txQty);
		pnDetail.add(lbPrice);
		pnDetail.add(txPrice);
		pnButton= new JPanel(new GridLayout(5,1,15,15));
		pnButton.add(btInsert);
		pnButton.add(btUpdate);
		pnButton.add(btDelete);
		pnButton.add(btSubmit);
		pnButton.add(btCancel);
		pnMain.add(pnDetail,BorderLayout.CENTER);
		pnMain.add(pnButton,BorderLayout.EAST);
		pnBody.add(pnMain);
		add(pnBody, BorderLayout.CENTER);
		
		
		
	}
	
	public void Action() {
		tbMenu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				choose = tbMenu.getSelectedRow();
				txMenuID.setText(tbMenu.getValueAt(choose, 0).toString());
				txMenuname.setText(tbMenu.getValueAt(choose, 1).toString());
				txQty.setValue(tbMenu.getValueAt(choose, 2));
				txPrice.setText(tbMenu.getValueAt(choose, 3).toString());
				
			}
		});
		
		btInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btState ="Insert";
				btState(false);
				txMenuname.setEnabled(true);
				txMenuname.setText("");
				txQty.setEnabled(true);
				txQty.setValue(0);
				txPrice.setEnabled(true);
				txPrice.setText("");
				
			}
		});
		
		btUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choose == 0) {
					JOptionPane.showMessageDialog(null, "Please Choose 1 Menu", "Error Message", JOptionPane.ERROR_MESSAGE);					
				}
				else {
					btState="Update";
					btUpdate.setEnabled(false);
					btInsert.setEnabled(false);
					btDelete.setEnabled(true);
					btCancel.setEnabled(true);
					btSubmit.setEnabled(true);
					txMenuname.setEnabled(true);
					txQty.setEnabled(true);
					txPrice.setEnabled(true);
				}
				
			}
		});
		
		btDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(choose == 0) {
					JOptionPane.showMessageDialog(null, "Please Choose 1!","Error Messages", JOptionPane.ERROR_MESSAGE);					
				}
				else {
					btState="Delete";
					int choice = JOptionPane.showConfirmDialog(null, "Are you Sure ?","Delete",JOptionPane.YES_NO_OPTION);
					if( choice == JOptionPane.YES_OPTION) {
						try {
							String QUERY ="Delete from menu where name=?";
							PreparedStatement ps = Connect.getInstance().con.prepareStatement(QUERY);
							ps.setString(1, txMenuname.getText());
							ps.execute();
							btState(true);
							JOptionPane.showMessageDialog(null, "Delete Success");
							refreshTable();
							dispose();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		btSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btState.equals("Insert")) {
					menuName = txMenuname.getText();
					newQty = (int) txQty.getValue();
					Price = txPrice.getText();
					if(Price.equals("")) {
						newPrice=0;
					}
					else {
						newPrice = Integer.parseInt(Price);
					}
					if(menuName.equals("")) {
						JOptionPane.showMessageDialog(null, "Menu Name cant be empty!","Error Message",JOptionPane.ERROR_MESSAGE);
					}
					else if(menuName.length()<5 && menuName.length()>20) {
						JOptionPane.showMessageDialog(null, "Menu Name lenght must be 5-20 Characters!","Error Messages",JOptionPane.ERROR_MESSAGE);
					}
					else if(newQty == 0) {
						JOptionPane.showMessageDialog(null, "Quantity cannot be zero","Error messages",JOptionPane.ERROR_MESSAGE);
					}
					else if(Price.equals("")) {
						JOptionPane.showMessageDialog(null, "Price cant be empty","Error MEssages",JOptionPane.ERROR_MESSAGE);
					}
					if(newPrice < 10000) {
						JOptionPane.showMessageDialog(null, "Price must be at least 10000","ErrorMessage",JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							final String QUERY	= "insert into menu (name,qty,price) values (?,?,?)";
							PreparedStatement ps = Connect.getInstance().con.prepareStatement(QUERY);
							ps.setString(1, menuName);
							ps.setInt(2, newQty);
							ps.setInt(3, newPrice);
							ps.execute();
							btState(true);
							refreshTable();
							JOptionPane.showMessageDialog(null, "Sukses insert new menu");
							dispose();
						}catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				else if(btState.equals("Update")) {
					int choice = JOptionPane.showConfirmDialog(null, "Yakin Update ?","Delet",JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.YES_OPTION) {
						try {
							int id = Integer.parseInt(txMenuID.getText()),
								nprice = Integer.parseInt(txPrice.getText()),
								sstock = (int) txQty.getValue();
							String QUERY = "UPDATE menu SET name=?,qty=?,price=? WHERE menuID=?";
							PreparedStatement ps = Connect.getInstance().con.prepareStatement(QUERY);
							ps.setString(1, txMenuname.getText());
							ps.setInt(2, sstock);
							ps.setInt(3,nprice);
							ps.setInt(4,id);
							ps.execute();
							JOptionPane.showMessageDialog(null,"Successfully Update Menu");
							dispose();
						}catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					
				}
				
			}
		});
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btState(true);
				txMenuname.setEnabled(false);
				txQty.setEnabled(false);
				txPrice.setEnabled(false);			
			}
		});
		
	}
	
	public void loadMenu() {
		tbMenu = new JTable(dtMenu);
		tHeader = new Vector<>();
		tHeader.add("Menu ID");
		tHeader.add("Name");
		tHeader.add("Quantity");
		tHeader.add("Price");
		dtMenu = new DefaultTableModel(tHeader.toArray(),0);
		try {
			ps = Connect.getInstance().con.prepareStatement("SELECT * FROM menu");
			rs =ps.executeQuery();
			dtMenu.addRow(tHeader);
			rs.last();
			int limit=rs.getRow();
			rs.beforeFirst();
			while(rs.next()) {
				tRow = new Vector<>();
				for(int i=1;i<limit;i++) {
					tRow.add(rs.getObject(i));
				}
				dtMenu.addRow(tRow);
			}
			tbMenu.setModel(dtMenu);
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void refreshTable() {
		dtMenu.fireTableDataChanged();
		tbMenu.repaint();
		tbMenu.setModel(dtMenu);
		tbMenu=new JTable(dtMenu);
		tHeader=new Vector<>();
		tHeader.add("Menu ID");
		tHeader.add("Name");
		tHeader.add("Quantity");
		tHeader.add("Price");
		dtMenu=new DefaultTableModel(tHeader.toArray(), 0);
		try {
			ps = Connect.getInstance().con.prepareStatement("SELECT * FROM menu");
			rs = ps.executeQuery();
			dtMenu.addRow(tHeader);
			rs.last(); 
			int limit = rs.getRow(); 
			rs.beforeFirst();
			while (rs.next()) {
				tRow = new Vector<>();
				for (int i = 1; i < limit; i++) {
					tRow.add(rs.getObject(i));
				}
				dtMenu.addRow(tRow);
			}
			tbMenu.setModel(dtMenu);
			dispose();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void btState(Boolean x) {
		btUpdate.setEnabled(x);
		btInsert.setEnabled(x);
		btDelete.setEnabled(x);
		btCancel.setEnabled(!x);
		btSubmit.setEnabled(!x);
	}
	
	public ManageMenu() {
		Attribute();
		Components();
		Action();
		
	}

}
