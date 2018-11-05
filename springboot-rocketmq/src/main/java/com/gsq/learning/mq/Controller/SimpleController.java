package com.gsq.learning.mq.Controller;

import com.gsq.learning.mq.simple.AsyncProducer;
import com.gsq.learning.mq.simple.OnewayProducer;
import com.gsq.learning.mq.simple.OrderedProducer;
import com.gsq.learning.mq.simple.SyncProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/simple/sync")
    public Object sync() {
        try {
            syncProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/simple/async")
    public Object async() {
        try {
            asyncProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/simple/oneway")
    public Object oneway() {
        try {
            onewayProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }

    @GetMapping("/simple/order")
    public Object order() {
        try {
            orderedProducer.send();
        } catch (Exception e) {
            return "failed";
        }
        return "success";
    }
}
