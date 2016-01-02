package com.libmanagement.service;

import com.libmanagement.entity.LabRoom;
import com.libmanagement.entity.User;
import com.libmanagement.repository.LabRoomRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class LabRoomService {
    private Log logger = LogFactory.getLog(LabRoomService.class);

    @Autowired
    private LabRoomRepository labRoomRepository;

    public List<LabRoom> getByType(Integer type) {
        return labRoomRepository.getByType(type);
    }

    public LabRoom findById(String id) {
        return labRoomRepository.findOne(id);
    }

    public List<LabRoom> listLabRooms() {
        return labRoomRepository.findAll();
    }

    public LabRoom findByFullName(String fullName) {
        String regEx="[0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(fullName);
        if (m.find()) {
            String department = fullName.substring(0, m.start());
            String roomNumber = fullName.substring(m.start());
            System.out.println(department + " " + roomNumber);
            List<LabRoom> temp = labRoomRepository.findByRoomNumber(department, roomNumber);
            if (temp.size() > 0) {
                return temp.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String addLabRoom(LabRoom temp) {
        if (temp.getType() == null) {
            temp.setType(LabRoom.TYPE_COMPUTER_SCIENCE);
        }

        if (temp.getManager() == null) {
            User defaultManager = new User();
            defaultManager.setId("5c5a8bbb0a2b4bd68a522e64e5b6f392");
            temp.setManager(defaultManager);
        }
        labRoomRepository.save(temp);
        return temp.getId();
    }

    public void updateLabRoom(LabRoom data) {
        List<LabRoom> temp = labRoomRepository.findByRoomNumber(data.getDepartment(), data.getRoomNumber());
        if(temp.size() > 0){
            LabRoom entity = temp.get(0);
            entity.setDescription(data.getDescription());
            entity.setType(data.getType());
            entity.setManager(data.getManager());
            labRoomRepository.save(entity);
        }
    }

    public boolean deleteLabRoom(List<String> ids) {
        List<LabRoom> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        labRoomRepository.delete(list);
        return true;
    }

    public boolean isValidLabRoom (LabRoom temp) {
        return true;
    }
}
