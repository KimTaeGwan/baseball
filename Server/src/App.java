
//Server ����ϴ� ����
public class App {
	public static Team[] hometeam = null, awayteam = null;
	public static int[] matchnum = null;
	static MyNet net = MyNet.getInstance();
	static GetInformation info = new GetInformation();
	MyDB db = MyDB.getInstance();

	// public static String TeamMsg;
	// public static String PlayerMsg;
	// ������ ����
	public static String RecvData(String msg) {
		System.out.println("[����]" + msg);

		// 1. ���� ������ �м�

		// 2. �м��� ����� ���� ó��(DB)

		// 3. Ŭ���̾�Ʈ�� ������ ��ȯ ���� ����
		msg = "[�������� �� ����]" + msg;
		return msg;
	}

	public static void main(String[] args)
	{
		info.GetInformations();
		net.Start(4000);

	}
}
