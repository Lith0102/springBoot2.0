package com.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class lpFrameUtils {
static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	static Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getLocalGraphicsEnvironment().getDefaultScreenDevice()
					.getDefaultConfiguration());
 
	/**
	 * 获得整个屏幕的宽度
	 * 
	 * @return
	 */
	public static double getScreenWidth() {
		return screen.getWidth();
	}
 
	/**
	 * 获得整个屏幕的高度
	 * 
	 * @return
	 */
	public static double getScreenHeight() {
		return screen.getHeight();
	}
 
	/**
	 * 获得程序在屏幕中间的位置
	 * 
	 * @param WIDTH
	 *            程序的宽度
	 * @param HEIGHT
	 *            程序的高度
	 * @return Rectangle
	 */
	public static Rectangle getSysLocation(int WIDTH, int HEIGHT) {
		Rectangle rectangle = new Rectangle();
		rectangle.setLocation((int) (getScreenWidth() - WIDTH) / 2,
				(int) (getScreenHeight() - HEIGHT) / 2);
		rectangle.setSize(WIDTH, HEIGHT);
		return rectangle;
	}
	
	/**
	 * 获得程序在屏幕右下角的位置
	 * 
	 * @param WIDTH
	 *            程序的宽度
	 * @param HEIGHT
	 *            程序的高度
	 * @return Rectangle
	 */
	public static Rectangle getRDLocation(int WIDTH, int HEIGHT) {
		Rectangle rectangle = new Rectangle();
		rectangle.setLocation((int) (getScreenWidth() - WIDTH),
				(int) (getScreenHeight() - HEIGHT - insets.bottom));
		rectangle.setSize(WIDTH, HEIGHT);
		return rectangle;
	}
 
	public static void confirm(Frame frame) {
		JDialog dialog = new JDialog(frame, true);
		dialog.setBackground(Color.white);
		int width = 100;
		int height = 100;
		dialog.setBounds((int) (getScreenWidth() - width) / 2,
				(int) (getScreenHeight() - height) / 2, width, height);
		dialog.setTitle("ffffffff");
		dialog.setVisible(true);
	}
 
	static Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
 
	/**
	 * 向系统粘贴板中写入字符串
	 * 
	 * @param temp
	 *            要写入的字符串
	 */
	public static void storeClipString(String temp) {
		StringSelection stringSelection = new StringSelection(temp);
		sysClip.setContents(stringSelection, null);
 
	}
 
	/**
	 * 读取系统粘贴板中的字符串
	 * 
	 * @return
	 */
	public static String readClipString() {
		String temp = "";
		try {
			temp = (String) sysClip.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
 
	public static void shakeWindow(JFrame frame){
		Rectangle currentRect = new Rectangle();
		currentRect.setLocation(frame.getLocation());
		currentRect.setSize(frame.getSize());
		for (int i = 0; i < 5; i++) {
			Random random = new Random();
			int raint = (int) (random.nextFloat() * 10);
			int newPointX = currentRect.getLocation().x + raint;
			int newPointY = currentRect.getLocation().y + raint;
			;
			frame.setBounds(newPointX, newPointY, currentRect.getSize().width, currentRect.getSize().height);
			frame.setVisible(true);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
	}

}
