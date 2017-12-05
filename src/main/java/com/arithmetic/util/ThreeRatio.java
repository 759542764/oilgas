package com.arithmetic.util;

/**
 * <p>Description: 三比值法 .</p>
 * <p>Company: </p>
 * <p>Date: 2017/11/29 14:18</p>
 * <p>Copyright: 2016-2017 happylifeplat.com All Rights Reserved</p>
 *
 * @author hyq
 */
public class ThreeRatio {

    private static final int ML_THERMAL=1;
    private static final int H_THERMAL=2;
    private static final int L_DISCHARGE=3;
    private static final int H_DISCHARGE=4;
    private static final int THER_DISCH=5;
    private static final int CODELESS=10;
    private static final double defInfMin=0.00001;





    public static void main(String[] args) {
        double result[] = new double[500];
        double oridata[][] = new double[500][7];
        int m , i;

    }


    public int imp(double dH2,double dCH4,double dC2H6,double dC2H4,double dC2H2) {
        double m1, m2, m3;
        int diagnosis;
        if(Math.abs(dC2H4) <= defInfMin) dC2H4 = defInfMin;//保证分母不为零
        if(Math.abs(dH2) <= defInfMin) dH2 = defInfMin;//保证分母不为零
        if(Math.abs(dC2H6) <= defInfMin) dC2H6 = defInfMin;//保证分母不为零
        m1 = dC2H2/dC2H4;
        m2 = dCH4/dH2;
        m3 = dC2H4/dC2H6;

        //中低温过热判断
        if (m1 <= 0.1 && m2 > 0.1 && m2 <= 1 && m3 >1 && m3 <= 3)
        {
            diagnosis = ML_THERMAL;
        }
        else if (m1 <= 0.1 && m2 > 1 && m3 <= 3)
        {
            diagnosis = ML_THERMAL;
        }
        //高温过热判断
        else if (m1 <= 0.1 && m3 > 3)
        {
            diagnosis = H_THERMAL;
        }
        //低能放电判断
        else if (m1 <= 0.1 && m2 <= 0.1 && m3<= 1)
        {
            diagnosis = L_DISCHARGE;
        }
        else if (m1 > 0.1 && m1 <= 3 && m2 <= 1)
        {
            diagnosis = L_DISCHARGE;
        }
        //高能放电判断
        else if (m1 > 3 && m2 <= 1)
        {
            diagnosis = H_DISCHARGE;
        }
        //放电兼过热判断
        else if (m1 > 0.1 && m1 <= 3 && m2 > 1)
        {
            diagnosis = THER_DISCH;
        }
        else if (m1 > 3 && m2 > 1)
        {
            diagnosis = THER_DISCH;
        }
        //无编码
        else
        {
            diagnosis = CODELESS;
        }
        return diagnosis;
    }
}
