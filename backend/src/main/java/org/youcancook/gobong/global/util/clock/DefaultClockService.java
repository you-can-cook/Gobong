package org.youcancook.gobong.global.util.clock;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DefaultClockService implements ClockService {
    @Override
    public LocalDateTime localDateTimeNow() {
        return LocalDateTime.now();
    }

    @Override
    public Date dateNow() {
        return new Date();
    }
}
