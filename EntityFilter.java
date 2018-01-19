package com.soprasteria.mycompany.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ggranata
 *
 */
public class EntityFilter {
	private List<RecordFilter> recordFilter;

	public EntityFilter(ArrayList<RecordFilter> recordFilter) {
		this.recordFilter = recordFilter;
	}
	
	public EntityFilter() {
		
	}

	public List<RecordFilter> getRecordFilter() {
		return recordFilter;
	}

	public void setRecordFilter(List<RecordFilter> recordFilter) {
		this.recordFilter = recordFilter;
	}
}
