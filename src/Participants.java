
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;


public class Participants extends JDialog{

	// 접속자리스트 화면 패널 관련 Component
	public JPanel listPanel;
	
	
	Participants(JList perList)
   {
		getContentPane().setPreferredSize(new Dimension(400,500));           
		pack();
		setLayout(new BorderLayout());
		setResizable(false);

		JScrollPane scrollpane1= new JScrollPane(perList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//항상 세로축 스크롤바 추가    	 
		add(scrollpane1,BorderLayout.CENTER);
		
		setVisible(true);
   }
}