package net.voidinteractive.safariTD.Sound;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import com.sun.javafx.application.PlatformImpl;

@SuppressWarnings("restriction")
public class Sound {

   public static final int UNKNOWN = 0;
   public static final int PLAYING = 1;
   public static final int PAUSED = 2;
   public static final int STOPPED = 3;
   public static final int READY = 4;
   public static final int STALLED = 5;
   
   private MediaPlayer mediaPlayer;
   private boolean initialized = false;

   public static Sound test = new Sound("/sound/NokiaDubstep.mp3", false);
   public static Sound theme = new Sound("/sound/theme.mp3", true);
   
   // EXAMPLE
   //public static AudioClip pistol = new AudioClip(Sound.class.getResource("/media/gunfire/bullet_01.wav").toString());
   public static AudioClip shoot = new AudioClip(Sound.class.getResource("/sound/shoot.wav").toString());
   
   public Sound(String path, boolean autoplay) {
      if (!initialized) init();

      try {
         Media sound = new Media(Sound.class.getResource(path).toString());
         mediaPlayer = new MediaPlayer(sound);
         mediaPlayer.setAutoPlay(autoplay);
         mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
            	//Platform.exit();
            	// Restarts clip to beginning when it finishes
            	mediaPlayer.seek(new Duration(0));
            	mediaPlayer.pause();
            }
         });
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void init() {
      PlatformImpl.startup(new Runnable() {
         @Override
         public void run() {
         }
      });
      
      Platform.setImplicitExit(true);
   }
   
   public void play() {
      mediaPlayer.play();
   }

   public void play(double volume){
      setVolume(volume);
      play();
   }
   
   public void play(int cycleCount){
      setCycleCount(cycleCount);
      play();
   }
   
   public void play(double volume, int cycleCount){
      setVolume(volume);
      setCycleCount(cycleCount);
      play();
   }

   public void pause() {
      mediaPlayer.pause();
   }

   public void stop() {
      mediaPlayer.stop();
   }
   
   public void setVolume(double value) {
      mediaPlayer.setVolume(value);
   }

   public void autoPlay(boolean value) {
      mediaPlayer.setAutoPlay(value);
   }

   public void setBalance(double value) {
      mediaPlayer.setBalance(value);
   }

   public void setCycleCount(int value) {
      mediaPlayer.setCycleCount(value);
   }

   public void setMute(boolean value) {
      mediaPlayer.setMute(value);
   }
   
   public Duration getTime() {
	   return mediaPlayer.getCurrentTime();
   }
   
   public MediaPlayer getMedia() {
	   return mediaPlayer;
   }
   
   public int getStatus(){
      if(mediaPlayer.getStatus().equals(Status.UNKNOWN)) return 0;   //unknown
      if(mediaPlayer.getStatus().equals(Status.PLAYING)) return 1;   //playing
      if(mediaPlayer.getStatus().equals(Status.PAUSED)) return 2;    //paused
      if(mediaPlayer.getStatus().equals(Status.STOPPED)) return 3;   //stopped
      if(mediaPlayer.getStatus().equals(Status.READY)) return 4;     //ready
      if(mediaPlayer.getStatus().equals(Status.STALLED)) return 5;   //stalled
      
      return -1;                                          			 //default
   }
}