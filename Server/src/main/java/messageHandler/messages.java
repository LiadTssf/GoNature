package messageHandler;


import account.DB.SqlConnection;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;
import java.sql.*;
import java.time.LocalTime;
import java.util.Properties;


public class messages extends Thread{

    @Override
    public void run(){
        while(true){
            try {
                Connection con = SqlConnection.getConnection();
                Statement stmt = con.createStatement();


                ResultSet rs = stmt.executeQuery("SELECT OrderNumber,TelephoneNumber,TimeOfVisit,NumberOfVisitors,ParkName FROM order");

                while (rs.next()){
                    String phone = rs.getString("TelephoneNumber");
                    int OrderNumber = rs.getInt("OrderNumber");
                    int NumberOfVisitors = rs.getInt("NumberOfVisitors");
                    String ParkName = rs.getString("ParkName");
                    Time time = rs.getTime("TimeOfVisit");

                    if (time.equals(LocalTime.now())){
                        sendEmail(phone,OrderNumber,NumberOfVisitors,ParkName);
                    }
                }

                rs.close();
                stmt.close();
                con.close();



                Thread.sleep(60000);
            }catch (SQLException | InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    private void sendEmail(String phone,int OrderNumber,int numberOfVisitors,String parkName){
        String host = "smtp.example.com";
        String from = "your-email@example.com";
        String pass = "your-password";
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(phone));

            message.setSubject("Your Subject");
            message.setText("Your email message goes here");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
