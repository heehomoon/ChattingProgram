import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTextArea;

public class MultiChatData {

	JTextArea textarea; // ä��â ��ȭâ
	JList list;         // ������ ����Ʈ
	
	public void addObj(JComponent comp)
	{
		textarea = (JTextArea) comp; // ���޵� Component�� textarea�� ����
	}
	
	public void addObj(JList comp)
	{
		list =  comp; // ���޵� Component�� textarea�� ����
	}
	
	public void refreshData(String msg) 
	{
		textarea.append(msg);        // ���޵� message�� textarea�� ����
	}
	
	public void refreshList(Vector<String> vector) 
	{
		list.setListData(vector);        // ���޵� message�� textarea�� ����
	}

}
