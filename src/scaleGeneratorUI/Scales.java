package scaleGeneratorUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileInputStream;

public class Scales {

    public String[] keys = {"", "C", "C#", "D", "Eb", "E", "F", "F#", "G", "G#", "A", "Bb", "B"};
    public String[] scaleTypes = {"Major", "Whole Tone", "Natural Minor", "Harmonic Minor",
                                    "Major Pentatonic", "Minor Pentatonic", "Whole Half Diminished",
                                    "Half Whole Diminished", "Ionian", "Dorian", "Phrygian",
                                    "Lydian", "Mixolydian", "Aeolian", "Locrian", "Blues"};
    public String[] instrumentNames = {"Grand Piano", "Church Organ", "Violin", "Acoustic Guitar",
                                        "Banjo", "Trumpet", "Sitar", "Metallic Pad", "Square Lead"};

    public GridPane pane;
    public Label keyLabel, octaveLabel;
    public JFXSlider key, octave;
    public JFXComboBox<String> scales;
    public JFXComboBox<String> instruments;
    public JFXButton play;

    public void initialize() {
        try {
            Font roboto = Font.loadFont(new FileInputStream(new File("resources/font/roboto/Roboto-Regular.ttf")), 16);
            Font robotoLarge = Font.loadFont(new FileInputStream(new File("resources/font/roboto/Roboto-Regular.ttf")), 54);
            Font robotoMedium = Font.loadFont(new FileInputStream(new File("resources/font/roboto/Roboto-Black.ttf")), 22);
            keyLabel.setFont(robotoLarge);
            octaveLabel.setFont(roboto);
            scales.getJFXEditor().setFont(robotoMedium);
            instruments.getJFXEditor().setFont(robotoMedium);
            play.setFont(robotoMedium);
        } catch(Exception e) {
            System.out.println("Font Error!");
        }
        key.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int i = (int) (Double.parseDouble(object.toString()));
                if(i > 0 && i <= 12) return keys[i];
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
        scales.setItems(FXCollections.observableArrayList(scaleTypes));
        instruments.setItems(FXCollections.observableArrayList(instrumentNames));
        scales.setValue(scaleTypes[0]);
        instruments.setValue(instrumentNames[0]);
    }
    public int getInstrumentNumber(String instrument) {
        if (instrument.equals("Grand Piano"))
            return 1;
        if (instrument.equals("Acoustic Guitar"))
            return 25;
        if (instrument.equals("Sitar"))
            return 105;
        if (instrument.equals("Violin"))
            return 41;
        if (instrument.equals("Trumpet"))
            return 57;
        if (instrument.equals("Banjo"))
            return 106;
        if (instrument.equals("Church Organ"))
            return 20;
        if (instrument.equals("Metallic Pad"))
            return 94;
        if (instrument.equals("Square Lead"))
            return 81;
        return 0;
    }
    public void playScale() {
        new scaleGeneratorUI.ScaleGenerator(key.getLabelFormatter().toString(key.getValue()),
                            (int) octave.getValue(),
                            scales.getValue(),
                            getInstrumentNumber(instruments.getValue()));
    }
}
