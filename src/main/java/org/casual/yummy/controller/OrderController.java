package org.casual.yummy.controller;

import org.casual.yummy.utils.ResultMsg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @PostMapping
    public ResultMsg submitOrder(@RequestBody Map param) {
        return null;
    }
}
