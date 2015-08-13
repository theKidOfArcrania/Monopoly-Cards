package monopolycards;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import static java.lang.ClassLoader.getSystemResource;

public class ResourceDefaults extends Properties {
	private static final long serialVersionUID = 761446494739060039L;
	private static final ResourceDefaults defs;
	private static final Logger LOG = Logger.getLogger(ResourceDefaults.class.getName());

	static {
		defs = new ResourceDefaults();
		try {
			defs.load(ClassLoader.getSystemResourceAsStream("res.properties"));
			defs.scale = defs.getIntProperty("scale", 1);

		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unable to load resource 'res.properties'. Crashing now.", e);
			System.exit(1);
		}
	}

	public static ResourceDefaults getDefaults() {
		return defs;
	}

	private int scale = 1;

	public BufferedImage getImageProperty(String imageProp) throws IOException {
		String imageFile = getProperty(imageProp, null);
		if (imageFile == null) {
			return null;
		}
		return ImageIO.read(getSystemResource(imageFile));
	}

	public int getIntProperty(String name, int defaultValue) {
		String num = getProperty(name, Integer.toString(defaultValue));
		if (num.startsWith("$")) {
			return Integer.parseInt(num.substring(1)) * scale;
		} else {
			return Integer.parseInt(num);
		}

	}

	public int getScale() {
		return scale;
	}
}
