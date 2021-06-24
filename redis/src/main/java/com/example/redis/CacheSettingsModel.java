package com.example.redis;

public class CacheSettingsModel {
	private String cacheName;
	private String timeToLiveSeconds;

	public final String getCacheName() {
		return cacheName;
	}

	public final void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public final String getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public final void setTimeToLiveSeconds(String timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}
}
