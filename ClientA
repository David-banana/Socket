import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientA {
	Socket s;
	String name;
	PrintWriter pw;
	BufferedReader br;
	
	public ClientA(String name){
		try {
			s = new Socket("127.0.0.1",9000);
			this.name = name;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
		sendMessage(name);
		
		
	}
	public void init() {
		try {
			pw = new PrintWriter(s.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	public String receiveMessage() {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		System.out.println("请输入用户名");
		ClientA client = new ClientA(console.nextLine());
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
				String message = client.receiveMessage();
				System.out.println("服务器说" + message);
			}
			}
			
		});
		t.start();
		while(true) {
			client.sendMessage(console.nextLine());
		}
	}
}
