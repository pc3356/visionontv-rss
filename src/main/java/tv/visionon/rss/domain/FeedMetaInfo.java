package tv.visionon.rss.domain;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author administrator
 */
@Entity
@Table(name="feed", uniqueConstraints = {
		@UniqueConstraint(columnNames={"short_name", "uri"})
		})
public class FeedMetaInfo implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String uri;
	private String title;
	private String link;
	private String encoding;
	@Column(name="published_date")
	private Date publishedDate;
	@Column(name="short_name")
	private String shortName;
	private boolean active = true;
	
	@OneToMany(mappedBy="feed")
	//@Cascade()
	private List<EntryMetaInfo> entries;
	
	//@ManyToMany
	//private List<Tag> tags;
	
	public FeedMetaInfo() {
		entries = new ArrayList<EntryMetaInfo>();
	}
	
	public FeedMetaInfo(FeedMetaInfo source) {
		this();
		this.active = source.active;
		this.encoding = source.encoding;
		this.entries.addAll(source.entries);
		this.id = source.id;
		this.link = source.link;
		this.publishedDate = source.publishedDate;
		this.shortName = source.shortName;
		this.title = source.title;
		this.uri = source.uri;
	}
	
	public FeedMetaInfo(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public URL getUrl() {
		try {
			return new URL(getUri());
		} catch(MalformedURLException mue) {
			return null;
		}		
	}
	
	public boolean equals(Object anObj) {
		return EqualsBuilder.reflectionEquals(this, anObj);
	}
	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public String toString() {
		return "FeedMetaInfo: " + shortName + " : " + uri;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public List<EntryMetaInfo> getEntries() {
		return Collections.unmodifiableList(entries);
	}
	
	public void addEntry(EntryMetaInfo entry) {
		if(!entries.contains(entry)) { // key by..? URI?
			entries.add(entry);
		}
	}
	
	public void setEntries(List<EntryMetaInfo> entries) {
		this.entries = entries;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	/*
	public List<Tag> getTags() {
		return tags;
	}
	
	public void addTag(Tag tag) {
		if(!tags.contains(tag)) {
			tags.add(tag);
		}
	}
	
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	*/
}
