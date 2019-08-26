package com.fulan.common.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description Excel导入导出工具类
 * @author hjs
 * @Date 2019年8月12日
 */
public class ExcelUtil {

    private static ExcelUtil instance = null;
    
    private ExcelUtil(){};
    
    public static final ExcelUtil getInstance(){
        synchronized (ExcelUtil.class) {
            if(instance == null){
                instance = new ExcelUtil();
            }
        }
        return instance;
    }
    
    private static volatile boolean isTimestamp = false;
    
    public static void setTimestamp(boolean isTimestamp) {
        isTimestamp = isTimestamp;
    }

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 解析excel转成Java对象
     * @param is
     * @param entityClass
     * @return
     * @throws IntrospectionException
     */
    public <T> List<T> parceExcel(InputStream is,Class<T> entityClass) throws IntrospectionException{
        ExcelMapping excelMapping = entityClass.getAnnotation(ExcelMapping.class);
        if(excelMapping == null){
            throw new IllegalArgumentException("Excel解析失败");
        }
        HSSFWorkbook wb = null;
        
        try {
            POIFSFileSystem fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(is);
        } catch (Exception e) {
            logger.info("解析异常"+e); 
            e.printStackTrace();
        }
        return excelToEntity(entityClass, wb.getSheetAt(0), parseAnnotation(entityClass), excelMapping.beginRow());
    }
    
    /**
     * 获得一个excel文件
     * @param entityList
     * @param map
     * @author hjs
     * @throws IOException 
     */
    public <T>void getSheetWorkBook(List<T> entityList,Map<String, Object> map) throws IOException{
        String filepath = (String) map.get("filepath");
        String filename = (String) map.get("filename");
        
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        exportExcel(workBook,"Export Excel",entityList,(String[])map.get("fields"),(String[])map.get("headtitles"));
        
        File file = new File(filepath+filename);
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            workBook.write(arrayOutputStream);
            arrayOutputStream.writeTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            logger.info("文件读取异常"+e);
            e.printStackTrace();
        }
        
    }
    
    /**
     * Java对象转成Excel文件流
     * @param entityList
     * @param searchCondition
     * @return
     * @throws IntrospectionException 
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public <T> OutputStream exportExcel(List<T> entityList,String searchCondition) throws IntrospectionException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if(entityList == null || entityList.size() < 1){
            throw new IllegalArgumentException("导出为空");
        }
        
        T annotation = entityList.get(0);
        ExcelExportMapping excelExportMapping = annotation.getClass().getAnnotation(ExcelExportMapping.class);
        if(excelExportMapping == null){
            throw new IllegalArgumentException("ExcelExportMapping为空");
        }
        int rownum = 0;
        
        Map<Integer, ParamentEntity> annotationMap = parseAnnotation(annotation.getClass());
        
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        HSSFSheet sheet = workBook.createSheet("export excel");
        
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        //垂直居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workBook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        
        Iterator<T> iterator = entityList.iterator();
        
        while (iterator.hasNext()) {
            T entity = iterator.next();
            
            if(searchCondition != null){
                if(rownum == 0){
                    HSSFRow row = sheet.createRow(rownum);
                    //说明：1：开始行 2：结束行 3：开始列 4：技术列
                    sheet.addMergedRegion(new CellRangeAddress(0,0,0,14));
                    
                    row.setHeight((short) 700);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(searchCondition);
                    
                    HSSFCellStyle cellStyle0 = workBook.createCellStyle();
                    //垂直居中
                    cellStyle0.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                    
                    HSSFFont font0 = workBook.createFont();
                    font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    font0.setFontHeightInPoints((short) 11);
                    cellStyle0.setFont(font0);
                    cell.setCellStyle(cellStyle0);
                    
                    rownum = 1;
                }
                if(rownum == 1 && hasTableHeader(excelExportMapping)){
                    createTableHeader(sheet,rownum++,entity,annotationMap,cellStyle);
                }
            }else {
                if(rownum == 0 && hasTableHeader(excelExportMapping)){
                    createTableHeader(sheet,rownum++,entity,annotationMap,cellStyle);
                }
            }
            createTableRow(sheet,rownum++,entity,annotationMap);
            
        }
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        workBook.write(arrayOutputStream);
        
        return arrayOutputStream;
    }
    
    public <T> void createTableRow(HSSFSheet sheet,int rownum,T entity,
            Map<Integer, ParamentEntity> annotationMap) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        HSSFRow row = sheet.createRow(rownum);
        
        Iterator<Integer> iterator = annotationMap.keySet().iterator();
        while (iterator.hasNext()) {
            int key = iterator.next().intValue();
            HSSFCell cell = row.createCell(key - 1);
            ParamentEntity paramentEntity = (ParamentEntity)annotationMap.get(Integer.valueOf(key));
            
            Object obj = paramentEntity.getDescriptor().getReadMethod().invoke(entity, new Object[0]);
            if(paramentEntity.isNull() && obj == null){
                cell.setCellValue("");
            }else{
                if(obj == null){
                    cell.setCellValue("");
                }else if(paramentEntity.getDescriptor().getPropertyType().equals(Date.class)){
                    if(!isTimestamp){
                        cell.setCellValue(com.fulan.common.utils.DateUtil.formatDate((Date) obj, "yyyy-MM-dd"));
                    }else{
                        cell.setCellValue(com.fulan.common.utils.DateUtil.formatDate((Date) obj, "yyyy-MM-dd HH:mm:ss"));
                    }
                }else{
                    Object field = paramentEntity.getEnumExcel().checkField(obj);
                    if(field != null && field.toString() != null){
                        cell.setCellValue(field.toString());
                    }else{
                        cell.setCellValue("");
                    }
                }
            }
        }
    }
    
    public <T> void createTableHeader(HSSFSheet sheet,int rownum,T entity,
            Map<Integer, ParamentEntity> annotationMap,HSSFCellStyle cellStyle){
        HSSFRow row = sheet.createRow(rownum);
        row.setHeight((short) 700);
        
        Iterator<Integer> iterator = annotationMap.keySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            int key = iterator.next().intValue();
            
            HSSFCell cell = row.createCell(key - 1);
            ParamentEntity paramentEntity = (ParamentEntity)annotationMap.get(Integer.valueOf(key));
            
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
            cell.setCellValue(String.valueOf(paramentEntity.getTableHeader()));
            cell.setCellStyle(cellStyle);
            
            sheet.setColumnWidth(i, 4500);
            i++;
        }
    }
    
    /**
     * @param excelExportMapping
     * @return
     */
    private <T> boolean hasTableHeader(ExcelExportMapping excelExportMapping) {
        return excelExportMapping.hasTableHeader();
    }

    /**
     * 填充内容，返回一个sheet
     * @param workbook
     * @param sheetName
     * @param entityList
     * @param fields
     * @param headtitles
     * @return
     */
    public <T> HSSFSheet exportExcel(HSSFWorkbook workBook,String sheetName,
            List<T> entityList,String[] fields,String[] headtitles){
        HSSFSheet sheet = workBook.createSheet(sheetName);
        
        //生成标题
        HSSFRow row0 = sheet.createRow(0);
        row0.setHeight((short) 400);
        for (int i = 0; i < headtitles.length; i++) {
            HSSFCell cell = row0.createCell(i);
            cell.setCellValue(headtitles[i]);
        }
        
        //生成内容
        if(entityList != null && entityList.size() > 0){
            HashMap<String,Field> fieldMap = new HashMap<String, Field>();
            for (int i = 0; i < entityList.size(); i++) {
                T entity = entityList.get(i);
                if(i == 0){
                    //填充反射字段
                    Field[] fieldsArray = entity.getClass().getDeclaredFields();
                    for (Field field : fieldsArray) {
                        field.setAccessible(true);
                        fieldMap.put(field.getName(), field);
                    }
                }
                HSSFRow row = sheet.createRow(i+1);
                for (int j = 0; j < fields.length; j++) {
                    Object val = null;
                    try {
                        fieldMap.get(fields[j]).get(entity);
                    } catch (Exception e) {
                        logger.info("映射异常"+e);
                    }
                    HSSFCell cell = row.createCell(j);
                    String str = "";
                    if(val != null && "null".equals(val)){
                        if(val instanceof Date){
                            str = com.fulan.common.utils.DateUtil.formatDate((Date) val, "yyyy-MM-dd");
                        }else{
                            str = String.valueOf(val);
                        }
                    }
                    cell.setCellValue(str);
                }
            }
        }
        return sheet;
    }
    
    /**
     * 行转换成实体
     * @param entity 泛型类
     * @param sheet 待解析的sheet页
     * @param map 实体字段上的注解信息
     * @param beginRow 从哪一行开始解析
     * @return
     */
    private <T> List<T> excelToEntity(Class<T> entity,HSSFSheet sheet,Map<Integer, ParamentEntity> map,int beginRow){
        ArrayList<T> list = new ArrayList<T>();
        for (int i = beginRow - 1; i < sheet.getPhysicalNumberOfRows(); ++i) {
            HSSFRow row = sheet.getRow(i);
            
            T instance = null;
            try {
                instance = entity.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.info("解析异常"+e);
                e.printStackTrace();
            }
            
            if(!isBlankRow(row)){
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    if(!(map.containsKey(Integer.valueOf(j+1)))){
                      continue;  
                    }
                    try {
                        HSSFCell cell = row.getCell(j);
                        ParamentEntity paramentEntity = map.get(Integer.valueOf(j+1));
                        
                        if(cell == null && !(paramentEntity.isNull())){
                            throw new IllegalArgumentException();
                        }
                        
                        if(cell != null || !(paramentEntity.isNull())){
                            String cellStr = getCellValue(cell);
                            
                            setVlaure(instance,cellStr,paramentEntity);
                        }
                    } catch (Exception e) {
                        logger.info("解析异常"+e);
                        e.printStackTrace();
                    }  
                }
                list.add(instance);
            }
        }
        return list;
    }
    
    /**
     * HSSFRow是否为空
     * @param row
     * @return
     */
    private boolean isBlankRow(HSSFRow row) {
        if(row == null){
            return true;
        }
        
        boolean result = true;
        
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            HSSFCell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int)cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                  
                default:
                    break;
                }
                if(value != null){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private Map<Integer, ParamentEntity>  parseAnnotation(Class<?> entity) throws IntrospectionException{
        Field[] fields = entity.getDeclaredFields();
        if(fields == null){
            throw new IllegalArgumentException(entity.getName()+"无字段");
        }
        HashMap<Integer,ParamentEntity> map = new HashMap<Integer, ParamentEntity>();
        
        for (Field field : fields) {
            //排除未被标记的属性
            if(!(field.isAnnotationPresent(CellMapping.class))){
                continue;
            }
            
            CellMapping cellMapping = field.getAnnotation(CellMapping.class);
            ParamentEntity paramentEntity = new ParamentEntity();
            
            paramentEntity.setEnumExcel(cellMapping.enumExcel());
            paramentEntity.setDescriptor(new PropertyDescriptor(field.getName(), entity));
            paramentEntity.setTableHeader(cellMapping.tableHeader());
            paramentEntity.setNull(cellMapping.isNull());
            
            map.put(Integer.valueOf(cellMapping.cellNum()), paramentEntity);
            
        }
        if(map.size() < 1){
            throw new IllegalArgumentException(entity.getName()+"no cellMapping");
        }
        return map;
    }
    
    /**
     * @param instance2
     * @param cellStr
     * @param paramentEntity
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    private <T> T setVlaure(T instance, Object value, ParamentEntity paramentEntity) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        paramentEntity.getDescriptor().getWriteMethod().invoke(instance, new Object[]{paramentEntity.getEnumExcel().checkField(value)});
        return instance;
    }

    /**
     * @param cell
     * @return
     */
    private String getCellValue(HSSFCell cell) {
        String value = null;
        
        switch (cell.getCellType()) {
        case 1://字符串
            if(cell.getRichStringCellValue() == null){
                value = null;
                break;
            }else{
                value = cell.getRichStringCellValue().toString();
                break;
            }
        case 0://数值型
            long dd = Double.valueOf(cell.getNumericCellValue()).longValue();
            if(DateUtil.isCellDateFormatted(cell)){
                Date date = DateUtil.getJavaDate(dd);
                value = com.fulan.common.utils.DateUtil.formatDate(date, "yyyy-MM-dd");
                break;
            }
            value = String.valueOf(dd);
            break;
        case 2://公式型
            value = String.valueOf(cell.getCellFormula());
            break;
        case 3://空值
            value = null;
            break;
        case 4://布尔值
            value = String.valueOf(cell.getBooleanCellValue());
            break;

        default:
            value = null;
        }
        return value;
    }

}
