package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.swing.JFrame;

import javax.swing.JFrame;

import snake.HelpDialog;

public class MainFrame extends JFrame {
	//����˵��������
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mLevel = new JMenu("�Ѷ�");
	private JRadioButtonMenuItem miBegin = new JRadioButtonMenuItem("����");
	private JRadioButtonMenuItem miMiddle = new JRadioButtonMenuItem("�м�");
	private JRadioButtonMenuItem miHard = new JRadioButtonMenuItem("�߼�");
	
	//�����������������
	private JToolBar toolBar = new JToolBar();
	private JButton jbtStart = new JButton("��ʼ");//��Ϸ��ʼ��ť
	private JButton jbtPause = new JButton("��ͣ");//��Ϸ��ͣ��ť
	private JButton jbtStop = new JButton("����");//��Ϸ������ť
	private JButton jbtHelp = new JButton("����");//��Ϸ������ť
	
	
	//������Ϸ��
	private PlayPanel playPane = new PlayPanel();
	
	private boolean isPause = false;
	private boolean isEnd = true;
	public int speed = 300;
	
	class SnakeThread extends Thread{
		public void run(){
			while(true){
				try{
					//ͣ��
					Thread.sleep(speed);
					//����Ϸ������������״̬�����ƶ�����
					if(! isEnd && ! isPause){
						playPane.moveSnake();
						if(playPane.isLost()){
						isEnd = true;
						JOptionPane.showMessageDialog(null,"��Ϸ������");
						isPause = false;
						playPane.clear();
						jbtStart.setEnabled(true);
						jbtPause.setText("��ͣ");
						jbtPause.setEnabled(false);
						jbtStop.setEnabled(false);
						}
					}
					
				}
				catch(Exception ex){}
			}
		}
	}
	SnakeThread thread = new SnakeThread();//��Ϸ���߳�
	
	
	public static final int BEGINNER = 1, MIDDLE = 2, EXPERT = 3;//��Ϸ������
	private int level = BEGINNER;//��ǰ��Ϸ����
	
	HelpDialog helpDialog;	
	
	//���췽�������������ĳ�ʼ����
	public MainFrame(){
		//����ȼ��趨�˵�
		this.setJMenuBar(menuBar);
		menuBar.add(mLevel);
		ButtonGroup group = new ButtonGroup();
		group.add(miBegin);
		group.add(miMiddle);
		group.add(miHard);
		mLevel.add(miBegin);
		mLevel.add(miMiddle);
		mLevel.add(miHard);
		miBegin.setSelected(true);
		
		
		//������Ϸ���ƹ�����
		Container contentPane = this.getContentPane();
		contentPane.add(toolBar,BorderLayout.NORTH);
		toolBar.add(jbtStart);
		toolBar.add(jbtPause);
		toolBar.add(jbtStop);
		toolBar.add(jbtHelp);
		
		//���ð�ť��ʼ״̬
		jbtPause.setEnabled(false);
		jbtStop.setEnabled(false);
		
		//��ӵ�����������ݴ�����
		contentPane.add(playPane,BorderLayout.CENTER);
		
		//���ð�ť���ɻ�ȡ����
		jbtStart.setFocusable(false);
		jbtPause.setFocusable(false);
		jbtStop.setFocusable(false);
		jbtHelp.setFocusable(false);
		//���������¼��ļ���������
		MainFrameKeyListener keyListener = new MainFrameKeyListener();
		//��������ע���������
		this.addKeyListener(keyListener);
		 
		//���������¼��ļ���������
		MainFrameActionListener actionListener = new MainFrameActionListener();
		//��������ע����������ư�ť
		jbtStart.addActionListener(actionListener);
		jbtPause.addActionListener(actionListener);
		jbtStop.addActionListener(actionListener);
		
		jbtHelp.addActionListener(actionListener);

		miBegin.addActionListener(actionListener);
		miMiddle.addActionListener(actionListener);
		miHard.addActionListener(actionListener);
		
	}
	//�����¼��ļ�������
	class MainFrameKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			//�ж���Ϸ״̬
			if(! isEnd && ! isPause){
				//�����û��������������˶�����
				if(e.getKeyCode() == KeyEvent.VK_UP){
					playPane.setSnakeDirection(PlayPanel.UP);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					playPane.setSnakeDirection(PlayPanel.DOWN);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					playPane.setSnakeDirection(PlayPanel.LEFT);
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					playPane.setSnakeDirection(PlayPanel.RIGHT);
				}
			}
		}
	}
	
	//�����¼��ļ�������
	class MainFrameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//���û������ʼʱִ��
			if(e.getSource()==jbtStart){
			//��ʼ��״̬
			isEnd = false;
			isPause = false;
			//����̰����
			playPane.createSnake();
			//����ڷ�ʳ��
			playPane.createFood();
				try{
					//������Ϸ
					thread.start();
				}
				catch (Exception ex){
			}
				jbtStart.setEnabled(false);
				jbtPause.setEnabled(true);
				jbtStop.setEnabled(true);
		}
			else if(e.getSource() == jbtPause){
				if(isPause == true){
					jbtPause.setText("��ͣ");
				}else if(isPause == false){
				jbtPause.setText("����");
				}
				isPause = ! isPause;
			}else if(e.getSource() == jbtStop){
				isEnd = true;
				isPause = false;
				playPane.clear();
				jbtStart.setEnabled(true);
				jbtPause.setText("��ͣ");
				jbtPause.setEnabled(false);
				jbtStop.setEnabled(false);
			
		}else if(e.getSource() == jbtHelp){
			if(helpDialog == null){
				helpDialog = new HelpDialog(null);
			}
			helpDialog.setVisible(true);
		}
			if(e.getSource() == miBegin){
				speed = 300;
			}else if(e.getSource() == miMiddle){
				speed = 200;
			}else if(e.getSource() == miHard){
				speed = 100;
			}
	}
}
}