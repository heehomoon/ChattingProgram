
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;


public class Participants extends JDialog{

	// �����ڸ���Ʈ ȭ�� �г� ���� Component
	public JPanel listPanel;
	JScrollPane scrollpane1;
	JList pList;         // ������ ����Ʈ
	
	Participants()
	{
		getContentPane().setPreferredSize(new Dimension(400,500));           
		pack();
		setLayout(new BorderLayout());
		setResizable(false);
		
		pList = new JList();
		scrollpane1= new JScrollPane(pList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
		add(scrollpane1,BorderLayout.CENTER);
	}
	
	void setList()
	{
		scrollpane1.setViewportView(pList);
	}

}