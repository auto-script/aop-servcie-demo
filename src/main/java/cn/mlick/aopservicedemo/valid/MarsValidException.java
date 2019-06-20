package cn.mlick.aopservicedemo.valid;

/**
 * @author lixiangxin
 * @date 2018/12/6 11:15
 **/
public class MarsValidException extends RuntimeException {

    public MarsValidException(String msg) {
        super(msg);
    }


    public MarsValidException(Throwable e) {
        super(e);
    }
}
