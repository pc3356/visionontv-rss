<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" isELIgnored="false" %>
	<h2>Feeds</h2>
	<c:forEach var="feed" items="${feeds}">
	<h3>${feed.shortName}: <a href="${feed.uri}">${feed.uri}</a></h3>
		<c:forEach var="entry" items="${feed.entries}">
			<h4><a href="${entry.uri}">${entry.uri}</a>: ${entry.title}</h4>
			<p>${entry.description}</p>		
		</c:forEach>
	</c:forEach>