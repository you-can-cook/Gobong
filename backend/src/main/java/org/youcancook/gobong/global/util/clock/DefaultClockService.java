package org.youcancook.gobong.global.util.clock;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultClockService implements ClockService {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
