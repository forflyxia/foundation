package cn.tcc.foundation.es.constant;

public enum SortTypeEnum {

    ASC("asc"),
    DESC("desc");

    private String value;

    SortTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
