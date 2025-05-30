package com.cat.bean;

public enum EOperation {

    Add("add", "A"),

    Update("update", "U"),

    Delete("delete", "D"),

    View("view", "V"),

    History("history", "H");

    private String _value;

    private String _type;

    private EOperation(String type, String mime) {
        _type = type;
        _value = mime;
    }

    public String value() {
        return _value;
    }

    public String type() {
        return _type;
    }


}
