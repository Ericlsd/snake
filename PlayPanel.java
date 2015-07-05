package snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayPanel extends JPanel{
	//״̬���������
	private JLabel jlScore = new JLabel("0");
	private JLabel jlThroughBody = new JLabel("0");
	private JLabel jlThroughWall = new JLabel("0");
	private int score = 0;
	private int throughBody = 0;
	private int throughWall = 0;
	
	//������Ϸ���������
	private static final int ROWS = 30;
	private static final int COLS = 50;
	private JButton[][] playBlocks;
	
	//�����������
	private int length = 3;
	private int[] rows = new int[ROWS*COLS];
	private int[] columes = new int[ROWS*COLS];
	public static final int UP = 1,LEFT = 2,DOWN = 3,RIGHT = 4;
	private int direction = RIGHT;
	private int lastdirection = RIGHT;
	private boolean lost = false;
	
	//��������
	public void createSnake(){
		length = 3;
		score = 0;
		throughBody = 0;
		throughWall = 0;
		lost=false;
		direction = RIGHT;
		lastdirection = RIGHT;
		
		for(int i = 0; i <= length; i++){
			rows[i] = 1;
			columes[i] = length - i;
		}
		for(int i = 0; i <= length; i++){
			playBlocks[rows[i]][columes[i]].setBackground(Color.green);
			playBlocks[rows[i]][columes[i]].setVisible(true);
		}
	}
	
	//���췽��
	public PlayPanel(){
		//����״̬��
		JPanel statusPane = new JPanel();
		statusPane.add(new JLabel("�÷֣�"));
		statusPane.add(jlScore);
		statusPane.add(new JLabel("�����"));
		statusPane.add(jlThroughBody);
		statusPane.add(new JLabel("��ǽ���"));
		statusPane.add(jlThroughWall);
		
		//������Ϸ�����
		JPanel showPane = new JPanel();//��ʾ�����˶�����Ϸ�����
		showPane.setLayout(new GridLayout(ROWS,COLS,0,0));
		//���ñ߿�
		showPane.setBorder(BorderFactory.createEtchedBorder());
		playBlocks = new JButton[ROWS][COLS];
		for (int i = 0; i < ROWS; i++){
			for (int j = 0;j < COLS; j++){
				playBlocks[i][j] = new JButton();
				playBlocks[i][j].setBackground(Color.LIGHT_GRAY);
				playBlocks[i][j].setVisible(false);    //�����������ʱ�ĳ�false
				playBlocks[i][j].setEnabled(false);
				showPane.add(playBlocks[i][j]);
			}
		}
		this.setLayout(new BorderLayout());
		this.add(statusPane,BorderLayout.NORTH);
		this.add(showPane,BorderLayout.CENTER);
	}
	
	//����Ϸ�����������ʳ��
	public void createFood(){
		int x = 0;
		int y = 0;
		do{
			x = (int)(Math.random() * ROWS);
			y = (int)(Math.random() * COLS);
		}while(playBlocks[x][y].isVisible());
		int random = (int)(Math.random() * 10);
		if(random < 7){
			playBlocks[x][y].setBackground(Color.yellow);
		}
		else if (random < 9){
			playBlocks[x][y].setBackground(Color.blue);
		}
		else{
			playBlocks[x][y].setBackground(Color.red);
		}
		playBlocks[x][y].setVisible(true);
	}
	
	//�ƶ���
	public void moveSnake(){
		//ȥ����ͷ
		playBlocks[rows[length]][columes[length]].setVisible(false);
		playBlocks[rows[length]][columes[length]].setBackground(Color.lightGray);
		//��ʾ����ͷ������
		for(int i = 0; i < length; i++){
			playBlocks[rows[i]][columes[i]].setBackground(Color.green);
			playBlocks[rows[i]][columes[i]].setVisible(true);
		}
		//�ƶ�����ͷ������
		for(int i = length; i > 0; i--){
			rows[i] = rows[i - 1];
			columes[i] = columes[i - 1];
		}
		//���������˶����򣬾�����ͷλ��
		switch (direction){
			case UP:{
				if(lastdirection == DOWN)
					rows[0] += 1;
				else{
					rows[0] -= 1;
					lastdirection = UP;
				}
				break;
			}
			case LEFT:{
				if(lastdirection == RIGHT)
					columes[0] += 1;
				else{
					columes[0] -= 1;
					lastdirection = LEFT;
				}
				break;
			}
			case DOWN:{
				if(lastdirection == UP)
					rows[0] -= 1;
				else{
					rows[0] += 1;
					lastdirection = DOWN;
				}
				break;
			}
			case RIGHT:{
				if(lastdirection == LEFT)
					columes[0] -= 1;
				else{
					columes[0] += 1;
					lastdirection = RIGHT;
				}
				break;
			}
		
		}
		
		//������ͷ����ǽ��ʱ����
		if(rows[0] >= ROWS || rows[0] < 0 || columes[0] >= COLS || columes[0] <0){
			if(throughWall != 0){
				throughWall--;
				jlThroughWall.setText(Integer.toString(throughWall));
				if(rows[0] >= ROWS){
					rows[0] = 0;
				}
				else if(rows[0] < 0){
					rows[0] = ROWS - 1;
				}
				else if(columes[0] >= COLS){
					columes[0] = 0;
				}
				else if(columes[0] < 0){
					columes[0] = COLS - 1;
				}
			}
			else{
				lost = true;
				return;
			}
		}
		//��ͷ��������ʱ�Ĵ������
		if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.green)){
			if(throughBody != 0){
				throughBody--;
				jlThroughBody.setText(Integer.toString(throughBody));
			}
			else{
				lost = true;
				return;
			}
		}
		//��ͷ����ʳ�������ӳ����������ʾ��һ��ʳ�����
		if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.yellow)
				||playBlocks[rows[0]][columes[0]].getBackground().equals(Color.blue)
				||playBlocks[rows[0]][columes[0]].getBackground().equals(Color.red)){
			length++;
			createFood();
			score +=100;
			jlScore.setText(Integer.toString(score));
			
			if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.blue)){
				throughBody++;
				jlThroughBody.setText(Integer.toString(throughBody));
			}
			if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.red)){
				throughWall++;
				jlThroughWall.setText(Integer.toString(throughWall));
			}
		}
		
		//��ʾ��ͷ
		playBlocks[rows[0]][columes[0]].setBackground(Color.green);
		playBlocks[rows[0]][columes[0]].setVisible(true);
	}
	
	//�����Ϸ��
	public void clear(){
		score = 0;
		throughBody = 0;
		throughWall = 0;
		jlScore.setText("0");
		jlThroughBody.setText("0");
		jlThroughWall.setText("0");
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playBlocks[i][j].setBackground(Color.LIGHT_GRAY);
				playBlocks[i][j].setVisible(false);
			}
		}
	}
	
	
	//��ȡ��Ϸ״̬
	public boolean isLost(){
		return lost;
	}
	
	//�����ߵ����з���
	public void setSnakeDirection(int direction){
		this.direction = direction;
	}
	
}