
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

	  if("�������".equals(net.SendRecv("����#")))
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
     
   
   //���ӽ���
   public void Start(Team away, Team home, int m_num ) throws InterruptedException
   {      
      System.out.println(away.team_name+" vs "+home.team_name + " ����!");
      String Pitcher = null;
      String Batter =null;
      

      //1.������ Ȩ������ �׻� �����Ѵ�
      //2.�� ���� ������ Ÿ�ڸ� �ҷ��´� (Ÿ�ڴ� �ƿ��ɽ� ����Ÿ�ڸ� �ҷ��;���)
      //3.�ƿ�ī��Ʈ�� 3�϶� ���� ����  ----> �̴ױ���   -->9ȸ�� ����� ��������
      //4.���������� �׿� ���� ó���� �Ѵ�.

      while(true)
      {
         int num = gongsu%2;
         if(gongsu==18)
         {
            break;
         }
         if(num==0)//Ȩ�����ݽ�
         {
            Pitcher = away.pitcher_name;
            Batter = home.batter_name[h_tasun];
            System.out.println("[����]"+ Pitcher + "  [Ÿ��]"+Batter );
            Pitching(Pitcher,Batter);
         }
         else if(num==1)//����� ���ݽ�
         {
            Pitcher = home.pitcher_name;
            Batter = away.batter_name[a_tasun];
            System.out.println("[����]"+ Pitcher + "  [Ÿ��]"+Batter );
            Pitching(Pitcher,Batter);
         }
      }
     
      System.out.println("================��������===================");
      System.out.println(away.team_name+ "["+a_score+"]" + ":" +home.team_name +"["+h_score+"]");
      if(a_score>h_score)
      {
         System.out.println(away.team_name +"�¸�!!");
//         db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,0);
      }
      else if(a_score<h_score)
      {
         System.out.println(home.team_name +"�¸�!!");
//         db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,0); 
      } 
      else if(a_score==h_score)
      {
         System.out.println("���º�");
 //        db.MatchEnd(m_num, a_score, h_score, away.team_name, home.team_name,1);  
      }
      System.out.println("=========================================");
//      db.Rotation(m_num,away.pitcher_number,home.pitcher_number);
      
      Refresh();

   }
   
   public void Pitching(String p, String b)
   {
      
      System.out.println("���� ��Ī");
      int random = (int) (Math.random() * 100 + 1); 
      int score=0;
      
      if(random>=1 && random<=28) //��Ʈ����ũ
      {
         
         if(strikecount ==2)
         {
            System.out.println("��Ʈ����ũ ����!!");
            out(1);
         }
         else
         {
            System.out.println("��Ʈ����ũ!");
            strikecount++;
         }

      }
      else if(random>=29 && random<=50)//��
      {
         
         if(ballcount ==3)
         {
            System.out.println("����");            
            strikecount=0;
            ballcount=0;
            score=JinRu(6);
            BatterChange();
         }
         else
         {
            ballcount++;
            System.out.println(ballcount+"��");
            
         }
            
      }
      else if(random>=51 && random<=75) //��Ÿ(Ȩ��)
      {
         //1��45 2��30 3��10 Ȩ��15
         int ran = (int) (Math.random() * 101);
         if(ran>=1 && ran<=45)
         {
            System.out.println("1��Ÿ!");
            score=JinRu(1);
            strikecount=0;
            ballcount=0;
            BatterChange();
         }
         else if(ran>=46 && ran<=75)
         {
            System.out.println("2��Ÿ!");
            score=JinRu(2);
            strikecount=0;
            ballcount=0;
            BatterChange();
            
            
         }
         else if(ran>=76 && ran<=85)
         {
            System.out.println("3��Ÿ!");
            score=JinRu(3);
            strikecount=0;
            ballcount=0;
            BatterChange();
            
         }
         else if(ran>=86 && ran<=100)
         {
            System.out.println("Ȩ��!!!");
            score=JinRu(4);
            strikecount=0;
            ballcount=0;
            BatterChange();
         }
      }
      else if(random>=76 && random<=77) //���庼
      {
         System.out.println("���庼!");
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
            System.out.println("�����ƿ�!");
            out(1);

         }
         else if(ran==2)
         {
            System.out.println("�ö��̾ƿ�!");
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
      if(num==0) // Ȩ�������϶�
      {
         if(h_tasun>=8)
            h_tasun=0;
         else
         h_tasun++;
      }
      else if(num==1) //������� �����϶�
      {
         if(a_tasun>=8)
            a_tasun=0;
         else
         a_tasun++;
      }
      
   }
   
   
   public void out(int num) //�ƿ��϶�
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
   
   public void BatterChange()  //Ÿ�ڱ���
   {
      int num = gongsu%2;
      if(num==0) // Ȩ�������϶�
      {
         if(h_tasun>=8)
            h_tasun=0;
         else
         h_tasun++;
      }
      else if(num==1) //������� �����϶�
      {
         if(a_tasun>=8)
            a_tasun=0;
         else
         a_tasun++;
      }
      System.out.println("========Ÿ�ڱ���=====");
      
   }

   public void GongsuChange() //��������
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
            System.out.println("========��������["+((gongsu/2)+1)+"]ȸ��======" );
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
            System.out.println("========��������["+((gongsu/2)+1)+"]ȸ��======" );
      }
   }
   
   public int JinRu(int num)  //����
   {
      int score=0;
      switch(num)
      {   
      case 1: //1��Ÿ
         if(base1==0 && base2==0 && base3==0){base1=1;} 
         else if(base1==1 && base2==0 && base3==0){base2=1;}
         else if(base1==0 && base2==1 && base3==0){base3=1;base1=1;base2=0;}
         else if(base1==0 && base2==0 && base3==1){base1=1; base3=0; GetPoint(1);score=1;}//����1��
         else if(base1==1 && base2==1 && base3==0){base3=1;}
         else if(base1==1 && base2==0 && base3==1){base2=1;base3=0; GetPoint(1);score=1;}//����1��
         else if(base1==0 && base2==1 && base3==1){base1=1;base2=0; GetPoint(1);score=1;}//����1��
         else if(base1==1 && base2==1 && base3==1){GetPoint(1);score=1;}//����1��
         break;
      
      
      case 2: //2��Ÿ
         if(base1==0 && base2==0 && base3==0){base1=2;} 
         else if(base1==1 && base2==0 && base3==0){base1=0;base2=1;base3=1;}
         else if(base1==0 && base2==1 && base3==0){score=1;}//����1��
         else if(base1==0 && base2==0 && base3==1){base2=1;base3=0;score=1;}//����1��
         else if(base1==1 && base2==1 && base3==0){base1=0;base3=1;score=1;}//����1��
         else if(base1==1 && base2==0 && base3==1){base1=0;base2=1;score=1;}//����1��
         else if(base1==0 && base2==1 && base3==1){base3=0;score=2;}//����2��
         else if(base1==1 && base2==1 && base3==1){base1=0;score=2;}//����2��
         break;
         
      case 3: //3��Ÿ
         if(base1==0 && base2==0 && base3==0){base3=1;} 
         else if(base1==1 && base2==0 && base3==0){base3=1;score=1;}//����1��
         else if(base1==0 && base2==1 && base3==0){base2=0; base3=1;score=1;}//����1��
         else if(base1==0 && base2==0 && base3==1){score=1;}//����1��
         else if(base1==1 && base2==1 && base3==0){base1=0;base2=0;base3=1;score=2;}//����2��
         else if(base1==1 && base2==0 && base3==1){base1=0;score=2;}//����2��
         else if(base1==0 && base2==1 && base3==1){base1=1;score=2;}//����2��
         else if(base1==1 && base2==1 && base3==1){base1=0;base2=0;score=3;}//����3��
         break;
         
      case 4: //Ȩ��
         if(base1==0 && base2==0 && base3==0){score=1;} //����1��
         else if(base1==1 && base2==0 && base3==0){base1=0;score=2;}//����2��
         else if(base1==0 && base2==1 && base3==0){base2=0;score=2;}//����2��
         else if(base1==0 && base2==0 && base3==1){base3=0;score=2;}//����2��
         else if(base1==1 && base2==1 && base3==0){base1=0;base2=0;score=3;}//����3��
         else if(base1==1 && base2==0 && base3==1){base1=0;base3=0;score=3;}//����3��
         else if(base1==0 && base2==1 && base3==1){base2=0;base3=0;score=3;}//����3��
         else if(base1==1 && base2==1 && base3==1){base1=0;base2=0;base3=0;score=4;}//����4��
         break;
         
      case 5: //���庼
          if(base1==0 && base2==0 && base3==0){base1=1;} 
            else if(base1==1 && base2==0 && base3==0){base2=1;}      
            else if(base1==0 && base2==1 && base3==0){base1=1;base2=1;}      
            else if(base1==0 && base2==0 && base3==1){base1=1;}       
            else if(base1==1 && base2==1 && base3==0){base3=1;} 
            else if(base1==1 && base2==0 && base3==1){base1=1; base2=1;}
            else if(base1==0 && base2==1 && base3==1){base1=1;}
            else if(base1==1 && base2==1 && base3==1){score=1;} // 1�� ����
            break;
         
      case 6: //����
            if(base1==0 && base2==0 && base3==0){base1=1;} 
            else if(base1==1 && base2==0 && base3==0){base2=1;}      
            else if(base1==0 && base2==1 && base3==0){base1=1;base2=1;}      
            else if(base1==0 && base2==0 && base3==1){base1=1;}       
            else if(base1==1 && base2==1 && base3==0){base3=1;} 
            else if(base1==1 && base2==0 && base3==1){base1=1; base2=1;}
            else if(base1==0 && base2==1 && base3==1){base1=1;}
            else if(base1==1 && base2==1 && base3==1){score=1;} // 1�� ����
            break;
      }
      return score;
   }
   
   public void GetPoint(int score)//����
   {
      int num = gongsu%2;
      if(num==0) // Ȩ�������϶�
      {
         h_score+=score;
      }
      else if(num==1) //������� �����϶�
      {
         a_score+=score;
      }
      
   }
   
   public void Refresh() //������� �� �ʱ�ȭ
   {
      gongsu=0; base1 = 0;base2 = 0;base3 =0;h_tasun=0; a_tasun=0;
      ballcount=0; strikecount=0; ballcount=0; a_score=0; h_score=0;
   }
}