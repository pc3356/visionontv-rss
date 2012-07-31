/**
 * 
 */
package tv.visionon.rss.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author administrator
 *
 */
@Entity
@Table(name="feed_entry")
public class EntryMetaInfo implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	@Column(name="published")
	private Date publishedDate;
	@Column(name="updated")
	private Date updatedDate;
    @JoinColumn(name="feed_id")
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	private FeedMetaInfo feed;
	private String uri;
	private String description; // Content

		
	public EntryMetaInfo() {

	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getPublishedDate() {
		return publishedDate;
	}
	
	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public FeedMetaInfo getFeed() {
		return feed;
	}
	
	public void setFeed(FeedMetaInfo feed) {
		this.feed = feed;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, Arrays.asList("id"));
	}

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
