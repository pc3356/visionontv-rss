/**
 * 
 */
package tv.visionon.rss.service;

/**
 * @author pjc
 *
 */
public class NewArticleException extends Exception
{

	/**
	 * @param arg0
	 */
	public NewArticleException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public NewArticleException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NewArticleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
