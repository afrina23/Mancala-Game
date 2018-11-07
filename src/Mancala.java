import javafx.util.Pair;

import java.util.Scanner;


public class Mancala {
    static int turn;
    static Scanner scanner;
    static Boolean gameover;
    static int MIN=1;
    static  int MAX=0;
    static int MAX_DEPTH=10;
    public static void main(String[] args) {
        scanner= new Scanner(System.in);

        System.out.println("DO you want to play first? (Y/N)");
        String msg=scanner.nextLine();

        if(msg.equals("Y")||msg.equals("y")){
            turn=1;
        }
        else{
            turn =0;
        }
        gameover=false;
        Board gameBoard=new Board();
        gameBoard.initialize();
        while(!gameover){
            if(turn==MIN){
                System.out.println("----Your Move------");
                gameBoard.printBoard();
                int selection=askForChoice();

                boolean no_extra_turn=gameBoard.giveMove(MIN,selection);
                if(no_extra_turn) turn=MAX;

                if(gameBoard.checkGameOver()){
                    break;
                }


            }
            else if(turn==MAX){
                System.out.println("----PC Move------");
                gameBoard.printBoard();
                //int selection=askForChoice();
                int selection=getPCMove(gameBoard);
                boolean no_extra_turn=gameBoard.giveMove(MAX,selection);
                System.out.println("Pc has moved from hole "+selection);
                if(no_extra_turn) turn=MIN;

                if(gameBoard.checkGameOver()){
                    break;
                }

            }

        }
        int score=gameBoard.calculateScore();
        gameBoard.printBoard();
        if(score==10){
            System.out.println("You won the game");
        }
        else if(score==-10){
            System.out.println("PC has won");
        }
        else{
            System.out.println("Tie");
        }


    }
    static int askForChoice(){
        System.out.println("select a hole (5/4/3/2/1/0)");
        int selection=scanner.nextInt();
        return selection;
    }
    static int getPCMove(Board gameboard){
        Pair<Integer,Integer> move;
        int alpha=Integer.MIN_VALUE;
        int beta=Integer.MAX_VALUE;
        //System.out.println("alpha beta pruning started");
        move=minMax(gameboard,0,alpha,beta,MAX);

        return move.getKey();
    }
    static Pair<Integer,Integer> minMax(Board board,int depth,int alpha,int beta,int minOrMax){
        Pair<Integer,Integer> temp,move = null;
        Board temporaryBoard=new Board();
        boolean check=board.checkGameOver();
        if(check||depth==MAX_DEPTH){
            System.out.println("game is finished");
            int score=board.calculateScore();

            move=new Pair<>(-1,score);
            return move;
        }
        else{
            if(minOrMax==MIN){
                //System.out.println("minimizing player function");
                move=new Pair<>(-1,Integer.MAX_VALUE);
                for(int i=0;i<board.player.length-1;i++){
                    if(board.player[i]!=0){
                        //System.out.println("making empty the bin no "+i);
                        temporaryBoard.copyBoard(board);
                        board.giveMove(minOrMax,i);
                        //System.out.println("value of alpha is "+alpha+ " value of beta is "+beta+ " depth is "+depth);
                        temp=minMax(board,depth+1,alpha,move.getValue(),MAX);
                        if(temp.getValue()<beta){
                            beta=temp.getValue();
                            move=new Pair<>(i,temp.getValue());

                        }
                        if(beta <=alpha){
                            System.out.println("************pruned**************");
                            break;
                        }

                        board.copyBoard(temporaryBoard);
                    }
                }
                return move;
            }
            else if(minOrMax==MAX){
                //System.out.println("maximizing pc move");
                move=new Pair<>(-1,Integer.MIN_VALUE);
                for(int i=0;i<board.pc.length-1;i++){
                    if(board.pc[i]!=0){
                        temporaryBoard.copyBoard(board);
                        //System.out.println("making empty the bin no "+i);
                        board.giveMove(minOrMax,i);
                        //System.out.println("value of alpha is "+alpha+ " value of beta is "+beta);
                        temp=minMax(board,depth+1,move.getValue(),beta,MIN);
                        if(temp.getValue()> alpha){
                            alpha=temp.getValue();
                            move=new Pair<>(i,temp.getValue());
                        }
                        if(beta<=alpha){
                            System.out.println("************pruned**************");
                            break;
                        }
                        if(temp.getValue()>=move.getValue()){
                            move=new Pair<>(i,temp.getValue());
                        }
                        board.copyBoard(temporaryBoard);
                    }
                }
                return move;
            }
        }
        return move;

    }
}
