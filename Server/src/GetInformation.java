
public class GetInformation {
	MyDB db = MyDB.getInstance();

	public GetInformation() 
	{
		System.out.println("�����ͺ��̽����� ������ �޾ƿ��� ���Դϴ�...");
	}

	public void GetInformations() {
		db.Connect();
		int count = db.SelectDateCount(); // �� ��� ���� (�� ) �޾ƿ���

		App.hometeam = new Team[count];
		App.awayteam = new Team[count];
		App.matchnum = new int[count];

		for (int i = 0; i < count; i++) {
			App.hometeam[i] = new Team();
			App.awayteam[i] = new Team();
			for (int j = 0; j < 9; j++) // Ÿ�������������� ����
			{
				App.hometeam[i].batter_name[j] = new String();
				App.awayteam[i].batter_name[j] = new String();

			}
		}

		db.GetTeam(App.hometeam, App.awayteam, App.matchnum); // ���̸�,��ġ�ѹ� �޾ƿ���

		for (int i = 0; i < App.matchnum.length - 1; i++) // ���� �ҷ�����
		{
			db.SelectPlayer(App.awayteam[i]);
			db.SelectPlayer(App.hometeam[i]);
		}
	}

}
