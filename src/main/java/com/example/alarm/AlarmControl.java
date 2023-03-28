package com.example.alarm;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AlarmControl implements Initializable {

    DBConn db = new DBConn();
    @FXML
    private Label noDatatext;
    @FXML
    private Label dosnum;
    @FXML
    private Label mednum;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnAdd;

    @FXML
    private VBox pnItems = null;

    public static int num;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        removeExpired();
        ObservableList<demoinfo> list = db.getTable();
        demoinfo[] llst = new demoinfo[list.size()];
        mednum.setText(String.valueOf(list.size()));
        dosnum.setText(String.valueOf(db.doss()));
        llst = db.forDisplay();
        Node[] nodes = new Node[list.size()];
        if(list.size() == 0) noDatatext.setText("No reminders");
        else
        {
            System.out.println("ekhane?");
            for (int i = 0; i < list.size(); i++) {

                try {

                    final int j = i;
                    num = i;

                    nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                    //give the items some effect

                    nodes[i].setOnMouseEntered(event -> {
                        nodes[j].setStyle("-fx-background-color : #694969");
                    });
                    nodes[i].setOnMouseExited(event -> {
                        nodes[j].setStyle("-fx-background-color : #fdfdff");
                    });
                    pnItems.getChildren().add(nodes[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        handleButtons();

    }

    private void removeExpired()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String str = formatter.format(date);
        db.removeExpDates(str);
    }

    @FXML
    private void handleButtons()
    {
        btnAdd.setOnMouseEntered(event ->
        {
            btnAdd.setStyle("-fx-background-color : rgba(235, 221, 250, 0.45)");
        });
        btnAdd.setOnMouseExited(event -> {
            btnAdd.setStyle("-fx-background-color :  rgba(185,7,185,0.82)");
        });
        btnOverview.setOnMouseEntered(event ->
        {
            btnOverview.setStyle("-fx-background-color : rgba(235, 221, 250, 0.45)");
        });
        btnOverview.setOnMouseExited(event -> {
            btnOverview.setStyle("-fx-background-color :  rgba(185,7,185,0.82)");
        });
        btnAdd.setOnMousePressed(event ->
        {
            btnAdd.setStyle("-fx-background-color : rgb(91, 11, 87)");
        });
        btnOverview.setOnMousePressed(event ->
        {
            btnOverview.setStyle("-fx-background-color : rgb(91, 11, 87)");
        });
        btnAdd.setOnMouseReleased(event-> {
            btnAdd.setStyle("-fx-background-color :  rgba(185,7,185,0.82)");
        });
        btnOverview.setOnMouseReleased(event-> {
            btnOverview.setStyle("-fx-background-color :  rgba(185,7,185,0.82)");
        });
    }

    @FXML
    protected void onAddNewPressed(ActionEvent event) throws IOException
    {
        Node root = (Node) event . getSource () ;
        Stage myStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader ( AlarmControl.class.getResource ("hello-view.fxml") ) ;
        Scene as = new Scene ( fxmlLoader.load() ) ;
        myStage . setScene ( as ) ;
        myStage . show () ;
    }




}
