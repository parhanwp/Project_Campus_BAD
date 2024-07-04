package Main;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Object.Connect;
import Object.LoginInfo;
import Object.MenuInfo;

public class Buy extends JFrame {
	
	JLabel lbTitle,lbMenuID,lbPrice,lbQty,lbTotalPrice;
	JTextField txMenuID,txPrice,txTotalPrice;
	JSpinner spinQty;
	JButton btAdd,btRemove,btCo;
	JPanel utama, bawah, kanan, kiri, btn, data;
	DefaultTableModel dtCart,dtMenu;
	
	LoginInfo info;
	MenuInfo mInfo;
	
	JScrollPane scrMenu = new JScrollPane();
	JScrollPane scrCart = new JScrollPane();
	JTable tbCart = new JTable();
	JTable tbMenu = new JTable();
	Vector<MenuInfo> vMenu = new Vector<>();
	Vector<MenuInfo> vCart = new Vector<>();
	
	
	public Buy(){
		info = new LoginInfo();
		
		setTitle("Buy Drink");
		setSize(1000,600);
		setLocationRelativeTo(null);
		showMenu();
		showCart();
		
		
		lbTitle = new JLabel("Buy Drink");
		lbMenuID = new JLabel("Menu ID");
		lbPrice = new JLabel("Price");
		lbQty = new JLabel("Quantity");
		lbTotalPrice = new JLabel("Total Price");
		
		txMenuID = new JTextField();
		txPrice = new JTextField();
		txTotalPrice = new JTextField();
		
		spinQty = new JSpinner();
		
		btAdd = new JButton("Add to cart");
		btRemove = new JButton("Remove all");
		btCo = new JButton("Checkout");
		
		utama = new JPanel(new GridLayout(2, 1));
		bawah = new JPanel(new GridLayout(1,2));
		kiri = new JPanel();
		kanan = new JPanel();
		data = new JPanel(new GridLayout(4, 2));
		btn = new JPanel();
		
		lbTitle.setFont(new Font("Calibri", Font.BOLD, 20));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		kiri.setLayout(new BorderLayout());
		
		btn.add(btAdd);
		btn.add(btRemove);
		btn.add(btCo);
		
		data.add(lbMenuID);
		data.add(txMenuID);
		data.add(lbPrice);
		data.add(txPrice);
		data.add(lbQty);
		data.add(spinQty);
		data.add(lbTotalPrice);
		data.add(txTotalPrice);
		
		kiri.add(data, BorderLayout.CENTER);
		kiri.add(btn, BorderLayout.SOUTH);
		
		kanan.add(scrCart);
		
		bawah.add(kiri);
		bawah.add(kanan);
		
		utama.add(scrMenu);
		utama.add(bawah);
		
		add(lbTitle, BorderLayout.NORTH);
		add(utama, BorderLayout.CENTER);
		
		//Button action
		
		spinQty.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(!txPrice.getText().equals("")) {
					int total = Integer.parseInt(txPrice.getText()) * (int)(spinQty.getValue());
					txTotalPrice.setText(String.valueOf(total));
				}
			}
		});
		
		
		tbMenu.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent arg0) {
				txPrice.setText("0");
				String sId = tbMenu.getValueAt(tbMenu.getSelectedRow(), 0).toString();
				int sQty = Integer.parseInt(tbMenu.getValueAt(tbMenu.getSelectedRow(), 2).toString());
				int sPrice = Integer.parseInt(tbMenu.getValueAt(tbMenu.getSelectedRow(), 3).toString());
				int total = Integer.parseInt(txPrice.getText()) * (Integer)(spinQty.getValue());
				txTotalPrice.setText(""+total);
				txMenuID.setText(sId);
				txPrice.setText(String.valueOf(sPrice));		
				spinQty.setModel(new SpinnerNumberModel(0, 0, sQty, 1));
			}
		});
		
		btAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tbMenu.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "Please choose the menu first !");
				}else {

						int cId = vMenu.get(tbMenu.getSelectedRow()).getMenuID();
						String cName = vMenu.get(tbMenu.getSelectedRow()).getMenuName();
						int cPrice = vMenu.get(tbMenu.getSelectedRow()).getMenuPrice();
						int cQty = (int) spinQty.getValue();
						int cTotal = Integer.parseInt(txTotalPrice.getText());						
						
						mInfo = new MenuInfo(
									cId,
									cName,
									cQty,
									cPrice,
									cTotal
						);
							
						vCart.add(mInfo);						
						showCart();
				}
			}
		});
		
		btRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(vCart.size() == 0) {
					JOptionPane.showMessageDialog(null, "There is nothing to remove");
				}else {
					vCart.clear();
					clear();
//					showMenu();
					showCart();
					
				}
				
			}
		});
		
		btCo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					PreparedStatement ps = null;
					ps = Connect.getInstance().con.prepareStatement("INSERT INTO header(UserID, TransactionDate) VALUES (?,?)", 
		            ps.RETURN_GENERATED_KEYS);
					ps.setString(1, info.getUserID());
					ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
					int rs = ps.executeUpdate();
					ResultSet generatedKeys = ps.getGeneratedKeys();
					
					if (generatedKeys.next()) {
						int insertId = (int) generatedKeys.getLong(1);
						if(rs > 0) {
							
							String query = "INSERT INTO detailtransaction VALUES ";
							
							for(int i = 0; i < vCart.size(); i++) {
								int menuId = vCart.get(i).getMenuID();
								int qty = vCart.get(i).getMenuQuantity();
								int price = vCart.get(i).getMenuTotal();
								
								ps = Connect.getInstance().con.prepareStatement("UPDATE menu SET qty = qty-? WHERE menuId = ?");
								ps.setInt(1, qty);
								ps.setInt(2, menuId);
								rs = ps.executeUpdate();
								
								String add = "("+insertId+","+menuId+","+qty+","+price+"),";
								
								query = query + add;
							}
							
							query = query.substring(0, query.length()-1);
												
							ps = Connect.getInstance().con.prepareStatement(query);
							rs = ps.executeUpdate();
							
							if(rs > 0) {
								JOptionPane.showMessageDialog(null, "Transaction Success \n Transaction ID #"+insertId);
								vMenu.clear();
								vCart.clear();
								clear();
								showMenu();
								showCart();
							}else {
								JOptionPane.showMessageDialog(null, "Can't insert the detail");
							}
							
						}else {
							JOptionPane.showMessageDialog(null, "Can't Buy");
						}
		            }
					
				} catch (Exception exception) {
					System.out.println(exception);
					exception.printStackTrace();
				}
				
			}
		});
		
	}
	
	void clear() {
		spinQty.setModel(new SpinnerNumberModel(0, 0, 0, 1));
		txMenuID.setText("");
		txPrice.setText("");
		txTotalPrice.setText(String.valueOf(0));
		spinQty.setValue(0);
		txMenuID.setEnabled(false);
		txPrice.setEnabled(false);
		txTotalPrice.setEnabled(false);
	}
	
	void showMenu(){
		tbMenu.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbMenu.getTableHeader().setReorderingAllowed(false);
		scrMenu.setViewportView(tbMenu);
		dtMenu = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
		dtMenu.addColumn("Menu ID");
		dtMenu.addColumn("Name");
		dtMenu.addColumn("Quantity");
		dtMenu.addColumn("Price");
		
		try {
			PreparedStatement ps = Connect.getInstance().con.prepareStatement("Select * from menu");
			ResultSet rs = ps.executeQuery();
			
			MenuInfo data;
			while(rs.next()){
				dtMenu.addRow(new Object[] {
						rs.getString("MenuID"),
						rs.getString("MenuName"),
						rs.getString("MenuQuantity"),
						rs.getString("MenuPrice"),
				});
				data = new MenuInfo(
						rs.getInt("MenuID"),
						rs.getString("MenuName"),
						rs.getInt("MenuQuantity"),
						rs.getInt("MenuPrice")
				);
				vMenu.add(data);
			}
			
			tbMenu.setModel(dtMenu);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	void showCart(){
		tbCart.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbCart.getTableHeader().setReorderingAllowed(false);
		scrCart.setViewportView(tbCart);
		dtCart = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
			return false;
			}
		};
		
		dtCart.addColumn("Menu ID");
		dtCart.addColumn("Name");
		dtCart.addColumn("Quantity");
		dtCart.addColumn("Price");
		dtCart.addColumn("Total Price");

		try {
			
			for(int i = 0; i < vCart.size(); i++) {
				dtCart.addRow(new Object[] {
						vCart.get(i).getMenuID(),
						vCart.get(i).getMenuName(),
						vCart.get(i).getMenuQuantity(),
						vCart.get(i).getMenuPrice(),
						vCart.get(i).getMenuTotal()
				});
			}
			tbCart.setModel(dtCart);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	
	
	

}
