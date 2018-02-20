//Connor Albrecht (ca7xf)
//Project 1
//Feb. 15, 2018
import java.io.*;
import java.net.*;


public class EmailSender
{
   public static void main(String[] args) throws Exception
   {
      Socket socket = null;
	   // Establish a TCP connection with the mail server.
	   socket = new Socket("mail.virginia.edu", 25);
      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("220")) {
    	 socket.close();
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      OutputStream os = socket.getOutputStream();

      // Send HELO command and get server response.
      String command = "HELO alice\r\n";
      System.out.print(command);
      os.write(command.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("250")) {
    	 socket.close();
         throw new Exception("250 reply not received from server.");
      }

      // Send MAIL FROM command.
      String mailFrom = "MAIL FROM: <ca7xf@virginia.edu>\r\n";
      System.out.print(mailFrom);
      os.write(mailFrom.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("250")) {
    	 socket.close();
         throw new Exception("250 reply not received from server.");
      }

      // Send RCPT TO command.
      String RCPT_TO = "RCPT TO: <ca7xf@virginia.edu>\r\n";
      System.out.print(RCPT_TO);
      os.write(RCPT_TO.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("250")) {
    	 socket.close();
         throw new Exception("250 reply not received from server.");
      }

      // Send DATA command.
      String data = "DATA SUBJECT: Project 1\r\n";
      System.out.print(data);
      os.write(data.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("354")) {
    	 socket.close();
         throw new Exception("354 reply not received from server.");
      }

      // Send message data.
      String sendData = "Subject: Computer Networks Project 1\r\n" + "Connor Albrecht (ca7xf).\r\n";
      System.out.print(sendData);
      os.write(sendData.getBytes("US-ASCII"));

      // End with line with a single period.
      String endLine = ".\r\n";
      System.out.print(endLine);
      os.write(endLine.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("250")) {
    	 socket.close();
         throw new Exception("250 reply not received from server.");
      }

      // Send QUIT command.
      String quit = "QUIT\r\n";
      System.out.print(quit);
      os.write(quit.getBytes("US-ASCII"));

      response = br.readLine();
      System.out.println(response);

      if (!response.startsWith("221")) {
    	 socket.close();
         throw new Exception("221 reply not received from server.");
      }
      socket.close();
   }
}