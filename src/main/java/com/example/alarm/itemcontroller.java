package com.example.alarm;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class itemcontroller implements Initializable
{
    @FXML
    private Label alarmtime = new Label();
    @FXML
    private Label mednem = new Label();
    @FXML
    private Label dos = new Label();
    //private DBConn db = new DBConn();

    @FXML
    private HBox itemC;
    @FXML
    private Label alarm = new Label();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConn db = new DBConn();
        ObservableList<demoinfo> list = db.getTable();
        demoinfo[] llst = new demoinfo[list.size()];
        llst = db.forDisplay();

        AlarmControl hc = new AlarmControl();
        //System.out.println(llst[hc.num].m_name);
        mednem.setText(llst[hc.num].m_name);
        alarmtime.setText(llst[hc.num].remtimee);
        dos.setText(String.valueOf(llst[hc.num].doses));

    }

    @FXML
    private void onItemPressed() throws IOException
    {
        System.out.println(mednem.getText() + " " + dos.getText() + " " + alarmtime.getText());
        Stage myStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader ( Random.class.getResource ("random.fxml") ) ;
        Scene as = new Scene ( fxmlLoader.load() ) ;
        myStage . setScene ( as ) ;
        myStage . show () ;
    }

    @FXML
    protected void onDeletePressed()
    {

        //DBConn db = new DBConn();

    }
}
