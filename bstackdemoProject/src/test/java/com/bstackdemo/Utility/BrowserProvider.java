package com.bstackdemo.Utility; //helper class to provide browser instance for each thread in parallel execution

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserProvider {

	//all downloads (e.g. order receipt) go to <project>/downloads
	public static final String DOWNLOAD_DIR = System.getProperty("user.dir") + File.separator + "downloads";

	private static ThreadLocal<WebDriver> tdriver=new ThreadLocal<WebDriver>();


	public static WebDriver getDriver() {
		return tdriver.get();
	}
    public static WebDriver setDriver(String bname) {
    	new File(DOWNLOAD_DIR).mkdirs();

    	//download settings: save to DOWNLOAD_DIR without asking, don't open PDFs in the browser
    	Map<String, Object> prefs = new HashMap<>();
    	prefs.put("download.default_directory", DOWNLOAD_DIR);
    	prefs.put("download.prompt_for_download", false);
    	prefs.put("plugins.always_open_pdf_externally", true);

    	WebDriver driver;
		switch(bname.toLowerCase().trim()){
		case "chrome":
			ChromeOptions co = new ChromeOptions();
			co.setExperimentalOption("prefs", prefs);
			driver=new ChromeDriver(co); break;
		case "edge":
			EdgeOptions eo = new EdgeOptions();
			eo.setExperimentalOption("prefs", prefs);
			driver=new EdgeDriver(eo); break;
		case "firefox":
			FirefoxOptions fo = new FirefoxOptions();
			fo.addPreference("browser.download.folderList", 2);
			fo.addPreference("browser.download.dir", DOWNLOAD_DIR);
			fo.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
			fo.addPreference("pdfjs.disabled", true);
			driver=new FirefoxDriver(fo); break;
		default:throw new IllegalArgumentException("Unsupported browser: " + bname);
		}
		tdriver.set(driver);
		return getDriver();


    }

}
