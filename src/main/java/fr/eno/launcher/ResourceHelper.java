package fr.eno.launcher;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class ResourceHelper
{	
	public static Background createBackground(String name)
	{
		Background bg = new Background(new BackgroundImage(loadImage(name), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
		return bg;
	}
	
	public static Image loadImage(String name)
	{
		Image backgroundImage = new Image(ResourceHelper.class.getClassLoader().getResource("fr/eno/launcher/images/" + name).toString());
		
		return backgroundImage;
	}
	
	public static Font getFont(int size)
	{
		InputStream is = ResourceHelper.class.getClassLoader().getResourceAsStream("fonts/minecraft.ttf");
		Font font = Font.loadFont(is, size);
		return font;
	}
	
	public static InputStream getSound(String name)
	{
		InputStream is = ResourceHelper.class.getClassLoader().getResourceAsStream("sounds/" + name);
		
		return is;
	}
	
	public static Image decodeIcon(byte[] data)
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		Image img = new Image(bais);
		
		return img;
	}
	
	public static void playSound(String sound)
	{
	    try
	    {
	    	AudioInputStream audioIn = AudioSystem.getAudioInputStream(getSound(sound));
	    	Clip clip = AudioSystem.getClip();
		    clip.open(audioIn);
	    	FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    	gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
		    clip.start();
	    }
	    catch(IOException | LineUnavailableException | UnsupportedAudioFileException e)
	    {
	    	e.printStackTrace();
	    }	    
	}
}
