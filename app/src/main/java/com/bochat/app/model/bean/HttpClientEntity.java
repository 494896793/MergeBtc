package com.bochat.app.model.bean;


import java.io.Serializable;

/**
 * Created by ldl on 2019/4/9.
 */
public class HttpClientEntity implements Serializable {


	/**
	 * json解析生成的对象
	*/
	
	private Object Obj;
	private String message;
	private int code;
	private String jsonData;
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		this.message = msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public <T> T getObj() {

		return (T) Obj;
	}

	public void setObj(Object obj) {

		Obj = obj;
	}

	public void setCodeMsg(ResponseCode responseCode){
		this.message = responseCode.getMsg();
		this.code = responseCode.getCode();
	}

	@Override
	public String toString() {
/*		Log.e("输出", "HttpClientEntity{" +
				"url='" + url + '\'' +
				", message='" + message + '\'' +
				", code=" + code +
				", jsonData='" + jsonData + '\'' +
				'}');*/
		return "HttpClientEntity={" +
				", message='" + message + '\'' +
				", code=" + code +
				", jsonData='" + jsonData + '\'' +
				'}';
	}
}
