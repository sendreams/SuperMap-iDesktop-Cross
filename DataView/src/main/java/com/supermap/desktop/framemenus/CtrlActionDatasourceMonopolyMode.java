package com.supermap.desktop.framemenus;

import com.supermap.desktop.Interface.IBaseItem;
import com.supermap.desktop.Interface.IForm;

/**
 * Created by highsad on 2016/8/25.
 */
public class CtrlActionDatasourceMonopolyMode extends CtrlActionDatasourceMode {

	public CtrlActionDatasourceMonopolyMode(IBaseItem caller, IForm formClass) {
		super(caller, formClass);
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}
}
