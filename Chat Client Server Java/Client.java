import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Client extends JFrame implements ActionListener {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JLabel label ,label1;
    private JTextField textField;
    private JButton button;
    String a;
    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server.");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            
            Thread messageHandlerThread = new Thread(() -> handleIncomingMessages());
            messageHandlerThread.start();


            label = new JLabel("Enter your message: ");
            label1 = new JLabel("Message: ");
            textField = new JTextField();
            textField.setPreferredSize(new Dimension(200, 30));
            button = new JButton("Send");
            button.addActionListener(this);


            this.add(label);
            this.add(textField);
            this.add(button);
            this.add(label1);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new FlowLayout());
            this.setSize(400, 400);
            this.setTitle("Client");
            this.setVisible(true);
            
            if(textField.getText() != null)
            {
                while (true) {
                if(a!=null)
                {
                    if (a.equalsIgnoreCase("exit")) 
                    {
                        break;
                    }

                    out.println(a);
                    textField.setText("");
                }
            }
            }

            System.out.println("Disconnected from server.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessages() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + message);
                label1.setText("Server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("192.168.2.12", 1234);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == button)
        {
            System.out.println("Button pressed");
            a = textField.getText();
            //System.out.println("Message: " + a);
            out.println(a);
            textField.setText("");
        }
    }
}
