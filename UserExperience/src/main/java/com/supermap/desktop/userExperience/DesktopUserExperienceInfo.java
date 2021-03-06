package com.supermap.desktop.userExperience;

import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONObject;
import com.supermap.desktop.interfaces.UserExperienceBean;
import com.supermap.desktop.properties.CoreProperties;

import java.awt.*;
import java.util.Locale;

/**
 * @author XiaJT
 */
public class DesktopUserExperienceInfo implements UserExperienceBean, JSONAware {

	private static final String culture = Locale.getDefault().getDisplayLanguage();
	private static final String optionSystem = System.getProperties().getProperty("os.name");
	private static final String platForm = System.getProperties().getProperty("os.version");
	private static final String RESOLUTION_RATIO = Toolkit.getDefaultToolkit().getScreenSize().width + "*" + Toolkit.getDefaultToolkit().getScreenSize().height;

	private FunctionInfo functionInfo;
	private LicenseInfo licenseInfo;

	public DesktopUserExperienceInfo(FunctionInfo functionInfo) {
		this.functionInfo = functionInfo;
	}

	public DesktopUserExperienceInfo(LicenseInfo userLicenseInfo) {
		licenseInfo = userLicenseInfo;
	}

	@Override
	public String getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Culture", culture);
		jsonObject.put("DesktopVersion", "900");
		jsonObject.put("FunctionInfo", functionInfo);
		jsonObject.put("LicenseInfo", licenseInfo);
		jsonObject.put("OptionSystem", optionSystem);
		jsonObject.put("PlatForm", platForm);
		jsonObject.put("ProductName", "SuperMap iDesktop Cross 9D");
		jsonObject.put("ResolutionRatio", RESOLUTION_RATIO);
		jsonObject.put("UserID", "");
		jsonObject.put("UserName", CoreProperties.getString("String_UserManage_Visitor"));
		jsonObject.put("Version", CoreProperties.getString("String_ProductVersion"));
		return jsonObject.toJSONString();
	}


	public FunctionInfo getFunctionInfo() {
		return functionInfo;
	}

	public void setFunctionInfo(FunctionInfo functionInfo) {
		this.functionInfo = functionInfo;
	}

	public LicenseInfo getLicenseInfo() {
		return licenseInfo;
	}

	public void setLicenseInfo(LicenseInfo licenseInfo) {
		this.licenseInfo = licenseInfo;
	}

	@Override
	public String toJSONString() {
		return getJson();
	}
}
