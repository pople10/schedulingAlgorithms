import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import algorithms.InterfaceAlgorithm;
import exceptions.InvalidDataException;
import factory.MethodFactoryBuilder;
import utility.DataType;
import utility.Helper;
import utility.Methods;
import utility.Statistic;
import utility.Validator;


public class Display extends JFrame {
	private static final long serialVersionUID = 1L;
	public static HashMap<Methods,String> methodLabels = new HashMap<Methods,String>();
	public static HashMap<String,Methods> methodLabelsRev = new HashMap<String,Methods>();
	public Methods selectedMethod = null;
	List<DataType> input = null;
	
	public void showErrorMessage(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void showSuccessMessage(String msg)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/done.png"));
		} catch (IOException e) {
			this.showErrorMessage("Loading image is getting an error");
		}
		Image image = img.getScaledInstance((int)30, (int)30, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);
		JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE, 
				icon);
	}
	
	public void showWarningMessage(String msg)
	{
		JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	public void showSplashScreen()
	{
		JWindow window = new JWindow();
		window.getContentPane().add(
		    new JLabel(new ImageIcon("images/splashscreen.jpg")), SwingConstants.CENTER);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setSize(600,600);
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x,y);
		window.setVisible(true);
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    this.showErrorMessage("Loading image is getting an error");
		}
		window.setVisible(false);
		this.getOptionGUI();
		window.dispose();
	}
	
	public void getOptionGUI()
	{
		JFrame window = new JFrame("Beginning");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File  
				(System.getProperty("user.home")+"\\Desktop"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		JButton choosing = new JButton("Select a csv file");
		JLabel chosen = new JLabel();
		chosen.setHorizontalAlignment(SwingConstants.CENTER);
		chosen.setText("No file chosen");
		JCheckBox header = new JCheckBox("Is there any header?",true);
		window.setIconImage((new ImageIcon("images/logo.png")).getImage());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3,3,3,3);
        JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("First Come First Served");
		comboBox.addItem("Round Robin");
		comboBox.addItem("SJF without preemption");
		comboBox.addItem("SJF with preemption");
		comboBox.addItem("Shortest Remaining Task");
		JLabel infoMethod = new JLabel("Choose your method");
		JLabel infoNumber = new JLabel("Choose a number for quantum");
		JButton button = new JButton("Select");
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/tap.png"));
		} catch (IOException e) {
			this.showErrorMessage("Loading image is getting an error");
		}
		Image image = img.getScaledInstance((int)20, (int)20, Image.SCALE_SMOOTH);
		ImageIcon finalIcon = new ImageIcon(image);
		button.setIcon(finalIcon);
		JTextField number = new JTextField();
		number.setText(1+"");
		number.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					button.doClick();
			}
        });
		choosing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int returnVal = fileChooser.showOpenDialog(Display.this);
   
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
						input = Helper.readDataFromFile(file,header.isSelected());
						chosen.setText(file.getName());
					} catch (IOException e1) {
						showErrorMessage("Something went wrong with the file");
						chosen.setText("No file chosen");
						input=null;
					} catch (InvalidDataException e1) {
						showErrorMessage(e1.toString());
						chosen.setText("No file chosen");
						input=null;
					}
                } else {
                		showWarningMessage("You have to select a file");
                		chosen.setText("No file chosen");
                		input=null;
                }
            }
		});
		number.setVisible(false);
		infoNumber.setVisible(false);
		comboBox.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent arg0) {
		    	String methodLabelSelectedTmp = (String) comboBox.getSelectedItem();
            	Methods method = Display.methodLabelsRev.get(methodLabelSelectedTmp);
            	if(method!=Methods.RR)
            	{
            		number.setVisible(false);
            		infoNumber.setVisible(false);
            		window.pack();
            	}
            	else
            	{
            		number.setVisible(true);
            		infoNumber.setVisible(true);
            		window.pack();
            	}
		    }
		});
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(input==null)
            	{
            		showWarningMessage("You have to select a valid data first");
            		return;
            	}
            	String methodLabelSelectedTmp = (String) comboBox.getSelectedItem();
            	Methods method = Display.methodLabelsRev.get(methodLabelSelectedTmp);
        		int qtm=0;
        		if(method==Methods.RR)
        		{
	            	String num = number.getText();
	            	if(!Validator.isInteger(num))
	            	{
	            		showErrorMessage("Invalid number");
	            		return;
	            	}
            		qtm=Integer.parseInt(num);
        		}
            	if(method==null)
            	{
            		showErrorMessage("Invalid method");
            		return;
            	}
        		//window.setVisible(false);
        		JWindow loader = new JWindow();
        		loader.getContentPane().add(
        			    new JLabel(new ImageIcon("images/wait.png")), SwingConstants.CENTER);
        		loader.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        		loader.pack();
        	    int x = (int) ((dimension.getWidth() - loader.getWidth()) / 2);
        	    int y = (int) ((dimension.getHeight() - loader.getHeight()) / 2);
        	    loader.setLocation(x,y);
        		loader.setVisible(true);
        		selectedMethod=method;
        		InterfaceAlgorithm methodSolving = MethodFactoryBuilder.build(selectedMethod,qtm);
        		methodSolving.setData(input);
        		methodSolving.solve();
        		loader.setVisible(false);
        		Map<String, Integer[]> result = methodSolving.getSolution();
        		List<Statistic> stat = Helper.getStatisticsFromResult(result, input);
        		if(methodSolving.isSolutionFound())
        		{
        			show(result,methodLabelSelectedTmp,methodSolving.getMax());
        			showStatistic(stat);
        			if(methodSolving.isSolutionFound())
            			showSuccessMessage("La solution a été trouvé\nTrouvé dans "+
            		Helper.secondsToString(((float)methodSolving.getDuration()/1000)));
            		else
            			showWarningMessage("La solution n'a pas été trouvé\nTimed out");
        		}
        		else
        			showErrorMessage("Solution cannot be found");
        	
            }
        });
		button.setVerticalTextPosition(JButton.CENTER);
		comboBox.setBackground(Color.white);
		button.setBackground(Color.decode("#337ab7"));
		button.setFocusable(false);
		window.getContentPane().add(infoMethod,gbc);
		window.getContentPane().add(comboBox, gbc);
		window.getContentPane().add(infoNumber, gbc);
		window.getContentPane().add(number, gbc);
		window.getContentPane().add(choosing, gbc);
		window.getContentPane().add(chosen, gbc);
		window.getContentPane().add(header, gbc);
		window.getContentPane().add(button, gbc);
		window.pack();
		window.setLocationRelativeTo(null);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
	    window.setLocation(x,y);
	    window.setResizable(false);
		window.setVisible(true);
	}
	
	public void begin()
	{
		this.showSplashScreen();
	}
	
	public void emptyComponants()
	{
		this.removeAll();
	}
	
	public void showStatistic(List<Statistic> list)
	{
		int n = list.size();
		JFrame sFrame = new JFrame("Statistics");
		sFrame.setLayout(new GridLayout(n+2,4));
		JLabel empty = new JLabel();
		sFrame.add(empty);
		JLabel rotationLabel = new JLabel("Rotation",SwingConstants.CENTER);
		sFrame.add(rotationLabel);
		JLabel waitingLabel = new JLabel("Waiting Time",SwingConstants.CENTER);
		sFrame.add(waitingLabel);
		JLabel rendementLabel = new JLabel("Rendement",SwingConstants.CENTER);
		sFrame.add(rendementLabel);
		int cmp=0;
		float sumR=0;
		float sumW=0;
		float sumRe=0;
		for(Statistic i : list)
		{
			JLabel tmp = new JLabel(i.getName(),SwingConstants.CENTER);
			JLabel tmp1 = new JLabel(""+i.getRotation(),SwingConstants.CENTER);
			JLabel tmp2 = new JLabel(""+i.getWaiting(),SwingConstants.CENTER);
			JLabel tmp3 = new JLabel(""+i.getRendement(),SwingConstants.CENTER);
			sFrame.add(tmp);
			sFrame.add(tmp1);
			sFrame.add(tmp2);
			sFrame.add(tmp3);
			sumR+=i.getRotation();
			sumW+=i.getWaiting();
			sumRe+=i.getRendement();
			cmp++;
		}

		JLabel tmp = new JLabel("Average",SwingConstants.CENTER);
		JLabel tmp1 = new JLabel(""+((float)(sumR/cmp)),SwingConstants.CENTER);
		JLabel tmp2 = new JLabel(""+((float)(sumW/cmp)),SwingConstants.CENTER);
		JLabel tmp3 = new JLabel(""+((float)(sumRe/cmp)),SwingConstants.CENTER);
		sFrame.add(tmp);
		sFrame.add(tmp1);
		sFrame.add(tmp2);
		sFrame.add(tmp3);
		sFrame.pack();
		sFrame.setIconImage((new ImageIcon("images/logo.png")).getImage());
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - sFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - sFrame.getHeight()) / 2);
	    sFrame.setLocation(x, y);
		sFrame.setVisible(true);
	}
	
	public void show(Map<String, Integer[]> data,String method,int n)
	{
		this.getContentPane().removeAll();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize((int) dimension.getWidth(),600);
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x,y);
		this.setLayout( new GridLayout(data.size()+1,n+2));
		for(Map.Entry<String, Integer[]> entry : data.entrySet())
		{
			String labelName = entry.getKey();
			JLabel name = new JLabel(labelName,SwingConstants.CENTER);
			this.add(name);
			int started = Helper.getStartFromName(input, labelName);
			for(int j = 0;j<n;j++)
			{
				JButton btn = new JButton();
				btn.setEnabled(false);
				btn.setFocusable(false);
				if(Helper.valueExist(entry.getValue(), j+1))
				{
					btn.setBackground(Color.GRAY);
				}
				else
				{
					btn.setBackground(Color.WHITE);
				}
				if(started==j)
				{
		            btn.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				this.add(btn);
			}
			JLabel empty = new JLabel();
			this.add(empty);
		}
		for(Integer i=0;i<=n;i++)
		{
			JLabel names = new JLabel(i.toString(),SwingConstants.RIGHT);
			this.add(names);
		}
		JLabel empty2 = new JLabel();
		this.add(empty2);
		this.setIconImage((new ImageIcon("images/logo.png")).getImage());
		this.setTitle("Scheduling problem - "+method);
		this.setVisible(true);
	}
	
	public Display()
	{
		Display.methodLabels.put(Methods.FIFO, "First Come First Served");
		Display.methodLabels.put(Methods.RR, "Round Robin");
		Display.methodLabels.put(Methods.SJF_NOP, "SJF without preemption");
		Display.methodLabels.put(Methods.SJF_P, "SJF with preemption");
		Display.methodLabels.put(Methods.SRT, "Shortest Remaining Task");
		Display.methodLabelsRev.put("First Come First Served",Methods.FIFO);
		Display.methodLabelsRev.put("Round Robin",Methods.RR);
		Display.methodLabelsRev.put("SJF without preemption",Methods.SJF_NOP);
		Display.methodLabelsRev.put("SJF with preemption",Methods.SJF_P);
		Display.methodLabelsRev.put("Shortest Remaining Task",Methods.SRT);
	}
	
	public static void main(String[] f)
	{
		Display instance = new Display();
		instance.begin();
	}


}
