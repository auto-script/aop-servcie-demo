package cn.mlick.aopservicedemo.valid;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

/**
 * @author lixiangxin
 * @date 2019/1/10 15:15
 * 内置 公共方法
 **/
public class MarsValidComMethods {

    public boolean isNotEmpty(Object object) {

        System.out.println("===========================================================");
        return !StringUtils.isEmpty(object);
    }

    public boolean hasNoneErrors(BindingResult br) {
        if (br.hasErrors()) {
            MarsValidUtil.throwException(br.getFieldError() != null ?
                    br.getFieldError().getDefaultMessage() : "BindingResult null is not Find");
        }
        return true;
    }


}
