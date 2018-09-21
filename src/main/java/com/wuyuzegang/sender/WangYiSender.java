package com.wuyuzegang.sender;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.wuyuzegang.proj.DayEndPriceProj;

public class WangYiSender {
	private static Logger logger = Logger.getLogger(WangYiSender.class);
	public static final String SINAURLBEFORE = "http://quotes.money.163.com/service/chddata.html?code=";
	public static final String SINAURLAFTER = "&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";

	public List<DayEndPriceProj> send(String code, String start, String end) {
		List<DayEndPriceProj> list = new ArrayList();
		String urll = SINAURLBEFORE + code + "&start=" + start + "&end=" + end + SINAURLAFTER;
		logger.info("请求地址："+urll);
		File file = null;
		try {
			// 统一资源
			URL url=new URL(urll);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();
			URLConnection con = url.openConnection();
			InputStreamReader bin = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader reader = new BufferedReader(bin);
		    //装填list对象
			list = getProj(reader);
			bin.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		logger.info(code+"返回了"+list.size()+"条数据");
		return list;
	}
	
	private List<DayEndPriceProj> getProj(BufferedReader reader) {
		List<DayEndPriceProj> list = new ArrayList<DayEndPriceProj>();
		try {
		reader.readLine();
		String line = null;    
			while((line=reader.readLine())!=null){ 
				DayEndPriceProj proj = new DayEndPriceProj();
	            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分   
	            proj.setSecurityCode(item[1].replaceAll("'", ""));
	            proj.setSecurityName(item[2]);
	            proj.setOpenPrice(item[6]);
	            proj.setClosePrice(item[7]);
	            proj.setNowPrice(item[3]);//收盘价格
	            proj.setMaxPrice(item[4]);
	            proj.setMinPrice(item[5]);
	            proj.setSubmitTime(new Date());
	            proj.setDaataTime(getTime(item[0]));
	            proj.setNum(item[11]);
	            proj.setAlllimit(item[12]);
	            list.add(proj);
	            
	            logger.info(item[1].replaceAll("'", ""));
	            logger.info(item[2]);
	            logger.info(item[6]);
	            logger.info(item[7]);
	            logger.info(item[3]);//收盘价格
	            logger.info(item[4]);
	            logger.info(item[5]);
	            logger.info(new Date());
	            logger.info(getTime(item[0]));
	            logger.info(item[11]);
	            logger.info(item[12]);
			}
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//第一行信息，为标题信息，不用,如果需要，注释掉   
            return list;
        } 
	private Date getTime(String time) {
		String timeArray[] = time.split("[/]");
		String myTime = timeArray[0]+"-";
		for(int i =1;i<timeArray.length;i++) {
			String a = timeArray[i];
			if(a.length()<2) {
				a = "0"+a;
			}
			myTime =myTime+a+"-";
		}
		myTime=myTime.substring(0, myTime.length()-1);
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			logger.info("myTime:"+myTime);
			date = df.parse(myTime);
		} catch (ParseException e) {
			logger.info("时间出错");
			e.printStackTrace();
		}
		return date;
			
	}
	
}
