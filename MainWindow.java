import javax.swing.*;
import java.awt.*;


public class MainWindow {
		public static JPanel subrow(String subject,int percentage){
			JPanel subPanel = new JPanel(new BorderLayout(10,10));
			subPanel.setPreferredSize(new Dimension(600,25));
			subPanel.setMaximumSize(new Dimension(600,25));
			subPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

			//name of the subject
			JLabel subjectName = new JLabel(subject);
			subjectName.setPreferredSize(new Dimension(130,25));
			subjectName.setFont(new Font("Arial",Font.BOLD,15));
			


			//progress bar for that subject:
			JProgressBar bar = new JProgressBar(0,100);
			bar.setValue(percentage);
			bar.setStringPainted(true);
			bar.setString(percentage + "%");
			bar.setPreferredSize(new Dimension(130,25));
			bar.setMaximumSize(new Dimension(130,25));



			//attendance int:
			JLabel ratio = new JLabel(String.valueOf(percentage));


			//adding name and bar to the subpanel
			subPanel.add(subjectName,BorderLayout.WEST);
			subPanel.add(bar,BorderLayout.CENTER);
			subPanel.add(ratio,BorderLayout.EAST);

			return subPanel;

		}

	public class AttenGUI extends JPanel{
	public AttenGUI(){

		// the main window we will be dealing with
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(10,10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		//title label
		JLabel titlelabel = new JLabel("Attendance tracker",JLabel.CENTER);
		titlelabel.setFont(new Font("Inter",Font.BOLD,25));
		mainPanel.add(titlelabel,BorderLayout.NORTH);
		//subject panel
		JPanel subjectPanel = new JPanel();
		subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));



//--- RAW DATA------------------------------------------
		String[] subjects = {"TOC","OOP","MATHS","DSA","DE&LD","ETHICS"};
		int[] percentages = {85,70,92,95,78,65};
// ---------------------------------------------------



			//loop to display percentages
		for(int i =0; i<subjects.length;i++){
			subjectPanel.add(subrow(subjects[i],percentages[i]));
			subjectPanel.add(Box.createRigidArea(new Dimension(0,8)));
		}



		//adding the subject panel to mainpanel:
		mainPanel.add(subjectPanel,BorderLayout.CENTER);
		add(mainPanel);

	}
}


public class GradeGUI extends JPanel{
	public GradeGUI(){

	}

	
}
public class MainFrame extends JFrame {

	public MainFrame(){
		setTitle("Academic Tracker");
		setSize(1280,720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
                //need an explanation on this cardlayout paired with jpanel
		CardLayout cardlay = new CardLayout();
		JPanel cardpan = new JPanel(cardlay);

		//creating attengui and gradgui instances:
		AttenGUI attenpage = new AttenGUI();
		GradeGUI gradepage = new GradeGUI();

		//adding pages to the card cardpan:
		cardpan.add(attenpage,"ATTENDANCE");
		cardpan.add(gradepage,"GRADE");


		//buttons for navigation:
		JPanel btnpan = new JPanel();
		JButton attenbtn = new JButton("Attendance");
		JButton gradebtn = new JButton("Grade");

		attenbtn.addActionListener(e-> {cardlay.show(cardpan,"ATTENDANCE");});
		gradebtn.addActionListener(e-> {cardlay.show(cardpan,"GRADE");});

		btnpan.add(attenbtn);
		btnpan.add(gradebtn);
		add(btnpan,BorderLayout.NORTH);
		add(cardpan,BorderLayout.CENTER);
		

}
}
//main function
public void main(String[] args){
	SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
	
     }

}
