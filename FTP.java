import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.undo.*;
public class FTP{
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Client c = new Client();
			}
		});
	}
}
class Client implements ActionListener{
	JFrame frame = new JFrame("FTPClient");
	JMenuBar mb = new JMenuBar();
	JMenu menu1 = new JMenu("查看(V)");
	JMenu menu2 = new JMenu("传输(T)");
	JMenu menu3 = new JMenu("服务器(S)");
	JMenu m2menu1 = new JMenu("速度限制(S)");
	JCheckBoxMenuItem m1item1 = new JCheckBoxMenuItem("文件列表状态栏(B)");
	JCheckBoxMenuItem m1item2 = new JCheckBoxMenuItem("快速链接工具栏(Q)");
	JCheckBoxMenuItem m1item3 = new JCheckBoxMenuItem("消息日志(M)");
	JCheckBoxMenuItem m1item4 = new JCheckBoxMenuItem("本地目录树(L)");
	JCheckBoxMenuItem m1item5 = new JCheckBoxMenuItem("远程目录(E)");
	JCheckBoxMenuItem m1item6 = new JCheckBoxMenuItem("传输队列(T)");
	JMenuItem m1item7 = new JMenuItem("刷新(R)");
	JMenuItem m2m1item1 = new JMenuItem("启用(E)");
	JMenuItem m2m1item2 = new JMenuItem("配置(C)...");
	JMenuItem m3item1 = new JMenuItem("重新连接(R)");
	JMenuItem m3item2 = new JMenuItem("断开连接(D)");
	JMenuItem m3item3 = new JMenuItem("搜索远程文件(S)...");
	JPanel panel1 = new JPanel();
	JLabel p1label1 = new JLabel("主机(H):");
	JLabel p1label2 = new JLabel("用户名(U):");
	JLabel p1label3 = new JLabel("密码(W):");
	JLabel p1label4 = new JLabel("端口(P):");
	JTextField p1field1 = new JTextField(11);
	JTextField p1field2 = new JTextField(11);
	JPasswordField p1field3 = new JPasswordField(11);
	JTextField p1field4 = new JTextField(3);
	JButton p1button1 = new JButton("快速链接(Q)");
	JPanel panel2 = new JPanel();
	JTextArea p2area = new JTextArea();
	JScrollPane p2scrollarea = new JScrollPane(p2area);
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JPanel panel5 = new JPanel();
	JPanel panel6 = new JPanel();
	JLabel p3label = new JLabel("本地站点:");
	JLabel p4label = new JLabel("远程站点:");
	JTextField p3field = new JTextField(30);
	JTextField p4field = new JTextField(30);
	DefaultMutableTreeNode treeRoot1 = new DefaultMutableTreeNode("此电脑");
	DefaultTreeModel treeModel1 = new DefaultTreeModel(treeRoot1);
	DefaultMutableTreeNode parent;
	JTree tree1 = new JTree(treeModel1);
	JScrollPane scrolltree1 = new JScrollPane(tree1);
	JPanel panel7 = new JPanel();
	JTextField p7field1 = new JTextField(35);
	JTextField p7field2 = new JTextField(35);
	JPanel panel8 = new JPanel();
	JPopupMenu p1m1 = new JPopupMenu();
	JMenuItem m1i1 = new JMenuItem("撤销(U)");
	JMenuItem m1i2 = new JMenuItem("剪切(T)");
	JMenuItem m1i3 = new JMenuItem("复制(C)");
	JMenuItem m1i4 = new JMenuItem("粘贴(P)");
	JMenuItem m1i5 = new JMenuItem("删除(D)");
	JMenuItem m1i6 = new JMenuItem("全选(A)");
	JPopupMenu p1m2 = new JPopupMenu();
	JMenuItem m2i1 = new JMenuItem("撤销(U)");
	JMenuItem m2i2 = new JMenuItem("剪切(T)");
	JMenuItem m2i3 = new JMenuItem("复制(C)");
	JMenuItem m2i4 = new JMenuItem("粘贴(P)");
	JMenuItem m2i5 = new JMenuItem("删除(D)");
	JMenuItem m2i6 = new JMenuItem("全选(A)");
	JPopupMenu p1m3 = new JPopupMenu();
	JMenuItem m3i1 = new JMenuItem("撤销(U)");
	JMenuItem m3i2 = new JMenuItem("剪切(T)");
	JMenuItem m3i3 = new JMenuItem("复制(C)");
	JMenuItem m3i4 = new JMenuItem("粘贴(P)");
	JMenuItem m3i5 = new JMenuItem("删除(D)");
	JMenuItem m3i6 = new JMenuItem("全选(A)");
	Toolkit tk = Toolkit.getDefaultToolkit();
	Clipboard cb = tk.getSystemClipboard();
	JPopupMenu p2m1 = new JPopupMenu();
	JMenuItem p2m1i1 = new JMenuItem("复制到剪切板(C)");
	JMenuItem p2m1i2 = new JMenuItem("全部清除(C)");
	JPopupMenu treem = new JPopupMenu();
	JMenuItem ti1 = new JMenuItem("上传(U)");
	JMenuItem ti2 = new JMenuItem("添加文件到队列(A)");
	JMenuItem ti3 = new JMenuItem("打开(O)");
	JMenuItem ti4 = new JMenuItem("创建目录(C)");
	JMenuItem ti5 = new JMenuItem("删除(D)");
	JMenuItem ti6 = new JMenuItem("重命名(R)");
	JPanel panel9 = new JPanel();
	DefaultMutableTreeNode treeRoot2;
	DefaultTreeModel treeModel2;
	JTree tree2;
	JScrollPane scrolltree2;
	JPopupMenu tree2m = new JPopupMenu();
	JMenuItem tr1 = new JMenuItem("下载(D)");
	JMenuItem tr2 = new JMenuItem("添加文件到队列(A)");
	JMenuItem tr3 = new JMenuItem("创建目录(C)");
	JMenuItem tr4 = new JMenuItem("删除(D)");
	JMenuItem tr5 = new JMenuItem("重命名(R)");
	String[] row = { "服务器/本地文件", "方向", "远程文件","大小","进度","百分比" };
	Object[][] list = { {} };
	DefaultTableModel tableModel = new DefaultTableModel(list, row);
	JTable table = new JTable(tableModel);
	JScrollPane scrollTable = new JScrollPane(table);
	JPopupMenu tablem = new JPopupMenu();
	JMenuItem ta1 = new JMenuItem("处理队列(Q)");
	JMenuItem ta2 = new JMenuItem("停止并删除所有(A)");
	JMenuItem ta3 = new JMenuItem("移除选定(R)");
	UndoManager um1 = new UndoManager();
	UndoManager um2 = new UndoManager();
	UndoManager um3 = new UndoManager();
	UndoableEditListener ue1 = new MyUndoableEditListener1();
	UndoableEditListener ue2 = new MyUndoableEditListener2();
	UndoableEditListener ue3 = new MyUndoableEditListener3();
	JDialog dialog = new JDialog(frame,"速度限制",true);
	Container container = dialog.getContentPane();
	JCheckBox dcb = new JCheckBox("启用速度限制(E)");
	JPanel dp1 = new JPanel();
	JLabel dl1 = new JLabel("下载限制(L):");
	JLabel dl2 = new JLabel("(以 KiB/s)");
	JPanel dp2 = new JPanel();
	JLabel dl3 = new JLabel("上传限制(P):");
	JLabel dl4 = new JLabel("(以 KiB/s)");
	JLabel dl5 = new JLabel("输入 0 表示无速度限制");
	JTextField df1 = new JTextField(5);
	JTextField df2 = new JTextField(5);
	JPanel dp3 = new JPanel();
	JButton db1 = new JButton("确定");
	JButton db2 = new JButton("取消(C)");
	File oldfile;
	File newfile;
	String oldname;
	String newname;
	Socket socket;
	BufferedReader reader;  
    BufferedWriter writer;
    String oldhost;
    String olduname;
    char[] oldpwd;
    int oldport;
    String size;
    String oldpath;
	public Client() {
		frame.setSize(900,800);
		frame.setLocation(300, 20);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setJMenuBar(mb);
		frame.setLayout(null);
		frame.setResizable(false);
		menu1.setMnemonic('V');
		m1item1.setMnemonic('B');
		m1item1.setSelected(true);
		m1item1.addActionListener(this);
		m1item2.setMnemonic('Q');
		m1item2.setSelected(true);
		m1item2.addActionListener(this);
		m1item3.setMnemonic('M');
		m1item3.setSelected(true);
		m1item3.addActionListener(this);
		m1item4.setMnemonic('L');
		m1item4.setSelected(true);
		m1item4.addActionListener(this);
		m1item5.setMnemonic('E');
		m1item5.setSelected(true);
		m1item5.addActionListener(this);
		m1item6.setMnemonic('T');
		m1item6.setSelected(true);
		m1item6.addActionListener(this);
		m1item7.setMnemonic('R');
		m1item7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
		m1item7.addActionListener(this);
		menu1.add(m1item7);
		menu1.addSeparator();
		menu1.add(m1item1);
		menu1.add(m1item2);
		menu1.add(m1item3);
		menu1.add(m1item4);
		menu1.add(m1item5);
		menu1.add(m1item6);
		menu2.setMnemonic('T');
		m2menu1.setMnemonic('S');
		m2m1item1.setMnemonic('E');
		m2m1item2.setMnemonic('C');
		m2menu1.add(m2m1item1);
		m2m1item1.addActionListener(this);
		m2menu1.add(m2m1item2);
		m2m1item2.addActionListener(this);
		menu2.add(m2menu1);
		menu3.setMnemonic('S');
		m3item1.setMnemonic('R');
		m3item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
		m3item2.setMnemonic('D');
		m3item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,KeyEvent.CTRL_MASK));
		m3item3.setMnemonic('S');
		m3item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
		menu3.add(m3item1);
		m3item1.addActionListener(this);
		menu3.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				if(socket == null) {
					m3item1.setEnabled(false);
				}else {
					m3item1.setEnabled(true);
				}
			}
			@Override
			public void menuDeselected(MenuEvent e) {
			}
			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		menu3.add(m3item2);
		m3item2.addActionListener(this);
		menu3.addSeparator();
		menu3.add(m3item3);
		m3item3.addActionListener(this);
		mb.add(menu1);
		mb.add(menu2);
		mb.add(menu3);
		panel1.add(p1label1);
		panel1.add(p1field1);
		panel1.add(p1label2);
		panel1.add(p1field2);
		panel1.add(p1label3);
		panel1.add(p1field3);
		panel1.add(p1label4);
		panel1.add(p1field4);
		panel1.add(p1button1);
		p1button1.addActionListener(this);
		panel1.setBounds(0, 0, 900, 40);
		frame.add(panel1);
		p2area.setEditable(false);
		p2scrollarea.setPreferredSize(new Dimension(800,100));
		panel2.add(p2scrollarea);
		panel2.setBounds(0, 40, 900, 105);
		frame.add(panel2);
		scrolltree1.setPreferredSize(new Dimension(400, 300));
		for(char i = 'c'; i < 'z'; i++) {
			String path = i + ":/";
			if(((new File(path)).exists())) {
				DefaultMutableTreeNode diskNode = new DefaultMutableTreeNode(new MyFile(new File(path)));
				treeRoot1.add(diskNode);
			}
		}
		tree1.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = tree1.getSelectionPath();
					if(path != null) {
						DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
						if(!selectnode.isLeaf()) {
							if(!(selectnode.getUserObject() instanceof MyFile)) {
								return;
							}
						}
						String[] ss = path.toString().split(", ");
						String s = "";
						for(int i = 1;i<ss.length;i++) {
							s += ss[i];
							if(!s.endsWith("\\")) {
								s+='\\';
							}
						}
						s =s.replace("]", "");
						File fileselect = new File(s);
						p3field.setText(fileselect.getAbsolutePath());
						p7field1.setText("该文件大小为: "+fileselect.length()+"字节");
						readfiles(fileselect, selectnode);
						DefaultMutableTreeNode firstchild = (DefaultMutableTreeNode) selectnode.getFirstChild();
						selectnode.remove(firstchild);
					}
				}catch(Exception ee) {
				}
			}
		});
		treeRoot2 = new DefaultMutableTreeNode("/");
		treeModel2 = new DefaultTreeModel(treeRoot2);
		tree2 = new JTree(treeModel2);
		scrolltree2 = new JScrollPane(tree2);
		scrolltree2.setPreferredSize(new Dimension(400, 300));
		tree2.setEditable(true);
		tree2.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				try {
					TreePath path = tree2.getSelectionPath();
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					String[] ss = path.toString().split(", ");
					String s = "/";
					for(int i = 1;i<ss.length;i++) {
						s += ss[i];
						if(!s.endsWith("/")) {
							s+='/';
						}
					}
					s = s.replace("]", "");
					p4field.setText(s);
					if(!selectnode.isLeaf()) {
						p7field2.setText("该文件大小为: 0字节");
						return;
					}
					ftpw w = new ftpw();
					w.setCom("readftp");
					w.setServerPath(s);
					w.setNode(selectnode);
					w.execute();
				}catch(Exception ee) {
				}
			}
		});
		tree2.add(tree2m);
		tree2.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					tree2m.show(tree2, e.getX(), e.getY());
					File file = new File(p3field.getText());
					if(!file.exists()) {
						tr1.setEnabled(false);
						tr2.setEnabled(false);
					}else {
						tr1.setEnabled(true);
						tr2.setEnabled(true);
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		treeModel2.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
			}
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
			}
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
			}
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				try {
					TreePath path = e.getTreePath();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					int[] index = e.getChildIndices();
					node = (DefaultMutableTreeNode) node.getChildAt(index[0]);
					String[] ss = path.toString().split(", ");
					String s = "";
					for(int i=1;i<ss.length;i++) {
						s += ss[i];
						s = s.replace("]", "");
						if(!s.endsWith("/")) {
							s+='/';
						}
					}
					newname = s + (String) node.getUserObject();
					ftpw w = new ftpw();
					w.setCom("renamef");
					w.setFilepath(newname);
					w.setServerPath(oldpath);
					w.execute();
				}catch(Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		panel9.add(scrolltree2);
		panel3.add(p3label);
		panel3.add(p3field);
		panel3.setBounds(50, 150, 400, 30);
		frame.add(panel3);
		panel4.add(p4label);
		panel4.add(p4field);
		panel4.setBounds(450, 150, 400, 30);
		frame.add(panel4);
		panel5.add(scrolltree1);
		panel5.setBounds(50, 180, 400, 305);
		frame.add(panel5);
		panel9.setBounds(450,180,400,305);
		frame.add(panel9);
		p7field1.setEditable(false);
		p7field1.setBackground(Color.white);
		panel7.add(p7field1);
		p7field2.setEditable(false);
		p7field2.setBackground(Color.white);
		panel7.add(p7field2);
		panel7.setBounds(0, 480, 900, 30);
		frame.add(panel7);
		scrollTable.setPreferredSize(new Dimension(800,210));
		panel8.add(scrollTable);
		panel8.setBounds(0, 510, 900, 220);
		frame.add(panel8);
		p1m1.add(m1i1);
		m1i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		m1i1.setEnabled(false);
		m1i1.addActionListener(this);
		p1m1.addSeparator();
		p1m1.add(m1i2);
		m1i2.addActionListener(this);
		m1i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		p1m1.add(m1i3);
		m1i3.addActionListener(this);
		m1i3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		p1m1.add(m1i4);
		m1i4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		m1i4.addActionListener(this);
		p1m1.add(m1i5);
		m1i5.addActionListener(this);
		p1m1.addSeparator();
		p1m1.add(m1i6);
		m1i6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		m1i6.addActionListener(this);
		p1field1.add(p1m1);
		p1field1.getDocument().addUndoableEditListener(ue1);
		p1field1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				m1i1.setEnabled(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				m1i1.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				m1i1.setEnabled(true);
			}
		});
		p1field1.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					p1m1.show(p1field1, e.getX(), e.getY());
				}
				if(p1field1.getSelectedText()==null) {
					m1i2.setEnabled(false);
					m1i3.setEnabled(false);
					m1i5.setEnabled(false);
				}else {
					m1i2.setEnabled(true);
					m1i3.setEnabled(true);
					m1i5.setEnabled(true);
				}
				Transferable tf = cb.getContents(this);
				if(tf==null) {
					m1i4.setEnabled(false);
				}else {
					m1i4.setEnabled(true);
				}
				p1field1.requestFocus();
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		p1m2.add(m2i1);
		m2i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		m2i1.addActionListener(this);
		m2i1.setEnabled(false);
		p1m2.addSeparator();
		p1m2.add(m2i2);
		m2i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		m2i2.addActionListener(this);
		p1m2.add(m2i3);
		m2i3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		m2i3.addActionListener(this);
		p1m2.add(m2i4);
		m2i4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		m2i4.addActionListener(this);
		p1m2.add(m2i5);
		m2i5.addActionListener(this);
		p1m2.addSeparator();
		p1m2.add(m2i6);
		m2i6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		m2i6.addActionListener(this);
		p1field2.add(p1m2);
		p1field2.getDocument().addUndoableEditListener(ue2);
		p1field2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				m2i1.setEnabled(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				m2i1.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				m2i1.setEnabled(true);
			}
		});
		p1field2.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					p1m2.show(p1field2, e.getX(), e.getY());
				}
				if(p1field2.getSelectedText()==null) {
					m2i2.setEnabled(false);
					m2i3.setEnabled(false);
					m2i5.setEnabled(false);
				}else {
					m2i2.setEnabled(true);
					m2i3.setEnabled(true);
					m2i5.setEnabled(true);
				}
				Transferable tf = cb.getContents(this);
				if(tf==null) {
					m2i4.setEnabled(false);
				}else {
					m2i4.setEnabled(true);
				}
				p1field2.requestFocus();
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		p1m3.add(m3i1);
		m3i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
		m3i1.addActionListener(this);
		m3i1.setEnabled(false);
		p1m3.addSeparator();
		p1m3.add(m3i2);
		m3i2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		m3i2.addActionListener(this);
		p1m3.add(m3i3);
		m3i3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		m3i3.addActionListener(this);
		p1m3.add(m3i4);
		m3i4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
		m3i4.addActionListener(this);
		p1m3.add(m3i5);
		m3i5.addActionListener(this);
		p1m3.addSeparator();
		p1m3.add(m3i6);
		m3i6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		m3i6.addActionListener(this);
		p1field3.add(p1m3);
		p1field3.getDocument().addUndoableEditListener(ue3);
		p1field3.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				m3i1.setEnabled(true);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				m3i1.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				m3i1.setEnabled(true);
			}
		});
		p1field3.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					p1m3.show(p1field3, e.getX(), e.getY());
				}
				if(p1field3.getSelectedText()==null) {
					m3i2.setEnabled(false);
					m3i3.setEnabled(false);
					m3i5.setEnabled(false);
				}else {
					m3i2.setEnabled(true);
					m3i3.setEnabled(true);
					m3i5.setEnabled(true);
				}
				Transferable tf = cb.getContents(this);
				if(tf==null) {
					m3i4.setEnabled(false);
				}else {
					m3i4.setEnabled(true);
				}
				p1field3.requestFocus();
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		p2m1.add(p2m1i1);
		p2m1i1.addActionListener(this);
		p2m1.add(p2m1i2);
		p2m1i2.addActionListener(this);
		p2area.add(p2m1);
		p2area.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					p2m1.show(p2area, e.getX(), e.getY());
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		dialog.setResizable(false);
		dialog.setSize(300, 200);
		dialog.setLocation(630, 380);
		dialog.setDefaultCloseOperation(dialog.HIDE_ON_CLOSE);
		container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		container.add(dcb);
		dcb.addActionListener(this);
		dp1.add(dl1);
		df1.setEditable(false);
		df1.setText("1000");
		dp1.add(df1);
		dp1.add(dl2);
		container.add(dp1);
		dp2.add(dl3);
		df2.setEditable(false);
		df2.setText("100");
		dp2.add(df2);
		dp2.add(dl4);
		container.add(dp2);
		container.add(dl5);
		dp3.add(db1);
		db1.addActionListener(this);
		dp3.add(db2);
		db2.addActionListener(this);
		container.add(dp3);
		treeModel1.addTreeModelListener(new TreeModelListener() {
			@Override
			public void treeStructureChanged(TreeModelEvent e) {
			}
			@Override
			public void treeNodesRemoved(TreeModelEvent e) {
			}
			@Override
			public void treeNodesInserted(TreeModelEvent e) {
			}
			@Override
			public void treeNodesChanged(TreeModelEvent e) {
				try {
					TreePath path = e.getTreePath();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					int[] index = e.getChildIndices();
					node = (DefaultMutableTreeNode) node.getChildAt(index[0]);
					String[] ss = path.toString().split(", ");
					String s = "";
					for(int i=1;i<ss.length;i++) {
						s += ss[i];
						if(!s.endsWith("\\")) {
							s+='\\';
						}
					}
					s = s.replace("]", "");
					newfile = new File(s + (String) node.getUserObject());
					if(newfile.exists()) {
						node.setUserObject(oldfile.getName());
						JOptionPane.showMessageDialog(frame, newfile.getName() + "已经存在", "", JOptionPane.INFORMATION_MESSAGE);
					}else {
						oldfile.renameTo(newfile);
					}
				}catch(Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		tree1.setEditable(true);
		treem.add(ti1);
		ti1.addActionListener(this);
		treem.add(ti2);
		ti2.addActionListener(this);
		treem.addSeparator();
		treem.add(ti3);
		ti3.addActionListener(this);
		treem.addSeparator();
		treem.add(ti4);
		ti4.addActionListener(this);
		treem.addSeparator();
		treem.add(ti5);
		ti5.addActionListener(this);
		treem.add(ti6);
		ti6.addActionListener(this);
		tree1.add(treem);
		tree1.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					treem.show(tree1, e.getX(), e.getY());
					if(p4field.getText().length()==0) {
						ti1.setEnabled(false);
						ti2.setEnabled(false);
					}else {
						ti1.setEnabled(true);
						ti2.setEnabled(true);
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		tablem.add(ta1);
		ta1.addActionListener(this);
		tablem.add(ta2);
		ta2.addActionListener(this);
		tablem.addSeparator();
		tablem.add(ta3);
		ta3.addActionListener(this);
		table.add(tablem);
		tableModel.setRowCount(0);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					tablem.show(table, e.getX(), e.getY());
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tree2m.add(tr1);
		tr1.addActionListener(this);
		tree2m.add(tr2);
		tr2.addActionListener(this);
		tree2m.addSeparator();
		tree2m.add(tr3);
		tr3.addActionListener(this);
		tree2m.addSeparator();
		tree2m.add(tr4);
		tr4.addActionListener(this);
		tree2m.add(tr5);
		tr5.addActionListener(this);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == m1item2) {
			if(m1item2.isSelected()) {
				panel1.setVisible(true);
			}else {
				panel1.setVisible(false);
			}
		}else if(e.getSource() == m1item3) {
			if(m1item3.isSelected()) {
				panel2.setVisible(true);
			}else {
				panel2.setVisible(false);
			}
		}else if(e.getSource() == m1item4) {
			if(m1item4.isSelected()) {
				panel5.setVisible(true);
			}else {
				panel5.setVisible(false);
			}
		}else if(e.getSource() == m1item1) {
			if(m1item1.isSelected()) {
				panel7.setVisible(true);
			}else {
				panel7.setVisible(false);
			}
		}else if(e.getSource() == m1item6) {
			if(m1item6.isSelected()) {
				panel8.setVisible(true);
			}else {
				panel8.setVisible(false);
			}
		}else if(e.getSource() == m1i1) {
			p1field1.requestFocus();
			if(um1.canUndo()) {
				try {
					um1.undo();
				}catch(Exception ee) {
				}
			}
			if(!um1.canUndo()) {
				m1i1.setEnabled(false);
			}
		}else if(e.getSource() == m2i1) {
			p1field2.requestFocus();
			if(um2.canUndo()) {
				try {
					um2.undo();
				}catch(Exception ee) {
				}
			}
			if(!um2.canUndo()) {
				m2i1.setEnabled(false);
			}
		}else if(e.getSource() == m3i1) {
			p1field3.requestFocus();
			if(um3.canUndo()) {
				try {
					um3.undo();
				}catch(Exception ee) {
				}
			}
			if(!um3.canUndo()) {
				m3i1.setEnabled(false);
			}
		}else if(e.getSource() == m1i2) {
			p1field1.requestFocus();
			try {
				p1field1.cut();
			}catch(Exception ee) {
			}
			if(p1field1.getSelectedText() == null) {
				m1i2.setEnabled(false);
			}
		}else if(e.getSource() == m2i2) {
			p1field2.requestFocus();
			try {
				p1field2.cut();
			}catch(Exception ee) {
			}
			if(p1field2.getSelectedText() == null) {
				m2i2.setEnabled(false);
			}
		}else if(e.getSource() == m3i2) {
			p1field3.requestFocus();
			try {
				p1field3.cut();
			}catch(Exception ee) {
			}
			if(p1field3.getSelectedText() == null) {
				m3i2.setEnabled(false);
			}
		}else if(e.getSource() == m1i3) {
			p1field1.requestFocus();
			try {
				p1field1.copy();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m2i3) {
			p1field2.requestFocus();
			try {
				p1field2.copy();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m3i3) {
			p1field3.requestFocus();
			try {
				p1field3.copy();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m1i4) {
			p1field1.requestFocus();
			try {
				p1field1.paste();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m2i4) {
			p1field2.requestFocus();
			try {
				p1field2.paste();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m3i4) {
			p1field3.requestFocus();
			try {
				p1field3.paste();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m1i5) {
			p1field1.requestFocus();
			p1field1.replaceSelection("");
		}else if(e.getSource() == m2i5) {
			p1field2.requestFocus();
			p1field2.replaceSelection("");
		}else if(e.getSource() == m3i5) {
			p1field3.requestFocus();
			p1field3.replaceSelection("");
		}else if(e.getSource() == m1i6) {
			p1field1.selectAll();
		}else if(e.getSource() == m2i6) {
			p1field2.selectAll();
		}else if(e.getSource() == m3i6) {
			p1field3.selectAll();
		}else if(e.getSource() == p2m1i1) {
			p2area.selectAll();
			try {
				p2area.copy();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == p2m1i2) {
			p2area.selectAll();
			p2area.replaceSelection("");
		}else if(e.getSource() == m2m1item2) {
			dialog.setVisible(true);
		}else if(e.getSource() == dcb) {
			if(dcb.isSelected()) {
				df1.setEditable(true);
				df2.setEditable(true);
			}else {
				df1.setEditable(false);
				df2.setEditable(false);
			}
		}else if(e.getSource() == db1) {
			dialog.setVisible(false);
		}else if(e.getSource() == db2) {
			dialog.setVisible(false);
		}else if(e.getSource() == m2m1item1) {
			dcb.setSelected(true);
			df1.setEditable(true);
			df2.setEditable(true);
		}else if(e.getSource() == ti1) {
			ftpw w = new ftpw();
			w.setCom("upload");
			w.setFilepath(p3field.getText());
			w.setServerPath(p4field.getText());
			w.execute();
		}else if(e.getSource() == ti2) {
			try {
				File fileselect = new File(p3field.getText());
				String sizeFile = size(fileselect);
				Object re[][] = {{p3field.getText(),"-->",p4field.getText(),sizeFile,"",""}};
				list = re;
				tableModel.addRow(list[0]);
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == ti3) {
			try {
				TreePath path = tree1.getSelectionPath();
				if(path !=null) {
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					String[] ss = path.toString().split(", ");
					String s = "";
					for(int i = 1;i<ss.length;i++) {
						s += ss[i];
						if(!s.endsWith("\\")) {
							s+='\\';
						}
					}
					s =s.replace("]", "");
					File fileselect = new File(s);
					Desktop.getDesktop().open(fileselect);
				}
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == ti4) {
			try {
				TreePath path = tree1.getSelectionPath();
				if(path !=null) {
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					String[] ss = path.toString().split(", ");
					String s = "";
					for(int i = 1;i<ss.length;i++) {
						s += ss[i];
						if(!s.endsWith("\\")) {
							s+='\\';
						}
					}
					s =s.replace("]", "");
					File fileselect = new File(s);
					File file;
					if(fileselect.getAbsolutePath().endsWith("\\")) {
						file = new File(fileselect.getAbsolutePath()+"新建文件夹");
					}else {
						file = new File(fileselect.getAbsolutePath()+"\\新建文件夹");
					}
					if(file.mkdir()) {
						parent = new DefaultMutableTreeNode(new MyFile(file));
						File stubadd = null;
						DefaultMutableTreeNode stub = new DefaultMutableTreeNode(stubadd);
						parent.add(stub);
						treeModel1.insertNodeInto(parent, selectnode, 0);
					}else {
						JOptionPane.showMessageDialog(frame, "新建文件夹已经存在", "", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == ti5) {
			try {
				TreePath path = tree1.getSelectionPath();
				if(path !=null) {
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					String[] ss = path.toString().split(", ");
					String s = "";
					for(int i = 1;i<ss.length;i++) {
						s += ss[i];
						if(!s.endsWith("\\")) {
							s+='\\';
						}
					}
					s =s.replace("]", "");
					File fileselect = new File(s);
					dfile(fileselect);
					Enumeration en = selectnode.children();
					while(en.hasMoreElements()) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)en.nextElement();
						treeModel1.removeNodeFromParent(node);
					}
					treeModel1.removeNodeFromParent(selectnode);
				}
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == ti6) {
			try {
				TreePath path = tree1.getSelectionPath();
				if(path != null) {
					oldfile = new File(p3field.getText());
					tree1.startEditingAtPath(path);
				}
			}catch(Exception ee) {
			}
		}else if(e.getSource() == ta1) {
			
		}else if(e.getSource() == ta2) {
			tableModel.setRowCount(0);
		}else if(e.getSource() == ta3) {
			int numrow = table.getSelectedRows().length;
			for(int i=0;i < numrow;i++) {
				tableModel.removeRow(table.getSelectedRow());
			}
		}else if(e.getSource() == p1button1) {
			ftpw w = new ftpw();
			w.setCom("connect");
			w.execute();
		}else if(e.getSource() == m3item2) {
			ftpw w = new ftpw();
			w.setCom("quit");
			w.execute();
		}else if(e.getSource() == m3item1) {
			p1field1.setText(oldhost);
			p1field2.setText(olduname);
			String s = "";
			for(char c:oldpwd) {
				s+=c;
			}
			p1field3.setText(s);
			p1field4.setText(Integer.toString(oldport));
			ftpw w = new ftpw();
			w.setCom("connect");
			w.execute();
		}else if(e.getSource() == tr1) {
			ftpw w = new ftpw();
			w.setCom("download");
			w.setServerPath(p4field.getText());
			w.setFilepath(p3field.getText());
			w.execute();
		}else if(e.getSource() == tr2) {
			try {
				Object re[][] = {{p3field.getText(),"<--",p4field.getText(),size,"",""}};
				list = re;
				tableModel.addRow(list[0]);
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == tr3) {
			try {
				TreePath path = tree2.getSelectionPath();
				DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
				String s = p4field.getText();
				if(selectnode.isLeaf()) {
					String[] ss = s.split("/");
					s = "";
					for(int i = 0;i<ss.length-1;i++) {
						s+=ss[i] + "/";
					}
				}
				ftpw w = new ftpw();
				w.setCom("mkdir");
				w.setServerPath(s);
				w.execute();
			}catch(Exception ee) {
				ee.printStackTrace();
			}
		}else if(e.getSource() == tr4) {
			try {
				ftpw w = new ftpw();
				w.setCom("deletef");
				w.setServerPath(p4field.getText());
				w.execute();
			}catch(Exception ee) {
			}
		}else if(e.getSource() == tr5) {
			try {
				TreePath path = tree2.getSelectionPath();
				if(path != null) {
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					oldname = (String)selectnode.getUserObject();
					String[] ss = p4field.getText().split("/");
					oldpath="";
					for(int i=0;i<ss.length;i++) {
						oldpath += ss[i] + "/";
					}
					tree2.startEditingAtPath(path);
				}
			}catch(Exception ee) {
			}
		}else if(e.getSource() == m1item5) {
			if(m1item5.isSelected()) {
				scrolltree2.setVisible(true);
			}else {
				scrolltree2.setVisible(false);
			}
		}else if(e.getSource() == m1item7) {
			
			ftpw w = new ftpw();
			w.setCom("connect");
			w.execute();
		}
	}
	public void readfiles(File file, DefaultMutableTreeNode node) {
		File[] list = file.listFiles();
		if(list != null) {
			for(int i = 0; i < list.length; i++) {
				File fileinlist = list[i];
				if(fileinlist.isDirectory()) {
					parent = new DefaultMutableTreeNode(new MyFile(fileinlist));
					File stubadd = null;
					DefaultMutableTreeNode stub = new DefaultMutableTreeNode(stubadd);
					parent.add(stub);
					node.add(parent);
				}else {
					DefaultMutableTreeNode son = new DefaultMutableTreeNode(new MyFile(fileinlist));
					node.add(son);
				}
			}
		}
	}
	class ftpw extends SwingWorker<Object, Object>{
		String com;
		String serverPath;
		DefaultMutableTreeNode node;
		String filepath;
		public void setServerPath(String serverPath) {
			this.serverPath = serverPath;
		}
		public void setNode(DefaultMutableTreeNode node) {
			this.node = node;
		}
		public void setCom(String com) {
			this.com = com;
		}
		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}
		@Override
		protected Object doInBackground() throws Exception {
			if(com.equals("connect")) {
				connect();
			}else if(com.equals("readftp")) {
				readftp();
			}else if(com.equals("quit")) {
				quit();
			}else if(com.equals("download")) {
				download();
			}else if(com.equals("upload")) {
				upload();
			}else if(com.equals("renamef")) {
				renamef();
			}else if(com.equals("deletef")) {
				deletef();
			}else if(com.equals("mkdir")) {
				mkdir();
			}
			return null;
		}
		public void connect() {
			try {
				scrolltree2.requestFocus();
				treeRoot2.removeAllChildren();
				treeModel2.reload();
				if(p1field4.getText().length() == 0) {
					p1field4.setText("21");
				}
				if(p1field2.getText().length() == 0) {
					p1field2.setText("anonymous");
				}
				if(p1field1.getText().startsWith("ftp://")) {
					p1field1.setText(p1field1.getText().replace("ftp://", ""));
				}
				if(p1field1.getText().length()==0) {
					p2area.append("没有连接客户端\n");
				}else {
					socket = new Socket(p1field1.getText(), Integer.parseInt(p1field4.getText()));
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
					writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					reader.readLine();
					p2area.append("正在连接ftp服务器\n");
					sendCommand("USER " + p1field2.getText());
					p2area.append(reader.readLine() + '\n');
					String s="";
					for(char c:p1field3.getPassword()) {
						s += c;
					}
					sendCommand("PASS " + s);
					p2area.append(reader.readLine() + '\n');
					sendCommand("PASV");
					p2area.append(reader.readLine() + '\n');
					oldhost = p1field1.getText();
					olduname = p1field2.getText();
					oldpwd = p1field3.getPassword();
					oldport = Integer.parseInt(p1field4.getText());
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		public void readftp() {
			try {
				writer.write("CWD " + "/" + "\r\n");
				writer.flush();
				if(reader.readLine().startsWith("226")) {
					reader.readLine();
				}
				writer.write("CWD " + serverPath + "\r\n");
				writer.flush();
				sendCommand("PASV");
				String response = reader.readLine();
				while(!response.startsWith("227")) {
					if(response.startsWith("550")) {
						sendCommand("SIZE " + serverPath);
						reader.readLine();
						String s=reader.readLine();
						if(!s.startsWith("213")) {
							p7field2.setText("无法获取文件大小");
						}else {
							String[] ss = s.split(" ");
							size = ss[1];
							p7field2.setText("该文件大小为: " + ss[1] + "字节");
						}
						return;
					}
					p2area.append(response + "\n");
					response = reader.readLine();
				}
				p7field2.setText("该文件大小为: 0字节");
				p2area.append(response + "\n");
				String ip = null;
				int port1 = -1;
				int opening = response.indexOf('(');
				int closing = response.indexOf(')', opening + 1);
				if (closing > 0) {
					String dataLink = response.substring(opening + 1, closing);
					StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
					ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."  + tokenizer.nextToken() + "." + tokenizer.nextToken();
					port1 = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());
				}
				Socket dataSocket = new Socket(ip, port1);
				writer.write("NLST " + "\r\n");
				writer.flush();
				DataInputStream dis = new DataInputStream(dataSocket.getInputStream());
				String s = "";
				while ((s = dis.readLine()) != null) {
					String l = new String(s.getBytes("ISO-8859-1"), "gbk");
					DefaultMutableTreeNode parent2 = new DefaultMutableTreeNode(l);
					node.add(parent2);
				}
				dis.close();
				dataSocket.close();
				reader.readLine();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void quit() {
			try {
				sendCommand("QUIT");
				socket.close();
				writer.close();
				reader.close();
				p2area.append("    连接已断开\n");
			}catch(Exception e) {
				e.printStackTrace();
				p2area.append("    连接断开失败\n");
			}
		}
		public void download() {
			try {
				File file = new File(filepath);
				if(file.isFile()) {
					file = file.getParentFile();
				}
				String[] ss = p4field.getText().split("/");
				file = new File(file.getAbsolutePath() + "\\" + ss[ss.length - 1]);
				if(file.exists()) {
					JOptionPane.showMessageDialog(frame, "文件已经存在", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				writer.write("CWD " + "/" + "\r\n");
				writer.flush();
				if(reader.readLine().startsWith("226")) {
					reader.readLine();
				}
				sendCommand("PASV");
				String response = reader.readLine();
				while(!response.startsWith("227")) {
					response = reader.readLine();  
				}
				p2area.append(response + "\n");
				String ip = null;
				int port = -1;
				int opening = response.indexOf('(');
				int closing = response.indexOf(')', opening + 1);
				if (closing > 0) {
					String dataLink = response.substring(opening + 1, closing);
					StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
					ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."  + tokenizer.nextToken() + "." + tokenizer.nextToken();
					port = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());
				}
				Socket dataSocket = new Socket(ip, port);
				sendCommand("RETR " + serverPath);
				BufferedInputStream bis = new BufferedInputStream(dataSocket.getInputStream());
				Object re[][] = {{p3field.getText(),"<--",p4field.getText(),size,"",""}};
				list = re;
				tableModel.addRow(list[0]);
				byte[] buffer = new byte[1024*8];
				int bytes = 0;
				int i = 0;
				for(i = 0; i<table.getRowCount();i++) {
					String s = (String)table.getValueAt(i, 4);
					if(s.length()==0) {
						break;
					}
				}
				while((bytes = bis.read(buffer))!=-1) {
					bos.write(buffer, 0, bytes);
					tableModel.setValueAt(file.length() + "/" + size, i, 4);
					BigInteger file1 = new BigInteger(file.length() + "");
					BigInteger ftp1 = new BigInteger(size);
					BigInteger bfb = new BigInteger("0");
					bfb = file1.multiply(new BigInteger("100")).divide(ftp1);
					tableModel.setValueAt(bfb + "", i, 5);
				}
				bos.close();
				bis.close();
				tableModel.setValueAt("传输完成", i, 4);
				tableModel.setValueAt("100", i, 5);
				p2area.append(reader.readLine() + "\n");
				String s = reader.readLine();
				if(s.startsWith("226")) {
					TreePath path1 = tree1.getSelectionPath();
					DefaultMutableTreeNode selectnode1 = (DefaultMutableTreeNode) path1.getLastPathComponent();
					TreePath path2 = tree2.getSelectionPath();
					DefaultMutableTreeNode selectnode2 = (DefaultMutableTreeNode) path2.getLastPathComponent();
					if(selectnode1.isLeaf()) {
						selectnode1 = (DefaultMutableTreeNode)selectnode1.getParent();
					}
					DefaultMutableTreeNode node = new DefaultMutableTreeNode((String)selectnode2.getUserObject());
					treeModel1.insertNodeInto(node,selectnode1,  0);
				}
				p2area.append(s + "\n");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void upload() {
			try {
				File file = new File(filepath);
				String filename = file.getName();
				TreePath path2 = tree2.getSelectionPath();
				DefaultMutableTreeNode selectnode2 = (DefaultMutableTreeNode) path2.getLastPathComponent();
				if(selectnode2.isLeaf()) {
					selectnode2 = (DefaultMutableTreeNode)selectnode2.getParent();
				}
				Enumeration en = selectnode2.children();
				while(en.hasMoreElements()) {
					selectnode2 = (DefaultMutableTreeNode)en.nextElement();
					if(filename.equals((String)selectnode2.getUserObject())) {
						JOptionPane.showMessageDialog(frame, "文件已经存在", "", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
				writer.write("CWD " + "/" + "\r\n");
				writer.flush();
				if(reader.readLine().startsWith("226")) {
					reader.readLine();
				}
				writer.write("CWD " + serverPath + "\r\n");
				writer.flush();
				if(reader.readLine().startsWith("226")) {
					reader.readLine();
				}
				sendCommand("PASV");
				String response = reader.readLine();
				while(!response.startsWith("227")) {
					if(response.startsWith("550")) {
						String[] ss = serverPath.split("/");
						serverPath = "";
						for(int i = 0; i<ss.length-1;i++) {
							serverPath += ss[i];
						}
						writer.write("CWD " + "/" + "\r\n");
						writer.flush();
						reader.readLine();
						writer.write("CWD " + serverPath + "\r\n");
						writer.flush();
						sendCommand("PASV");
						response = reader.readLine();
					}
					p2area.append(response + "\n");
					response = reader.readLine();
				}
				p2area.append(response + "\n");
				String ip = null;
				int port = -1;
				int opening = response.indexOf('(');
				int closing = response.indexOf(')', opening + 1);
				if (closing > 0) {
					String dataLink = response.substring(opening + 1, closing);
					StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
					ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."  + tokenizer.nextToken() + "." + tokenizer.nextToken();
					port = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());
				}
				sendCommand("STOR " + filename);
				Socket dataSocket = new Socket(ip, port);
				BufferedOutputStream output = new BufferedOutputStream(dataSocket.getOutputStream());
				Object re[][] = {{p3field.getText(),"-->",p4field.getText(),file.length(),"",""}};
				list = re;
				tableModel.addRow(list[0]);
				byte[] buffer = new byte[1024*8];
				int bytesRead = 0;
				int count = 0;
				int i = 0;
				for(i = 0; i<table.getRowCount();i++) {
					String s = (String)table.getValueAt(i, 4);
					if(s.length()==0) {
						break;
					}
				}
				while ((bytesRead = input.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
					count += bytesRead;
					tableModel.setValueAt(count + "/" + file.length(), i, 4);
					BigInteger file1 = new BigInteger(count + "");
					BigInteger ftp1 = new BigInteger(file.length() + "");
					BigInteger bfb = new BigInteger("0");
					bfb = file1.multiply(new BigInteger("100")).divide(ftp1);
					tableModel.setValueAt(bfb + "", i, 5);
				}
				output.close();
				input.close();
				tableModel.setValueAt("传输完成", i, 4);
				tableModel.setValueAt("100", i, 5);
				p2area.append(reader.readLine() + "\n");
				String s = reader.readLine();
				if(s.startsWith("226")) {
					TreePath path1 = tree1.getSelectionPath();
					DefaultMutableTreeNode selectnode1 = (DefaultMutableTreeNode) path1.getLastPathComponent();
					path2 = tree2.getSelectionPath();
					selectnode2 = (DefaultMutableTreeNode) path2.getLastPathComponent();
					if(selectnode2.isLeaf()) {
						selectnode2 = (DefaultMutableTreeNode)selectnode2.getParent();
					}
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(selectnode1.getUserObject()+"");
					treeModel2.insertNodeInto( node, selectnode2,0);
				}
				p2area.append(s + "\n");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void renamef() {
			try {
				sendCommand("RNFR " + serverPath);
				p2area.append(reader.readLine() + "\n");
				sendCommand("RNTO " + filepath);
				String s = "";
				s = reader.readLine();
				if(!s.startsWith("250")) {
					TreePath path = tree2.getSelectionPath();
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					selectnode.setUserObject(oldname);
					JOptionPane.showMessageDialog(frame, "重命名失败", "", JOptionPane.INFORMATION_MESSAGE);
				}
				p2area.append(s + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void deletef() {
			try {
				sendCommand("DELE " + serverPath);
				String s = "";
				if((s = reader.readLine()).startsWith("250")) {
					TreePath path = tree2.getSelectionPath();
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					treeModel2.removeNodeFromParent(selectnode);
				}
				p2area.append(s + "\n");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void mkdir() {
			try {
				writer.write("CWD /\r\n");
				writer.flush();
				if(reader.readLine().startsWith("226")) {
					reader.readLine();
				}
				sendCommand("MKD " + serverPath + "新建文件夹");
				String s = reader.readLine();
				if(s.startsWith("226")) {
					s = reader.readLine();
				}
				if(s.startsWith("257")) {
					TreePath path = tree2.getSelectionPath();
					DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
					DefaultMutableTreeNode node = new DefaultMutableTreeNode("新建文件夹");
					if(selectnode.isLeaf()) {
						selectnode = (DefaultMutableTreeNode)selectnode.getParent();
					}
					treeModel2.insertNodeInto(node, selectnode, 0);
				}
				p2area.append(s + "\n");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public void sendCommand(String com) throws IOException {
			if (com.equals("")) {
				return;
			}
			writer.write(com + "\r\n");
			writer.flush();
		}
	}
	class MyFile{
		private File file;
		public MyFile(File file) {
			this.file = file;
		}
		public String toString() {
			String name = file.getName().trim();
			if(name.length() == 0) {
				name = file.getAbsolutePath().trim();
			}
			return name;
		}
	}
	class MyUndoableEditListener1 implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent uee) {   
			um1.addEdit(uee.getEdit());  
		}  
	}
	class MyUndoableEditListener2 implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent uee) {   
			um2.addEdit(uee.getEdit());  
		}  
	}
	class MyUndoableEditListener3 implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent uee) {   
			um3.addEdit(uee.getEdit());  
		}  
	}
	public Object[][] fu(File[] file){
		Object[][] m = new Object[file.length][6];
		for (int i = 0; i < file.length; i++) {
			m[i][0] = file[i].getName();
			m[i][1] = "-->";
			try {
				if (file[i].isDirectory()) {
					m[i][3] = "目录";
				}else {
					m[i][3] = size(file[i]);
				}
			}catch(Exception e) {
			}
		}
		return m;
	}
	public String size(File file) throws IOException {//读取文件的大小
		FileInputStream fileLength = new FileInputStream(file);
		String sizefile = fileLength.available() + "字节";
		return sizefile;
	}
	public void addrow(File file) {
		File[] fileList = file.listFiles();
		list = fu(fileList);
		for (int i = 0; i < fileList.length; i++) {
			tableModel.addRow(list[i]);
		}
		for(File file2 : fileList) {
			if(file2.isDirectory()) {
				addrow(file2);
			}
		}
	}
	public void dfile(File file) {
		try {
			if(file.isDirectory()) {
				File[] files = file.listFiles();
				for(File file2 : files) {
					dfile(file2);
				}
			}
			file.delete();
		}catch(Exception e) {
		}
	}
}