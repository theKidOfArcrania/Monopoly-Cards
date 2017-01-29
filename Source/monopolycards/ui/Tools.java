package monopolycards.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Tools
{
	static
	{
		// Install fonts
		loadFont("monopolycards/res/KabaleMedium-Normal.ttf", 0);
	}

//	public static final Image DARK_METAL_IMAGE = createFXImage("maze/res/DarkMetal.jpg");
//	public static final BackgroundImage DARK_METAL_FILL = new BackgroundImage(DARK_METAL_IMAGE, null, null, null,
//			new BackgroundSize(1, 1, true, true, false, true));
	public static final double CORNER_RADIUS = 20.0;
	public static final InnerShadow BEVEL_SHADOW = new InnerShadow(10.0, Color.BLACK);
	public static final InnerShadow BEVEL_HIGHLIGHT = new InnerShadow(10.0, Color.GRAY);

	@SuppressWarnings("unused")
	public static Clip createAudioClip(String soundFile)
	{
		Clip sound;
		return null;
		// try {
		// sound = AudioSystem.getClip();
		// sound.open(AudioSystem.getAudioInputStream(ClassLoader.getSystemResource(soundFile)));
		//
		// } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
		// e.printStackTrace();
		// return null;
		// }
	}

	public static BufferedImage createAwtImage(String res)
	{
		try
		{
			return ImageIO.read(ClassLoader.getSystemResourceAsStream("monopolycards/res/" + res));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Image createFXImage(String res)
	{
		return new Image(ClassLoader.getSystemResourceAsStream("monopolycards/res/" + res));
	}

	public static ImageView createImageView(Image img, double x, double y, double h, double w)
	{
		ImageView sv = new ImageView();
		sv.setImage(img);
		sv.setFitHeight(h);
		sv.setFitWidth(w);
		sv.setPreserveRatio(true);
		sv.setSmooth(true);
		sv.setCache(true);
		sv.setTranslateX(x);
		sv.setTranslateY(y);

		return sv;
	}

	/**
	 * Sets up the borderings and backgrounds for the provided Pane object
	 *
	 * @param frame the Pane to fit with the
	 */
//	public static void frameify(Pane frame)
//	{
//		frame.setEffect(BEVEL_SHADOW);
//		frame.setBackground(new Background(DARK_METAL_FILL));
//
//		roundCorners(frame);
//	}

	public static Font loadFont(String fontFile, double size)
	{
		try (InputStream is = ClassLoader.getSystemResourceAsStream(fontFile))
		{
			return Font.loadFont(is, size);
		}
		catch (IOException e)
		{
			return Font.font(null);
		}

	}

	@SuppressWarnings("unused")
	public static void playClip(Clip sound, boolean loop)
	{
		// TO DO: add sound volume controlling
		// Platform.runLater(() -> {
		// sound.stop();
		// sound.setFramePosition(0);
		// if (loop) {
		// sound.loop(Clip.LOOP_CONTINUOUSLY);
		// } else {
		// sound.start();
		// }
		// });
	}

	public static void roundCorners(Node element)
	{
		roundCorners(element, CORNER_RADIUS);
	}

	public static void roundCorners(Node element, double cornerRadius)
	{
		Rectangle clip = new Rectangle();
		element.layoutBoundsProperty().addListener(val ->
		{
			Bounds size = element.getLayoutBounds();
			clip.setWidth(size.getWidth());
			clip.setHeight(size.getHeight());
		});
		clip.setArcHeight(cornerRadius);
		clip.setArcWidth(cornerRadius);
		element.setClip(clip);
	}

}
