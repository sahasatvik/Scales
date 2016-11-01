package scaleGeneratorUI;

import javax.sound.midi.*;

/**
 * The ScaleGenerator class plays the notes of the input scale type with the root note and instrument of the user's choice.
 * The scale is played both in ascending and descending order with a difference of four beats between each note.
 *
 * This code has been modified by Satvik Saha, for use in the Scales wrapper which provides a Material Design compliant
 * User Interface.
 *
 * @author Suprit Behera
 * @version 1.1
 * Created on 1/11/2016
 *
 */

public class ScaleGenerator {
    public ScaleGenerator(String note, int octave, String scaleType, int instrumentNumber) {
        int rootNote = numericValueOfRootNote(note, octave);
        int scaleNumber = numericValueOfScaleType(scaleType);
        int instrument = instrumentNumber;
        int numberOfNotes = getNumberOfNotes(scaleNumber);
        if (numberOfNotes == -1)
            System.err.println("Wrong Scale Input");
        else
            run(rootNote,scaleNumber,numberOfNotes,instrument);
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a,tick);
        } catch (Exception ex) {

        }
        return event;
    }

    public void run(int rootNote, int scaleNumber, int numberOfNotes, int instrument) {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();
            int note = rootNote;
            int limit = (numberOfNotes - 1) * 4;
            int finalNote = -1;
            int beat = -1;

            for (int i = 0; i <= limit; i+=4) {
                beat = beat + 4;
                note = callCorrectMethod(i, note, scaleNumber);
                track.add(makeEvent(192,1,instrument,0,beat));
                track.add(makeEvent(144,1,note,100,beat));
                track.add(makeEvent(128,1,note,100,beat+2));
                if (i == limit)
                    finalNote = note;
            }

            int startingNote = finalNote;
            for (int j = limit; j >= 4; j-=4) {
                beat = beat + 4;
                startingNote = startingNote + (startingNote - callCorrectMethod(j, startingNote, scaleNumber));

                track.add(makeEvent(192,1,instrument,0,beat));
                track.add(makeEvent(144,1,startingNote,100,beat));
                track.add(makeEvent(128,1,startingNote,100,beat+2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch(Exception ex) {

        }
    }

    public int numericValueOfRootNote(String rootName,int octaveNumber){
        int rootNumber = 0;
        if(rootName.equals("C"))
            rootNumber = 0;
        if(rootName.equals("C#"))
            rootNumber = 1;
        if(rootName.equals("D"))
            rootNumber = 2;
        if(rootName.equals("D#"))
            rootNumber = 3;
        if(rootName.equals("E"))
            rootNumber = 4;
        if(rootName.equals("F"))
            rootNumber = 5;
        if(rootName.equals("F#"))
            rootNumber = 6;
        if(rootName.equals("G"))
            rootNumber = 7;
        if(rootName.equals("G#"))
            rootNumber = 8;
        if(rootName.equals("A"))
            rootNumber = 9;
        if(rootName.equals("A#"))
            rootNumber = 10;
        if(rootName.equals("B"))
            rootNumber = 11;
        return (octaveNumber*12  + rootNumber);

    }

    public int numericValueOfScaleType(String scaleType){
        int scaleNumber = 1;
        if(scaleType.equals("Major"))
            scaleNumber = 1;
        if(scaleType.equals("Natural Minor"))
            scaleNumber = 2;
        if(scaleType.equals("Harmonic Minor"))
            scaleNumber = 3;
        if(scaleType.equals("Ionian"))
            scaleNumber = 4;
        if(scaleType.equals("Dorian"))
            scaleNumber = 5;
        if(scaleType.equals("Phrygian"))
            scaleNumber = 6;
        if(scaleType.equals("Lydian"))
            scaleNumber = 7;
        if(scaleType.equals("Mixolydian"))
            scaleNumber = 8;
        if(scaleType.equals("Aeolian"))
            scaleNumber = 9;
        if(scaleType.equals("Locrian"))
            scaleNumber = 10;
        if(scaleType.equals("Blues"))
            scaleNumber = 11;
        if(scaleType.equals("Minor Pentatonic"))
            scaleNumber = 12;
        if(scaleType.equals("Major Pentatonic"))
            scaleNumber = 13;
        if(scaleType.equals("Whole Tone"))
            scaleNumber = 14;
        if(scaleType.equals("Whole Half Diminished"))
            scaleNumber = 15;
        if(scaleType.equals("Half Whole Diminished"))
            scaleNumber = 16;
        return scaleNumber;


    }

    public int getNumberOfNotes(int scaleNumber) {
        if (scaleNumber <= 10)
            return 8;
        if (scaleNumber == 11 || scaleNumber == 14)
            return 7;
        if (scaleNumber == 12 || scaleNumber == 13)
            return 6;
        if (scaleNumber == 15 || scaleNumber == 16)
            return 9;
        return -1;
    }
    public static int callCorrectMethod(int count,int note, int scaleNumber) {
        if (scaleNumber == 1)
            note = getNextMajorNote(count,note);
        if (scaleNumber == 2)
            note = getNextNaturalMinorNote(count, note);
        if (scaleNumber == 3)
            note = getNextHarmonicMinorNote(count, note);
        if (scaleNumber == 4)
            note = getNextIonianNote(count, note);
        if (scaleNumber == 5)
            note = getNextDorianNote(count, note);
        if (scaleNumber == 6)
            note = getNextPhrygianNote(count, note);
        if (scaleNumber == 7)
            note = getNextLydianNote(count, note);
        if (scaleNumber == 8)
            note = getNextMixolydianNote(count, note);
        if (scaleNumber == 9)
            note = getNextAeolianNote(count, note);
        if (scaleNumber == 10)
            note = getNextLocrianNote(count, note);
        if (scaleNumber == 11)
            note = getNextBluesNote(count, note);
        if (scaleNumber == 12)
            note = getNextMinorPentatonicNote(count, note);
        if (scaleNumber == 13)
            note = getNextMajorPentatonicNote(count, note);
        if (scaleNumber == 14)
            note = getNextWholeToneNote(count, note);
        if (scaleNumber == 15)
            note = getNextWholeHalfDiminishedNote(count, note);
        if (scaleNumber == 16)
            note = getNextHalfWholeDiminishedNote(count, note);
        return note;
    }

    public static int getNextMajorNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 2 || count == 4 || count == 5 || count == 6) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextNaturalMinorNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 4 || count == 6 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextHarmonicMinorNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 4 ) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else if (count == 6) {
            return note + 3;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextIonianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 2 || count == 4 || count == 5 || count == 6) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextPhrygianNote(int count, int note) {
        count = count/4;
        if (count == 2 || count == 3 || count == 4 || count == 6 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextDorianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 4 || count == 5 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextLydianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 2 || count == 3 || count == 5 || count == 6) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextMixolydianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 2 || count == 4 || count == 5 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextAeolianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 4 || count == 6 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextLocrianNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 4 || count == 6 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextWholeToneNote(int count, int note) {
        return note + 2;
    }
    public static int getNextWholeHalfDiminishedNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 3 || count == 5 || count == 7) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextHalfWholeDiminishedNote(int count, int note) {
        count = count/4;
        if (count == 2 || count == 4 || count == 6 || count == 8) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else {
            return note + 1;
        }
    }


    public static int getNextBluesNote(int count, int note) {
        count = count/4;
        if (count == 2 || count == 6 ) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else if (count == 1 || count == 5) {
            return note + 3;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextMajorPentatonicNote(int count, int note) {
        count = count/4;
        if (count == 1 || count == 2 || count == 4 ) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else if (count == 3 || count == 5) {
            return note + 3;
        }
        else {
            return note + 1;
        }
    }
    public static int getNextMinorPentatonicNote(int count, int note) {
        count = count/4;
        if (count == 2 || count == 3 || count == 5 ) {
            return (note + 2) ;
        }
        else if (count == 0)   {
            return note;
        }
        else if (count == 1 || count == 4) {
            return note + 3;
        }
        else {
            return note + 1;
        }
    }

}
		
		
	
	    

		
			
			
		
		
	
 