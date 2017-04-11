package utils;

import org.junit.Test;

import com.example.utils.reader.ExcelReaderImpl;
import com.example.utils.reader.IExcelReader;

import bean.Sample;

public class TestReader {
	@Test
	public void testReader() throws Exception {
		IExcelReader reader = new ExcelReaderImpl();
		Sample sample = new Sample();
		sample = (Sample) reader.read("src/test/resources/sample.xls", sample);
		
		System.out.println(sample.marshall());
	}
}
