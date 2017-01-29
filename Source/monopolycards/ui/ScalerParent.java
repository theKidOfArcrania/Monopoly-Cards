package monopolycards.ui;

import java.util.Objects;

import javafx.beans.property.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;

public class ScalerParent extends Region
{

	private final ObjectProperty<Node> scaled = new SimpleObjectProperty<>(this, "scaled");

	private final BooleanProperty preserveRatio = new SimpleBooleanProperty(this, "preserveRatio");
	private final Scale sizer = new Scale();

	private final DoubleProperty scalingX = new SimpleDoubleProperty(this, "scalingX", 0);
	private final DoubleProperty scalingY = new SimpleDoubleProperty(this, "scalingY", 0);

	private final ObjectProperty<VPos> vAlign = new ObjectPropertyBase<VPos>()
	{

		@Override
		public Object getBean()
		{
			return ScalerParent.this;
		}

		@Override
		public String getName()
		{
			return "vAlign";
		}

		@Override
		public void set(VPos pos)
		{
			super.set(Objects.requireNonNull(pos, "Position must be non-null."));
			requestLayout();
		}
	};

	private final ObjectProperty<HPos> hAlign = new ObjectPropertyBase<HPos>()
	{

		@Override
		public Object getBean()
		{
			return ScalerParent.this;
		}

		@Override
		public String getName()
		{
			return "hAlign";
		}

		@Override
		public void set(HPos pos)
		{
			super.set(Objects.requireNonNull(pos, "Position must be non-null."));
			requestLayout();
		}
	};

	public ScalerParent(Node scaled)
	{
		this(scaled, HPos.CENTER, VPos.CENTER);
	}

	public ScalerParent(Node scaled, HPos hAlign, VPos vAlign)
	{
		this.scaled.addListener((val, before, after) ->
		{
			if (before != null)
				before.getTransforms().remove(sizer);
			if (after == null)
				throw new NullPointerException();
			after.getTransforms().add(sizer);
			if (getChildren().isEmpty())
				getChildren().add(scaled);
			else
				getChildren().set(0, scaled);
		});
		scalingY.addListener(val -> requestLayout());
		scalingX.addListener(val -> requestLayout());

		this.scaled.set(scaled);
		this.hAlign.set(hAlign);
		this.vAlign.set(vAlign);
	}

	public DoubleProperty scalingYProperty()
	{
		return scalingY;
	}

	public DoubleProperty scalingXProperty()
	{
		return scalingX;
	}

	public double getScalingY()
	{
		return scalingY.get();
	}

	public double getScalingX()
	{
		return scalingX.get();
	}

	public final HPos getHAlign()
	{
		return this.hAlignProperty().get();
	}

	public Node getScaled()
	{
		return scaled.get();
	}

	public final VPos getVAlign()
	{
		return this.vAlignProperty().get();
	}

	public final ObjectProperty<HPos> hAlignProperty()
	{
		return this.hAlign;
	}

	public boolean isPreserveRatio()
	{
		return preserveRatio.get();
	}

	public BooleanProperty preserveRatioProperty()
	{
		return preserveRatio;
	}

	public ObjectProperty<Node> scaledProperty()
	{
		return scaled;
	}

	public void setChild(Node val)
	{
		scaled.set(val);
	}

	public void setScalingY(double val)
	{
		scalingY.set(val);
	}

	public void setScalingX(double val)
	{
		scalingX.set(val);
	}

	public final void setHAlign(final HPos hAlign)
	{
		this.hAlignProperty().set(hAlign);
	}

	public void setPreserveRatio(boolean val)
	{
		preserveRatio.set(val);
	}

	public void setScaled(Node scaled)
	{
		this.scaled.set(scaled);
	}

	public final void setVAlign(final VPos vAlign)
	{
		this.vAlignProperty().set(vAlign);
	}

	public final ObjectProperty<VPos> vAlignProperty()
	{
		return this.vAlign;
	}

	@Override
	protected double computeMinHeight(double width)
	{
		return computePrefHeight(width);
	}

	@Override
	protected double computeMinWidth(double height)
	{
		return computePrefWidth(height);
	}

	@Override
	protected double computePrefHeight(double width)
	{
		return rescale(true).getHeight();
	}

	@Override
	protected double computePrefWidth(double height)
	{
		return rescale(true).getWidth();
	}

	@Override
	protected void layoutChildren()
	{
		rescale(false);
	}

	private Dimension2D rescale(boolean dryrun)
	{
		Orientation bias = scaled.get().getContentBias();
		double pfWidth;
		double pfHeight;
		if (bias == Orientation.HORIZONTAL)
		{
			pfWidth = scaled.get().prefWidth(-1);
			pfHeight = scaled.get().prefHeight(pfWidth);
		}
		else if (bias == Orientation.VERTICAL)
		{
			pfHeight = scaled.get().prefHeight(-1);
			pfWidth = scaled.get().prefWidth(pfHeight);
		}
		else
		{
			pfWidth = scaled.get().prefWidth(-1);
			pfHeight = scaled.get().prefHeight(-1);
		}

		boolean wFit = scalingX.get() != 0;
		boolean hFit = scalingY.get() != 0;
		double wScale = scalingX.get();
		double hScale = scalingY.get();

		if (!dryrun)
			Region.layoutInArea(scaled.get(), 0, 0, pfWidth, pfHeight, 0, null, true, true, HPos.CENTER, VPos.CENTER, false);

		if (wFit && hFit && preserveRatio.get()) // Both fit width and height are given
		{
			// If preserveRatio, we have to make both scales equal, so scale it by minimum num
			if (wScale > hScale)
				wScale = hScale;
			else
				hScale = wScale;
		}
		else if ((wFit ^ hFit) && preserveRatio.get())
			if (wFit)
				hScale = wScale;
			else
				wScale = hScale;
		
		// Add a tolerance.
		double cWidth = wScale * pfWidth;
		double cHeight = hScale * pfHeight;
		double width = getWidth();
		double height = getHeight();

		if (!dryrun)
		{
			sizer.setX(wScale);
			sizer.setY(hScale);

			double x = width - cWidth;
			double y = height - cHeight;

			switch (hAlign.get())
			{
			case LEFT:
				x = 0;
				break;
			case CENTER:
				x /= 2;
				break;
			}

			switch (vAlign.get())
			{
			case TOP:
				y = 0;
				break;
			case CENTER:
				y /= 2;
				break;
			}
			Region.layoutInArea(scaled.get(), x, y, pfWidth, pfHeight, 0, null, true, true, HPos.CENTER, VPos.CENTER, false);
		}

		return new Dimension2D(cWidth, cHeight);
	}

}
