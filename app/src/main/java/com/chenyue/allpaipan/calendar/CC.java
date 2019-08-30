package com.chenyue.allpaipan.calendar;

/**
 * CC stands for Calendar Constants
 * These Constants are recorded in chinese historical astronomical literature
 */
public class CC {
    public static void initCC() {
        QB = jieya(qiS);
        SB = jieya(suoS);

    }

    public static String QB = "";
    /**
     * 气直线拟合参数
     */
    public final static double[] qiKB = {
            1640650.479938, 15.21842500, // -221-11-09 h=0.01709 古历·秦汉
            1642476.703182, 15.21874996, // -216-11-09 h=0.01557 古历·秦汉
            1683430.515601, 15.218750011, // -104-12-25 h=0.01560 汉书·律历志(太初历)平气平朔 回归年=365.25000
            1752157.640664, 15.218749978, //   85-02-23 h=0.01559 后汉书·律历志(四分历) 回归年=365.25000
            1807675.003759, 15.218620279, //  237-02-22 h=0.00010 晋书·律历志(景初历) 回归年=365.24689
            1883627.765182, 15.218612292, //  445-02-03 h=0.00026 宋书·律历志(何承天元嘉历) 回归年=365.24670
            1907369.128100, 15.218449176, //  510-02-03 h=0.00027 宋书·律历志(祖冲之大明历) 回归年=365.24278
            1936603.140413, 15.218425000, //  590-02-17 h=0.00149 随书·律历志(开皇历) 回归年=365.24220
            1939145.524180, 15.218466998, //  597-02-03 h=0.00121 随书·律历志(大业历) 回归年=365.24321
            1947180.798300, 15.218524844, //  619-02-03 h=0.00052 新唐书·历志(戊寅元历)平气定朔 回归年=365.24460
            1964362.041824, 15.218533526, //  666-02-17 h=0.00059 新唐书·历志(麟德历) 回归年=365.24480
            1987372.340971, 15.218513908, //  729-02-16 h=0.00096 新唐书·历志(大衍历,至德历) 回归年=365.24433
            1999653.819126, 15.218530782, //  762-10-03 h=0.00093 新唐书·历志(五纪历) 回归年=365.24474
            2007445.469786, 15.218535181, //  784-02-01 h=0.00059 新唐书·历志(正元历,观象历) 回归年=365.24484
            2021324.917146, 15.218526248, //  822-02-01 h=0.00022 新唐书·历志(宣明历) 回归年=365.24463
            2047257.232342, 15.218519654, //  893-01-31 h=0.00015 新唐书·历志(崇玄历) 回归年=365.24447
            2070282.898213, 15.218425000, //  956-02-16 h=0.00149 旧五代·历志(钦天历) 回归年=365.24220
            2073204.872850, 15.218515221, //  964-02-16 h=0.00166 宋史·律历志(应天历) 回归年=365.24437
            2080144.500926, 15.218530782, //  983-02-16 h=0.00093 宋史·律历志(乾元历) 回归年=365.24474
            2086703.688963, 15.218523776, // 1001-01-31 h=0.00067 宋史·律历志(仪天历,崇天历) 回归年=365.24457
            2110033.182763, 15.218425000, // 1064-12-15 h=0.00669 宋史·律历志(明天历) 回归年=365.24220
            2111190.300888, 15.218425000, // 1068-02-15 h=0.00149 宋史·律历志(崇天历) 回归年=365.24220
            2113731.271005, 15.218515671, // 1075-01-30 h=0.00038 李锐补修(奉元历) 回归年=365.24438
            2120670.840263, 15.218425000, // 1094-01-30 h=0.00149 宋史·律历志 回归年=365.24220
            2123973.309063, 15.218425000, // 1103-02-14 h=0.00669 李锐补修(占天历) 回归年=365.24220
            2125068.997336, 15.218477932, // 1106-02-14 h=0.00056 宋史·律历志(纪元历) 回归年=365.24347
            2136026.312633, 15.218472436, // 1136-02-14 h=0.00088 宋史·律历志(统元历,乾道历,淳熙历) 回归年=365.24334
            2156099.495538, 15.218425000, // 1191-01-29 h=0.00149 宋史·律历志(会元历) 回归年=365.24220
            2159021.324663, 15.218425000, // 1199-01-29 h=0.00149 宋史·律历志(统天历) 回归年=365.24220
            2162308.575254, 15.218461742, // 1208-01-30 h=0.00146 宋史·律历志(开禧历) 回归年=365.24308
            2178485.706538, 15.218425000, // 1252-05-15 h=0.04606 淳祐历 回归年=365.24220
            2178759.662849, 15.218445786, // 1253-02-13 h=0.00231 会天历 回归年=365.24270
            2185334.020800, 15.218425000, // 1271-02-13 h=0.00520 宋史·律历志(成天历) 回归年=365.24220
            2187525.481425, 15.218425000, // 1277-02-12 h=0.00520 本天历 回归年=365.24220
            2188621.191481, 15.218437494, // 1280-02-13 h=0.00015 元史·历志(郭守敬授时历) 回归年=365.24250
            2322147.76// 1645-09-21
    };

    public final static double[] suoKB = {
            1457698.231017, 29.53067166, // -721-12-17 h=0.00032 古历·春秋
            1546082.512234, 29.53085106, // -479-12-11 h=0.00053 古历·战国
            1640640.735300, 29.53060000, // -221-10-31 h=0.01010 古历·秦汉
            1642472.151543, 29.53085439, // -216-11-04 h=0.00040 古历·秦汉
            1683430.509300, 29.53086148, // -104-12-25 h=0.00313 汉书·律历志(太初历)平气平朔
            1752148.041079, 29.53085097, //   85-02-13 h=0.00049 后汉书·律历志(四分历)
            1807665.420323, 29.53059851, //  237-02-12 h=0.00033 晋书·律历志(景初历)
            1883618.114100, 29.53060000, //  445-01-24 h=0.00030 宋书·律历志(何承天元嘉历)
            1907360.704700, 29.53060000, //  510-01-26 h=0.00030 宋书·律历志(祖冲之大明历)
            1936596.224900, 29.53060000, //  590-02-10 h=0.01010 随书·律历志(开皇历)
            1939135.675300, 29.53060000, //  597-01-24 h=0.00890 随书·律历志(大业历)
            1947168.00//  619-01-2
    };
    public static String SB = "";

    public final static String[] numCn = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"}; //中文数字

    public final static String[] Gan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};

    public final static String[] Zhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    public final static String[] ShX = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    public final static String[] XiZ = {"摩羯", "水瓶", "双鱼", "白羊", "金牛", "双子", "巨蟹", "狮子", "处女", "天秤", "天蝎", "射手"};

    public final static String[] yxmc = {"朔", "上弦", "望", "下弦"}; //月相名称表

    public final static String[] jqmc = {"冬至", "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪"};

    public final static String[] ymc = {"十一", "十二", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十"}; //月名称,建寅

    public final static String[] rmc = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十", "卅一"};

    public final static String qiS = "FrcFs22AFsckF2tsDtFqEtF1posFdFgiFseFtmelpsEfhkF2anmelpFlF1ikrotcnEqEq2FfqmcDsrFor22FgFrcgDscFs22FgEe"
            + "FtE2sfFs22sCoEsaF2tsD1FpeE2eFsssEciFsFnmelpFcFhkF2tcnEqEpFgkrotcnEqrEtFermcDsrE222FgBmcmr22DaEfnaF22"
            + "2sD1FpeForeF2tssEfiFpEoeFssD1iFstEqFppDgFstcnEqEpFg11FscnEqrAoAF2ClAEsDmDtCtBaDlAFbAEpAAAAAD2FgBiBqo"
            + "BbnBaBoAAAAAAAEgDqAdBqAFrBaBoACdAAf1AACgAAAeBbCamDgEifAE2AABa1C1BgFdiAAACoCeE1ADiEifDaAEqAAFe1AcFbcA"
            + "AAAAF1iFaAAACpACmFmAAAAAAAACrDaAAADG0";
    public final static String suoS =  //  619-01-21开始16598个朔日修正表 d0=1947168
            "EqoFscDcrFpmEsF2DfFideFelFpFfFfFiaipqti1ksttikptikqckstekqttgkqttgkqteksttikptikq2fjstgjqttjkqttgkqt"
                    + "ekstfkptikq2tijstgjiFkirFsAeACoFsiDaDiADc1AFbBfgdfikijFifegF1FhaikgFag1E2btaieeibggiffdeigFfqDfaiBkF"
                    + "1kEaikhkigeidhhdiegcFfakF1ggkidbiaedksaFffckekidhhdhdikcikiakicjF1deedFhFccgicdekgiFbiaikcfi1kbFibef"
                    + "gEgFdcFkFeFkdcfkF1kfkcickEiFkDacFiEfbiaejcFfffkhkdgkaiei1ehigikhdFikfckF1dhhdikcfgjikhfjicjicgiehdik"
                    + "cikggcifgiejF1jkieFhegikggcikFegiegkfjebhigikggcikdgkaFkijcfkcikfkcifikiggkaeeigefkcdfcfkhkdgkegieid"
                    + "hijcFfakhfgeidieidiegikhfkfckfcjbdehdikggikgkfkicjicjF1dbidikFiggcifgiejkiegkigcdiegfggcikdbgfgefjF1"
                    + "kfegikggcikdgFkeeijcfkcikfkekcikdgkabhkFikaffcfkhkdgkegbiaekfkiakicjhfgqdq2fkiakgkfkhfkfcjiekgFebicg"
                    + "gbedF1jikejbbbiakgbgkacgiejkijjgigfiakggfggcibFifjefjF1kfekdgjcibFeFkijcfkfhkfkeaieigekgbhkfikidfcje"
                    + "aibgekgdkiffiffkiakF1jhbakgdki1dj1ikfkicjicjieeFkgdkicggkighdF1jfgkgfgbdkicggfggkidFkiekgijkeigfiski"
                    + "ggfaidheigF1jekijcikickiggkidhhdbgcfkFikikhkigeidieFikggikhkffaffijhidhhakgdkhkijF1kiakF1kfheakgdkif"
                    + "iggkigicjiejkieedikgdfcggkigieeiejfgkgkigbgikicggkiaideeijkefjeijikhkiggkiaidheigcikaikffikijgkiahi1"
                    + "hhdikgjfifaakekighie1hiaikggikhkffakicjhiahaikggikhkijF1kfejfeFhidikggiffiggkigicjiekgieeigikggiffig"
                    + "gkidheigkgfjkeigiegikifiggkidhedeijcfkFikikhkiggkidhh1ehigcikaffkhkiggkidhh1hhigikekfiFkFikcidhh1hit"
                    + "cikggikhkfkicjicghiediaikggikhkijbjfejfeFhaikggifikiggkigiejkikgkgieeigikggiffiggkigieeigekijcijikgg"
                    + "ifikiggkideedeijkefkfckikhkiggkidhh1ehijcikaffkhkiggkidhh1hhigikhkikFikfckcidhh1hiaikgjikhfjicjicgie"
                    + "hdikcikggifikigiejfejkieFhegikggifikiggfghigkfjeijkhigikggifikiggkigieeijcijcikfksikifikiggkidehdeij"
                    + "cfdckikhkiggkhghh1ehijikifffffkhsFngErD1pAfBoDd1BlEtFqA2AqoEpDqElAEsEeB2BmADlDkqBtC1FnEpDqnEmFsFsAFn"
                    + "llBbFmDsDiCtDmAB2BmtCgpEplCpAEiBiEoFqFtEqsDcCnFtADnFlEgdkEgmEtEsCtDmADqFtAFrAtEcCqAE1BoFqC1F1DrFtBmF"
                    + "tAC2ACnFaoCgADcADcCcFfoFtDlAFgmFqBq2bpEoAEmkqnEeCtAE1bAEqgDfFfCrgEcBrACfAAABqAAB1AAClEnFeCtCgAADqDoB"
                    + "mtAAACbFiAAADsEtBqAB2FsDqpFqEmFsCeDtFlCeDtoEpClEqAAFrAFoCgFmFsFqEnAEcCqFeCtFtEnAEeFtAAEkFnErAABbFkAD"
                    + "nAAeCtFeAfBoAEpFtAABtFqAApDcCGJ";

    private static String jieya(String s) { //气朔解压缩
        String o = "0000000000", o2 = o + o;
        s = s.replace("J", "00");
        s = s.replace("I", "000");
        s = s.replace("H", "0000");
        s = s.replace("G", "00000");
        s = s.replace("t", "02");
        s = s.replace("s", "002");
        s = s.replace("r", "0002");
        s = s.replace("q", "00002");
        s = s.replace("p", "000002");
        s = s.replace("o", "0000002");
        s = s.replace("n", "00000002");
        s = s.replace("m", "000000002");
        s = s.replace("l", "0000000002");
        s = s.replace("k", "01");
        s = s.replace("j", "0101");
        s = s.replace("i", "001");
        s = s.replace("h", "001001");
        s = s.replace("g", "0001");
        s = s.replace("f", "00001");
        s = s.replace("e", "000001");
        s = s.replace("d", "0000001");
        s = s.replace("c", "00000001");
        s = s.replace("b", "000000001");
        s = s.replace("a", "0000000001");
        s = s.replace("A", o2 + o2 + o2);
        s = s.replace("B", o2 + o2 + o);
        s = s.replace("C", o2 + o2);
        s = s.replace("D", o2 + o);
        s = s.replace("E", o2);
        s = s.replace("F", o);
        return s;
    }
}
