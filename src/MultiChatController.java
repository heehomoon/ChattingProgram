import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import com.google.gson.Gson;

public class MultiChatController implements Runnable {

	//뷰 클래스 참조 객체
	private final MultiChatUI2 v;
	//데이터 클래스 참조 객체
	private final MultiChatData chatData;
	
	Logger logger; 				// 서버 연결상태 확인을 위한 logger
	
	BufferedReader inMsg;	  	// 입력 스트림 변수
	PrintWriter outMsg;			// 출력 스트림 변수
	
	Socket socket;              // 서버와 클라이언트 사이를 연결해주는 소켓
	Thread thread;              //쓰레드 객체
	Message m;                  //메시지 객체


	boolean status;             //쓰레드 실행 결정을 위한 변수
	Gson gson = new Gson();     //Gson 객체 생성
	
	Participants listDialog;
	public Vector<String> clientID = new Vector<String>();    //현재 참여하고 있는 참여자 ID를 저장히는 Vector
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//클라이언트 생성
		MultiChatController app = new MultiChatController(new MultiChatData(),new MultiChatUI2());
		app.appMain();		
		
	}
	
	public MultiChatController(MultiChatData chatData, MultiChatUI2 v) {
			
		//로거 객체 초기화
		logger = Logger.getLogger(this.getClass().getName());
		
		// 모델과 뷰 클래스 참조
		this.chatData = chatData;
		this.v = v;
	}
	
	public void appMain()
	{
		//데이터 객체에서 데이터 변화를 처리할 UI 객체 (JTextArea) 추가 
		chatData.addObj(v.msgOut);
		//데이터 객체에서 데이터 변화를 처리할 UI 객체 (JList) 추가
		chatData.addObj(v.perList);
		
		// 이벤트 처리 메소드 생성후 view에서 사용함
		v.addButtonActionListener(new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object obj = e.getSource();    // 넘어온 이벤트의 객체 반환
			
			if(obj == v.exitButton) 	   // 넘어온 이벤트가 종료 버튼일 때
			{
				System.exit(0);
			}  
			else if (obj == v.loginButton) 			  // 넘어온 이벤트가 로그인 버튼일 때
			{
				v.id = v.idInput.getText();		      // 입력한 대화명으로 설정
				v.cardLayout.show(v.tab, "msg");
				connectServer();                      // 서버와 연결
				v.idInput.setText("");
			}
			else if (obj == v.logoutButton) 		  // 넘어온 이벤트가 로그인 버튼일 때
			{
				outMsg.println(gson.toJson(new Message(v.id,"","","logout")));
				v.msgOut.setText("");
				outMsg.close();
				
				try {
					inMsg.close();
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				status = false;
				v.cardLayout.show(v.tab, "login");
			}
			else if (obj == v.sendButton || obj == v.msgInput) 		  // 넘어온 이벤트가 로그인 버튼일 때
			{
				if(v.msgInput.getText().equals("") == false)
				{
					outMsg.println(gson.toJson(new Message(v.id,"",v.msgInput.getText(),"")));
					v.msgInput.setText("");
				}
			}
			else if(obj == v.showlistButton)
			{
				clientID.clear();
				// 서버에 참여자 리스트 요청 메시지 전송
				outMsg.println(gson.toJson(new Message(v.id,"","","list")));
	
				listDialog = new Participants(chatData.list);
			}
		}
		});
	}
	
	String myip = "127.0.0.1"; //자신의 컴퓨터 IP 주소
	
	public void connectServer() 
	{
		try
		{
			//ip주소와 포트번호를 사용하여 소켓 생성
			socket = new Socket(myip,1234);
			logger.info("[Client]Server 연결성공!!");
						
			// 입출력 스트림 생성
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(),true);
			
			// JSon 형태로 서버에 메시지 전달
			outMsg.println(gson.toJson(new Message(v.id,"","","login")));
			
			//메시지 수신을 위한 스레드 생성
			Thread thread = new Thread(this);
			//쓰레드 시작
			thread.start();
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.warning("[MultiChatUI]connectServer() Exception 발생!");
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		//수신 메시지를 처리하는 데 필요한 변수 선언
		String msg;
		
		//쓰레드 실행을 결정하는 status 
		status = true;
	
		while(status) 
		{	
			try {
					// 메시지를 수신하여 msg에 저장
					msg = inMsg.readLine();
					// msg에서 파싱을 통하여 메시지 정보를 message 객체에 저장
					m = gson.fromJson(msg, Message.class);
					
					// 메시지 타입이 채팅창 참여자 정보 메시지일 경우
					if(m.getType().equals("member"))
					{
						clientID.add(m.getMsg());
						chatData.refreshList(clientID);
					}
					// 메시지 타입이 채팅방 참여자가 로그인 했다는 메시지일 경우
					else if(m.getType().equals("login"))   //login type 메시지가 들어올 때
					{
						//ClientID에 새로 들어온 참여자의 id 추가
						clientID.add(m.getId());           
						 //view의 List를 현재의 clientID로 변경
						chatData.refreshList(clientID);   
						//view의 TextArea에 참여자 로그인 알림
						chatData.refreshData(m.getId()+" "+m.getMsg()+"\n");
					}
					// 메시지 타입이 채팅방 참여자가 로그아웃 했다는 메시지일 경우
					else if(m.getType().equals("logout"))   //logout type 메시지가 들어올 때
					{
						//ClientID에 나간 참여자의 id 삭제
						clientID.remove(m.getId()); 	
						 //view의 List를 현재의 clientID로 변경
						chatData.refreshList(clientID);   
						//view의 TextArea에 참여자 로그아웃 알림
						chatData.refreshData(m.getId()+" "+m.getMsg()+"\n");
					}
					else
					{
					   //view의 TextArea를 현재의 clientID로 변경
						chatData.refreshData(m.getId()+" : "+m.getMsg()+"\n");
						
						//커서를 현재 대화 메시지에 표시
						v.msgOut.setCaretPosition(v.msgOut.getDocument().getLength());
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.warning("[MultiChatU]메시지 스트림 종료!!");
			}
			
		}

	}
}
