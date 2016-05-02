package TicTacToe;

import java.util.Scanner;

public class TicTacToe {
	private static int div=3;
	private int[][] TTT=new int[TicTacToe.div][TicTacToe.div];
	private int presentStep=0;
	private int winner=0;
	private int winRow=0;
	private int winLine=0;
	private int winDiag=0;
	private boolean isFinished=false;
	
	
	TicTacToe(){
		this.init();
	}
	
	TicTacToe(int[][] T){//残局设置
		this.inputTTTArray(T);
	}
	
	TicTacToe(int p){//先行设置
		this.init();
		this.presentStep=p-1;
	}
//==========公共接口==========
	public boolean checkFinished(){
		return this.isFinished;
	}
	
	public int checkWin(){
		return this.winner;
	}
	
	public boolean checkDraw(){
		if(this.isFilled()&&this.winner==0){
			return true;
		}
		return false;
	}
	
	public boolean playerPutOn(int player,int line,int row){
		if(line>=TicTacToe.div||line<0||row>=TicTacToe.div||row<0){
			return false;
		}
		if(this.TTT[line][row]==0){
			this.TTT[line][row]=player;
			return true;
		}
		return false;
	}
	
	public void startGame(){
		Scanner input=new Scanner(System.in);
		String str;
		int l,r,p;
		
		this.init();
		this.drawArray();
		while(!this.isFinished){
			System.out.println("===========================");
			System.out.println("Step "+(this.presentStep+1)+"(Use\',\'to seperate line and row):");
			str=new String(input.nextLine().trim().replace(" ",""));		
			if(str.indexOf(',')>0&&str.indexOf(',')<str.length()){//输入格式合法
				l=Character.getNumericValue(str.charAt(str.indexOf(',')-1))-1;
				r=Character.getNumericValue(str.charAt(str.indexOf(',')+1))-1;
				p=this.presentStep%2+1;
				if(this.playerPutOn(p,l,r)){//输入坐标合法
					this.presentStep++;
				}
				else{
					System.out.println("Invalid input!");
				}
			}
			this.drawArray();
		}
		System.out.println("===========================");
		if(this.isFilled()){
			System.out.println("Draw!");
		}
		else{
			System.out.println("Winner is player"+this.winner);
		}
		input.close();
		
	}
	
	public void drawArray(){
		this.isFinished();
		for(int i=0;i<TicTacToe.div;i++){
			for(int j=0;j<TicTacToe.div;j++){
				if(i==this.winLine-1||j==this.winRow-1||((i==j)&&(this.winDiag==1))||((i+j==TicTacToe.div-1)&&(this.winDiag==2))){
					System.out.print(" ["+this.TTT[i][j]+"] ");
				}
				else{
					System.out.print("  "+this.TTT[i][j]+"  ");
				}
			}
			System.out.println("");
		}
	}
	
	public int readArray(int line,int row){
		if(line>0&&line<=TicTacToe.div&&row>0&&row<=TicTacToe.div){
			return this.TTT[line][row];
		}
		else{
			return -1;
		}
	}
	
	public static int readDiv(){
		return TicTacToe.div;
	}
	
	
//==========内部方法==========	
	private void init(){
		for(int i=0;i<TicTacToe.div;i++){
			for(int j=0;j<TicTacToe.div;j++){
				this.TTT[i][j]=0;
			}
		}
		this.presentStep=0;
		this.winner=0;
		this.winRow=0;
		this.winLine=0;
		this.winDiag=0;
		this.isFinished=false;
	}
	
	private boolean checkLine(int player,int line){
		for(int i=0;i<TicTacToe.div;i++){
			if(this.TTT[line][i]!=player){
				return false;
			}
		}
		return true;
	}
	
	private boolean checkRow(int player,int row){
		for(int i=0;i<TicTacToe.div;i++){
			if(this.TTT[i][row]!=player){
				return false;
			}
		}
		return true;
	}
	

	private boolean checkDiag(int player,int direc){
		if(direc==0){
			for(int i=0;i<TicTacToe.div;i++){
				if(this.TTT[i][i]!=player){
					return false;
				}
			}
		}
		else if(direc==1){
			for(int i=0;i<TicTacToe.div;i++){
				if(this.TTT[i][TicTacToe.div-i-1]!=player){
					return false;
				}
			}
		}
		else{
			return false;
		}
		return true;
	}
	
	private boolean isFilled(){
		for(int i=0;i<TicTacToe.div;i++){
			for(int j=0;j<TicTacToe.div;j++){
				if(this.TTT[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isFinished(){
		for(int player_n=1;player_n<=2;player_n++){
			for(int i=0;i<TicTacToe.div;i++){
				if(this.checkRow(player_n, i)){
					this.winner=player_n;
					this.winRow=i+1;
					this.isFinished=true;
					return true;
				}
				if(this.checkLine(player_n, i)){
					this.winner=player_n;
					this.winLine=i+1;
					this.isFinished=true;
					return true;
				}
			}
			for(int i=0;i<2;i++){
				if(this.checkDiag(player_n, i)){
					this.winner=player_n;
					this.winDiag=i+1;
					this.isFinished=true;
					return true;
				}
			}
		}
		if(this.isFilled()){
			this.isFinished=true;
			return true;
		}
		return false;
	}
	
//==========测试方法==========
	private void inputTTTArray(int[][] Tarray){
		for(int i=0;i<TicTacToe.div;i++){
			for(int j=0;j<TicTacToe.div;j++){
				this.TTT[i][j]=Tarray[i][j];
			}
		}
	}
	
	private void test(){
		this.drawArray();
		if(this.isFinished()){
			System.out.println("Winner is player"+this.winner);
		}
		else{
			if(this.isFilled()){
				System.out.println("Draw!");
			}
			else{
				System.out.println("Not finished!");
			}
		}
	}

	
}
