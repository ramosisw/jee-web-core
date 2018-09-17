package com.ramosisw.jee.web.core.api.to;

import java.math.BigInteger;

/**
 * Clase de ayuda para el manejo de ficheros
 * @author jcramos
 *
 */
public class FilePropertiesType {
	private String content;
	private String fileName;
	private String checkSum;
	private BigInteger size;
	private String compressionType;
	private String encodingType;

	public FilePropertiesType() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public String getCompressionType() {
		return compressionType;
	}

	public void setCompressionType(String compressionType) {
		this.compressionType = compressionType;
	}

	public String getEncodingType() {
		return encodingType;
	}

	public void setEncodingType(String encodingType) {
		this.encodingType = encodingType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilePropertiesType [content=");
		builder.append(content);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", checkSum=");
		builder.append(checkSum);
		builder.append(", size=");
		builder.append(size);
		builder.append(", compressionType=");
		builder.append(compressionType);
		builder.append(", encodingType=");
		builder.append(encodingType);
		builder.append("]");
		return builder.toString();
	}

}
