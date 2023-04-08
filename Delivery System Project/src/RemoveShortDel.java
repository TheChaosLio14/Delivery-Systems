import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
public class RemoveShortDel extends JFrame {
	private JPanel contentPane;
	public RemoveShortDel(Managers manager) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 200, 558, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel = new JLabel("Remove Short Delivery");
		lblNewLabel.setFont(new Font("Kefa", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(16, 24, 178, 28);
		contentPane.add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("Choose Delivery code:");
		lblNewLabel_1.setBounds(6, 115, 151, 23);
		contentPane.add(lblNewLabel_1);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(153, 114, 103, 27);
		contentPane.add(comboBox);
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		for (HashMap.Entry<String,Members> 	members : manager.getMembers().entrySet())
			comboBox_1.addItem((String) members.getKey());
		comboBox_1.setSelectedItem(null);
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.removeAllItems();
				Members member=manager.getMembers().get(String.valueOf(comboBox_1.getSelectedItem()));
				ArrayList<Delivery> deliveries= member.getDeliveries();
				for(Delivery del:deliveries)
				{
					if(del instanceof ShortDistanceDelivery)
						comboBox.addItem(del.getCode());	
				}
			}
		});
	
		comboBox_1.setBounds(153, 77, 103, 27);
		contentPane.add(comboBox_1);
		JButton btnNewButton = new JButton("Remove");
		/*by pressing on the member id that we want to delete short delivery from, i present the codes of all the short deliveries he has then choose the delivery that 
		 * you want to delete and delete from the database and the program.*/
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delivery shortdelivery=new ShortDistanceDelivery(String.valueOf(comboBox.getSelectedItem()));
				Members member=DataBase.members.get(String.valueOf(comboBox_1.getSelectedItem()));
				member.getDeliveries().remove(shortdelivery);
				ShortDistanceDelivery del=null;
				for (Entry<String, SubManager> entry : DataBase.allSubManagers.entrySet()) {
					if(entry.getValue().getShortDel().contains(shortdelivery))
					{
						
						entry.getValue().getShortDel().remove(shortdelivery);
						DataBase.shortDeleviries.remove(shortdelivery);
						DataBase.codesOfDels.remove(comboBox.getSelectedItem());
						File inputFile = new File("src/ShortDeliveries");
						File tempFile = new File("myTempFile");
						BufferedReader reader = null;
						try {
							reader = new BufferedReader(new FileReader(inputFile));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						BufferedWriter writer = null;
						try {
							writer = new BufferedWriter(new FileWriter(tempFile));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Scanner scanner = new Scanner(System.in);
						String currentLine;
						try {
							while((currentLine = reader.readLine()) != null) {
								if(currentLine.contains(String.valueOf(comboBox.getSelectedItem())))
									continue;
							    writer.write(currentLine+"\n");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						tempFile.renameTo(inputFile);
						JOptionPane.showMessageDialog(null,"Successful.");
						setVisible(false);
						new ManagerMenu(manager).setVisible(true);
						}
					
					}
				}
			});
		JLabel lblNewLabel_13 = new JLabel("Return to the menu-->");
		lblNewLabel_13.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_13.setBounds(21, 385, 170, 16);
		contentPane.add(lblNewLabel_13);
		
		JButton btnNewButton_1 = new JButton("Menu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ManagerMenu(manager).setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 14));
		btnNewButton_1.setBounds(183, 380, 94, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton.setBounds(30, 150, 103, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Choose member:");
		lblNewLabel_2.setBounds(6, 81, 108, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(RemoveShortDel.class.getResource("/Images/Screenshot 2021-12-20 at 22.33.41 (1).png")));
		lblNewLabel_3.setBounds(0, 0, 558, 427);
		contentPane.add(lblNewLabel_3);
		
		
	}

}