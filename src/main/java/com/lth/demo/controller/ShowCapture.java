package com.lth.demo.controller;
 
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
 
import com.util.lpFrameUtils;
 
import com.sun.awt.AWTUtilities;
 
public class ShowCapture {
 
	JFrame frame = null;
	int WIDTH = 500;
	int HEIGHT = 500;
	JLabel label = null;
	public ShowCapture(final String url) {
		frame = new JFrame();
		label = new JLabel();
		final Timer timer = new Timer();
		TimerTask task = new TimerTask(){
 
			File file = new File(url);
			File[] files = file.listFiles();
			final int size = files.length;
			int index = 0;
			@Override
			public void run() {
				if(index < size-1){
					label.setText("");
					Image image = Toolkit.getDefaultToolkit().getImage(files[index].getPath());
					ImageIcon icon = new ImageIcon();
					image = image.getScaledInstance((int)lpFrameUtils.getScreenWidth(),(int)lpFrameUtils.getScreenHeight(), Image.SCALE_FAST);
					icon.setImage(image);
					label.setIcon(icon);
					index++;
				}else{
					timer.cancel();
					frame.getContentPane().setBackground(Color.BLACK);
					AWTUtilities.setWindowOpacity(frame, 0.5f);
					frame.remove(label);
					JOptionPane.showMessageDialog(frame, " 播放完毕！");
					frame.dispose();
				}
			}
		};
		timer.schedule(task, 1000, 200);
		
		frame.setUndecorated(true);
		frame.add(label);
		frame.setBounds(0,0,(int)lpFrameUtils.getScreenWidth(),(int)lpFrameUtils.getScreenHeight());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		frame.setResizable(false);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.addAWTEventListener(new AWTEventListener(){
 
			public void eventDispatched(AWTEvent event) {
				if(event.getClass()== KeyEvent.class){
					KeyEvent e = (KeyEvent) event;
					int key = e.getKeyCode();
					if(key == 27){
						timer.cancel();
						frame.dispose();
					}
				}
			}
			
		}, 1000);
	}
	
	
	public static void main(String[] args) {
		new ShowCapture("");
	}
 
}