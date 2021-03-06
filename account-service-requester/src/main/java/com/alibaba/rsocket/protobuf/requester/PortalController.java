package com.alibaba.rsocket.protobuf.requester;

import com.alibaba.account.AccountProto2Service;
import com.google.protobuf.util.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class PortalController {
    @Autowired
    AccountProto2Service accountProtoService;

    @GetMapping(value = "/account/{id}", produces = "application/json")
    public Mono<String> showAccount(@PathVariable("id") Integer id) {
        return accountProtoService.findById(id).handle((account, sink) -> {
            try {
                sink.next(JsonFormat.printer().print(account));
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }
}
