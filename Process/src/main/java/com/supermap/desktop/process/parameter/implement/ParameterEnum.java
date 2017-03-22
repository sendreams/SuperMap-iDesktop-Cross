package com.supermap.desktop.process.parameter.implement;

import com.supermap.desktop.process.enums.ParameterType;
import com.supermap.desktop.process.parameter.ParameterDataNode;
import com.supermap.desktop.process.parameter.interfaces.ISelectionParameter;
import com.supermap.desktop.process.util.EnumParser;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xie
 */
public class ParameterEnum extends AbstractParameter implements ISelectionParameter {

    private EnumParser parser;
    private Object value;
    private String describe;

    public ParameterEnum(EnumParser parser) {
        this.parser = parser;
    }

    @Override
    public String getType() {
        return ParameterType.ENUM;
    }


    @Override
    public void setSelectedItem(Object value) {
        if (!(value instanceof ParameterDataNode)) {
            CopyOnWriteArrayList<ParameterDataNode> items = parser.getEnumItems();
            for (ParameterDataNode item : items) {
                if ((value instanceof String && value.equals(item.getDescribe())) || item.getData() == value) {
                    value = item;
                    break;
                }
            }
        }
        if (value instanceof ParameterDataNode) {
            Object oldValue = this.value;
            this.value = ((ParameterDataNode) value).getData();
            firePropertyChangeListener(new PropertyChangeEvent(this, AbstractParameter.PROPERTY_VALE, oldValue, this.value));
        }
    }

    @Override
    public Object getSelectedItem() {
        return value;
    }

    public EnumParser getEnumParser() {
        return parser;
    }

    public String getDescribe() {
        return describe;
    }

    public ParameterEnum setDescribe(String describe) {
        this.describe = describe;
        return this;
    }

    @Override
    public void dispose() {

    }
}
