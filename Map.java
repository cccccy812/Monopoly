public class Map {
    int[] map=new int[56];
    int[] chance={3,15,27,39,50};
    int[] destiny={10,20,30,40};
    int[] landMine={24,33,47,53};
    int[] placePrice=new int[56];

    public void createMap(){
        
        for(int i=0;i<5;i++){
            map[chance[i]]=1;
        }
        for(int i=0;i<4;i++){
            map[destiny[i]]=2;
        }
        for(int i=0;i<4;i++){
            map[landMine[i]]=3;
        }
        map[0]=4;
        for(int i=0;i<56;i++){
          if(map[i]==0){
            if(i<20){
                placePrice[i]=1000;
            }else if(i>=20&&i<=40){
                placePrice[i]=2000;
            }else{
                placePrice[i]=3000;
            }
          }
        }
    }
    public String getMap(int graphType,int index,int player1,int player2){
        String graph="";
        if(player1==index&&player2==index){
            graph="|@|";
        }else if(player1==index){
            graph="|A|";
        }else if(player2==index){
            graph="|B|" ;
        }else{
           switch(graphType){
            case 1:
              graph="|C|";
              break;
            case 2:
              graph="|D|";
              break;
            case 3:
              graph="|L|";
              break;
            case 4:
              graph="|S|";
              break;
            case 5:
              graph="|1|";
              break;
            case 6:
              graph="|2|";
              break;
            default:
              graph="| |";
              break;
           }
        }
        return graph;
    }
    public void printMap(int player1,int player2){
        for(int i=0;i<15;i++){
            System.out.print(getMap(map[i],i,player1,player2));
        }
        System.out.print("\n");

       
            for(int i=55,j=15;i>42&&j<28;i--,j++){
                System.out.print(getMap(map[i],i,player1,player2));
                for(int k=0;k<13;k++){
                System.out.print("   ");
                }
                System.out.print(getMap(map[j],j,player1,player2));
                System.out.print("\n");
            }
            
        
        for(int i=42;i>27;i--){
            System.out.print(getMap(map[i],i,player1,player2));
        }
        System.out.print("\n");

    }
}
