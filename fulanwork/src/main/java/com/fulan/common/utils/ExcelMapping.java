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
     * ����ӵڼ��п�ʼ����
     * @return
     */
    public int beginRow() default 2;
    
    /**
     * �����Ƿ�����ͷ��
     * @return
     */
    public boolean hasTableHeader() default true;
}
