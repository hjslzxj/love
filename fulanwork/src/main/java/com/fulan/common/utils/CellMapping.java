/**
 * 
 */
package com.fulan.common.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hjs
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CellMapping {

    /**
     * 列的序号
     * @return
     */
    public abstract int cellNum();
    
    /**
     * 导出的列名
     * @return
     */
    public abstract String tableHeader();
    
    /**
     * 是否允许为空
     * @return
     */
    public boolean isNull() default true;
    
    /**
     * 时间格式
     * @return
     */
    public String dateFormat() default "yyyy-MM-dd";
    
    /**
     * 枚举类型
     * @return
     */
    public abstract EnumExcel enumExcel();
}
