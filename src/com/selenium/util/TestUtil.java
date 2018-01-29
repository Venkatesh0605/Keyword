package com.selenium.util;

import java.util.Hashtable;

public class TestUtil {
	
	public static Object [][] getData(String tcName,String sheetName,Xls_Reader xls)
	{
		int tcStartRow=1;
		while(!xls.getCellData(sheetName, 0, tcStartRow).equals(tcName)){
			tcStartRow++;
		}
		//System.out.println(tcStartRow);
		int colstartRow=tcStartRow+1;
		int cols=0;
		while(!xls.getCellData(sheetName, cols, colstartRow).equals("N")){
			cols++;
		}
		//System.out.println(cols);
		int dataStartRow=tcStartRow+2;
		int rows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRow+rows).equals("N")){
			rows++;
		}
		//System.out.println(rows);
		Hashtable<String,String> table=null;
		Object data[][]=new Object[rows][1];
		int index=0;
		for(int rNum=dataStartRow;rNum<dataStartRow+rows;rNum++){
			table=new Hashtable();
			for(int cNum=0;cNum<cols;cNum++){
				String key=xls.getCellData(sheetName, cNum, colstartRow);
				String value=xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
				//System.out.print(xls.getCellData(sheetName, cNum, rNum)+" ");
			}
			data[index][0]=table;
			index++;
			System.out.println();
		}
		return data;
		
	}
}
