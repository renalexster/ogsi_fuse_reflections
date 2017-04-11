package bean;

import com.example.utils.annotations.CellPosition;
import com.example.utils.reader.SampleBean;

public class Sample extends SampleBean {
	private static final long serialVersionUID = 9117872101937713202L;
	
	@CellPosition(line=1, column=1)
	private String name;
	@CellPosition(line=1, column=2)
	private Integer age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
