package cn.mlick.aopservicedemo.valid;

import org.springframework.util.StringUtils;

/**
 * @author create by lixiangxin at 2018/12/3 14:22
 **/
public class MarsValidUtil {

    public static void throwException(String message) {
        throw new MarsValidException(message);
    }

    public static void throwException(Throwable e) {
        throw new MarsValidException(e);
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new MarsValidException(message);
        }
    }

    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new MarsValidException(message);
        }
    }

}
