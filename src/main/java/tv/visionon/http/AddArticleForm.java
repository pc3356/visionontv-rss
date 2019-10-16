package tv.visionon.http;

import org.apache.commons.lang.builder.ToStringBuilder;


public class AddArticleForm extends WebContentForm {

	private final static String ADD_ARTICLE = "addArticle";
	private final static String SERVICE_PARAMETERS = "[groupId,articleId,autoArticleId,title,description,content,type,structureId,templateId,displayDateMonth,displayDateDay,displayDateYear,displayDateHour,displayDateMinute,expirationDateMonth,expirationDateDay,expirationDateYear,expirationDateHour,expirationDateMinute,neverExpire,reviewDateMonth,reviewDateDay,reviewDateYear,reviewDateHour,reviewDateMinute,neverReview,indexable,articleURL,serviceContext]";
	private final static String SERVICE_PARAMETER_TYPES = "[long,java.lang.String,boolean,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,int,int,int,int,int,int,int,int,int,boolean,int,int,int,int,int,boolean,boolean,java.lang.String,com.liferay.portal.service.ServiceContext]";
		
	private boolean autoArticleId = true;
	private String type = "general";
	private String description;
	private String structureId = "";
	private String templateId = "";
	
	private int displayDateMonth;
	private int displayDateDay;
	private int displayDateYear;
	private int displayDateHour;
	private int displayDateMinute;
	
	private int expirationDateMonth;
	private int expirationDateDay;
	private int expirationDateYear;
	private int expirationDateHour;
	private int expirationDateMinute;
	private boolean neverExpire;
	
	private int reviewDateMonth;
	private int reviewDateDay;
	private int reviewDateYear;
	private int reviewDateHour;
	private int reviewDateMinute;
	private boolean neverReview;

	private boolean indexable = true;
	private String articleURL = "articleUrl";
	//private com.liferay.portal.service.ServiceContext serviceContext;
	private String serviceContext = "{}";
	
	public AddArticleForm() {
		super(ADD_ARTICLE, SERVICE_PARAMETERS, SERVICE_PARAMETER_TYPES);
	}

	public boolean isAutoArticleId() {
		return autoArticleId;
	}

	public AddArticleForm setAutoArticleId(boolean autoArticleId) {
		this.autoArticleId = autoArticleId;
		addParameter("autoArticleId", autoArticleId);
		return this;
	}

	public String getType() {
		return type;
	}

	public AddArticleForm setType(String type) {
		this.type = type;
		addParameter("type", type);
		return this;
	}

	public String getDescription() {
		return description;
	}

	public AddArticleForm setDescription(String description) {
		this.description = description;
		addParameter("description", description);
		return this;
	}

	public String getStructureId() {
		return structureId;
	}

	public AddArticleForm setStructureId(String structureId) {
		this.structureId = structureId;
		addParameter("structureId", structureId);
		return this;
	}

	public String getTemplateId() {
		return templateId;
	}

	public AddArticleForm setTemplateId(String templateId) {
		this.templateId = templateId;
		addParameter("templateId", templateId);
		return this;
	}

	public int getDisplayDateMonth() {
		return displayDateMonth;
	}

	public AddArticleForm setDisplayDateMonth(int displayDateMonth) {
		this.displayDateMonth = displayDateMonth;
		addParameter("displayDateMonth", displayDateMonth);
		return this;
	}

	public int getDisplayDateDay() {
		return displayDateDay;
	}

	public AddArticleForm setDisplayDateDay(int displayDateDay) {
		this.displayDateDay = displayDateDay;
		addParameter("displayDateDay", displayDateDay);
		return this;
	}

	public int getDisplayDateYear() {
		return displayDateYear;
	}

	public AddArticleForm setDisplayDateYear(int displayDateYear) {
		this.displayDateYear = displayDateYear;
		addParameter("displayDateYear", displayDateYear);
		return this;
	}

	public int getDisplayDateHour() {
		return displayDateHour;
	}

	public AddArticleForm setDisplayDateHour(int displayDateHour) {
		this.displayDateHour = displayDateHour;
		addParameter("displayDateHour", displayDateHour);
		return this;
	}

	public int getDisplayDateMinute() {
		return displayDateMinute;
	}

	public AddArticleForm setDisplayDateMinute(int displayDateMinute) {
		this.displayDateMinute = displayDateMinute;
		addParameter("displayDateMinute", displayDateMinute);
		return this;
	}

	public int getExpirationDateMonth() {
		return expirationDateMonth;
	}

	public AddArticleForm setExpirationDateMonth(int expirationDateMonth) {
		this.expirationDateMonth = expirationDateMonth;
		addParameter("expirationDateMonth", expirationDateMonth);
		return this;
	}

	public int getExpirationDateDay() {
		return expirationDateDay;
	}

	public AddArticleForm setExpirationDateDay(int expirationDateDay) {
		this.expirationDateDay = expirationDateDay;
		addParameter("expirationDateDay", expirationDateDay);
		return this;
	}

	public int getExpirationDateYear() {
		return expirationDateYear;
	}

	public AddArticleForm setExpirationDateYear(int expirationDateYear) {
		this.expirationDateYear = expirationDateYear;
		addParameter("expirationDateYear", expirationDateYear);
		return this;
	}

	public int getExpirationDateHour() {
		return expirationDateHour;
	}

	public AddArticleForm setExpirationDateHour(int expirationDateHour) {
		this.expirationDateHour = expirationDateHour;
		addParameter("expirationDateHour", expirationDateHour);
		return this;
	}

	public int getExpirationDateMinute() {
		return expirationDateMinute;
	}

	public AddArticleForm setExpirationDateMinute(int expirationDateMinute) {
		this.expirationDateMinute = expirationDateMinute;
		addParameter("expirationDateMinute", expirationDateMinute);
		return this;
	}

	public boolean isNeverExpire() {
		return neverExpire;
	}

	public AddArticleForm setNeverExpire(boolean neverExpire) {
		this.neverExpire = neverExpire;
		addParameter("neverExpire", neverExpire);
		return this;
	}

	public int getReviewDateMonth() {
		return reviewDateMonth;
	}

	public AddArticleForm setReviewDateMonth(int reviewDateMonth) {
		this.reviewDateMonth = reviewDateMonth;
		addParameter("reviewDateMonth", reviewDateMonth);
		return this;
	}

	public int getReviewDateDay() {
		return reviewDateDay;
	}

	public AddArticleForm setReviewDateDay(int reviewDateDay) {
		this.reviewDateDay = reviewDateDay;
		addParameter("reviewDateDay", reviewDateDay);
		return this;
	}

	public int getReviewDateYear() {
		return reviewDateYear;
	}

	public AddArticleForm setReviewDateYear(int reviewDateYear) {
		this.reviewDateYear = reviewDateYear;
		addParameter("reviewDateYear", reviewDateYear);
		return this;
	}

	public int getReviewDateHour() {
		return reviewDateHour;
	}

	public AddArticleForm setReviewDateHour(int reviewDateHour) {
		this.reviewDateHour = reviewDateHour;
		addParameter("reviewDateHour", reviewDateHour);
		return this;
	}

	public int getReviewDateMinute() {
		return reviewDateMinute;
	}

	public AddArticleForm setReviewDateMinute(int reviewDateMinute) {
		this.reviewDateMinute = reviewDateMinute;
		addParameter("reviewDateMinute", reviewDateMinute);
		return this;
	}

	public boolean isNeverReview() {
		return neverReview;
	}

	public AddArticleForm setNeverReview(boolean neverReview) {
		this.neverReview = neverReview;
		addParameter("neverReview", neverReview);
		return this;
	}

	public boolean isIndexable() {
		return indexable;
	}

	public AddArticleForm setIndexable(boolean indexable) {
		this.indexable = indexable;
		addParameter("indexable", indexable);
		return this;
	}

	public String getArticleURL() {
		return articleURL;
	}

	public AddArticleForm setArticleURL(String articleURL) {
		this.articleURL = articleURL;
		addParameter("articleURL", articleURL);
		return this;
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public AddArticleForm setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
		addParameter("serviceContext", serviceContext);
		return this;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

