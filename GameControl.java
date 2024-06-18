import java.util.Scanner;

public class GameControl {

    Map map;
    boolean gameOver;
    Scanner s = new Scanner(System.in);
    int playerNum;
    int playDays;
    Player player1=new Player();
    Player player2=new Player();
    Player player3=new Player();
    Player player4=new Player();
    Player[] players={player1,player2,player3,player4};
    
    public static void main(String[] args) {
        GameControl monopoly=new GameControl();
        monopoly.play();
    }
    public void init(){
        map=new Map();
        map.createMap();
        gameOver=false;
       
     
       
        System.out.println("----------歡迎來到大富翁遊戲----------");
        System.out.println("規則說明:1.破產的人會輸掉遊戲\n2.當經過有主的土地時，需支付土地價值50%的過路費\n3.土地不得變賣，現金不足則為破產\n");
        System.out.println("請輸入遊玩人數(2-4人):");
        while (true) {
            playerNum=s.nextInt();
            if(playerNum>1&& playerNum<5){
               
                break;
            }else{
                System.out.println("輸入錯誤，請重新輸入");
        
            }
        }

        System.out.println("請輸入遊玩天數:");
       
        playDays=s.nextInt();
            
       
        for(int i=0;i<playerNum;i++){
            System.out.println("Player"+(i+1)+",please enter your name:");
            String scannerName=s.next();
            players[i].name=scannerName;
            
        }
        if(playerNum!=4){
            for(int i=playerNum;i<4;i++){
                players[i].position=57;
            }
        }
       
       
    }

    public void play(){
        init();
        System.out.println("----------Game Start----------");
        for(int i=0;i<playerNum;i++){
           
            System.out.println(players[i].name+":"+(i+1));
        }
        System.out.println("S:start,H:chance,L:landMine,E:denstiny,@:in same place,ABCD:Players'place");
        map.printMap(player1.position,player2.position,player3.position,player4.position);

        while(!gameOver){
            
            for(int i=0;i<playerNum;i++){
                System.out.println("現在輪到"+players[i].name);
                rollDice(i);      
               playerActivity(i);
               

                map.printMap(player1.position,player2.position,player3.position,player4.position);

                System.out.println("各玩家存款:");
                for(int j=0;j<playerNum;j++){
                System.out.println(players[j].name+":"+players[j].money);
                
                }
                


                checkGameOver();
                if(gameOver){
                    break;
                }
            }
            playDays--;
            System.out.println("----------遊戲剩下"+playDays+"天----------");
            checkGameOver();
               

    }
}
public void rollDice(int player){
    System.out.println("輸入任意文字來擲骰子");
    
    s.next();
    
    int step=(int)(Math.random()*6)+1;
    System.out.println("投擲結果:"+step);
    players[player].position +=step;
    if(players[player].position>=56){
        players[player].position-=56;
    }
    
   

}
public void playerActivity(int player){
    int placeOwner=map.map[players[player].position]-5;
   
   switch(map.map[players[player].position]){
    
    case 0:
       buyPlace(player);
   
       break;

    case 1:
    System.out.println("您抽到了一張機會卡");
    chance(player);
    break;
    case 2:
    System.out.println("您抽到了一張命運卡");
    destiny(player);
    break;
    case 3:
    System.out.println("您被地雷炸到，回到原點");
    players[player].position=0;
    break;
    case 4:
    System.out.println("您到達原點，給予200獎勵金");
    players[player].money+=200;
    break;
    case 5:
    payPassMoney(player,placeOwner);
    break;
    case 6:
    payPassMoney(player,placeOwner);
    break;
    case 7:
    payPassMoney(player,placeOwner);
    break;
    case 8:
    payPassMoney(player,placeOwner);
    break;
   
   }

  
}
public void buyPlace(int player){
    String ans="";
    System.out.println("這裡是一塊價值"+map.placePrice[players[player].position]+"的空土地");
    System.out.println("請輸入yes或no決定是否購買");
    
   
   while (true) {
    ans=s.next();
    if(ans.equals("yes")){
       
        map.map[players[player].position]=player+5;
       
        players[player].money-=map.placePrice[players[player].position];
        players[player].ownedPlace++;
        System.out.println("您已購買此土地");
        
        break;
    }else if(ans.equals("no")){
        System.out.println("您拒絕購買此土地");
       
        break;
    }else{
        System.out.println("輸入錯誤，請重新輸入");

    }
   }
}
public void chance(int player){
    int chanceCase=(int)(Math.random()*6)+1;
    switch(chanceCase){
        
        case 1:
        System.out.println("在新加坡亂吐口香糖，罰款500");
        players[player].money-=500;
        break;
        case 2:
        System.out.println("坐船被百慕達三角洲被吸進去，發現亞特蘭提斯，得到1000元");
        players[player].money+=1000;
        break;
        case 3:
        System.out.println("在韓國看到很多整型醫院，去打玻尿酸花費2000元");
        players[player].money-=2000;
        break;
        case 4:
        System.out.println("去拉斯維加斯遇到賭神，傳授賭術，賺了2000元");
        players[player].money+=2000;
        break;
        case 5:
        System.out.println("潛水的時候被鱷魚咬傷，醫藥費1500元");
        players[player].money-=1500;
        break;
        case 6:
        System.out.println("買大樂透中了200元");
        players[player].money+=200;
        break;
    }
   
}
public void destiny(int player){
    int destinyCase=(int)(Math.random()*6)+1;
    switch(destinyCase){
        case 1:
        System.out.println("去埃及看金字塔中暑，倒退一格");
        players[player].position-=1;
        break;
        case 2:
        System.out.println("到北海道擠牛奶，精神百倍前進三格");
        players[player].position+=3;
        break;
        case 3:
        System.out.println("到夏威夷亂撿被下了詛咒的石頭，倒退三步");
        players[player].position-=3;
        break;
        case 4:
        System.out.println("到貴州掉進地下伏流，隨波逐流，前進2格");
        players[player].position+=2;
        break;
        case 5:
        System.out.println("到大溪地跳草裙舞，不小心扭傷腰，倒退一格");
        players[player].position-=1;
        break;
        case 6:
        System.out.println("到歐洲遊玩，晚上不睡覺遇到吸血鬼，狂奔前進3格躲避追殺");
        players[player].position+=3;
        break;
    }
    
}
public void payPassMoney(int player,int placeOwner){
    long passMoney=Math.round(map.placePrice[players[player].position]*0.5);
    String ans="";
    if(player==placeOwner){
        System.out.println("您到達自己的領地，是否要支付500元升級房屋，過路費將會提升25%");
        while (true) {
            ans=s.next();
            if(ans.equals("yes")){
                players[player].money-=500;
                map.placePrice[players[player].position]+=map.placePrice[players[player].position]*0.25;
                System.out.println("您已升級此土地");
                
                break;
            }else if(ans.equals("no")){
                System.out.println("您拒絕升級土地");
               
                break;
            }else{
                System.out.println("輸入錯誤，請重新輸入");

            }
        }
    }else{
        System.out.println("您到達"+players[placeOwner].name+"的領地,支付過路費"+passMoney);

        players[player].money-=(int) passMoney;
        players[placeOwner].money+=(int) passMoney;
        
           }
}
public void checkGameOver(){
    if(playDays==0){
        System.out.println("----------遊戲天數已到，遊戲結束----------");
        System.out.println("遊戲現金數量結算:");
        int max=0;
                for(int j=0;j<playerNum;j++){
                    
                    if (players[j].money > max) {
                        max = players[j].money;
                    }
                System.out.println(players[j].name+"擁有的現金:"+players[j].money);
                
                
                }
                System.out.println("贏家:");
                for(int j=0;j<playerNum;j++){
                   
                    if (players[j].money == max) {
                        System.out.println(players[j].name);
                    }
               
                
                
                }

       
     gameOver=true;
        
    }else{
        for(int i=0;i<playerNum;i++){
            if(players[i].money<=0){
                gameOver=true;
                System.out.println("----------"+players[i].name+"破產，遊戲結束----------");
            }
        }
    }
    
    
}
}

