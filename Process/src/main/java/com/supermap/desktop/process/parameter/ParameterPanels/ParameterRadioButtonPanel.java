package com.supermap.desktop.process.parameter.ParameterPanels;

import com.supermap.desktop.process.parameter.ParameterDataNode;
import com.supermap.desktop.process.parameter.implement.AbstractParameter;
import com.supermap.desktop.process.parameter.implement.ParameterRadioButton;
import com.supermap.desktop.ui.controls.GridBagConstraintsHelper;
import com.supermap.desktop.utilities.StringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Enumeration;

/**
 * @author XiaJT
 */
public class ParameterRadioButtonPanel extends JPanel {

	private ParameterRadioButton parameterRadioButton;
	private ActionListener radioButtonSelectedListener;
	private boolean isSelectingItem = false;
	private ButtonGroup buttonGroup = new ButtonGroup();

	public ParameterRadioButtonPanel(ParameterRadioButton parameterRadioButton) {
		this.parameterRadioButton = parameterRadioButton;
		initLayout();
		initListeners();
	}

	private void initLayout() {
		this.setLayout(new GridBagLayout());
		ParameterDataNode[] items = parameterRadioButton.getItems();
		radioButtonSelectedListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isSelectingItem) {
					isSelectingItem = true;
					parameterRadioButton.setSelectedItem(((MyRadioButton) e.getSource()).getParameterDataNode());
					isSelectingItem = false;
				}
			}
		};
		if (items.length == 3) {
			for (int i = 0; i < items.length; i++) {
				ParameterDataNode item = items[i];
				JRadioButton jRadioButton = new MyRadioButton(item);
				buttonGroup.add(jRadioButton);
				jRadioButton.addActionListener(radioButtonSelectedListener);
				this.add(jRadioButton, new GridBagConstraintsHelper(i, 0, 1, 1).setWeight(1, 1).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.NONE));
			}
		} else {
			for (int i = 0; i < items.length; i++) {
				ParameterDataNode item = items[i];
				MyRadioButton myRadioButton = new MyRadioButton(item);
				buttonGroup.add(myRadioButton);
				myRadioButton.addActionListener(radioButtonSelectedListener);
				int x = i % 2;
				int y = i / 2;
				this.add(myRadioButton, new GridBagConstraintsHelper(x, y, 1, 1).setWeight(1, 1).setAnchor(GridBagConstraints.CENTER).setFill(GridBagConstraints.NONE));
			}
		}
		setSelectedButton(parameterRadioButton.getSelectedItem());
	}

	private void initListeners() {
		parameterRadioButton.addPropertyListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (!isSelectingItem && evt.getPropertyName().equals(AbstractParameter.PROPERTY_VALE)) {
					Object newValue = evt.getNewValue();
					setSelectedButton(newValue);
				}
			}
		});
	}

	private void setSelectedButton(Object newValue) {
		if (newValue == null) {
			return;
		}
		isSelectingItem = true;
		Enumeration<AbstractButton> elements = buttonGroup.getElements();
		while (elements.hasMoreElements()) {
			MyRadioButton myRadioButton = (MyRadioButton) elements.nextElement();
			if (myRadioButton.getParameterDataNode() == newValue) {
				myRadioButton.setSelected(true);
				break;
			}
		}
		isSelectingItem = false;
	}

	class MyRadioButton extends JRadioButton {
		private ParameterDataNode parameterDataNode;

		public MyRadioButton(ParameterDataNode parameterDataNode) {
			this.parameterDataNode = parameterDataNode;
			this.setText(StringUtilities.isNullOrEmpty(parameterDataNode.getDescribe()) ? String.valueOf(parameterDataNode.getData()) : parameterDataNode.getDescribe());
		}

		public ParameterDataNode getParameterDataNode() {
			return parameterDataNode;
		}

		public void setParameterDataNode(ParameterDataNode parameterDataNode) {
			this.parameterDataNode = parameterDataNode;
		}
	}
}
