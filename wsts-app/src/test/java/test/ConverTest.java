package test;

import java.math.BigDecimal;

import com.haoyu.sip.core.utils.BeanUtils;

public class ConverTest {
	
	public static void main(String[] args) {
		Person p = new Person();
		p.setStudy(new BigDecimal(10));
		MPerson mp = new MPerson();
		
		BeanUtils.copyProperties(p, mp);
		
		System.out.println(mp.getStudy());
	}

}

class Person{
	private BigDecimal study;

	public BigDecimal getStudy() {
		return study;
	}

	public void setStudy(BigDecimal study) {
		this.study = study;
	}
	
	
}

class MPerson{
	private double study = 0l;

	public double getStudy() {
		return study;
	}

	public void setStudy(double study) {
		this.study = study;
	}
	
	
}
