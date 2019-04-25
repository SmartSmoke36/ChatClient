
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientCreate {
    JLabel Label1;  //账号
    JLabel Label2;  //密码
    JButton enter;
    JButton createAccount;
    JFrame frame =new JFrame("用户登录");;
    JTextField Account;
    JPasswordField passWord;
    public static void main(String []args){
        ClientCreate gui=new ClientCreate();
        gui.start();
        gui.SetLocation();
    }
    private void start(){
        AddButton();//添加功能项目
        Label1=new JLabel("账号");
        Label2=new JLabel("密码");
        Account =new JTextField(10);
        passWord=new JPasswordField(10);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void SetLocation(){
         JPanel panel =new JPanel();
         panel.add(Label1);
         panel.add(Account);
         panel.add(Label2);
         panel.add(passWord);
         frame.getContentPane().add(BorderLayout.CENTER, panel);
    }private void AddButton(){
           JPanel jPanel=new JPanel();
           enter =new JButton("登录");
           createAccount =new JButton("创建新账号");
           enter.addActionListener(new enterActioner());
           createAccount.addActionListener(new CreateAccountActioner());
           jPanel.add(enter);
           jPanel.add(createAccount);
           frame.getContentPane().add(BorderLayout.SOUTH,jPanel);
    }
    class enterActioner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int i=Integer.parseInt(Account.getText());
            String password = String.valueOf(passWord.getPassword());//获取密码框中的内容
            int j=Integer.parseInt(password);
            Sql.Establish();
            Sql.Search(i,j);//在数据库中遍历用户
            if(Sql.Search(i,j)==true){
               ClientSide temp=new ClientSide();
            }else{
                System.out.println("没找到");
                JOptionPane.showMessageDialog(null, "用户不存在！", "标题",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    class CreateAccountActioner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int i=Integer.parseInt(Account.getText());
            String password = String.valueOf(passWord.getPassword());//获取密码框中的内容
            int j=Integer.parseInt(password);
            Sql.Establish();
            Sql.InputData(i,j);
        }
    }


}
