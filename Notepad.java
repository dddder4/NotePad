import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.io.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.undo.*;

public class Notepad{
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MyWindow w = new MyWindow();
			}
		});
	}
}

class MyWindow extends JFrame implements MouseListener, ActionListener, DocumentListener, WindowListener, MenuListener, ListSelectionListener, CaretListener{
	private JFrame frame = new JFrame("�ޱ���.txt");
	private JMenuBar mb = new JMenuBar();
	private JMenu menu1 = addMenu("�ļ�(F)",KeyEvent.VK_F);
	private JMenu menu2 = addMenu("�༭(E)",KeyEvent.VK_E);
	private JMenu menu3 = addMenu("��ʽ(O)",KeyEvent.VK_O);
	private JMenu menu4 = addMenu("�鿴(V)",KeyEvent.VK_V);
	private JTextArea jta = new JTextArea();
	private JPopupMenu popup = new JPopupMenu();
	private JScrollPane scrollPane = new JScrollPane(jta);
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Clipboard cb = tk.getSystemClipboard();
	private JMenuItem item1 = addMenuItem("�½�(N)",KeyEvent.VK_N,KeyEvent.VK_N,KeyEvent.CTRL_MASK);
	private JMenuItem item2 = addMenuItem("��(O)...",KeyEvent.VK_O,KeyEvent.VK_O,KeyEvent.CTRL_MASK);
	private JMenuItem item3 = addMenuItem("����(S)",KeyEvent.VK_S,KeyEvent.VK_S,KeyEvent.CTRL_MASK);
	private JMenuItem item4 = addMenuItem("���Ϊ(A)...",KeyEvent.VK_A);
	private JMenuItem item5 = addMenuItem("�˳�(X)...",KeyEvent.VK_X);
	private JMenuItem item6 = addMenuItem("����(U)",KeyEvent.VK_U,KeyEvent.VK_Z,KeyEvent.CTRL_MASK);
	private JMenuItem item7 = addMenuItem("����(T)",KeyEvent.VK_T,KeyEvent.VK_X,KeyEvent.CTRL_MASK);
	private JMenuItem item8 = addMenuItem("����(C)",KeyEvent.VK_C,KeyEvent.VK_C,KeyEvent.CTRL_MASK);
	private JMenuItem item9 = addMenuItem("ճ��(P)",KeyEvent.VK_P,KeyEvent.VK_V,KeyEvent.CTRL_MASK);
	private JMenuItem item10 = addMenuItem("ɾ��(L)",KeyEvent.VK_L,KeyEvent.VK_DELETE,0);
	private JMenuItem item11 = addMenuItem("����(F)...",KeyEvent.VK_F,KeyEvent.VK_F,KeyEvent.CTRL_MASK);
	private JMenuItem item12 = addMenuItem("������һ��(N)",KeyEvent.VK_N,KeyEvent.VK_F3,0);
	private JMenuItem item13 = addMenuItem("�滻(R)",KeyEvent.VK_R,KeyEvent.VK_H,KeyEvent.CTRL_MASK);
	private JMenuItem item14 = addMenuItem("ת��(G)",KeyEvent.VK_G,KeyEvent.VK_G,KeyEvent.CTRL_MASK);
	private JMenuItem item15 = addMenuItem("ȫѡ(A)",KeyEvent.VK_A,KeyEvent.VK_A,KeyEvent.CTRL_MASK);
	private JMenuItem item16 = addMenuItem("ʱ��/����(D)",KeyEvent.VK_D,KeyEvent.VK_F5,0);
	private JCheckBoxMenuItem item17 = addCheckBoxMenuItem("�Զ�����(W)",KeyEvent.VK_W,false);
	private JMenuItem item18 = addMenuItem("����(F)...",KeyEvent.VK_F);
	private JCheckBoxMenuItem item19 = addCheckBoxMenuItem("״̬��(S)",KeyEvent.VK_S,false);
	private JMenuItem item20 = addMenuItem("����(U)",KeyEvent.VK_U);
	private JMenuItem item21 = addMenuItem("����(T)",KeyEvent.VK_T);
	private JMenuItem item22 = addMenuItem("����(C)",KeyEvent.VK_C);
	private JMenuItem item23 = addMenuItem("ճ��(P)",KeyEvent.VK_P);
	private JMenuItem item24 = addMenuItem("ɾ��(L)",KeyEvent.VK_L);
	private JMenuItem item25 = addMenuItem("ȫѡ(A)",KeyEvent.VK_A);
	private JCheckBoxMenuItem item26 = addCheckBoxMenuItem("���ҵ�����Ķ�˳��(R)",KeyEvent.VK_R,false);
	private JLabel label = new JLabel("                 ��" + 1 + "��,��" + 1 + "��");
	protected UndoManager um = new UndoManager();
	protected UndoableEditListener ue = new MyUndoableEditListener();
	private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private int[] wordArray = {Font.PLAIN,Font.BOLD,Font.ITALIC,Font.BOLD+Font.ITALIC};
	private String pString = "";
	private String[] wordString1 = ge.getAvailableFontFamilyNames();
	private String[] wordString2 = {"����","����","��б","��ƫб��"};
	private String[] wordString3 = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72","����","С��","һ��","Сһ","����","С��","����","С��","�ĺ�","С��","���","С��","����","С��","�ߺ�","�˺�"};
	private boolean newFile = true;
	private File currentFile;
	private JDialog dialog1 = new JDialog(frame,"�滻",false);
	private JDialog dialog2 = new JDialog(frame,"����",false);
	private JDialog dialog3 = new JDialog(frame,"����",true);
	private JDialog dialog4 = new JDialog(frame,"ת��ָ����",true);
	private Container container1 = dialog1.getContentPane();
	private Container container2 = dialog2.getContentPane();
	private Container container3 = dialog3.getContentPane();
	private Container container4 = dialog4.getContentPane();
	private JCheckBox findCheckBox1 = new JCheckBox("���ִ�Сд(C)");
	private JCheckBox replaceCheckBox1 = new JCheckBox("���ִ�Сд(C)");
	private ButtonGroup bg2 = new ButtonGroup();
	private JLabel findLabel1 = new JLabel("��������(N)��");
	private JLabel replaceLabel1 = new JLabel("��������(N)��");
	private JLabel replaceLabel2 = new JLabel("�滻Ϊ(P)��");
	private JLabel wordLabel1 = new JLabel("����(F)��");
	private JLabel wordLabel2 = new JLabel("����(Y)��");
	private JLabel wordLabel3 = new JLabel("��С(S)��");
	private JLabel wordLabel4 = new JLabel("AaBbYyZz");
	private JLabel toLabel1 = new JLabel("�к�(L)��");
	private JTextField findText1 = new JTextField(25);
	private JTextField replaceText1 = new JTextField(25);
	private JTextField replaceText2 = new JTextField(25);
	private JTextField wordText1 = new JTextField(9);
	private JTextField wordText2 = new JTextField(8);
	private JTextField wordText3 = new JTextField(5);
	private JTextField toText1 = new JTextField(25);
	private JPanel findPanel1 = new JPanel();
	private JPanel findPanel2 = new JPanel();
	private JPanel findPanel3 = new JPanel();
	private JPanel findPanel4 = new JPanel();
	private JPanel replacePanel2 = new JPanel();
	private JPanel replacePanel3 = new JPanel();
	private JPanel replacePanel4 = new JPanel();
	private JPanel replacePanel5 = new JPanel();
	private JPanel wordPanel1 = new JPanel();
	private JPanel wordPanel2 = new JPanel();
	private JPanel wordPanel3 = new JPanel();
	private JPanel wordPanel4 = new JPanel();
	private JPanel wordPanel5 = new JPanel();
	private JPanel toPanel1 = new JPanel();
	private JPanel toPanel2 = new JPanel();
	private JButton findButton1 = new JButton("������һ��(F)");
	private JButton findButton2 = new JButton("ȡ��");
	private JButton replaceButton1 = new JButton("������һ��(F)");
	private JButton replaceButton2 = new JButton("ȡ��");
	private JButton replaceButton3 = new JButton("�滻(R)");
	private JButton replaceButton4 = new JButton("ȫ���滻(A)");
	private JButton toButton1 = new JButton("ת��");
	private JButton toButton2 = new JButton("ȡ��");
	private JButton wordButton1 = new JButton("ȷ��");
	private JButton wordButton2 = new JButton("ȡ��");
	private JRadioButton findRbutton1 = new JRadioButton("����(U)");
	private JRadioButton findRbutton2 = new JRadioButton("����(U)");
	private JRadioButton replaceRbutton1 = new JRadioButton("����(U)");
	private JRadioButton replaceRbutton2 = new JRadioButton("����(U)");
	private Font font = jta.getFont();
	private JList wordList1 = new JList(wordString1);
	private JList wordList2 = new JList(wordString2);
	private JList wordList3 = new JList(wordString3);
	private JFileChooser jfc1 = new JFileChooser();
	public MyWindow() {
		//�������д���
		jta.setFont(new Font("����", Font.PLAIN, 18));
		frame.setSize(900, 600);
		frame.setLocation(450, 200);
		frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
		frame.setJMenuBar(mb);
		item6.setEnabled(false);
		item20.setEnabled(false);
		menu1.add(item1);
		menu1.add(item2);
		menu1.add(item3);
		menu1.add(item4);
		menu1.addSeparator();
		menu1.add(item5);
		mb.add(menu1);
		menu2.add(item6);
		menu2.addSeparator();
		menu2.add(item7);
		menu2.add(item8);
		menu2.add(item9);
		menu2.add(item10);
		menu2.addSeparator();
		menu2.add(item11);
		menu2.add(item12);
		menu2.add(item13);
		menu2.add(item14);
		menu2.addSeparator();
		menu2.add(item15);
		menu2.add(item16);
		mb.add(menu2);
		menu3.add(item17);
		menu3.add(item18);
		mb.add(menu3);
		menu4.add(item19);
		mb.add(menu4);
		frame.add(scrollPane,BorderLayout.CENTER);
		popup.add(item20);
		popup.addSeparator();
		popup.add(item21);
		popup.add(item22);
		popup.add(item23);
		popup.add(item24);
		popup.addSeparator();
		popup.add(item25);
		jta.add(popup);
		jta.addMouseListener(this);
		jta.setWrapStyleWord(true);
		jta.setLineWrap(false);
	    label.setVisible(false);
	    jta.getDocument().addUndoableEditListener(ue);
	    frame.add(label,BorderLayout.SOUTH);
		enable(item7);
		enable(item8);
		enable(item9);
		enable(item10);
		enable(item21);
		enable(item22);
		enable(item23);
		enable(item24);
		dialog2.setResizable(false);
		dialog2.setSize(580, 200);
		dialog2.setLocation(630,380);
		dialog2.setDefaultCloseOperation(dialog2.HIDE_ON_CLOSE);
		container2.setLayout(new FlowLayout(FlowLayout.LEFT));
		findPanel1.setLayout(new GridLayout(2,1));
		container2.add(findPanel2);
		container2.add(findPanel3);
		findPanel4.setBorder(BorderFactory.createTitledBorder("����"));
		findPanel2.add(findLabel1);
		findPanel2.add(findText1);
		findPanel1.add(findButton1);
		findPanel1.add(findButton2);
		findPanel2.add(findPanel1);
		findPanel3.add(findCheckBox1);
		findPanel4.add(findRbutton1);
		bg2.add(findRbutton1);
		findRbutton2.setSelected(true);
		findPanel4.add(findRbutton2);
		bg2.add(findRbutton2);
		findPanel3.add(findPanel4);
		container1.setLayout(new FlowLayout(FlowLayout.CENTER));
		enable();
		replaceRbutton2.setSelected(true);
		replacePanel5.setLayout(new GridLayout(2, 1));
		replacePanel2.add(replaceLabel1);
		replacePanel2.add(replaceText1);
		replacePanel2.add(replaceButton1);
		replacePanel5.add(replaceButton3);
		replacePanel5.add(replaceButton4);
		replacePanel3.add(replaceLabel2);
		replacePanel3.add(replaceText2);
		replacePanel3.add(replacePanel5);
		replacePanel4.add(replaceCheckBox1);
		replacePanel4.add(replaceButton2);
		container1.add(replacePanel2);
		container1.add(replacePanel3);
		container1.add(replacePanel4);
		dialog1.setResizable(false);
		dialog1.setSize(580, 250);
		dialog1.setLocation(630,380);
		dialog1.setDefaultCloseOperation(dialog1.HIDE_ON_CLOSE);
		container3.setLayout(new FlowLayout(FlowLayout.LEFT));
		wordLabel1.setPreferredSize(new Dimension(100,20));
		wordLabel2.setPreferredSize(new Dimension(100,20));
		wordLabel3.setPreferredSize(new Dimension(100,20));
		wordText1.setPreferredSize(new Dimension(200,20));
		wordText2.setPreferredSize(new Dimension(200,20));
		wordText3.setPreferredSize(new Dimension(200,20));
		wordPanel1.setPreferredSize(new Dimension(300,150));
		wordText1.setText(font.getFontName());
		wordText1.setEditable(false);
		wordText1.selectAll();
		if(font.getStyle() == Font.PLAIN) {
            wordText2.setText("����");
		}else if(font.getStyle() == Font.BOLD) {
            wordText2.setText("����");  
		}else if(font.getStyle() == Font.ITALIC) {  
            wordText2.setText("��б");  
		}else if(font.getStyle() == (Font.BOLD+Font.ITALIC)) { 
            wordText2.setText("��ƫб��");
		}
		wordText2.setEditable(false);
		wordText2.selectAll();
		wordText3.setText(String.valueOf(font.getSize()));
		wordText3.setEditable(false);
		wordText3.selectAll();
		wordList1.setFixedCellWidth(156);
		wordList1.setFixedCellHeight(20);
		wordList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wordList2.setFixedCellWidth(106);
		wordList2.setFixedCellHeight(20);
		wordList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if(font.getStyle() == Font.PLAIN) {  
            wordList2.setSelectedIndex(0);
		}else if(font.getStyle() == Font.BOLD) { 
            wordList2.setSelectedIndex(1);  
        }else if(font.getStyle() == Font.ITALIC) { 
            wordList2.setSelectedIndex(2); 
        }else if(font.getStyle() == (Font.BOLD+Font.ITALIC)) { 
            wordList2.setSelectedIndex(3);
        }
		wordList3.setFixedCellWidth(43);
		wordList3.setFixedCellHeight(20);
		wordList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wordPanel1.setBorder(BorderFactory.createTitledBorder("ʾ��"));
		wordPanel1.add(wordLabel4);
		wordPanel2.setLayout(new BoxLayout(wordPanel2, BoxLayout.Y_AXIS));
		wordPanel2.add(wordLabel1);
		wordPanel2.add(wordText1);
		wordPanel2.add(new JScrollPane(wordList1));
		wordPanel3.setLayout(new BoxLayout(wordPanel3, BoxLayout.Y_AXIS));
		wordPanel3.add(wordLabel2);
		wordPanel3.add(wordText2);
		wordPanel3.add(new JScrollPane(wordList2));
		wordPanel4.setLayout(new BoxLayout(wordPanel4, BoxLayout.Y_AXIS));
		wordPanel4.add(wordLabel3);
		wordPanel4.add(wordText3);
		wordPanel4.add(new JScrollPane(wordList3));
		wordPanel5.add(wordButton1);
		wordPanel5.add(wordButton2);
		container3.add(wordPanel2);
		container3.add(wordPanel3);
		container3.add(wordPanel4);
		container3.add(wordPanel1);
		container3.add(wordPanel5);
		dialog3.setSize(550,420);  
        dialog3.setLocation(800,300);  
        dialog3.setResizable(false);
        dialog3.setDefaultCloseOperation(dialog3.HIDE_ON_CLOSE);
        container4.setLayout(new BoxLayout(container4, BoxLayout.Y_AXIS));
        toPanel1.setLayout(new BoxLayout(toPanel1, BoxLayout.Y_AXIS));
        toPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toPanel1.add(toLabel1);
        toPanel1.add(toText1);
        toPanel2.add(toButton1);
        toPanel2.add(toButton2);
        container4.add(toPanel1);
        container4.add(toPanel2);
        dialog4.setSize(280,120);  
        dialog4.setLocation(800,300);  
        dialog4.setResizable(false);
        dialog4.setDefaultCloseOperation(dialog4.HIDE_ON_CLOSE);
        jfc1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc1.addChoosableFileFilter(new MyFileFilter(".java","Java�ļ�(*.java)"));
        jfc1.addChoosableFileFilter(new MyFileFilter(".txt","�ı��ĵ�(*.txt)"));
        jfc1.setCurrentDirectory(new File("."));
	    jta.requestFocus();
	    setListener();
		frame.setVisible(true);
	}

	public JMenuItem addMenuItem(String name,int i1,int i2,int i3) {
		JMenuItem jmi = new JMenuItem(name);
		jmi.setMnemonic(i1);
		jmi.setAccelerator(KeyStroke.getKeyStroke(i2, i3));
		return jmi;
	}
	
	public JMenuItem addMenuItem(String name,int i) {
		JMenuItem jmi = new JMenuItem(name);
		jmi.setMnemonic(i);
		return jmi;
	}

	public JCheckBoxMenuItem addCheckBoxMenuItem(String name, int i, boolean b) {
		JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(name);
		cbmi.setMnemonic(i);
		cbmi.setState(b);
		return cbmi;
	}
	
	public JMenu addMenu(String name, int i) {
		JMenu menu = new JMenu(name);
		menu.setMnemonic(i);
		return menu;
	}
	//����Ϊ��������
	//Ϊ�ı��༭���������������������Ҽ��򿪲˵������ò˵��������
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			popup.show(jta, e.getX(), e.getY());
		}
		enable(item21);
		enable(item22);
		enable(item23);
		enable(item24);
		jta.requestFocus();
	}
	//����Ϊ�ı��༭����������
	//�����������
	public void actionPerformed(ActionEvent e) {
		//�½�
		if("�½�(N)".equals(e.getActionCommand())) {
			jta.requestFocus();
			if(!pString.equals(jta.getText())) {
				int a = JOptionPane.showConfirmDialog(frame,"�Ƿ񽫸��ı���","���±�",JOptionPane.YES_NO_OPTION);
				if(a == JOptionPane.YES_OPTION) {
					write(0);
				}
			}
			jta.setText("");
			pString = jta.getText();
		//�˳�
		}else if("�˳�(X)...".equals(e.getActionCommand())) {
			close();
		//��
		}else if("��(O)...".equals(e.getActionCommand())) {
			jta.requestFocus();
			if(!jta.getText().equals(pString)) {
				int a = JOptionPane.showConfirmDialog(frame,"�Ƿ񽫸��ı���","���±�",JOptionPane.YES_NO_OPTION);
				if(a == JOptionPane.YES_OPTION) {
					write(0);
				}
			}
			read();
		//����
		}else if("����(S)".equals(e.getActionCommand())) {
			write(0);
		//���Ϊ
		}else if("���Ϊ(A)...".equals(e.getActionCommand())) {
			write(1);
		//����
		}else if("����(U)".equals(e.getActionCommand())) {
			jta.requestFocus();
			if(um.canUndo()) {
				try {
					um.undo();
				}catch(Exception ee) {
					JOptionPane.showMessageDialog(frame, "����ʧ��!", "",JOptionPane.WARNING_MESSAGE);
				}
			}
			if(!um.canUndo()) {
				item6.setEnabled(false);
				item20.setEnabled(false);
			}
		//����
		}else if("����(T)".equals(e.getActionCommand())) {
			jta.requestFocus();
			try {
				jta.cut();
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(frame, "����ʧ��!", "",JOptionPane.WARNING_MESSAGE);
			}
			enable(item7);
			enable(item21);
		//����
		}else if("����(C)".equals(e.getActionCommand())) {
			jta.requestFocus();
			try {
				jta.copy();
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(frame, "����ʧ��!", "",JOptionPane.WARNING_MESSAGE);
			}
			enable(item8);
			enable(item22);
		//ճ��
		}else if("ճ��(P)".equals(e.getActionCommand())) {
			jta.requestFocus();
			try {
				jta.paste();
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(frame, "ճ��ʧ��!", "",JOptionPane.WARNING_MESSAGE);
			}
			enable(item9);
			enable(item23);
		//ɾ��
		}else if("ɾ��(L)".equals(e.getActionCommand())) {
			jta.requestFocus();
			jta.replaceSelection("");
			enable(item10);
			enable(item24);
		//����
		}else if("����(F)...".equals(e.getActionCommand())) {
			jta.requestFocus();
			findText1.selectAll();
			dialog2.setVisible(true);
			enable();
		//�˵��еĲ�����һ��
		}else if(e.getSource() == item12) {
			jta.requestFocus();
			if(findText1.getText() == null) {
				dialog2.setVisible(true);
			}else {
				find(findCheckBox1,findText1,findRbutton1,findRbutton2,0);
			}
			enable();
		//�˵��е��滻
		}else if(item13 == e.getSource()) {
			jta.requestFocus();
			dialog1.setVisible(true);
			enable();
		//ȫѡ
		}else if("ȫѡ(A)".equals(e.getActionCommand())) {
			jta.selectAll();
		//ת��
		}else if(item14 == e.getSource()) {
			dialog4.setVisible(true);
		//��ʾʱ��
		}else if("ʱ��/����(D)".equals(e.getActionCommand())) {
			jta.requestFocus();
			Calendar c = Calendar.getInstance();
			Date d = c.getTime();
			jta.insert(d.toString(),jta.getCaretPosition());
		//�Զ�����
		}else if("�Զ�����(W)".equals(e.getActionCommand())) {
			if(item17.getState()) {
				jta.setLineWrap(true);
				item19.setEnabled(false);
			}else {
				jta.setLineWrap(false);
				item19.setEnabled(true);
			}
		//��������
		}else if("����(F)...".equals(e.getActionCommand())) {
			jta.requestFocus();
			dialog3.setVisible(true);
		//״̬��
		}else if("״̬��(S)".equals(e.getActionCommand())) {
			if(item19.getState()) {
				label.setVisible(true);
				item17.setEnabled(false);
			}else {
				label.setVisible(false);
				item17.setEnabled(true);
			}
		//���Ҵ����е�ȡ����ť
		}else if(e.getSource() == findButton2) {
			dialog2.setVisible(false);
		//���Ҵ����еĲ�����һ����ť
		}else if(e.getSource() == findButton1) {
			find(findCheckBox1,findText1,findRbutton1,findRbutton2,0);
		//�滻�����е�ȡ����ť
		}else if(replaceButton2 == e.getSource()) {
			dialog1.setVisible(false);
		//�滻�����еĲ�����һ����ť
		}else if(replaceButton1 == e.getSource()) {
			find(replaceCheckBox1,replaceText1,replaceRbutton1,replaceRbutton2,0);
		//�滻�����е��滻��ť
		}else if(replaceButton3 == e.getSource()) {
			if(replaceText2.getText().length() == 0&&jta.getSelectedText() != null) {
				jta.replaceSelection("");
			}else if(replaceText2.getText().length() > 0&&jta.getSelectedText() != null) {
				jta.replaceSelection(replaceText2.getText());
			}
		//�滻�����е��滻ȫ����ť
		}else if(replaceButton4 == e.getSource()) {
			int num = 0;
			jta.setCaretPosition(0);
			while(find(replaceCheckBox1,replaceText1,replaceRbutton1,replaceRbutton2,num)) {
				num++;
				if(replaceText2.getText().length() == 0) {
					jta.replaceSelection("");
				}else {
					jta.replaceSelection(replaceText2.getText());
				}
			}
		//���崰���е�ȡ����ť
		}else if(wordButton2 == e.getSource()) {
			dialog3.setVisible(false);
		//���崰���е�ȷ����ť
		}else if(wordButton1 == e.getSource()) {
			jta.setFont(createFont());
			dialog3.setVisible(false);
		//ת�������е�ȡ����ť
		}else if(toButton2 == e.getSource()) {
			dialog4.setVisible(false);
		//ת�������е�ת����ť
		}else if(toButton1 == e.getSource()) {
			int a = 0;
			try {
				a = Integer.parseInt(toText1.getText());
				try {
					jta.setCaretPosition(jta.getLineStartOffset(a-1));
					dialog4.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "����������������", "���±�-����", JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception ee) {
				JOptionPane.showMessageDialog(frame, "�����ܳ�����������κ��ַ�", "���±�-����", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	//�����������ʵ�����
	//���ÿ�����
	public void enable(JMenuItem jmi) {
		if(jta.getSelectedText()==null&&(jmi.getActionCommand().equals("����(T)")||jmi.getActionCommand().equals("����(C)")||jmi.getActionCommand().equals("ɾ��(L)"))) {
			jmi.setEnabled(false);
		}else if(jta.getSelectedText()!=null&&(jmi.getActionCommand().equals("����(T)")||jmi.getActionCommand().equals("����(C)")||jmi.getActionCommand().equals("ɾ��(L)"))){
			jmi.setEnabled(true);
		}
		Transferable tf = cb.getContents(this);
		if(tf == null&&jmi.getActionCommand().equals("ճ��(P)")) {
			jmi.setEnabled(false);
		}else if(tf != null&&jmi.getActionCommand().equals("ճ��(P)")) {
			jmi.setEnabled(true);
		}
	}
	
	public void enable() {
		if(findText1.getText().length() == 0) {
			findButton1.setEnabled(false);
		}else {
			findButton1.setEnabled(true);
		}
		if(replaceText1.getText().length() == 0) {
			replaceButton1.setEnabled(false);
			replaceButton3.setEnabled(false);
			replaceButton4.setEnabled(false);
		}else {
			replaceButton1.setEnabled(true);
			replaceButton3.setEnabled(true);
			replaceButton4.setEnabled(true);
		}
	}
	//�������������
	//�����Ӽ���
	public void setListener() {
		menu2.addMenuListener(this);
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);
		item4.addActionListener(this);
		item6.addActionListener(this);
		item5.addActionListener(this);
		item7.addActionListener(this);
		item8.addActionListener(this);
		item9.addActionListener(this);
		item10.addActionListener(this);
		item11.addActionListener(this);
		item12.addActionListener(this);
		item13.addActionListener(this);
		item14.addActionListener(this);
		item15.addActionListener(this);
		item16.addActionListener(this);
		item17.addActionListener(this);
		item18.addActionListener(this);
		item19.addActionListener(this);
		item20.addActionListener(this);
		item21.addActionListener(this);
		item22.addActionListener(this);
		item23.addActionListener(this);
		item24.addActionListener(this);
		item25.addActionListener(this);
		item26.addActionListener(this);
		findButton1.addActionListener(this);
		findButton2.addActionListener(this);
		replaceButton1.addActionListener(this);
		replaceButton2.addActionListener(this);
		replaceButton3.addActionListener(this);
		replaceButton4.addActionListener(this);
		wordButton1.addActionListener(this);
		wordButton2.addActionListener(this);
		toButton1.addActionListener(this);
		toButton2.addActionListener(this);
		wordList1.addListSelectionListener(this);
		wordList2.addListSelectionListener(this);
		wordList3.addListSelectionListener(this);
		frame.addWindowListener(this);
		jta.addCaretListener(this);
		findText1.getDocument().addDocumentListener(this);
		jta.getDocument().addDocumentListener(this);
		replaceText1.getDocument().addDocumentListener(this);
	}
	//�������������
	//���ò��ҡ��滻�����а�ť�ͳ����Ŀ�����
	@Override
	public void changedUpdate(DocumentEvent e) {
		if(e.getLength() > 0) {
			enable();
		}
		item6.setEnabled(true);
		item20.setEnabled(true);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if(e.getLength() > 0) {
			enable();
		}
		item6.setEnabled(true);
		item20.setEnabled(true);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(e.getLength() > 0) {
			enable();
		}
		item6.setEnabled(true);
		item20.setEnabled(true);
	}
	//�������������
	//����������
	class MyUndoableEditListener implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent uee) {   
			um.addEdit(uee.getEdit());  
		}  
	}
	//�����ǳ���������
	//���Һ���
	public boolean find(JCheckBox checkbox, JTextField findtext12, JRadioButton rbutton1, JRadioButton rbutton2, int i) {
		int a = -1;
		final String s1,s2;
		if(checkbox.isSelected()) {
			s1 = jta.getText();
			s2 = findtext12.getText();
		}else {
			s1 = jta.getText().toLowerCase();
			s2 = findtext12.getText().toLowerCase();
		}
		if(rbutton1.isSelected()) {
			if(jta.getSelectedText() == null) {
				a = s1.lastIndexOf(s2, jta.getCaretPosition() - 1);
			}else {
				a = s1.lastIndexOf(s2, jta.getSelectionStart() - 1);
			}
		}else if(rbutton2.isSelected()) {
			if(jta.getSelectedText() == null) {
				a = s1.indexOf(s2, jta.getCaretPosition());
			}else {
				a = s1.indexOf(s2, jta.getSelectionEnd());
			}
		}
		if(a > -1) {
			jta.setCaretPosition(a);
			jta.select(a, a + s2.length());
			return true;
		}else {
			if(i == 0) {
				JOptionPane.showMessageDialog(frame, "�Ҳ���\"" + s2 + "\"", "���±�", JOptionPane.INFORMATION_MESSAGE);
			}
			return false;
		}
	}
	//���Һ�������
	//frame��Ӵ��ڼ���
	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	//frame�ر�ʱ����
	public void windowClosing(WindowEvent e) {
		close();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
	//���ڼ���������
	//�༭�˵���ʱ�����˵��������
	@Override
	public void menuCanceled(MenuEvent e) {
	}

	@Override
	public void menuDeselected(MenuEvent e) {
	}

	@Override
	public void menuSelected(MenuEvent e) {
		enable(item7);
		enable(item8);
		enable(item9);
		enable(item10);
	}
	//�����Լ�����
	//�������ú���
	public Font createFont() {
		int a = 0;
		try {
			a = Integer.parseInt(wordText3.getText());
		}catch(Exception e) {
			if(wordText3.getText().equals("����")) {
				a = 42;
			}else if(wordText3.getText().equals("С��")) {
				a = 36;
			}else if(wordText3.getText().equals("һ��")) {
				a = 26;
			}else if(wordText3.getText().equals("Сһ")) {
				a = 24;
			}else if(wordText3.getText().equals("����")) {
				a = 22;
			}else if(wordText3.getText().equals("С��")) {
				a = 18;
			}else if(wordText3.getText().equals("����")) {
				a = 16;
			}else if(wordText3.getText().equals("С��")) {
				a = 15;
			}else if(wordText3.getText().equals("�ĺ�")) {
				a = 14;
			}else if(wordText3.getText().equals("С��")) {
				a = 12;
			}else if(wordText3.getText().equals("���")) {
				a = 11;
			}else if(wordText3.getText().equals("С��")) {
				a = 9;
			}else if(wordText3.getText().equals("����")) {
				a = 8;
			}else if(wordText3.getText().equals("С��")) {
				a = 7;
			}else if(wordText3.getText().equals("�ߺ�")) {
				a = 6;
			}else if(wordText3.getText().equals("�˺�")) {
				a = 5;
			}
		}
		Font font1 = new Font(wordText1.getText(),wordArray[wordList2.getSelectedIndex()],a);
		return font1;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(wordList1 == e.getSource()) {
			wordText1.setText(wordString1[wordList1.getSelectedIndex()]);
			wordLabel4.setFont(createFont());
		}else if(wordList2 == e.getSource()) {
			wordText2.setText(wordString2[wordArray[wordList2.getSelectedIndex()]]);
			wordLabel4.setFont(createFont());
		}else if(wordList3 == e.getSource()) {
			wordText3.setText(wordString3[wordList3.getSelectedIndex()]);
			wordLabel4.setFont(createFont());
		}
	}
	//���庯������
	//���ڹر�ʱ����
	public void close() {
		jta.requestFocus();
		if(jta.getText().equals(pString)) {
			System.exit(0);
		}else {
			int a = JOptionPane.showConfirmDialog(frame,"�Ƿ񽫸��ı���","���±�",JOptionPane.YES_NO_CANCEL_OPTION);
			if(a == JOptionPane.YES_OPTION) {
				write(0);
				System.exit(0);
			}else if(a == JOptionPane.NO_OPTION) {
				System.exit(0);
			}else {
			}
		}
	}
	//�رպ�������
	//д�뺯��
	public void write(int i) {
		if(jta.getText().length() > 10485760) {
			JOptionPane.showMessageDialog(frame, "�ļ�̫��", "���±�-����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(newFile||i == 1) {
			int a = jfc1.showSaveDialog(frame);
			File file = jfc1.getSelectedFile();
			if(a == JFileChooser.APPROVE_OPTION) {
				if(file == null || file.getName().equals("")) {
					
				}else {
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						bw.write(jta.getText());
						bw.close();
						pString = jta.getText();
						newFile = false;
						currentFile = file;
					}catch(Exception e) {
						JOptionPane.showMessageDialog(frame, "�޷������ļ�", "���±�-����", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}else {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
				bw.write(jta.getText());
				bw.close();
				pString = jta.getText();
			}catch(Exception e) {
				JOptionPane.showMessageDialog(frame, "�޷������ļ�", "���±�-����", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	//д�뺯������
	//��ȡ����
	public void read() {
		int a = jfc1.showOpenDialog(frame);
		File file = jfc1.getSelectedFile();
		if(a == JFileChooser.APPROVE_OPTION) {
			if(file == null || file.getName().equals("")) {
				
			}else {
				if(file.length() > 10485760) {
					JOptionPane.showMessageDialog(frame, "�ļ�̫��", "���±�-��", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					BufferedReader br = new BufferedReader(new FileReader(file),5*1024*1024);
					jta.setText("");
					int b;
					char[] ba = new char[1024*1024*5];
					while((b = br.read(ba)) != -1) {
						jta.append(new String(ba,0,b));
					}
					frame.setTitle(file.getName() + " - ���±�");
					br.close();
					newFile = false;
					currentFile = file;
					pString = jta.getText();
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "�޷����ļ�", "���±�-��", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	//��ȡ��������
	//������λ�ú���
	@Override
	public void caretUpdate(CaretEvent arg0) {
		try 
        {
			label.setText("                 ��" + (jta.getLineOfOffset(jta.getCaretPosition()) + 1) + "��,��" + (jta.getCaretPosition() - jta.getLineStartOffset(jta.getLineOfOffset(jta.getCaretPosition())) + 1) + "��"); 
        }catch(Exception ex) {
			label.setText("                 �޷���õ�ǰ���λ�� "); 
		}
	}
	//������λ�ú�������
	//�ļ�ɸѡ
	class MyFileFilter extends FileFilter{
		private String extension;
		private String description;

		public MyFileFilter() {
			super();
			this.extension = null;
			this.description = null;
		}

		public MyFileFilter(String extension, String description) {
			super();
			if(description == null) {
				this.description = new String("�����ļ�(*.*)");
			}else {
				this.description = description;
			}
			if(extension == null) {
				this.extension = null;
			}else {
				this.extension = new String(extension).toLowerCase();
				if(!this.extension.startsWith(".")) {
					this.extension = "." + this.extension;
				}
			}
		}

		@Override
		public boolean accept(File f) {
			if(f.getName().toLowerCase().endsWith(extension.toLowerCase())||f.isDirectory()||extension == null) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}
	//�ļ�ɸѡ����
}