package testMSS;

import java.math.BigDecimal;

public class Test {
	public static void main(String[] args) {
		System.out.println("6000123".substring(0, 2));
		String a = "sh603131,sh601198,sh603377,sh603136,sh603378,sh603133,sh601199,sh603139,sh603138,sh600090,sh600091,sh600092,sh600093,sh600094,sh603363,sh600095,sh603360,sh600096,sh600097,sh603366,sh600098,sh601186,sh603367,sh600099,sh601188,sh603123,sh603365,sh603128,sh603129,sh603126,sh603368,sh603127,sh603369,sh603108,sh603590,sh600080,sh600081,sh600082,sh600083,sh603110,sh600084,sh600085,sh600086,sh603113,sh603355,sh600087,sh603356,sh603598,sh600088,sh603111,sh603595,sh600089,sh601177,sh603596,sh603117,sh603359,sh601179,sh603118,sh603357,sh603599,sh603116,sh603358,sh603339,sh600070,sh600071,sh600072,sh603583,sh600073,sh601163,sh603580,sh600074,sh600075,sh603586,sh600076,sh603103,sh603345,sh603587,sh600077,sh603100,sh600078,sh601166,sh603101,sh603585,sh600079,sh601169,sh603106,sh603348,sh601168,sh603588,sh603105,sh603589,sh603328,sh603329,sh601390,sh600060,sh600061,sh603330,sh600062,sh600063";
		String b[] = a.split(",");
		System.out.println(b.length);
//		System.out.println(Long.valueOf("23.33"));
		System.out.println(toPrice("23.55"));
	}
	public static String toPrice(String price) {
		 BigDecimal   bd  =   new  BigDecimal(price);  
		 bd   =  bd.setScale(2,4);  
		 return bd.toString();
	}
	
}
