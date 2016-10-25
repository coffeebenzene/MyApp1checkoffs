package istd.eric.myapp1checkoffs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class CheckoffServerActivity extends AppCompatActivity {

    int port = 0; // 8080 previously. 0 dynamically gets a free port.
    String message = ""; // Log message
    SocketServerThread socketserverthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_server);

        //((TextView)findViewById(R.id.server_ipinfo)).setText("Server port: " + port + "\n" + getIpAddress());

        socketserverthread = new SocketServerThread();
        socketserverthread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socketserverthread.serverSocket!=null && !socketserverthread.serverSocket.isClosed()){
            try {
                socketserverthread.serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    void update_log_info(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.log_info)).setText( CheckoffServerActivity.this.message );
            }
        });
    }



    public String getIpAddress() {
        String ip = "Server IPs :\n";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces(); // Get all network interfaces.
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses(); // For each network interface, get all IP addresses.
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement(); // Get all IP addresses
                    if (inetAddress.isSiteLocalAddress()) { // Check if is local.
                        ip += "Server running at IP : " + inetAddress.getHostAddress() + "\n";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Something Went Wrong! " + e.toString() + "\n";
        }
        return ip;
    }



    private class SocketServerThread extends Thread {
        int count = 0;
        ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                // create ServerSocket using specified port
                serverSocket = new ServerSocket(port);
                // Dynamically show the port chosen.
                CheckoffServerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.server_ipinfo)).setText("Server port: " + serverSocket.getLocalPort() + "\n" + CheckoffServerActivity.this.getIpAddress());
                    }
                });
                // Run forever
                while (true) {
                    // block the call until connection is created (by client) and return Socket Object
                    Socket socket = serverSocket.accept();
                    // Update the log info with new message
                    ++count;
                    CheckoffServerActivity.this.message += "#" + count + " from " +
                                                           socket.getInetAddress() + ":" +
                                                           socket.getPort() + "\n";
                    CheckoffServerActivity.this.update_log_info();
                    // Start new reply thread to respond to the client.
                    // This allows server thread to keep running, and prevents client failures from affecting server thread.
                    (new SocketServerReplyThread(socket, count) ).run();
                }
            } catch (IOException e) {
                e.printStackTrace();
                CheckoffServerActivity.this.message += e.getMessage();
                CheckoffServerActivity.this.update_log_info();
            } finally {
                if (serverSocket!=null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }



    private class SocketServerReplyThread extends Thread {
        Socket client_socket;
        int count;

        SocketServerReplyThread(Socket socket, int c) {
            this.client_socket = socket;
            this.count = c;
        }

        @Override
        public void run() {
            try{
                // Print to client socket
                PrintStream printStream = new PrintStream(client_socket.getOutputStream());
                printStream.print("Hello from server, you are client #" + count);
                printStream.close();
                // Update log info with new message.
                CheckoffServerActivity.this.message += "Responded to client #" + count + ": Hello from server, you are client #" + count + "\n";
                CheckoffServerActivity.this.update_log_info();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}



