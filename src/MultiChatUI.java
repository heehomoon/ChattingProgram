import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MultiChatUI extends JFrame {
	
	// �α���-�α׾ƿ� �г� ������ Component
	protected JPanel tab;	   		//�α���, �α׾ƿ� �г� ������ ���� tab �г�
	protected CardLayout cardLayout;	
	
	// �α��� �г� ���� Component
	public JPanel loginPanel;      //�α��� �г�
	public JButton loginButton;    //�α��� ��ư
	public JLabel inLabel;         //��ȭ�� �α��� ��
	public JTextField idInput;     //��ȭ�� �Է� �ʵ�
	
	// �α׾ƿ� �г� ���� Component
	public JPanel logoutPanel;	   //�α׾ƿ� �г�
	public JButton showPeople;     //�����ں��� ��ư
	public JButton logoutButton;   //�α׾ƿ� ��ư
	public JLabel outLabel;        //��ȭ�� ������ ��
	
	// textarea & ������ ����Ʈ �г� ������ ����  Component
	protected JPanel tab2;
	protected CardLayout cardLayout2;	
	
	// ��ȭâ JTextArea
	public JTextArea msgOut;       //��ȭâ TextArea
	
	//������ ����Ʈ�� �����ֱ� ���� JList
	public JList perList;
	
	// �޽��� �Է� ���� Component
	public JPanel msgPanel;        //�޽��� �г�
	public JTextField msgInput;    //�޽��� �Է� �ʵ�
	public JButton exitButton;	   //���� ��ư

	public String id;
	
	
	MultiChatUI()
	{
		setTitle("ä�� ���α׷�");                          //Frame ���� ����
		setLayout(new BorderLayout());                  //���̾ƿ� BorderLayout���� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame ���� ����
		
		setSize(400,500);                               //Frame ũ�� ����
		setLocation(200, 100);                          //Frame ��ġ ����
		
		//�α��� Panel
		loginPanel = new JPanel(); 						//loginPanel ����
		loginPanel.setLayout(new BorderLayout());       //loginPanel ���̾ƿ� BorderLayout���� ����
		inLabel = new JLabel("��ȭ��");                   //��ȭ�� ���� �� ��
		idInput = new JTextField(15);                  	//��ȭ�� �Է��� ���� TextField ����
		loginButton = new JButton("�α���");              //�α��� ��ư ����
		
		//��, �ʵ�, ��ư BorderLayout ����
		loginPanel.add(inLabel,BorderLayout.WEST);		
		loginPanel.add(idInput,BorderLayout.CENTER);
		loginPanel.add(loginButton,BorderLayout.EAST);	
		loginPanel.setBackground(Color.LIGHT_GRAY);   	//loginPanel ���� ����
		
		//�α׾ƿ� Panel
		logoutPanel = new JPanel();						//logoutPanel ����
		logoutPanel.setLayout(new BorderLayout());      //logoutPanel ���̾ƿ� BorderLayout���� ����
		showPeople = new JButton("������ ����");
		outLabel = new JLabel();						//��ȭ�� ���� �� ��
		logoutButton = new JButton("�α׾ƿ�");			//�α׾ƿ� ��ư
		
		//��, �ʵ�, ��ư BorderLayout ����
		logoutPanel.add(showPeople,BorderLayout.WEST);
		logoutPanel.add(outLabel,BorderLayout.CENTER);	
		logoutPanel.add(logoutButton,BorderLayout.EAST);
		logoutPanel.setBackground(Color.LIGHT_GRAY);    //loginPanel ���� ����
	
		// login �гΰ� logout �г� ������ ���� tab �г�
		tab = new JPanel();								//tab �г� ����
		cardLayout = new CardLayout();      			//cardLayout ����
		tab.setLayout(cardLayout);					 	//tab�� ���̾ƿ� cardLayout���� ����
		tab.add(loginPanel,"login");					//tab�� �α��� �г� �߰�
		tab.add(logoutPanel,"logout");					//tab�� �α׾ƿ� �г� �߰�
		
		this.add(tab, BorderLayout.NORTH);		    	//tab�� Frame�� NORTH�� �߰�
		
		
		//��ȭâ TextArea
		msgOut = new JTextArea("",10,10);               //���� �� 10, ���� �� 10 Textarea ����
		msgOut.setEditable(false);						//��ȭâ Ŀ���� ���� ����
		
		JScrollPane scrollpane = new JScrollPane(msgOut);  //scroll plane�� textArea �߰�
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
				
		//������ ����Ʈ List
		perList = new JList();
		
		// ��ȭâ �гΰ� �����ڸ���Ʈ �г� ������ ���� tab �г�
		tab2 = new JPanel();
		cardLayout2 = new CardLayout();
		tab2.setLayout(cardLayout2);
		tab2.add(scrollpane,"text");
		tab2.add(perList,"list");
		
		this.add(tab2,BorderLayout.CENTER);      //scrollpane�� Frame�� CENTER�� �߰�
		
		//�޽��� �Է�â Panel
		msgPanel = new JPanel();                    //�޽��� �г� ����
		msgPanel.setLayout(new BorderLayout()); 	 //msgPanel�� ���̾ƿ� BorderLayout���� ����
		msgInput = new JTextField(15);				//�޽��� �Է� �ʵ� ����
		exitButton = new JButton("����");				//���� ��ư ����
	
		
		//�ʵ�, ��ư�� BorderLayout ����
		msgPanel.add(msgInput,BorderLayout.CENTER);
		msgPanel.add(exitButton,BorderLayout.EAST);
		msgPanel.setBackground(Color.lightGray);
		
		this.add(msgPanel,BorderLayout.SOUTH);     //scrollpane�� Frame�� SOUTH�� �߰�
		
		setVisible(true);							//Frame ���̱�
	}
	
	//controller���� �̺�Ʈ�� ó���ϱ� ���� �̸� ������ �޼ҵ�
	public void addButtonActionListener(ActionListener listener)
	{
		loginButton.addActionListener(listener);  //�α��� ��ư ������ �� action
		showPeople.addActionListener(listener);   //�����ں��� ��ư ������ �� action
		logoutButton.addActionListener(listener); //�α׾ƿ� ��ư ������ �� action
		exitButton.addActionListener(listener);   //���� ��ư ������ �� action
		msgInput.addActionListener(listener);     //�޽��� �Է�ĭ ������ �� action
	}
}
