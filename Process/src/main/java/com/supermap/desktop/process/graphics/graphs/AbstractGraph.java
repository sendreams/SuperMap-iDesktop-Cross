package com.supermap.desktop.process.graphics.graphs;

import com.supermap.desktop.process.graphics.GraphCanvas;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by highsad on 2017/1/20.
 */
public abstract class AbstractGraph implements IGraph {

	private GraphCanvas canvas;
	protected Shape shape;

	public AbstractGraph(GraphCanvas canvas, Shape shape) {
		this.canvas = canvas;
		this.shape = shape;
	}

	public Shape getShape() {
		return this.shape;
	}

	@Override
	public Rectangle getBounds() {
		if (this.shape != null) {
			return this.shape.getBounds();
		} else {
			return null;
		}
	}

	@Override
	public Point getLocation() {
		if (this.shape != null) {
			return this.shape.getBounds().getLocation();
		} else {
			return null;
		}
	}

	@Override
	public Point getCenter() {
		if (this.shape != null) {
			double x = this.shape.getBounds().getX();
			double y = this.shape.getBounds().getY();
			double width = this.shape.getBounds().getWidth();
			double height = this.shape.getBounds().getHeight();
			Point center = new Point();
			center.setLocation(x + width / 2, y + height / 2);
			return center;
		} else {
			return null;
		}
	}

	@Override
	public int getWidth() {
		if (this.shape != null) {
			return this.shape.getBounds().width;
		} else {
			return -1;
		}
	}

	@Override
	public int getHeight() {
		if (this.shape != null) {
			return this.shape.getBounds().height;
		} else {
			return -1;
		}
	}

	public GraphCanvas getCanvas() {
		return this.canvas;
	}

	public abstract IGraph clone();
}