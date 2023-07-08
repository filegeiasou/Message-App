import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Server extends JFrame implements ActionListener {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private JLabel label ,label1;
    private JTextField textField;
    private JButton button;
    private String a;
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("Server started. Waiting for clients to connect...");

            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

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
            this.setTitle("Server");
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

            System.out.println("Disconnected from client.");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessages() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                System.out.println("Client: " + message);
                label1.setText("Message: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1234);
        server.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button)
        {
            a = textField.getText();
            out.println(a);
            textField.setText("");
        }
    }
}
