
public class Message {

	private String id; 			// ���̵�
	private String passwd;      // ��й�ȣ
	private String msg;			// ä�� �޽���
	private String type;        // �޽��� ���� (�α���, �α׾ƿ�, �޽��� ����)
	
	Message(){};  				// ������
	
	Message(String i, String p, String m, String y) // ���ڸ� ����ϴ� ������
	{
		this.id = i;
		this.passwd = p;
		this.msg = m;
		this.type = y;
	}
	
	// getter/setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
