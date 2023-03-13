package com.nuoian.common.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.nuoian.core.datatype.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Author: 吴宇森
 * @Date: 2022/12/7 18:04
 * @Description: Excel导入导出
 * @Package: com.nuoian.common.poi
 * @Version: 1.0
 */
@Slf4j
public class ExcelUtil {

    /**
     * 功能描述:
     * 〈导出Excel〉
     *
     * @param list           数据集合
     * @param title          标题
     * @param sheetName      工作表名
     * @param pojoClass      实体Class
     * @param fileName       文件名
     * @param isCreateHeader 是否创建头
     * @param type Excel类型
     * @param response       返回信息
     * @author 吴宇森
     * @date 2022/12/7 18:15
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader,ExcelType type, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName,type);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 功能描述:
     * 〈导出Excel〉
     *
     * @param list      数据集合
     * @param title     标题
     * @param sheetName 工作表名
     * @param pojoClass 实体Class
     * @param fileName  文件名
     * @param type Excel类型
     * @param response  返回信息
     * @author 吴宇森
     * @date 2022/12/7 18:14
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,ExcelType type,
                                   HttpServletResponse response) {
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName,type));
    }

    /**
     * 功能描述:
     * 〈导出Excel〉
     *
     * @param list     数据集合
     * @param fileName 文件名
     * @param response 返回信息
     * @author 吴宇森
     * @date 2022/12/7 18:13
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 功能描述:
     * 〈默认导出〉
     *
     * @param list         数据集合
     * @param pojoClass    实体Class
     * @param fileName     文件名
     * @param response     返回信息
     * @param exportParams 导出参数
     * @author 吴宇森
     * @date 2022/12/7 18:13
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                      ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (StringUtils.isNotNull(workbook)) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 功能描述:
     * 〈下载Excel〉
     *
     * @param fileName 文件名
     * @param response 返回信息
     * @param workbook 工作簿
     * @author 吴宇森
     * @date 2022/12/7 18:12
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            //response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("下载Excel失败",e);
        }
    }

    /**
     * 功能描述:
     * 〈默认导出Excel〉
     *
     * @param list     导出数据集合
     * @param fileName 文件名
     * @param response 返回信息
     * @author 吴宇森
     * @date 2022/12/7 18:11
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (StringUtils.isNotNull(workbook)) {
            downLoadExcel(fileName, response, workbook);
        }

    }

    /**
     * 功能描述:
     * 〈导入Excel〉
     *
     * @param filePath   文件路径
     * @param titleRows  标题行
     * @param headerRows 头行
     * @param pojoClass  实体class
     * @return java.util.List<T>
     * @author 吴宇森
     * @date 2022/12/7 18:11
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("模板不能为空");
        } catch (Exception e) {
            log.error("导入Excel失败", e);
        }
        return list;
    }

    /**
     * 功能描述:
     * 〈导入Excel〉
     *
     * @param file       文件
     * @param titleRows  标题行
     * @param headerRows 头行
     * @param pojoClass  实体class
     * @return java.util.List<T>
     * @author 吴宇森
     * @date 2022/12/7 18:10
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (StringUtils.isNull(file)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            log.error("导入Excel失败", e);
        }
        return list;
    }

    /**
     * 功能描述:
     * 〈下载文件〉
     *
     * @param fileName 文件名
     * @param response 返回信息
     * @param workbook 工作簿
     * @author 吴宇森
     * @date 2022/12/7 18:10
     */
    public static void downFile(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("下载文件失败", e);
        }
    }
}
