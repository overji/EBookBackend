package com.overji.ebookbackend.serviceLayer.Implementation;

import com.overji.ebookbackend.serviceLayer.TimerRecordService;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimerRecordServiceImpl implements TimerRecordService {

    private long startTime;

    @Override
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public int endTimer() {
        long endTime = System.currentTimeMillis();
        return (int) (endTime - startTime);
    }
}