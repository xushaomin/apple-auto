package com.appleframework.auto.open.platform.response;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.appleframework.exception.ServiceException;
import com.appleframework.rop.RopRequestContext;
import com.appleframework.rop.security.MainError;
import com.appleframework.rop.security.MainErrorType;
import com.appleframework.rop.security.MainErrors;
import com.appleframework.rop.security.SubError;
import com.appleframework.rop.security.SubErrorType;
import com.appleframework.rop.security.SubErrors;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "serviceExceptionResponse")
public class ServiceExceptionResponse extends ErrorResponse {
	
	public static String SERVICE_CURRENTLY_UNAVAILABLE = "SERVICE_CURRENTLY_UNAVAILABLE"; //服务不可用

    private static final String ISV = "isv.";
    
    private static final String ISP = "isp.";

    private static final String SERVICE_UNAVAILABLE = "-service-unavailable";

    private static final String SERVICE_ERROR = "-service-error:";

    //注意，这个不能删除，否则无法进行流化
    public ServiceExceptionResponse() {
    }


    /**
     * 服务发生错误的错误响应，错误码的格式为：isv.***-service-error:###,假设
     * serviceName为book.upload，error_code为INVLIAD_USERNAME_OR_PASSWORD，则错误码会被格式化为：
     * isv.book-upload-service-error:INVLIAD_USERNAME_OR_PASSWORD
     *
     * @param serviceName 服务名，如book.upload,会被自动转换为book-upload
     * @param errorCode   错误的代码，如INVLIAD_USERNAME_OR_PASSWORD,在错误码的后面，一般为大写或数字。
     * @param locale      本地化对象
     * @param params      错误信息的参数，如错误消息的值为this is a {0} error，则传入的参数为big时，错误消息格式化为：
     *                    this is a big error
     */
    @Deprecated
    public ServiceExceptionResponse(String serviceName, ServiceException exception, Locale locale) {
    	MainError mainError = null;
    	SubError subError = null;
    	ArrayList<SubError> subErrors = new ArrayList<SubError>();
    	if(exception.getCode().equals(SERVICE_CURRENTLY_UNAVAILABLE)) {
    		mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
            String errorCodeKey = ISP + transform(serviceName) + SERVICE_UNAVAILABLE;
            subError = SubErrors.getSubError(errorCodeKey,
                    SubErrorType.ISP_SERVICE_UNAVAILABLE.value(),
                    locale, serviceName,"NONE","NONE");
            subErrors.add(subError);
    	}
    	else {
    		mainError = MainErrors.getError(MainErrorType.BUSINESS_LOGIC_ERROR, locale);
    		serviceName = transform(serviceName);
    		String subErrorCode = ISV + serviceName + SERVICE_ERROR + exception.getCode();
    		subError = SubErrors.getSubError(subErrorCode, exception.getKey(), locale, exception.getParams());
    		subErrors.add(subError);
		}
        setMainError(mainError);
        setSubErrors(subErrors);
    }

    /**
     * 服务发生错误的错误响应，错误码的格式为：isv.***-service-error:###,假设
     * serviceName为file.upload，error_code为INVLIAD_USERNAME_OR_PASSWORD，则错误码会被格式化为：
     * isv.file-upload-service-error:INVLIAD_USERNAME_OR_PASSWORD
     *
     * @param context     请求上下文
     * @param errorCode   错误的代码，如INVLIAD_USERNAME_OR_PASSWORD,在错误码的后面，一般为大写或数字。
     * @param params      错误信息的参数，如错误消息的值为this is a {0} error，则传入的参数为big时，错误消息格式化为：
     *                    this is a big error
     */
    public ServiceExceptionResponse(RopRequestContext context, ServiceException exception) {
    	Locale locale = context.getLocale();
    	String method = context.getMethod();
        MainError mainError = null;
    	SubError subError = null;
    	ArrayList<SubError> subErrors = new ArrayList<SubError>();
    	
		if (exception.getCode().equals(SERVICE_CURRENTLY_UNAVAILABLE)) {
			mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
			String errorCodeKey = ISP + transform(context.getMethod()) + SERVICE_UNAVAILABLE;
			String throwableMessage = null;
			Throwable throwable = exception.getCause();
			if (throwable != null) {
				throwableMessage = getThrowableInfo(throwable);
			}
			subError = SubErrors.getSubError(errorCodeKey,
					SubErrorType.ISP_SERVICE_UNAVAILABLE.value(), locale,
					method, throwable.getClass().getName(),
					throwableMessage);
			subErrors.add(subError);
		}
    	else {
    		 mainError = MainErrors.getError(MainErrorType.BUSINESS_LOGIC_ERROR,context.getLocale(),
                     context.getMethod(),context.getVersion());
    		 String serviceName = transform(context.getMethod());
    		 String subErrorCode = ISV + serviceName + SERVICE_ERROR + exception.getCode();
    		 if(null != exception.getMessage()) {
    			 subError = new SubError(subErrorCode, exception.getMessage());
    		 } else {
    			 subError = SubErrors.getSubError(subErrorCode, exception.getKey(), context.getLocale(), exception.getParams());
    		 }
    	     subErrors.add(subError);
		}
        setMainError(mainError);
        setSubErrors(subErrors);
    }    
    
    private String getThrowableInfo(Throwable throwable) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        return outputStream.toString();
    }

}

