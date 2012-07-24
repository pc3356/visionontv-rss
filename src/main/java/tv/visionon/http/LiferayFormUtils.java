/**
 * 
 */
package tv.visionon.http;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author administrator
 *
 */
public abstract class LiferayFormUtils {

	private static DocumentBuilder createDocumentBuilder() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		return builder;
	}
	
	public static Document convertDocumentFromHtml(String html) throws Exception {
		String replacedContent = html.replaceAll("&", "&amp;");
		Document document = createDocumentBuilder().parse(new ByteArrayInputStream(replacedContent.getBytes()));
		return document;
	}
	
	public static NodeList extractForms(Document xhtml) {
		return xhtml.getElementsByTagName("form");
	}
	
	public static Map<String, String> getFormAttributes(Node formNode) {
		NamedNodeMap attrs = formNode.getAttributes();
		
		Map<String, String> attributeMap = new HashMap<String, String>();
		for(int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			attributeMap.put(attr.getNodeName(), attr.getTextContent());
		}
		
		return attributeMap;
	}
	
	public static String revertXmlEscapedQueryString(String escaped) {
		return escaped.replaceAll("&amp;", "&");
	}
	
	public static String extractFormAction(List<String> formTags) {
		String nameAttrMatch = "action=\"(.*?)\"";
		String valueRegex = "action=\"(.*?)\"";
		return getAttributeMatchForRegexFromTags(nameAttrMatch, valueRegex, formTags);
	}
	
	public static int getFormCollationId(Node form) {
		
		NamedNodeMap attributes = form.getAttributes();
		String formName = attributes.getNamedItem("name").getTextContent();
		String pattern = "_\\d+_fm";
		
		// wget https://test:test@localhost:8080/tunnel-web/secure/json
		
		
		return 0;
	}
	
	public static int getFormCollationId(List<String> formTags) {
		String nameAttrMatch = "name=\"_\\d+_fm\"";
		String nameRegex = "name=\"_(\\d+)_fm\"";
		String matched = getAttributeMatchForRegexFromTags(nameAttrMatch, nameRegex, formTags);
		if(matched == null || matched.trim().length() == 0) {
			return 0;
		} else {
			return Integer.valueOf(matched);
		}
	}
	
	public static String getAuthId(String content) {
		// p_p_auth
		String wholeAuthRegex = "p_p_auth=.*?&";
		String authRegex = "p_p_auth=(.*?)&";
		Pattern pattern = Pattern.compile(wholeAuthRegex);
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()) {				
			String name = matcher.group();
			Pattern valuePattern = Pattern.compile(authRegex);
			Matcher valueMatcher = valuePattern.matcher(name);
			if(valueMatcher.matches()) {
				String action = valueMatcher.group(1);
				return action;
			}
		}
		return null;
	}
	
	public static List<String> getFormFields(String content) throws Exception {
		String formsRegex = "<form.*?>";
		return getTags(formsRegex, content);
	}
	
	public static List<String> getInputFields(String content) throws Exception {
		String inputRegex = "<input.*?/>";	
		return getTags(inputRegex, content);
	}
	
	public static List<String> getTextAreas(String content) throws Exception {
		String inputRegex = "<textarea.*?>";	
		return getTags(inputRegex, content);
	}
	
	public static List<String> getTags(String regex, String content) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		List<String> tags = new ArrayList<String>();
		while(matcher.find()) {
			tags.add(matcher.group());
		}
		return tags;

	}
	
	private static String getAttributeMatchForRegexFromTags(String attributeRegex, String nameRegex, List<String> formTags) {
		Pattern pattern = Pattern.compile(attributeRegex);
		System.out.println("Found " + formTags.size() + " forms");
		for(String formTag : formTags) {
			System.out.println("Form tag : " + formTag);
			// match for name
			Matcher matcher = pattern.matcher(formTag);
			while(matcher.find()) {				
				String name = matcher.group();
				System.out.println("Match " + name);
				Pattern valuePattern = Pattern.compile(nameRegex);
				Matcher valueMatcher = valuePattern.matcher(name);
				if(valueMatcher.matches()) {
					String action = valueMatcher.group(1);
					if(action.trim().length() > 0) {
						return action;
					}
				}
			}
		}
		return "";
	}
	
}
