<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.lang.Integer"%>

 <html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
			<meta name="description" content="How I Learned to Make Ice Cream"/>
			<title>The Ice Creamery Blog</title>
			<script type="text/javascript" src="scripts/base_page_header.js"></script>
			<script type="text/javascript" src="scripts/slideshow.js"></script>
			<link rel='stylesheet' type='text/css' href='styles/slideshow_styles.css'/> 
		</head>
		<body>
		<div class="contentDisplayArea">
		
			<div class="page_subtitle" style="font-style: italic; padding: 5%">
			I scream, you scream, we all scream for...
			</div> <!-- page_title -->
			
 			<%@ include file="intro.jspf"  %>				
	
			<div class="continue">
			Care to comment? Click <a href="forms/comment_form.jsp">here</a>.
			</div> <!-- continue -->
			
		</div> <!-- contentDisplayArea -->

		<div class="send_back">
			<div class="send_back_pair">
				<div class="send_back_label">View </div>
				<div class="send_back_target"><a href="./methodology.jsp">Methodology</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="./tools_of_the_trade.jsp">Tools of the Trade</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="javascript: displaySupplemental('acknowledgements.jsp')">Acknowledgements</a></div>
			</div><!-- send_back_pair -->
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="./testKitchen/admin.jsp">Test Kitchen</a></div>
			</div>
			<div class="send_back_pair">
				<div class="send_back_label"></div>
				<div class="send_back_target"><a href="./comments/comments.jsp">Comments</a></div>
			</div><!-- send_back_pair -->
		</div><!--  send_back -->
			
		</body>
	</html>