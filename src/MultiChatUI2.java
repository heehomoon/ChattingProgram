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
	public JLabel inLabel;         //대화명 로그인 라벨
	public JTextField idInput;     //대화명 입력 필드
	
	// 접속자리스트 화면 패널 관련 Component
	public JPanel listPanel;
	public JList perList;
	
	// 대화창 화면 패널 관련 Component
	public JPanel msgPanel;        //메시지 패널
	public JTextArea msgOut;       //대화창 TextArea
	public JTextField msgInput;    //메시지 입력 필드
	public JButton exitButton;	   //종료 버튼

	public String id;
		
	MultiChatUI2()
	{
		setTitle("채팅 프로그램");                          //Frame 제목 설정
		setLayout(new BorderLayout());                  //레이아웃 BorderLayout으로 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame 끄면 종료
		
		setSize(400,500);                               //Frame 크기 설정
		setLocation(200, 100);                          //Frame 위치 설정
		
		// 로그인 Panel
		loginPanel = new JPanel(); 						//loginPanel 생성
		loginPanel.setLayout(null);  
		
		inLabel = new JLabel("ID");         inLabel.setBounds(30, 100, 50, 10);
		idInput = new JTextField(15);       idInput.setBounds(30, 150, 50, 10);
		loginButton = new JButton("로그인");  loginButton.setBounds(80, 100, 50, 20);
		
		loginPanel.add(inLabel);
		loginPanel.add(idInput);
		loginPanel.add(loginButton);

		// 참여자 리스트 Panel
		listPanel = new JPanel(); 					
		listPanel.setLayout(new BorderLayout());  
		
		perList = new JList();
		JScrollPane scrollpane1= new JScrollPane(perList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
		listPanel.add(scrollpane1,BorderLayout.CENTER);
		
		
		//메시지 입력창 Panel
		msgPanel = new JPanel();                    //메시지 패널 생성
		msgPanel.setLayout(new BorderLayout()); 	 

		msgOut = new JTextArea("",10,10);               //행의 수 10, 열의 수 10 Textarea 생성
		msgOut.setEditable(false);						//대화창 커서로 수정 금지
		JScrollPane scrollpane2 = new JScrollPane(msgOut);  //scroll plane에 textArea 추가
		scrollpane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
			
		msgInput = new JTextField(15);				//메시지 입력 필드 생성
		exitButton = new JButton("종료");				//종료 버튼 생성
	
		//필드, 버튼의 BordeLyout 설정
		msgPanel.add(exitButton,BorderLayout.NORTH);
		msgPanel.add(msgOut,BorderLayout.CENTER);
		msgPanel.add(msgInput,BorderLayout.SOUTH);
		msgPanel.setBackground(Color.lightGray);
		
		// Tab 
		tab = new JPanel();								//tab 패널 생성
		cardLayout = new CardLayout();      			//cardLayout 생성
		tab.setLayout(cardLayout);					 	//tab의 레이아웃 cardLayout으로 설정
		tab.add(loginPanel,"login");					//tab에 로그인 패널 추가
		tab.add(listPanel,"list");						//tab에 리스트 패널 추가
		tab.add(msgPanel,"msg");
		
		this.add(tab);
		
		setVisible(true);							//Frame 보이기
	}
	
	
	//controller에서 이벤트를 처리하기 위해 미리 구축한 메소드
	public void addButtonActionListener(ActionListener listener)
	{
//		loginButton.addActionListener(listener);  //로그인 버튼 눌렀을 때 action
//		showPeople.addActionListener(listener);   //참여자보기 버튼 눌렀을 때 action
//		logoutButton.addActionListener(listener); //로그아웃 버튼 눌렀을 때 action
//		exitButton.addActionListener(listener);   //종료 버튼 눌렀을 때 action
//		msgInput.addActionListener(listener);     //메시지 입력칸 눌렀을 때 action
	}
}
