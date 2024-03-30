package gui;

import database.DatabaseController;

import javax.swing.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class CancelNotApprovedOrderThread implements Runnable{

    private int accountId;
    private String orderIdPk;
    DatabaseController DB;
    public CancelNotApprovedOrderThread(int accountId,String orderIdPk){
        this.accountId = accountId;
        this.orderIdPk = orderIdPk;
        this.DB= new DatabaseController();
    }
    @Override
    public void run() {
        try {
//            Thread.sleep(2 * 60 * 60 * 1000); // 2 hours in milliseconds
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }

      if(orderIdPk != null){
          cancelOrder(orderIdPk);
      }
       System.out.println("no order to delete");

    }

    // TODO add approved column to order in db
    private void cancelOrder(String orderId) {
        LocalDate tomorrow = LocalDate.now().plusDays(2);
        try {

            String query = "UPDATE `gonature`.`order` SET cancelled = ? WHERE visit_date = ? AND approve = ?";
            PreparedStatement statement = DB.getConnection().prepareStatement(query);
            statement.setBoolean(1,true);
            statement.setDate(2, Date.valueOf(tomorrow));
            statement.setBoolean(3,false);
            statement.executeUpdate();
            System.out.println("order deleted ! ");
//            statement.close();
//            DB.getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
