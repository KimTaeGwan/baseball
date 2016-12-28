import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyNet {
	private Socket clientSocket;
	private BufferedReader in;
	private BufferedWriter out;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Team[] team;
	Scanner scan = new Scanner(System.in);

	// �а� ���°� �����ϰ� ���ش�.

	public MyNet(String ip, int port) 
	{
		try {
			clientSocket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("���� ���� ����");
		}
	}

	// Ŭ���̾�Ʈ�� �޼����� ������ �������� ������ Ŭ���̾�Ʈ�� ����
	public String SendRecv(String msg) 
	{
	
		String message=null;
		try 
		{
			// 1. ������ ����
			out.write(msg + "\n");
			out.flush();
			
			team = new Team[1438];
			if (App.GetTPInfo == false)
			{
				
				team = (Team[]) ois.readObject();
				// home
				for (int i = 0; i < 720; i++) 
				{
					App.awayteam[i] = team[i];
				}
				// away
				for (int i = 720, j = 0; i < 1438; i++, j++) 
				{

					App.hometeam[j] = team[i];
				}
				App.matchnum = new int[team[0].matchnum.size()];
				
				for(int i =0; i <team[0].matchnum.size();i++)
				{
					App.matchnum[i] = (int)team[0].matchnum.get(i); 
				}
				
				System.out.println("���� ������ ���������� �����Ͽ����ϴ�.");
				App.GetTPInfo = true;
				message="�������";
				return message;
			}

		}

		catch (Exception e) 
		{
			System.out.println("������ �м� ���� !" + e);
		}
		return message;
	}

	public static byte[] Otb(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			os = new ObjectOutputStream(out);
			os.writeObject(obj);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return out.toByteArray();
	}

	public static Object Bto(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(in);
			return is.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}