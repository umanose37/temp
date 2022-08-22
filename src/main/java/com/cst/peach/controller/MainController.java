package com.cst.peach.controller;

import com.cst.peach.constants.CommonConstants;
import com.cst.peach.model.DateModel;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class MainController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    /**
     * 프로그램 기동 후 실시간 시간 경과 처리
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Calendar cal = Calendar.getInstance();
        // 가동일시 초기화 2022-08-01 09:00
        cal.set(2022, 7, 1, 9, 0, 0);
        Date date = new Date(cal.getTimeInMillis());
        DateModel.setRunDts(date);
        Timer timer = new Timer();
        // 프로그램 시간 가산 task
        TimerTask runTimeTask = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                SimpleDateFormat transFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_BASIC);
                String runDtsStr = transFormat.format(DateModel.getRunDts());
                log.info(String.format("[시간경과 안내] %s", runDtsStr));

                // 1분 가산값 설정
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateModel.getRunDts());
                cal.add(Calendar.MINUTE, 1);
                Date modDate = new Date(cal.getTimeInMillis());
                DateModel.setRunDts(modDate);
            }
        };
        timer.schedule(runTimeTask, 1000, 1000);

    }



}
