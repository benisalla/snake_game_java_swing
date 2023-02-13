package Snake;
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.SourceDataLine;
 
public class SnakeEating extends Thread    //implements Runnable
{
 
    //defining the byte buffer
    private static final int BUFFER_SIZE = 4096;
    String thePath;
    
    
    public SnakeEating(String path)
    {
    	thePath = path ;
    }
    

    void play() {
        File soundFile = new File(thePath);
        try {
          //convering the audio file to a stream
            AudioInputStream sampleStream = AudioSystem.getAudioInputStream(soundFile);
 
            AudioFormat formatAudio = sampleStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatAudio);
 
            SourceDataLine theAudioLine = (SourceDataLine) AudioSystem.getLine(info);
 
            theAudioLine.open(formatAudio);
 
            theAudioLine.start();
             
//            System.out.println("Audio Player Started.");
             
            byte[] bufferBytes = new byte[BUFFER_SIZE];
            int readBytes = -1;
 
            while ((readBytes = sampleStream.read(bufferBytes)) != -1) {
                theAudioLine.write(bufferBytes, 0, readBytes);
            }
             
            theAudioLine.drain();
            theAudioLine.close();
            sampleStream.close();
             
//            System.out.println("Playback has been finished.");
             
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Line not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Experienced an error.");
            e.printStackTrace();
        }      
    }


	@Override
	public void run() 
	{
		this.play();
	}
     
	static public void main(String[] args)
	{
//		SnakeEating SE1 = new SnakeEating("D:\\eclipse\\MyProjects\\src\\Snake\\snakeEating.wav");
//		SnakeEating SE2 = new SnakeEating("D:\\eclipse\\MyProjects\\src\\Snake\\eatinghimself.wav");
//		SnakeEating SE3 = new SnakeEating("D:\\eclipse\\MyProjects\\src\\Snake\\youlost.wav");
//		
//		Thread t1 = new Thread(SE1);
//		Thread t2 = new Thread(SE2);
//		Thread t3 = new Thread(SE3);
//		
//		t1.start();
//		t2.start();
//		t3.start();
		String path;
//		path = "D:\\eclipse\\MyProjects\\src\\Snake\\yes_yes.wav";
		path = "D:\\eclipse\\MyProjects\\src\\Snake\\WooSound.wav";
//		path = "D:\\eclipse\\MyProjects\\src\\Snake\\comic_applause.wav";
		new SnakeEating(path).play();
		
		
	}
 
}












//public static void main(String[] args) {
////  String thePath = "D:\\\\eclipse\\\\MyProjects\\\\src\\\\Snake\\\\snakeEating.wav";
////  SnakeEating player = new SnakeEating();
////  player.play(thePath);
//}








//Play Sound Using SourceDataLine in Java
//The SourceDataLine is found in javax.sound.sampled.SourceDataLine. To implement SourceDataLine sound play, we follow the following steps.
//
//The first step is to create an object of the audio input stream. This step converts the audio file into an input stream that the app can use.
//The second step is to open a line using the AudioSystem.getLine() method.
//The third step is to repeatedly read the specified chunks of the audio input stream created in step 1 and forward it to SourceDataLine’s buffer. This is repeated until the end of the audio stream.
//After the read and buffer have been completed, the resources are freed by closing the line.




//================================================================================================
/*
package Snake;

import java.io.File; 
import java.util.Scanner; 
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException; 



public class SnakeEating 
{
	//define storage for start position
		Long nowFrame; 
		Clip clip; 
		
		// get the clip status 
		String thestatus; 
		
		AudioInputStream audioStream; 
		static String thePath = "D:\\eclipse\\MyProjects\\src\\Snake\\snakeEating.wav"; 

		// initialize both the clip and streams 
		public SnakeEating() 
			throws UnsupportedAudioFileException, 
			IOException, LineUnavailableException 
		{ 
			// the input stream object 
			audioStream = 
					AudioSystem.getAudioInputStream(
					    new File(thePath)
					    .getAbsoluteFile()); 
			
			// the reference to the clip 
			clip = AudioSystem.getClip(); 
	 
			clip.open(audioStream); 
			
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} 

		public static void main(String[] args) 
		{ 
			System.out.println("hello");
			try
			{ 
				SnakeEating sound = new SnakeEating (); 
				 
				 
				while (true) 
				{ 
					sound.clip.start();
				} 
			} 
			
			catch (Exception e) 
			{ 
				System.out.println("Experienced an error while playing sound."); 
				e.printStackTrace(); 
			
			} 
		}
		
		
};

*/






/*
 * package Snake;

import java.io.File; 
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException; 



public class SnakeEating 
{
	//define storage for start position
		Long nowFrame; 
		Clip clip; 
		
		AudioInputStream audioStream; 
		static String thePath; 

		// initialize both the clip and streams 
		public SnakeEating() 
			throws UnsupportedAudioFileException, 
			IOException, LineUnavailableException 
		{ 
			// the input stream object 
			audioStream = AudioSystem.getAudioInputStream(new File(thePath).getAbsoluteFile()); 
			
			// the reference to the clip 
			clip = AudioSystem.getClip(); 
	 
			clip.open(audioStream); 
			
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} 

		public static void main(String[] args) 
		{ 
			try
			{ 
				thePath = "D:\\eclipse\\MyProjects\\src\\Snake\\snakeEating.wav"; 
				SnakeEating  sound = new SnakeEating(); 
				sound.play(); 
			} 
			catch (Exception e) 
			{ 
				System.out.println("Experienced an error while playing sound."); 
				e.printStackTrace(); 
			} 
		}  
		
		// play 
		public void play() 
		{ 
			clip.start(); 
		} 
		public void pause() 
		{ 
			this.nowFrame = this.clip.getMicrosecondPosition(); 
			clip.stop();  
		} 
}


//================================================================================
//================================================================================
package Snake;

import java.io.File; 
import java.util.Scanner; 
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException; 



public class SnakeEating 
{
	//define storage for start position
		Long nowFrame; 
		Clip clip; 
		
		// get the clip status 
		String thestatus; 
		
		AudioInputStream audioStream; 
		static String thePath; 

		// initialize both the clip and streams 
		public SnakeEating() 
			throws UnsupportedAudioFileException, 
			IOException, LineUnavailableException 
		{ 
			// the input stream object 
			audioStream = 
					AudioSystem.getAudioInputStream(
					    new File(thePath)
					    .getAbsoluteFile()); 
			
			// the reference to the clip 
			clip = AudioSystem.getClip(); 
	 
			clip.open(audioStream); 
			
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} 

		public static void main(String[] args) 
		{ 
			try
			{ 
			  //add the path to the audio file
				thePath = "D:\\eclipse\\MyProjects\\src\\Snake\\snakeEating.wav"; 
			
				SnakeEating  simpleSoundPlayer = 
								new SnakeEating (); 
				
				simpleSoundPlayer.play(); 
				Scanner scanned = new Scanner(System.in); 
				
				//show the options
				while (true) 
				{ 
					System.out.println("1. pause"); 
					System.out.println("2. resume"); 
					System.out.println("3. restart"); 
					System.out.println("4. stop"); 
					System.out.println("5. Jump to specific time"); 
					int a = scanned.nextInt(); 
					simpleSoundPlayer.gotoChoice(a); 
					if (a == 4) 
					break; 
				} 
				scanned.close(); 
			} 
			
			catch (Exception e) 
			{ 
				System.out.println("Experienced an error while playing sound."); 
				e.printStackTrace(); 
			
			} 
		} 
		
		// operation is now as per the user's choice 
		
		private void gotoChoice(int a) 
				throws IOException, LineUnavailableException, UnsupportedAudioFileException 
		{ 
			switch (a) 
			{ 
				case 1: 
					pause(); 
					break; 
				case 2: 
					resumeAudio(); 
					break; 
				case 3: 
					restart(); 
					break; 
				case 4: 
					stop(); 
					break; 
				case 5: 
					System.out.println("Selected time (" + 0 + 
					", " + clip.getMicrosecondLength() + ")"); 
					Scanner scan = new Scanner(System.in); 
					long cc = scan.nextLong(); 
					jump(cc); 
					break; 
		
			} 
		
		} 
		
		// play 
		public void play() 
		{ 
			//start the clip 
			clip.start(); 
			
			thestatus = "play"; 
		} 
		
		// Pause audio 
		public void pause() 
		{ 
			if (thestatus.equals("paused")) 
			{ 
				System.out.println("audio is already paused"); 
				return; 
			} 
			this.nowFrame = 
			this.clip.getMicrosecondPosition(); 
			clip.stop(); 
			thestatus = "paused"; 
		} 
		
		// resume audio
		public void resumeAudio() throws UnsupportedAudioFileException, 
								IOException, LineUnavailableException 
		{ 
			if (thestatus.equals("play")) 
			{ 
				System.out.println("The audio is"+ 
				"being played"); 
				return; 
			} 
			clip.close(); 
			resetAudioStream(); 
			clip.setMicrosecondPosition(nowFrame); 
			this.play(); 
		} 
		
		// restart audio 
		public void restart() throws IOException, LineUnavailableException, 
												UnsupportedAudioFileException 
		{ 
			clip.stop(); 
			clip.close(); 
			resetAudioStream(); 
			nowFrame = 0L; 
			clip.setMicrosecondPosition(0); 
			this.play(); 
		} 
		
		// stop audio 
		public void stop() throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
		{ 
			nowFrame = 0L; 
			clip.stop(); 
			clip.close(); 
		} 
		
		// jump to a selected point 
		public void jump(long a) throws UnsupportedAudioFileException, IOException, 
															LineUnavailableException 
		{ 
			if (a > 0 && a < clip.getMicrosecondLength()) 
			{ 
				clip.stop(); 
				clip.close(); 
				resetAudioStream(); 
				nowFrame = a; 
				clip.setMicrosecondPosition(a); 
				this.play(); 
			} 
		} 
		
		// reset the audio stream 
		public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
												LineUnavailableException 
		{ 
			audioStream = AudioSystem.getAudioInputStream( 
			new File(thePath).getAbsoluteFile()); 
			clip.open(audioStream); 
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} 
}

		 
*/
