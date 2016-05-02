package TicTacToe;

public class Test {
	public static void main(String args[]){
/*		Scanner input_TTT=new Scanner(System.in);
		int[][] TA=new int[3][];
		TA[0]=new int[]{1,2,0};
		TA[1]=new int[]{1,1,2};
		TA[2]=new int[]{2,1,1};
		TicTacToe T=new TicTacToe();
		T.inputTTTArray(TA);
		T.test();
*/		

		
/*		手动输入
		TicTacToe T=new TicTacToe();
		T.startGame();
/**/
		
		
/*		参数输入
		int index_s,index_e;
		int new_l,new_r;
		int player=0;
		TicTacToe T=new TicTacToe();
		String input_step=new String(args[0].replace(" ", ""));
		
		player=1;
		do{
			index_s=input_step.indexOf("(");
			index_e=input_step.indexOf(")");
			if(index_s>=0&&index_s<index_e&&index_e<input_step.length()){
				new_l=Character.getNumericValue(input_step.charAt(index_s+1));
				new_r=Character.getNumericValue(input_step.charAt(index_e-1));
				input_step=new String(input_step.substring(index_e+1));
				if(T.playerPutOn(player, new_l, new_r)){
					T.drawArray();
					System.out.println("------------------");
					if(T.checkFinished()){
						if(player==T.checkWin()){
							System.out.println("Player "+player+" wins!");
							break;//胜利结束
						}
						else{
							System.out.println("Draw!");
							break;//平局结束
						}
					}
					else{
						player=player==1?2:1;
						System.out.println("Next: Player"+player);
					}
				}
			}
			else{
				break;//输入异常或流结束
			}
		}while(true);
		*/
		

		int[][] T=new int[3][3];
		T[0]=new int[]{0,0,2};
		T[1]=new int[]{1,0,0};
		T[2]=new int[]{0,0,1};
		
		AI AI_play=new AI();
		AI_play.playWithAI();
//		AI_play.test(T);
/**/
	}	
}
