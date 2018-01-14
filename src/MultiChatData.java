import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextArea;

public class MultiChatData {

	JTextArea textarea; // 채팅창 대화창
	JList list;         // 참여자 리스트
	
	public void addObj(JComponent comp)
	{
		textarea = (JTextArea) comp; // 전달된 Component를 textarea에 저장
	}
	
	public void addObj(JList comp)
	{
		list =  comp; // 전달된 Component를 textarea에 저장
	}
	
	public void refreshData(String msg) 
	{
		textarea.append(msg);        // 전달된 message를 textarea에 붙임
	}
	
	public void refreshList(Vector<String> vector) 
	{
		list.setListData(vector);        // 전달된 message를 textarea에 붙임
	}

}
