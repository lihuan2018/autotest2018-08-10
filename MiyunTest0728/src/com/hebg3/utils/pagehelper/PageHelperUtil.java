package com.hebg3.utils.pagehelper;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.hebg3.pages.purchase.HomePage;
import com.hebg3.utils.SeleniumUtil;

public class PageHelperUtil {
	//提供本类中日志输出对象.
	public static Logger logger = LogManager.getLogger(PageHelperUtil.class.getName());
	
	
	/**
	 * 读取表单中指定位置的数据.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param frameId  页面名，如销售出库单查询、采购订单查询.
	 * @param tableId  表单Id，详见变量表.
	 * @param row  指定数据位于表单第几行.
	 * @param column  指定数据位于表单第几列.
	 * @return  表单中指定位置的数据.
	 */
	public static String getTableCell(SeleniumUtil seleniumUtil , String frameId , String tableId , int row , int column) {
		String text = null;
		//添加强制等待&显式等待，保证获取到的为刷新后数据，不会出现element is not attached to the page document元素失效报错. 2018-07-06 15:51
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.waitForElementToLoad(5, tableCell(seleniumUtil , frameId , tableId , row , column));
		//获取指定位置元素的text值.
		text = seleniumUtil.findElementBy(tableCell(seleniumUtil , frameId , tableId , row , column)).getText();
		return text;
	}
	/**
	 * 点击表单中指定位置的链接.
	 * 经测试，可正常点击链接进行跳转，如后续有不能跳转(点击空白处效果)现象，可增加tableCellLink方法，在tableCell的基础上，xpath加上“/a”，<br>
	 * 但在实际使用中，需注意：确认是可以点击的链接方可使用clickTableCell方法，否则会报错<br>
	 * 以上为测试采购入库单，经测试销售出库单，需增加tableCellLink方法.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param frameId  页面名，如销售出库单查询、采购订单查询.
	 * @param tableId  数据表单Id，推荐使用页面类定义的变量.
	 * @param row  指定数据位于表单第几行.
	 * @param column  指定数据位于表单第几列.
	 */
	public static void clickTableCell(SeleniumUtil seleniumUtil , String frameId , String tableId , int row , int column) {
		//点击指定位置元素
		seleniumUtil.click(tableCell(seleniumUtil , frameId , tableId , row ,column));
	}
	
	
	/**
	 * 数据对比，适用于单据提交前后的库存数、成本价对比.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param data1  单据提交前的库存数/成本价.
	 * @param data2  单据提交后的库存数/成本价.
	 * @param data3  正常情况下以上两个数据的差值("####.##"格式).
	 */
	public static void compareData(SeleniumUtil seleniumUtil , String data1 , String data2 , String data3) {
		//获取到的字符串data1、data2，去除其中的断位符号","
		//包含","的字符串，不能直接创建为BigDecimal对象
		//data3需要为“####.##”格式，否则会数据对比失败  2018-07-06 17:48
		String data_01 = data1.replace(",", "");
		String data_02 = data2.replace(",", "");
		String data_03 = data3.replace(",", "");//*[@id="orderTable"]/tbody/tr[1]/td[3]
		
		BigDecimal data_1 = new BigDecimal(data_01);  
		BigDecimal data_2 = new BigDecimal(data_02);
		BigDecimal data_3 = new BigDecimal(data_03);
		BigDecimal data_difference = data_1.subtract(data_2);  //两次查询结果的差值（data1 - data2)
		BigDecimal data_should = data_1.subtract(data_3);  //第二次查询结果应为该数值(data1 - data3)
		if (data_difference.equals(data_3)) {
			logger.info("数据对比成功，两条数据分别为：" + data1 + "/" + data2 + "，设定差值为：" + data3);
		} else {
			logger.error("数据对比失败，第一条数据为:" + data1 + ",第二条数据为：" + data2 + "，应为：" + data_should + "，设定差值为：" + data3);
			Assert.fail("数据对比失败，第一条数据为:" + data1 + ",第二条数据为：" + data2 + "，应为：" + data_should + "，设定差值为：" + data3);
		}
	}
	/**
	 * 对比单据填写数据&保存后的单据详情.  2018-07-15 22:58
	 * @param map_1
	 * @param map_2
	 */
	/**public static void compareMap(Map<String, String> map_1, Map<String, String> map_2) {
		// TODO Auto-generated method stub
		
		//2018-07-15 22:28  以下三种方法，后两种性能效率较高
		Iterator<String> iter1 = map_1.keySet().iterator();
		while (iter1.hasNext()) {
			String map_1_key = (String) iter1.next();
			System.out.println(map_1_key + "----" + map_1.get(map_1_key) + "-----" + map_2.get(map_1_key));
		}
		
		for(Map.Entry<String, String> entry1:map_1.entrySet()){
			String m1value = entry1.getValue() == null?"":entry1.getValue();
			String m2value = map_2.get(entry1.getKey())==null?"":map_2.get(entry1.getKey());
			System.out.println(m1value + "------" + m2value); 
		} 
		
		//  https://www.cnblogs.com/shininguang/p/5294157.html
		Iterator<Entry<String, String>> iter1 = map_1.entrySet().iterator();
        while(iter1.hasNext()){
        	Map.Entry<String, String> entry1 = (Entry<String, String>) iter1.next();
            String m1value = entry1.getValue() == null?"":entry1.getValue();
            String m2value = map_2.get(entry1.getKey())==null?"":map_2.get(entry1.getKey());
            
            System.out.println(m1value + "--------" + m2value);
            
            if (!m1value.equals(m2value)) {//若两个map中相同key对应的value不相等
            	//其他操作...
            	System.out.println("failure");
            } else {
            	System.out.println("equals");
            }
        }
	}*/
	

	/**
	 * 选择下拉选项——根据文本内容(SELECT属性为display:none)——适用于页面.
	 * 如果SELECT属性为可见，用seleniumUtil.java中同名方法.
	 * @param seleniumUtil
	 * @param by	SELECT标签的By值.
	 * @param id	SELECT标签的id.
	 * @param visibleText	(要选择的)下拉选项的文本内容.
	 */
	public static void selectByVisibleText(SeleniumUtil seleniumUtil , By by , String id , String visibleText) {
		//页面中选择SELECT时，添加显式等待.
		//seleniumUtil.waitForElementToLoad(5, by);  //显式等待报错，原因或为元素不可见(采购入库单) 2018-07-05 16:52
		//以上，显式等待报错的原因：by指向的是不可见的SELECT元素，需要改为可见元素的等待
		//更改为：等待第一个按钮加载，增加强制等待，之后进行下一步动作		//2018-07-10 16:50
		//个别页面对按钮进行了屏蔽，无法加载第一个tagName为button的元素，故改为table  2018-07-11 14:21
		//无法加载到table？？？  
		//暂改为loadPage 2018-07-11 16:40
		//seleniumUtil.waitForElementToLoad(5, By.tagName("table"));
		seleniumUtil.waitForPageLoading(6);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		selectByVisibleText_0(seleniumUtil , by , id , visibleText);
	}
	/**
	 * 选择下拉选项——根据文本内容(SELECT属性为display:none)——适用于弹框(模态/非模态).
	 * 如果SELECT属性为可见，用seleniumUtil.java中同名方法.
	 * @param seleniumUtil
	 * @param by	SELECT标签的By值.
	 * @param id	SELECT标签的id.
	 * @param visibleText	(要选择的)下拉选项的文本内容.
	 */
	public static void selectByVisibleText_model(SeleniumUtil seleniumUtil , By by , String id , String visibleText) {
		//弹窗中选择SELECT时，添加强制等待(显式等待无法找到元素).
		//报错原因与上一方法相同：selectByVisibleText  暂不解决  2018-07-10 16:52
		try {  //20180704 生成拣货单，使用waitForElementToLoad 报错，超时无法查找到元素；改为强制等待，即可
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		selectByVisibleText_0(seleniumUtil , by , id , visibleText);
	}

	/**
	 * 填写日期(日期输入栏一般为readonly属性，若不是readonly属性，需要重写方法).
	 * @param seleniumUtil
	 * @param by	日期input标签的By值.
	 * @param id	日期input标签的id.
	 * @param date	要填写的日期.
	 */
	public static void chooseDate(SeleniumUtil seleniumUtil , By by , String id , String date) {
		seleniumUtil.waitForElementToLoad(5, by);
		String js = "document.getElementById('" + id + "').removeAttribute('readonly')";
		seleniumUtil.executeJS(js);
		seleniumUtil.findElementBy(by).clear();
		seleniumUtil.findElementBy(by).sendKeys(date);
	}
	
	/**
	 * 获取表头和表单数据(目前仅限第一条数据)，并存入Map<String , String>;<br>
	 * 针对个别页面(如， 库存查询)，无法获取到表头内容，需要增加一个参数，直接取Page文件中定义好的表头；<br>
	 * 无法获取表头内容的原因：页面存在fixed-table-head、fixed-table-body，后者的table元素中，thead内容被前者所覆盖.
	 * @param seleniumUtil
	 * @param map  要储存数据的Map，一般在调用该方法前创建.
	 * @param tableId  该页面数据表单的tableId，建议调用页面类定义的元素.
	 * @param startNum  要获取数据的列数(从第几列开始).
	 * @param endNum  要获取数据的列数(到第几列结束).
	 * @author Young 2018-07-17 10:58
	 */
	public static void recordData(SeleniumUtil seleniumUtil , Map<String , String> map , String tableId , 
			int startNum , int endNum) {
		String head;
		String body;
		//为fixed-table-head中的table标签增加id属性
		String tableSetId = "document.getElementsByTagName('table')[0].setAttribute('id','fixedTableHeader')";
		seleniumUtil.executeJS(tableSetId);
		for (int num = startNum; num <= endNum ; num ++) {
			head = seleniumUtil.findElementBy(By.xpath("//*[@id='" + tableId + "']/thead/tr/th[" + num + "]/div[1]")).getText();
			body = seleniumUtil.findElementBy(By.xpath("//*[@id='" + tableId + "']/tbody/tr[1]/td[" + num + "]")).getText();
			//针对部分页面(大部分集中在查询页面)无法直接获取到表头内容，原因为：
			//页面存在fixed-table-head、fixed-table-body，后者的table元素中，thead内容被前者所覆盖.
			//此时从fixed-table-head中的table元素，取thead内容.
			if (StringUtils.isBlank(head)) {
				head = seleniumUtil.findElementBy(By.xpath("//*[@id='fixedTableHeader']/thead/tr/th[" + num + "]/div[1]")).getText();
			}
			map.put(head, body);
			logger.error(head + "-----" + body);
		}
	}
	
	/**
	 * Map比较的方法，2018-07-19 14:20
	 * @param map_1_key
	 * @param map_2_key
	 * @param map_1_value
	 * @param map_2_value
	 */
	public static void compareMap(String map_1_key , String map_2_key , String map_1_value , 
			String map_2_value) {
		if (map_1_value.equals(map_2_value) == false) {
			//不同页面表示相同意义的表头，对应的内容不同，有可能是数字保留位数不同(如20.00 & 20的区别)，需尝试转为BigDecimal格式再比较
			try {
				BigDecimal value_1 = new BigDecimal(map_1_value);
				BigDecimal value_2 = new BigDecimal(map_2_value);
				if (value_1.subtract(value_2).compareTo(value_2.subtract(value_1)) == 0) {
					//map_1_value == map_2_value , 不作操作
					logger.info(map_1_key + "(" + map_1_value + "，" + map_2_value + ")：对比成功！");
				} else {
					logger.error("数据对比失败：字段名(" + map_1_key + ")，字段值(" + map_1_value + "，" + map_2_value + ")");
					Assert.fail("数据对比失败：字段名(" + map_1_key + ")，字段值(" + map_1_value + "，" + map_2_value + ")");
				}
			} catch (NumberFormatException e) {
				logger.error("数据对比失败：字段名(" + map_1_key + ")，字段值(" + map_1_value + "，" + map_2_value + ")");
				Assert.fail("数据对比失败：字段名(" + map_1_key + ")，字段值(" + map_1_value + "，" + map_2_value + ")");
			}
			
		} else {
			//不同页面相同意义的表头，对应的内容相同，不做操作，直接输出日志
			logger.info(map_1_key + "(" + map_1_value + "，" + map_2_value + ")：对比成功！");
		} //if else 判断语句结束
	}
	/**
	 * 用于获取表单中指定位置的元素,页面table有Id属性才可以使用.
	 * @param seleniumUtil  SeleniumUtil的实例化对象，填入seleniumUtil即可.
	 * @param frameId  页面名，如销售出库单查询、采购订单查询.
	 * @param tableId  数据表单Id，推荐使用页面类定义的变量.
	 * @param row  指定数据位于表单第几行.
	 * @param column  指定数据位于表单第几列.
	 * @return  表单中指定位置元素的定位值(By类型).
	 */
	public static By tableCell(SeleniumUtil seleniumUtil , String frameId , String tableId , int row , int column) {
		//返回tableCell的By值
		String xpath = "//*[@id='" + tableId +"']/tbody/tr[" + row +"]/td[" + column + "]";
		By by = By.xpath(xpath);
		return by;
	}
	/**
	 * 内部调用，选择下拉选项——根据文本内容(SELECT属性为display:none).
	 * 如果SELECT属性为可见，用seleniumUtil.java中同名方法.
	 * @param seleniumUtil
	 * @param by	SELECT标签的By值.
	 * @param id	SELECT标签的id.
	 * @param visibleText	(要选择的)下拉选项的文本内容.
	 */
	static void selectByVisibleText_0(SeleniumUtil seleniumUtil , By by , String id , String visibleText) {
		String js = "document.getElementById('" + id + "').setAttribute('class','.block{display:block}')";
		try {  //拣货保存时，选择配送人，此处不添加等待，概率性报错：元素状态不可用
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		seleniumUtil.executeJS(js);
		Select sel = new Select(seleniumUtil.findElementBy(by));
		sel.selectByVisibleText(visibleText);
	}
}


