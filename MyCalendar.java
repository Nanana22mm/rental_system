import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.AbstractAction;


public class MyCalendar extends JPanel {
  boolean clickCloseFlg;
  Date clickDate;
  ArrayList<Component> clickDateListenerList = new ArrayList<Component>();
  static JFrame sampleFrame;
  JPanel calenderPanel;
  Calendar now;
  static final Color todayColor = new Color(255, 255, 222, 255);//today's color
  ArrayList<DateLabel> labelList = new ArrayList<DateLabel>();


  public static void Calendar() {
    sampleFrame = new JFrame();
    sampleFrame.setTitle("calender");
    
    sampleFrame.setBounds(400, 400, 400, 400);
    sampleFrame.setLayout(new FlowLayout());

    JSeparator sp = new JSeparator(JSeparator.HORIZONTAL);
    sp.setPreferredSize(new Dimension(320, 2));
    sampleFrame.add(sp);
    MyCalendar jc = new MyCalendar();
    sampleFrame.add(jc);
    sampleFrame.setVisible(true);
  }
  
  public MyCalendar() {
    init(false, new Date());
  }

  public MyCalendar(Date date) {
    init(false, date);
  }

  public MyCalendar(boolean b) {
    init(b, new Date());
  }

  public MyCalendar(boolean b, Date date) {
    init(b, date);
  }

  class ButtonAction extends JFrame implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			sampleFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
  }
  
  private void init(boolean clickCloseFlg, Date date) {
    this.clickCloseFlg = clickCloseFlg;
    setLayout(new BorderLayout());
    
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(0, 1));
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, 14);
    int y = c.get(Calendar.YEAR);
    int m = c.get(Calendar.MONTH);
    
    
    JLabel label=new JLabel("                   Please return until this day.");
    p.add(label);
    
    JPanel pb = new JPanel();
    pb.setLayout(new GridLayout(1, 0));
    JButton Button = new JButton("Ok");
	ButtonAction ButtonListener = new ButtonAction();
	Button.addActionListener( ButtonListener );
	pb.add(Box.createRigidArea(new Dimension(10, 20)));
	Button.setAlignmentX(Component.CENTER_ALIGNMENT);
	pb.add(Button);
	pb.add(Box.createRigidArea(new Dimension(10, 20)));
	p.add(pb);
	
	JPanel p1 = new JPanel();
	p1.setLayout(new GridLayout(0, 1));
	p1.add(Box.createRigidArea(new Dimension(10, 20)));
    JLabel label1=new JLabel(y+"."+(m+1));
    label1.setAlignmentX(Component.CENTER_ALIGNMENT); 
    p1.add(label1);
	p.add(p1);
	
    add(p, BorderLayout.NORTH);

    if (date == null) {
      date = new Date();
    }
    setCalender(date);
    setVisible(true);
  }

  private JPanel createCalenderPanel() {
    JPanel pp = new JPanel();
    JPanel p = new JPanel();
    p.setPreferredSize(new Dimension(250, 150));
    Dimension d = new Dimension(30, 20);
    for (int i = 0; i < 42; i++) {
      DateLabel dateLabel = new DateLabel();
      dateLabel.setPreferredSize(d);
      dateLabel.setHorizontalAlignment(JLabel.RIGHT);
      
      dateLabel.setOpaque(true);
      p.add(dateLabel);
      labelList.add(dateLabel);
    }
    pp.add(p);
    add(pp, BorderLayout.CENTER);
    calenderPanel = pp;
    return pp;
  }

  private void setCalender(Date date) {
    if (calenderPanel == null) {
      createCalenderPanel();
    }
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DAY_OF_MONTH, 14);
    int today = c.get(Calendar.DATE);
    now = (Calendar) c.clone();
    c.set(Calendar.DATE, 1);
       
    if (c.get(Calendar.DAY_OF_WEEK) < Calendar.WEDNESDAY) {
      c.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK) + 1);
      c.add(Calendar.DATE, -7);
    } else {
      c.add(Calendar.DATE, -c.get(Calendar.DAY_OF_WEEK) + 1);
    }

    //color
    Color color = Color.gray;
    Color sundayColor = Color.pink;
    for (int i = 0; i < 42; i++) {
      if (i < 14) {
        if (c.get(Calendar.DATE) == 1) {
          color = Color.black;
          sundayColor = Color.red;
        }
      }
      if (i > 20) {
        if (c.get(Calendar.DATE) == 1) {
          color = Color.gray;
          sundayColor = Color.pink;
        }
      }

      DateLabel dateLabel = labelList.get(i);
      dateLabel.setText("" + c.get(Calendar.DATE));
      if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
        dateLabel.setForeground(sundayColor);
      } else {
        dateLabel.setForeground(color);
      }
      if (c.get(Calendar.DATE) == today && color.equals(Color.black)) {
        dateLabel.setBackground(todayColor);
      } else {
        dateLabel.setBackground(null);
      }
      dateLabel.date = c.getTime();
      c.add(Calendar.DATE, 1);
    }
  }
  
  class DateLabel extends JLabel {
    Date date;
  }
}
