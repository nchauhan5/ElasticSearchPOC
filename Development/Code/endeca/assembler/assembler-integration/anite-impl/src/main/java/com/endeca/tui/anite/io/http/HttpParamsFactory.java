package com.endeca.tui.anite.io.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating HttpParams objects.
 */
public class HttpParamsFactory {
	
	/** The params. */
	protected Map<String, Object> params = new HashMap<String, Object>();
	
	/** The integer parameters. */
	protected Map<String, Integer> integerParameters = new HashMap<String, Integer>();
	
	/** The boolean parameters. */
	protected Map<String, Boolean> booleanParameters = new HashMap<String, Boolean>();

	/**
	 * Builds the http params.
	 *
	 * @return the http params
	 */
	public HttpParams buildHttpParams(){
		BasicHttpParams p = new BasicHttpParams();
		for (Entry<String, Object> entry : params.entrySet()){
			p.setParameter(entry.getKey(), entry.getValue());
		}
		for (Entry<String, Integer> e : getIntegerParameters().entrySet()){
			p.setIntParameter(e.getKey(), e.getValue());
		}
		for (Entry<String, Boolean> e : getBooleanParameters().entrySet()){
			p.setBooleanParameter(e.getKey(), e.getValue());
		}
		return p;
	}

	/**
	 * Gets the proxy http host.
	 *
	 * @return the proxy http host
	 */
	public HttpHost getProxyHttpHost(){
		return (HttpHost) getParam(ConnRoutePNames.DEFAULT_PROXY);
	}

	/**
	 * Sets the proxy http host.
	 *
	 * @param proxy the new proxy http host
	 */
	public void setProxyHttpHost(HttpHost proxy){
		setParam(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	/**
	 * Sets the proxy.
	 *
	 * @param endpoint the new proxy
	 */
	public void setProxy(String endpoint){
		String[] split = endpoint.split(":");
		if (2 == split.length){
			setProxyHttpHost(new HttpHost(split[0], Integer.parseInt(split[1])));
		} else{
			throw new IllegalArgumentException("Invalid endpoint (format should be host:port): " + endpoint);
		}
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams(){
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the params
	 */
	public void setParams(Map<String, Object> params){
		this.params = params;
	}

	/**
	 * Sets the param.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setParam(String name, Object value){
		getParams().put(name, value);
	}

	/**
	 * Gets the param.
	 *
	 * @param name the name
	 * @return the param
	 */
	public Object getParam(String name){
		return getParams().get(name);
	}

	/**
	 * Gets the integer parameters.
	 *
	 * @return the integer parameters
	 */
	public Map<String, Integer> getIntegerParameters(){
		return integerParameters;
	}

	/**
	 * Sets the integer parameters.
	 *
	 * @param intParams the int params
	 */
	public void setIntegerParameters(Map<String, Integer> intParams){
		this.integerParameters = intParams;
	}

	/**
	 * Gets the boolean parameters.
	 *
	 * @return the boolean parameters
	 */
	public Map<String, Boolean> getBooleanParameters(){
		return booleanParameters;
	}

	/**
	 * Sets the boolean parameters.
	 *
	 * @param booleanParameters the boolean parameters
	 */
	public void setBooleanParameters(Map<String, Boolean> booleanParameters){
		this.booleanParameters = booleanParameters;
	}
}
