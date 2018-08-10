package com.hebg3.plugins.arrow;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.hebg3.plugins.arrow.utils.ConfigReader;
import com.hebg3.utils.LogConfiguration;

/**
 * 负责失败用例的重跑
 * @author Administrator
 *
 */

public class TestngRetry implements IRetryAnalyzer {
    static {
        try {
			LogConfiguration.initLog("TestngRetryPage_");
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
    private static Logger logger = LogManager.getLogger(TestngRetry.class);
    private int retryCount = 1;
    private static int maxRetryCount;

    static {
        ConfigReader config = ConfigReader.getInstance();
        maxRetryCount = config.getRetryCount();
        logger.info("RetryCount=" + maxRetryCount);
        logger.info("SourceDir=" + config.getSourceCodeDir());
        logger.info("SourceEncoding=" + config.getSrouceCodeEncoding());
    }

    public boolean retry(ITestResult result) {
        if (retryCount <= maxRetryCount) {
            String message = "Retry for： [" + result.getName() + "] on class [" + result.getTestClass().getName() + "] retry " + retryCount + " times";
            logger.info(message);
            Reporter.setCurrentTestResult(result);
            Reporter.log("RunCount=" + (retryCount + 1));
            retryCount++;
            return true;
        }
        return false;
        
    }

    public static int getMaxRetryCount() {
        return maxRetryCount;
    }

    public int getRetryCount() {
        return retryCount;
    }
}