package Main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Object.Connect;

public class ViewTransactionUser extends JFrame{

	JLabel title, trans, date, total;
	JLabel aDate, aTotal;
	
	JScrollPane scrTbl = new JScrollPane();
	JTable table = new JTable();
	DefaultTableModel dtTable;
	
	JComboBox comboTrans = new JComboBox();
	JPanel utama, atas;
	
	public ViewTransactionUser() {
		setSize(600,400);
		setLocationRelativeTo(null);
		
		String kolom[] = {"Transaction ID", "Menu ID", "Quantity", "Price"};
		dtTable = new DefaultTableModel(null, kolom);
		displayOrder();
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		scrTbl.setViewportView(table);
		
		title = new JLabel("View Transaction");
		trans = new JLabel("Transaction ID");
		date = new JLabel("Date");
		total = new JLabel("Total Price");
		aDate = new JLabel("");
		aTotal = new JLabel("");
		
		utama = new JPanel(new GridLayout(2, 1));
		atas = new JPanel(new GridLayout(3,2));
		
		atas.add(trans);
		atas.add(comboTrans);
		atas.add(date);
		atas.add(aDate);
		atas.add(total);
		atas.add(aTotal);
		
		utama.add(atas);
		utama.add(scrTbl);
		
		add(title, BorderLayout.NORTH);
		add(utama);
		
		setVisible(true);
		
	}

	void displayOrder(){
		try{
			String sql = "SELECT * FROM detailtransaction"; //where TransactionID =?
			PreparedStatement ps = Connect.getInstance().con.prepareStatement(sql);
//			ps.setString(1, comboTrans.getSelectedItem().toString());
			
            ResultSet rs = ps.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt(1);
                int menu = rs.getInt(2);
                int qty = rs.getInt(3);
                int price = rs.getInt(4);
            	Object[] data2 = {id,menu,qty,price};
                dtTable.addRow(data2);
            }
           rs.close();
        }catch (Exception e){
        	e.printStackTrace();
		}
	}
	
}
