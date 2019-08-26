/**
 * 
 */
package com.fulan.common.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * c
 * @author hjs
 *
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMapping {

    /**
     * 导入从第几行开始解析
     * @return
     */
    public int beginRow() default 2;
    
    /**
     * 导出是否设置头部
     * @return
     */
    public boolean hasTableHeader() default true;
}
