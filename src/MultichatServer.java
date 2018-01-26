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
	//���� ���� �� Ŭ���̾�Ʈ ���� ����
	ServerSocket ss = null;
	Socket s = null;
	
	//����� Ŭ���̾�Ʈ �����带 �����ϴ� ArrayList
	ArrayList<ChatThread> chatThreads = new ArrayList<ChatThread>();
	
	//����� Ŭ���̾�Ʈ ID�� �����ϴ� ArrayList
	ArrayList<String> chatIds = new ArrayList<String>();
	
	Logger logger; //������¸� ǥ�����ִ� logger
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		//���� ����
		MultichatServer server = new MultichatServer();
		//���� start
		server.start();
	}
	
	
	// ��Ƽ ä�� ���� ���α׷� �κ�
	public void start()
	{
		
		logger = Logger.getLogger(this.getClass().getName());
		try {
			//���� ���� ����
			ss = new ServerSocket(1234);
			logger.info("MultiChatServer start");
		
			//���� ������ ���鼭 Ŭ���̾�Ʈ ������ ��ٸ�
			while(true) 
			{
				s = ss.accept();
				System.out.println("�����!");
				//����� Ŭ���̾�Ʈ�� ���� ������ Ŭ���� ����
				ChatThread chat = new ChatThread();
				//Ŭ���̾�Ʈ ����Ʈ �߰�
				chatThreads.add(chat);
				//������ ����
				chat.start();
			}
		}
		catch (Exception e) {
			logger.info("[MultiChatServer]start() Exception �߻�!!");
			e.printStackTrace();
		}
	}
	
	public class ChatThread extends Thread
	{
		//���� �޽��� �� �Ľ� �޽��� ó���� ���� ���� ����
		String msg ;
		//�޽��� ��ü ����
		Message m = new Message();
		//JSON �ļ� �ʱ�ȭ
		Gson gson = new Gson();
		
		String clientId; //Thread�� ����ϴ� Client ID ����
		
		//����� ��Ʈ��
		private BufferedReader inMsg = null;
		private PrintWriter outMsg = null;
		
		boolean status = true;
		
		public void run()
		{	
			try {
				//����� ��Ʈ�� ����
				inMsg = new BufferedReader(new InputStreamReader(s.getInputStream()));
				outMsg = new PrintWriter(s.getOutputStream(),true);
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//���� ������ true�̸� ������ ���鼭 ����ڿ��Լ� ���ŵ� �޽��� ó��
			while(status)
			{
				try {
					//���ŵ� �޽����� msg ������ ����
					msg = inMsg.readLine();
					
					//JSON �޽����� Message ��ü�� ����
					m = gson.fromJson(msg, Message.class);
			
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				//�Ľ̵� ���ڿ� �迭�� �� ��° ��Ұ��� ���� ó��
				//�α׾ƿ� �޽����� ��
				if(m.getType().equals("logout")) 
				{
					//�α׾ƿ� �޽����� ������ thread ����
					chatThreads.remove(this);
					//�α׾ƿ� �޽����� ������ ����Ʈ���� �ش� id ����
					chatIds.remove(m.getId());
					
					//��� �����ڿ��� �α׾ƿ��� ������ ���� �˸�
					msgSendAll(gson.toJson(new Message(m.getId(),""," is logged out","logout")));
					//�ش� Ŭ���̾�Ʈ ������ ����� status�� false�� ����
					status = false; 
				}//�α��� �޽����� ��
				else if(m.getType().equals("login"))
				{
					//�α��� �޽����� ������ clientID ����
					clientId = m.getId();
					//�α��� �޽����� ������ ����Ʈ�� �ش� id �߰�
					chatIds.add(m.getId());
					//��� �����ڿ��� �α����� ������ ���� �˸�
					msgSendAll(gson.toJson(new Message(m.getId(),""," is logged in","login")));
				}//������ list ��û �޽��� �� ��
				else if(m.getType().equals("list"))
				{
					//chatIds�� �ִ� ��� id "member" Ÿ�� �޽����� ����
					for(String id : chatIds)
					{	
						msgSend(m.getId(),gson.toJson(new Message("","",id,"member")));
					}
				}//�� ���� ���, �� �Ϲ� �޽��� �϶�
				else if(m.getType().equals("message"))
				{
					msgSendAll(msg);
				}
			}
			
			// ������ ����� Ŭ���̾�Ʈ ������ ����ǹǷ� ���ͷ�Ʈ
			this.interrupt();
			logger.info(this.getName()+"�����!");
		}
	
		// ArrayList�� �����ϴ� ��� chatThread�� �޽����� �����ϴ� �޼ҵ�
		public void msgSendAll(String msg)
		{		
			// ��� chatThrea�� �޽��� ���� 
			for(ChatThread ct : chatThreads)
			{
				ct.outMsg.println(msg);	
			}
		}
	
		// chatThreads ArrayList�� �����ϴ� thread �߿� ���޵� ID Ŭ���̾�Ʈ���� �޽��� ����
		public void msgSend(String ID, String msg)
		{			
			// ��� chatThrea �˻�
			for(ChatThread ct : chatThreads)
			{
				//���޵� ID�� ���� Ŭ���̾�Ʈ �޽��� ����
				if(ct.clientId.equals(ID))
					ct.outMsg.println(msg);
			}
		}

	
	}
	
}
