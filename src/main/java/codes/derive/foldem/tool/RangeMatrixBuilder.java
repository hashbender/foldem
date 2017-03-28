/*
 * This file is part of Fold'em, a Java library for Texas Hold 'em Poker.
 *
 * Fold'em is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Fold'em is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Fold'em.  If not, see <http://www.gnu.org/licenses/>.
 */
package codes.derive.foldem.tool;

import static codes.derive.foldem.Poker.*;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;

import codes.derive.foldem.Hand;
import codes.derive.foldem.Range;

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

		/*
		 * Create an image to draw our matrix on.
		 */
		BufferedImage image = new BufferedImage(SIZE_PX + 1, SIZE_PX + 1,
				BufferedImage.TYPE_INT_ARGB);

		/*
		 * Obtain the Graphics2D context for our image.
		 */
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		/*
		 * Begin rendering our matrix.
		 */
		int boxSize = SIZE_PX / MATRIX_SIZE;
		for (int x = 0; x < MATRIX_SIZE; x++) {
			int drawX = x * boxSize;
			for (int y = 0; y < MATRIX_SIZE; y++) {
				int drawY = y * boxSize;
				
				/*
				 * Obtain the label for the hand group at this coordinate,
				 */
				String label = label(x, y);

				/*
				 * Calculate the collective weight of hands at this location.
				 */
				Collection<Hand> hands = handGroup(label);
				float weight = 0.0f;
				for (Hand hand : hands) {
					double w = 0.0;
					if (range.contains(hand)) {
						w = range.weight(hand);
					}
					weight += w / hands.size();
				}

				/*
				 * Calculate our color based on our collective weight.
				 */
				double wr = 2.0 * (1.0 - weight);
				double wg = 2.0 * weight;
				if (wg > 1.0) {
					wg = 1.0;
				}
				if (wr > 1.0) {
					wr = 1.0;
				}

				/*
				 * Set our box color.
				 */
				Color color = new Color((int) (255 * wr), (int) (255 * wg), 0);
				g.setColor(color);

				/*
				 * Calculate our box Y coordinate and height.
				 */
				int boxY = drawY + boxSize - (int) (boxSize * weight);
				int boxHeight = (int) (boxSize * weight);
				
				/*
				 * Draw the box foreground.
				 */
				g.fillRect(drawX, boxY, boxSize, boxHeight);

				/*
				 * Calculate the position of the label.
				 */
				FontMetrics metrics = g.getFontMetrics();
				int labelX = drawX + 1 + (boxSize - 1 - metrics.stringWidth(label)) / 2;
				int labelY = drawY + 1 + (boxSize - 1 + metrics.getMaxAscent()) / 2;
				
				/*
				 * Draw the label.
				 */
				g.setColor(Color.BLACK);
				g.drawString(label, labelX, labelY);
				
				/*
				 * Finally, render the outline for this group.
				 */
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
