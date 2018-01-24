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

	//�� Ŭ���� ���� ��ü
	private final MultiChatUI2 v;
	//������ Ŭ���� ���� ��ü
	private final MultiChatData chatData;
	
	Logger logger; 				// ���� ������� Ȯ���� ���� logger
	
	BufferedReader inMsg;	  	// �Է� ��Ʈ�� ����
	PrintWriter outMsg;			// ��� ��Ʈ�� ����
	
	Socket socket;              // ������ Ŭ���̾�Ʈ ���̸� �������ִ� ����
	Thread thread;              //������ ��ü
	Message m;                  //�޽��� ��ü


	boolean status;             //������ ���� ������ ���� ����
	Gson gson = new Gson();     //Gson ��ü ����
	
	Participants listDialog;
	public Vector<String> clientID = new Vector<String>();    //���� �����ϰ� �ִ� ������ ID�� �������� Vector
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Ŭ���̾�Ʈ ����
		MultiChatController app = new MultiChatController(new MultiChatData(),new MultiChatUI2());
		app.appMain();		
		
	}
	
	public MultiChatController(MultiChatData chatData, MultiChatUI2 v) {
			
		//�ΰ� ��ü �ʱ�ȭ
		logger = Logger.getLogger(this.getClass().getName());
		
		// �𵨰� �� Ŭ���� ����
		this.chatData = chatData;
		this.v = v;
	}
	
	public void appMain()
	{
		//������ ��ü���� ������ ��ȭ�� ó���� UI ��ü (JTextArea) �߰� 
		chatData.addObj(v.msgOut);
		//������ ��ü���� ������ ��ȭ�� ó���� UI ��ü (JList) �߰�
		chatData.addObj(v.perList);
		
		// �̺�Ʈ ó�� �޼ҵ� ������ view���� �����
		v.addButtonActionListener(new ActionListener()
		{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Object obj = e.getSource();    // �Ѿ�� �̺�Ʈ�� ��ü ��ȯ
			
			if(obj == v.exitButton) 	   // �Ѿ�� �̺�Ʈ�� ���� ��ư�� ��
			{
				System.exit(0);
			}  
			else if (obj == v.loginButton) 			  // �Ѿ�� �̺�Ʈ�� �α��� ��ư�� ��
			{
				v.id = v.idInput.getText();		      // �Է��� ��ȭ������ ����
				v.cardLayout.show(v.tab, "msg");
				connectServer();                      // ������ ����
				v.idInput.setText("");
			}
			else if (obj == v.logoutButton) 		  // �Ѿ�� �̺�Ʈ�� �α��� ��ư�� ��
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
			else if (obj == v.sendButton || obj == v.msgInput) 		  // �Ѿ�� �̺�Ʈ�� �α��� ��ư�� ��
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
				// ������ ������ ����Ʈ ��û �޽��� ����
				outMsg.println(gson.toJson(new Message(v.id,"","","list")));
	
				listDialog = new Participants(chatData.list);
			}
		}
		});
	}
	
	String myip = "127.0.0.1"; //�ڽ��� ��ǻ�� IP �ּ�
	
	public void connectServer() 
	{
		try
		{
			//ip�ּҿ� ��Ʈ��ȣ�� ����Ͽ� ���� ����
			socket = new Socket(myip,1234);
			logger.info("[Client]Server ���Ἲ��!!");
						
			// ����� ��Ʈ�� ����
			inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outMsg = new PrintWriter(socket.getOutputStream(),true);
			
			// JSon ���·� ������ �޽��� ����
			outMsg.println(gson.toJson(new Message(v.id,"","","login")));
			
			//�޽��� ������ ���� ������ ����
			Thread thread = new Thread(this);
			//������ ����
			thread.start();
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.warning("[MultiChatUI]connectServer() Exception �߻�!");
			e.printStackTrace();
		}
	}
	
	public void run() 
	{
		//���� �޽����� ó���ϴ� �� �ʿ��� ���� ����
		String msg;
		
		//������ ������ �����ϴ� status 
		status = true;
	
		while(status) 
		{	
			try {
					// �޽����� �����Ͽ� msg�� ����
					msg = inMsg.readLine();
					// msg���� �Ľ��� ���Ͽ� �޽��� ������ message ��ü�� ����
					m = gson.fromJson(msg, Message.class);
					
					// �޽��� Ÿ���� ä��â ������ ���� �޽����� ���
					if(m.getType().equals("member"))
					{
						clientID.add(m.getMsg());
						chatData.refreshList(clientID);
					}
					// �޽��� Ÿ���� ä�ù� �����ڰ� �α��� �ߴٴ� �޽����� ���
					else if(m.getType().equals("login"))   //login type �޽����� ���� ��
					{
						//ClientID�� ���� ���� �������� id �߰�
						clientID.add(m.getId());           
						 //view�� List�� ������ clientID�� ����
						chatData.refreshList(clientID);   
						//view�� TextArea�� ������ �α��� �˸�
						chatData.refreshData(m.getId()+" "+m.getMsg()+"\n");
					}
					// �޽��� Ÿ���� ä�ù� �����ڰ� �α׾ƿ� �ߴٴ� �޽����� ���
					else if(m.getType().equals("logout"))   //logout type �޽����� ���� ��
					{
						//ClientID�� ���� �������� id ����
						clientID.remove(m.getId()); 	
						 //view�� List�� ������ clientID�� ����
						chatData.refreshList(clientID);   
						//view�� TextArea�� ������ �α׾ƿ� �˸�
						chatData.refreshData(m.getId()+" "+m.getMsg()+"\n");
					}
					else
					{
					   //view�� TextArea�� ������ clientID�� ����
						chatData.refreshData(m.getId()+" : "+m.getMsg()+"\n");
						
						//Ŀ���� ���� ��ȭ �޽����� ǥ��
						v.msgOut.setCaretPosition(v.msgOut.getDocument().getLength());
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.warning("[MultiChatU]�޽��� ��Ʈ�� ����!!");
			}
			
		}

	}
}
