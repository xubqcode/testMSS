package mavenone;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
 
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import com.alibaba.fastjson.JSON;
import com.mysql.fabric.xmlrpc.base.Array;
import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.proj.User;
import com.wuyuzegang.service.IComputeMyData;
import com.wuyuzegang.service.IDayEndPrice;
import com.wuyuzegang.service.ISecurityCode;
import com.wuyuzegang.service.IUserService;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
 
public class TestMybatis {
	private static Logger logger = Logger.getLogger(TestMybatis.class);
	
	
	@Resource
	private IUserService userService = null;
	@Resource
	private ISecurityCode securityCodeService = null;
	@Resource
	private IDayEndPrice dayEndPrice = null;
//	@Resource
//	private IComputeMyData computeMyData = null;
	
//	@Test
//	public void test1()
//	{
//		User user = userService.getUserById(1);
//		logger.info(JSON.toJSONString(user));
//	}
	
//	@Test
//	public void test2()
//	{
//		List<SecurityCodeProj> list = securityCodeService.selectAll();
//		for(int i =0;i<list.size();i++) {
//			SecurityCodeProj a = list.get(i);
////			logger.info(a.getSecurityCode()+":"+a.getSecurityName());
//		}
//	}
//	@Test
//	public void test3(){
//		Date date = new Date();
//		List <DayEndPriceProj> list = new ArrayList<DayEndPriceProj>();
//		DayEndPriceProj p1 = new DayEndPriceProj();
//		DayEndPriceProj p2 = new DayEndPriceProj();
////		p2.setId(1);
//		p1.setSecurityCode("23333");
//		p1.setSecurityName("ssss");
//		p1.setOpenPrice(toPrice("23.23"));
//		p1.setClosePrice(toPrice("23.23"));
//		p1.setNowPrice(toPrice("23.23"));
//		p1.setMaxPrice(toPrice("23.23"));
//		p1.setMinPrice(toPrice("23.23"));
//		p1.setSubmitTime(date);
//		p1.setDaataTime(date);
//		System.out.println("=============================================");
////		p2.setId(2);
//		p2.setSecurityCode("123333");
//		p2.setSecurityName("ssss");
//		p2.setOpenPrice(toPrice("123.23"));
//		p2.setClosePrice(toPrice("123.23"));
//		p2.setNowPrice(toPrice("123.23"));
//		p2.setMaxPrice(toPrice("123.23"));
//		p2.setMinPrice(toPrice("123.23"));
//		p2.setSubmitTime(date);
//		p2.setDaataTime(date);
//		list.add(p1);
//		list.add(p2);
//		int i=0;
//		DayEndPriceProj a = dayEndPrice.selectById(1234);
//		try{
//			i= dayEndPrice.insertBatch(list);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		System.out.println("test3:"+i);
//	}
//	@Test
//	public void test4(){
//		computeMyData.computeBuy("2018-08-31");
//	}
	@Test
	public void test5(){
		List<DayEndPriceProj> list = dayEndPrice.selectFiveAvg("2018-09-12 00:00:00", "2018-09-04 00:00:00");
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).getSecurityCode()+"====="+list.get(i).getFree1());
		}
	}
	
	
	
	public String toPrice(String price) {
		 BigDecimal   bd  =   new  BigDecimal(price);  
		 BigDecimal setScale = bd.setScale(BigDecimal.ROUND_HALF_DOWN);;  
		 return setScale.toString();
	}
}
