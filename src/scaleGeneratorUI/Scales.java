package scaleGeneratorUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;

public class Scales {

    public String[] keys = {"", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};

    public Label keyLabel;
    public JFXSlider key, octave;
    public JFXButton play;

    public void initialize() {
        try {
            Font roboto = Font.loadFont(new FileInputStream(new File("resources/font/roboto/Roboto-Regular.ttf")), 42);
            keyLabel.setFont(roboto);
            play.setFont(roboto);
        } catch(Exception e) {}
        key.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = (int) (Double.parseDouble(object.toString()));
                if(i > 0 && 1 <= 12) return keys[i];
                return keys[1];
            }

            @Override
            public Double fromString(String string) {
                for(int i = 1; i <= 12; i++) {
                    if(string.equals(keys[i])) {
                        return new Double(i);
                    }
                }
                return new Double(1);
            }
        });
        key.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                keyLabel.setText(key.getLabelFormatter().toString(key.getValue()));
            }
        });
        keyLabel.setText(key.getLabelFormatter().toString(key.getValue()));
    }
    public void playScale() {
        new ScaleGenerator(key.getLabelFormatter().toString(key.getValue()), (int) octave.getValue(), "Major", 0);
    }
}
