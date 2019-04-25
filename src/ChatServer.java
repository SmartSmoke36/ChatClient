import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {
    ArrayList clientOutputStreams;
    public class ClientHandler implements Runnable{

        BufferedReader reader;
        Socket socket;
        public ClientHandler(Socket clientSocket){
            try{
                socket=clientSocket;
                InputStreamReader isReader= new InputStreamReader(socket.getInputStream());
                reader=new BufferedReader(isReader);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        public void run() {
            String message;
            try{
                while((message=reader.readLine())!=null){
                    System.out.println("read"+message);
                    tellEveryone(message);
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
      public static void main(String[] args){
          ChatServer server=new ChatServer();
          server.go();
      }
//    public ChatServer(){
//        //ChatServer server=new ChatServer();
//        go();
//    }
    private void go() {
        clientOutputStreams=new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while(true){
                Socket socket =serverSocket.accept();
                PrintWriter writer=new PrintWriter(socket.getOutputStream());
                clientOutputStreams.add(writer);
                Thread t=new Thread(new ClientHandler(socket));
                t.start();
                System.out.println("got a Connection");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public void tellEveryone(String message){
        Iterator it =clientOutputStreams.iterator();
        while(it.hasNext()){
            try{
                PrintWriter writer=(PrintWriter)it.next();
                writer.println(message);
                writer.flush();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
