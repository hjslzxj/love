/**
 * 
 */
package com.fulan.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;



/**
 * excel支持的枚举类
 * @author hjs
 *
 */
public enum EnumExcel {

    STRING{
        public String checkField(Object paramObject){
            if(paramObject == null){
                return null;
            }
            return paramObject.toString();
        }
    },INT{
        public Integer checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            return Integer.parseInt(paramObject.toString().trim());
        }
    },DATE{
        public Date checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            Date date = null;
            date = DateUtil.formatDate(paramObject.toString().trim(), "yyyy-MM-dd");
            return date;
        }
    },TIMESTAMP{
        public Date checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            Date date = null;
            date = DateUtil.formatDate(paramObject.toString().trim(), "yyyy-MM-dd HH:mm:ss");
            return date;
        }
    },BIGINTEGER{
        public Object checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            return new BigInteger(paramObject.toString().trim());
        }
    },LONG{
        public Object checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            return Long.parseLong(paramObject.toString().trim());
        }
    },SHORT{
        public Object checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            return Short.parseShort(paramObject.toString().trim());
        }
    },BIGDECIMAL{
        public Object checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            return new BigDecimal(paramObject.toString().trim());
        }
    },BIGDECIMAL_RATE{
        public Object checkField(Object paramObject){
            if(paramObject == null){
                return null;  
            }
            BigDecimal big = new BigDecimal(paramObject.toString().trim());
            return big.setScale(2, BigDecimal.ROUND_HALF_UP)+"%";
        }
    };
    
    public abstract Object checkField(Object object);
}
