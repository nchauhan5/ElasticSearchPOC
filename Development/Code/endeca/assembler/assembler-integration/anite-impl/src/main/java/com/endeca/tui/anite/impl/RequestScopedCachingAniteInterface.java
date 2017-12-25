package com.endeca.tui.anite.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.endeca.tui.anite.AniteInterface;
import com.endeca.tui.anite.CacheStatsCallback;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;


// TODO: Auto-generated Javadoc
/**
 * The Class RequestScopedCachingAniteInterface.
 */
public class RequestScopedCachingAniteInterface implements AniteInterface
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestScopedCachingAniteInterface.class);

	/** The backing anite interface. */
	private AniteInterface backingAniteInterface;

	/** The cache stats callback. */
	private CacheStatsCallback cacheStatsCallback;

	/** The cache. */
	Map<AniteQueryType, Map<AniteRequiredParameters, Object>> cache = new HashMap<AniteQueryType, Map<AniteRequiredParameters, Object>>(
			AniteQueryType.values().length);

	/**
	 * Instantiates a new request scoped caching anite interface.
	 */
	public RequestScopedCachingAniteInterface()
	{
	}

	/**
	 * Instantiates a new request scoped caching anite interface.
	 * 
	 * @param backingAniteInterface
	 *           the backing anite interface
	 */
	public RequestScopedCachingAniteInterface(final AniteInterface backingAniteInterface)
	{
		this();
		setBackingAniteInterface(backingAniteInterface);
	}

	/**
	 * Gets the backing anite interface.
	 * 
	 * @return the backing anite interface
	 */
	public AniteInterface getBackingAniteInterface()
	{
		return backingAniteInterface;
	}

	/**
	 * Making method final to reduce the sonar issue.
	 * 
	 * @param backingAniteInterface
	 *           the new backing anite interface
	 */
	public final void setBackingAniteInterface(final AniteInterface backingAniteInterface)
	{
		this.backingAniteInterface = backingAniteInterface;
	}

	/**
	 * Cache miss.
	 */
	protected void cacheMiss()
	{
		LOGGER.trace("Cache miss");
		final CacheStatsCallback callback = getCacheStatsCallback();
		if (null != callback)
		{
			callback.cacheMiss();
		}
	}

	/**
	 * Cache hit.
	 */
	protected void cacheHit()
	{
		LOGGER.trace("Cache hit");
		final CacheStatsCallback callback = getCacheStatsCallback();
		if (null != callback)
		{
			callback.cacheHit();
		}
	}

	/**
	 * Gets the cache stats callback.
	 * 
	 * @return the cache stats callback
	 */
	public CacheStatsCallback getCacheStatsCallback()
	{
		return cacheStatsCallback;
	}

	/**
	 * Sets the cache stats callback.
	 * 
	 * @param cacheStatsCallback
	 *           the new cache stats callback
	 */
	public void setCacheStatsCallback(final CacheStatsCallback cacheStatsCallback)
	{
		this.cacheStatsCallback = cacheStatsCallback;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.tui.anite.AniteInterface#query(com.endeca.tui.anite.AniteQueryType,
	 * com.endeca.tui.anite.AniteRequiredParameters)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T query(final AniteQueryType type, final AniteRequiredParameters params) throws AniteException
	{

		if (!cache.containsKey(type))
		{
			synchronized (cache)
			{
				if (!cache.containsKey(type))
				{
					cache.put(type, new HashMap<AniteRequiredParameters, Object>());
				}
			}
		}
		final Map<AniteRequiredParameters, Object> map = cache.get(type);
		if (map.containsKey(params))
		{
			cacheHit();
			return (T) map.get(params);
		}
		else
		{
			cacheMiss();
			final T response = getBackingAniteInterface().query(type, params);
			map.put(params, response);
			return response;
		}
	}
}
