package com.example.carpro;

import com.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;

public class ReceiptController {

    @FXML
    private Button btnDownload;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBookingNo;

    @FXML
    private Label lblCarDesc;

    @FXML
    private Label lblDropOffTime;

    @FXML
    private Label lblFarePerHour;

    @FXML
    private Label lblFinalPrice;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblLocationState;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblPickUpTime;

    @FXML
    private Label lblSelectedPaymentMethod;

    @FXML
    private Label lblState;

    @FXML
    private Label lblTotalHour;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private StackPane spReceipt;

    @FXML
    private Tooltip ttCarDesc, ttLocation;

    private com.model.dataFactory dataFactory = new dataFactory();
    private database dbCar = dataFactory.getDB("car");
    private database dbPymt = dataFactory.getDB("payment");
    private database dbUser = dataFactory.getDB("user");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());
    private List<Payment> payments = new ArrayList<>(dbPymt.getAllData());
    private List<User> users = new ArrayList<>(dbUser.getAllData());

    public void setData(Booking booking){
        for(User user: users){
            if(booking.getCustomerId().equals(user.getUsername())){
                for(Payment payment: payments){
                    if(booking.getPaymentId().equals(payment.getId())){
                        for(Car car: cars){
                            if(booking.getCarId().equals(car.getId())){
                                lblName.setText(user.getFirstName() + " " + user.getLastName());
                                lblAddress.setText(user.getAddress() + ",");
                                lblState.setText(user.getPostCode() + " " + user.getState() + ".");
                                lblBookingNo.setText("Booking No. : #" + booking.getId());
                                lblPayment.setText("Payment Date: " + payment.getDate());
                                lblCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate() + " - " + car.getSeat() + " seats");
                                ttCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate() + " - " + car.getSeat() + " seats");
                                lblPickUpTime.setText("Pick-up: " + booking.getStartDate() + " " + booking.getStartTime());
                                lblDropOffTime.setText("Drop-off: " + booking.getEndDate() + " " + booking.getEndTime());
                                lblLocation.setText("Location: " + car.getAddress() + ",");
                                ttLocation.setText("Location: " + car.getAddress() + ",");
                                lblLocationState.setText(car.getPostCode() + " " + car.getState() + ".");
                                lblTotalHour.setText(String.valueOf(payment.getTotal()/car.getPrice()).replaceAll(".0",""));
                                lblFarePerHour.setText(car.getPrice() + "0");
                                lblTotalPrice.setText(payment.getTotal() + "0");
                                lblFinalPrice.setText(payment.getTotal() + "0");
                                lblSelectedPaymentMethod.setText(payment.getMethod());
                            }
                        }
                    }
                }
            }
        }
    }

    private void printJavaFXNode(Node node){
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = job.getPrinter();

        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        double width = node.getBoundsInParent().getWidth();
        double height = node.getBoundsInParent().getHeight();

        PrintResolution resolution = job.getJobSettings().getPrintResolution();
        width /= resolution.getFeedResolution();
        height /= resolution.getCrossFeedResolution();

        double scaleX = pageLayout.getPrintableWidth()/width/600;
        double scaleY = pageLayout.getPrintableHeight()/height/600;
        Scale scale = new Scale(scaleX, scaleY);
        node.getTransforms().add(scale);

        boolean success = job.printPage(pageLayout, node);
        if(success){
            job.endJob();
        }
        node.getTransforms().remove(scale);
    }

    @FXML
    private void downloadReceipt(ActionEvent event) {
        btnDownload.setVisible(false);
        printJavaFXNode(spReceipt);
        btnDownload.setVisible(true);
    }

    @FXML
    private void close(ActionEvent event) {
        CusController cusController = (CusController) Scene.getController("cusMain.fxml", spReceipt);
        Scene.switchScene("cusHistory.fxml", cusController.getSpCusDefault());
        cusController.getBtnHistory().requestFocus();
    }
}
