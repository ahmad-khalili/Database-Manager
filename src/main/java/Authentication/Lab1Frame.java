package Authentication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;


public class Lab1Frame extends JFrame {
	public static String userType;
	private static final int FRAME_WIDTH = 250;
	private static final int FRAME_HEIGHT = 200;
	private File userData;
	static JTextField userNameField;
	static JTextField passwordField;

	public Lab1Frame() throws FileNotFoundException
	{
		createComponents();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	private void createComponents() throws FileNotFoundException
	{
		userNameField = new JTextField(10);
		passwordField = new JTextField(10);

		JLabel userNameLabel = new JLabel("User Name");
		JLabel passwordLabel = new JLabel("Password");

		JButton loginButton = new JButton("Login");
		JButton exitButton = new JButton("Exit");

		JPanel panel = new JPanel();
		panel.add(userNameField);
		panel.add(userNameLabel);
		panel.add(passwordField);
		panel.add(passwordLabel);
		panel.add(loginButton);
		panel.add(exitButton);
		add(panel);

		ActionListener exitListener = new ClickListener1();
		ActionListener loginListener = new ClickListener2();
		exitButton.addActionListener(exitListener);
		loginButton.addActionListener(loginListener);
	}
	public class ClickListener1 implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	public static class ClickListener2 implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			File inputFile = new File("USERDATA.txt");

			String userNameInput = userNameField.getText();

			String passwordInput = passwordField.getText();

			try {
				Scanner in = new Scanner(new File("USERDATA.txt"));
				while (in.hasNextLine())
				{
					String s = in.nextLine();
					String[] sArray = s.split(",");

					System.out.println(sArray[0]); //Just to verify that file is being read
					System.out.println(sArray[1]);

					userType = sArray[2];



					if (userNameInput.equals(sArray[0]) && passwordInput.equals(sArray[1]))
					{
						JOptionPane.showMessageDialog(null,
								"Login Successful", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"Invalid Username / Password Combo", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				in.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"User Database Not Found", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}
