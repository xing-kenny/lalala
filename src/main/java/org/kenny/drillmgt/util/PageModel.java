package org.kenny.drillmgt.util;

import java.util.List;

public class PageModel<T> {

	private List<T> result;

	private int maxPage;

	public PageModel() {
		super();
	}

	public PageModel(List<T> result, int maxPage) {
		super();
		this.result = result;
		this.maxPage = maxPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

}
