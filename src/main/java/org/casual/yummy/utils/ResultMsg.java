package org.casual.yummy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg {

    private String msg;

    private int code;

    @Override
    public boolean equals(Object o) {
        if (o instanceof ResultMsg)
            return code == ((ResultMsg) o).code;
        else return false;
    }
}
