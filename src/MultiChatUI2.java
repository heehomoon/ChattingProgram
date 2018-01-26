import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MultiChatUI2 extends JFrame {
	
	// �α���-�α׾ƿ� �г� ������ Component
	protected JPanel tab;	   		//�α���, �α׾ƿ� �г� ������ ���� tab �г�
	protected CardLayout cardLayout;	
	
	// �α��� �г� ���� Component
	public JPanel loginPanel;      //�α��� �г�
	public JButton loginButton;    //�α��� ��ư
	public JLabel idLabel;         //��ȭ�� �α��� ��
	public JTextField idInput;     //��ȭ�� �Է� �ʵ�

	
	// ��ȭâ ȭ�� �г� ���� Component
	public JPanel msgPanel;        //�޽��� �г�
	public JScrollPane scrollpane;
	public JTextArea msgOut;       //��ȭâ TextArea
	public JTextField msgInput;    //�޽��� �Է� �ʵ�
	public JButton logoutButton;
	public JButton showlistButton;
	public JButton sendButton;
	public JButton exitButton;	   //���� ��ư

	public String id;
		
	MultiChatUI2()
	{
		setTitle("ä�� ���α׷�");                          //Frame ���� ����
		getContentPane().setPreferredSize(new Dimension(400,500));                     //Frame ��ġ ����
		pack();
		setResizable(false);    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame ���� ����

		loginPanel = new JPanel(); 						//loginPanel ����
		loginPanel.setLayout(null);  
		
		idLabel = new JLabel("ID");         idLabel.setBounds(60, 100, 200, 30);
		idInput = new JTextField(15);       idInput.setBounds(90, 100, 150, 30);
		loginButton = new JButton("Login");  loginButton.setBounds(250, 100, 80, 30);
		
		loginPanel.add(idLabel);
		loginPanel.add(idInput);
		loginPanel.add(loginButton);
		
		//�޽��� �Է�â Panel
		msgPanel = new JPanel();                    //�޽��� �г� ����
		msgPanel.setLayout(null); 					//NULL

		msgOut = new JTextArea("",10,10);               //���� �� 10, ���� �� 10 Textarea ����
		msgOut.setEditable(false);						//��ȭâ Ŀ���� ���� ����
		scrollpane = new JScrollPane(msgOut);           //scroll plane�� textArea �߰�
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
			
		msgInput = new JTextField(15);				 	//�޽��� �Է� �ʵ� ����
		exitButton = new JButton("Exit");				//���� ��ư ����
		sendButton = new JButton("Send");				//���� ��ư ����
		logoutButton = new JButton("Logout");			//���� ��ư ����
		showlistButton = new JButton("Participants");
		
		showlistButton.setBounds(0,0,200,30);
		logoutButton.setBounds(300, 0, 100, 30);
		exitButton.setBounds(200,0,100,30);
		
		scrollpane.setBounds(0,30,400,400);
		msgInput.setBounds(0,430,330,70);
		sendButton.setBounds(330,430,70,70);
		//�ʵ�, ��ư�� BordeLyout ����
		msgPanel.add(scrollpane);
		msgPanel.add(msgInput);
		msgPanel.add(exitButton);
		msgPanel.add(sendButton);
		msgPanel.add(logoutButton);
		msgPanel.add(showlistButton);
		msgPanel.setBackground(Color.lightGray);
		
		// Tab 
		tab = new JPanel();								//tab �г� ����
		cardLayout = new CardLayout();      			//cardLayout ����
		tab.setLayout(cardLayout);					 	//tab�� ���̾ƿ� cardLayout���� ����
		tab.add(loginPanel,"login");					//tab�� �α��� �г� �߰�
		tab.add(msgPanel,"msg");
		
		this.add(tab);
		
		setVisible(true);							//Frame ���̱�
	}
	
	//controller���� �̺�Ʈ�� ó���ϱ� ���� �̸� ������ �޼ҵ�
	public void addButtonActionListener(ActionListener listener)
	{
		loginButton.addActionListener(listener);  //�α��� ��ư ������ �� action
		showlistButton.addActionListener(listener);   //�����ں��� ��ư ������ �� action
		logoutButton.addActionListener(listener); //�α׾ƿ� ��ư ������ �� action
		exitButton.addActionListener(listener);   //���� ��ư ������ �� action
		sendButton.addActionListener(listener);   //���� ��ư ������ �� action
		msgInput.addActionListener(listener);     //�޽��� �Է�ĭ ������ �� action
	}
}
