package com.endeca.search.api.adapters;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.endeca.search.api.adapter.CodeFormatAdapter;


/**
 * Test class for {@link ChainedCodeFormatAdapter}
 */
@RunWith(MockitoJUnitRunner.class)
public class ChainedCodeFormatAdapterTest
{

	/** The hybris code padapter. */
	@Mock
	private HybrisCodeAdapter hybrisCodeAdapter;

	/** The brand adapter. */
	@Mock
	private PaddingAdapter paddingAdapter;

	/** The chained code format adapter. */
	@InjectMocks
	private final ChainedCodeFormatAdapter chainedCodeFormatAdapter = new ChainedCodeFormatAdapter();

	/**
	 * Before each.
	 */
	@Before
	public void beforeEach()
	{
		final List<CodeFormatAdapter> adapters = new ArrayList<CodeFormatAdapter>();
		adapters.add(hybrisCodeAdapter);
		adapters.add(paddingAdapter);
		chainedCodeFormatAdapter.setAdapters(adapters);
	}

	/**
	 * Test that chained adapters are called in Correct order for
	 * {@link ChainedCodeFormatAdapter#endeca2SearchAPI(String)}
	 */
	@Test
	public void chainedAdaptersCalledInCorrectOrderEnd2SchMethod()
	{
		chainedCodeFormatAdapter.endeca2SearchAPI("PC-0001234");
		final InOrder inOrder = Mockito.inOrder(paddingAdapter, hybrisCodeAdapter);
		inOrder.verify(hybrisCodeAdapter).endeca2SearchAPI(Mockito.anyString());
		inOrder.verify(paddingAdapter).endeca2SearchAPI(Mockito.anyString());
	}

	/**
	 * Test adapters are working sequentially i.e. output of 1st is passed as an input to 2nd adapter for
	 * {@link ChainedCodeFormatAdapter#endeca2SearchAPI(String)}
	 */
	@Test
	public void outputIsPassedSequentiallyEnd2SchMethod()
	{
		final String input = "P-0001234";

		final String output1 = "0001234";

		final String output2 = "1234";
		when(hybrisCodeAdapter.endeca2SearchAPI(input)).thenReturn(output1);
		when(paddingAdapter.endeca2SearchAPI(output1)).thenReturn(output2);
		chainedCodeFormatAdapter.endeca2SearchAPI(input);

		verify(hybrisCodeAdapter).endeca2SearchAPI(input);
		verify(paddingAdapter).endeca2SearchAPI(output1);
	}

	/**
	 * Test that chained adapters are called in Correct order for
	 * {@link ChainedCodeFormatAdapter#accommCodeConverter(String)}
	 */
	@Test
	public void chainedAdaptersCalledInCorrectOrderAccomConvertMethod()
	{
		chainedCodeFormatAdapter.accommCodeConverter("PC-0001234");
		final InOrder inOrder = Mockito.inOrder(paddingAdapter, hybrisCodeAdapter);
		inOrder.verify(hybrisCodeAdapter).accommCodeConverter(Mockito.anyString());
		inOrder.verify(paddingAdapter).accommCodeConverter(Mockito.anyString());
	}

	/**
	 * Test adapters are working sequentially i.e. output of 1st is passed as an input to 2nd adapter for
	 * {@link ChainedCodeFormatAdapter#accommCodeConverter(String)}
	 */
	@Test
	public void outputIsPassedSequentiallyAccomConvertMethod()
	{
		final String input = "PC-0001234";

		final String output1 = "0001234";

		final String output2 = "1234";
		when(hybrisCodeAdapter.accommCodeConverter(input)).thenReturn(output1);
		when(paddingAdapter.accommCodeConverter(output1)).thenReturn(output2);
		chainedCodeFormatAdapter.accommCodeConverter(input);

		verify(hybrisCodeAdapter).accommCodeConverter(input);
		verify(paddingAdapter).accommCodeConverter(output1);
	}

	/**
	 * Test that chained adapters are called in Correct order for
	 * {@link ChainedCodeFormatAdapter#searchAPI2endeca(String)}
	 */
	@Test
	public void chainedAdaptersCalledInCorrectOrderSch2EndMethod()
	{
		chainedCodeFormatAdapter.searchAPI2endeca("1234");
		final InOrder inOrder = Mockito.inOrder(paddingAdapter, hybrisCodeAdapter);
		inOrder.verify(hybrisCodeAdapter).searchAPI2endeca(Mockito.anyString());
		inOrder.verify(paddingAdapter).searchAPI2endeca(Mockito.anyString());
	}

	/**
	 * Test adapters are working sequentially i.e. output of 1st is passed as an input to 2nd adapter for
	 * {@link ChainedCodeFormatAdapter#searchAPI2endeca(String)}
	 */
	@Test
	public void outputIsPassedSequentiallySch2EndMethod()
	{
		final String input = "1234";

		final String output1 = "0001234";

		final String output2 = "P-0001234";
		when(hybrisCodeAdapter.accommCodeConverter(input)).thenReturn(output1);
		when(paddingAdapter.accommCodeConverter(output1)).thenReturn(output2);
		chainedCodeFormatAdapter.accommCodeConverter(input);

		verify(hybrisCodeAdapter).accommCodeConverter(input);
		verify(paddingAdapter).accommCodeConverter(output1);
	}
}
