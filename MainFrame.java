package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.swing.JFrame;

import javax.swing.JFrame;

import snake.HelpDialog;

public class MainFrame extends JFrame {
	//定义菜单相关属性
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mLevel = new JMenu("难度");
	private JRadioButtonMenuItem miBegin = new JRadioButtonMenuItem("初级");
	private JRadioButtonMenuItem miMiddle = new JRadioButtonMenuItem("中级");
	private JRadioButtonMenuItem miHard = new JRadioButtonMenuItem("高级");
	
	//定义控制面板相关属性
	private JToolBar toolBar = new JToolBar();
	private JButton jbtStart = new JButton("开始");//游戏开始按钮
	private JButton jbtPause = new JButton("暂停");//游戏暂停按钮
	private JButton jbtStop = new JButton("结束");//游戏结束按钮
	private JButton jbtHelp = new JButton("帮助");//游戏帮助按钮
	
	
	//定义游戏区
	private PlayPanel playPane = new PlayPanel();
	
	private boolean isPause = false;
	private boolean isEnd = true;
	public int speed = 300;
	
	class SnakeThread extends Thread{
		public void run(){
			while(true){
				try{
					//停顿
					Thread.sleep(speed);
					//当游戏处于正常运行状态，则移动蛇身
					if(! isEnd && ! isPause){
						playPane.moveSnake();
						if(playPane.isLost()){
						isEnd = true;
						JOptionPane.showMessageDialog(null,"游戏结束！");
						isPause = false;
						playPane.clear();
						jbtStart.setEnabled(true);
						jbtPause.setText("暂停");
						jbtPause.setEnabled(false);
						jbtStop.setEnabled(false);
						}
					}
					
				}
				catch(Exception ex){}
			}
		}
	}
	SnakeThread thread = new SnakeThread();//游戏主线程
	
	
	public static final int BEGINNER = 1, MIDDLE = 2, EXPERT = 3;//游戏级别常量
	private int level = BEGINNER;//当前游戏级别
	
	HelpDialog helpDialog;	
	
	//构造方法，完成主窗体的初始构造
	public MainFrame(){
		//构造等级设定菜单
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
		
		
		//构造游戏控制工具栏
		Container contentPane = this.getContentPane();
		contentPane.add(toolBar,BorderLayout.NORTH);
		toolBar.add(jbtStart);
		toolBar.add(jbtPause);
		toolBar.add(jbtStop);
		toolBar.add(jbtHelp);
		
		//设置按钮初始状态
		jbtPause.setEnabled(false);
		jbtStop.setEnabled(false);
		
		//添加到主窗体的内容窗格中
		contentPane.add(playPane,BorderLayout.CENTER);
		
		//设置按钮不可获取焦点
		jbtStart.setFocusable(false);
		jbtPause.setFocusable(false);
		jbtStop.setFocusable(false);
		jbtHelp.setFocusable(false);
		//创建键盘事件的监听器对象
		MainFrameKeyListener keyListener = new MainFrameKeyListener();
		//将监听器注册给主窗体
		this.addKeyListener(keyListener);
		 
		//创建动作事件的监听器对象
		MainFrameActionListener actionListener = new MainFrameActionListener();
		//将监听器注册给各个控制按钮
		jbtStart.addActionListener(actionListener);
		jbtPause.addActionListener(actionListener);
		jbtStop.addActionListener(actionListener);
		
		jbtHelp.addActionListener(actionListener);

		miBegin.addActionListener(actionListener);
		miMiddle.addActionListener(actionListener);
		miHard.addActionListener(actionListener);
		
	}
	//键盘事件的监听器类
	class MainFrameKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			//判断游戏状态
			if(! isEnd && ! isPause){
				//根据用户按键，设置蛇运动方向
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
	
	//动作事件的监听器类
	class MainFrameActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//当用户点击开始时执行
			if(e.getSource()==jbtStart){
			//初始化状态
			isEnd = false;
			isPause = false;
			//创建贪吃蛇
			playPane.createSnake();
			//随机摆放食物
			playPane.createFood();
				try{
					//启动游戏
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
					jbtPause.setText("暂停");
				}else if(isPause == false){
				jbtPause.setText("继续");
				}
				isPause = ! isPause;
			}else if(e.getSource() == jbtStop){
				isEnd = true;
				isPause = false;
				playPane.clear();
				jbtStart.setEnabled(true);
				jbtPause.setText("暂停");
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