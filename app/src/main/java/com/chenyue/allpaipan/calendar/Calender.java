package com.chenyue.allpaipan.calendar;

import java.util.ArrayList;

import static com.chenyue.allpaipan.calendar.AC.J2000;

/**
 * This class is for calculating the time of twenty-four terms(jieqi) of a year.
 * The core algorithm is from Shouxing calendar which is designed by
 */
public class Calender {
    // the jieqi in index 0 is reserved for dongzhi in previous yearp
    private ArrayList<NewDate> jieqiArray = new ArrayList<>(25);
    private static String[] tiangan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static String[] dizhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    public static String[] ganzhi = new String[60];
    private double rad = 180f / 3600f * Math.PI;
    private int leap;
    private String[] ym_str = new String[13];

    public Calender(int year) {
        CC.initCC();
        AC.initAC();
        MC.initMC();
        initGanzhi();
        // calculate all 24 terms(jieqi) within that year.
        updateJieqiArray(year);
    }

    public Calender(NewDate dateIn) {
        this(dateIn.getYear());
    }

    public static boolean isLeap(int year) {
        if (year <= 1582) {
            return year % 4 == 0;
        } else {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else return year % 4 == 0;
        }
    }

    public static boolean isLeap(NewDate dateIn) {
        return isLeap(dateIn.getYear());
    }

    public void updateJieqiArray(int year) {
        calcY(year);
    }


    private void initGanzhi() {

        for (int ganzhiIdx = 0; ganzhiIdx < 60; ganzhiIdx++) {
            int ganIdx = ganzhiIdx % 10;
            int zhiIdx = ganzhiIdx % 12;
            ganzhi[ganzhiIdx] = tiangan[ganIdx] + dizhi[zhiIdx];
        }
    }

    /**
     * Algorithm from Shouxing calendar.
     * This function calculate the exact time when the sun arrives at some certain degree of ecliptic longitude
     * E.g. the ecliptic longitude degree will be 0 when the time is Chunfen(Spring Equinox or Vernal), and will be 270 when the time is Dongzhi(Winter Solstice or midwinter)
     */
    public void calcY(int year) {
        double w, W;
        double[] ZQ = new double[28]; // 中气表
        double[] HS = new double[26]; // 日月合朔表(整日)
        double[] dx = new double[14];
        int[] ym = new int[14];
        int jd = dToInt((double) (year - 2000) * 365.2422 + 180);
        // 该年的气
        W = (jd - 355 + 183) / 365.2422 * 365.2422 + 355;  // 355是2000.12冬至,得到较靠近jd的冬至估计值
        if (calc(W, "气") > jd) W -= 365.2422;
        for (int i = 0; i < 25; i++) {
            ZQ[i] = calc(W + 15.2184 * i, "气"); // 25个节气时刻(北京时间),从冬至开始到下一个冬至以后
        }
        ZQ[25] = calc(W - 15.2, "气");
        ZQ[26] = calc(W - 30.4, "气"); // 补算二气,确保一年中所有月份的“气”全部被计算在内

        // 今年"首朔"的日月黄经差w
        w = calc(ZQ[0], "朔"); // 求较靠近冬至的朔日
        if (w > ZQ[0]) w -= 29.53;

        // 该年所有朔,包含14个月的始末
        for (int i = 0; i < 15; i++) {
            HS[i] = calc(w + 29.5306 * i, "朔");
        }

        //月大小
        leap = 0;
        for (int i = 0; i < 14; i++) {
            dx[i] = HS[i + 1] - HS[i]; //月大小
            ym[i] = i;  //月序初始化
        }

        // 无中气置闰法确定闰月,(气朔结合法,数据源需有冬至开始的的气和朔)
        if (HS[13] <= ZQ[24]) { // 第13月的月末没有超过冬至(不含冬至),说明今年含有13个月
            int i = 1;
            for (; HS[i + 1] > ZQ[2 * i] && i < 13; i++) ; //在13个月中找第1个没有中气的月份
            this.leap = i;
            for (; i < 14; i++) ym[i]--;
        }

        //名称转换(月建别名)
        for (int i = 0; i < 14; i++) {
            double Dm = HS[i] + J2000;
            int v2 = ym[i]; //Dm初一的儒略日,v2为月建序号
            String mc = CC.ymc[v2 % 12]; //月建对应的默认月名称：建子十一,建丑十二,建寅为正……
            if (Dm >= 1724360 && Dm <= 1729794)
                mc = CC.ymc[(v2 + 1) % 12]; //  8.01.15至 23.12.02 建子为十二,其它顺推
            else if (Dm >= 1807724 && Dm <= 1808699)
                mc = CC.ymc[(v2 + 1) % 12]; //237.04.12至239.12.13 建子为十二,其它顺推
            else if (Dm >= 1999349 && Dm <= 1999467)
                mc = CC.ymc[(v2 + 2) % 12]; //761.12.02至762.03.30 建子为正月,其它顺推
            else if (Dm >= 1973067 && Dm <= 1977052) {
                if (v2 % 12 == 0) mc = "正";
                if (v2 == 2) mc = "一";
            } //689.12.18至700.11.15 建子为正月,建寅为一月,其它不变

            if (Dm == 1729794 || Dm == 1808699) mc = "拾贰"; //239.12.13及23.12.02均为十二月,为避免两个连续十二月，此处改名

            ym_str[i] = mc;
        }
    }

    private double calc(double jd, String strQs) { //jd应靠近所要取得的气朔日,qs='气'时，算节气的儒略日
        jd += 2451545;
        int i;
        double D;
        String n;
        double[] B;
        int pc;
        if (strQs.equals("气")) {
            B = CC.qiKB;
            pc = 7;
        } else {
            B = CC.suoKB;
            pc = 14;
        }

        double f1 = B[0] - pc, f2 = B[B.length - 1] - pc, f3 = 2436935;

        if (jd < f1 || jd >= f3) { //平气朔表中首个之前，使用现代天文算法。1960.1.1以后，使用现代天文算法 (这一部分调用了qi_high和so_high,所以需星历表支持)
            if (strQs.equals("气"))
                return Math.floor(qi_high(Math.floor((jd + pc - 2451259) / 365.2422 * 24) * Math.PI / 12) + 0.5); //2451259是1999.3.21,太阳视黄经为0,春分.定气计算
            else
                return Math.floor(so_high(Math.floor((jd + pc - 2451551) / 29.5306) * Math.PI * 2) + 0.5); //2451551是2000.1.7的那个朔日,黄经差为0.定朔计算

        } else if (jd >= f1 && jd < f2) { //平气或平朔
            for (i = 0; i < B.length; i += 2) if (jd + pc < B[i + 2]) break;
            D = B[i] + B[i + 1] * Math.floor((jd + pc - B[i]) / B[i + 1]);
            D = Math.floor(D + 0.5);
            if (D == 1683460)
                D += 1; //如果使用太初历计算-103年1月24日的朔日,结果得到的是23日,这里修正为24日(实历)。修正后仍不影响-103的无中置闰。如果使用秦汉历，得到的是24日，本行D不会被执行。
            return D - 2451545;

        } else if (jd >= f2 && jd < f3) { //定气或定朔
            if (strQs.equals("气")) {
                D = Math.floor(qi_low(Math.floor((jd + pc - 2451259) / 365.2422 * 24) * Math.PI / 12) + 0.5); //2451259是1999.3.21,太阳视黄经为0,春分.定气计算
                n = CC.QB.substring((int) Math.floor((jd - f2) / 365.2422 * 24), 1); //找定气修正值
            } else {
                D = Math.floor(so_low(Math.floor((jd + pc - 2451551) / 29.5306) * Math.PI * 2) + 0.5); //2451551是2000.1.7的那个朔日,黄经差为0.定朔计算
                n = CC.SB.substring((int) Math.floor((jd - f2) / 29.5306), 1); //找定朔修正值
            }
            if (n.equals("1")) return D + 1;
            if (n.equals("2")) return D - 1;
            return D;
        }
    }

    private double so_low(double v) {
        return 0;
    }

    private double qi_low(double v) {
        return 0;
    }

    /**
     * 较高精度朔
     */
    private double so_high(double W) {
        double t = MS_aLon_t2(W) * 36525;
        t = t - dt_T(t) + 8 / 24;
        double v = ((t + 0.5) % 1) * 86400;
        if (v < 1800 || v > 86400 - 1800) t = MS_aLon_t(W) * 36525 - dt_T(t) + 8 / 24;
        return t;
    }

    /**
     * 已知月日视黄经差求时间,高速低精度,误差不超过600秒(只验算了几千年)
     */
    private double MS_aLon_t2(double W) {
        double t, v = 7771.37714500204;
        t = (W + 1.08472) / v;
        double L, t2 = t * t;
        t -= (-0.00003309 * t2 + 0.10976 * Math.cos(0.784758 + 8328.6914246 * t + 0.000152292 * t2) + 0.02224 * Math.cos(0.18740 + 7214.0628654 * t - 0.00021848 * t2) - 0.03342 * Math.cos(4.669257 + 628.307585 * t)) / v;
        L = M_Lon(t, 20) - (4.8950632 + 628.3319653318 * t + 0.000005297 * t * t + 0.0334166 * Math.cos(4.669257 + 628.307585 * t) + 0.0002061 * Math.cos(2.67823 + 628.307585 * t) * t + 0.000349 * Math.cos(4.6261 + 1256.61517 * t) - 20.5 / rad);
        v = 7771.38 - 914 * Math.sin(0.7848 + 8328.691425 * t + 0.0001523 * t * t) - 179 * Math.sin(2.543 + 15542.7543 * t) - 160 * Math.sin(0.1874 + 7214.0629 * t);
        t += (W - L) / v;
        return t;
    }

    /**
     * 月球经度计算,返回Date分点黄经,传入世纪数,n是项数比例
     */
    private double M_Lon(double t, int n) {
        return XL1_calc(0, t, n);
    }

    private double XL1_calc(int zn, double t, int n) { //计算月亮
        Object[] ob = MC.XL1.get(zn);
        int i, j, N, v = 0, tn = 1, c;
        double t2 = t * t, t3 = t2 * t, t4 = t3 * t, t5 = t4 * t, tx = t - 10;
        if (zn == 0) {
            v += (3.81034409 + 8399.684730072 * t - 3.319e-05 * t2 + 3.11e-08 * t3 - 2.033e-10 * t4) * rad; //月球平黄经(弧度)
            v += 5028.792262 * t + 1.1124406 * t2 + 0.00007699 * t3 - 0.000023479 * t4 - 0.0000000178 * t5;  //岁差(角秒)
            if (tx > 0) v += -0.866 + 1.43 * tx + 0.054 * tx * tx; //对公元3000年至公元5000年的拟合,最大误差小于10角秒
        }
        t2 /= 1e4;
        t3 /= 1e8;
        t4 /= 1e8;
        n *= 6;
        if (n < 0) n = ((double[]) ob[0]).length;
        for (i = 0; i < ob.length; i++, tn *= t) {
            double[] F = (double[]) ob[i];
            N = dToInt((double) n * F.length / ((double[]) ob[0]).length + 0.5);
            if (i > 0) N += 6;
            if (N >= F.length) N = F.length;
            for (j = 0, c = 0; j < N; j += 6)
                c += F[j] * Math.cos(F[j + 1] + t * F[j + 2] + t2 * F[j + 3] + t3 * F[j + 4] + t4 * F[j + 5]);
            v += c * tn;
        }
        if (zn != 2) v /= rad;
        return v;
    }

    /**
     * 已知月日视黄经差求时间
     */
    private double MS_aLon_t(double W) {
        double t, v = 7771.37714500204;
        t = (W + 1.08472) / v;
        t += (W - MS_aLon(t, 3, 3)) / v;
        v = M_v(t) - this.E_v(t);  //v的精度0.5%，详见原文
        t += (W - MS_aLon(t, 20, 10)) / v;
        t += (W - MS_aLon(t, -1, 60)) / v;
        return t;
    }

    /**
     * 月球速度计算,传入世经数
     */
    private double M_v(double t) {
        double v = 8399.71 - 914 * Math.sin(0.7848 + 8328.691425 * t + 0.0001523 * t * t); //误差小于5%
        v -= 179 * Math.sin(2.543 + 15542.7543 * t)  //误差小于0.3%
                + 160 * Math.sin(0.1874 + 7214.0629 * t)
                + 62 * Math.sin(3.14 + 16657.3828 * t)
                + 34 * Math.sin(4.827 + 16866.9323 * t)
                + 22 * Math.sin(4.9 + 23871.4457 * t)
                + 12 * Math.sin(2.59 + 14914.4523 * t)
                + 7 * Math.sin(0.23 + 6585.7609 * t)
                + 5 * Math.sin(0.9 + 25195.624 * t)
                + 5 * Math.sin(2.32 - 7700.3895 * t)
                + 5 * Math.sin(3.88 + 8956.9934 * t)
                + 5 * Math.sin(0.49 + 7771.3771 * t);
        return v;
    }

    private double MS_aLon(double t, int Mn, int Sn) { //月日视黄经的差值
        return M_Lon(t, Mn) + gxc_moonLon(t) - (E_Lon(t, Sn) + gxc_sunLon(t) + Math.PI);
    }

    private double gxc_moonLon(double t) {
        return -3.4E-6;
    } //月球经度光行差,误差0.07"

    private double S_aLon(double t, double n) {  //太阳视黄经
        return E_Lon(t, n) + nutationLon2(t) + gxc_sunLon(t) + Math.PI; //注意，这里的章动计算很耗时
    }

    /**
     * Algorithm from Shou Xing Calender
     * 已知太阳视黄经反求时间
     * this function calculate the exact time when the sun arrives at some certain degree of ecliptic longitude
     * E.g. the ecliptic longitude degree will be 0 when the time is Chunfen(Spring Equinox or Vernal), and will be 270 when the time is Dongzhi(Winter Solstice  midwinter)
     *
     * @param W 太阳视黄经 the ecliptic longitude degree of sun
     */
    private double S_aLon_t(double W) { //
        double t, v = 628.3319653318;
        t = (W - 1.75347 - Math.PI) / v;
        v = E_v(t); //v的精度0.03%，详见原文
        t += (W - S_aLon(t, 10)) / v;
        v = E_v(t); //再算一次v有助于提高精度,不算也可以
        t += (W - S_aLon(t, -1)) / v;
        return t;
    }

    private double E_Lon(double t, double n) {
        return XL0_calc(0, 0, t, n);  //地球经度计算,返回Date分点黄经,传入世纪数、取项数
    }

    private double XL0_calc(int xt, int zn, double t, double n) { //xt星体,zn坐标号,t儒略世纪数,n计算项数
        t /= 10; //转为儒略千年数
        double v = 0, n1, n2, n0, N;
        double tn = 1;
        double[] F = AC.XL0.get(xt);
        int pn = zn * 6 + 1;
        double N0 = F[pn + 1] - F[pn]; //N0序列总数
        for (int i = 0; i < 6; i++, tn *= t) {
            n1 = F[pn + i];
            n2 = F[pn + 1 + i];
            n0 = n2 - n1;
            if (n0 <= 0) continue;
            if (n < 0) N = n2;  //确定项数
            else {
                N = dToInt(3 * n * n0 / N0 + 0.5) + n1;
                if (i > 0) N += 3;
                if (N > n2) N = n2;
            }
            for (int j = (int) n1, c = 0; j < N; j += 3) {
                c += F[j] * Math.cos(F[j + 1] + t * F[j + 2]);
                v += c * tn;
            }
        }
        v /= F[0];
        if (xt == 0) { //地球
            double t2 = t * t, t3 = t2 * t; //千年数的各次方
            if (zn == 0) v += (-0.0728 - 2.7702 * t - 1.1019 * t2 - 0.0996 * t3) / rad;
            if (zn == 1) v += (+0.0000 + 0.0004 * t + 0.0004 * t2 - 0.0026 * t3) / rad;
            if (zn == 2) v += (-0.0020 + 0.0044 * t + 0.0213 * t2 - 0.0250 * t3) / 1000000;
        } else { //其它行星
            double dv = AC.XL0_xzb[(xt - 1) * 3 + zn];
            if (zn == 0) v += -3 * t / rad;
            if (zn == 2) v += dv / 1000000;
            else v += dv / rad;
        }
        return v;
    }

    private double nutationLon2(double t) {  //只计算黄经章动
        int i;
        double a, t2 = t * t, dL = 0;
        double[] B = AC.nutB;
        for (i = 0; i < B.length; i += 5) {
            if (i == 0) a = -1.742 * t;
            else a = 0;
            dL += (B[i + 3] + a) * Math.sin(B[i] + B[i + 1] * t + B[i + 2] * t2);
        }
        return dL / 100 / rad;
    }

    private double gxc_sunLon(double t) { //太阳光行差,t是世纪数
        double v = -0.043126 + 628.301955 * t - 0.000002732 * t * t; //平近点角
        double e = 0.016708634 - 0.000042037 * t - 0.0000001267 * t * t;
        return (-20.49552 * (1 + e * Math.cos(v))) / rad; //黄经光行差

    }

    private double E_v(double t) {
        double f = 628.307585 * t;
        return 628.332 + 21 * Math.sin(1.527 + f) + 0.44 * Math.sin(1.48 + f * 2) + 0.129 * Math.sin(5.82 + f) * t + 0.00055 * Math.sin(4.21 + f) * t * t;
    }

    /**
     * 高精度气
     * calculate the time of 24 terms with high accuracy
     *
     * @param W degree of ecliptic longitude of sun
     */
    private double qi_high(double W) {
        double t = S_aLon_t2(W) * 36525;
        t = t - dt_T(t) + 8f / 24f;
        double v = ((t + 0.5) % 1) * 86400;
        if (v < 1200 || v > 86400 - 1200) {
            t = S_aLon_t(W) * 36525 - dt_T(t) + 8f / 24f;
        }
        return t;
    }

    /**
     * 传入儒略日(J2000起算),计算TD-UT(单位:日)
     */
    private double dt_T(double t) {
        return dt_calc(t / 365.2425 + 2000) / 86400.0;
    }

    /**
     * 计算世界时与原子时之差,传入年
     *
     * @param y 年 year
     */
    private double dt_calc(double y) {
        double y0 = AC.dt_at[AC.dt_at.length - 2]; //表中最后一年
        double t0 = AC.dt_at[AC.dt_at.length - 1]; //表中最后一年的deltatT
        if (y >= y0) {
            double jsd = 31; //sjd是y1年之后的加速度估计。瑞士星历表jsd=31,NASA网站jsd=32,skmap的jsd=29
            if (y > y0 + 100) return dt_ext(y, jsd);
            double v = dt_ext(y, jsd);       //二次曲线外推
            double dv = dt_ext(y0, jsd) - t0;  //ye年的二次外推与te的差
            return v - dv * (y0 + 100 - y) / 100;
        }
    }

    /**
     * 已知太阳视黄经反求时间,高速低精度,最大误差不超过600秒
     */
    private double S_aLon_t2(double W) {
        double t, L, v = 628.3319653318;
        t = (W - 1.75347 - Math.PI) / v;
        t -= (0.000005297 * t * t + 0.0334166 * Math.cos(4.669257 + 628.307585 * t) + 0.0002061 * Math.cos(2.67823 + 628.307585 * t) * t) / v;
        t += (W - E_Lon(t, 8) - Math.PI + (20.5 + 17.2 * Math.sin(2.1824 - 33.75705 * t)) / rad) / v;
        return t;
    }

    public int dToInt(double d) {
        return (int) Math.floor(d);
    }

    /**
     * 二次曲线外推
     */
    private double dt_ext(double y, double jsd) {
        double dy = (y - 1820) / 100;
        return -20 + jsd * dy * dy;
    }

}
