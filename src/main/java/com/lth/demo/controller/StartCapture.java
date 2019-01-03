package com.lth.demo.controller;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.util.lpCapUtils;
import com.util.lpFrameUtils;
import com.util.lpUI;

public class StartCapture {
	JFrame frame = null;

	int WIDTH = 580;

	int HEIGHT = 290;

	JLabel titleLabel = null;
	JLabel timeLabel = null;
	JLabel currentTime = null;

	JWindow openFrame = null;
	JMenuBar bar = null;
	JMenu menu = null;
	JMenu menu2 = null;
	JMenu menu3 = null;

	JPanel panel = null;
	InnerTask it = new InnerTask();
	boolean isRecord = false; // 视频是否正在录制

	Point startPoint = null;
	Point endPoint = null;
	JPanel windowPanel = null;
	JPanel panelRight = null;

	JToolBar stateBar = null;

	// ++++++++++++++++++++++++++++++++++
	JList videoList = null;
	DefaultListModel listModel = null;
	JScrollPane scrollPane = null;
	// ++++++++++++++++++++++++++++++++++

	public void initList() {
		listModel.clear();
		List list = lpCapUtils.getCaptureFolder();
		int length = list.size();
		// ++++++++++++++++++++++++++++++++++++++

		for (int i = 0; i < length; i++) {
			listModel.addElement(" " + list.get(i));
		}

	}

	public StartCapture() {

		panelRight = new JPanel();

		listModel = new DefaultListModel();
		initList();

		videoList = new JList(listModel);
		videoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		videoList.setSelectedIndex(0);
		videoList.setVisibleRowCount(5);
		videoList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// if(e.getClickCount() == 2 && e.getButton() == 1){
				// String list = (String) listModel.get(videoList.getSelectedIndex());
				// new ShowCapture(CapUtils.URL+list.trim());
				// }
				if (e.getButton() == 3) {
					int index = videoList.locationToIndex(e.getPoint());
					videoList.setSelectedIndex(index);
					JPopupMenu menu = new JPopupMenu("dddddd");
					JMenuItem item1 = new JMenuItem("播放");
					JMenuItem item3 = new JMenuItem("删除");
					menu.add(item1);
					menu.add(new JSeparator(SwingConstants.HORIZONTAL));
					menu.add(item3);
					menu.show(videoList, e.getX(), e.getY());
					item1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (listModel.getSize() != 0) {
								String list = (String) listModel.get(videoList.getSelectedIndex());
								new ShowCapture(lpCapUtils.URL + list.trim());
							}
						}
					});
					item3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (listModel.getSize() != 0) {
								int isDelete = JOptionPane.showConfirmDialog(frame, "是否删除录制的视频？", "删除",
										JOptionPane.YES_NO_OPTION);
								if (isDelete == 0) {
									String list = (String) listModel.get(videoList.getSelectedIndex());
									File file = new File(lpCapUtils.URL + list.trim());
									File[] files = file.listFiles();
									for (int i = 0; i < files.length; i++) {
										files[i].delete();
									}
									file.delete();
									listModel.remove(videoList.getSelectedIndex());
								}

							}
						}
					});
				}
			}

		});
		scrollPane = new JScrollPane(videoList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		panelRight.add(scrollPane);

		// +++++++++++++++++++++++++++++++++++++++

		panel = new JPanel(new BorderLayout());
		frame = new JFrame("QNM录制");
		frame.setLayout(new BorderLayout());

		Font font = new Font("", Font.ITALIC, 32);
		timeLabel = new JLabel();
		timeLabel.setFont(font);
		Font font2 = new Font("", Font.LAYOUT_RIGHT_TO_LEFT, 32);
		titleLabel = new JLabel(" 距离本月发饷：");
		titleLabel.setFont(font2);
		Font font3 = new Font("", Font.PLAIN, 32);
		currentTime = new JLabel("", JLabel.RIGHT);
		currentTime.setFont(font3);

		bar = new JMenuBar();
		menu = new JMenu("文件");
		menu2 = new JMenu("录制");
		menu3 = new JMenu("播放");
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu3);

		JMenuItem item1 = new JMenuItem("打开目录");
		JMenuItem item2 = new JMenuItem("开始录制");
		JMenuItem item3 = new JMenuItem("停止录制");
		JMenuItem item4 = new JMenuItem("播放录制");
		menu.add(item1);
		menu2.add(item2);
		menu3.add(item4);
		menu2.add(new JSeparator(SwingConstants.HORIZONTAL));
		menu2.add(item3);

		stateBar = new JToolBar();
		stateBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		stateBar.setEnabled(false);
		stateBar.add(new JLabel(" 欢迎使用QNM系统"));
		frame.getContentPane().add(stateBar, BorderLayout.SOUTH);
		frame.getContentPane().add(bar, BorderLayout.NORTH);
		panel.add(currentTime, BorderLayout.SOUTH);
		panel.add(titleLabel, BorderLayout.NORTH);
		panel.add(timeLabel, BorderLayout.CENTER);
		// panel.add(currentTime);
		// panel.add(titleLabel);
		// panel.add(timeLabel);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(panelRight, BorderLayout.WEST);
		frame.setBounds(lpFrameUtils.getSysLocation(WIDTH, HEIGHT));
		// frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		// frame.setResizable(false);
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 3) {
					JOptionPane.showMessageDialog(frame, "QNM系列");
				}
			}

		});

		item2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				it.startCapture();
			}
		});
		item3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				it.stopCapture();
			}

		});
		item1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String cmd = "cmd /c start " + lpCapUtils.URL;
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
		});

		item4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (listModel.getSize() != 0) {
					String list = (String) listModel.get(videoList.getSelectedIndex());
					new ShowCapture(lpCapUtils.URL + list.trim());
				} else {
					JOptionPane.showMessageDialog(frame, "未选中任何文件");
				}

			}

		});

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Calendar cal = Calendar.getInstance();

				Calendar cal2 = Calendar.getInstance();
				if (cal.get(Calendar.DATE) >= 15) {
					cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 15, 0, 0, 0);
				} else {
					cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 15, 0, 0, 0);
				}
				long saveTime = cal2.getTimeInMillis() - cal.getTimeInMillis();
				long second = saveTime / 1000;
				long minute = second / 60;
				long hour = minute / 60;

				long day = hour / 24;

				long saveHour = (hour % 24);
				long saveMinute = minute % 60;
				long saveSecond = second % 60;

				String saveStr = "  " + day + "天" + saveHour + "小时" + saveMinute + "分钟" + saveSecond + "秒";

				cal.setTimeInMillis(System.currentTimeMillis());
				String time = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.DATE) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)
						+ ":" + cal.get(Calendar.SECOND);
				timeLabel.setText(saveStr);
				currentTime.setText(time + "  ");

			}
		};
		timer.schedule(task, 0, 500);
	}

	/**
	 * 内部任务，用来展开视频录制的开始与停止的。
	 * 
	 * @author Administrator
	 *
	 */
	class InnerTask {
		Timer captureDeskTopTimer = null;
		TimerTask captureDeskTopTask = null;
		File folderFile = null;

		public void startCapture() {
			if (!isRecord) {
				folderFile = new File(lpCapUtils.URL + System.currentTimeMillis());
				System.out.println(folderFile.getPath());
				if (!folderFile.isDirectory()) {
					folderFile.mkdir();
				}
				System.out.println("正在录制");
				captureDeskTopTimer = new Timer();
				captureDeskTopTask = new TimerTask() {
					@Override
					public void run() {
						try {
							Robot ro = new Robot();
							BufferedImage bdi = ro.createScreenCapture(new Rectangle((int) lpFrameUtils.getScreenWidth(),
									(int) lpFrameUtils.getScreenHeight()));
							ImageIO.write(bdi, "jpg",
									new File(folderFile.getPath() + "\\" + System.currentTimeMillis() + ".jpg"));
						} catch (AWTException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				captureDeskTopTimer.schedule(captureDeskTopTask, 0, 40);
				isRecord = true;
			}
		}

		public void stopCapture() {
			if (isRecord) {
				captureDeskTopTimer.cancel();
				isRecord = false;
				String newName = JOptionPane.showInputDialog(frame, "是否重命名录制的视频，为空则默认", "重命名",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if ("".equals(newName) || newName == null || "null".equals(newName)) {
					listModel.addElement(" " + folderFile.getName());
					videoList.setSelectedValue(" " + folderFile.getName(), true);
				} else {
					File newFile = new File(lpCapUtils.URL + newName);
					folderFile.renameTo(newFile);
					listModel.addElement(" " + newName);
					videoList.setSelectedValue(" " + newName, true);
				}
			}
		}

	}

	public static void main(String[] args) {
		lpUI.setWindowStyle();
		new StartCapture();
	}
}
