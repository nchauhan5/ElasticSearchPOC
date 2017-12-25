package com.endeca.tui.anite;

import java.util.EventListener;

// TODO: Auto-generated Javadoc
/**
 * The Interface CacheStatsCallback.
 */
public interface CacheStatsCallback extends EventListener {
	
	/**
	 * Cache hit.
	 */
	void cacheHit();

	/**
	 * Cache miss.
	 */
	void cacheMiss();
}