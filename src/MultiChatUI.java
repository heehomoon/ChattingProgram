import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MultiChatUI extends JFrame {
	
	// 로그인-로그아웃 패널 저장을 Component
	protected JPanel tab;	   		//로그인, 로그아웃 패널 저장을 위한 tab 패널
	protected CardLayout cardLayout;	
	
	// 로그인 패널 관련 Component
	public JPanel loginPanel;      //로그인 패널
	public JButton loginButton;    //로그인 버튼
	public JLabel inLabel;         //대화명 로그인 라벨
	public JTextField idInput;     //대화명 입력 필드
	
	// 로그아웃 패널 관련 Component
	public JPanel logoutPanel;	   //로그아웃 패널
	public JButton showPeople;     //참여자보기 버튼
	public JButton logoutButton;   //로그아웃 버튼
	public JLabel outLabel;        //대화명 설정후 라벨
	
	// textarea & 참여자 리스트 패널 저장을 위한  Component
	protected JPanel tab2;
	protected CardLayout cardLayout2;	
	
	// 대화창 JTextArea
	public JTextArea msgOut;       //대화창 TextArea
	
	//참여자 리스트를 보여주기 위한 JList
	public JList perList;
	
	// 메시지 입력 관련 Component
	public JPanel msgPanel;        //메시지 패널
	public JTextField msgInput;    //메시지 입력 필드
	public JButton exitButton;	   //종료 버튼

	public String id;
	
	
	MultiChatUI()
	{
		setTitle("채팅 프로그램");                          //Frame 제목 설정
		setLayout(new BorderLayout());                  //레이아웃 BorderLayout으로 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Frame 끄면 종료
		
		setSize(400,500);                               //Frame 크기 설정
		setLocation(200, 100);                          //Frame 위치 설정
		
		//로그인 Panel
		loginPanel = new JPanel(); 						//loginPanel 생성
		loginPanel.setLayout(new BorderLayout());       //loginPanel 레이아웃 BorderLayout으로 설정
		inLabel = new JLabel("대화명");                   //대화명 설정 전 라벨
		idInput = new JTextField(15);                  	//대화명 입력을 위한 TextField 생성
		loginButton = new JButton("로그인");              //로그인 버튼 생성
		
		//라벨, 필드, 버튼 BorderLayout 설정
		loginPanel.add(inLabel,BorderLayout.WEST);		
		loginPanel.add(idInput,BorderLayout.CENTER);
		loginPanel.add(loginButton,BorderLayout.EAST);	
		loginPanel.setBackground(Color.LIGHT_GRAY);   	//loginPanel 배경색 설정
		
		//로그아웃 Panel
		logoutPanel = new JPanel();						//logoutPanel 생성
		logoutPanel.setLayout(new BorderLayout());      //logoutPanel 레이아웃 BorderLayout으로 설정
		showPeople = new JButton("참여자 보기");
		outLabel = new JLabel();						//대화명 설정 후 라벨
		logoutButton = new JButton("로그아웃");			//로그아웃 버튼
		
		//라벨, 필드, 버튼 BorderLayout 설정
		logoutPanel.add(showPeople,BorderLayout.WEST);
		logoutPanel.add(outLabel,BorderLayout.CENTER);	
		logoutPanel.add(logoutButton,BorderLayout.EAST);
		logoutPanel.setBackground(Color.LIGHT_GRAY);    //loginPanel 배경색 설정
	
		// login 패널과 logout 패널 저장을 위한 tab 패널
		tab = new JPanel();								//tab 패널 생성
		cardLayout = new CardLayout();      			//cardLayout 생성
		tab.setLayout(cardLayout);					 	//tab의 레이아웃 cardLayout으로 설정
		tab.add(loginPanel,"login");					//tab에 로그인 패널 추가
		tab.add(logoutPanel,"logout");					//tab에 로그아웃 패널 추가
		
		this.add(tab, BorderLayout.NORTH);		    	//tab을 Frame의 NORTH에 추가
		
		
		//대화창 TextArea
		msgOut = new JTextArea("",10,10);               //행의 수 10, 열의 수 10 Textarea 생성
		msgOut.setEditable(false);						//대화창 커서로 수정 금지
		
		JScrollPane scrollpane = new JScrollPane(msgOut);  //scroll plane에 textArea 추가
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
				
		//참여자 리스트 List
		perList = new JList();
		
		// 대화창 패널과 참여자리스트 패널 저장을 위한 tab 패널
		tab2 = new JPanel();
		cardLayout2 = new CardLayout();
		tab2.setLayout(cardLayout2);
		tab2.add(scrollpane,"text");
		tab2.add(perList,"list");
		
		this.add(tab2,BorderLayout.CENTER);      //scrollpane을 Frame의 CENTER에 추가
		
		//메시지 입력창 Panel
		msgPanel = new JPanel();                    //메시지 패널 생성
		msgPanel.setLayout(new BorderLayout()); 	 //msgPanel의 레이아웃 BorderLayout으로 설정
		msgInput = new JTextField(15);				//메시지 입력 필드 생성
		exitButton = new JButton("종료");				//종료 버튼 생성
	
		
		//필드, 버튼의 BorderLayout 설정
		msgPanel.add(msgInput,BorderLayout.CENTER);
		msgPanel.add(exitButton,BorderLayout.EAST);
		msgPanel.setBackground(Color.lightGray);
		
		this.add(msgPanel,BorderLayout.SOUTH);     //scrollpane을 Frame의 SOUTH에 추가
		
		setVisible(true);							//Frame 보이기
	}
	
	//controller에서 이벤트를 처리하기 위해 미리 구축한 메소드
	public void addButtonActionListener(ActionListener listener)
	{
		loginButton.addActionListener(listener);  //로그인 버튼 눌렀을 때 action
		showPeople.addActionListener(listener);   //참여자보기 버튼 눌렀을 때 action
		logoutButton.addActionListener(listener); //로그아웃 버튼 눌렀을 때 action
		exitButton.addActionListener(listener);   //종료 버튼 눌렀을 때 action
		msgInput.addActionListener(listener);     //메시지 입력칸 눌렀을 때 action
	}
}
