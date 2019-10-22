package com.yi.core.common;

public enum FileType {
	DOCUMENT("文档"), COMPRESSION("压缩"), PICTURES("图片"), VIDEOS("视频"), OTHERS("其他");

	private String name;

	FileType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
