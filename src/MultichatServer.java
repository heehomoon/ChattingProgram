import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class MultichatServer 
{
	//서버 소켓 및 클라이언트 연결 소켓
	ServerSocket ss = null;
	Socket s = null;
	
	//연결된 클라이언트 스레드를 관리하는 ArrayList
	ArrayList<ChatThread> chatThreads = new ArrayList<ChatThread>();
	
	//연결된 클라이언트 ID를 관리하는 ArrayList
	ArrayList<String> chatIds = new ArrayList<String>();
	
	Logger logger; //연결상태를 표시해주는 logger
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		//서버 생성
		MultichatServer server = new MultichatServer();
		//서버 start
		server.start();
	}
	
	
	// 멀티 채팅 메인 프로그램 부분
	public void start()
	{
		
		logger = Logger.getLogger(this.getClass().getName());
		try {
			//서버 소켓 생성
			ss = new ServerSocket(1234);
			logger.info("MultiChatServer start");
		
			//무한 루프를 돌면서 클라이언트 연결을 기다림
			while(true) 
			{
				s = ss.accept();
				System.out.println("연결됨!");
				//연결된 클라이언트에 대해 스레드 클래스 생성
				ChatThread chat = new ChatThread();
				//클라이언트 리스트 추가
				chatThreads.add(chat);
				//스레드 시작
				chat.start();
			}
		}
		catch (Exception e) {
			logger.info("[MultiChatServer]start() Exception 발생!!");
			e.printStackTrace();
		}
	}
	
	public class ChatThread extends Thread
	{
		//수신 메시지 및 파싱 메시지 처리를 위한 변수 선언
		String msg ;
		//메시지 객체 생성
		Message m = new Message();
		//JSON 파서 초기화
		Gson gson = new Gson();
		
		String clientId; //Thread를 사용하는 Client ID 저장
		
		//입출력 스트림
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		
		boolean status = true;
		
		public void run()
		{	
			try {
				//입출력 스트림 연결
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(),true);
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//상태 정보가 true이면 루프를 돌면서 사용자에게서 수신된 메시지 처리
			while(status)
			{
				try {
					//수신된 메시지를 msg 변수에 저장
					msg = inMsg.readLine();
					
					//JSON 메시지를 Message 객체로 매핑
					m = gson.fromJson(msg, Message.class);
			
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				//파싱된 문자열 배열의 두 번째 요소값에 따라 처리
				//로그아웃 메시지일 때
				if(m.getType().equals("logout")) 
				{
					//로그아웃 메시지가 들어오면 thread 삭제
					chatThreads.remove(this);
					//로그아웃 메시지가 들어오면 리스트에서 해당 id 삭제
					chatIds.remove(m.getId());
					
					//모든 참여자에게 로그아웃한 참여자 정보 알림
					msgSendAll(gson.toJson(new Message(m.getId(),""," is logged out","logout")));
					//해당 클라이언트 스레드 종료로 status를 false로 설정
					status = false; 
				}//로그인 메시지일 때
				else if(m.getType().equals("login"))
				{
					//로그인 메시지가 들어오면 clientID 지정
					clientId = m.getId();
					//로그인 메시지가 들어오면 리스트에 해당 id 추가
					chatIds.add(m.getId());
					//모든 참여자에게 로그인한 참여자 정보 알림
					msgSendAll(gson.toJson(new Message(m.getId(),""," is logged in","login")));
				}//참여자 list 요청 메시지 일 때
				else if(m.getType().equals("list"))
				{
					//chatIds에 있는 모든 id "member" 타입 메시지로 전달
					for(String id : chatIds)
					{	
						msgSend(m.getId(),gson.toJson(new Message("","",id,"member")));
					}
				}//그 밖의 경우, 즉 일반 메시지 일때
				else if(m.getType().equals("message"))
				{
					msgSendAll(msg);
				}
			}
			
			// 루프를 벗어나면 클라이언트 연결이 종료되므로 인터럽트
			this.interrupt();
			logger.info(this.getName()+"종료됨!");
		}
	
		// ArrayList에 존재하는 모든 chatThread에 메시지를 전달하는 메소드
		public void msgSendAll(String msg)
		{		
			// 모든 chatThrea에 메시지 전달 
			for(ChatThread ct : chatThreads)
			{
				ct.outMsg.println(msg);	
			}
		}
	
		// chatThreads ArrayList에 존재하는 thread 중에 전달된 ID 클라이언트에만 메시지 전달
		public void msgSend(String ID, String msg)
		{			
			// 모든 chatThrea 검색
			for(ChatThread ct : chatThreads)
			{
				//전달된 ID와 같은 클라이언트 메시지 전달
				if(ct.clientId.equals(ID))
					ct.outMsg.println(msg);
			}
		}

	
	}
	
}
