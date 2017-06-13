package com.appleframework.auto.open.platform.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.rop.security.MainError;
import com.appleframework.rop.security.MainErrorType;
import com.appleframework.rop.security.MainErrors;
import com.appleframework.rop.security.SubError;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errorResponse")
public class ErrorResponse {

    /*@XmlAttribute
    protected String errorToken = CommonConstant.ERROR_TOKEN;*/

    @XmlAttribute
    protected String code = "0";

    @XmlElement
    protected String message;

    @XmlElement
    protected String solution;

    @XmlElementWrapper(name = "subErrors")
    @XmlElement(name = "subErrors")
    protected List<SubError> subErrors;

    public ErrorResponse() {
    }
    
    public ErrorResponse(String code) {
    	this.code = code;
    	this.message = PropertyConfigurer.getValue(code);
    }
    
    public ErrorResponse(String code, String message) {
    	this.code = code;
    	if(message == null)
    		this.message = PropertyConfigurer.getValue(code);
    	else
    		this.message = message;
    }

    public ErrorResponse(MainError mainError) {
        this.code = mainError.getCode();
        this.message = mainError.getMessage();
        if(null == this.message) {
        	this.message = PropertyConfigurer.getValue(code);
        }
        this.solution = mainError.getSolution();
        if (mainError.getSubErrors() != null && mainError.getSubErrors().size() > 0) {
            this.subErrors = mainError.getSubErrors();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public List<SubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }

    public void addSubError(SubError subError){
        if (subErrors == null) {
            subErrors = new ArrayList<SubError>();
        }
        subErrors.add(subError);
    }

    protected MainError getInvalidArgumentsError(Locale locale) {
        return MainErrors.getError(MainErrorType.INVALID_ARGUMENTS, locale);
    }

    protected void setMainError(MainError mainError) {
        setCode(mainError.getCode());
        setMessage(mainError.getMessage());
        setSolution(mainError.getSolution());
    }

   /* public String getErrorToken() {
        return errorToken;
    }*/

    /**
     * 对服务名进行标准化处理：如book.upload转换为book-upload，
     *
     * @param method
     * @return
     */
    protected String transform(String method) {
        if(method != null){
            method = method.replace(".", "-");
            return method;
        }else{
            return "LACK_METHOD";
        }
    }
    
    public static ErrorResponse create(String code) {
		return new ErrorResponse(code);
	}
    
    public static ErrorResponse create(String code, String message) {
		return new ErrorResponse(code, message);
	}
    
    public static ErrorResponse create(MainError mainError) {
		return new ErrorResponse(mainError);
	}

}
