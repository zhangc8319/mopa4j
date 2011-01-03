package com.foogaro.nosql.mopa;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class City extends ADocumentObject {

    private String code;
    private String name;

    public City() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
