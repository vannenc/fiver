package ws.vannen.fiver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

public class MruPhoneNumberUtils {
	
   private static Pattern mauritianPhoneNumber = Pattern
			.compile("(\\+230|\\+230 )?[0-9]{3}( |-)?[0-9]{2}( |-)?[0-9]{2}");
   
   private static Pattern mauritianMobilePhoneNumber = Pattern
			.compile("(\\+230|\\+230 )?(4|7|2|8|9)[0-9]{2}( |-)?[0-9]{2}( |-)?[0-9]{2}");
	
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
