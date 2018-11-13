package com.example.kasyah.dnexpenseinsights;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsParser {

    public static Sms parse(Context context, String from, String body, String date) {
        Sms sms = new Sms();

        if (!body.isEmpty() && body!=null) {
            try {
                if(parseBody(sms, body))
                    sms.setSmsBody (body);
                else
                    return null;
            } catch (Exception e) {
                return null;
            }

        } else {
            return null;
        }

        if (!from.isEmpty() && from!=null) {
            sms.setSmsFrom(from);
        } else {
           return null;
        }

        DateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        Date smsDate = null;
        try {
            smsDate = formatter.parse(formatter.format(new Date(Long.valueOf(date))));
            sms.setSmsDateLong(Long.valueOf(date));
        } catch(Exception e) {
            return null;
        }
        sms.setSmsDate(smsDate);

        return sms;
    }

    public static boolean parseBody(Sms sms, String body) {
        try {
            String regex = SmsRules.msg_Filter_Rule;
            Pattern filterPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher filterMatcher = filterPattern.matcher(body);

            while (filterMatcher.find()) {

                for(SmsRules rule : SmsRules.values())
                {
                    Pattern rulePattern = Pattern.compile(rule.useRule(), Pattern.CASE_INSENSITIVE);
                    Matcher ruleMatcher = rulePattern.matcher(body);

                    while (ruleMatcher.find()) {
                        sms.setSmsType(rule.name());
                        Pattern amountPattern = Pattern.compile(SmsRules.msg_Amount_Rule);
                        Matcher amountMatcher = amountPattern.matcher(filterMatcher.group(1));

                        while (amountMatcher.find()) {
                            sms.setSmsAmount(Double.valueOf(amountMatcher.group(1)));
                            return true;
                        }
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e)   {
            return false;
        }
    }

}
