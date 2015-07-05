package snake;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog{
	
	private JTextArea jta;
	public HelpDialog(JFrame owner){
		super(owner,"����");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setModal(true);

		jta = new JTextArea();
		jta.setBackground(this.getBackground());
		jta.append("��Ϸ˵����\n");
		jta.append("1.����������ߵ��ƶ�����.\n");
		jta.append("2.����ʼ����ʼ��Ϸ.\n");
		jta.append("3.����ͣ��������ͣ��Ϸ���ٰ���ͣ�����Լ�����Ϸ.\n");
		jta.append("4.��ɫΪ��ͨʳ���һ����100��.\n");
		jta.append("5.��ɫΪ�������һ����100�֣��ñ���������Ҵ���һ������.\n");
		jta.append("6.��ɫΪ��ǽ�����һ����100�֣��ñ���������Ҵ���һ��ǽ��.\n");
		jta.append("7.����������һ��ʱ�����Զ�����. ���ﵽ��߼���ʱ��û��������.");
		jta.setFocusable(false);
		this.add(jta);
	}
}
