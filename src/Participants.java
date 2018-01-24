
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;


public class Participants extends JDialog{

	// 접속자리스트 화면 패널 관련 Component
	public JPanel listPanel;
	JScrollPane scrollpane1;
	JList pList;         // 참여자 리스트
	
	Participants()
	{
		getContentPane().setPreferredSize(new Dimension(400,500));           
		pack();
		setLayout(new BorderLayout());
		setResizable(false);
		
		pList = new JList();
		scrollpane1= new JScrollPane(pList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
		add(scrollpane1,BorderLayout.CENTER);
	}
	
	void setList()
	{
		scrollpane1.setViewportView(pList);
	}

}