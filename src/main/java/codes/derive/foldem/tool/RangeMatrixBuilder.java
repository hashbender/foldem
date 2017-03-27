package codes.derive.foldem.tool;

import static codes.derive.foldem.Foldem.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;

import codes.derive.foldem.Hand;
import codes.derive.foldem.range.Range;

/**
 * A type that can generate images containing each hand in a range and its
 * respective weight in a matrix.
 */
public class RangeMatrixBuilder {
	
	/* Size of the hand matrix */
	private static final int MATRIX_SIZE = 13;
	
	/* The size in pixels (excluding 1px border) of output images. */
	private static final int SIZE_PX = 390;

	/* Card labels ordered by strength. */
	private static final char[] LABELS = {
		'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
	};
	
	/**
	 * Builds a matrix image using the specified {@link Range}.
	 * @param range
	 * 		The range to use.
	 * @return
	 * 		The image generated.
	 */
	public BufferedImage build(Range range) {
		
		// create an image to draw our matrix on
		BufferedImage image = new BufferedImage(SIZE_PX + 1, SIZE_PX + 1,
				BufferedImage.TYPE_INT_ARGB);

		// obtain the Graphics2D context for our image
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// begin rendering our matrix
		int boxSize = SIZE_PX / MATRIX_SIZE;
		for (int x = 0; x < MATRIX_SIZE; x++) {
			int drawX = x * boxSize;
			for (int y = 0; y < MATRIX_SIZE; y++) {
				int drawY = y * boxSize;
				
				// obtain our label
				String label = label(x, y);
				
				// calculate the collective weight of hands at this location
				Collection<Hand> hands = handGroup(label);
				float weight = 0.0f;
				for (Hand hand : hands) {
					double w = 0.0;
					if (range.contains(hand)) {
						w = range.weight(hand);
					}
					weight += w / hands.size();
				}

				// calculate our color based on our weights
				double wr = 2.0 * (1.0 - weight);
				double wg = 2.0 * weight;
				if (wg > 1.0) {
					wg = 1.0;
				}
				if (wr > 1.0) {
					wr = 1.0;
				}

				// set our box color
				Color color = new Color((int) (255 * wr), (int) (255 * wg), 0);
				g.setColor(color);

				// calculate box Y and height
				int boxY = drawY + boxSize - (int) (boxSize * weight);
				int boxHeight = (int) (boxSize * weight);
				
				// draw foreground box
				g.fillRect(drawX, boxY, boxSize, boxHeight);
				
				// calculate hand label position
				FontMetrics metrics = g.getFontMetrics();
				int labelX = drawX + (boxSize - metrics.stringWidth(label)) / 2;
				int labelY = drawY + (boxSize + metrics.getAscent()) / 2;
				
				// render hand label
				g.setColor(Color.BLACK);
				g.drawString(label, labelX, labelY);
				
				// render outline
				g.drawRect(drawX, drawY, boxSize, boxSize);
			}
		}
		return image;
	}
	
	/**
	 * Finds the label to use at the specified coordinates of the matrix.
	 * @param x
	 * 		The x coordinate.
	 * @param y
	 * 		The y coordinate.
	 * @return
	 * 		The label to use at the specified coordinates.
	 */
	private static String label(int x, int y) {
		StringBuilder bldr = new StringBuilder();
		if (x < y) {
			bldr.append(LABELS[x]).append(LABELS[y]);
		} else {
			bldr.append(LABELS[y]).append(LABELS[x]);
		}
		if (x + y > x * 2) {
			bldr.append("o");
		} else if (x + y < x * 2) {
			bldr.append("s");
		}
		return bldr.toString();
	}
	
}
