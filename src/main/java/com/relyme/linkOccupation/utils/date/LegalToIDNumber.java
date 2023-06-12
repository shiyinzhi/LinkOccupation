package com.relyme.linkOccupation.utils.date;

import java.util.Calendar;
import java.util.Date;

public class LegalToIDNumber {

    public static void main(String[] args) {
        String[] strIDNumberArr = {"53010119810602001x","330521197411044030","53010119810602007x"};
        for (int i = 0; i < strIDNumberArr.length; ++i){
            if (isLegal(strIDNumberArr[i])){
                System.out.println(strIDNumberArr[i] + "为正确的身份证号。");
                displayBirthDate(strIDNumberArr[i]);
            }
            else{
                System.out.println(strIDNumberArr[i] + "为错误身份证号。");
            }
        }
    }

    public static boolean isLegal(String strID){
        boolean flag = true;
        //判断输入的字符串是否为空
        if ((strID == null) || (strID.length() <= 0) || strID.equals("")){
            flag = false;
        }
        //判断输入的字符串长度是否为15或者18
        if (strID.length() != 15 && strID.length() != 18){
            flag = false;
        }
        //判断输入的字符串是否都为数字
        if (!isDigit(strID)){
            flag = false;
        }
        //判断身份证号的前两位是否正确
        if (!isCorrectFirstTwo(strID.substring(0,2))){
            flag = false;
        }
        //验证生日是否正确
        String strBirthDay = "";
        if (strID.length() == 15){
            strBirthDay = "19" + strID.substring(6,12);
        }
        else{
            strBirthDay = strID.substring(6,14);
        }
        if (!isCorrectBirthDay(strBirthDay)){
            flag = false;
        }
        //验证18位身份证号的校验码是否正确
        if (strID.length() == 18){
            if (!isCheckCode(strID)){
                flag = false;
            }
        }
        return flag;
    }

    public static String displayBirthDate(String strID){
        String strYear = "";
        String strMonth = "";
        String strDay = "";

        if (strID.length() == 15){
            strYear = "19" + strID.substring(6,8);
            strMonth = strID.substring(8,10);
            strDay = strID.substring(10,12);
        }

        if (strID.length() == 18){
            strYear = strID.substring(6,10);
            strMonth = strID.substring(10,12);
            strDay = strID.substring(12,14);
        }

        System.out.println("生日日期为：" + strYear + "年" + strMonth + "月" + strDay + "日");
        return strYear+"-"+strMonth+"-"+strDay;
    }

    public static boolean isCheckCode(String strID){
        // 每位加权因子
        final int arrWeight[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        // 第18位校检码
        final String strArrCheckCode[] = { "1", "0", "X", "9", "8", "7", "6", "5",
                "4", "3", "2" };
        //将身份证号前17位存入数组，进行下一步计算
        int[] arrID = new int[17];
        for (int i = 0; i < 17; ++i){
            arrID[i] = Integer.parseInt(strID.substring(i, i + 1));
        }
        int nSum = 0;
        for (int i = 0; i < 17; ++i){
            nSum += arrID[i] * arrWeight[i];
        }
        int nIdx = nSum % 11;
        String strLast = strID.substring(17);
        if (strArrCheckCode[nIdx].equals(strLast)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isCorrectBirthDay(String strDate){
        int nYear = Integer.parseInt(strDate.substring(0, 4));
        int nMonth = Integer.parseInt(strDate.substring(4, 6));
        int nDay = Integer.parseInt(strDate.substring(6, 8));
        //获取系统当前的日期，判断是否在当前日期之前
        Date curDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        if (nYear > cal.get(Calendar.YEAR)){
            return false;
        }
        //判断是否为合法月份
        if (nMonth < 1 || nMonth > 12){
            return false;
        }
        //判断是否为合法日期
        boolean bFlag = false;
        switch(nMonth){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (nDay >= 1 && nDay <= 31){
                    bFlag = true;
                }
                break;
            case 2:
                if ((((nYear % 4 == 0) && (nYear % 100 != 0))|| (nYear % 400 == 0))){
                    if (nDay >=1 && nDay <= 29){
                        bFlag = true;
                    }
                }
                else{
                    if (nDay >=1 && nDay <= 28){
                        bFlag = true;
                    }
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (nDay >= 1 && nDay <= 30){
                    bFlag = true;
                }
                break;
        }
        if (!bFlag){
            return false;
        }
        return true;
    }

    public static boolean isCorrectFirstTwo(String strID12){
        final String strArrCityCode[] = { "11", "12", "13", "14", "15", "21", "22",
                "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
                "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
                "64", "65", "71", "81", "82", "91" };
        boolean bFlag = false;
        for (String strIdx : strArrCityCode){
            if (strIdx.equalsIgnoreCase(strID12))
            {
                bFlag = true;
                break;
            }
        }
        if (bFlag){
            return true;
        }
        return false;
    }

    public static boolean isDigit(String strID){
        int nDigitCnt = 0;
        int nLength = (strID.length() == 15)? strID.length() : (strID.length() - 1);
        for (int i = 0; i < nLength; ++i){
            char cTmp = strID.charAt(i);
            if (cTmp >= '0' && cTmp <= '9'){
                ++nDigitCnt;
            }
        }
        if (nDigitCnt != nLength){
            return false;
        }
        else{
            return true;
        }
    }


}
