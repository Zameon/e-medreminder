package com.example.alarm;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    //Time time = new Time(new CurrentTime().currentTime());
    //Time time = new Time("12:13:34");
    Time time = new Time(new CurrentTime().currentTime());
    HashMap<String, Boolean> alarmtimes = new HashMap<String, Boolean>();
    HashMap<Pair<String, String>, Boolean> timeandweek = new HashMap<>();


    @FXML
    private Text timer;

    @FXML
    private TextField alarmTime;
    @FXML
    private Button b1;

    @FXML
    private Button hourup;

    @FXML
    private Button hourdown;
    @FXML
    private Button minup;
    @FXML
    private Button mindown;

    @FXML
    private TextField hourfield;
    @FXML
    private TextField minfield;

    @FXML
    private TextField medicinename;
    @FXML
    private TextField dosage;

    @FXML
    private Button Submit;
    @FXML
    private Button Done;


    private String convertto12(String t) {
        String ans = new String();
        int h1 = (int) t.charAt(0) - '0';
        int h2 = (int) t.charAt(1) - '0';

        int hh = h1 * 10 + h2;
        int temp = hh;
        hh %= 12;


        if (hh == 0) {
            ans += '1';
            ans += '2';

            for (int i = 2; i < 8; ++i) {
                ans += t.charAt(i);
            }
        } else {
            ans += hh / 10;
            ans += hh % 10;

            for (int i = 2; i < 8; ++i) {
                ans += t.charAt(i);
            }
        }
        ans += ' ';

        if (temp < 12) {
            ans += 'A';
            ans += 'M';
        } else {
            ans += 'P';
            ans += 'M';
        }
        return ans;
    }

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e ->  {
                        String[] brk = (java.time.LocalDate.now()).toString().split("-");
                        CurrentTime tm;
                        try {
                            tm = new CurrentTime(brk[2]+"-"+brk[1]+"-"+brk[0]);
                        } catch (ParseException ex)
                        {
                            throw new RuntimeException(ex);
                        }
                        String now = time.getCurrentTime();
                        System.out.println(now + " " + tm.currentweekday());
                        Pair<String, String> temp = new Pair<String, String>(now, tm.currentweekday());
                        System.out.println(temp);
                        System.out.println(alarmtimes.get(now) + " " + timeandweek.get(temp));
                        //setalarmtime();
                        if (alarmtimes.get(now) == Boolean.TRUE && timeandweek.get(temp) == Boolean.TRUE) {
                            System.out.println("ALARM!");
                            //System.out.println(now + " " + tm.currentweekday());
                            //try {
                                //handleChanges(now, tm.currentweekday());
                            //} catch (ParseException ex) {
                               // throw new RuntimeException(ex);
                            //}
                            showNotifications();
                        }

                        //if(time.getCurrentTime().equals(alarmTime.getText())){
                        //System.out.println("ALARM!");
                        //setalarmtime();
                        time.oneSecondPassed();
                        String t = new String(time.getCurrentTime());
                        String ans = new String(convertto12(t));
                        timer.setText(ans);
                    }));

    @FXML
    private Label minflderr;
    @FXML
    private TextField mednum;
    @FXML
    private VBox days = null;
    @FXML
    private Text txt;
    @FXML
    private Button okbutton;
    private ObservableList<demoinfo> list = FXCollections.observableArrayList();

    // Create a combo box
    @FXML
    private ComboBox myComboBox = new ComboBox();
    @FXML
    private ComboBox nums = new ComboBox();

    @FXML
    private Label selected = new Label("default item selected");
    // final int f = Integer.parseInt(mednum.getText());
    ObservableList<TextField> fields = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initmap();

        // Create action event
        myComboBox.getItems().addAll(
                "Pill(s)",
                "Cc",
                "Ml",
                "Gr",
                "Mg",
                "Drop(s)",
                "Piece(s)",
                "Unit(s)",
                "Teaspoon(s)",
                "Tablespoon",
                "Spray",
                "Puff(s)"
        );

        myComboBox.setOnAction((Event ev) -> {
            selected.setText(myComboBox.getSelectionModel().getSelectedItem().toString());
        });
        myComboBox.setValue("Pill(s)");

        // Create a tile pane

        timer.setText(time.getCurrentTime());

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        minfield.getText();
        nums.getItems().addAll(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7"
        );

        mednum.setVisible(true);

        if (setWeekly.isSelected() == false) enddate.setDisable(false);
        else enddate.setDisable(true);

        System.out.println(setWeekly.isSelected());


        /*nums.setOnAction((Event ev) -> {
            mednum.setText(nums.getSelectionModel().getSelectedItem().toString());
            //days.setVisible(true);
            final int num = Integer.parseInt(mednum.getText());

            days.getChildren().clear();

            for(int i=1; i<=num; i++)
            {
                days.getStylesheets().add(getClass().getResource("numofdays.css").toExternalForm());
                TextField tf = new TextField(String.valueOf(i));
                days.getChildren().add(tf);

            }


        });*/


    }

    /*private void handleChanges(String crtime, String weeek)
    {

        DBConn db = new DBConn();
        db.updatenextTimes(medicinename.getText(), crtime, weeek);
        //db.updatenextTimes(medicinename.getText(), crtime, tm.currentweekday());
    }*/

    @FXML
    private void onOKpressed()
    {
        list.add(new demoinfo(medicinename.getText(), alarmTime.getText(), Integer.parseInt(dosage.getText())));
        medicinename.setText("");
        dosage.setText("");
    }

    @FXML
    private void onSchedulePressed()
    {
        mednum.setVisible(true);
        days.setVisible(true);

        //final int num;


        nums.setOnAction((Event ev) -> {
            mednum.setText(nums.getSelectionModel().getSelectedItem().toString());
        });

        System.out.println(mednum.getText());

    }

    @FXML
    protected void onWeeklyPressed()
    {
        if(setWeekly.isSelected() == false) enddate.setDisable(false);
        else enddate.setDisable(true);
    }

    @FXML
    private Label medtype;

    @FXML
    private void onSyringeClicked()
    {
        medtype.setText("Syringe");
    }
    @FXML
    private void onPillsClicked()
    {
        medtype.setText("Pills");
    }
    @FXML
    private void onCapsuleClicked()
    {
        medtype.setText("Capsule");
    }
    @FXML
    private void onLiquidClicked()
    {
        medtype.setText("Liquid");
    }

    private void initmap()
    {
        DBConn db = new DBConn();
        alarmtimes = db.getAlarms();
        timeandweek = db.getTimes();
    }

    @FXML
    private DatePicker startdate;
    @FXML
    private DatePicker enddate;


    @FXML
    protected void showNotifications()
    {
        Stage stage = new Stage();
        stage.setTitle("Creating popup");

        // create a button
        HBox hbox=new HBox();
        hbox.setMinSize(60,40);

        Button button = new Button("ALARM!");
        hbox.getChildren().add(button);
        // create a tile pane
        TilePane tilepane = new TilePane();

        // create a label
        Label label = new Label(medicinename.getText() + ", " + dosage.getText());
        //Label label2 = new Label(dosage.getText());

        // create a popup
        Popup popup = new Popup();

        label.setStyle(" -fx-background-color: white;");
        popup.getContent().add(label);

        label.setMinWidth(80);
        label.setMinHeight(50);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        if (!popup.isShowing())
                            popup.show(stage);
                        else
                            popup.hide();
                    }
                };
        button.setOnAction(event);

        //button.on

        // add button
        tilepane.getChildren().add(hbox);

        // create a scene
        Scene scene = new Scene(tilepane, 200, 200);

        // set the scene
        stage.setScene(scene);

        Media ple = new Media(new File("who-let-the-dogs-out-4720.mp3").toURI().toString());
        //Media ple = new Media(new File("twirling-intime-lenovo-k8-note-alarm-tone-41440.mp3").toURI().toString());
        MediaPlayer mp = new MediaPlayer(ple);
        mp.play();

        stage.show();
        stage.setOnCloseRequest( eevent -> {mp.stop();} );

    }

   private String meridien;

    public  void setHourup() {
        if (!hourfield.getText().equals("12")) {
            if(Integer.parseInt(hourfield.getText()) < 9)
            {
                hourfield.setText("0" + Integer.toString(Integer.parseInt(hourfield.getText()) + 1));
            }
            else hourfield.setText(Integer.toString(Integer.parseInt(hourfield.getText()) + 1));
        }
        else
        {
            System.out.println("Limit reached");
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);


            alert.getDialogPane().setContentText("Boundary reached!");
            alert.getDialogPane().setHeaderText("INVALID");
            alert.showAndWait();
        }
    }

    public  void setHourdown()
    {
        if (!hourfield.getText().equals("01")) {

            if(Integer.parseInt(hourfield.getText()) <= 10)
            {
                hourfield.setText("0" + Integer.toString(Integer.parseInt(hourfield.getText()) - 1));
            }
            else hourfield.setText(Integer.toString(Integer.parseInt(hourfield.getText()) - 1));

        }
        else
        {
            System.out.println("Limit reached");
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);


            alert.getDialogPane().setContentText("Boundary reached!");
            alert.getDialogPane().setHeaderText("INVALID");
            alert.showAndWait();
        }
    }

    public  void setminup()
    {
        if (!minfield.getText().equals("59")) {

            if(Integer.parseInt(minfield.getText()) < 9)
            {
                minfield.setText("0" + Integer.toString(Integer.parseInt(minfield.getText()) + 1));
            }
            else minfield.setText(Integer.toString(Integer.parseInt(minfield.getText()) + 1));

        }
        else
        {
            System.out.println("Limit reached");
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);


            alert.getDialogPane().setContentText("Boundary reached!");
            alert.getDialogPane().setHeaderText("INVALID");
            alert.showAndWait();
        }
    }
    public  void setMindown()
    {
        if (!minfield.getText().equals("00")) {
            if(Integer.parseInt(minfield.getText()) <= 10)
            {
                minfield.setText("0" + Integer.toString(Integer.parseInt(minfield.getText()) - 1));
            }
            else minfield.setText(Integer.toString(Integer.parseInt(minfield.getText()) - 1));

        }
        else
        {
            System.out.println("Limit reached");
            Alert.AlertType type = Alert.AlertType.WARNING;
            Alert alert = new Alert(type, "");
            alert.initModality(Modality.APPLICATION_MODAL);


            alert.getDialogPane().setContentText("Boundary reached!");
            alert.getDialogPane().setHeaderText("INVALID");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onSubmitClicked()
    {

        if(Integer.parseInt(minfield.getText()) > 59 || Integer.parseInt(minfield.getText()) < 0)
        {
            minflderr.setText("Invalid time. Please enter again");
            minfield.setText("");
        }
        else {

            minflderr.setText("");
            if (meridien.equals("AM")) {
                if (hourfield.getText().equals("12")) alarmTime.setText("00:" + minfield.getText() + ":00");
                else alarmTime.setText(hourfield.getText() + ":" + minfield.getText() + ":00");
            } else {
                if (hourfield.getText().equals("12")) alarmTime.setText("12:" + minfield.getText() + ":00");
                else alarmTime.setText(Integer.parseInt(hourfield.getText()) + 12 + ":" + minfield.getText() + ":00");
            }
        }


    }

    @FXML
    private ToggleButton ambut;

    @FXML
    private ToggleGroup buttons;

    @FXML
    private ToggleButton pmbut;

    @FXML
    void togglebutton(ActionEvent event)
    {
        if(event.getSource() == ambut)
        {
            meridien = "AM";
            //System.out.println(meridien);
        }
        else if(event.getSource() == pmbut)
        {
            meridien = "PM";
        }
        System.out.println(meridien);
    }

    @FXML
    private RadioButton setWeekly;
    @FXML
    private CheckBox Sun;
    @FXML
    private CheckBox Mon;
    @FXML
    private CheckBox Tue;
    @FXML
    private CheckBox Wed;
    @FXML
    private CheckBox Thurs;
    @FXML
    private CheckBox Fri;
    @FXML
    private CheckBox Sat;



    @FXML
    protected void onDonePressed(ActionEvent event) throws ParseException, IOException
    {
        String[] stuff = startdate.getValue().toString().split("-");
        String starans = new String(stuff[2] +"-"+stuff[1]+"-"+stuff[0]);
        String endans = new String();
        String weak = new String();
        int yesweekly = 0;
        if(setWeekly.isSelected())
        {
            yesweekly = 1;
            endans = "";
        }
        DBConn db = new DBConn();
        //System.out.println(alarmTime.getText());
        //db.insertval(medicinename.getText(), alarmTime.getText(), Integer.parseInt(dosage.getText()));
        if(Sun.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Sunday", yesweekly);
        }
        if(Mon.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Monday", yesweekly);
        }
        if(Tue.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Tuesday", yesweekly);
        }
        if(Wed.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Wednesday", yesweekly);
        }
        if(Thurs.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Thursday", yesweekly);
        }
        if(Fri.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Friday", yesweekly);
        }
        if(Sat.isSelected())
        {
            db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, "Saturday", yesweekly);
        }

        timeandweek = db.getTimes();

        Iterator it = timeandweek.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry mpel = (Map.Entry)it.next();
            System.out.println(mpel.getKey() + " " + mpel.getValue());
        }


        db.close();

        //db.insertvalues(medicinename.getText(), Integer.parseInt(dosage.getText()), myComboBox.getSelectionModel().getSelectedItem().toString(), medtype.getText(), Integer.parseInt(nums.getSelectionModel().getSelectedItem().toString()), alarmTime.getText(), starans, endans, yesweekly, );
        alarmtimes.put(alarmTime.getText(), true);
        Node root = (Node) event . getSource () ;
        Stage myStage = ( Stage ) root . getScene () . getWindow () ;
        FXMLLoader fxmlLoader = new FXMLLoader ( HelloController.class.getResource ("remview2.fxml") ) ;
        Scene as = new Scene ( fxmlLoader.load() ) ;
        myStage . setScene ( as ) ;
        myStage . show () ;
    }

    @FXML
    protected void onBackPressed(ActionEvent event) throws IOException
    {
        Node root = (Node) event . getSource () ;
        Stage myStage = ( Stage ) root . getScene () . getWindow () ;
        FXMLLoader fxmlLoader = new FXMLLoader ( HelloController.class.getResource ("remview2.fxml") ) ;
        Scene as = new Scene ( fxmlLoader.load() ) ;
        myStage . setScene ( as ) ;
        myStage . show () ;
    }




}