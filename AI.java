package TicTacToe;

import java.util.Random;
import java.util.Scanner;
/**
 * A negative AI
 * @author Ni Shiying
 * @version 1.0.0
 *
 */
public class AI {
	private int div=TicTacToe.readDiv();
	private int[][] TTT_AI=new int[div][div];
	private int[][] TTT_judge=new int[div][div];;
	private int myPlayer=0;
	private int oppoPlayer=0;
	private int[][] tryStep=new int[9][2];
	private int tryNum=0;
	private boolean isFinished=false;
	private int winner=-1;
	private int winLine=-1;
	private int winRow=-1;
	private int winDiag=-1;
	
	
	AI(){
		this.initAI();
		this.initJudge();
	}
	
//PUBLIC METHOD
	public void playWithAI(){
		Scanner input_AI=new Scanner(System.in);
		boolean valid_input=false;
		
		
		while(valid_input==false){
			int temp;
			valid_input=false;
			System.out.println("Do you want the offensive(1) or the deffensive(2)?");
			temp=input_AI.nextInt();
			if(temp==1||temp==2){
				valid_input=true;
				this.myPlayer=3-temp;
				this.oppoPlayer=temp;
			}
		}
		input_AI.nextLine();
		
		this.initAI();
		this.initJudge();
		this.refreshJudge();
		
		if(this.myPlayer==2){
			this.drawArray();
			valid_input=false;
			while(valid_input==false){
				int l,r;
				String str=new String();
				System.out.println("Please input your step:(seperating with \",\")");
				str=input_AI.nextLine().replace(" ","");
				//System.out.println(str.indexOf(','));
				if((str.indexOf(',')>0)&&(str.indexOf(',')<str.length()-1)){
					l=Character.getNumericValue(str.charAt(str.indexOf(',')-1))-1;
					r=Character.getNumericValue(str.charAt(str.indexOf(',')+1))-1;
					if(this.playerPutOn(this.oppoPlayer,l,r)){
						this.refreshJudge();
						valid_input=true;
					}
				}
			}
		}
		

		do{
			int nextStep=this.chooseNext();
//			this.drawJudge();
//			this.drawWillLose();
			
			this.playerPutOn(this.myPlayer,nextStep/this.div,nextStep%this.div);
			if(this.isFinished()){
//				this.playerPutOn(this.myPlayer,nextStep/this.div,nextStep%this.div);
				this.drawArray();
				if(this.winner==this.myPlayer){
					System.out.println("I win!");
					break;
				}
				else{
					if(this.winner==this.oppoPlayer){
						System.out.println("You win!");
						break;
					}
					else{
						System.out.println("Draw!");
						break;
					}
				}
			}
			
//			this.drawJudge();
//			this.drawArray();
			
//			System.out.println(nextStep);
			this.drawArray();

			valid_input=false;
			while(valid_input==false){
				int l,r;
				String str=new String();
				System.out.println("Please input your step:(seperating with \",\")");
				str=input_AI.nextLine().replace(" ","");
				if((str.indexOf(',')>0)&&(str.indexOf(',')<str.length()-1)){
					l=Character.getNumericValue(str.charAt(str.indexOf(',')-1))-1;
					r=Character.getNumericValue(str.charAt(str.indexOf(',')+1))-1;
					if(this.playerPutOn(this.oppoPlayer,l,r)){
						this.refreshJudge();
						valid_input=true;
					}
				}
			}
			if(this.isFinished()){
				if(this.winner==this.myPlayer){
					System.out.println("I win!");
					break;
				}
				else{
					if(this.winner==this.oppoPlayer){
						System.out.println("You win!");
						break;
					}
					else{
						System.out.println("Draw!");
						break;
					}
				}
			}
		}while(this.isFinished==false);
		
		this.drawArray();
		input_AI.close();
	}
	
	
//TEST METHOD
	public static void main(String[] args){
		AI T=new AI();
		T.playWithAI();
	}
	
	private void inputTTT(int[][] T){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				this.TTT_AI[i][j]=T[i][j];
			}
		}
	}
	
	public void test(int[][] T){
		this.inputTTT(T);
		this.myPlayer=2;
		this.oppoPlayer=1;
		
		this.refreshJudge();
		this.drawArray();
		System.out.println("-----------------");
		this.drawJudge();
		
		System.out.println("Checkingpoint?"+this.hasCheckingPoint(this.oppoPlayer));
		System.out.println("Attackingpoint?"+this.hasAttackedPoint(this.oppoPlayer));
		System.out.println("judgenext 0 2 "+this.judgeNext(this.myPlayer, 0, 2));
		
		System.out.println("Win:"+this.playerWillWin(this.myPlayer));
		System.out.println("cheking:"+this.isChecking(this.myPlayer));
	}
	
	private void drawWillLose(){
		System.out.println("-=-=-=-=-=-=-=-=");
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				System.out.print("  "+this.willLose(this.myPlayer, i, j)+"  ");
			}
			System.out.println("");
		}
		System.out.println("-=-=-=-=-=-=-=-=");
	}
	
	
//PRIVATE METHOD
	private boolean isFilled_AI(){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.TTT_AI[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
	
	private int playerWillWinLine_AI(int player){
		for(int i=0;i<this.div;i++){
			int countMine_Line=0;
			for(int j=0;j<this.div;j++){
				if(this.TTT_AI[i][j]==player){
					countMine_Line++;
				}
				else{
					if(this.TTT_AI[i][j]!=0){
						countMine_Line=0;
						break;
					}
				}
			}
			if(countMine_Line==this.div){
				return i;
			}
		}
		return -1;
	}
	
	private int playerWillWinRow_AI(int player){
		for(int i=0;i<this.div;i++){
			int countMine_Line=0;
			for(int j=0;j<this.div;j++){
				if(this.TTT_AI[j][i]==player){
					countMine_Line++;
				}
				else{
					if(this.TTT_AI[j][i]!=0){
						countMine_Line=0;
						break;
					}
				}
			}
			if(countMine_Line==this.div){
				return i;
			}
		}
		return -1;
	}
	
	private int playerWillWinDiag_AI(int player){

		int countMine_Diag_0=0;
		int countMine_Diag_1=0;
		for(int i=0;i<this.div;i++){
			if(this.TTT_AI[i][i]==player){
				countMine_Diag_0++;
			}
			else{
				if(this.TTT_AI[i][i]!=0){
					countMine_Diag_0=0;
				}
			}
			if(this.TTT_AI[i][this.div-1-i]==player){
				countMine_Diag_1++;
			}
			else{
				if(this.TTT_AI[i][this.div-1-i]!=0){
					countMine_Diag_1=0;
				}
			}
		}
		if(countMine_Diag_0==this.div){
			return 0;
		}
		if(countMine_Diag_1==this.div){
			return 1;
		}
		return -1;
	}
	
	private boolean playerWillWin(int player){
		if(this.playerWillWinLine_AI(player)>-1||this.playerWillWinRow_AI(player)>-1||this.playerWillWinDiag_AI(player)>-1){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean isFinished(){
		if(this.playerWillWin(this.myPlayer)){
			this.winner=this.myPlayer;
			if(this.playerWillWinLine_AI(this.myPlayer)>-1){
				this.winLine=this.playerWillWinLine_AI(this.myPlayer);
				this.isFinished=true;
				return true;
			}
			if(this.playerWillWinRow_AI(this.myPlayer)>-1){
				this.winRow=this.playerWillWinRow_AI(this.myPlayer);
				this.isFinished=true;
				return true;
			}
			if(this.playerWillWinDiag_AI(this.myPlayer)>-1){
				this.winDiag=this.playerWillWinDiag_AI(this.myPlayer);
				this.isFinished=true;
				return true;
			}
		}
		else{
			if(this.playerWillWin(this.oppoPlayer)){
				this.winner=this.oppoPlayer;
				if(this.playerWillWinLine_AI(this.oppoPlayer)>-1){
					this.winLine=this.playerWillWinLine_AI(this.oppoPlayer);
					this.isFinished=true;
					return true;
				}
				if(this.playerWillWinRow_AI(this.oppoPlayer)>-1){
					this.winRow=this.playerWillWinRow_AI(this.oppoPlayer);
					this.isFinished=true;
					return true;
				}
				if(this.playerWillWinDiag_AI(this.oppoPlayer)>-1){
					this.winDiag=this.playerWillWinDiag_AI(this.oppoPlayer);
					this.isFinished=true;
					return true;
				}
			}
			else{
				if(this.isFilled_AI()){
					this.winner=0;
					this.isFinished=true;
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isChecking(int player){
		int checkingPointNum=0;
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.tryNext(player,i,j)){
					if(this.playerWillWin(player)){
						checkingPointNum++;
						}
					this.reverseNext();
				}
			}
		}
		if(checkingPointNum>=2){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean hasCheckingPoint(int player){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.tryNext(player, i, j)){
					if(this.isChecking(player)){
						this.reverseNext();
						return true;
					}
					else{
						this.reverseNext();
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasAttackedPoint(int player){
		int op=player==this.myPlayer?this.oppoPlayer:this.myPlayer;
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.tryNext(op, i, j)){
					if(this.playerWillWin(op)){
						this.reverseNext();
						return true;
					}
					this.reverseNext();
				}
			}
		}
		return false;
	}
	
	private int chooseAttackedPoint(int player){
		int op=player==this.myPlayer?this.oppoPlayer:this.myPlayer;
		if(this.hasAttackedPoint(player)){
			for(int i=0;i<this.div;i++){
				for(int j=0;j<this.div;j++){
					if(this.tryNext(op, i, j)){
						if(this.playerWillWin(op)){
							this.reverseNext();
							return i*this.div+j;
						}
						this.reverseNext();
					}
				}
			}
		}
		return -1;
	}
	
	
	private boolean willLose(int player,int line,int row){
		int op=player==this.myPlayer?this.oppoPlayer:this.myPlayer;
		int D=-1;
	
		if(this.tryNext(player, line, row)){
			if(this.playerWillWin(player)){
				this.reverseNext();
				return false;
			}	
			if(this.hasAttackedPoint(player)){
				this.reverseNext();
				return true;
			}
			if(this.isChecking(player)){
				this.reverseNext();
				return false;
			}
			if(this.hasAttackedPoint(op)){
				D=this.chooseAttackedPoint(op);
				this.tryNext(op, D/this.div, D%this.div);
				if(this.hasAttackedPoint(player)){
					D=this.chooseAttackedPoint(player);
					if(this.willLose(player, D/this.div, D%this.div)){
						this.reverseNext();
						this.reverseNext();
						return true;
					}
				}
				this.reverseNext();
			}
			else{
				for(int i=0;i<this.div*this.div;i++){
					if(this.tryNext(op, i/this.div, i%this.div)){
						if(this.hasAttackedPoint(player)){
							D=this.chooseAttackedPoint(player);
							if(this.willLose(player, D/this.div, D%this.div)){
								this.reverseNext();
								this.reverseNext();
								return true;
							}
						}
						this.reverseNext();
					}
				}
			}
			this.reverseNext();
		}
		return false;
	}
	
	private boolean tryNext(int player,int line,int row){
		if(this.TTT_AI[line][row]==0){
			this.TTT_AI[line][row]=player;
			this.tryStep[tryNum][0]=line;
			this.tryStep[tryNum][1]=row;
			this.tryNum++;
//			System.out.println("["+this.tryNum+"]"+"Try"+" "+(line+1)+","+(row+1));
			return true;
		}
		return false;
	}
	
	private boolean reverseNext(){
		if(this.tryNum==0){
			return false;
		}
		else{
			tryNum--;
			this.TTT_AI[this.tryStep[tryNum][0]][this.tryStep[tryNum][1]]=0;	
//			System.out.println("Reverse to "+tryNum);
			return true;
		}
	}
	
	private void initAI(){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				this.TTT_AI[i][j]=0;
			}
		}
	}
	
	private void initJudge(){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.TTT_AI[i][j]!=0){
					this.TTT_judge[i][j]=0;
				}
				else{
					this.TTT_judge[i][j]=1;
				}
			}
		}
	}
	
	private void refreshJudge(){
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				this.TTT_judge[i][j]=this.judgeNext(this.myPlayer, i, j);
			}
		}
	}
	
	private int judgeNext(int player,int line,int row){
		int op=player==this.myPlayer?this.oppoPlayer:this.myPlayer;
		
		if(this.TTT_AI[line][row]!=0){
			return -1;
		}
		if(this.tryNext(player, line, row)){
			if(this.playerWillWin(player)){
				this.reverseNext();
				return 10;
			}
			this.reverseNext();
		}
		if(this.tryNext(op, line, row)){
			if(this.playerWillWin(op)){
				this.reverseNext();
				return 9;
			}
			this.reverseNext();
		}
		if(this.tryNext(player, line, row)){
			if(this.isChecking(player)){
				this.reverseNext();
				return 8;
			}
			this.reverseNext();
		}
		if(this.willLose(player, line, row)){
			return 0;
		}
		return 1;
	}
	
	private int chooseNext(){
		int MaxValue=0;
		int MaxNum=0;
		int[] MaxArray=new int[this.div*this.div];
		Random rand=new Random();
			
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(this.TTT_judge[i][j]>MaxValue){
					MaxNum=1;
					MaxValue=this.TTT_judge[i][j];
					MaxArray[0]=i*this.div+j;
				}
				else{
					if(this.TTT_judge[i][j]==MaxValue){
						MaxArray[MaxNum]=i*this.div+j;
						MaxNum++;
					}
				}
			}
		}
		
		if(MaxNum==0){
			return -1;
		}

		return MaxArray[rand.nextInt(MaxNum)];

	}
	
	private void drawArray(){
		this.isFinished();
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				if(i==this.winLine||j==this.winRow||((i==j)&&(this.winDiag==0))||((i+j==this.div-1)&&(this.winDiag==1))){
					System.out.print(" ["+this.TTT_AI[i][j]+"] ");
				}
				else{
					System.out.print("  "+this.TTT_AI[i][j]+"  ");
				}
			}
			System.out.println("");
		}
	}
	
	private void drawJudge(){
		this.isFinished();
		for(int i=0;i<this.div;i++){
			for(int j=0;j<this.div;j++){
				System.out.print("  "+this.TTT_judge[i][j]+"  ");
			}
			System.out.println("");
		}
	}
	
	private boolean playerPutOn(int player,int line,int row){
		if(line>=0&&line<this.div&&row>=0&&row<this.div&&this.TTT_AI[line][row]==0){
			this.TTT_AI[line][row]=player;
			return true;
		}
		return false;
	}


}
