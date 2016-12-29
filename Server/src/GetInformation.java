
public class GetInformation {
	MyDB db = MyDB.getInstance();

	public GetInformation() 
	{
		System.out.println("데이터베이스에서 정보를 받아오는 중입니다...");
	}

	public void GetInformations() {
		db.Connect();
		int count = db.SelectDateCount(); // 총 경기 일정 (수 ) 받아오기

		App.hometeam = new Team[count];
		App.awayteam = new Team[count];
		App.matchnum = new int[count];

		for (int i = 0; i < count; i++) {
			App.hometeam[i] = new Team();
			App.awayteam[i] = new Team();
			for (int j = 0; j < 9; j++) // 타자정보받을공간 생성
			{
				App.hometeam[i].batter_name[j] = new String();
				App.awayteam[i].batter_name[j] = new String();

			}
		}

		db.GetTeam(App.hometeam, App.awayteam, App.matchnum); // 팀이름,매치넘버 받아오기

		for (int i = 0; i < App.matchnum.length - 1; i++) // 선수 불러오기
		{
			db.SelectPlayer(App.awayteam[i]);
			db.SelectPlayer(App.hometeam[i]);
		}
	}

}
