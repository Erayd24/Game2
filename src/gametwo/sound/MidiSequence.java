package gametwo.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.*;

public class MidiSequence {
	//primary midi sequencer object
	private Sequencer sequencer;
	
	//Make sequence a read-only property
	private Sequence song;
	public Sequence getSong() { return song; }
	
	//Filename property - read only
	private String filename;
	public String getFilename() { return filename; }
	
	//looping property for continuous playback
	private boolean looping = false;
	public void setLooping(boolean _looping) { looping = _looping; }
	public boolean getLooping() {return looping; }
	
	//Repeat sound multiple times
	private int repeat = 0;
	public void setRepeat(int _repeat) { repeat = _repeat; }
	public int getRepeat() { return repeat; }
	
	//Is the sequence ready?
	public boolean isLoaded() {
		return (boolean)(sequencer.isOpen());
	}
	
	private URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch(Exception e) {}
		return url;
	}
	
	//Constructor
	public MidiSequence() {
		try {
			//Start sequencer
			sequencer = MidiSystem.getSequencer();
		} catch (MidiUnavailableException e) {}
	}
	
	//Constructor that accepts a filename
	public boolean MidiSequence(String midifile) {
		try {
			//load the midi file in to the sequencer
			filename = midifile;
			song = MidiSystem.getSequence(getURL(filename));
			sequencer.setSequence(song);
			sequencer.open();
			return true;
		} catch(InvalidMidiDataException e) { return false; } catch(MidiUnavailableException e) { return false; } catch(IOException e) { return false; }
	}
	
	//Play the midi sequence
	public void play() {
		if(!sequencer.isOpen()) return;
		if(looping) {
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		} else {
			sequencer.setLoopCount(repeat);
			sequencer.start();
		}
	}
	
	public void stop() {
		sequencer.stop();
	}
}