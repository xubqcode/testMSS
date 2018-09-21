package com.wuyuzegang.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.HalfPastFourteenPriceProj;
import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;
import com.wuyuzegang.proj.TimespamProj;
import com.wuyuzegang.sender.SinaSender;
import com.wuyuzegang.sender.SohuSender;
import com.wuyuzegang.service.IDayEndPrice;
import com.wuyuzegang.service.IHalfPastFourteenPrice;
import com.wuyuzegang.service.ISecurityCode;
import com.wuyuzegang.service.IThirtyAverageValueBefore;
import com.wuyuzegang.service.ITimespam;

@Controller
@RequestMapping("/getSecurityNowPrice")
public class GetSecurityNowPrice {
	private static Logger logger = Logger.getLogger(GetSecurityNowPrice.class);
	public static Map<String, String> AllSecurityCodeMap = Collections.synchronizedMap(new HashMap());
	public static Map<String, String> SHSecurityCodeMap = Collections.synchronizedMap(new HashMap());
	public static Map<String, String> SZSecurityCodeMap = Collections.synchronizedMap(new HashMap());
	public static Map<String, String> CYSecurityCodeMap = Collections.synchronizedMap(new HashMap());
	public static final BigDecimal NUMUNIT = new BigDecimal(100);
	public static final BigDecimal PRICESUNIT = new BigDecimal(10000);
	// 引一个services要加一个@Resource
	@Resource
	private ISecurityCode sc;
	@Resource
	private IDayEndPrice dayEndPrice;
	@Resource
	private IHalfPastFourteenPrice hp;
	@Resource
	private IThirtyAverageValueBefore tavb;
	@Resource
	private ITimespam its;

	@RequestMapping("/getThirtyAveragevalue/db/{date1}")
	public void getThirtyAveragevalueDB(HttpServletRequest request, Model model, @PathVariable("date1") String date1)
			throws InterruptedException, ParseException {
		logger.info("getThirtyAveragevalueDB开始");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		ThirtyAverageValueBeforeProj proj = new ThirtyAverageValueBeforeProj();
		List<DayEndPriceProj> dayEndList = new ArrayList<DayEndPriceProj>();
		TimespamProj tsBefore30 = its.selectBeforeThrity(date1);
		TimespamProj beforeOpenTimePojt = its.selectYestoday(date1);
		logger.info("时间获取完毕");
		logger.info("tsBefore30"+tsBefore30.getTimeSpam());
		logger.info("beforeOpenTimePojt"+beforeOpenTimePojt.getTimeSpam());
		date1 = date1 + " 00:00:00";
		Date beforeOpenTime = beforeOpenTimePojt.getTimeSpam();
		logger.info("入参"+f.format(tsBefore30.getTimeSpam())+","+date1);
		dayEndList = dayEndPrice.selectTrightyAvgPrice(date1,f.format(tsBefore30.getTimeSpam()));
		logger.info("dayEndList公"+dayEndList.size()+"条");
		for (int i = 0; i < dayEndList.size(); i++) {
			DayEndPriceProj deproj = new DayEndPriceProj();
			deproj = dayEndList.get(i);
			if (Integer.parseInt(deproj.getFree2()) >= 30) {
				proj.setSecurityCode(deproj.getSecurityCode());
				proj.setThirtyAverageValue(new BigDecimal(deproj.getFree1()));
				proj.setSubmitTime(beforeOpenTime);
				int retrunNum = tavb.insert(proj);
				if (retrunNum > 0) {
					logger.info(deproj.getSecurityCode() + "入库成功" + retrunNum);
				}
			} else {
				logger.info(deproj.getSecurityCode() + "小于30不入库");
			}

		}
	}

	@RequestMapping("/getThirtyAveragevalue/sohu/{start}/{end}")
	public void getThirtyAveragevalue(HttpServletRequest request, Model model, @PathVariable("start") String start,
			@PathVariable("end") String end) throws InterruptedException, ParseException {
		// 加载股票代码
		int j = initSecurityCode();
		List<String> securityCodeList = new ArrayList<String>();
		logger.info("================获取上一日的30日均量,需要获取" + j + "个===================");
		//
		for (String key : SHSecurityCodeMap.keySet()) {
			securityCodeList.add(key);
		}
		for (String key : SZSecurityCodeMap.keySet()) {
			securityCodeList.add(key);
		}
		for (String key : CYSecurityCodeMap.keySet()) {
			securityCodeList.add(key);
		}

		for (int i = 0; i < securityCodeList.size(); i++) {
			ThirtyAverageValueBeforeProj proj = new ThirtyAverageValueBeforeProj();
			BigDecimal a = new BigDecimal(30);
			BigDecimal b = new BigDecimal(0);

			SohuSender sender = new SohuSender();
			String securityCode = securityCodeList.get(i);
			// param为搜狐返回的历史数据
			String param1 = sender.send(securityCode, start, end);
			/**
			 * 计算30日均值
			 * ["2018-08-30","8.10","8.03","-0.05","-0.62%","8.01","8.14","41525","3352.51","1.02%"],
			 * 日期，开盘价，盘终价格，涨跌差值，涨跌幅度，最低价格，最高价格，成交量(手)，总交易额(万元),换手率 一支股票的30的所有数据
			 */
			if (param1.split("hq").length < 2) {
				logger.info("异常数据" + securityCode + "返回报文为：" + param1);
				continue;
			}
			String param2 = param1.split("hq")[1];
			String param3 = param2.split("code")[0];
			param3 = param3.substring(3, param3.length());
			param3 = param3.substring(0, param3.length() - 3);
			param3 = param3.replaceAll("]", ",");
			logger.info("param3:" + param3);
			String param4[] = param3.split(",,");
			logger.info("param4.length:" + param4.length);
			for (int v = 0; v < param4.length; v++) {
				logger.info("param4[]:" + param4[v]);
				String param5[] = param4[v].split(",");
				for (int z = 0; z < param5.length; z++) {
					logger.info(" param5[]:" + param5[z]);
				}
				String price = param5[2].replaceAll("\"", "");
				b = b.add(new BigDecimal(price));
			}
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
			Date date = f.parse(end + " 10:00:00");
			proj.setSecurityCode(securityCode);
			logger.info("b:" + b + "|a:" + a);
			proj.setThirtyAverageValue(b.divide(a, 2, BigDecimal.ROUND_HALF_UP));
			proj.setSubmitTime(date);
			logger.info(securityCode + "的30日算法为：" + b + "除以" + a);
			logger.info(securityCode + "的30日均值为" + b.divide(a, 2, BigDecimal.ROUND_HALF_UP));
			int retrunNum = tavb.insert(proj);
			logger.info(securityCode + "入库成功" + retrunNum);
		}
	}

	@RequestMapping("/dayEndPrice")
	public void nowPrice(HttpServletRequest request, Model model) throws InterruptedException, ParseException {
		// 加载股票代码
		int j = initSecurityCode();
		logger.info("================获取现价===================");
		// 发送获取List，分60，00，30请求 http://hq.sinajs.cn/list=sz002649,sz002491
		String return60 = getNowPrice("sh", SHSecurityCodeMap);
		String return00 = getNowPrice("sz", SZSecurityCodeMap);
		String return30 = getNowPrice("sz", CYSecurityCodeMap);
		String returndata = return60 + return00 + return30;
		String returnNum[] = returndata.split(";");
		logger.info(">>>>>>>>>>>>>>>总返回现价信息为：" + returndata);
		logger.info("操作总数为：" + j);
		logger.info("返回总数为：" + returnNum.length);
		logger.info("最后一位应该为空：" + returnNum[returnNum.length - 1]);
		// 落地，先整理成一个List<DayEndPriceProj>
		// 每个数组元素样式应为：
		// var
		// hq_str_sz300709="精研科技,33.810,33.800,33.850,34.190,33.690,33.850,33.990,238384,8086664.880,8800,33.850,500,33.840,1100,33.830,600,33.810,1100,33.800,1700,33.990,1200,34.000,500,34.020,1000,34.030,500,34.040,2018-08-29,12:01:03,00"
		List<DayEndPriceProj> list = getList(returnNum);
		logger.info("准备落地" + list.size() + "条数据");
		int v = dayEndPrice.insertBatch(list);
		logger.info("落地条数为：" + v);
		logger.info("落地完毕");
	}

	@RequestMapping("/halfPastFourteenPrice")
	public void halfPastFourteenPrice(HttpServletRequest request, Model model)
			throws InterruptedException, ParseException {
		// 加载股票代码
		int j = initSecurityCode();
		logger.info("================获取现价===================");
		// 发送获取List，分60，00，30请求 http://hq.sinajs.cn/list=sz002649,sz002491
		String return60 = getNowPrice("sh", SHSecurityCodeMap);
		String return00 = getNowPrice("sz", SZSecurityCodeMap);
		String return30 = getNowPrice("sz", CYSecurityCodeMap);
		String returndata = return60 + return00 + return30;
		String returnNum[] = returndata.split(";");
		logger.info(">>>>>>>>>>>>>>>总返回现价信息为：" + returndata);
		logger.info("操作总数为：" + j);
		logger.info("返回总数为：" + returnNum.length);
		logger.info("最后一位应该为空：" + returnNum[returnNum.length - 1]);
		// 落地，先整理成一个List<DayEndPriceProj>
		// 每个数组元素样式应为：
		// var
		// hq_str_sz300709="精研科技,33.810,33.800,33.850,34.190,33.690,33.850,33.990,238384,8086664.880,8800,33.850,500,33.840,1100,33.830,600,33.810,1100,33.800,1700,33.990,1200,34.000,500,34.020,1000,34.030,500,34.040,2018-08-29,12:01:03,00"
		List<HalfPastFourteenPriceProj> list = getList2(returnNum);
		logger.info("准备落地" + list.size() + "条数据");
		int v = hp.insertBatch(list);
		logger.info("落地条数为：" + v);
		logger.info("落地完毕");
	}

	// -----------------------------------------------------以上是controller方法---------------------------------------------------------------------------
	public List<HalfPastFourteenPriceProj> getList2(String returnNum[]) throws ParseException {
		List<HalfPastFourteenPriceProj> list = new ArrayList<HalfPastFourteenPriceProj>();
		// 最后一位为空字符串
		StringBuffer errorData = new StringBuffer();
		for (int i = 0; i < returnNum.length - 1; i++) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HalfPastFourteenPriceProj p = new HalfPastFourteenPriceProj();
			String data = returnNum[i];
			String dataArray[] = data.split("=");
			logger.info(data);
			String head = dataArray[0];
			head = head.replaceAll("\r|\n", "");
			logger.info("leng1:" + (head.length() - 6));
			logger.info("leng1:" + head.length());
			String code = head.trim().substring(head.length() - 6, head.length());
			logger.info("code:" + code);
			String param[] = dataArray[1].split(",");
			if (param.length > 2) {
				int j = param.length;
				String daataTime = param[j - 3].trim() + " " + param[j - 2].trim();
				p.setSecurityCode(code);
				p.setSecurityName(param[0].replaceAll("\"", ""));
				p.setOpenPrice(param[1]);
				p.setClosePrice(param[2]);
				p.setNowPrice(param[3]);
				p.setMaxPrice(param[4]);
				p.setMinPrice(param[5]);
				p.setSubmitTime(date);
				p.setDaataTime(sdf.parse(daataTime));
				p.setNum(param[8]);
				p.setAlllimit(param[9]);
				list.add(p);
			} else {
				errorData.append(code + ",");
			}
		}
		logger.info("不合法数据有：" + errorData.toString());
		return list;
	}

	public List<DayEndPriceProj> getList(String returnNum[]) throws ParseException {
		List<DayEndPriceProj> list = new ArrayList<DayEndPriceProj>();
		// 最后一位为空字符串
		StringBuffer errorData = new StringBuffer();
		for (int i = 0; i < returnNum.length - 1; i++) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DayEndPriceProj p = new DayEndPriceProj();
			String data = returnNum[i];
			String dataArray[] = data.split("=");
			logger.info(data);
			String head = dataArray[0];
			head = head.replaceAll("\r|\n", "");
			logger.info("leng1:" + (head.length() - 6));
			logger.info("leng1:" + head.length());
			String code = head.trim().substring(head.length() - 6, head.length());
			logger.info("code:" + code);
			String param[] = dataArray[1].split(",");
			if (param.length > 2) {
				int j = param.length;
				String daataTime = param[j - 3].trim() + " " + param[j - 2].trim();
				p.setSecurityCode(code);
				p.setSecurityName(param[0].replaceAll("\"", ""));
				// logger.info("toPrice(param[1]):"+toPrice(param[1]));
				p.setOpenPrice(param[1]);
				p.setClosePrice(param[2]);
				p.setNowPrice(param[3]);
				p.setMaxPrice(param[4]);
				p.setMinPrice(param[5]);
				p.setSubmitTime(date);
				p.setDaataTime(sdf.parse(daataTime));
				p.setNum(param[8]);
				p.setAlllimit(param[9]);
				list.add(p);
			} else {
				errorData.append(code + ",");
			}
		}
		logger.info("不合法数据有：" + errorData.toString());
		return list;
	}

	public String toPrice(String price) {
		BigDecimal bd = new BigDecimal(price);
		bd = bd.setScale(2, 4);
		return bd.toString();
	}

	public int initSecurityCode() {
		int j = 0;
		logger.info("开始加载股票代码");
		List<SecurityCodeProj> list = sc.selectAll();
		for (int i = 0; i < list.size(); i++) {
			SecurityCodeProj a = list.get(i);
			AllSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
		}
		logger.info("==================开始过滤沪指股票代码==================");
		for (int i = 0; i < list.size(); i++) {
			SecurityCodeProj a = list.get(i);
			if ("60".equals(a.getSecurityCode().substring(0, 2))) {
				SHSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}

		}
		logger.info("==================开始过滤深指股票代码==================");
		for (int i = 0; i < list.size(); i++) {
			SecurityCodeProj a = list.get(i);
			if ("00".equals(a.getSecurityCode().substring(0, 2))) {
				SZSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("==================开始过滤创业板股票代码==================");
		for (int i = 0; i < list.size(); i++) {
			SecurityCodeProj a = list.get(i);
			if ("30".equals(a.getSecurityCode().substring(0, 2))) {
				CYSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("股票代码加载完成");
		return j;
	}

	/**
	 * 
	 * @param code
	 *            sh和sz
	 * @param map
	 *            股票代码列表
	 * @return
	 * @throws InterruptedException
	 */
	public String getNowPrice(String code, Map<String, String> map) throws InterruptedException {
		// 沪指
		int num = 1;
		String return100 = "";
		StringBuffer returnEnd = new StringBuffer();
		SinaSender sender = new SinaSender();
		StringBuffer look100 = new StringBuffer();
		List<String> list = new ArrayList();
		for (String key : map.keySet()) {
			look100.append(code).append(key).append(",");
			num++;
			if (num % 100 == 0) {
				num = 1;
				String a = new String(look100);
				a = a.substring(0, a.length() - 1);
				list.add(a);
				logger.info("==============look100:" + a);
				look100 = new StringBuffer();
			}

		}
		// 最后不到100的数
		String a = new String(look100);
		a = a.substring(0, a.length() - 1);
		list.add(a);
		logger.info("最后==============look100:" + a);
		logger.info(list.size());
		for (int i = 0; i < list.size(); i++) {
			Thread.sleep(5);
			return100 = sender.send(list.get(i));
			returnEnd.append(return100);
		}
		logger.info("待处理的返回数据：" + returnEnd.toString());
		return returnEnd.toString();
	}

	public String remove1(String param) {
		String retrunParam;
		retrunParam = param.replaceAll("\"", "");
		return retrunParam.trim();
	}
}
