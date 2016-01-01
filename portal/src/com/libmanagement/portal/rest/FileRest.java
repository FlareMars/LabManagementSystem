package com.libmanagement.portal.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.common.utils.FileUtils;
import com.libmanagement.entity.*;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.ExperimentLessionService;
import com.libmanagement.service.ExperimentResourceService;
import com.libmanagement.service.ExperimentResultService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2015/10/21
 */
@Controller
@RequestMapping("/file")
public class FileRest extends RestBaseBean {

    private Log logger = LogFactory.getLog(FileRest.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Autowired
    private ExperimentResourceService experimentResourceService;

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private ExperimentLessionService lessionService;

    @RequestMapping("/teacherUpload")
    public @ResponseBody
    Result uploadFile(@RequestParam(value = "file") MultipartFile sourceFile,
                        @RequestParam(value = "fileName" ) String fileName,
                            @RequestParam(value = "experimentId") String id,
                                @RequestParam(value = "userId") String teacherId,
                                    @RequestParam(value = "fileType") Integer fileType) {
        Result result = new Result();
        result.setMessage("上传成功");
        result.setStatusCode(200);

        String path = "";
        if (sourceFile == null) {
            result.setStatusCode(210);
            result.setMessage("file is empty");
            return result;
        }

        try {
            byte[] bytes = sourceFile.getBytes();
            int index = fileName.lastIndexOf("\\");
            if (index != -1) {
                fileName = fileName.substring(index + 1);
            }
            path = FileUtils.saveFile(bytes, fileName);

        } catch (Exception e) {
            result.setStatusCode(210);
            result.setMessage("You failed to upload " + fileName + " => " + e.getMessage());
        }

        if (!path.equals("")) {
            ExperimentResource newResource = new ExperimentResource();
            newResource.setName(fileName);
            newResource.setExperimentId(id);
            newResource.setPath(path);
            newResource.setSize(sourceFile.getSize());
            newResource.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
            newResource.setUploaderId(teacherId);
            experimentResourceService.addResuource(newResource);
            result.put("dataId",newResource.getId());
        }

        return result;
    }

    @RequestMapping("/studentUpload")
    public @ResponseBody
    Result uploadHomework(@RequestParam(value = "file") MultipartFile sourceFile,
                      @RequestParam(value = "fileName" ) String fileName,
                      @RequestParam(value = "lessionId") String planId,
                      @RequestParam(value = "userId") String studentId,
                      @RequestParam(value = "fileType") Integer fileType) {
        Result result = new Result();
        result.setMessage("上传成功");
        result.setStatusCode(200);

        String path = "";
        if (sourceFile == null) {
            result.setStatusCode(210);
            result.setMessage("file is empty");
            return result;
        }

        try {
            byte[] bytes = sourceFile.getBytes();
            int index = fileName.lastIndexOf("\\");
            if (index != -1) {
                fileName = fileName.substring(index + 1);
            }
            path = FileUtils.saveFile(bytes, fileName);

        } catch (Exception e) {
            result.setStatusCode(210);
            result.setMessage("You failed to upload " + fileName + " => " + e.getMessage());
        }

        if (!path.equals("")) {
            String id = lessionService.findByPlanId(planId).getId();
            System.out.println(id);
            List<ExperimentResult> temp = experimentResultService.findByLessionAndStudent(id,studentId);
            ExperimentResult resultContainer;
            if (temp.size() > 0) {
                resultContainer = temp.get(0);

            } else {
                resultContainer = new ExperimentResult();
                resultContainer.setExperimentLessionId(id);
                resultContainer.setScore(0);
                StudentUser studentUser = new StudentUser();
                studentUser.setId(studentId);
                resultContainer.setTargetStudent(studentUser);
            }
            ExperimentResultFile resultFile = new ExperimentResultFile();
            resultFile.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
            resultFile.setSize(sourceFile.getSize());
            resultFile.setPath(path);
            resultFile.setName(fileName);
            resultContainer.setResultFile(resultFile);

            experimentResultService.addHomework(resultContainer);

            //课程作业接收数量增加(限制每个同学只能够上传一次作业)
            lessionService.addReceivedNum(id);
            result.put("dataId",resultContainer.getId());
        }

        return result;
    }

    @RequestMapping(value = "/downloadResource/{fileId}")
    public ResponseEntity<byte[]> downloadResource(@PathVariable String fileId) {
        ExperimentResource file = experimentResourceService.findById(fileId);
        System.out.println(file.getPath());

        HttpHeaders headers = new HttpHeaders();

        String fileName = "";
        try {
            fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("file", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return new ResponseEntity<>(FileUtils.toByteArray(file.getPath()),
                    headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/downloadHomeWork/{fileId}")
    public ResponseEntity<byte[]> downloadHomework(@PathVariable String fileId) {
        ExperimentResult resultEntity = experimentResultService.findById(fileId);
        ExperimentResultFile file = resultEntity.getResultFile();
        HttpHeaders headers = new HttpHeaders();

        String fileName = "";
        try {
            fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");
            System.out.println(fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.setContentDispositionFormData("file", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return new ResponseEntity<>(FileUtils.toByteArray(file.getPath()),
                    headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
