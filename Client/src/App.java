import java.util.Scanner;

public class App {
	static Scanner scan = new Scanner(System.in);
	public static Team[] hometeam = new Team[725], awayteam = new Team[725];
	public static int[] matchnum = null;
	public static boolean GetMachnum = true , GetTPInfo = false;
	

	// 서버한테 받은 데이터 출력
	public static void RecvData(String msg) 
	{
		System.out.println(msg);
	}

	public static void main(String[] args) {
		// TODO 자동 생성된 메소드 스텁

		// 서버 정보 입력
		MyNet net = new MyNet("61.81.99.47", 4444);
		Play p = new Play(net);
		try 
		{
			p.playgame();
		} 
		catch 
		(InterruptedException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}


	}

}
