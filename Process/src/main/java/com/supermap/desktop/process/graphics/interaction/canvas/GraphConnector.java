package com.supermap.desktop.process.graphics.interaction.canvas;

import com.supermap.desktop.Application;
import com.supermap.desktop.process.core.IProcess;
import com.supermap.desktop.process.graphics.CanvasCursor;
import com.supermap.desktop.process.graphics.GraphCanvas;
import com.supermap.desktop.process.graphics.GraphicsUtil;
import com.supermap.desktop.process.graphics.connection.DefaultLine;
import com.supermap.desktop.process.graphics.graphs.*;
import com.supermap.desktop.process.parameter.interfaces.datas.Inputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by highsad on 2017/3/22.
 */
public class GraphConnector extends CanvasEventAdapter {
	private GraphCanvas canvas;
	private DefaultLine previewLine;
	private OutputGraph startGraph = null;
	private IGraph endGraph = null;

	public GraphConnector(GraphCanvas canvas) {
		this.canvas = canvas;
	}

	public void connecting() {
		CanvasCursor.setConnectingCursor(this.canvas);
		this.previewLine = new DefaultLine(this.canvas);
		this.canvas.setEventHandlerEnabled(Selection.class, false);
		this.canvas.setEventHandlerEnabled(DraggedHandler.class, false);
		this.canvas.setEventHandlerEnabled(GraphCreator.class, false);
	}

	public void preview(Graphics g) {
		if (this.previewLine != null) {
			this.previewLine.paint(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			IGraph hit = this.canvas.findGraph(e.getPoint());

			if (isStartValid(hit)) {
				this.startGraph = (OutputGraph) hit;
				this.previewLine.setStartPoint(hit.getCenter());
			} else {
				this.startGraph = null;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		try {
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (this.startGraph != null && this.endGraph != null) {
					this.canvas.getConnection().connect(this.startGraph, this.endGraph);
				}
			}
		} catch (Exception ex) {
			Application.getActiveApplication().getOutput().output(ex);
		} finally {
			this.previewLine.setStartPoint(null);
			this.previewLine.setEndPoint(null);
			this.previewLine.setStatus(DefaultLine.NORMAL);
			this.startGraph = null;
			this.endGraph = null;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			if (SwingUtilities.isRightMouseButton(e)) {
				clean();
			}
		} catch (Exception ex) {
			Application.getActiveApplication().getOutput().output(ex);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		try {
		} catch (Exception ex) {
			Application.getActiveApplication().getOutput().output(ex);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			if (this.previewLine == null || this.previewLine.getStartPoint() == null) {
				return;
			}

			IGraph hit = this.canvas.findGraph(e.getPoint());

			if (hit == null) {
				this.endGraph = null;
				this.previewLine.setStatus(DefaultLine.NORMAL);
				this.previewLine.setEndPoint(this.canvas.getCoordinateTransform().inverse(e.getPoint()));
			} else {
				if (isEndValid(hit)) {
					this.endGraph = hit;
					this.previewLine.setStatus(DefaultLine.PREPARING);
					this.previewLine.setEndPoint(GraphicsUtil.chop(((AbstractGraph) this.endGraph).getShape(), this.startGraph.getCenter()));
				} else {
					this.endGraph = null;
					this.previewLine.setStatus(DefaultLine.INVALID);
					this.previewLine.setEndPoint(this.canvas.getCoordinateTransform().inverse(e.getPoint()));
				}
			}
		}
	}

	private boolean isStartValid(IGraph graph) {
		return graph instanceof DataGraph;
	}

	private boolean isEndValid(IGraph graph) {
		boolean ret = false;

		if (this.startGraph == null) {
			return false;
		}

		if (graph == this.startGraph) {
			return false;
		}

		// If the specified graph  has already been connected to this startGraph, return false.
		if (this.canvas.getConnection().isConnected(this.startGraph, graph)) {
			return false;
		}

		if (!(graph instanceof ProcessGraph)) {
			return false;
		}

		ProcessGraph processGraph = (ProcessGraph) graph;
		IProcess process = processGraph.getProcess();
		if (process == null || process.getInputs() == null || process.getInputs().getCount() == 0) {
			return false;
		}

		Inputs inputs = process.getInputs();
		if (inputs.getDatas(this.startGraph.getProcessData().getType()).length > 0) {
			ret = true;
		}
		return ret;
	}

	@Override
	public void clean() {
		try {
			if (this.previewLine != null) {
				this.previewLine.setStartPoint(null);
				this.previewLine.setEndPoint(null);
				this.previewLine.setStatus(DefaultLine.NORMAL);
				this.previewLine = null;
			}
			this.endGraph = null;
			this.startGraph = null;
		} catch (Exception e) {
			Application.getActiveApplication().getOutput().output(e);
		} finally {
			CanvasCursor.resetCursor(this.canvas);
			this.canvas.setEventHandlerEnabled(Selection.class, true);
			this.canvas.setEventHandlerEnabled(DraggedHandler.class, true);
			this.canvas.setEventHandlerEnabled(GraphCreator.class, true);
		}
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && this.previewLine != null;
	}
}