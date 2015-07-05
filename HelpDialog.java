package snake;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog{
	
	private JTextArea jta;
	public HelpDialog(JFrame owner){
		super(owner,"帮助");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);

		jta = new JTextArea();
		jta.setBackground(this.getBackground());
		jta.append("游戏说明：\n");
		jta.append("1.方向键控制蛇的移动方向.\n");
		jta.append("2.按开始键开始游戏.\n");
		jta.append("3.按暂停键可以暂停游戏，再按暂停键可以继续游戏.\n");
		jta.append("4.黄色为普通食物，吃一个得100分.\n");
		jta.append("5.蓝色为穿身宝物，吃一个得100分，该宝物允许玩家穿过一次蛇身.\n");
		jta.append("6.红色为穿墙宝物，吃一个得100分，该宝物允许玩家穿过一次墙壁.\n");
		jta.append("7.当分数到达一定时，会自动升级. 当达到最高级别时就没有升级了.");
		jta.setFocusable(false);
		this.add(jta);
	}
}
