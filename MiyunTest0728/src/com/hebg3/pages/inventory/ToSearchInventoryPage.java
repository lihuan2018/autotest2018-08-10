package com.hebg3.pages.inventory;

import org.openqa.selenium.By;

/**
 * 库存查询页面.
 * @author Young.
 *
 */
public class ToSearchInventoryPage {
	/**商品名称*/
	public static final By TSIP_INPUT_GOODNAME = By.id("goodName");
	/**仓库名称*/
	public static final By TSIP_SELECT_WAREHOUSE = By.id("warehouse");
	/**查询按钮*/
	public static final By TSIP_BUTTON_SEARCH = By.xpath("//*[@id='content']/div[1]/div[2]/button[1]");
	/**重置按钮*/
	public static final By TSIP_BUTTON_DEFAULT = By.xpath("//*[@id='content']/div[1]/div[2]/button[2]");
	
	/**选择显示列按钮*/
	public static final By TSIP_BUTTON_DROPDOWNTOGGLE = By.xpath("//*[@id='content']/div[2]/div[1]/div[1]/div/div/button");
	/**显示列_仓库名称_复选框*/
	public static final By TSIP_CHECKBOX_WAREHOUSENAME = By.xpath("//*[@id='content']/div[2]/div[1]/div[1]/div/div/ul/li[1]/label/input");
	/**显示列_仓库类型_复选框*/
	public static final By TSIP_CHECKBOX_WAREHOUSETYPE = By.xpath("//*[@id='content']/div[2]/div[1]/div[1]/div/div/ul/li[2]/label/input");
	
	/**合计库存数量*/
	public static final By TSIP_DATA_INVENTORYSUM = By.id("sum");
	/**合计成本价总额*/
	public static final By TSIP_DATA_PRICESUM = By.id("sumg");
	
	/**数据表单table Id*/
	public static final String TSIP_TABLE_DATA = "inventorySearchTable";
	/**数据表单中的列名*/
	public static final String[] TSIP_TABLE_HEAD = {"仓库名称","仓库类型","商品名称","商品类型","商品规格","是否赠品","供应商","单位","库存数量","成本价","采购价","采购价总额","成本价总额"};
	
}
