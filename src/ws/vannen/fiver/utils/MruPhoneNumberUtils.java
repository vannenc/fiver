package ws.vannen.fiver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ws.vannen.fiver.data.Contact.PhoneNumberType;

import android.text.TextUtils;

public class MruPhoneNumberUtils {
	
   public static String patternPhoneNumberConvert = "(?:^|(?<=\\+230)|(?<=\\+230 ))([0-9]{3})";
   public static String patternPhoneNumberRevertOldFormat = "(?:^|(?<=\\+230)|(?<=\\+230 ))5(?=[0-9]{3})";
   
   private static Pattern mauritianPhoneNumber = Pattern
			.compile("(\\+230|\\+230 )?[0-9]{3}( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern mauritianMobilePhoneNumber = Pattern
			.compile("(\\+230|\\+230 )?(4|7|2|8|9)[0-9]{2}( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern cellplusPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(25[0-9]|70[0-9]|75[0-9]|76[0-9]|77[0-9]|78[0-9]|79[0-9]|82[0-9]|875|876|877|878|90[0-9]|91[0-9]|92[0-9]|94[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern mtmlPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(29[0-9]|86[0-9]|871|95[0-9]|96[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern emtelPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(421|422|423|428|429|44[0-9]|472|473|474|475|476|477|478|479|49[0-9]|71[0-9]|72[0-9]|73[0-9]|74[0-9]|93[0-9]|97[0-9]|98[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
  
   private static Pattern cellplusConvertedPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(525[0-9]|570[0-9]|575[0-9]|576[0-9]|577[0-9]|578[0-9]|579[0-9]|582[0-9]|5875|5876|5877|5878|590[0-9]|591[0-9]|592[0-9]|594[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern mtmlConvertedPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(529[0-9]|586[0-9]|5871|595[0-9]|596[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern emtelConvertedPhoneNumber = Pattern
   .compile("(\\+230|\\+230 )?(5421|5422|5423|5428|5429|544[0-9]|5472|5473|5474|5475|5476|5477|5478|5479|549[0-9]|571[0-9]|572[0-9]|573[0-9]|574[0-9]|593[0-9]|597[0-9]|598[0-9])( |-)?[0-9]{2}( |-)?[0-9]{2}");
  
   
   
   
   private static Pattern mauritianProcessedMobilePhoneNumber = Pattern
		   .compile("(\\+230|\\+230 )?5[0-9]{3}( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
	public static final boolean isProcessedMobilePhoneNumber(CharSequence target) {

	    if (target != null || TextUtils.isEmpty(target) == false) {
	        Matcher numberMatcher = mauritianProcessedMobilePhoneNumber.matcher(target);
	        return numberMatcher.matches();
	    }

	    return false;
	}
	
	public static final PhoneNumberType detectPhoneNumber(CharSequence target){
		
	    if (target != null || TextUtils.isEmpty(target) == false) {
	    	
	    	//check cellplus pattern
	        Matcher numberMatcher = cellplusPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Cellplus;
	        }
	        
	        //check emtel pattern
	        numberMatcher = emtelPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Emtel;
	        }
	        
	        //check mtml pattern
	        numberMatcher = mtmlPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Mtml;
	        }
	        
	    }
	    
	    return PhoneNumberType.Unknown;
	}
	
	public static final PhoneNumberType detectConvertedPhoneNumber(CharSequence target){
		
	    if (target != null || TextUtils.isEmpty(target) == false) {
	    	
	    	//check cellplus pattern
	        Matcher numberMatcher = cellplusConvertedPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Cellplus;
	        }
	        
	        //check emtel pattern
	        numberMatcher = emtelConvertedPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Emtel;
	        }
	        
	        //check mtml pattern
	        numberMatcher = mtmlConvertedPhoneNumber.matcher(target);
	        if(numberMatcher.matches()){
	        	return PhoneNumberType.Mtml;
	        }
	        
	    }
	    
	    return PhoneNumberType.Unknown;
	}
	
	public static final boolean isMobilePhoneNumber(CharSequence target) {

	    if (target != null || TextUtils.isEmpty(target) == false) {
	        Matcher numberMatcher = mauritianMobilePhoneNumber.matcher(target);
	        return numberMatcher.matches();
	    }

	    return false;
	}
	
	public static final boolean isPhoneNumber(CharSequence target) {

	    if (target != null || TextUtils.isEmpty(target) == false) {
	        Matcher numberMatcher = mauritianPhoneNumber.matcher(target);
	        return numberMatcher.matches();
	    }

	    return false;
	}
}
