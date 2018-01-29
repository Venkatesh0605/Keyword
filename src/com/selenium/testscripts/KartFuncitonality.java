package com.selenium.testscripts;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.util.Keywords;
import com.selenium.util.TestUtil;

public class KartFuncitonality {
	
	//@Test(dataProvider="getAddItemtoKartdata")
	public void AddItemtoKart(Hashtable<String,String> table) throws Exception{
		if(table.get("Runmode").equals("N"))
			throw new SkipException("Test data set is skipped since runmode set to no");
		
		Keywords k=new Keywords();
		k.executeKeywords("Keywords2", "AddItemtoKart", table);
	}
	
	@Test(dataProvider="getRemoveIemFromKartdata")
	public void RemoveIemFromKart(Hashtable<String,String> table) throws Exception{
		if(table.get("Runmode").equals("N"))
			throw new SkipException("Test data set is skipped since runmode set to no");
		
		Keywords k=new Keywords();
		k.executeKeywords("Keywords2", "RemoveIemFromKart", table);
	}
	
	@DataProvider
	public Object[][] getAddItemtoKartdata(){
		return TestUtil.getData("AddItemtoKart", "Sheet2", Keywords.xls);
	}
	
	@DataProvider
	public Object[][] getRemoveIemFromKartdata(){
		return TestUtil.getData("RemoveIemFromKart", "Sheet2", Keywords.xls);
	}


}
