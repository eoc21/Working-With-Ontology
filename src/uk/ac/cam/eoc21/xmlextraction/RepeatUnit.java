package xmlextraction;

import java.util.ArrayList;

public class RepeatUnit {
	ArrayList<RepeatUnitSample> samples;

	public RepeatUnit() {

	}

	/**
	 * 
	 * @param repeatUnitSamples
	 *            - ArrayList<RepeatUnitSample>
	 */
	public RepeatUnit(final ArrayList<RepeatUnitSample> repeatUnitSamples) {
		this.samples = repeatUnitSamples;
	}

	/**
	 * 
	 * @return ArrayList<RepeatUnitSample>
	 */
	public ArrayList<RepeatUnitSample> getRepeatUnitSamples() {
		return samples;
	}

	/**
	 * 
	 * @param sampleRepeatUnits - ArrayList<RepeatUnitSample>
	 */
	public void setSamples(final ArrayList<RepeatUnitSample> sampleRepeatUnits) {
		this.samples = sampleRepeatUnits;
	}
}
