package com.yd.ecabinet.server.scan;

import com.yd.ecabinet.rfid.TagHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TidTagHandler implements TagHandler {
    private final TidRepository tidRepository;

    @Override
    public void handle(String tid) {
        tidRepository.save(tid);
    }
}
