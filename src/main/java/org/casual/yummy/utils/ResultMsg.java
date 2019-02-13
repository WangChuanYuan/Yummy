package org.casual.yummy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultMsg<T> {

    private String msg;

    private Code code;

    private T value;

    public ResultMsg() {
    }

    public ResultMsg(Code code) {
        this.msg = code.toString();
        this.code = code;
        this.value = null;
    }

    public ResultMsg(String msg, Code code) {
        this.msg = msg;
        this.code = code;
        this.value = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultMsg)
            return code == ((ResultMsg) o).code;
        else return false;
    }
}
