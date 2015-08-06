package com.jlj.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jlj.model.Alarmrecord;
import com.jlj.model.Sensordata;



public class ExportExcelForAlarmRecord {
	 //表头-up
//    public static final String[] tableHeader = {"编号","所属传感器","报警时间","报警类型","报警值","联系人列表","报警返回"};
    public static final String[] tableHeader = {"项目名称","线路名称","网关名称","传感器编号","报警时间","报警类型","数值","发送手机","报警返回数据"};
    //创建工作本
    public static HSSFWorkbook demoWorkBook = new HSSFWorkbook();
    //创建表-up
    public static HSSFSheet demoSheet = demoWorkBook.createSheet("sensordateExcel");
    //表头的单元格个数目
    public static final short cellNumber = (short)tableHeader.length;
    //数据库表的列数-up
    public static final int columNumber = 9;
    /** *//**
     * 创建表头
     * @return
     */
    public static void createTableHeader()
    {
        HSSFHeader header = demoSheet.getHeader();
        header.setCenter("传感器报警记录导出数据");
        HSSFRow headerRow = demoSheet.createRow((short) 0);
        for(int i = 0;i < cellNumber;i++)
        {
            HSSFCell headerCell = headerRow.createCell((short) i);
            headerCell.setCellType(HSSFCell.CELL_TYPE_STRING);
            headerCell.setCellValue(tableHeader[i]);
        }
    }
    /** *//**
     * 创建行
     * @param cells
     * @param rowIndex
     */
    public static void createTableRow(List<String> cells,int rowIndex)
    {
        //创建第rowIndex行
        HSSFRow row = demoSheet.createRow(rowIndex);
        for(short i = 0;i < cells.size();i++)
        {
            //创建第i个单元格
            HSSFCell cell = row.createCell((short) i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(cells.get(i));
        }
    }
    
    /** *//**
     * 创建整个Excel表-up
     * @throws SQLException 
     *
     */
    public static void createExcelSheeet(List<Alarmrecord> alarmrecords)
    {
    	//生成表头
        createTableHeader();
        try {
        	for (int k = 0;k < alarmrecords.size(); k++) {
        		Alarmrecord alarmrecord = alarmrecords.get(k);
            	//用来装行中的每个单元格
    			List<String> list = new ArrayList<String>();
    			list.add(alarmrecord.getSensor().getGateway().getLine().getProject().getName());
    			list.add(alarmrecord.getSensor().getGateway().getLine().getName());
    			list.add(alarmrecord.getSensor().getGateway().getName());
    			list.add(alarmrecord.getSensor().getName());
    			list.add(DateTimeKit.getDateString(alarmrecord.getAlarmtime()));
    			
    			String typename = "";
    			switch (alarmrecord.getAlarmtype()) {
    			case 0:
    				typename = "其他";
    					break;
    				case 1:
    				typename = "温度";
    					break;	
    				case 2:
    				typename = "压力";
    					break;
    				case 3:
    				typename = "流量";
    					break;
    				case 4:
    				typename = "电池电压";
    					break;
    				case 5:
    				typename = "表面温度";
    					break;
    			default:
    				typename = "其他";
    				break;
    			}
    			list.add(typename);
    			
    			list.add(alarmrecord.getAlarmdata().toString());
    			list.add(alarmrecord.getPhones());
    			list.add(alarmrecord.getSendreturn());
    			
    			createTableRow(list,(k+1));
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    /** *//**
     * 导出表格
     * @param sheet
     * @param os
     * @throws IOException
     */
    public void exportExcel(HSSFSheet sheet,OutputStream os) throws IOException
    {
        sheet.setGridsPrinted(true);
        HSSFFooter footer = sheet.getFooter();
        footer.setRight("Page " + HSSFFooter.page() + " of " +
        HSSFFooter.numPages());
        demoWorkBook.write(os);
    }
    
    /*public static void main(String[] args) {
        String fileName = "D:/lowrent.xls";
         FileOutputStream fos = null;
            try {
            	ImportExcel pd = new ImportExcel();
                pd.createExcelSheeet(null);
                fos = new FileOutputStream(fileName);
                pd.exportExcel(demoSheet,fos);
                JOptionPane.showMessageDialog(null, "表格已成功导出到 : "+fileName);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "表格导出出错，错误信息 ："+e+"\n错误原因可能是表格已经打开。");
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }*/
    
	public static boolean exportExcel(String savePath,List<Alarmrecord> alarmrecords){
		
//		String fileName = "D:/soft/Tomcat6/webapps/tempsensor/temps.xls";
//		String fileName = savePath;
		FileOutputStream fos = null;
           try {
           	   ExportExcelForAlarmRecord pd = new ExportExcelForAlarmRecord();
               createExcelSheeet(alarmrecords);
               fos = new FileOutputStream(savePath);
               pd.exportExcel(demoSheet,fos);
               
           } catch (Exception e) {
               e.printStackTrace();
               return false;
           } finally {
               try {
                   fos.close();
               } catch (Exception e) {
                   e.printStackTrace();
                   return false;
               }
           }
           return true;
	}
	
}
