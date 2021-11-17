package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import it.unibo.oop.lab.mvcio.Controller;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    private final JFrame frame = new JFrame("Interface");
    public SimpleGUIWithFileChooser(final Controller ctrl) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel jp = new JPanel(new BorderLayout());
        final JTextArea ta = new JTextArea();
        final JButton but = new JButton("SAVE");
        jp.add(but, BorderLayout.SOUTH);
        jp.add(ta, BorderLayout.CENTER);
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                try {
                    ctrl.save(ta.getText());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }); 
        final JPanel j2 = new JPanel();
        final JTextField jt = new JTextField(ctrl.getPath());
        final JButton jb = new JButton("Browse...");
        jt.setEditable(false);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub 
                final JFileChooser chos = new JFileChooser();
                chos.setSelectedFile(ctrl.getFile());
                final int result = chos.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    ctrl.setFile(chos.getSelectedFile());
                    jt.setText(ctrl.getPath());
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("");
                } else {
                    JOptionPane.showMessageDialog(frame, result, "NOPE!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        jp.add(j2, BorderLayout.NORTH);
        j2.add(jt, BorderLayout.CENTER);
        j2.add(jb, BorderLayout.AFTER_LINE_ENDS);
        frame.setContentPane(jp);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        //frame.pack();
        frame.setLocationByPlatform(true);
    }
    private void display() {
        frame.setVisible(true);
    }
    public static void main(final String... args) {
        final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser(new Controller()); 
        gui.display();
     }
    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

}
