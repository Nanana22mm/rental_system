import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI2 extends JFrame implements ActionListener {
	JPanel cardPanel;
	JScrollPane scrollPaner ;
    CardLayout layout;
    private JTextField field;
    private JTextField field2;
    private JTextField field025;
    private JTextField field025c;
    private JTextField field_id;
    private JTextField field_name;
    private JTextField field_pass;
    private JTextField staff_pass;
    private JTextField fieldr;
    private JTextField field3;
	private DefaultListModel<String> listModel;
	private DefaultListModel<String> listModel2;
	private DefaultListModel<String> listModel025;
	private DefaultListModel<String> listModel3;
	private DefaultListModel<String> listModelr;
	private JList<String> list;
	private JList<String> list2;
	private JList<String> list3;
	private JList<String> list025;
	private JList<String> listr;
	private JCheckBox check1;
	private JCheckBox check2;
	private static String labelPrefix1 = "Which do you want to access?";
	JLabel label_name;
    JLabel label_pass;
	
    static final String staff_password="java_final";//staff_password
    
	static int indexx=0;//index of list
	int n_or_l=0;//new customer or loyal customer
	static File f=new File();
    static Customer2 c;
    static Staff2 staff;
    public static String name;
	public static String password;
    
    
    //Screen_Customer:Rent
	class Rent_Button implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			name=field_name.getText();
			password=field_pass.getText();		
			
			boolean status1 =check1.isSelected();
			boolean status2 =check2.isSelected();
			if(status1==false && status2==false) {
				JOptionPane.showMessageDialog(list, "Please check a box, new or loyal.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(name.length()==0 || password.length()==0) {
				JOptionPane.showMessageDialog(list, "Please type your name and password.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				if(status1==true) {
					n_or_l=1;
				}
				else if(status2==true) {
					n_or_l=2;
				}
				
				c=new Customer2(f);
				int flag=0;
				if(n_or_l==1) {
					
					for(int i=0;i<f.count_id;i++) {
						if(password.equals(f.password_list[i])) {
							flag++;
						}
					}
					if(flag!=0) {
						JOptionPane.showMessageDialog(list, "Sorry. The password is already used. Please change password.", "Error", JOptionPane.ERROR_MESSAGE);	
					}
					else {
						indexx=c.Register_New_C(name,password,f);
						JOptionPane.showMessageDialog(listr, "your id is "+String.valueOf(f.id_list[indexx])+".  Please remember.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
						listModelr.clear();
						for(int i=0;i<f.fur_count[indexx];i++) {
				 			String s=f.furniture_list[indexx][i];
				 			listModelr.add(i,s);
				 		}
						layout.show(cardPanel, "Rent");
					}
				}
				else if(n_or_l==2) {
					flag=0;
					String input_id_s=field_id.getText();	
					if(input_id_s.equals("(You don't need to write, if you are new.)")) {
						JOptionPane.showMessageDialog(listr, "Please type your id.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						int input_id= Integer.valueOf(input_id_s);
						
						for(int i=0;i<f.count_id;i++) {
							if(name.equals(f.members_list[i])) {
								if(password.equals(f.password_list[i])) {
									if(input_id==f.id_list[i]) {
										flag++;
									}
								}
							}
						}
						
						if(flag==0) {
							JOptionPane.showMessageDialog(listr, "Aren't you mistype your name or password or id?", "Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							indexx=c.Get_information(name, password,f);
							if(indexx==-1) {
								JOptionPane.showMessageDialog(listr, "Aren't you mistype your name or password or id?", "Error", JOptionPane.ERROR_MESSAGE);
							}
							listModelr.clear();
							for(int i=0;i<f.fur_count[indexx];i++) {
						 		String s=f.furniture_list[indexx][i];
						 		listModelr.add(i,s);
						 	}
							layout.show(cardPanel, "Rent");
						}
					}
				}
			}
			field_name.setText("");
			field_pass.setText("");
			field_id.setText("(You don't need to write, if you are new.)");	
		}
	}
	//Screen_Customer: Register_Furniture
	class RegButtonAction_CF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String tempText = fieldr.getText();
			int flag=0;
			int index=-1;
			for(int i=0;i<f.count_num;i++) {
				if(tempText.equals(f.system_fur_list[i][0])) {
					flag++;
					index=i;
					break;
				}
			}
			if(f.fur_count[indexx]>=10) {
				JOptionPane.showMessageDialog(list, "Sorry. You can borrow only 10 things.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(index==-1) {
				JOptionPane.showMessageDialog(list, "Sorry. There is no such furniture.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(Integer.valueOf(f.system_fur_list[index][1])==0) {
				JOptionPane.showMessageDialog(list, "Sorry. The furniture is lending and the stock is zero.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(index!=-1) {
				listModelr.addElement(tempText);
				c.Edit_C_Fur_List(listModelr,f,indexx);
				c.Rent_System_Fur_List(tempText,f);
				MyCalendar jc = new MyCalendar();
				jc.Calendar();
			}
			flag=0;
		}
	}
	//Screen_Customer: Delete_Furniture
	class DelButtonAction_CF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			int index = listr.getSelectedIndex();
			if (index != -1) {
				String tempText=listModelr.getElementAt(index);
				c.Return_System_Fur_List(tempText,f);
				listModelr.remove(index);
				c.Edit_C_Fur_List(listModelr,f,indexx);
			} else {
				JOptionPane.showMessageDialog(listr, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Screen_Staff: Customer_list_Register
	class RegButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			staff=new Staff2(f);
			String tempText = field.getText();
			int flag=0;
			
			String[] ll=tempText.split("\\s+");
			if(ll.length==1) {
				JOptionPane.showMessageDialog(list, "Please type the password,too.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				
				for(int i=0;i<f.count_id;i++) {
					if(ll[0].equals(f.members_list[i]) || ll[1].equals(f.password_list[i])) {
						flag++;
						break;
					}
				}
				if(flag!=0) {
					JOptionPane.showMessageDialog(list, "The cutomer already exist or the password is already used. Please change name or password.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(flag==0) {
					int s=listModel.getSize();
					String l=listModel.get(s-1);
					ll=l.split("\\s+");
					listModel.addElement((Integer.valueOf(ll[0])+1)+" "+tempText);
					staff.Edit_F_C_List(listModel,f);	
					field.setText("(Customer_name_and_password)");
				}
			}
		}
	}
	//Screen_Staff: Customer_list_Delete
	class DelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			int index = list.getSelectedIndex();
			if (index != -1) {
				int flag=-1;
				String tempText=listModel.getElementAt(index);
				String[] ll=tempText.split("\\s+");
				for(int i=0;i<f.count_id;i++) {
					if(ll[1].equals(f.members_list[i])) {
						flag=i;
						break;
					}
				}
				if(flag!=-1) {
					if(f.fur_count[flag]>0) {
						JOptionPane.showMessageDialog(list, "You can't delete this customer because he or she is lending.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						listModel.remove(index);
						staff.Edit_F_C_List(listModel,f);
					}
				}
			} else {
				JOptionPane.showMessageDialog(list, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Screen_Staff: Stock(Furniture)_Register
	class RegButtonAction_FF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int flag=0;
			String tempText = field2.getText();
			String[] ll=tempText.split("\\s+");
			
			if(ll.length==1) {
				JOptionPane.showMessageDialog(list2, "Please type the furniture number,too.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				for(int i=0;i<f.count_num;i++) {
					if(ll[0].equals(f.system_fur_list[i][0])) {
						flag++;
						break;
					}
				}
				if(flag!=0) {
					JOptionPane.showMessageDialog(list2, "The furniture already exist.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(flag==0) {
					listModel2.addElement(tempText);
					staff.Edit_F_Fur_List(listModel2,f);	
				}
			}
		}
	}
	//Screen_Staff: Stock(Furniture)_Delete
	class DelButtonAction_FF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list2.getSelectedIndex();
			if (index != -1) {
				int flag=0;
				
				String tempText=listModel2.getElementAt(index);
				String[] ll=tempText.split("\\s+");
				for(int i=0;i<f.count_id;i++) {
					for (int j=0;j<f.fur_count[i];j++) {
						if(ll[0].equals(f.furniture_list[i][j])) {
							flag++;
							break;
						}
					}
				}
				if(flag!=0) {
					JOptionPane.showMessageDialog(list2, "You can't delete this furniture because someone is lending.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					listModel2.remove(index);
					staff.Edit_F_Fur_List(listModel2,f);
				}
			} else if(index==-1) {
				JOptionPane.showMessageDialog(list2, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Screen_Staff: The customer who rent(Furniture)_Register
	class RegButtonAction_W implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			listModel3.clear();
			int flag=0;
			String tempText = field3.getText();
			
			for(int i=0;i<f.count_num;i++) {
				if(tempText.equals(f.system_fur_list[i][0])) {
					flag++;
					break;
				}
			}
			if(flag==0) {
				JOptionPane.showMessageDialog(list3, "There is no such furniture.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				flag=0;
				for(int i=0;i<f.count_id;i++) {
					for(int j=0;j<f.fur_count[i];j++) {
						if(tempText.equals(f.furniture_list[i][j])) {
							flag++;
							listModel3.addElement(f.members_list[i]);						}
					}
				}
				if(flag==0) {
					JOptionPane.showMessageDialog(list3, "Nobody rent the furniture.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(list3, listModel3.size()+" customer rent the furniture.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	//Screen_Staff: The customer who rent(Furniture)_Delete
	class DelButtonAction_W implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list3.getSelectedIndex();
			String Text = field3.getText();
			
			
			if (index != -1) {	
				String tempText=listModel3.getElementAt(index);
				for(int i=0;i<f.count_id;i++) {
					if(tempText.equals(f.members_list[i])) {
						c=new Customer2(f);
						staff.Edit_F_C_List_from_fur_name(Text,i,f);
						c.Return_System_Fur_List(tempText,f);
						break;
					}
				}
				
				listModel3.remove(index);
			} else if(index==-1) {
				JOptionPane.showMessageDialog(list3, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Screen_Staff: Each customer's fur_list(Furniture)_Show
	class ShowButtonAction_EDF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tempText0 = field025c.getText();
			int flag=0;
			int index=0;
			
			for(int i=0;i<f.count_id;i++) {
				if(tempText0.equals(f.members_list[i])){
					flag++;
					index=i;
					break;
				}
			}
			if(flag>0) {
				if(f.fur_count[index]==0) {
					JOptionPane.showMessageDialog(list025, "The customer rent no furniture.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
				}
				
				listModel025.clear();
				for(int i=0;i<f.fur_count[index];i++) {
					String s=f.furniture_list[index][i];
					listModel025.add(i,s);
				}
			}
			else if(flag==0) {
				JOptionPane.showMessageDialog(list025, "Aren't you mistype the customer's name? There is no such customer.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Screen_Staff: Each customer's fur_list(Customer)_Register
	class RegButtonAction_EDF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tempText0 = field025c.getText();
			String tempText = field025.getText();
			int flag=-1;
			int index=0;
			for(int i=0;i<f.count_id;i++) {
				if(tempText0.equals(f.members_list[i])){
					index=i;
					break;
				}
			}
			for(int i=0;i<f.count_num;i++) {
				if(tempText.equals(f.system_fur_list[i][0])){
					flag=i;
					break;
				}
			}
			if(f.fur_count[index]>=10) {
				JOptionPane.showMessageDialog(list025, "Sorry. The customer can borrow only 10 things.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(flag==-1) {
				JOptionPane.showMessageDialog(list025, "Sorry. There is no such furniture.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(Integer.valueOf(f.system_fur_list[flag][1])==0) {
				JOptionPane.showMessageDialog(list025, "Sorry. The furniture is lending and the stock is zero.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(flag!=-1) {
				c=new Customer2(f);
				listModel025.addElement(tempText);
				c.Edit_C_Fur_List(listModel025,f,index);
				c.Rent_System_Fur_List(tempText,f);
			}
		}
	}
	//Screen_Staff: Each customer's fur_list(Customer)_Delete
	class DelButtonAction_EDF implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = list025.getSelectedIndex();
			String tempText0 = field025c.getText();
			if (index != -1) {
				String tempText=listModel025.getElementAt(index);
				
				for(int i=0;i<f.count_id;i++) {
					if(tempText0.equals(f.members_list[i])){
						indexx=i;
					}
				}
				c=new Customer2(f);
				c.Return_System_Fur_List(tempText,f);
				listModel025.remove(index);
				c.Edit_C_Fur_List(listModel025,f,indexx);
			} else {
				JOptionPane.showMessageDialog(list025, "None selected!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//Quit
	class QuitButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	//Screen_Customer: Secession
	class Secession_Button implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			name=field_name.getText();
			password=field_pass.getText();
			
			boolean status1 =check1.isSelected();
			boolean status2 =check2.isSelected();
			if(status1==false && status2==false) {
				JOptionPane.showMessageDialog(list, "Please check a box, new or loyal.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(status1==true) {
				JOptionPane.showMessageDialog(list, "Sorry, you can't secession.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				String input_id_s=field_id.getText();	
				if(input_id_s.equals("(You don't need to write, if you are new.)")) {
					JOptionPane.showMessageDialog(listr, "Please type your id.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int input_id= Integer.valueOf(input_id_s);
					int flag=0;
					for(int i=0;i<f.count_id;i++) {
						if(name.equals(f.members_list[i])) {
							if(password.equals(f.password_list[i])) {
								if(input_id==f.id_list[i]) {
									flag++;
								}
							}
						}
					}
					if(flag==0) {
						JOptionPane.showMessageDialog(listr, "Aren't you mistype your name or password or id?", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						c=new Customer2(f);
						indexx=c.Get_information(name, password,f);
						if(indexx==-1) {
							JOptionPane.showMessageDialog(listr, "Aren't you mistype your name or password or id?", "Error", JOptionPane.ERROR_MESSAGE);
						}
						layout.show(cardPanel, "Secession");
					}
				}
			}
		}
	} 
	//Screen_Exit:Yes
	class Yes implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	//Screen_Exit: No
	class No implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		     layout.show(cardPanel, "Customer");
		}
	}
	//Screen_Customer: Secession->Yes
	class Yes_Secession implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(f.fur_count[indexx]==0) {
				layout.show(cardPanel, "See you");
				c=new Customer2(f);
				c.Secession(indexx,f);
			}
			else if(f.fur_count[indexx]>0) {
				layout.show(cardPanel, "Please_return");
			}
		}
	}	
	//Screen_Staff: Customer
	class Staff_C implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String s_p=staff_pass.getText();
			
			if(s_p.equals(staff_password)==false) {
				JOptionPane.showMessageDialog(list2, "The password is wrong.", "Error", JOptionPane.ERROR_MESSAGE);
				staff_pass.setText("");
			}
			else {
				staff=new Staff2(f);
				staff_pass.setText("");
				listModel.clear();
				for(int i=0;i<f.count_id;i++) {
		 			String s=f.id_list[i]+" "+f.members_list[i]+"  "+f.password_list[i];
		 			listModel.add(i,s);
		 		}
			     layout.show(cardPanel, "Staff_C");
			}
		}
	}
	//Screen_Staff: Furniture
	class Staff_F implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s_p=staff_pass.getText();
			
			if(s_p.equals(staff_password)==false) {
				JOptionPane.showMessageDialog(list2, "The password is wrong.", "Error", JOptionPane.ERROR_MESSAGE);
				staff_pass.setText("");
			}
			else {
				staff=new Staff2(f);
				staff_pass.setText("");
				listModel2.clear();
				for(int i=0;i<f.count_num;i++) {
		 			String s=f.system_fur_list[i][0]+"  "+f.system_fur_list[i][1];
		 			listModel2.add(i,s);
		 		}
			     layout.show(cardPanel, "Staff_F");
			}
		}
	}
	//Screen_Staff->Stock (Furniture)
	class Staff_Stock implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			staff=new Staff2(f);
			layout.show(cardPanel, "Staff_F_Stock");
		}
	}
	//Screen_Staff->The customer who rent(Furniture)
    class Staff_who_rent implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		listModel3.clear();
    		staff=new Staff2(f);
    		layout.show(cardPanel, "Staff_Each_F");
		}
    }
    //Screen_Staff->Customer_list(Customer)
    class Staff_C_List implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		staff=new Staff2(f);
		    layout.show(cardPanel, "Customer_list");
    	}
    }
    //Screen_Staff->Each Customer's fur_list(Customer)
    class Staff_what_rent implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		listModel025.clear();
    		staff=new Staff2(f);
		    layout.show(cardPanel, "Customer_fur_list");
    	}
    }
	
	
    public Component createComponents() {
    	f.Read();
    	f.Read_f_file();
    	// panel01
    	//Screen:Customer
    	JPanel panel01 = new JPanel();
        panel01.setLayout(new GridLayout(0,1));
        panel01.add(new JLabel("Customer"));
        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        JPanel pane3 = new JPanel();
        
        JButton button1 = new JButton("Rent");
    	JButton button2 = new JButton("Secession");
    	Rent_Button buttonListener1 = new Rent_Button();
  	    button1.addActionListener( buttonListener1);
  	    Secession_Button buttonListener2 = new Secession_Button();
  	    button2.addActionListener( buttonListener2);
    	
    	pane1.setBorder(BorderFactory.createEmptyBorder( 10, 15, 10, 15 ));
	    pane1.setLayout(new GridLayout(1,0));
	    check1=new JCheckBox("new");
	    check2=new JCheckBox("loyal");
    	pane1.add(check1);
        pane1.add(check2);
        pane2.setBorder(BorderFactory.createEmptyBorder( 0, 15, 10, 15 ));
	    pane2.setLayout(new GridLayout(3,3));
	   
        field_id=new JTextField("(You don't need to write, if you are new.)", 5);
        field_name=new JTextField("", 5);
        field_pass=new JTextField("", 5);
        pane2.add(new JLabel("id"));
	    pane2.add(field_id);
        pane2.add(new JLabel("name"));
        pane2.add(field_name);
        pane2.add(new JLabel("password"));
        pane2.add(field_pass);
        pane3.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20 ));
	    pane3.setLayout(new GridLayout(1,0));
        pane3.add(button1);
        pane3.add(button2);
        panel01.add(pane1);
        panel01.add(pane2);
        panel01.add(pane3);
               
        
        // panel02
        //Screen:Staff
        JPanel panel02 = new JPanel();
        JPanel panes1 = new JPanel();
        JPanel panes2= new JPanel();
        
        panel02.add(new JLabel("Staff"));
        panel02.setLayout(new GridLayout(0,1));
        panes1.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20 ));
	    panes1.setLayout(new GridLayout(1,0));
        staff_pass=new JTextField("", 20);
        panes2.add(new JLabel("password"));
	    panes2.add(staff_pass);
        panel02.add(panes2);
        JLabel labels1 = new JLabel(labelPrefix1 );
        panel02.add(labels1);
        JButton buttons1 = new JButton("Customer");
  	    JButton buttons2 = new JButton("Furniture");
  	    Staff_C fc = new Staff_C();
        Staff_F ff = new Staff_F();
        buttons1.addActionListener(fc);
        buttons2.addActionListener(ff);
  	    panes1.setBorder(BorderFactory.createEmptyBorder(0, 20, 60, 20 ));
	    panes1.setLayout(new GridLayout(1,0));
	    panes1.add(buttons1);
	    panes1.add(buttons2);
	    
	    panel02.add(panes1);
	   	    
	    
        // panel03
	    //Screen:Exit
        JPanel panel03 = new JPanel();
        panel03.setBackground(Color.LIGHT_GRAY);
        panel03.add(new JLabel("Do you really want to exit?"));
        panel03.setLayout(new GridLayout(0,1));
        JPanel panee1 = new JPanel();
        panee1.setBackground(Color.LIGHT_GRAY);
        panee1.setLayout(new GridLayout(1,0));
        panee1.setBorder(BorderFactory.createEmptyBorder( 0, 20, 150, 20 ));
        JButton btn03 = new JButton("Yes");
        JButton btn02 = new JButton("No");
        Yes y = new Yes();
        No n = new No();
        btn03.addActionListener(y);
        btn02.addActionListener(n);
        panee1.add(btn03);
        panee1.add(btn02);
        panel03.add(panee1);
        
        
        //panel04
        //Screen_Customer:Rent
        JPanel panel04=new JPanel();
        fieldr = new JTextField("(Furniture)");
 		listModelr = new DefaultListModel<String>();
 		
  		listr = new JList<String>(listModelr);
  		listr.setVisibleRowCount(10);
  		listr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		JScrollPane scrollPaner = new JScrollPane(listr);
  		scrollPaner.createVerticalScrollBar();
  		scrollPaner.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  		JButton regButtonr = new JButton("Register");
  		RegButtonAction_CF regButtonListenerr = new RegButtonAction_CF();
  		regButtonr.addActionListener( regButtonListenerr );
  		regButtonr.setAlignmentX(Component.CENTER_ALIGNMENT);

  		JButton delButtonr = new JButton("Delete");
  		DelButtonAction_CF delButtonListenerr = new DelButtonAction_CF();
  		delButtonr.addActionListener( delButtonListenerr );

  		JButton quitButtonr = new JButton("Quit");
  		QuitButtonAction quitButtonListenerr = new QuitButtonAction();
  		quitButtonr.addActionListener( quitButtonListenerr );

  		JPanel subPane1r = new JPanel();
  		subPane1r.setLayout(new GridLayout(1, 0));
  		subPane1r.add(delButtonr);
  		subPane1r.add(Box.createRigidArea(new Dimension(30, 10)));
  		subPane1r.add(quitButtonr);
  		
  		JPanel subPane2r = new JPanel();
  		subPane2r.setLayout(new GridLayout(1, 0));
  		subPane2r.add(new JLabel("Rent"));
  		panel04.add(subPane2r);
  		panel04.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
  		panel04.setLayout(new BoxLayout(panel04, BoxLayout.Y_AXIS));
  		panel04.add(fieldr);

  		panel04.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel04.add(regButtonr);
  		panel04.add(Box.createRigidArea(new Dimension(10, 30)));
  		panel04.add(scrollPaner);
  		panel04.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel04.add(subPane1r);
        
  		
        //panel108
  	    //Screen_Customer:Secession
  		JPanel panel08 = new JPanel();
        JPanel panes = new JPanel();
        panel08.setLayout(new GridLayout(0,1));
        panel08.add(new JLabel("Do you really want to Secession?"));
        panes.setLayout(new GridLayout(1,0));
        panes.setBorder(BorderFactory.createEmptyBorder( 0, 20, 150, 20 ));
        
        Yes_Secession yes = new Yes_Secession();
        No no = new No();
        JButton btn0y = new JButton("Yes");
        JButton btn0n = new JButton("No");
        btn0y.addActionListener(yes);
        btn0n.addActionListener(no);
        panes.add(btn0y);
        panes.add(btn0n);
        panel08.add(panes);
          
        
        //panel10
        //Screen_Customer:Secession -> the customer can't secession
        JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayout(0,1));
        panel10.add(new JLabel("Sorry.Please return your furniture."));
          
        //panel11
        //Screen_Customer:Secession -> the customer can secession
        JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayout(0,1));
        panel11.add(new JLabel("Thank you for using our services.  Please pless exit_button."));
          
        
        // panel021
        //Screen_Staff: Customer -> choose customer_list or customer's fur_list
        JPanel panel021 = new JPanel();
        JPanel panes021 = new JPanel();
           
        panel021.add(new JLabel("Staff"));
        panel021.setLayout(new GridLayout(0,1));
        panes021.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20 ));
	    panes021.setLayout(new GridLayout(1,0));
        
        JLabel label021 = new JLabel(labelPrefix1 );
        panel021.add(label021);
        JButton buttons021 = new JButton("Customer list");
  	    JButton buttons022 = new JButton("Each Customer's fur_list");
  	    Staff_C_List cl = new Staff_C_List();
        Staff_what_rent cw = new Staff_what_rent();
        buttons021.addActionListener(cl);
        buttons022.addActionListener(cw);
  	    panes021.setBorder(BorderFactory.createEmptyBorder(0, 20, 60, 20 ));
	    panes021.setLayout(new GridLayout(1,0));
	    panes021.add(buttons021);
	    panes021.add(buttons022);
	    panel021.add(panes021);
  		
      
        //panel024
	    //Screen_Staff: Customer -> Customer_list -> Edit 
        JPanel panel024=new JPanel();
        field = new JTextField("(Customer_name_and_password)");
 		listModel = new DefaultListModel<String>();

  		list = new JList<String>(listModel);
  		list.setVisibleRowCount(10);
  		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		JScrollPane scrollPane = new JScrollPane(list);
  		scrollPane.createVerticalScrollBar();
  		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  		JButton regButton = new JButton("Register");
  		RegButtonAction regButtonListener = new RegButtonAction();
  		regButton.addActionListener( regButtonListener );
  		regButton.setAlignmentX(Component.CENTER_ALIGNMENT);

  		JButton delButton = new JButton("Delete");
  		DelButtonAction delButtonListener = new DelButtonAction();
  		delButton.addActionListener( delButtonListener );
  		
  		
  		JButton quitButton = new JButton("Quit");
  		QuitButtonAction quitButtonListener = new QuitButtonAction();
  		quitButton.addActionListener( quitButtonListener );

  		JPanel subPane1 = new JPanel();
  		subPane1.setLayout(new GridLayout(1, 0));
  		subPane1.add(delButton);
  		subPane1.add(Box.createRigidArea(new Dimension(30, 10)));
  		subPane1.add(quitButton);
  		
  		JPanel subPane5 = new JPanel();
  		subPane5.setLayout(new GridLayout(1, 0));
  		subPane5.add(new JLabel("Customer"));
  		panel024.add(subPane5);
  		panel024.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
  		panel024.setLayout(new BoxLayout(panel024, BoxLayout.Y_AXIS));
  		panel024.add(field);

  		panel024.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel024.add(regButton);
  		panel024.add(Box.createRigidArea(new Dimension(10, 30)));
  		panel024.add(scrollPane);
  		panel024.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel024.add(subPane1);
  		
  		
  		//panel025
  	    //Screen_Staff: Customer -> Each Customer's fur_list -> Edit
        JPanel panel025=new JPanel();
        field025c = new JTextField("(Customer_name)");
        field025 = new JTextField("(Furniture)");
 		listModel025 = new DefaultListModel<String>();
 		
  		list025 = new JList<String>(listModel025);
  		list025.setVisibleRowCount(10);
  		list025.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		JScrollPane scrollPane025 = new JScrollPane(list025);
  		scrollPane025.createVerticalScrollBar();
  		scrollPane025.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  		
  		JButton showButton025 = new JButton("Show");
  		ShowButtonAction_EDF showButtonListene025 = new ShowButtonAction_EDF();
  		showButton025.addActionListener( showButtonListene025);
  		showButton025.setAlignmentX(Component.CENTER_ALIGNMENT);
  		
  		JButton regButton025 = new JButton("Register");
  		RegButtonAction_EDF regButtonListene025 = new RegButtonAction_EDF();
  		regButton025.addActionListener( regButtonListene025);
  		regButton025.setAlignmentX(Component.CENTER_ALIGNMENT);

  		JButton delButton025 = new JButton("Delete");
  		DelButtonAction_EDF delButtonListene025 = new DelButtonAction_EDF();
  		delButton025.addActionListener( delButtonListene025 );

  		JButton quitButton025 = new JButton("Quit");
  		QuitButtonAction quitButtonListener025 = new QuitButtonAction();
  		quitButton025.addActionListener( quitButtonListener025 );

  		JPanel subPane1025 = new JPanel();
  		subPane1025.setLayout(new GridLayout(1, 0));
  		subPane1025.add(delButton025);
  		subPane1025.add(Box.createRigidArea(new Dimension(30, 10)));
  		subPane1025.add(quitButton025);
  		panel025.add(field025c);
  		panel025.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel025.add(showButton025);
  		JPanel subPane2025 = new JPanel();
  		subPane2025.setLayout(new GridLayout(1, 0));
  		subPane2025.add(new JLabel("Rent"));
  		panel025.add(subPane2025);
  		panel025.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
  		panel025.setLayout(new BoxLayout(panel025, BoxLayout.Y_AXIS));
  		panel025.add(field025);

  		panel025.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel025.add(regButton025);
  		panel025.add(Box.createRigidArea(new Dimension(10, 30)));
  		panel025.add(scrollPane025);
  		panel025.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel025.add(subPane1025);

  		
  	    // panel020
  	    //Screen_Staff: Furniture -> choose Stock or The customer who rent
  		JPanel panel020 = new JPanel();
        JPanel panes10 = new JPanel();
           
        panel020.add(new JLabel("Staff"));
        panel020.setLayout(new GridLayout(0,1));
        panes10.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20 ));
	    panes10.setLayout(new GridLayout(1,0));
        
        JLabel labels2 = new JLabel(labelPrefix1 );
        panel020.add(labels2);
        JButton buttons12 = new JButton("Stock");
  	    JButton buttons22 = new JButton("The Customer who rent");
  	    Staff_Stock s = new Staff_Stock();
        Staff_who_rent ww = new Staff_who_rent();
        buttons12.addActionListener(s);
        buttons22.addActionListener(ww);
  	    panes10.setBorder(BorderFactory.createEmptyBorder(0, 20, 60, 20 ));
	    panes10.setLayout(new GridLayout(1,0));
	    panes10.add(buttons12);
	    panes10.add(buttons22);
	    panel020.add(panes10);
  		
	    
  	    //panel022
	    //Screen_Staff: Furniture -> Stock -> Edit
        JPanel panel022=new JPanel();
        field2 = new JTextField("(Furniture)");
 		listModel2 = new DefaultListModel<String>();

  		list2 = new JList<String>(listModel2);
  		list2.setVisibleRowCount(10);
  		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		JScrollPane scrollPane2 = new JScrollPane(list2);
  		scrollPane2.createVerticalScrollBar();
  		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  		JButton regButton2 = new JButton("Register");
  		RegButtonAction_FF regButtonListener2 = new RegButtonAction_FF();
  		regButton2.addActionListener( regButtonListener2 );
  		regButton2.setAlignmentX(Component.CENTER_ALIGNMENT);

  		JButton delButton2 = new JButton("Delete");
  		DelButtonAction_FF delButtonListener2 = new DelButtonAction_FF();
  		delButton2.addActionListener( delButtonListener2 );

  		JButton quitButton2 = new JButton("Quit");
  		QuitButtonAction quitButtonListener2 = new QuitButtonAction();
  		quitButton2.addActionListener( quitButtonListener2 );

  		JPanel subPane3 = new JPanel();
  		subPane3.setLayout(new GridLayout(1, 0));
  		subPane3.add(delButton2);
  		subPane3.add(Box.createRigidArea(new Dimension(30, 10)));
  		subPane3.add(quitButton2);
  		
  		JPanel subPane4 = new JPanel();
  		subPane4.setLayout(new GridLayout(1, 0));
  		subPane4.add(new JLabel("Furniture"));
  		panel022.add(subPane4);
  		panel022.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
  		panel022.setLayout(new BoxLayout(panel022, BoxLayout.Y_AXIS));
  		panel022.add(field2);

  		panel022.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel022.add(regButton2);
  		panel022.add(Box.createRigidArea(new Dimension(10, 30)));
  		panel022.add(scrollPane2);
  		panel022.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel022.add(subPane3);
  		
  		
  		//panel023
  	    //Screen_Staff: Furniture -> The Customer who rent-> Edit
  		JPanel panel023=new JPanel();
        field3 = new JTextField("(Furniture)");
 		listModel3 = new DefaultListModel<String>();

  		list3 = new JList<String>(listModel3);
  		list3.setVisibleRowCount(10);
  		list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  		JScrollPane scrollPane3 = new JScrollPane(list3);
  		scrollPane3.createVerticalScrollBar();
  		scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  		JButton regButton3 = new JButton("Check");
  		RegButtonAction_W regButtonListener3 = new RegButtonAction_W();
  		regButton3.addActionListener( regButtonListener3 );
  		regButton3.setAlignmentX(Component.CENTER_ALIGNMENT);

  		JButton delButton3 = new JButton("Delete");
  		DelButtonAction_W delButtonListener3 = new DelButtonAction_W();
  		delButton3.addActionListener( delButtonListener3 );

  		JButton quitButton3 = new JButton("Quit");
  		QuitButtonAction quitButtonListener3 = new QuitButtonAction();
  		quitButton3.addActionListener( quitButtonListener3 );

  		JPanel subPane33 = new JPanel();
  		subPane33.setLayout(new GridLayout(1, 0));
  		subPane33.add(delButton3);
  		subPane33.add(Box.createRigidArea(new Dimension(30, 10)));
  		subPane33.add(quitButton3);
  		
  		JPanel subPane44 = new JPanel();
  		subPane44.setLayout(new GridLayout(1, 0));
  		subPane44.add(new JLabel("Furniture"));
  		panel023.add(subPane44);
  		panel023.setBorder(BorderFactory.createEmptyBorder( 30, 30, 30, 30 ));
  		panel023.setLayout(new BoxLayout(panel023, BoxLayout.Y_AXIS));
  		panel023.add(field3);

  		panel023.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel023.add(regButton3);
  		panel023.add(Box.createRigidArea(new Dimension(10, 30)));
  		panel023.add(scrollPane3);
  		panel023.add(Box.createRigidArea(new Dimension(10, 20)));
  		panel023.add(subPane33);

 		
        // CardLayout
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        cardPanel.add(panel01, "Customer");
        cardPanel.add(panel02, "Staff");
        cardPanel.add(panel021, "Staff_C");
        cardPanel.add(panel024, "Customer_list");
        cardPanel.add(panel025, "Customer_fur_list");
        cardPanel.add(panel020, "Staff_F");
        cardPanel.add(panel022, "Staff_F_Stock");
        cardPanel.add(panel023, "Staff_Each_F");
        cardPanel.add(panel03, "Exit");   
        cardPanel.add(panel04,"Rent");
        
        cardPanel.add(panel08, "Secession");
        cardPanel.add(panel10, "Please_return");
        cardPanel.add(panel11, "See you");
        
       
        // The button of CardLayout
        JButton firstButton = new JButton("Customer");
        firstButton.addActionListener(this);
        firstButton.setActionCommand("Customer");
        JButton secondButton = new JButton("Staff");
        secondButton.addActionListener(this);
        secondButton.setActionCommand("Staff");
        JButton thirdButton = new JButton("Exit");
        thirdButton.addActionListener(this);
        thirdButton.setActionCommand("Exit");

        JPanel btnPanel = new JPanel();
        btnPanel.add(firstButton);
        btnPanel.add(secondButton);
        btnPanel.add(thirdButton);

       
        // CardPanel_Setting
        Container contentPane = getContentPane();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        contentPane.add(btnPanel, BorderLayout.PAGE_END);
        
        return contentPane;
    }
    
    //actionPerformed for CardLayout
    public void actionPerformed(ActionEvent e)  {
        String cmd = e.getActionCommand();
        layout.show(cardPanel, cmd);    
    }
    
	public static void main(String[] args) {
        JFrame frame = new JFrame("Renta");
        GUI2 g = new GUI2();
        frame.setSize(500, 500);
        Component contents = g.createComponents();
	    frame.getContentPane().add(contents, BorderLayout.CENTER);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}
