package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;



import database.database;
import database.student;

public class gui extends JFrame implements ActionListener {
	
	 // Tạo Table ___________________________________________________________________
    public Vector<String> header = new Vector<String>();
    public Vector<Vector<String>> data = new Vector<>();

    // Tạo ManagerList______________________________________________________________
    student manager = new student();
	
	JLabel title, mssvL, tenL, gioitinhL, diachiL, emailL;
    JTextField  mssvF, tenF, gioitinhF, diachiF, emailF;
    JButton  updateButton,deleteButton,resetButton,restarButton,printfButton,refresh;
    JRadioButton nam, nu;
    ButtonGroup bg;

    JPanel panel;
    JTable table;

    DefaultTableModel model;
    JScrollPane scrollPane;
    Connection con;
    Statement stmt;
   
    public gui() throws   ClassNotFoundException, SQLException {
    	
		    
    	  super("Quản lí sinh viên");
          setSize(770, 430);
          setLayout(null);
    	
          title = new JLabel("Registration Form");
          title.setBounds(60, 7, 200, 30);
       
          mssvL = new JLabel("MSSV");          
          mssvL.setBounds(30, 50, 60, 30);
          
          tenL = new JLabel("Họ & tên"); 
          tenL.setBounds(30, 85, 60, 30);

          gioitinhL = new JLabel("Giới tính"); 
          gioitinhL.setBounds(30, 120, 60, 30);

          diachiL = new JLabel("Địa chỉ"); 
          diachiL.setBounds(30, 155, 60, 30); 

          emailL = new JLabel("Email"); 
          emailL.setBounds(30, 190, 60, 30);
                  
          mssvF = new JTextField(); 
          mssvF.setBounds(95, 50, 130, 30);
          mssvF.setEditable(false);
                
          tenF = new JTextField(); 
          tenF.setBounds(95, 85, 130, 30); 
                
          nam = new JRadioButton("Male");
          nam.setBounds(95, 120, 60, 30);
          
         nu = new JRadioButton("Female");
          nu.setBounds(155, 120, 70, 30); 
          
          bg = new ButtonGroup(); 
          bg.add(nam); 
          bg.add(nu); 
          
          diachiF = new JTextField(); 

          diachiF.setBounds(95, 155, 130, 30);

          
          emailF = new JTextField();
          emailF.setBounds(95, 190, 130, 30);
        
          add(title);
          add(mssvL);
          add(tenL);
          add(gioitinhL);
          add(diachiL);
          add(emailL);
       
          add(mssvF);
          add(tenF);
          add(nam);
          add(nu);
          add(diachiF);
          add(emailF);
              
          // Xây dựng Update Button

          updateButton = new JButton("Add");
          updateButton.setBounds(25, 255, 80, 25);  
          updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					int index = table.getSelectedRow();
					  String mssv = mssvF.getText();
					  String ten = tenF.getText();
					  String gioitinh;
					  if(nam.isSelected())
					  {
						  gioitinh = "nam";
					  }
					  else
					  {
						  gioitinh = "nu";
					  }					  
					  String diachi = diachiF.getText();
					  String email = emailF.getText();
					  try {
						manager.addNew(ten, gioitinh, diachi, email);
						model.setRowCount(0);
						
						JOptionPane.showMessageDialog(panel, "Add Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}			
				
			}
		});
          

      printfButton = new JButton("Print");
      printfButton.setBounds(60, 325, 100, 25);
      printfButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		    MessageFormat header = new MessageFormat("Danh Sách Sinh Viên");
		    MessageFormat footer = new MessageFormat("");
		    try {
				table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});

          // Xây dựng Delete Button

          deleteButton = new JButton("Delete");
          deleteButton.setBounds(110, 255, 100, 25);
          deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tenF.getText() == null) {
					JOptionPane.showMessageDialog(panel, "Please select the object!", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}		
				else {
					
					try {
						String mssv = mssvF.getText();
						manager.delete(mssv);					
				        model.setRowCount(0);

				         		
					    JOptionPane.showMessageDialog(panel, "Delete Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(panel, "Delete failure\nDetails:" + e1, "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
        
          // Xây dựng Updata Button

          restarButton = new JButton("Updata");
          restarButton.setBounds(25, 290, 80, 25);
          restarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(tenF.getText() != null){
					int index = table.getSelectedRow();
					  String mssv = mssvF.getText();
					  String ten = tenF.getText();
					  String gioitinh;
					  if(nam.isSelected())
					  {
						  gioitinh = "nam";
					  }
					  else
					  {
						  gioitinh = "nu";
					  }
				
					  
					  String diachi = diachiF.getText();
					  String email = emailF.getText();
				      try {
						manager.update(mssv, ten, gioitinh, diachi, email);
						JOptionPane.showMessageDialog(panel, "Add Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
						model.setRowCount(0);
					    
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(panel, "Please select the object!", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}			 			 
				
			}
		});
          
          // xây dựng Reset Button

          resetButton = new JButton("Reset");

          resetButton.setBounds(110, 290, 100, 25);

          resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mssvF.setText(null);
				tenF.setText(null);
				diachiF.setText(null);
				emailF.setText(null);
				nam.setSelected(false);
				nu.setSelected(false);
			}
		});


          add(restarButton);

          add(updateButton);

          add(deleteButton);

          add(resetButton);    

          add(printfButton);
       // Xaay dựng Panel

          panel = new JPanel();

          panel.setLayout(new GridLayout());

          panel.setBounds(250, 20, 480, 330);

          panel.setBorder(BorderFactory.createDashedBorder(Color.blue));

          add(panel);
          
          // Xây dựng Refresh Button
         
          
          refresh = new JButton("Refresh Table");

          refresh.setBounds(350, 360, 270, 15);

          add(refresh);
          refresh.addActionListener(new ActionListener() {
  			
  			@Override
  			public void actionPerformed(ActionEvent e) {
  				// TODO Auto-generated method stub
  				 DefaultTableModel dm = (DefaultTableModel)table.getModel();
  				 dm.getDataVector().removeAllElements();

  		         System.out.println("Refresh Table");
  		         try {
  					data = manager.getAll();
  				} catch (ClassNotFoundException | SQLException e1) {
  					// TODO Auto-generated catch block
  					e1.printStackTrace();
  				}
  		         model.setDataVector(data, header);
  			}
  		});
          
          model = new DefaultTableModel();

          //Thêm đối tượng của DefaultTableModel vào JTable

          table = new JTable(model);

          //Sửa cột di chuyển

          table.getTableHeader().setReorderingAllowed(false);
         
          model.addColumn("MSSV");

          model.addColumn("Tên");

          model.addColumn("Giới tính");

          model.addColumn("Địa chỉ");
          
          model.addColumn("Email");
          

          scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,

                  JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);        
          panel.add(scrollPane);
          
        
          
          Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

          this.setLocation(dim.width/2-this.getSize().width/2,

                           dim.height/2-this.getSize().height/2);
          	data = manager.getAll();

			// Thiết kế bảng dữ liệu để hiển thị
			header = new Vector<String>();
			header.add("MSSV");
			header.add("Họ & tên");
			header.add("Giới tính");
			header.add("Địa chỉ");
			header.add("Email");
			model.setDataVector(data, header);
  		
          this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {                              		        
				        int index=table.getSelectedRow();
				        mssvF.setText((String)(model.getValueAt(index, 0)));
				        tenF.setText((String)model.getValueAt(index, 1));
				        String k = ((String)model.getValueAt(index, 2));
				        if(k.equals("nam"))
				        {
				        	nam.setSelected(true);;				        	  
				        }
				        else 
				        {
				        	nu.setSelected(true);;
				        	         	 
				        }
				         diachiF.setText((String)model.getValueAt(index, 3));
				         emailF.setText((String)model.getValueAt(index, 4));
				         mssvF.setEditable(false);
				            
				    }
				// TODO Auto-generated method stub
				
			
		});
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          setResizable(false);

          setVisible(true);
          
    }
   
          public static void main(String[] args) {
        	  try {
        		String user = "sa";
  			    String pass = "phanhjeu";  			 
  			    database.dbUser = user;
  			    database.dbPass = pass;
				new gui();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	 

}
