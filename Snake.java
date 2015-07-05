package snake;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.*;

public class Snake {
	//���췽��
	public Snake(){
		MainFrame frame = new MainFrame();
		frame.setTitle("̰������Ϸ");
		frame.setSize(760,584);
		frame.setResizable(false);
		
		//�������������ʾ
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if(frameSize.height > screenSize.height){
			frameSize.height = screenSize.height;
		}
		if(frameSize.width > screenSize.width){
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width)/2,(screenSize.height - frameSize.height)/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new Snake();
	}
}
