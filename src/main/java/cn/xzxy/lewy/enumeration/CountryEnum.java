package cn.xzxy.lewy.enumeration;

import lombok.Getter;

/**
 * 自定义枚举类
 */
public enum  CountryEnum {

    ONE(1, "GER"),
    TWO(2, "JAP"),
    THREE(3, "FRA"),
    FOUR(4, "ENG"),
    FIVE(5, "SPN"),
    SIX(6, "ICE");

    @Getter private Integer returnCode;
    @Getter private String returnText;

    CountryEnum(Integer returnCode, String returnText) {
        this.returnCode = returnCode;
        this.returnText = returnText;
    }

    // 提供一个根据code返回text的方法
    public static CountryEnum getTextByCode(int index) {
        CountryEnum[] enums = CountryEnum.values();
        for (CountryEnum element: enums) {
            if (index == element.getReturnCode()) {
                return element;
            }
        }
        return null;
    }
}
