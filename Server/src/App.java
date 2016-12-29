
//Server 사용하는 예제
public class App {
	public static Team[] hometeam = null, awayteam = null;
	public static int[] matchnum = null;
	static MyNet net = MyNet.getInstance();
	static GetInformation info = new GetInformation();
	MyDB db = MyDB.getInstance();

	// public static String TeamMsg;
	// public static String PlayerMsg;
	// 데이터 수신
	public static String RecvData(String msg) {
		System.out.println("[수신]" + msg);

		// 1. 수신 정보를 분석

		// 2. 분석된 결과에 따른 처리(DB)

		// 3. 클라이언트에 전송할 반환 정보 생성
		msg = "[서버에서 재 전송]" + msg;
		return msg;
	}

	public static void main(String[] args)
	{
		info.GetInformations();
		net.Start(4000);

	}
}
