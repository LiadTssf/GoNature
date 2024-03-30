package gui;

import command.VisitExportReport;
import data.Order;
import handler.ClientHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExReportWindow implements Initializable {
    @FXML
    private PieChart ex_report_pie;
    @FXML
    private Button exit_btn;

    private int waitingListCounter = 0;
    private int cancelledCounter = 0;
    private int regularOrder = 0;
    private int guidedOrderCounter = 0;
    private int paidOrder = 0;
    private ArrayList<Order> orderList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (ClientHandler.getLastResponse().getCommand().equals("VisitExportReport")){
            orderList = (ArrayList<Order>) ClientHandler.getLastResponse().getParam();
        }


        for (Order order : orderList){
            if (order.cancelled){
                cancelledCounter++;
            }
            if (order.on_waiting_list){
                waitingListCounter++;
            }
            if (!order.guided_order && !order.cancelled){
                regularOrder++;
            }
            if (order.guided_order && !order.cancelled){
                guidedOrderCounter++;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Cancelled Order",cancelledCounter),
                new PieChart.Data("Waiting List Orders",waitingListCounter),
                new PieChart.Data("Regular Orders",regularOrder),
                new PieChart.Data("guided orders",guidedOrderCounter),
                new PieChart.Data("paid orders",paidOrder));

        ex_report_pie.setData(pieChartData);

    }
}
