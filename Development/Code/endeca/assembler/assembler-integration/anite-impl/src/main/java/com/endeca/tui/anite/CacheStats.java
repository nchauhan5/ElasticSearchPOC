package com.endeca.tui.anite;


// TODO: Auto-generated Javadoc
/**
 * The Class CacheStats.
 */
public class CacheStats implements CacheStatsCallback
{

	/**
	 * The Class CallbackRegistry.
	 */
	protected static class CallbackRegistry extends ThreadLocal<RequestScopedCallback>
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.ThreadLocal#initialValue()
		 */
		@Override
		protected RequestScopedCallback initialValue()
		{
			return new RequestScopedCallback();
		}
	}

	/**
	 * The Class RequestScopedCallback.
	 */
	private static class RequestScopedCallback implements CacheStatsCallback
	{

		/** The hit. */
		private int hit;

		/** The miss. */
		private int miss;

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.endeca.infront.tui.anite.CacheStatsCallback#cacheMiss()
		 */
		@Override
		public void cacheMiss()
		{
			miss++;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.endeca.infront.tui.anite.CacheStatsCallback#cacheHit()
		 */
		@Override
		public void cacheHit()
		{
			hit++;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			final int total = hit + miss;
			if (total > 0)
			{
				return hit + "/" + total + "=" + (hit * 100 / total) + "%";
			}
			else
			{
				return "No calls";
			}
		}
	}

	/** The registry. */
	CallbackRegistry registry = new CallbackRegistry();


	public void assemblyComplete()
	{
		registry.remove();
	}

	/**
	 * Gets the callback.
	 * 
	 * @return the callback
	 */
	protected RequestScopedCallback getCallback()
	{
		return registry.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.tui.anite.CacheStatsCallback#cacheHit()
	 */
	@Override
	public void cacheHit()
	{
		getCallback().cacheHit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.endeca.infront.tui.anite.CacheStatsCallback#cacheMiss()
	 */
	@Override
	public void cacheMiss()
	{
		getCallback().cacheMiss();
	}
}
