package com.hebg3.pages.sale;

import org.openqa.selenium.By;

/**
 * 拣货单生成_预览页面
 * @author Administrator
 *
 */
public class ToPrintPicking {
	/**按仓库  单选框*/
	public static final By TPP_INPUT_BYWAREHOUSE = By.id("typeChack_0");
	/**按配送人  单选框*/
	public static final By TPP_INPUT_BYUSER = By.id("typeChack_2");
	/**选择配送人*/
	public static final By TPP_SELECT_USER = By.id("dispatchingUser");
	/**保存按钮*/
	public static final By TPP_BUTTON_SAVE = By.id("btnPickingSave");  //btnPickingSave
}
