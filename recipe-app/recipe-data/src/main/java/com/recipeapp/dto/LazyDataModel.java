package com.recipeapp.dto;

import java.util.List;

public class LazyDataModel {

	private List<Object> list;
	private int totalPageNumber;
	private int currentPage;

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
