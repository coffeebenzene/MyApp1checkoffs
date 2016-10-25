package istd.eric.myapp1checkoffs;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CheckoffClientActivity extends AppCompatActivity {

    ArrayList<MyClientTask> connected = new ArrayList<MyClientTask>();
    String log_info = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoff_client);
    }

    public void connect(View v){
        String ip_address = ((EditText) findViewById(R.id.ip_edit)).getText().toString();
        int port;
        try{
            port = Integer.parseInt( ((EditText) findViewById(R.id.port_edit)).getText().toString() );
        } catch (NumberFormatException e){
            log_info += "Invalid port: " + ((EditText) findViewById(R.id.port_edit)).getText().toString() + "\n";
            refresh_log();
            return;
        }
        for (MyClientTask client_task : connected){
            if (client_task.ip_address.equals(ip_address) && client_task.port==port) {
                log_info += "Connection already established with " + ip_address + ":" + port + "\n";
                refresh_log();
                return;
            }
        }
        log_info += "Attempting connection to " + ip_address + ":" + port + "\n";
        refresh_log();
        MyClientTask clientTask = new MyClientTask(ip_address, port);
        clientTask.execute();
    }

    public void clear(View v){
        connected = new ArrayList<MyClientTask>();
        log_info = "";
        refresh_log();
    }

    public void refresh_log(){
        ((TextView) findViewById(R.id.log_info)).setText(log_info);
    }



    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String ip_address;
        int port;
        String response = ""; // Response received from server.
        boolean isconnected = false;

        public MyClientTask(String ip_address, int port){
            this.ip_address = ip_address;
            this.port = port;
        }

        @Override
        protected Void doInBackground(Void[] arg0) {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect( new InetSocketAddress(ip_address, port) ,3000); // wait max 3s. If latency is long on LAN, something is very wrong.
                // Once connected, prepare to read and decode into string.
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];
                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                // notice: inputStream.read() will block if no data return
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }
                // Successful, flag as connected.
                isconnected = true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                response = "SocketTimeoutException: " + e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            CheckoffClientActivity.this.log_info += response + "\n";
            CheckoffClientActivity.this.refresh_log();
            if (isconnected){
                CheckoffClientActivity.this.connected.add(this);
            }
            super.onPostExecute(result);
        }
    }
}
