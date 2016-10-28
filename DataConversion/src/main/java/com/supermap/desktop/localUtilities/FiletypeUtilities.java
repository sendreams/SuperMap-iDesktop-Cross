package com.supermap.desktop.localUtilities;

import com.supermap.desktop.dataconversion.DataConversionProperties;
import com.supermap.desktop.iml.FileTypeLocale;

/**
 * Created by xie on 2016/10/14.
 * 文件类型转换工具类
 * 将文件类型对应为自己设定的文件类型
 */
public class FiletypeUtilities {
    private FiletypeUtilities() {
        // 工具类不提供公共的构造函数
    }

    public static String getParseFile(String filePath, String fileFilter) {
        String fileType = "";
        if (filePath.equalsIgnoreCase(FileTypeLocale.DBF_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_DBF");
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.VCT_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_VCT");
            // vct文件
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.DXF_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.DWG_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_CAD");
            // AutoCAD 格式(*.dxf,*.dwg)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.SHP_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.GRD_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.TXT_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.E00_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.DEM_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_ArcGIS");
            // ArcGIS 交换格式(*.shp,*.grd,*.txt,*.e00,*.dem，*dbf)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.TAB_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.MIF_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.WOR_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_MapInfo");
            // MapInfo 交换格式(*.tab,*.mif,*.wor)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.WAL_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.WAN_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.WAP_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.WAT_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_MapGIS");
            // MapGIS 交换格式(*.wat,*.wan,*.wal,*.wap)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.XLSX_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.CSV_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.XLS_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_MicroSoft");
            // Microsoft 交换格式(*.xlsx,*.csv)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.SIT_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.IMG_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.TIF_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.TIFF_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.BMP_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.PNG_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.JPG_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.JPEG_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.GIF_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.JP2_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.JPK_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_FilterImage");
            // 影像位图文件(*.sit,*.img,*.tif,*.tiff,*.bmp,*.png,*.gif,*.jpg,*.jpeg)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.SCV_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.OSGB_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.TDS_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.X_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_FilterModel");
            // 三维模型文件(*.scv,*.osgb,*.3ds,*.x)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.KML_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.KMZ_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_FilterGoogle");
            // 谷歌KML交换格式(*.kml,*.kmz)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.BIL_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.RAW_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.BSQ_STRING) || filePath.equalsIgnoreCase(FileTypeLocale.BIP_STRING)
                || filePath.equalsIgnoreCase(FileTypeLocale.B_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_GRID");
            // 栅格文件(*.bil,*.raw,*.bsq,*.bip,*.sid,*.b)
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.SID_STRING)) {
            fileType = "SID";
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.VCT_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_VCT");
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.DGN_STRING)) {
            fileType = "DGN";
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.ECW_STRING)) {
            fileType = "ECW";
        } else if (filePath.equalsIgnoreCase(FileTypeLocale.DEM_STRING)) {
            fileType = DataConversionProperties.getString("String_FormImport_ArcGIS");
        } else if (fileFilter.equalsIgnoreCase(DataConversionProperties.getString("string_filetype_lidar"))) {
            // LIDAR文件(*.txt)
            fileType = DataConversionProperties.getString("String_FormImport_FilterLIDAR");
        } else if (fileFilter.equalsIgnoreCase(DataConversionProperties.getString("string_filetype_3ds"))) {
            fileType = DataConversionProperties.getString("String_FormImport_FilterModel");
            // 三维dxf文件（.ModelDXF）
        }
        return fileType;
    }
}
