package cn.sunxyz.bean.sample;

import java.util.List;

public class Child extends Parent {

	private List<String> hobbies;

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
}