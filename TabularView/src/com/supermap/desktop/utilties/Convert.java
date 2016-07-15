package com.supermap.desktop.utilties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.supermap.desktop.Application;
import com.supermap.desktop.tabularview.TabularViewProperties;
import com.supermap.desktop.utilities.StringUtilities;

/**
 * 数据转换类
 * 
 * @author xie
 *
 */
public class Convert {
	private static SimpleDateFormat resultFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	public static String getDateStr(String str) {
		String success = "";
		if (str.contains(".")) {
			success = getDateInfo(str, success, ".");
		} else if (str.contains("/")) {
			success = getDateInfo(str, success, "/");
		}
		return success;
	}

	private static String getDateInfo(String str, String success, String temp) {
		SimpleDateFormat format;
		String[] dateStr = str.split("\\" + temp);
		if (dateStr.length == 1 && dateStr[0].length() == 4) {
			format = new SimpleDateFormat("yyyy");
			format.setLenient(false);
			success = getResultStr(str, success, format);
		} else if (dateStr.length == 2 && dateStr[0].length() == 4) {
			format = new SimpleDateFormat("yyyy" + temp + "MM");
			format.setLenient(false);
			success = getResultStr(str, success, format);
		} else if (dateStr.length == 3 && dateStr[0].length() == 4 && !dateStr[2].contains(":")) {
			format = new SimpleDateFormat("yyyy" + temp + "MM" + temp + "dd");
			format.setLenient(false);
			success = getResultStr(str, success, format);
		} else if (dateStr.length == 3 && dateStr[0].length() == 4 && dateStr[2].contains(":")) {
			String[] hourStr = dateStr[2].split(":");
			if (hourStr.length == 2) {
				format = new SimpleDateFormat("yyyy" + temp + "MM" + temp + "dd hh:mm");
				format.setLenient(false);
				success = getResultStr(str, success, format);
			} else if (hourStr.length == 3) {
				format = new SimpleDateFormat("yyyy" + temp + "MM" + temp + "dd hh:mm:ss");
				format.setLenient(false);
				success = getResultStr(str, success, format);
			}
		}
		return success;
	}

	private static String getResultStr(String str, String success, SimpleDateFormat format) {
		try {
			Date date = format.parse(str);
			success = resultFormat.format(date);
		} catch (Exception e) {
			Application.getActiveApplication().getOutput().output(TabularViewProperties.getString("String_DateError"));
		}
		return success;
	}

	public static Date toDateTime(String srcValue) {
		Date result = null;
		if (!StringUtilities.isNullOrEmpty(srcValue) && !StringUtilities.isNumber(getDateStr(srcValue))) {
			try {
				result = resultFormat.parse(getDateStr(srcValue));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static double toDouble(Object expression) {
		if (!StringUtilities.isNullOrEmptyString(expression)&&StringUtilities.isNumber(expression.toString())) {
			return Double.parseDouble(expression.toString());
		}
		return 0.0;
	}

	public static int toInteger(Object expression) {
		if (!StringUtilities.isNullOrEmptyString(expression) && StringUtilities.isNumeric(expression.toString())) {
			return Integer.parseInt(expression.toString());
		}
		if (!StringUtilities.isNullOrEmptyString(expression) && expression.toString().contains(".")) {
			double temp = Math.round(Double.parseDouble(expression.toString()));
			String tempStr = String.valueOf(temp);
			expression = tempStr.substring(0, tempStr.indexOf("."));
			return Integer.parseInt(expression.toString());
		}
		return 0;
	}

	public static boolean toBoolean(Object expression) {
		if (!StringUtilities.isNullOrEmptyString(expression) && !StringUtilities.isNumber(expression.toString())) {
			if (expression.toString().equalsIgnoreCase("true")) {
				return true;
			}
		}
		if (!StringUtilities.isNullOrEmptyString(expression) && StringUtilities.isNumber(expression.toString())) {
			return Double.compare(toDouble(expression), 0.0) >= 0 ? true : false;
		}
		return false;
	}
}