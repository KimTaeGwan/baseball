import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyNet {
	private static MyNet instance;

	private MyNet() {
	}

	public synchronized static MyNet getInstance() {
		if (instance == null) {
			instance = new MyNet();
		}
		return instance;
	}

	private ServerSocket serverSocket;

	Socket socket;

	public void Start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("���� ����");

			while (true) 
			{
				socket = serverSocket.accept();
				InetAddress addr = socket.getInetAddress();
				System.out.println("Ŭ���̾�Ʈ ����" + addr.getHostAddress().toString());

				RecvThread thread = new RecvThread(socket);
				thread.start();
			}

		} catch (IOException e) {
			try {
				System.out.println("���� ����");
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Socket GetSock() {

		return socket;
	}
}
