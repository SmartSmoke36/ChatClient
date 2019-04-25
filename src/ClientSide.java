import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class ClientSide {
    JTextArea chat_frame;//聊天框
    JTextField dialog_box;//对话框
    Socket client; //
    PrintWriter writer ;
    BufferedReader reader;

    public ClientSide(){
        go();
    }
//    public static void main(String[] args){
//        ClientSide gui=new ClientSide();
//        //gui.Data_Connections();
//        gui.go();
//    }

    private void go(){
        JFrame frame=new JFrame();                              //设置窗口
        frame.setBounds(0,0, 500, 500);      // 窗口大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel areaPanel=new JPanel();
        JPanel fieldPanel=new JPanel();
        JButton button =new JButton("send");
        button.addActionListener(new SendListener());
        chat_frame=new JTextArea(40,40);//文本域的大小
        chat_frame.setLineWrap(true);//自动换行
        chat_frame.setEditable(false);//文本不可修改
        JScrollPane scroll = new JScrollPane(chat_frame);//设置文本域的滚动条
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//使滚动条
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);    //一直出现
        dialog_box=new JTextField(20);//文本框的长度
        areaPanel.add(scroll);
        fieldPanel.add(dialog_box);
        fieldPanel.add(button);
        frame.add(scroll);
        frame.add(fieldPanel);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);
        frame.getContentPane().add(BorderLayout.SOUTH,fieldPanel);
        SetNetWork();
        Thread readerThread =new Thread(new ChatRoom());
        readerThread.start();
        frame.setVisible(true);
    }

    private void SetNetWork(){  //建立网络的链接
        try {
            client = new Socket("127.0.0.1", 7777);
            InputStreamReader streamReader=new InputStreamReader(client.getInputStream());
            reader=new BufferedReader(streamReader);
            writer=new PrintWriter(client.getOutputStream());
            System.out.println("networking has established");
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    class SendListener implements ActionListener{       //发送按钮的实现
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                writer.println(dialog_box.getText());   //向服务器发送内容
                writer.flush();
            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("消息未发出！");
            }
            dialog_box.setText("");
            dialog_box.requestFocus();
        }
    }

    class ChatRoom implements Runnable{
        @Override
        public void run() {
            String message;
            try{
                while((message=reader.readLine())!=null){
                    System.out.println("read"+message);
                    chat_frame.append(message+"\n");
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
