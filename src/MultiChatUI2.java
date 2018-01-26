import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MultiChatUI2 extends JFrame {
	
	// 로그인-로그아웃 패널 저장을 Component
	protected JPanel tab;	   		//로그인, 로그아웃 패널 저장을 위한 tab 패널
	protected CardLayout cardLayout;	
	
	// 로그인 패널 관련 Component
	public JPanel loginPanel;      //로그인 패널
	public JButton loginButton;    //로그인 버튼
	public JLabel idLabel;         //대화명 로그인 라벨
	public JTextField idInput;     //대화명 입력 필드

	
	// 대화창 화면 패널 관련 Component
	public JPanel msgPanel;        //메시지 패널
	public JScrollPane scrollpane;
	public JTextArea msgOut;       //대화창 TextArea
	public JTextField msgInput;    //메시지 입력 필드
	public JButton logoutButton;
	public JButton showlistButton;
	public JButton sendButton;
	public JButton exitButton;	   //종료 버튼

	public String id;
		
	MultiChatUI2()
	{
		setTitle("채팅 프로그램");                          //Frame 제목 설정
		getContentPane().setPreferredSize(new Dimension(400,500));                     //Frame 위치 설정
		pack();
		setResizable(false);    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame 끄면 종료

		loginPanel = new JPanel(); 						//loginPanel 생성
		loginPanel.setLayout(null);  
		
		idLabel = new JLabel("ID");         idLabel.setBounds(60, 100, 200, 30);
		idInput = new JTextField(15);       idInput.setBounds(90, 100, 150, 30);
		loginButton = new JButton("Login");  loginButton.setBounds(250, 100, 80, 30);
		
		loginPanel.add(idLabel);
		loginPanel.add(idInput);
		loginPanel.add(loginButton);
		
		//메시지 입력창 Panel
		msgPanel = new JPanel();                    //메시지 패널 생성
		msgPanel.setLayout(null); 					//NULL

		msgOut = new JTextArea("",10,10);               //행의 수 10, 열의 수 10 Textarea 생성
		msgOut.setEditable(false);						//대화창 커서로 수정 금지
		scrollpane = new JScrollPane(msgOut);           //scroll plane에 textArea 추가
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
			
		msgInput = new JTextField(15);				 	//메시지 입력 필드 생성
		exitButton = new JButton("Exit");				//종료 버튼 생성
		sendButton = new JButton("Send");				//종료 버튼 생성
		logoutButton = new JButton("Logout");			//종료 버튼 생성
		showlistButton = new JButton("Participants");
		
		showlistButton.setBounds(0,0,200,30);
		logoutButton.setBounds(300, 0, 100, 30);
		exitButton.setBounds(200,0,100,30);
		
		scrollpane.setBounds(0,30,400,400);
		msgInput.setBounds(0,430,330,70);
		sendButton.setBounds(330,430,70,70);
		//필드, 버튼의 BordeLyout 설정
		msgPanel.add(scrollpane);
		msgPanel.add(msgInput);
		msgPanel.add(exitButton);
		msgPanel.add(sendButton);
		msgPanel.add(logoutButton);
		msgPanel.add(showlistButton);
		msgPanel.setBackground(Color.lightGray);
		
		// Tab 
		tab = new JPanel();								//tab 패널 생성
		cardLayout = new CardLayout();      			//cardLayout 생성
		tab.setLayout(cardLayout);					 	//tab의 레이아웃 cardLayout으로 설정
		tab.add(loginPanel,"login");					//tab에 로그인 패널 추가
		tab.add(msgPanel,"msg");
		
		this.add(tab);
		
		setVisible(true);							//Frame 보이기
	}
	
	//controller에서 이벤트를 처리하기 위해 미리 구축한 메소드
	public void addButtonActionListener(ActionListener listener)
	{
		loginButton.addActionListener(listener);  //로그인 버튼 눌렀을 때 action
		showlistButton.addActionListener(listener);   //참여자보기 버튼 눌렀을 때 action
		logoutButton.addActionListener(listener); //로그아웃 버튼 눌렀을 때 action
		exitButton.addActionListener(listener);   //종료 버튼 눌렀을 때 action
		sendButton.addActionListener(listener);   //종료 버튼 눌렀을 때 action
		msgInput.addActionListener(listener);     //메시지 입력칸 눌렀을 때 action
	}
}
