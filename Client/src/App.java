import java.util.Scanner;

public class App {
	static Scanner scan = new Scanner(System.in);
	public static Team[] hometeam = new Team[725], awayteam = new Team[725];
	public static int[] matchnum = null;
	public static boolean GetMachnum = true , GetTPInfo = false;
	

	// �������� ���� ������ ���
	public static void RecvData(String msg) 
	{
		System.out.println(msg);
	}

	public static void main(String[] args) {
		// TODO �ڵ� ������ �޼ҵ� ����

		// ���� ���� �Է�
		MyNet net = new MyNet("61.81.99.47", 4444);
		Play p = new Play(net);
		try 
		{
			p.playgame();
		} 
		catch 
		(InterruptedException e) 
		{
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}


	}

}
