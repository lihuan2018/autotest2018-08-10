package com.hebg3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties; 

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 注：使用log4j 2.x时，需导入的包为log4j-core(核心)、log4j-api
 * 
 * 配置了log的输出等级，以及如何显示，如何输出，输出的日志保存位置等配置
 * @author Young
 *
 */
public class LogConfiguration {
	public static void initLog(String fileName) throws FileNotFoundException{
        //获取到模块名字
        String founctionName = getFunctionName(fileName);
        //声明日志文件存储路径以及文件名、格式
        //https://blog.csdn.net/chenhaotong/article/details/50615689 动态指定："'/'"改为"'\\'"
        final String logFilePath  = "./result/log/" + founctionName + "/" + fileName + ".log";
        
        final String logPath = "./result/log/"+founctionName;
        //Properties System = new Properties();
        
        
        //20180510：https://blog.csdn.net/lj15875569485/article/details/8685407
        //读取log4j2配置路径
        
        
        
        //配置日志输出的格式
        /**
        System.setProperty("log4j2.rootLogger","info, toConsole, toFile");
        System.setProperty("log4j2.appender.file.encoding","UTF-8" );
        System.setProperty("log4j2.appender.toConsole","org.apache.log4j.ConsoleAppender");
        System.setProperty("log4j2.appender.toConsole.Target","System.out");
        System.setProperty("log4j2.appender.toConsole.layout","org.apache.log4j.PatternLayout ");
        System.setProperty("log4j2.appender.toConsole.layout.ConversionPattern","[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");        
        System.setProperty("log4j2.appender.toFile", "org.apache.log4j.DailyRollingFileAppender");
        */
        System.setProperty("logFilePath", logFilePath);
        System.setProperty("logPath", logPath);
        System.setProperty("fileName", fileName);
        
        System.out.println(System.getProperty("logFilePath").toString());
        /**
        System.setProperty("log4j2.appender.toFile.append", "false");
        System.setProperty("log4j2.appender.toFile.Threshold", "info");
        System.setProperty("log4j2.appender.toFile.layout", "org.apache.log4j.PatternLayout");
        System.setProperty("log4j2.appender.toFile.layout.ConversionPattern", "[%d{yyyy-MM-dd HH:mm:ss}] [%p] %m%n");
        */
        //使配置生效
        
        /**https://blog.csdn.net/chenhaotong/article/details/50615689*/
        LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
        ctx.reconfigure();
        
        //使用DOM解析xml文件:https://blog.csdn.net/lz527657138/article/details/70591792
        DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();
        try {
        	DocumentBuilder b = a.newDocumentBuilder();
        	Document document = b.parse("./src/log4j2.xml");
        	NodeList booklist =document.getElementsByTagName("RollingFile");
        	for (int i = 0 ; i < booklist.getLength() ; i ++) {
        		Node book = booklist.item(i);
        		NamedNodeMap bookMap = book.getAttributes();
        		for (int j = 0 ; j < bookMap.getLength() ; j ++) {
        			Node node = bookMap.item(j);
        			System.out.print(node.getNodeName()+":");
        			System.out.println(node.getNodeValue());
        		}
        		NodeList childlist = book.getChildNodes();
        		for(int t = 0; t<childlist.getLength(); t++){
        			if(childlist.item(t).getNodeType() == Node.ELEMENT_NODE){
        				System.out.print(childlist.item(t).getNodeName()+":");
        				System.out.println(childlist.item(t).getTextContent());
        			}
        		}
        	}
        } catch (ParserConfigurationException e) { 
        	e.printStackTrace();
        } catch (SAXException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    
    /**取得模块名字*/
    public static String getFunctionName(String fileName){
        String functionName = null; 
        int firstUndelineIndex = fileName.indexOf("_"); 
        functionName = fileName.substring(0, firstUndelineIndex-4);
        return functionName;
    
}
}
