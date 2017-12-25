/**
 * 
 */
package com.endeca.tui.anite.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.tui.anite.CacheStats;
import com.endeca.tui.anite.enums.AniteQueryType;
import com.endeca.tui.anite.exceptions.AniteException;
import com.endeca.tui.anite.parameters.AniteRequiredParameters;
import com.endeca.tui.anite.parameters.impl.DefaultAniteParameters;



/**
 * Test class for {@link RequestScopedCachingAniteInterface}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestScopedCachingAniteInterfaceTest
{

	/** The default anite implementation. */
	@Mock
	private DefaultAniteImplementation defaultAniteImplementation;

	/** The cache stats. */
	@Mock
	private CacheStats cacheStats;

	/** The request scoped caching anite interface. */
	@InjectMocks
	private static final RequestScopedCachingAniteInterface requestScopedCachingAniteInterface = new RequestScopedCachingAniteInterface();

	/**
	 * Before each test class. Clearing the cache.
	 */
	@Before
	public void before()
	{
		requestScopedCachingAniteInterface.cache.clear();
	}

	/**
	 * Test for in case of new request backing anite interface should be called and fresh results should be fetched.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void newRequestBackingAniteInterfaceCalled() throws AniteException
	{
		final DefaultAniteParameters aniteRequiredParameters = new DefaultAniteParameters();
		aniteRequiredParameters.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters);
		verify(defaultAniteImplementation).query(AniteQueryType.CALENDAR, aniteRequiredParameters);
	}

	/**
	 * Test for in case of new request CacheCallback cache miss method is called.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void newRequestCacheStatsCacheMissCalled() throws AniteException
	{
		final DefaultAniteParameters aniteRequiredParameters = new DefaultAniteParameters();
		aniteRequiredParameters.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters);
		verify(cacheStats).cacheMiss();
	}

	/**
	 * Test for in case of new request cache is updated properly.
	 * 
	 * @throws AniteException
	 *            the anite exception
	 */
	@Test
	public void newRequestCacheIsUpdated() throws AniteException
	{
		final DefaultAniteParameters aniteRequiredParameters = new DefaultAniteParameters();
		aniteRequiredParameters.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters);
		assertThat(requestScopedCachingAniteInterface.cache.size(), is(1));
	}

	/**
	 * Test for Multiple request with same parameter there is no update in cache.
	 * 
	 * @throws AniteException
	 */
	@Test
	public void multipleRequestSameKeyCacheHitCalled() throws AniteException
	{
		final DefaultAniteParameters aniteRequiredParameters1 = new DefaultAniteParameters();
		aniteRequiredParameters1.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters1);
		final DefaultAniteParameters aniteRequiredParameters2 = new DefaultAniteParameters();
		aniteRequiredParameters2.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters2);


		final InOrder inOrder = Mockito.inOrder(cacheStats);
		inOrder.verify(cacheStats).cacheMiss();
		inOrder.verify(cacheStats).cacheHit();
	}

	/**
	 * Test for Multiple request with same key different parameter cache is updated with latest value.
	 * 
	 * @throws AniteException
	 */
	@Test
	public void multipleRequestSameKeyDifferentParameterCacheUpdated() throws AniteException
	{
		final DefaultAniteParameters aniteRequiredParameters1 = new DefaultAniteParameters();
		aniteRequiredParameters1.setAccommodationsString("123|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters1);
		assertThat(requestScopedCachingAniteInterface.cache.size(), is(1));
		final Map<AniteQueryType, Map<AniteRequiredParameters, Object>> cacheMap1 = requestScopedCachingAniteInterface.cache;

		final DefaultAniteParameters aniteRequiredParameters2 = new DefaultAniteParameters();
		aniteRequiredParameters2.setAccommodationsString("1234|S");
		requestScopedCachingAniteInterface.query(AniteQueryType.CALENDAR, aniteRequiredParameters2);
		assertThat(requestScopedCachingAniteInterface.cache.size(), is(1));
		final Map<AniteQueryType, Map<AniteRequiredParameters, Object>> cacheMap2 = requestScopedCachingAniteInterface.cache;
		Assert.assertEquals(cacheMap1.get(AniteQueryType.CALENDAR), cacheMap2.get(AniteQueryType.CALENDAR));
		verify(cacheStats, never()).cacheHit();
		verify(cacheStats, times(2)).cacheMiss();
	}
}
