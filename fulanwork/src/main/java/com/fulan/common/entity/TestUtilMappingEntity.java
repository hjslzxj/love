/**
 * 
 */
package com.fulan.common.entity;

import com.fulan.common.utils.CellMapping;
import com.fulan.common.utils.EnumExcel;
import com.fulan.common.utils.ExcelMapping;

/**
 * @author hjs
 *
 */
@ExcelMapping
public class TestUtilMappingEntity {

    @CellMapping(cellNum = 1, tableHeader = "客户姓名", enumExcel = EnumExcel.STRING)
    private String name;
}
