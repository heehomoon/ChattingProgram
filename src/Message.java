
public class Message {

	private String id; 			// 아이디
	private String passwd;      // 비밀번호
	private String msg;			// 채팅 메시지
	private String type;        // 메시지 유형 (로그인, 로그아웃, 메시지 전달)
	
	Message(){};  				// 생성자
	
	Message(String i, String p, String m, String y) // 인자를 사용하는 생성자
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
