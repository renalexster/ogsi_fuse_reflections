package com.example.utils.reader;

public interface IExcelReader<T extends SampleBean> {
	public SampleBean read(String filePath, T entity) throws Exception;
}
