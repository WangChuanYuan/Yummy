package org.casual.yummy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ResultMsg {

    private String msg;

    private Code code;

    public ResultMsg() {}

    public ResultMsg(Code code) {
        this.msg = code.toString();
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultMsg)
            return code == ((ResultMsg) o).code;
        else return false;
    }
}
