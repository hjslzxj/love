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
     * �е����
     * @return
     */
    public abstract int cellNum();
    
    /**
     * ����������
     * @return
     */
    public abstract String tableHeader();
    
    /**
     * �Ƿ�����Ϊ��
     * @return
     */
    public boolean isNull() default true;
    
    /**
     * ʱ���ʽ
     * @return
     */
    public String dateFormat() default "yyyy-MM-dd";
    
    /**
     * ö������
     * @return
     */
    public abstract EnumExcel enumExcel();
}
