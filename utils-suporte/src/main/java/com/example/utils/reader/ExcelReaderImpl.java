package com.example.utils.reader;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.example.utils.annotations.CellPosition;

public class ExcelReaderImpl implements IExcelReader<SampleBean> {

	public SampleBean read(String filePath, SampleBean entity) throws Exception {
		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator = spreadsheet.iterator();
		
		SampleBean template = (SampleBean) Class.forName(entity.getClass().getName()).newInstance();
		Class<?> classe = template.getClass();

		for (Field field : template.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(CellPosition.class)) {
				String metodoSet = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				Method metodo = classe.getMethod(metodoSet, field.getType());

				CellPosition pp = field.getAnnotation(CellPosition.class);
				
				int line = pp.line() - 1;
				int column = pp.column() - 1;
				
				try {
					
					if (field.getType() == String.class) {
						metodo.invoke(template, spreadsheet.getRow(line).getCell(column).getStringCellValue());
					} else if (field.getType() == Integer.class) {
						metodo.invoke(template, (int) spreadsheet.getRow(line).getCell(column).getNumericCellValue());
					} else if (field.getType() == Long.class) {
						metodo.invoke(template, (long) spreadsheet.getRow(line).getCell(column).getNumericCellValue());
					} else if (field.getType() == Date.class) {
						String pattern = pp.pattern();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						
						if (pattern!=null || !pattern.trim().isEmpty()) {
							sdf = new SimpleDateFormat(pattern);
						}
						
						try {
							metodo.invoke(template, (Date) spreadsheet.getRow(line).getCell(column).getDateCellValue());
						} catch (IllegalStateException e) {
							metodo.invoke(template, sdf.parse(spreadsheet.getRow(line).getCell(column).getStringCellValue()));
						}
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			} 

		}

		return template;
	}

}
