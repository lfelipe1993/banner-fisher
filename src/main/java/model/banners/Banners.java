package model.banners;

import java.io.Serializable;
import java.sql.Timestamp;

public class Banners implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String url;
	private Timestamp dateTime;
	
	public Banners() {
		// TODO Auto-generated constructor stub
	}
	
	public Banners(Integer id, String url, Timestamp dateTime) {
		super();
		this.id = id;
		this.url = url;
		this.dateTime = dateTime;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Banners other = (Banners) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Banners [id=");
		builder.append(id);
		builder.append(", url=");
		builder.append(url);
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append("]");
		return builder.toString();
	}
	

}
