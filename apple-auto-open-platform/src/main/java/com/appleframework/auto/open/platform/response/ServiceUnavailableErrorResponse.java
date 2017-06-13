package com.appleframework.auto.open.platform.response;

import java.util.ArrayList;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.appleframework.rop.RopRequestContext;
import com.appleframework.rop.response.ErrorResponse;
import com.appleframework.rop.security.MainError;
import com.appleframework.rop.security.SubError;
import com.appleframework.rop.security.SubErrorType;
import com.appleframework.rop.security.SubErrors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ServiceUnavailableErrorResponse extends ErrorResponse {

    private static final String ISP = "isp.";

    private static final String SERVICE_UNAVAILABLE = "-service-unavailable";

    public ServiceUnavailableErrorResponse() {
    }

    public ServiceUnavailableErrorResponse(String method, Locale locale) {
        MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
        String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
        /*SubError subError = SubErrors.getSubError(errorCodeKey,
                SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                locale, method,"NONE","NONE");*/
        
        SubError subError =  getSubError(errorCodeKey, "系统服务繁忙，请您稍后再试");
        ArrayList<SubError> subErrors = new ArrayList<SubError>();
        subErrors.add(subError);

        setMainError(mainError);
        setSubErrors(subErrors);
    }

    public ServiceUnavailableErrorResponse(String method, Locale locale, Throwable throwable) {
        MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);

        ArrayList<SubError> subErrors = new ArrayList<SubError>();

        String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
        /*Throwable srcThrowable = throwable;
        if(throwable.getCause() != null){
            srcThrowable = throwable.getCause();
        }
        SubError subError = SubErrors.getSubError(errorCodeKey,
                SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                locale,
                method, srcThrowable.getClass().getName(),getThrowableInfo(throwable));*/
        
        SubError subError =  getSubError(errorCodeKey, "系统服务繁忙，请您稍后再试");
        subErrors.add(subError);

        setSubErrors(subErrors);
        setMainError(mainError);
    }
    
    public ServiceUnavailableErrorResponse(RopRequestContext context, Throwable throwable) {
    	Locale locale = context.getLocale();
    	String method = context.getMethod();
        MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
        ArrayList<SubError> subErrors = new ArrayList<SubError>();

        String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
        /*Throwable srcThrowable = throwable;
        if(throwable.getCause() != null){
            srcThrowable = throwable.getCause();
        }
        SubError subError = SubErrors.getSubError(errorCodeKey,
                SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                locale,
                method, srcThrowable.getClass().getName(),getThrowableInfo(throwable));*/
        
        SubError subError =  getSubError(errorCodeKey, "系统服务繁忙，请您稍后再试");
        subErrors.add(subError);

        setSubErrors(subErrors);
        setMainError(mainError);
    }

    public ServiceUnavailableErrorResponse(RopRequestContext context) {
    	Locale locale = context.getLocale();
    	String method = context.getMethod();
        MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
        ArrayList<SubError> subErrors = new ArrayList<SubError>();

        String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
        /*SubError subError = SubErrors.getSubError(errorCodeKey,
                SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                locale,
                method, "服务系统繁忙，请您稍后再试");*/
        
        SubError subError =  getSubError(errorCodeKey, "系统服务繁忙，请您稍后再试");
        subErrors.add(subError);

        setSubErrors(subErrors);
        setMainError(mainError);
    }
    
    /*private String getThrowableInfo(Throwable throwable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        return outputStream.toString();
    }*/
    
    public static SubError getSubError(String code, String message) {
    	return new SubError("rsp.xxx-unavailable", message);
    }
}

