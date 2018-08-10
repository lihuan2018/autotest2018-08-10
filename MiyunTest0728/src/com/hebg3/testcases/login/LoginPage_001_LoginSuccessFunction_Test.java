package com.hebg3.testcases.login;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hebg3.base.BaseParpare;
import com.hebg3.entity.Entity;
import com.hebg3.pages.purchase.HomePage;
import com.hebg3.pages.sale.ToSaleOutWarehouseFormPage;
import com.hebg3.pageshelper.inventory.ToSearchInventoryPageHelper;
import com.hebg3.pageshelper.purchase.Document_PurchaseHelper;
import com.hebg3.pageshelper.purchase.Document_PurchasePlantHelper;
import com.hebg3.pageshelper.purchase.ToSearchList_PurchasePlantHelper;
import com.hebg3.pageshelper.purchase.Document_PurchaseReturnHelper;
import com.hebg3.pageshelper.purchase.ToSearchList_PurchaseHelper;
import com.hebg3.pageshelper.sale.OfToMovePageHelper;
import com.hebg3.pageshelper.sale.ToSaleOutPickingHelper;
import com.hebg3.pageshelper.sale.ToSaleOutReturnPageHelper;
import com.hebg3.pageshelper.sale.ToSaleOutSearchListPageHelper;
import com.hebg3.pageshelper.sale.ToSaleOutWarehouseFormPageHelper;
import com.hebg3.utils.PropertiesDataProvider;
import com.hebg3.utils.pagehelper.JumpInto;
import com.hebg3.utils.pagehelper.PageHelperUtil;
import com.hebg3.pageshelper.sale.ToLossBillHelper;

@Test(groups={"觅云测试"})
public class LoginPage_001_LoginSuccessFunction_Test extends BaseParpare {
	Logger logger = LogManager.getLogger(LoginPage_001_LoginSuccessFunction_Test.class.getName());
	
	//设置提供数据的文件路径，修改测试站点时，应选择相应的数据文件路径(不同站点，可能商品名称等测试数据不同)
	String paramFilePath = "config/paramsProvider/csjxs.miyunplus.properties";
	//变量初始化，所有变量从.properties文件中取值(文件位置为paramFilePath)
	private String goodsName = PropertiesDataProvider.getTestData(paramFilePath , "goodsName");//商品名称
	private String goodsNum = PropertiesDataProvider.getTestData(paramFilePath , "goodsNum");//商品数量
	private String goodsUnit = PropertiesDataProvider.getTestData(paramFilePath , "goodsUnit");//商品单位
	private String differenceValue_0 = PropertiesDataProvider.getTestData(paramFilePath , "differenceValue_0");//差值_正
	private String differenceValue_1 = PropertiesDataProvider.getTestData(paramFilePath , "differenceValue_1");//差值_负
	private String warehouse = PropertiesDataProvider.getTestData(paramFilePath , "warehouse");//仓库名称
	private String inventory = PropertiesDataProvider.getTestData(paramFilePath , "inventory");//查询字段——库存数量
	private String supplier = PropertiesDataProvider.getTestData(paramFilePath , "supplier");//供应商
	private String handlerUser = PropertiesDataProvider.getTestData(paramFilePath , "handlerUser");//经手人
	private String customer = PropertiesDataProvider.getTestData(paramFilePath , "customer");//客户名称
	
	
	//@Test(groups = {"生成采购订单"} , priority = 0)
	@Test(groups = {"生成采购订单"})
	public void toPurchasePlantOrders() throws InterruptedException {
		Document_PurchasePlantHelper.typeOrderQuickly(seleniumUtil , goodsName, "1", goodsUnit);
	}
/*
	@Test(groups = {"临时","采购订单填写详情数据一致"})
	public void temporary() throws InterruptedException {
		Map<String, String> map_1 = Document_PurchasePlantHelper.typeOrderQuickly(seleniumUtil , goodsName, "1", goodsUnit);
		Map<String, String> map_2 = ToSearchList_PurchasePlantHelper.searchOrder(seleniumUtil);
		Document_PurchasePlantHelper.compareMap(seleniumUtil , map_1, map_2);
	}
*/
	@Test(groups = {"订单生成采购入库单"} , priority = 1)
	public void plantToPurchaseOrders() throws InterruptedException {
		ToSearchList_PurchasePlantHelper.generatePurchaseOrder(seleniumUtil, warehouse, handlerUser);
	}

	@Test(groups={"生成采购入库单","检查库存变化正确"} , priority = 2)
	public void toPurchaseOrders() throws InterruptedException {
		//ToPurchaseInWarehouseHelper.typeOrderQuickly(seleniumUtil, warehouse, hanlderUser, goodsName);
		JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		String a1 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		Document_PurchaseHelper.typeOrderQuickly(seleniumUtil , warehouse, handlerUser, goodsName, "1", goodsUnit);
		JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		String a2 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		PageHelperUtil.compareData(seleniumUtil, a1, a2, differenceValue_1);
	}
/*
	@Test(groups={"临时","采购入库单填写&详情数据一致"})
	public void toPurchaseOrdersCompare() throws InterruptedException {
		Map<String , String> map_1 = Document_PurchaseHelper.typeOrderQuickly(seleniumUtil , warehouse,
				handlerUser, goodsName, "1", goodsUnit);
		Map<String , String> map_2 = ToSearchList_PurchaseHelper.viewDetail(seleniumUtil);
		Document_PurchaseHelper.compareMap(map_1, map_2);
	}
*/
	@Test(groups={"生成采购退货单"} , priority = 3)
	public void toPurchaseReturn() throws InterruptedException {
		Document_PurchaseReturnHelper.typeOrderQuickly(seleniumUtil, supplier, warehouse, handlerUser, goodsName, "1", goodsUnit);
	}
	@Test(groups={"生成销售出库单"} , priority = 4)
	public void toSaleOrders() throws InterruptedException {
		JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		String a1 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		ToSaleOutWarehouseFormPageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, goodsName);
		//添加多条商品  ToSaleOutWarehouseFormPageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, goodsName , "1" , goodsUnit);
		JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		String a2 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		PageHelperUtil.compareData(seleniumUtil, a1, a2, differenceValue_0);
	}

	@Test(groups={"生成销售退货单"} , priority = 5)
	public void toSaleReturn() throws InterruptedException {
		ToSaleOutReturnPageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, handlerUser, goodsName, goodsNum, goodsUnit);
	}

/*
	@Test(groups={"生成拣货单"} , priority = 6)
	public void picking() throws InterruptedException {
		//ToSaleOutPickingHelper pick = new ToSaleOutPickingHelper();
		ToSaleOutWarehouseFormPageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, goodsName, "", 
				"", "", "", "", "", "");
		JumpInto.jumpInto(seleniumUtil , "销售", "生成拣货单");
		ToSaleOutPickingHelper.picking(seleniumUtil, handlerUser);
	}
*/
	@Test(groups={"生成报损单"})
	public void toLose() throws InterruptedException {
		Thread.sleep(2000);
		JumpInto.jumpInto(seleniumUtil, "库存", "库存查询");
		String a1 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		ToLossBillHelper.typeOrderQuickly(seleniumUtil, warehouse, handlerUser , goodsName , "1" , goodsUnit, 1);
		JumpInto.jumpInto(seleniumUtil, "库存", "库存查询");
		String a2 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		PageHelperUtil.compareData(seleniumUtil, a1, a2, differenceValue_0);
	}
	
	@Test(groups={"生成报溢单"})
	public void toOver() throws InterruptedException {
		Thread.sleep(2000);
		JumpInto.jumpInto(seleniumUtil, "库存", "库存查询");
		String a1 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		ToLossBillHelper.typeOrderQuickly(seleniumUtil, warehouse, handlerUser , goodsName , "1" , goodsUnit , 0);
		JumpInto.jumpInto(seleniumUtil, "库存", "库存查询");
		String a2 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		PageHelperUtil.compareData(seleniumUtil, a1, a2, differenceValue_0);
	}
	
	
	@Test(groups={"生成移库单"} )
	public void toMoveOrders() throws InterruptedException {
		//JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		//String a1 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		OfToMovePageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, goodsName);
		//添加多条商品  ToSaleOutWarehouseFormPageHelper.typeOrderQuickly(seleniumUtil, customer, warehouse, goodsName , "1" , goodsUnit);
		//JumpInto.jumpInto(seleniumUtil , "库存", "库存查询");
		//String a2 = ToSearchInventoryPageHelper.searchInventory(seleniumUtil, goodsName, warehouse, inventory);
		//PageHelperUtil.compareData(seleniumUtil, a1, a2, differenceValue_0);
	}

	
		/**库存查询页面测试
		PageHelperUtil.jumpInto(seleniumUtil, "库存", "库存查询");
		ToSearchInventoryPageHelper ts = new ToSearchInventoryPageHelper();
		String str1 = ts.searchDataByGoodName(seleniumUtil, goodsName, 1, 11);
		String str2 = ts.searchDataByGoodName(seleniumUtil, goodsName, 2, 11);
		String str3 = ts.getCostPrice(seleniumUtil, goodsName, 0);
		//String str4 = ts.getCostPrice(seleniumUtil, goodsName);
		String str5 = ts.getCostPrice(seleniumUtil, goodsName, 1);
		logger.info("成本价1：" + str1);
		logger.info("成本价2：" + str2);
		logger.info("成本单价：" + str3);
		//logger.info("成本价总额：" + str4);
		logger.info("成本价总额：" + str5);
		PageHelperUtil.compareData(seleniumUtil, str1, str2, (float) 95.49);
		*/
		/**销售出库单查询
		PageHelperUtil.jumpInto(seleniumUtil , "销售" , "销售出库单");
		ToSaleOutWarehouseFormPageHelper.selectCustomer(seleniumUtil , "杨世雄");
		ToSaleOutWarehouseFormPageHelper.addProduct("主仓库", goodsName, 1);
		*/
		//String string1 = ToSaleOutSearchListPageHelper.searchDataByOrderNum(seleniumUtil, "2018" , 3, 4);
		//String string2 = ToSaleOutSearchListPageHelper.searchDataByOrderNum(seleniumUtil, "2018" , 4, 4);
		//seleniumUtil.isTextCorrect(string1, string2);
	
	
}
