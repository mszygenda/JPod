/**
 * 
 */
package jpod;
import line6.*;

import line6.commands.*;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import jpod.gui.*;

/**
 * Main class of application
 * @author Mateusz Szygenda
 *
 */
public class JPod implements Runnable {
	public static ArrayList<Device> devices;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("JPod application 0.1");
		devices = DeviceManager.getAllDevices();
		EventQueue.invokeLater(new JPod());
		
		System.out.println("bye!");
	}
	
	/**
	 * Creates new main window and sets native look and feel
	 */
	public void run()
	{
		try
		{ 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			System.out.println("Can not set native look and feel");
		}
		MainWindow window = new MainWindow();
		window.setVisible(true);
	}

}
