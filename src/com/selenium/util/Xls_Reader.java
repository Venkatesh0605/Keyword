package com.selenium.util;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xls_Reader {
	
	public String path;
	public FileInputStream f=null;
	private XSSFWorkbook w=null;
	private XSSFSheet s=null;
	private XSSFRow r=null;
	private XSSFCell c=null;
	
	public Xls_Reader(String path){
		try{
			f=new FileInputStream(path);
			w=new XSSFWorkbook(f);
			s=w.getSheetAt(0);
			f.close();
			
		}catch(Exception e){
			
		}
	}
	
	public String getCellData(String sheetName,int colNum,int rowNum){
		
		try{
			s=w.getSheet(sheetName);
			r=s.getRow(rowNum-1);
			c=r.getCell(colNum);
			//if(c.getCellType()==c.CELL_TYPE_STRING)
				return c.getStringCellValue();
			//else if(c.getCellType()==c.CELL_TYPE_NUMERIC)
			//	return c.getNumericCellValue();
			
		}catch(Exception e){
			return s+"Sheet may not exist"+r+"nor such row exist"+c+"no such col exist";
		}
		
	}
	
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			s=w.getSheet(sheetName);
			r=s.getRow(0);
			int colNum=0;
			System.out.println(colName);
			for(int i=0;i<r.getLastCellNum();i++){
				if(r.getCell(i).getStringCellValue().equals(colName)){
					colNum=i; 
				}
			}
			
			r=s.getRow(rowNum-1);
			c=r.getCell(colNum);
			
			return c.getStringCellValue();
		}catch(Exception e){
			return s+"Sheet may not exist"+r+"nor such row exist"+c+"no such col exist";
		}
	}
	
	public int getRowcount(String sheetName){
		s=w.getSheet(sheetName);
		return s.getLastRowNum()+1;
	}

}
