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
	private final MultiChatUI v;
	//������ Ŭ���� ���� ��ü
	private final MultiChatData chatData;
	
	Logger logger; 				// ���� ������� Ȯ���� ���� logger
	
	BufferedReader inMsg;	  	// �Է� ��Ʈ�� ����
	PrintWriter outMsg;			// ��� ��Ʈ�� ����
	
	Socket socket;              // ������ Ŭ���̾�Ʈ ���̸� �������ִ� ����
	Thread thread;              //������ ��ü
	Message m;                  //�޽��� ��ü

	Vector<String> clientID = new Vector<String>();    //���� �����ϰ� �ִ� ������ ID�� �������� Vector
	
	boolean bshowList = false;  //������ ���� ��ư�� �������� �����ִ� flag
	boolean status;             //������ ���� ������ ���� ����
	
	Gson gson = new Gson();     //Gson ��ü ����
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Ŭ���̾�Ʈ ����
		MultiChatController app = new MultiChatController(new MultiChatData(),new MultiChatUI());
		app.appMain();		
		
		//���� ����
		//MultichatServer server = new MultichatServer();
		//���� start
		//server.start();
	}
	
	public MultiChatController(MultiChatData chatData, MultiChatUI v) {
		
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
				System.exit(0);  		   // �ý��� ����
			}
			else if (obj == v.loginButton) 			  // �Ѿ�� �̺�Ʈ�� �α��� ��ư�� ��
			{
				v.id = v.idInput.getText();		      // �Է��� ��ȭ������ ����
				v.outLabel.setText("��ȭ��: " + v.id);  // �Է��� ��ȭ�� ������ 
				v.cardLayout.show(v.tab,"logout");    // logout �гη� ����
				connectServer();                      // ������ ����
			}
			else if (obj == v.showPeople) 			  // �Ѿ�� �̺�Ʈ�� ������ ���� ��ư�� ��
			{
				if(bshowList == false)                // ���� ������ ���� ���°� �ƴϸ�
				{
					clientID.clear();				  // clientID �ʱ�ȭ
					
					// ������ ������ ����Ʈ ��û �޽��� ����
					outMsg.println(gson.toJson(new Message(v.id,"","","list")));
					// ��ư �̸� ����
					v.showPeople.setText("ä��â����");
					// ��� �� ����
					v.outLabel.setText("���� ������ ����Ʈ"); 
					// ������ ����Ʈ �гη� ����
					v.cardLayout2.show(v.tab2,"list");    
					// ���� ������ ���� �����̹Ƿ� true�� ����
					bshowList = true;
				}
				else
				{
					// ��ư �̸� ����
					v.showPeople.setText("������ ����");
					// �Է��� ��ȭ������ ����
					v.id = v.idInput.getText();		     
					v.outLabel.setText("��ȭ��: " + v.id);  
					// ä�ù� ��ȭâ �гη� ����
					v.cardLayout2.show(v.tab2,"text"); 
					// ���� ������ ���� ���°� �ƴϹǷ� false�� ����
					bshowList = false;
				}
			}
			else if(obj == v.logoutButton) //�Ѿ�� �̺�Ʈ�� �α׾ƿ� ��ư�� ��
			{
				// ������ ������ �α׾ƿ� �޽��� ����
				outMsg.println(gson.toJson(new Message(v.id,"","","logout")));
				
			    // ��ȭ â Ŭ����
				v.msgOut.setText("");
				// ��ȭ�� �Է�â Ŭ���� 
				v.idInput.setText("");
				
				//��ȭâ �гη� ��ȯ
				v.cardLayout2.show(v.tab2,"text");   
				//�α��� �гη� ��ȯ
				v.cardLayout.show(v.tab, "login");
				
				//��� ��Ʈ�� ����
				outMsg.close();
				
				try {
					//�Է� ��Ʈ�� ����
					inMsg.close();
					//���� ����
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//������ ���Ḧ ���� status false�� ����
				status = false;
			}
			else if(obj == v.msgInput)
			{
				//�޽��� ����
				outMsg.println(gson.toJson(new Message(v.id,"",v.msgInput.getText(),"msg")));
				//�Է�â Ŭ����
				v.msgInput.setText("");
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
						chatData.refreshData(m.getId()+":"+m.getMsg()+"\n");
					}
					// �޽��� Ÿ���� ä�ù� �����ڰ� �α׾ƿ� �ߴٴ� �޽����� ���
					else if(m.getType().equals("logout"))   //logout type �޽����� ���� ��
					{
						//ClientID�� ���� �������� id ����
						clientID.remove(m.getId()); 	
						 //view�� List�� ������ clientID�� ����
						chatData.refreshList(clientID);   
						//view�� TextArea�� ������ �α׾ƿ� �˸�
						chatData.refreshData(m.getId()+":"+m.getMsg()+"\n");
					}
					else
					{
					   //view�� TextArea�� ������ clientID�� ����
						chatData.refreshData(m.getId()+":"+m.getMsg()+"\n");
						
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
