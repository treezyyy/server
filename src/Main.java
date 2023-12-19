import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void Connect() throws IOException {
        server = new ServerSocket(8080, 0, InetAddress.getLocalHost());
        System.out.println("Сервер запущен!");
    }
    public static String ReadInput() throws IOException {
        String InputReturn = "";
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return in.readLine();
    }
    public static void Send() throws IOException {
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out.write("alaxxx"+" \n");
        out.flush();
    }
    public static void CommandReader(String arg_input) throws ParseException, SQLException, ClassNotFoundException {
        String[] args = arg_input.split(" ");
        String command = args[0];
        String json = arg_input.replaceAll(command, "").trim();
        JSONObject obj = (JSONObject) new JSONParser().parse(json);
        System.out.println(command);
        System.out.println(json);

        switch (command){
            case "register":
                String name = (String) obj.get("name");
                String email = (String) obj.get("email");
                String password = (String) obj.get("password");
                DataBaseHandler db = new DataBaseHandler();
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                db.signUpUser(user);
            case "info":
                String namik;
                String emailik;
                String passwordik;

        }
    }

    public static void main(String[] args) throws IOException, ParseException, SQLException, ClassNotFoundException {
        Connect();
        while (true){
            clientSocket = server.accept();
            String result = ReadInput();
            CommandReader(result);
            Send();
            //clientSocket.close();
        }
    }
}