import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class RecvThread extends Thread {
	Socket sock;
	BufferedReader in;
	BufferedWriter out;
	MyDB db = MyDB.getInstance();
	GetInformation gi = new GetInformation();
	Team[] team;
	String temp = null;
	ObjectOutputStream oos;
	ObjectInputStream ois;

	public RecvThread(Socket _s) {
		try {
			sock = _s;
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		db.Connect();
		try {
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
		}

		while (true) {
			try {
			
				String line = in.readLine();
				String[] token = line.split("#");
				switch (token[0]) {
				case "����":
					
					team = new Team[1438];
					
					//home
					for(int i =0 ; i <719; i ++){
						team[i] = App.awayteam[i];
					}
					//away
					for(int i = 719 , j = 0; i <1438; i ++, j ++){
						team[i] = App.hometeam[j];
					}
					for(int i=0; i<App.matchnum.length-1;i++){
						team[0].matchnum.add(App.matchnum[i]);
					}
					oos.writeObject(team);
					out.write("Ŭ���̾�Ʈ�� �������Դϴ�");
					out.flush();
					
					break;
				case "�������":

					 oos.writeObject(App.matchnum);

					break;
				// case "������":
				// db.GetTeam(home, away, matchnum);
				// db.SelectPlayer(team);
				// break;

				}

				// �� msg�� �ٽ� Ŭ���̾�Ʈ���� ����
				// out.write(temp);
				// out.flush();

			} catch (Exception e) {
				try {
					sock.close();

					InetAddress addr = sock.getInetAddress();
					System.out.println("Ŭ���̾�Ʈ ��������" + addr.getHostAddress().toString());
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
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
