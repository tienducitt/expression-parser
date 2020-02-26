package com.example.checkhibernatesave.indexperformance;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JarvisLockingService {

    @Autowired
    private JarvisCityToAssignRepository repository;

    private Logger logger = LoggerFactory.getLogger(JarvisLockingService.class);

    @Transactional
    public Optional<String> acquireNextAvailableCity() {
        return repository
            .getNextCityToAssign()
            .map(this::freshNextCycleDueTime);
    }

    public JarvisCityToAssign get(String cityCode) {
        return repository.getOne(cityCode);
    }

    private String freshNextCycleDueTime(JarvisCityToAssign cityToAssign) {
        cityToAssign.setNextCycleDueAt(calculateNextCycleDueAt(cityToAssign.getPeriod()));
        repository.save(cityToAssign);
        return cityToAssign.getCityCode();
    }

    private Date calculateNextCycleDueAt(int period) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, period);
        return calendar.getTime();
    }
}
