import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Game {

    Map map;
    String[] playerName=new String[2];
    boolean goAndStop1;
    boolean goAndStop2;
    boolean gameOver;
    int player1Pos;
    int player2Pos;
    int player1Money;
    int player2Money;
    List<HashMap<Integer,Integer>> playersInformation;
    Scanner s = new Scanner(System.in);
    
    public static void main(String[] args) {
        Game game=new Game();
        game.start();
    }

    public void init(){
        map=new Map();
        map.createMap();
        goAndStop1=true;
        goAndStop2=false;
        gameOver=false;
        player1Pos=0;
        player2Pos=0;
        player1Money=15000;
        player2Money=15000;
    }

    public void start(){
        init();

       

        System.out.println("----------歡迎來到大富翁遊戲----------");
        System.out.println("規則說明:1.破產的人會輸掉遊戲\n2.當經過有主的土地時，需支付土地價值50%的過路費\n3.土地不得變賣，現金不足則為破產\n");
        System.out.println("Player 1,please enter your name:");
        playerName[0]=s.next();
        System.out.println("Player 2,please enter your name:");
        playerName[1]=s.next();
        
       

        play();

    }

    public void play(){

        System.out.println("----------Game Start----------");
        System.out.println(playerName[0]+":A");
        System.out.println(playerName[1]+":B");
        System.out.println("S:start,C:chance,L:landMine,D:denstiny,@:in same place,1:A's place,2:B's place");
        map.printMap(player1Pos,player2Pos);
        while(!gameOver){
            List<Integer> playerInformation=new ArrayList<>();
       
            if(goAndStop1){
                System.out.println("現在輪到"+playerName[0]);
                player1Pos=rollDice(player1Pos);      
                playerInformation=walk(player1Pos, player1Money);
                player1Pos=playerInformation.get(0);
                player1Money=playerInformation.get(1);
                
                
                goAndStop1=false;
                goAndStop2=true;
              }else if(goAndStop2){
                System.out.println("現在輪到"+playerName[1]);
                player2Pos=rollDice(player2Pos);
                playerInformation=walk(player2Pos, player2Money);
                player2Pos=playerInformation.get(0);
                player2Money=playerInformation.get(1);
                goAndStop2=false;
                goAndStop1=true;
              }
              map.printMap(player1Pos,player2Pos);
              System.out.println("兩方存款:");
                System.out.println(playerName[0]+":"+player1Money);
                System.out.println(playerName[1]+":"+player2Money);

                if(player1Money<=0||player2Money<=0){
                    gameOver=true;
                    if(player1Money<=0){
                        System.out.println(playerName[0]+"破產，遊戲結束");
                    }else{
                        System.out.println(playerName[1]+"破產，遊戲結束");
                    }
                   
                }
        }
        
    }
    public int rollDice(int player){
        System.out.println("輸入任意文字來擲骰子");
        
        s.next();
        
        int step=(int)(Math.random()*6)+1;
        System.out.println("投擲結果:"+step);
        player +=step;
        if(player>=56){
            player-=56;
        }
        
        return player;

    }
    public List<Integer> walk(int playerPos,int playermoney){
        List<Integer> playerInformation=new ArrayList<>();
        long passMoney=Math.round(map.placePrice[playerPos]*0.5);
        String ans="";
       switch(map.map[playerPos]){
        
        case 0:
            System.out.println("這裡是一塊價值"+map.placePrice[playerPos]+"的空土地");
            System.out.println("請輸入yes或no決定是否購買");
            
           
           while (true) {
            ans=s.next();
            if(ans.equals("yes")){
                if(goAndStop1){
                    map.map[playerPos]=5;
                }else{
                    map.map[playerPos]=6;
                }
                playermoney-=map.placePrice[playerPos];
                System.out.println("您已購買此土地");
                
                break;
            }else if(ans.equals("no")){
                System.out.println("您拒絕購買此土地");
               
                break;
            }else{
                System.out.println("輸入錯誤，請重新輸入");

            }
           }
       
           break;

        case 1:
        System.out.println("您抽到了一張機會卡");
        playermoney=chance(playermoney);
        break;
        case 2:
        System.out.println("您抽到了一張命運卡");
        playerPos=destiny(playerPos);
        break;
        case 3:
        System.out.println("您被地雷炸到，回到原點");
        playerPos=0;
        break;
        case 4:
        System.out.println("您到達原點，給予200獎勵金");
        playermoney+=200;
        break;
        case 5:
       
        if(goAndStop2){
        System.out.println("您到達"+playerName[0]+"的領地,支付過路費"+map.placePrice[playerPos]*0.25);

        playermoney-=(int) passMoney;
        player1Money+=(int) passMoney;
        
        }else{
            System.out.println("您到達自己的領地，是否要支付500元升級房屋，過路費將會提升25%");
            while (true) {
                ans=s.next();
                if(ans.equals("yes")){
                    playermoney-=500;
                    map.placePrice[playerPos]+=map.placePrice[playerPos]*0.25;
                    System.out.println("您已升級此土地");
                    
                    break;
                }else if(ans.equals("no")){
                    System.out.println("您拒絕升級土地");
                   
                    break;
                }else{
                    System.out.println("輸入錯誤，請重新輸入");
    
                }
               }
        }
        break;
        case 6:
        
        if(goAndStop1){
        System.out.println("您到達"+playerName[1]+"的領地,支付過路費"+map.placePrice[playerPos]*0.25);
        playermoney-=(int) passMoney;
        player2Money+=(int) passMoney;
        }else{
            System.out.println("您到達自己的領地，是否要支付500元升級房屋，過路費將會提升25%");
            while (true) {
                ans=s.next();
                if(ans.equals("yes")){
                    playermoney-=500;
                    map.placePrice[playerPos]+=map.placePrice[playerPos]*0.25;
                    System.out.println("您已購買此土地");
                    
                    break;
                }else if(ans.equals("no")){
                    System.out.println("您拒絕升級土地");
                   
                    break;
                }else{
                    System.out.println("輸入錯誤，請重新輸入");
    
                }
               }
        }
        break;
       
       }
       playerInformation.add(playerPos);
       playerInformation.add(playermoney);
       return playerInformation;
    }
    public int chance(int playermoney){
        int chanceCase=(int)(Math.random()*6)+1;
        switch(chanceCase){
            
            case 1:
            System.out.println("在新加坡亂吐口香糖，罰款500");
            playermoney-=500;
            break;
            case 2:
            System.out.println("坐船被百慕達三角洲被吸進去，發現亞特蘭提斯，得到1000元");
            playermoney+=1000;
            break;
            case 3:
            System.out.println("在韓國看到很多整型醫院，去打玻尿酸花費2000元");
            playermoney-=2000;
            break;
            case 4:
            System.out.println("去拉斯維加斯遇到賭神，傳授賭術，賺了2000元");
            playermoney+=2000;
            break;
            case 5:
            System.out.println("潛水的時候被鱷魚咬傷，醫藥費1500元");
            playermoney-=1500;
            break;
            case 6:
            System.out.println("買大樂透中了200元");
            playermoney+=200;
            break;
        }
        return playermoney;
    }
    public int destiny(int playerPos){
        int destinyCase=(int)(Math.random()*6)+1;
        switch(destinyCase){
            case 1:
            System.out.println("去埃及看金字塔中暑，倒退一格");
            playerPos-=1;
            break;
            case 2:
            System.out.println("到北海道擠牛奶，精神百倍前進三格");
            playerPos+=3;
            break;
            case 3:
            System.out.println("到夏威夷亂撿被下了詛咒的石頭，倒退三步");
            playerPos-=3;
            break;
            case 4:
            System.out.println("到貴州掉進地下伏流，隨波逐流，前進2格");
            playerPos+=2;
            break;
            case 5:
            System.out.println("到大溪地跳草裙舞，不小心扭傷腰，倒退一格");
            playerPos-=1;
            break;
            case 6:
            System.out.println("到歐洲遊玩，晚上不睡覺遇到吸血鬼，狂奔前進3格躲避追殺");
            playerPos+=3;
            break;
        }
        return playerPos;
    }
}