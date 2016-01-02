package com.libmanagement.service;

import com.libmanagement.entity.LabRoom;
import com.libmanagement.entity.LabRoomUsage;
import com.libmanagement.repository.LabRoomUsageRepository;
import javafx.scene.input.DataFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class LabRoomUsageService {

    private Log logger = LogFactory.getLog(LabRoomUsageService.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Autowired
    private LabRoomUsageRepository labRoomUsageRepository;

    public List<LabRoomUsage> findByDate(String labRoomId) {
        try {
            return labRoomUsageRepository.findByLabRoomId(labRoomId,DATE_FORMAT.parse(DATE_FORMAT.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String addUsage(LabRoomUsage data) {
        labRoomUsageRepository.save(data);
        return data.getId();
    }

    public void updateUsage(LabRoomUsage data){
        labRoomUsageRepository.save(data);
    }

    public List<LabRoomUsage> findByRoomId(String labRoomId){
        try {
            return labRoomUsageRepository.findByLabRoomId(labRoomId, DATE_FORMAT.parse("2000-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
