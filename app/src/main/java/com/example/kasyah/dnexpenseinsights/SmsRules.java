package com.example.kasyah.dnexpenseinsights;

public enum SmsRules
{
    FOOD("((FRESH|RESTAURANT|FOOD|DINNER|LUNCH|MEAL|SNACK)s?)"),
    GROCERY("((MART)s?)"),
    HOTEL("((HOTEL)s?)"),
    FUEL("(FUEL|ENERGY|GAS|ELECTRICIY)s?"),
    MOVIE("((MOVIE)s?)"),
    BILLS("((PAID|RECHARGE FOR|DEDUCT|BILL)s?)"),
    OFFERS("((ENJOY|CASH BACK|CASHBACK|LOAN|VOUCHER|ENROL|ENROLL|REGISTER|OFFER|SPECIAL|FREE|INVEST|DISCOUNT|PAYBACK|BONUS|TRIP|SALE|GET|COVER)s?)"),
    REMINDERS("((CREDIT|FEEDBACK|REFUND|PAY)s?)");

    private String _rule;
    public static String msg_Filter_Rule = "((INR|RS|\\$)\\.?\\ *[0-9]+)";
    public static String msg_Amount_Rule = "([0-9]+)";

    SmsRules(String rule) {
        _rule = rule;
    }

    public String useRule() {
        return _rule;
    }
}
