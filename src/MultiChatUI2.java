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
	public JLabel inLabel;         //��ȭ�� �α��� ��
	public JTextField idInput;     //��ȭ�� �Է� �ʵ�
	
	// �����ڸ���Ʈ ȭ�� �г� ���� Component
	public JPanel listPanel;
	public JList perList;
	
	// ��ȭâ ȭ�� �г� ���� Component
	public JPanel msgPanel;        //�޽��� �г�
	public JTextArea msgOut;       //��ȭâ TextArea
	public JTextField msgInput;    //�޽��� �Է� �ʵ�
	public JButton exitButton;	   //���� ��ư

	public String id;
		
	MultiChatUI2()
	{
		setTitle("ä�� ���α׷�");                          //Frame ���� ����
		setLayout(new BorderLayout());                  //���̾ƿ� BorderLayout���� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame ���� ����
		
		setSize(400,500);                               //Frame ũ�� ����
		setLocation(200, 100);                          //Frame ��ġ ����
		
		// �α��� Panel
		loginPanel = new JPanel(); 						//loginPanel ����
		loginPanel.setLayout(null);  
		
		inLabel = new JLabel("ID");         inLabel.setBounds(30, 100, 50, 10);
		idInput = new JTextField(15);       idInput.setBounds(30, 150, 50, 10);
		loginButton = new JButton("�α���");  loginButton.setBounds(80, 100, 50, 20);
		
		loginPanel.add(inLabel);
		loginPanel.add(idInput);
		loginPanel.add(loginButton);

		// ������ ����Ʈ Panel
		listPanel = new JPanel(); 					
		listPanel.setLayout(new BorderLayout());  
		
		perList = new JList();
		JScrollPane scrollpane1= new JScrollPane(perList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
		listPanel.add(scrollpane1,BorderLayout.CENTER);
		
		
		//�޽��� �Է�â Panel
		msgPanel = new JPanel();                    //�޽��� �г� ����
		msgPanel.setLayout(new BorderLayout()); 	 

		msgOut = new JTextArea("",10,10);               //���� �� 10, ���� �� 10 Textarea ����
		msgOut.setEditable(false);						//��ȭâ Ŀ���� ���� ����
		JScrollPane scrollpane2 = new JScrollPane(msgOut);  //scroll plane�� textArea �߰�
		scrollpane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
			
		msgInput = new JTextField(15);				//�޽��� �Է� �ʵ� ����
		exitButton = new JButton("����");				//���� ��ư ����
	
		//�ʵ�, ��ư�� BordeLyout ����
		msgPanel.add(exitButton,BorderLayout.NORTH);
		msgPanel.add(msgOut,BorderLayout.CENTER);
		msgPanel.add(msgInput,BorderLayout.SOUTH);
		msgPanel.setBackground(Color.lightGray);
		
		// Tab 
		tab = new JPanel();								//tab �г� ����
		cardLayout = new CardLayout();      			//cardLayout ����
		tab.setLayout(cardLayout);					 	//tab�� ���̾ƿ� cardLayout���� ����
		tab.add(loginPanel,"login");					//tab�� �α��� �г� �߰�
		tab.add(listPanel,"list");						//tab�� ����Ʈ �г� �߰�
		tab.add(msgPanel,"msg");
		
		this.add(tab);
		
		setVisible(true);							//Frame ���̱�
	}
	
	
	//controller���� �̺�Ʈ�� ó���ϱ� ���� �̸� ������ �޼ҵ�
	public void addButtonActionListener(ActionListener listener)
	{
//		loginButton.addActionListener(listener);  //�α��� ��ư ������ �� action
//		showPeople.addActionListener(listener);   //�����ں��� ��ư ������ �� action
//		logoutButton.addActionListener(listener); //�α׾ƿ� ��ư ������ �� action
//		exitButton.addActionListener(listener);   //���� ��ư ������ �� action
//		msgInput.addActionListener(listener);     //�޽��� �Է�ĭ ������ �� action
	}
}
