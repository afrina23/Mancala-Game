import java.util.ArrayList;
import java.util.Collection;

public class Board {
    int[] pc;
    int[] player;
    static int MIN=1;
    static  int MAX=0;

    Board(){
        pc=new int[7];
        player=new int[7];
    }
    void copyBoard(Board b){
        for(int i=0;i<b.player.length;i++){
            player[i]=b.player[i];
        }
        for(int i=0;i<b.pc.length;i++){
            pc[i]=b.pc[i];
        }
    }

    void initialize(){
        pc= new int[]{4, 4, 4, 4, 4, 4, 0};
        player= new int[]{4, 4, 4, 4, 4, 4, 0};
    }
    void printBoard(){
        System.out.println("------------Bot-1---------------");
        System.out.print("Goal-"+pc[pc.length-1]);
        for(int i=pc.length-2;i>=0;i--) System.out.print("  ("+i+")"+pc[i]+"   ");
        System.out.println();
        System.out.println();
        System.out.print("      ");
        for(int i=0;i<player.length-1;i++) System.out.print("  ("+i+")"+player[i]+"   ");
        System.out.println("Goal--"+player[player.length-1]);
        System.out.println("------------Player-1---------------");

        System.out.println();

    }
    int getScorePC(){
        return pc[6];
    }
    int getScorePlayer(){
        return player[6];
    }
    int getHoleValue(int pl,int no){
        if(pl==MAX){
            return pc[no];
        }
        else{
            return player[no];
        }
    }
    boolean giveMove(int plr,int position){
        int no_of_ball=getHoleValue(plr,position);

        if(plr==MAX){
            int start_position=position+1;
            pc[position]=0;
            while(no_of_ball>0){
                for(int i=start_position;i<pc.length && no_of_ball>0;i++){
                    pc[i]++;
                    no_of_ball--;
                    if(no_of_ball==0){
                        if(i!= pc.length-1){
                            doTheException(MAX,i);
                            return true;
                        }
                        else{
                            return false;
                        }

                    }
                }
                start_position=0;
                for(int i=0;i<player.length-1 && no_of_ball>0;i++){
                    player[i]++;
                    no_of_ball--;
                }
            }


        }
        else if(plr==MIN){
            int start_position=position+1;
            player[position]=0;
            while(no_of_ball>0){
                for(int i=start_position;i<player.length && no_of_ball>0;i++){
                    player[i]++;
                    no_of_ball--;
                    if(no_of_ball==0){
                        if(i!= pc.length-1){
                            doTheException(MIN,i);
                            return true;
                        }
                        else{
                            return false;
                        }

                    }
                }
                start_position=0;
                for(int i=0;i<pc.length-1 && no_of_ball>0;i++){
                    pc[i]++;
                    no_of_ball--;
                }
            }


        }
        return true;
    }
    void doTheException(int minOrMax,int position){
        if(minOrMax==MAX){
            if(pc[position]==1){
                int across=player.length-2-position;
                if(player[across]>0){
                    int to_go=player[across]+pc[position];
                    pc[position]=0;
                    player[across]=0;
                    pc[pc.length-1]+=to_go;
                }
                else{
                    return;
                }

            }
        }
        else{
            if(player[position]==1){
                int across=pc.length-2-position;
                if(pc[across]>0){
                    int to_go=pc[across]+player[position];
                    player[position]=0;
                    pc[across]=0;
                    player[player.length-1]+=to_go;
                }
                else{
                    return;
                }

            }

        }
    }
    boolean checkGameOver(){
        boolean gameover=true;
        for(int i=0;i<pc.length-1;i++){
            if(pc[i] !=0) gameover=false;
        }
        if(gameover) return true;
        gameover=true;
        for(int i=0;i<player.length-1;i++){
            if(player[i] !=0) gameover=false;
        }
        if(gameover) return true;

        return false;

    }

    int  calculateScore(){

        for(int i=0;i<player.length-1;i++){
            player[player.length-1]+=player[i];
            player[i]=0;
        }


        for(int i=0;i<pc.length-1;i++){
            pc[pc.length-1]+=pc[i];
            pc[i]=0;
        }
        if(pc[pc.length-1] >player[player.length-1]) return -10;
        else if(pc[pc.length-1] < player[player.length-1]) return 10;
        else return 0;


    }


}
