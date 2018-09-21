package testMSS;

import java.security.MessageDigest;
	

public abstract class TestMD52 {
	
	public static void main(String[] args) {
		System.out.println(md5("EpeY8ranNEzT03lDN2Qf71cN8g2rkbYFapp_id100013authorization_code110id_code210504199009111616methodyjy.mgw.insurance.paytoken.authcodeperson_no256988si_type210100001user_name徐博强verv1EpeY8ranNEzT03lDN2Qf71cN8g2rkbYF"));
	}
	
	public static String md5(String content) {
//	    if (StringUtils.isBlank(content)) {
//	        return null;
//	    }
	    try {
	        byte[] b = content.getBytes();
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.reset();
	        md.update(b);
	        byte[] hash = md.digest();
	        StringBuffer outStrBuf = new StringBuffer(32);
	        for (int i = 0; i < hash.length; i++) {
	            int v = hash[i] & 0xFF;
	            if (v < 16) {
	                outStrBuf.append('0');
	            }
	            outStrBuf.append(Integer.toString(v, 16).toLowerCase());
	        }
	        return outStrBuf.toString().toUpperCase();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    return null;
	}
}
