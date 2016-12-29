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
			System.out.println("서버 실행");

			while (true) 
			{
				socket = serverSocket.accept();
				InetAddress addr = socket.getInetAddress();
				System.out.println("클라이언트 접속" + addr.getHostAddress().toString());

				RecvThread thread = new RecvThread(socket);
				thread.start();
			}

		} catch (IOException e) {
			try {
				System.out.println("서버 종료");
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
