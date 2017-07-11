package com.supermap.desktop.process.meta.metaProcessImplements;

import com.supermap.data.Dataset;
import com.supermap.desktop.Application;
import com.supermap.desktop.controls.ControlsProperties;
import com.supermap.desktop.process.ProcessProperties;
import com.supermap.desktop.process.constraint.implement.EqualDatasourceConstraint;
import com.supermap.desktop.process.events.RunningEvent;
import com.supermap.desktop.process.messageBus.NewMessageBus;
import com.supermap.desktop.process.meta.MetaKeys;
import com.supermap.desktop.process.meta.MetaProcess;
import com.supermap.desktop.process.parameter.ParameterDataNode;
import com.supermap.desktop.process.parameter.implement.ParameterBigDatasourceDatasource;
import com.supermap.desktop.process.parameter.implement.ParameterCombine;
import com.supermap.desktop.process.parameter.implement.ParameterComboBox;
import com.supermap.desktop.process.parameter.implement.ParameterDatasource;
import com.supermap.desktop.process.parameter.implement.ParameterIServerLogin;
import com.supermap.desktop.process.parameter.implement.ParameterSingleDataset;
import com.supermap.desktop.process.parameter.interfaces.datas.types.BasicTypes;
import com.supermap.desktop.process.parameter.interfaces.datas.types.Type;
import com.supermap.desktop.process.tasks.ProcessTask;
import com.supermap.desktop.process.util.TaskUtil;
import com.supermap.desktop.properties.CommonProperties;
import com.supermap.desktop.properties.CoreProperties;
import com.supermap.desktop.ui.lbs.Interface.IServerService;
import com.supermap.desktop.ui.lbs.params.CommonSettingCombine;
import com.supermap.desktop.ui.lbs.params.JobResultResponse;
import com.supermap.desktop.utilities.CursorUtilities;
import com.supermap.desktop.utilities.DatasetUtilities;

/**
 * @author XiaJT
 */
public class MetaProcessSingleQuery extends MetaProcess {

	private ParameterIServerLogin parameterIServerLogin = new ParameterIServerLogin();
	private ParameterBigDatasourceDatasource parameterSourceDatasource;
	private ParameterSingleDataset parameterSourceDataset;

	private ParameterBigDatasourceDatasource parameterQueryDatasource;
	private ParameterSingleDataset parameterQueryDataset;

	private ParameterComboBox parameterQueryTypeComboBox;

	public MetaProcessSingleQuery() {
		initComponents();
		initComponentState();
		initConstraint();
		initListener();
	}

	private void initComponents() {
		parameterSourceDatasource = new ParameterBigDatasourceDatasource();
		parameterSourceDatasource.setDescribe(CommonProperties.getString("String_SourceDatasource"));
		parameterSourceDataset = new ParameterSingleDataset();
		parameterSourceDataset.setDescribe(CommonProperties.getString("String_Label_SourceDataset"));

		parameterQueryDatasource = new ParameterBigDatasourceDatasource();
		parameterQueryDatasource.setDescribe(ProcessProperties.getString("String_Label_QueryDatasource"));
		parameterQueryDataset = new ParameterSingleDataset();
		parameterQueryDataset.setDescribe(ProcessProperties.getString("String_Label_QueryDataset"));

		parameterQueryTypeComboBox = new ParameterComboBox(ControlsProperties.getString("String_OverlayAnalystType"));
		parameterQueryTypeComboBox.setItems(
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_ContainCHS"), "CONTAIN"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_CrossCHS"), "CROSS"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_DisjointCHS"), "DISJOINT"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_IdentityCHS"), "IDENTITY"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_IntersectCHS"), "INTERSECT"),
				new ParameterDataNode(CoreProperties.getString("String_None"), "NONE"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_OverlapCHS"), "OVERLAP"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_TouchCHS"), "TOUCH"),
				new ParameterDataNode(CoreProperties.getString("String_SpatialQuery_WithinCHS"), "WITHIN")
		);

		ParameterCombine parameterCombineSource = new ParameterCombine();
		parameterCombineSource.setDescribe(ControlsProperties.getString("String_GroupBox_SourceDataset"));
		parameterCombineSource.addParameters(parameterSourceDatasource, parameterSourceDataset);
		ParameterCombine parameterCombineQuery = new ParameterCombine();
		parameterCombineQuery.setDescribe(ProcessProperties.getString("String_QueryData"));
		parameterCombineQuery.addParameters(parameterQueryDatasource, parameterQueryDataset);
		ParameterCombine parameterCombineSetting = new ParameterCombine();
		parameterCombineSetting.setDescribe(ProcessProperties.getString("String_setParameter"));
		parameterCombineSetting.addParameters(parameterQueryTypeComboBox);

		parameters.addParameters(parameterIServerLogin, parameterCombineSource, parameterCombineQuery, parameterCombineSetting);
		parameters.addInputParameters("source", Type.UNKOWN, parameterCombineSource);// 缺少对应的类型
		parameters.addInputParameters("Query", Type.UNKOWN, parameterCombineQuery);// 缺少对应的类型
		parameters.addOutputParameters("QueryResult", BasicTypes.STRING, null);
	}

	private void initComponentState() {
		Dataset defaultBigDataStoreDataset = DatasetUtilities.getDefaultBigDataStoreDataset();
		if (defaultBigDataStoreDataset != null) {
			parameterSourceDatasource.setSelectedItem(defaultBigDataStoreDataset.getDatasource());
			parameterSourceDataset.setSelectedItem(defaultBigDataStoreDataset);

			parameterQueryDatasource.setSelectedItem(defaultBigDataStoreDataset.getDatasource());
			parameterQueryDataset.setSelectedItem(defaultBigDataStoreDataset);

		}
	}

	private void initConstraint() {
		EqualDatasourceConstraint equalSourceDatasource = new EqualDatasourceConstraint();
		equalSourceDatasource.constrained(parameterSourceDatasource, ParameterDatasource.DATASOURCE_FIELD_NAME);
		equalSourceDatasource.constrained(parameterSourceDataset, ParameterSingleDataset.DATASOURCE_FIELD_NAME);

		EqualDatasourceConstraint equalQueryDatasource = new EqualDatasourceConstraint();
		equalQueryDatasource.constrained(parameterQueryDatasource, ParameterDatasource.DATASOURCE_FIELD_NAME);
		equalQueryDatasource.constrained(parameterQueryDataset, ParameterSingleDataset.DATASOURCE_FIELD_NAME);
	}

	private void initListener() {

	}


	@Override
	public String getTitle() {
		return ProcessProperties.getString("String_SingleQuery");
	}

	@Override
	public boolean execute() {
		try {
		IServerService service = parameterIServerLogin.login();
		Dataset sourceDataset = parameterSourceDataset.getSelectedDataset();
		Dataset queryDataset = parameterQueryDataset.getSelectedDataset();
		String queryType = (String) parameterQueryTypeComboBox.getSelectedData();

			CommonSettingCombine input = new CommonSettingCombine("input", "");
			input.add(new CommonSettingCombine("datasetSource", sourceDataset.getName()));

			CommonSettingCombine analyst = new CommonSettingCombine("analyst", "");
			analyst.add(new CommonSettingCombine("datasetQuery", queryDataset.getName()));
			analyst.add(new CommonSettingCombine("mode", queryType));

			CommonSettingCombine query = new CommonSettingCombine("query", "");
			query.add(input, analyst);
			CursorUtilities.setWaitCursor();
			JobResultResponse response = service.queryResult(MetaKeys.SINGLE_QUERY,query.getFinalJSon());
			if (null != response) {
				ProcessTask task = TaskUtil.getTask(this);
				NewMessageBus messageBus = new NewMessageBus(response, task);
				messageBus.run();
			}
			fireRunning(new RunningEvent(this, 100, "finished"));
			parameters.getOutputs().getData("QueryResult").setValue("");
		} catch (Exception e) {
			Application.getActiveApplication().getOutput().output(e.getMessage());
			return false;
		} finally {
			CursorUtilities.setDefaultCursor();
		}
		return true;
	}

	@Override
	public String getKey() {
		return MetaKeys.SINGLE_QUERY;
	}
}