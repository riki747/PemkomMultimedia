package com.mycompany.multimedia;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

public class PrimaryController implements Initializable {

    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private StackPane sPane;

    @FXML
    private Button playPause;

    @FXML
    private Slider volume;

    @FXML
    private Slider seek;

    @FXML
    private BorderPane bPane;

    private List<String> playlist = new ArrayList<>();
    private List<String> sourceName = new ArrayList<>();
    private static int INDEX = 0;
    private static int PLAY = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Kosong dulu
    }

    @FXML
    private void openFiles(ActionEvent event) {
    DirectoryChooser dc = new DirectoryChooser();
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    File selectedDirectory = dc.showDialog(stage);
    if (selectedDirectory != null) {
        System.out.println("Selected folder: " + selectedDirectory.getAbsolutePath());
    }
    System.out.println("Open Folder diklik");
}
    private void playMedia(int index) {
        String source = playlist.get(index);
        Media media = new Media(source);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        // Auto resize
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        mediaView.fitWidthProperty().bind(bPane.widthProperty());
        mediaView.fitHeightProperty().bind(bPane.heightProperty());

        mediaView.setPreserveRatio(true);

        volume.setValue(50);
        volume.valueProperty().addListener((Observable observable) -> {
            mediaPlayer.setVolume(volume.getValue() / 100);
        });

        mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
            seek.setValue(newValue.toSeconds());
        });

        seek.setOnMouseClicked(event -> mediaPlayer.seek(Duration.seconds(seek.getValue())));
        seek.setOnMouseDragExited(event -> mediaPlayer.seek(Duration.seconds(seek.getValue())));

        mediaPlayer.play();
        PLAY = 1;
        playPause.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/00.png"))));
    }

    @FXML
    private void pausePlay(ActionEvent event) {
        if (!playlist.isEmpty()) {
            if (PLAY == 1) {
                mediaPlayer.pause();
                playPause.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/5.png"))));
                PLAY = 0;
            } else {
                mediaPlayer.play();
                playPause.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/00.png"))));
                PLAY = 1;
            }
        } else {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Message");
            dialog.setContentText("Please open media!");
            dialog.setOnCloseRequest(e -> dialog.close());
            dialog.initStyle(StageStyle.DECORATED);
            dialog.initModality(Modality.NONE);
            dialog.show();
        }
    }

    @FXML
    private void stop(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        playPause.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/5.png"))));
        PLAY = 0;
    }

    @FXML
    private void forward(ActionEvent event) {
        if (INDEX < playlist.size() - 1) {
            INDEX++;
            playMedia(INDEX);
        }
    }

    @FXML
    private void backward(ActionEvent event) {
        if (INDEX > 0) {
            INDEX--;
            playMedia(INDEX);
        }
    }

    @FXML
    private void seekForward(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.setRate(1.5);
        }
    }

    @FXML
    private void seekBackward(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.setRate(0.5);
        }
    }
}
