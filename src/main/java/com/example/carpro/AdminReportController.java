package com.example.carpro;

import com.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AdminReportController implements Initializable {

    @FXML
    private AreaChart<String, ?> areaChart;

    @FXML
    private Label cardLbl;

    @FXML
    private VBox transactionListLayout;

    @FXML
    private Label eWalletLbl;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private Pagination pagination;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label totalSalesLbl;

    @FXML
    private Label transactionLbl;

    @FXML
    private Label transferLbl;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox container;

    private final static int rowsPerPage = 8;

    dataFactory dataFactory = new dataFactory();
    database paymentDB = dataFactory.getDB("payment");
    List<Payment> payments = new ArrayList<>(paymentDB.getAllData());

    database bookingDB = dataFactory.getDB("booking");
    List<Booking> bookings = new ArrayList<>(bookingDB.getAllData());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        areaChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");

        //set default date
        fromDatePicker.setValue(LocalDate.now().minusMonths(6));
        toDatePicker.setValue(LocalDate.now());

        //set from date < to date
        toDatePicker.setDayCellFactory(datePicker -> new DateCell(){
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate from = fromDatePicker.getValue();

                setDisable(empty||date.compareTo(from)<0);
            }
        });

        //display report
        generateReport();
    }

    private void generateReport(){
        float total=0, creditCard=0, transfer=0, eWallet=0;
        int transaction = 0;

        List<Payment> paymentList = new ArrayList<>();
        List<Booking> bookingList = new ArrayList<>();

        for(Payment payment:payments){
            if(payment.getDate().isAfter(fromDatePicker.getValue()) && payment.getDate().isBefore(toDatePicker.getValue())){
                paymentList.add(payment);

                //set label
                total += payment.getTotal();
                transaction++;

                if(payment.getMethod().equals("Credit Card")){
                    creditCard+=payment.getTotal();
                }else if(payment.getMethod().equals("Transfer")){
                    transfer+=payment.getTotal();
                }else if(payment.getMethod().equals("E-wallet")){
                    eWallet+=payment.getTotal();
                }

                //filter booking
                for (Booking booking:bookings) {
                    if(payment.getId().equals(booking.getPaymentId())){
                        bookingList.add(booking);
                    }
                }
            }
        }

        //set label
        totalSalesLbl.setText("RM "+String.format("%.02f",total));
        transactionLbl.setText(String.valueOf(transaction));
        cardLbl.setText("RM "+String.format("%.02f",creditCard));
        transferLbl.setText("RM "+String.format("%.02f",transfer));
        eWalletLbl.setText("RM "+String.format("%.02f",eWallet));

        setAreaChart(paymentList);

        if(paymentList.isEmpty()){
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            container.setPrefHeight(480);
        }
        else if(!paymentList.isEmpty()){
            container.setPrefHeight(1000);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    transactionListLayout.getChildren().clear();
                    return loadTransaction(pageIndex,paymentList,bookingList);
                }
            });

            setPaginationPageCount(transaction);
        }

    }

    private void setAreaChart(List<Payment> payments){
        XYChart.Series series = new XYChart.Series();

        //sort payment date
        payments.sort(Comparator.comparing(o -> o.getDate()));

        for(Payment payment:payments){
            //set area chart
            series.getData().add(new XYChart.Data(payment.getDate().toString(),payment.getTotal()));
        }

        areaChart.getData().clear();
        areaChart.getData().addAll(series);
    }

    private void setPaginationPageCount(int transaction){
        int pageCount = transaction/rowsPerPage;
        int remain = transaction%rowsPerPage;
        if(remain!=0){
            pageCount++;
        }

        pagination.setPageCount(pageCount);
    }

    private Node loadTransaction(int pageIndex, List<Payment> payments ,List<Booking> bookings){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex+ rowsPerPage,payments.size());

        for (int i=fromIndex; i< toIndex; i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("reportList.fxml"));

            try{
                HBox hBox = fxmlLoader.load();
                ReportListController reportListController = fxmlLoader.getController();
                reportListController.setData(payments.get(i),bookings.get(i));
                transactionListLayout.getChildren().add(hBox);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return transactionListLayout;
    }

    @FXML
    private void handleDateChange(ActionEvent event){
        if(fromDatePicker.getValue().isAfter(toDatePicker.getValue())){
            toDatePicker.setValue(fromDatePicker.getValue());
        }
        generateReport();
    }
}
