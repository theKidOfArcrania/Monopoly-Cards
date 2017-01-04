package monopolycards;

import static java.lang.ClassLoader.getSystemResource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ResourceDefaults extends Properties {
	private static final long serialVersionUID = 761446494739060039L;
	private static final ResourceDefaults defs;
	private static final Logger LOG = Logger.getLogger(ResourceDefaults.class.getName());

	static {
		defs = new ResourceDefaults();
		try {
			defs.load(ClassLoader.getSystemResourceAsStream("res.properties"));
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unable to load resource 'res.properties'. Crashing now.", e);
			System.exit(1);
		}
	}

	public static ResourceDefaults getDefaults() {
		return defs;
	}

	private boolean loadedScale = false;
	private int scale;
	
	
	
	public <T extends Enum<T>> T getEnumProperty(String name, Class<T> enumClass, T defaultValue) {
		if (!enumClass.isEnum()) {
			// Shouldn't happen because of typesafe check.
			throw new InternalError("Class must be a enum");
		}
		int index = getIntProperty(name, -1);

		T[] constants = enumClass.getEnumConstants();
		if (index < 0 || index >= constants.length) {
			return defaultValue;
		}

		return constants[index];
	}

	public BufferedImage getImageProperty(String imageProp) throws IOException {
		String imageFile = getProperty(imageProp, null);
		if (imageFile == null) {
			return null;
		}
		return ImageIO.read(getSystemResource(imageFile));
	}

	public int getIntProperty(String name, int defaultValue) {
		String num = getProperty(name, Integer.toString(defaultValue));
		try
		{
			if (num.startsWith("$")) {
				return Integer.parseInt(num.substring(1)) * scale;
			} else {
				return Integer.parseInt(num);
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return defaultValue;
		}
	}

	public int getScale() {
		if (!loadedScale)
		{
			scale = getIntProperty("scale", 1);
			loadedScale = true;
		}
		return scale;
	}
}
