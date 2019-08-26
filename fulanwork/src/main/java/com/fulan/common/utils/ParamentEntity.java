/**
 * 
 */
package com.fulan.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;

/**
 * @author hjs
 *
 */
public class ParamentEntity {

    private EnumExcel enumExcel; 
    private PropertyDescriptor descriptor;
    private String tableHeader;
    private boolean isNull;
    private Type type;
    private String dateFormat;
    /**
     * @return the enumExcel
     */
    public EnumExcel getEnumExcel() {
        return enumExcel;
    }
    /**
     * @param enumExcel the enumExcel to set
     */
    public void setEnumExcel(EnumExcel enumExcel) {
        this.enumExcel = enumExcel;
    }
    /**
     * @return the descriptor
     */
    public PropertyDescriptor getDescriptor() {
        return descriptor;
    }
    /**
     * @param descriptor the descriptor to set
     */
    public void setDescriptor(PropertyDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    /**
     * @return the tableHeader
     */
    public String getTableHeader() {
        return tableHeader;
    }
    /**
     * @param tableHeader the tableHeader to set
     */
    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }
    /**
     * @return the isNull
     */
    public boolean isNull() {
        return isNull;
    }
    /**
     * @param isNull the isNull to set
     */
    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }
    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }
    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }
    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
}
