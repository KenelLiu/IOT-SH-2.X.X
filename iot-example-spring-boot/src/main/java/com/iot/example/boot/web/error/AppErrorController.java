package com.iot.example.boot.web.error;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class AppErrorController implements ErrorController{

    private static final Logger logger = LoggerFactory.getLogger(AppErrorController.class);

    private static AppErrorController appErrorController;

     /**
     * Error Attributes in the Application
     */
    @Autowired
    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    /**
     * Controller for the Error Controller
     * @param errorAttributes
     * @return 
     */ 

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public AppErrorController() {
        if(appErrorController == null){
            appErrorController = new AppErrorController(errorAttributes);
        }
    }

    /**
     * Supports the HTML Error View
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
        return new ModelAndView("/error/error", getErrorAttributes(request, false));
    }

    /**
     * Supports other formats like JSON, XML
     * @param request
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, getTraceParameter(request));
        HttpStatus status = getStatus(request);
        return new ResponseEntity<Map<String, Object>>(body, status);
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */   
    public String getErrorPath() {
        return ERROR_PATH;
    }


    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {     
    	ServletWebRequest requestAttributes = new ServletWebRequest(request);     	
    	Set<Include> includes= new HashSet<>();
    	includes.add(Include.EXCEPTION);
    	includes.add(Include.BINDING_ERRORS);
    	if(includeStackTrace)
    		includes.add(Include.STACK_TRACE);    	    	
    	ErrorAttributeOptions err=ErrorAttributeOptions.of(includes);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(requestAttributes,err);  
        String URL = request.getRequestURL().toString();
        map.put("URL", URL);        
        //logger.debug("AppErrorController.method [error info]: status-" + map.get("status") +", request url-" + URL);            
        return map;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            }
            catch (Exception ignored) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }   

}