package com.example.carpro;

import com.model.*;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    @FXML
    private Button btnClose;

    @FXML
    private Button btnDownload;

    @FXML
    private ImageView imgClose;

    @FXML
    private ImageView imgDownload;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblBookingNo;

    @FXML
    private Label lblCar;

    @FXML
    private Label lblCarDesc;

    @FXML
    private Label lblDropOffTime;

    @FXML
    private Label lblFarePerHour;

    @FXML
    private Label lblFarePerHrs;

    @FXML
    private Label lblFinalPrice;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblLocationState;

    @FXML
    private Label lblName;

    @FXML
    private Label lblOfficialReceipt;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblPaymentMethod;

    @FXML
    private Label lblPickUpTime;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblSelectedPaymentMethod;

    @FXML
    private Label lblState;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblTotalHour;

    @FXML
    private Label lblTotalHrs;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Line lineSeparate;

    @FXML
    private Pane pReceipt;

    @FXML
    private Pane pTitle;

    @FXML
    private StackPane spReceipt;

    @FXML
    void close(ActionEvent event) {
        Scene.switchScene("cusMain.fxml",spReceipt);
    }

    @FXML
    private void downloadReceipt(ActionEvent event) {
        btnDownload.setVisible(false);
        printJavaFXNode(spReceipt);
        btnDownload.setVisible(true);
    }

    private com.model.dataFactory dataFactory = new dataFactory();
    private database dbCar = dataFactory.getDB("car");
    private database dbUser = dataFactory.getDB("user");
    private List<Car> cars = new ArrayList<>(dbCar.getAllData());
    private List<User> users = new ArrayList<>(dbUser.getAllData());

    public void setData(Booking booking, Payment payment){
        for(User user: users){
            if(booking.getCustomerId().equals(user.getUsername())){
                for(Car car: cars){
                    if(booking.getCarId().equals(car.getId())){
                        lblName.setText(user.getFirstName() + " " + user.getLastName());
                        lblAddress.setText(user.getAddress() + ",");
                        lblState.setText(user.getPostCode() + " " + user.getState() + ".");
                        lblBookingNo.setText("Booking No. : #" + booking.getId());
                        lblPayment.setText("Payment Date: " + payment.getDate());
                        lblCarDesc.setText(car.getBrand() + " " + car.getModel() + " " + car.getNumPlate() + " - " + car.getSeat() + "seats");
                        lblPickUpTime.setText("Pick-up: " + booking.getStartDate() + " " + booking.getStartTime());
                        lblDropOffTime.setText("Drop-off: " + booking.getEndDate() + " " + booking.getEndTime());
                        lblLocation.setText("Location: " + car.getAddress() + ",");
                        lblLocationState.setText(car.getPostCode() + " " + car.getState() + ".");
                        lblTotalHour.setText(String.valueOf(payment.getTotal()/car.getPrice()));
                        lblFarePerHour.setText(car.getPrice() + "0");
                        lblTotalPrice.setText(payment.getTotal() + "0");
                        lblFinalPrice.setText(payment.getTotal() + "0");
                        lblSelectedPaymentMethod.setText(payment.getMethod());
                    }
                }
            }
        }
    }

    private void printJavaFXNode(Node node){
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = job.getPrinter();

        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        double width = spReceipt.getWidth();
        double height = spReceipt.getHeight();

        PrintResolution resolution = job.getJobSettings().getPrintResolution();
        width /= resolution.getFeedResolution();
        height /= resolution.getCrossFeedResolution();

        double scaleX = pageLayout.getPrintableWidth()/width/600;
        double scaleY = pageLayout.getPrintableHeight()/height/600;
        Scale scale = new Scale(scaleX, scaleY);

        spReceipt.getTransforms().add(scale);
        boolean success = job.printPage(pageLayout, spReceipt);
        if(success){
            job.endJob();
        }
        spReceipt.getTransforms().remove(scale);


        /*Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A2, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        job.getJobSettings().setPageLayout(pageLayout);
        boolean success = job.printPage(node);
        if (success) {
            job.endJob();
        }*/
    }
}
