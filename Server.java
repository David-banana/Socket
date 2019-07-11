import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Server {
	ServerSocket ss;
	Map<String,Socket> map = new HashMap<String,Socket>();
	BufferedReader br;
	PrintWriter pw;
	public Server(){
		try {
			ss = new ServerSocket(9000);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void service() {
		System.out.println("等待连接");
		while(true) {
			try {
				Socket s = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String name = br.readLine();
				map.put(name,s);
				System.out.println(name + "上线了");
				new Mysockets(s,name).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	private class Mysockets extends Thread {
		private Socket s;
		private String name;
		private BufferedReader br;
		public Mysockets(Socket s,String name) {
			this.s = s;
			this.name = name;
			init();
		}
		public void init() {
			try {
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			while(true) {
				String message = receiveMessage();
				Set<String> names = map.keySet();
				for(String name:names) {
					Socket s = map.get(name);
					sendMessage(s,this.name + "说" + message);
				}
				}
			}
		public void sendMessage(Socket s,String message) {
			PrintWriter pw;
			try {
				pw = new PrintWriter(s.getOutputStream(),true);
				pw.println(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		public String receiveMessage() {
			try {
				return br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;	
		}
	}
	public static void main(String[] args) {
		new Server().service();
	}
}
