package com.kage.trkr.api;

import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ApuUtil class auto generated from swagger code gen.
 * For this project we only generate the api and model and ignore everything else.
 * This file falls under 'everything else' so we copy it over here so we don't need to generate it again.
 *
 * @author Swagger SpringCodegen
 */
public class ApiUtil {
    public static void setExampleResponse(NativeWebRequest req, String contentType, String example) {
        try {
            HttpServletResponse res = req.getNativeResponse(HttpServletResponse.class);
            res.setCharacterEncoding("UTF-8");
            res.addHeader("Content-Type", contentType);
            res.getWriter().print(example);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
