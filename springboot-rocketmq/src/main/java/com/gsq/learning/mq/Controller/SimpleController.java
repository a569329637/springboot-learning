package com.gsq.learning.mq.Controller;

import com.gsq.learning.mq.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class SimpleController {

    @Autowired
    private SyncProducer syncProducer;

    @Autowired
    private AsyncProducer asyncProducer;

    @Autowired
    private OnewayProducer onewayProducer;

    @Autowired
    private OrderedProducer orderedProducer;

    @Autowired
    private TransactionProducer transactionProducer;

    @GetMapping("/sync")
    public Object sync() {
        try {
            syncProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/async")
    public Object async() {
        try {
            asyncProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/oneway")
    public Object oneway() {
        try {
            onewayProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/order")
    public Object order() {
        try {
            orderedProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/transaction")
    public Object transaction(@RequestParam(value = "index",required = false) Integer index) {
        try {
            if (null == index) {
                transactionProducer.send();
            } else if (index == 1) {
                transactionProducer.send1();
            } else if (index == 2) {
                transactionProducer.send2();
            } else {
                return "index error";
            }
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }
}
