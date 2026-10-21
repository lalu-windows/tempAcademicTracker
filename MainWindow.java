import javax.swing.*;
import java.awt.*;

public class MainWindow {

	// the row builder
	public static JPanel subrow(String subject, int attended, int total){
		int percentage = (int)(((double) attended / total) * 100);

		JPanel subPanel = new JPanel(new BorderLayout(10,10));
		subPanel.setPreferredSize(new Dimension(600,25));
		subPanel.setMaximumSize(new Dimension(600,25));
		subPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel subjectName = new JLabel(subject);
		subjectName.setPreferredSize(new Dimension(130,25));
		subjectName.setFont(new Font("Arial", Font.BOLD, 15));

		JProgressBar bar = new JProgressBar(0,100);
		bar.setValue(percentage);
		bar.setStringPainted(true);
		bar.setString(percentage + "%");

		JLabel ratio = new JLabel(attended + "/" + total);
		ratio.setPreferredSize(new Dimension(60,25));

		subPanel.add(subjectName, BorderLayout.WEST);
		subPanel.add(bar, BorderLayout.CENTER);
		subPanel.add(ratio, BorderLayout.EAST);

		return subPanel;
	}

	// ---------- Attendance page ----------
	static class AttenGUI extends JPanel {

		// data and the panel that holds the rows are fields so refresh() can reach them
		private final String[] subjects = {"TOC","OOP","MATHS","DSA","DE&LD","ETHICS"};
		private final int[] attended = {35,20,32,25,28,25};
		private final int[] tot = {40,38,40,35,34,35};
		private final JPanel subjectPanel = new JPanel();

		public AttenGUI(){
			setLayout(new BorderLayout(10,10));
			setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

			JLabel titlelabel = new JLabel("Attendance tracker", JLabel.CENTER);
			titlelabel.setFont(new Font("Inter", Font.BOLD, 25));
			add(titlelabel, BorderLayout.NORTH);

			subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.Y_AXIS));
			add(subjectPanel, BorderLayout.CENTER);

			add(buildUpdatePanel(), BorderLayout.SOUTH);

			refresh(); // first draw
		}

		// redraws subjectpanel
		public void refresh(){
			subjectPanel.removeAll();

			subjectPanel.add(Box.createVerticalGlue());
			for(int i = 0; i < subjects.length; i++){
				subjectPanel.add(subrow(subjects[i], attended[i], tot[i]));
				subjectPanel.add(Box.createRigidArea(new Dimension(0,8)));
			}
			subjectPanel.add(Box.createVerticalGlue());

		}

		// Update attendance section
		private JPanel buildUpdatePanel(){
			JPanel updatepan = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
			updatepan.setBorder(BorderFactory.createTitledBorder("Update attendance"));

			JComboBox<String> subjectdrop = new JComboBox<>(subjects);
			JTextField attenfield = new JTextField(4);
			JTextField totfield = new JTextField(4);
			JButton updatebtn = new JButton("Update");

			// autofill the text fields when a subject is selected
			subjectdrop.addActionListener(e -> {
				int index = subjectdrop.getSelectedIndex();
				attenfield.setText(String.valueOf(attended[index]));
				totfield.setText(String.valueOf(tot[index]));
			});
			subjectdrop.setSelectedIndex(0);

			updatebtn.addActionListener(e -> {
				int index = subjectdrop.getSelectedIndex();
				try{
					int atten = Integer.parseInt(attenfield.getText().trim());
					int newtot = Integer.parseInt(totfield.getText().trim());

					if(atten < 0 || newtot <= 0){
						JOptionPane.showMessageDialog(this, "Values must be positive", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(atten > newtot){
						JOptionPane.showMessageDialog(this, "Attended cannot be greater than total", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					attended[index] = atten;
					tot[index] = newtot;

					refresh(); // redraw with the new numbers
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			});

			updatepan.add(new JLabel("Subject: "));
			updatepan.add(subjectdrop);
			updatepan.add(new JLabel("Attended: "));
			updatepan.add(attenfield);
			updatepan.add(new JLabel("Total: "));
			updatepan.add(totfield);
			updatepan.add(updatebtn);

			return updatepan;
		}
	}

	// grade gui
	static class GradeGUI extends JPanel {
		public GradeGUI(){
			setLayout(new BorderLayout());
			add(new JLabel("Grades coming soon", JLabel.CENTER), BorderLayout.CENTER);
		}
	}

	// ---------- Main frame ----------
	static class MainFrame extends JFrame {
		public MainFrame(){
			setTitle("Academic Tracker");
			setSize(1280,720);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			CardLayout cardlay = new CardLayout();
			JPanel cardpan = new JPanel(cardlay);

			cardpan.add(new AttenGUI(), "ATTENDANCE");
			cardpan.add(new GradeGUI(), "GRADE");

			JPanel btnpan = new JPanel();
			JButton attenbtn = new JButton("Attendance");
			JButton gradebtn = new JButton("Grade");
			attenbtn.addActionListener(e -> cardlay.show(cardpan, "ATTENDANCE"));
			gradebtn.addActionListener(e -> cardlay.show(cardpan, "GRADE"));
			btnpan.add(attenbtn);
			btnpan.add(gradebtn);

			add(btnpan, BorderLayout.NORTH);
			add(cardpan, BorderLayout.CENTER);
		}
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
