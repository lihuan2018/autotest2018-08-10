package com.hebg3.utils.pagehelper;

import org.openqa.selenium.By;

import com.hebg3.utils.SeleniumUtil;

public class DialogPanel {

	/**
	 * 商品选择页面：通过商品名选择商品.<br>
	 * 
	 * @param pageName
	 * 			indexDialogPanel/divId中"_addProduct-index"的前缀.
	 * @param productName
	 * 			要添加的商品名，尽量填写全称.
	 */
	public static void addProductByName(SeleniumUtil seleniumUtil , String pageName , String productName) {
		//商品名称输入框
		String xpathOfSearchGoodsName = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[1]/div[1]/form/div/section/div/label[2]/input";
		//查询按钮
		String xpathOfSearchGoodsBtn = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[1]/div[2]/button[1]";
		//String num = null;
		//表单中的商品名xpath路径
		String xpathOfGoodsName = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[1]/td[2]";
		//复选框
		String xpathOfCheck = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[1]/td[1]";		
		//跳转到默认frame,并等待选择客户页面加载出来,后期需改为显式等待
		seleniumUtil.outFrame();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfSearchGoodsName));
		//输入商品名，点击查询按钮
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsName)).clear();
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsName)).sendKeys(productName);
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchGoodsBtn)).click();
		//强制等待，之后等待元素加载，获取第一行的商品
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfGoodsName));
		String goodsName = seleniumUtil.getText(By.xpath(xpathOfGoodsName));
		/**
		//遍历对比表单中商品名与查询的商品名是否一致
		for (int i = 1 ; i <= 10 && goodsName != productName ; i ++) {
			num = String.valueOf(i);
			xpathOfGoodsName = "//*[id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[" + num + "]/td[2]";
			goodsName = seleniumUtil.getText(By.xpath(xpathOfGoodsName));
		}
		//当前页无相应商品
		if (num == "10") {
			logger.warn("该商品不在第一页，PageHelperUtil.addProductByName需在该警告处增加翻页查询操作");
		}
		//勾选复选框
		String xpathOfCheck = "//*[id='" + pageName + "_addProduct-index']/div/div/div[2]/div/div[2]/div[2]/div[1]/div[2]/div[2]/table/tbody/tr[" + num + "]/td[1]";
		//判断是否存在进入页面，复选框已被勾选的BUG
		if (seleniumUtil.findElementBy(By.xpath(xpathOfCheck)).isSelected() == true) {
			logger.error("BUG：进入选择商品页面，复选框已被勾选");
		}
		*/
		//清除复选框并选择(复选框不能被clear？？20180611)
		//seleniumUtil.findElementBy(By.xpath(xpathOfCheck)).clear();//20180611注释
		seleniumUtil.findElementBy(By.xpath(xpathOfCheck)).click();
		//点击确认按钮，关闭页面
		String inventoryConfirm = "//*[@id='" + pageName + "_addProduct-index']/div/div/div[3]/button[1]";
		seleniumUtil.findElementBy(By.xpath(inventoryConfirm)).click();//采购入库、销售出库选择商品，确认按钮id不同  2018-07-05 17:04
		
	}

	/**
	 * 客户选择页面：根据客户名选择客户.
	 * 默认选择查询结果的第一条客户记录，如有需求，可增加参数，以便选择指定的客户记录.
	 * @param pageName
	 * 			indexDialogPanel/divId中"_selCustomer-index"的前缀.
	 * @param customerName
	 * 			要选择的客户名，尽量填写全称.
	 */
	public static void selectCustomer(SeleniumUtil seleniumUtil , String pageName , String customerName) {
		//SeleniumUtil seleniumUtil = new SeleniumUtil();
		//客户名称输入框
		String xpathOfCustomerNameInput = "//*[@id='" + pageName + "_selCustomer-index']/div/div/div[2]/div/div/div[1]/div[1]/form/div/section[2]/div/label[2]/input";
		//查询按钮
		String xpathOfSearchCustomerBtn = "//*[@id='" + pageName + "_selCustomer-index']/div/div/div[2]/div/div/div[1]/div[2]/button[1]";
		//表单路径
		String xpathOfTable = "//*[@id='" + pageName + "_selCustomer-index']/div/div/div[2]/div/div/div[2]/div[1]/div[2]/div[2]/table";
		//第一个单选radio
		String xpathOfFirstRadio = xpathOfTable + "/tbody/tr[1]/td[1]/input";
		//确认按钮
		String xpathOfConfirm = "//*[@id='" + pageName + "_selCustomer-index']/div/div/div[3]/button[1]";
		
		//跳转到默认frame,并等待选择客户页面加载出来,后期需改为显式等待
		seleniumUtil.outFrame();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//输入用户名，点击查询按钮
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfCustomerNameInput));
		seleniumUtil.findElementBy(By.xpath(xpathOfCustomerNameInput)).sendKeys(customerName);
		seleniumUtil.findElementBy(By.xpath(xpathOfSearchCustomerBtn)).click();
		//选择客户或有隐患，若多个页面存在选择客户，直接按照id定位或不准确，应当按照xpath为佳
		//选择客户(如有需求，该方法可添加参数，以便选择第几行的查询结果，先默认为第一行的结果)
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**2018-07-10 11:39 
		 * xpath路径错误，当页面存在两个tab页，且两个tab页均包含selCustomer页，会出现第二次定位不到
		 * 原因：xpath路径没有从indexDialogPanel的子div开始定位，indexDialogPanel的子div包含超过1个该路径，且从上到下开始定位
		 * 		定位地址为第一个tab页的该位置元素
		 * 修改为：xpath路径从indexDialogPanel的子div开始定位，参考本方法变量：xpathOfCustomerNameInput*/
		
		seleniumUtil.waitForElementToLoad(5, By.xpath(xpathOfFirstRadio));
		seleniumUtil.click(By.xpath(xpathOfFirstRadio));
		seleniumUtil.click(By.xpath(xpathOfConfirm));
	}

}
