package org.casual.yummy.utils;

import java.util.Random;

public class IDGenerator {

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)，数组顺序可进行调整增加反推难度，A用来补位因此此数组不包含A，共31个字符。
     */
    private static final char[] BASE = new char[]{'h', 'v', 'e', '8', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
            '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'w', 'y', 'L', 't', 'n', '6', 'b', 'g', 'q'};

    /**
     * A补位字符，不能与自定义重复
     */
    private static final char SUFFIX_CHAR = 'A';

    /**
     * 进制长度
     */
    private static final int BIN_LEN = BASE.length;

    /**
     * 生成id长度
     */
    private static final int CODE_LEN = 7;

    private static volatile long count = 0;


    public static synchronized String gen() {
        long id = count;
        count++;

        char[] buf = new char[BIN_LEN];
        int charPos = BIN_LEN;

        // 当id除以数组长度结果大于0，则进行取模操作，并以取模的值作为数组的坐标获得对应的字符
        while (id / BIN_LEN > 0) {
            int index = (int) (id % BIN_LEN);
            buf[--charPos] = BASE[index];
            id /= BIN_LEN;
        }

        buf[--charPos] = BASE[(int) (id % BIN_LEN)];
        // 将字符数组转化为字符串
        String result = new String(buf, charPos, BIN_LEN - charPos);

        // 长度过长则截取, 不足指定长度则随机补全
        int len = result.length();
        if (len > CODE_LEN)
            result = result.substring(0, CODE_LEN);
        else if (len < CODE_LEN) {
            StringBuilder sb = new StringBuilder();
            sb.append(SUFFIX_CHAR);
            Random random = new Random();
            // 去除SUFFIX_CHAR本身占位之后需要补齐的位数
            for (int i = 0; i < CODE_LEN - len - 1; i++) {
                sb.append(BASE[random.nextInt(BIN_LEN)]);
            }
            result += sb.toString();
        }
        return result;
    }

    /**
     * 解析出count<br/>
     * gen反向操作。
     *
     * @param id
     * @return
     */
    public static synchronized long id2count(String id) {
        char[] charArray = id.toCharArray();
        long result = 0L;
        for (int i = 0; i < charArray.length; i++) {
            int index = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (charArray[i] == BASE[j]) {
                    index = j;
                    break;
                }
            }
            if (charArray[i] == SUFFIX_CHAR) {
                break;
            }
            if (i > 0) {
                result = result * BIN_LEN + index;
            } else {
                result = index;
            }
        }
        return result;
    }
}
