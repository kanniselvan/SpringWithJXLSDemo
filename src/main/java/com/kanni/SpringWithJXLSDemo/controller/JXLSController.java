package com.kanni.SpringWithJXLSDemo.controller;

import com.kanni.SpringWithJXLSDemo.model.ResponseData;
import com.kanni.SpringWithJXLSDemo.service.JXLServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Random;

@RestController
@RequestMapping("/jxls")
public class JXLSController {

    @Autowired
    private JXLServiceImpl jxlService;


    @GetMapping("/{type}")
    public String getAPIData(@PathVariable String type) throws IOException {
        ResponseData responseData=jxlService.getFetchDataFromOpenAPI();
        if(null!=responseData) {
            String filename = "api_data_"+ new Random().nextInt(100)+"."+type;
            String templateName = "api_data."+type;
            if (jxlService.generateXls(responseData,templateName,filename))
                return "Success!!!!";
        }
       return "failure!!!";
    }

    @GetMapping("download/xls")
    public String getFile(HttpServletResponse httpServletResponse) throws IOException {

        ResponseData responseData=jxlService.getFetchDataFromOpenAPI();
        if(null!=responseData) {
            String templateName = "api_data.xls";
            if (jxlService.downloadXls(responseData, httpServletResponse,templateName))
                return "Success!!!!";
        }
        return "failure!!!";

    }

    @GetMapping("download/xlsx")
    public ResponseEntity<byte[]> getFile() throws IOException {
        ResponseData responseData=jxlService.getFetchDataFromOpenAPI();
        if(null!=responseData) {
            String filename = "api_data_"+ new Random().nextInt(100)+".xlsx";
            ByteArrayOutputStream  file = jxlService.downloadXls("api_data.xlsx",responseData);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(file.toByteArray());
        }
        return null;
    }
}
