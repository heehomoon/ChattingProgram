
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;


public class Participants extends JDialog{

	// �����ڸ���Ʈ ȭ�� �г� ���� Component
	public JPanel listPanel;
	
	
	Participants(JList perList)
   {
		getContentPane().setPreferredSize(new Dimension(400,500));           
		pack();
		setLayout(new BorderLayout());
		setResizable(false);

		JScrollPane scrollpane1= new JScrollPane(perList);
		scrollpane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//�׻� ������ ��ũ�ѹ� �߰�    	 
		add(scrollpane1,BorderLayout.CENTER);
		
		setVisible(true);
   }
}