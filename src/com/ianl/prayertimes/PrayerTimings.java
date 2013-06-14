package com.ianl.prayertimes;

/**
 * Object to store today's prayer times. Also formats the date/times
 * to choose a suitable way to display to the user.
 * 
 * @author Tehmur
 *
 */
public class PrayerTimings {	
	private String mToday;
	
	private String mFajrStart;
	private String mFajrJamaa;
	
	private String mSunrise;
	
	private String mDhuhrStart;
	private String mDhuhrJamaa;
	
	private String mAsrStart;
	private String mAsrJamaa;
	
	private String mMaghribStart;
	private String mMaghribJamaa;
	
	private String mIshaaStart;
	private String mIshaaJamaa;

	public PrayerTimings() {}
	
	public String getTodaysDate() {
		return mToday;
	}

	public void setTodaysDate(String string) {
		mToday = string;		
	}

	public String getFajrStart() {
		return mFajrStart;
	}

	public void setFajrStart(String mFajrStart) {
		this.mFajrStart = mFajrStart;
	}

	public String getFajrJamaa() {
		return mFajrJamaa;
	}

	public void setFajrJamaa(String mFajrJamaa) {
		this.mFajrJamaa = mFajrJamaa;
	}

	public String getSunrise() {
		return mSunrise;
	}

	public void setSunrise(String mSunrise) {
		this.mSunrise = mSunrise;
	}

	public String getDhuhrStart() {
		return mDhuhrStart;
	}

	public void setDhuhrStart(String mDhuhrStart) {
		this.mDhuhrStart = mDhuhrStart;
	}

	public String getDhuhrJamaa() {
		return mDhuhrJamaa;
	}

	public void setDhuhrJamaa(String mDhuhrJamaa) {
		this.mDhuhrJamaa = mDhuhrJamaa;
	}

	public String getAsrStart() {
		return mAsrStart;
	}

	public void setAsrStart(String mAsrStart) {
		this.mAsrStart = mAsrStart;
	}

	public String getAsrJamaa() {
		return mAsrJamaa;
	}

	public void setAsrJamaa(String mAsrJamaa) {
		this.mAsrJamaa = mAsrJamaa;
	}

	public String getMaghribStart() {
		return mMaghribStart;
	}

	public void setMaghribStart(String mMaghribStart) {
		this.mMaghribStart = mMaghribStart;
	}

	public String getMaghribJamaa() {
		return mMaghribJamaa;
	}

	public void setMaghribJamaa(String mMaghribJamaa) {
		this.mMaghribJamaa = mMaghribJamaa;
	}

	public String getIshaaStart() {
		return mIshaaStart;
	}

	public void setIshaaStart(String mIshaaStart) {
		this.mIshaaStart = mIshaaStart;
	}

	public String getIshaaJamaa() {
		return mIshaaJamaa;
	}

	public void setIshaaJamaa(String mIshaaJamaa) {
		this.mIshaaJamaa = mIshaaJamaa;
	}
	
}
