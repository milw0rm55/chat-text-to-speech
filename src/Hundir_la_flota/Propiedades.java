package Hundir_la_flota;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propiedades {
	
	public static Properties getProp() throws IOException {
		Properties pr = new Properties();
		InputStream in = new FileInputStream("config.ini");
		pr.load(in);
		return pr;
	}
	
}
