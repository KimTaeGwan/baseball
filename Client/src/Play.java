
public class Play 
{
	MyNet net;
  
   int outcount = 0;
   int strikecount = 0;
   int ballcount = 0;
   int base1 = 0;
   int base2 = 0;
   int base3 = 0;
   int h_tasun=0; 
   int a_tasun=0; 
   int gongsu=0; 
   int h_score=0;
   int a_score=0;
   
   public Play(MyNet _net){  net=_net;}

  
public void playgame() throws InterruptedException
   {
	System.out.println("~~~~");

	  if("경기일정".equals(net.SendRecv("실행#")))
	  {
		  System.out.println("~~~~");
		  int i =0;
	      while(true)
	      {
	         if(i==1)
	         {
	            i=0;
	            break;
	         }
	         Start(App.awayteam[i],App.hometeam[i], App.matchnum[i]);
	         i++;
	      }
	  }
   }
     
   
   //게임시작
   public void Start(Team away, Team home, int m_num ) throws InterruptedException
   {      
      System.out.println(away.team_name+" vs "+home.team_name + " 시작!");
      String Pitcher = null;
      String Batter =null;
      

      //1.선공은 홈팀으로 항상 시작한다
      //2.각 팀의 투수와 타자를 불러온다 (타자는 아웃될시 다음타자를 불러와야함)
      //3.아웃카운트가 3일때 공수 교대  ----> 이닝교대   -->9회말 종료시 게임종료
      //4.공을던지고 그에 대한 처리를 한다.

      while(true)
      {
         int num = gongsu%2;
         if(gongsu==18)
         {
            break;
         }
         if(num==0)//홈팀공격시
         {
            Pitcher = away.pitcher_name;
            Batter = home.batter_name[h_tasun];
            System.out.println("[투수]"+ Pitcher + "  [타자]"+Batter );
            Pitching(Pitcher,Batter);
         }
         else if(num==1)//어웨이 공격시
         {
            Pitcher = home.pitcher_name;
            Batter = away.batter_name[a_tasun];
            System.out.println("[투수]"+ Pitcher + "  [타자]"+Batter );
            Pitching(Pitcher,Batter);
         }
      }
     
      System.out.println("================게임종료===================");
      System.out.println(away.team_name+ "["+a_score+"]" + ":" +home.team_name +"["+h_score+"]");
      if(a_score>h_score)
      {
         System.out.println(away.team_name +"승리!!");
//         db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,0);
      }
      else if(a_score<h_score)
      {
         System.out.println(home.team_name +"승리!!");
//         db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,0); 
      } 
      else if(a_score==h_score)
      {
         System.out.println("무승부");
 //        db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,1);  
      }
      System.out.println("=========================================");
//      db.Rotation(m_num,away.pitcher_number,home.pitcher_number);
      
      Refresh();

   }
   
   public void Pitching(String p, String b)
   {
      
      System.out.println("투수 피칭");
      int random = (int) (Math.random() * 100 + 1); 
      int score=0;
      
      if(random>=1 && random<=28) //스트라이크
      {
         
         if(strikecount ==2)
         {
            System.out.println("스트라이크 삼진!!");
            out(1);
         }
         else
         {
            System.out.println("스트라이크!");
            strikecount++;
         }

      }
      else if(random>=29 && random<=50)//볼
      {
         
         if(ballcount ==3)
         {
            System.out.println("볼넷");            
            strikecount=0;
            ballcount=0;
            score=JinRu(6);
            BatterChange();
         }
         else
         {
            ballcount++;
            System.out.println(ballcount+"볼");
            
         }
            
      }
      else if(random>=51 && random<=75) //안타(홈런)
      {
         //1루45 2루30 3루10 홈런15
         int ran = (int) (Math.random() * 101);
         if(ran>=1 && ran<=45)
         {
            System.out.println("1루타!");
            score=JinRu(1);
            strikecount=0;
            ballcount=0;
            BatterChange();
         }
         else if(ran>=46 && ran<=75)
         {
            System.out.println("2루타!");
            score=JinRu(2);
            strikecount=0;
            ballcount=0;
            BatterChange();
            
            
         }
         else if(ran>=76 && ran<=85)
         {
            System.out.println("3루타!");
            score=JinRu(3);
            strikecount=0;
            ballcount=0;
            BatterChange();
            
         }
         else if(ran>=86 && ran<=100)
         {
            System.out.println("홈런!!!");
            score=JinRu(4);
            strikecount=0;
            ballcount=0;
            BatterChange();
         }
      }
      else if(random>=76 && random<=77) //데드볼
      {
         System.out.println("데드볼!");
         score=JinRu(5);
         strikecount=0;
         ballcount=0;
         BatterChange();
      }
      
      else if(random>=81 && random<=100)
      {
         int ran = (int) (Math.random() * 2+1);
         
         if(ran ==1)
         {
            System.out.println("땅볼아웃!");
            out(1);

         }
         else if(ran==2)
         {
            System.out.println("플라이아웃!");
            out(1);
         }
      }
      if(score!=0)
      {
         GetScore(score);
      }
   }
   
   public void GetScore(int score)
   {
      int num = gongsu%2;
      if(num==0) // 홈팀공격일때
      {
         if(h_tasun>=8)
            h_tasun=0;
         else
         h_tasun++;
      }
      else if(num==1) //어웨이팀 공격일때
      {
         if(a_tasun>=8)
            a_tasun=0;
         else
         a_tasun++;
      }
      
   }
   
   
   public void out(int num) //아웃일때
   {
      
      if(num ==1)
      {
         outcount += num;
         strikecount=0;
         ballcount=0;
         if(outcount>=3)
         {
            GongsuChange();
         }
         else
            BatterChange();
         
      }
      else if(num ==2)
      {
         outcount += num;
         strikecount=0;
         ballcount=0;
         
         if(outcount>=3)
         {
            GongsuChange();
         }
         else
            BatterChange();
         
      }
      else if(num ==3)
      {
         outcount += num;
         strikecount=0;
         ballcount=0;
         
         if(outcount>=3)
         {
            GongsuChange();
         }
         else
            BatterChange();
      }
      
   }
   
   public void BatterChange()  //타자교대
   {
      int num = gongsu%2;
      if(num==0) // 홈팀공격일때
      {
         if(h_tasun>=8)
            h_tasun=0;
         else
         h_tasun++;
      }
      else if(num==1) //어웨이팀 공격일때
      {
         if(a_tasun>=8)
            a_tasun=0;
         else
         a_tasun++;
      }
      System.out.println("========타자교대=====");
      
   }

   public void GongsuChange() //공수교대
   {
      outcount =0;
      int num = gongsu%2;
      if(num ==0)
      {
         gongsu++;
         if(gongsu==18)
         {
            return;
         }
         else
         {
            System.out.println("========공수교대["+((gongsu/2)+1)+"]회말======" );
         }
         
      }
      else if(num==1)
      {
         gongsu++;
         if(gongsu==18)
         {
            return;
         }
         else
            System.out.println("========공수교대["+((gongsu/2)+1)+"]회초======" );
      }
   }
   
   public int JinRu(int num)  //진루
   {
      int score=0;
      switch(num)
      {   
      case 1: //1루타
         if(base1==0 && base2==0 && base3==0){base1=1;} 
         else if(base1==1 && base2==0 && base3==0){base2=1;}
         else if(base1==0 && base2==1 && base3==0){base3=1;base1=1;base2=0;}
         else if(base1==0 && base2==0 && base3==1){base1=1; base3=0; GetPoint(1);score=1;}//득점1점
         else if(base1==1 && base2==1 && base3==0){base3=1;}
         else if(base1==1 && base2==0 && base3==1){base2=1;base3=0; GetPoint(1);score=1;}//득점1점
         else if(base1==0 && base2==1 && base3==1){base1=1;base2=0; GetPoint(1);score=1;}//득점1점
         else if(base1==1 && base2==1 && base3==1){GetPoint(1);score=1;}//득점1점
         break;
      
      
      case 2: //2루타
         if(base1==0 && base2==0 && base3==0){base1=2;} 
         else if(base1==1 && base2==0 && base3==0){base1=0;base2=1;base3=1;}
         else if(base1==0 && base2==1 && base3==0){score=1;}//득점1점
         else if(base1==0 && base2==0 && base3==1){base2=1;base3=0;score=1;}//득점1점
         else if(base1==1 && base2==1 && base3==0){base1=0;base3=1;score=1;}//득점1점
         else if(base1==1 && base2==0 && base3==1){base1=0;base2=1;score=1;}//득점1점
         else if(base1==0 && base2==1 && base3==1){base3=0;score=2;}//득점2점
         else if(base1==1 && base2==1 && base3==1){base1=0;score=2;}//득점2점
         break;
         
      case 3: //3루타
         if(base1==0 && base2==0 && base3==0){base3=1;} 
         else if(base1==1 && base2==0 && base3==0){base3=1;score=1;}//득점1점
         else if(base1==0 && base2==1 && base3==0){base2=0; base3=1;score=1;}//득점1점
         else if(base1==0 && base2==0 && base3==1){score=1;}//득점1점
         else if(base1==1 && base2==1 && base3==0){base1=0;base2=0;base3=1;score=2;}//득점2점
         else if(base1==1 && base2==0 && base3==1){base1=0;score=2;}//득점2점
         else if(base1==0 && base2==1 && base3==1){base1=1;score=2;}//득점2점
         else if(base1==1 && base2==1 && base3==1){base1=0;base2=0;score=3;}//득점3점
         break;
         
      case 4: //홈런
         if(base1==0 && base2==0 && base3==0){score=1;} //득점1점
         else if(base1==1 && base2==0 && base3==0){base1=0;score=2;}//득점2점
         else if(base1==0 && base2==1 && base3==0){base2=0;score=2;}//득점2점
         else if(base1==0 && base2==0 && base3==1){base3=0;score=2;}//득점2점
         else if(base1==1 && base2==1 && base3==0){base1=0;base2=0;score=3;}//득점3점
         else if(base1==1 && base2==0 && base3==1){base1=0;base3=0;score=3;}//득점3점
         else if(base1==0 && base2==1 && base3==1){base2=0;base3=0;score=3;}//득점3점
         else if(base1==1 && base2==1 && base3==1){base1=0;base2=0;base3=0;score=4;}//득점4점
         break;
         
      case 5: //데드볼
          if(base1==0 && base2==0 && base3==0){base1=1;} 
            else if(base1==1 && base2==0 && base3==0){base2=1;}      
            else if(base1==0 && base2==1 && base3==0){base1=1;base2=1;}      
            else if(base1==0 && base2==0 && base3==1){base1=1;}       
            else if(base1==1 && base2==1 && base3==0){base3=1;} 
            else if(base1==1 && base2==0 && base3==1){base1=1; base2=1;}
            else if(base1==0 && base2==1 && base3==1){base1=1;}
            else if(base1==1 && base2==1 && base3==1){score=1;} // 1점 득점
            break;
         
      case 6: //볼넷
            if(base1==0 && base2==0 && base3==0){base1=1;} 
            else if(base1==1 && base2==0 && base3==0){base2=1;}      
            else if(base1==0 && base2==1 && base3==0){base1=1;base2=1;}      
            else if(base1==0 && base2==0 && base3==1){base1=1;}       
            else if(base1==1 && base2==1 && base3==0){base3=1;} 
            else if(base1==1 && base2==0 && base3==1){base1=1; base2=1;}
            else if(base1==0 && base2==1 && base3==1){base1=1;}
            else if(base1==1 && base2==1 && base3==1){score=1;} // 1점 득점
            break;
      }
      return score;
   }
   
   public void GetPoint(int score)//득점
   {
      int num = gongsu%2;
      if(num==0) // 홈팀공격일때
      {
         h_score+=score;
      }
      else if(num==1) //어웨이팀 공격일때
      {
         a_score+=score;
      }
      
   }
   
   public void Refresh() //경기종료 후 초기화
   {
      gongsu=0; base1 = 0;base2 = 0;base3 =0;h_tasun=0; a_tasun=0;
      ballcount=0; strikecount=0; ballcount=0; a_score=0; h_score=0;
   }
}