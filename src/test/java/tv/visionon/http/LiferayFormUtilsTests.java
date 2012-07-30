package tv.visionon.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import tv.visionon.http.LiferayFormUtils;


public class LiferayFormUtilsTests {

	@Test
	public void shouldFindAuthId() {
		//getAuthId
		
		String sourceFragment = "													</a> </li> </ul> </div> </div> </li> </ul> </div> </div> </li> <li class=\"manage-content has-submenu\" id=\"_145_manageContent\"> <a class=\"menu-button\" href=\"javascript:;\"> <span> Manage\n" +
					"</span> </a> <div class=\"aui-menu manage-content-menu aui-overlaycontext-hidden\" id=\"_145_manageContentContainer\"> <div class=\"aui-menu-content\"> <ul> <li class=\"first manage-page\"> <a  href=\"http://92.239.225.98/web/guest/members?p_p_auth=wUUGy0P3&amp;p_p_id=88&amp;p_p_lifecycle=0&amp;p_p_state=maximized&amp;p_p_mode=view&amp;_88_struts_action=%2Flayout_management%2Fedit_pages&amp;_88_tabs1=public-pages&amp;_88_redirect=%2Fweb%2Fguest%2Fmembers&amp;_88_groupId=10156&amp;_88_selPlid=10873\"    > Page" +
					"</a> </li> <li class=\"page-layout\"> <a href=\"javascript:;\" id=\"pageTemplate\"> Page Layout";
		
		String expected = "wUUGy0P3";
		// p_p_auth=wUUGy0P3
		String actual = LiferayFormUtils.getAuthId(sourceFragment);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}
	
	@Test
	public void shouldFindNonEmptyFormAction() {
		List<String> tags = Arrays.asList("<form method=\"post\" action=\"\" id=\"2\" name=\"23\">", "<form method=\"post\" action=\"something\" id=\"12\" name=\"123\">");
		String actual = LiferayFormUtils.extractFormAction(tags);
		assertEquals("something", actual);
	}
}
