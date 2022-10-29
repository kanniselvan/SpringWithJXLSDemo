package com.kanni.SpringWithJXLSDemo.service;


import com.kanni.SpringWithJXLSDemo.model.ResponseData;
import com.kanni.SpringWithJXLSDemo.utils.XLXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class JXLServiceImpl {

    private final String OPEN_API="https://api.publicapis.org/entries";

    @Value("${jxls.template.path}")
    private String templatePath;

    @Value("${jxls.template.output.path}")
    private String outputPath;

    private static final String templateName="api_data.xls";

    @Autowired
    private RestTemplate restTemplate;

    public ResponseData getFetchDataFromOpenAPI(){
        return restTemplate.getForEntity(OPEN_API, ResponseData.class).getBody();
     }

    public boolean generateXls(ResponseData responseData,String templateName,String fileName) throws IOException {
        InputStream in =null;
        OutputStream out =null;
        try{
            if(!ObjectUtils.isEmpty(responseData)){

                out = new FileOutputStream(fileName);
                in =  new FileInputStream(ResourceUtils.getFile(templatePath+templateName));

                Map<String,Object> map = new HashMap<>();
                map.put("apiData", responseData.getEntries());
                map.put("count", responseData.getCount());

                XLXUtils.exportExcel(in,out,map);
                return true;
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return false;
    }



    public boolean downloadXls(ResponseData responseData,HttpServletResponse httpServletResponse,String templateName) throws IOException {
        InputStream in =null;
        OutputStream out =null;
        try{
            if(!ObjectUtils.isEmpty(responseData)){
                httpServletResponse.reset();
                httpServletResponse.addHeader("Content-disposition", "attachment;filename="+"api_data_"+ new Random().nextInt(100)+".xls");
                httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");

                out = httpServletResponse.getOutputStream();
                in =  new FileInputStream(ResourceUtils.getFile(templatePath+templateName));

                Map<String,Object> map = new HashMap<>();
                map.put("apiData", responseData.getEntries());
                map.put("count", responseData.getCount());

                XLXUtils.exportExcel(in,out,map);
                return true;
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return false;
    }


    public ByteArrayOutputStream downloadXls(String templateName,ResponseData responseData) throws IOException {
        InputStream in =null;
        ByteArrayOutputStream out =null;
        try{
            if(!ObjectUtils.isEmpty(responseData)){


                out = new ByteArrayOutputStream();
                in =  new FileInputStream(ResourceUtils.getFile(templatePath+templateName));

                Map<String,Object> map = new HashMap<>();
                map.put("apiData", responseData.getEntries());
                map.put("count", responseData.getCount());

                XLXUtils.exportExcel(in,out,map);

            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return out;
    }


}
