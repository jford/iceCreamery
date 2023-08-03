<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.electraink.iceCreamery.utilities.*,com.electraink.iceCreamery.testKitchen.datastore.*,com.electraink.iceCreamery.testKitchen.datastore.imageCtl.*,java.util.*,java.io.*" %>
   
 <html>
	<head>
		<title>Ice Creamery  Image Manager</title>
		<script type="text/javascript" src="../scripts/base_page_header.js"></script>
		<style>
			div.uploadImageControl
			{
				display: block;
			}
			div.deleteImageControl
			{
				display: none;
			}
		</style>
		<script>
			function showControl(mode)
			{
//				alert("mode = " + mode);
				
				var uploadImageControl = document.getElementById("uploadImageControl");
				var deleteImageControl = document.getElementById("deleteImageControl");
				
				if(mode == "add")
				{
					uploadImageControl.style.display = "block";
					deleteImageControl.style.display = "none";
				}
				if(mode == "delete")
				{
					uploadImageControl.style.display = "none";
					deleteImageControl.style.display = "block";
				}
			}
		</script>
	</head>
	<body>
	<div class="contentDisplayArea">
		<div class="page_title" style="width: 75%; text-align: left;">
		Image Manager
		</div> <!-- page_title -->
		<div class="text">
			<form name="image_upload_form" action="../imageUpload" method="post" enctype="multipart/form-data">
			<p>
			<input name="mode" id="mode" type="radio" value="add" checked onChange="showControl('add')"/>Add
			<input name="mode" id="mode" type="radio" value="delete" onChange="showControl('delete')"/>Delete
			</p>
			
			<div class="uploadImageControl" id="uploadImageControl">
				<p>
				Select image file:
				</p>
				<input type="file" name="image" id="image" size="50"/>
				<p>
				<input  type="submit" name="uploadSubmit" id="uploadSubmit"value="Upload Image"/>
				</p>
			</div><!-- uploadImageControl -->
			
			<div class="deleteImageControl" id="deleteImageControl">
				<%
				String pathMrkr = Utilities.getPathMrkr();
				String dirName = ImageMgr.imageDirName;
				File imageDir = new File(dirName);
				File[] filenames = imageDir.listFiles();
				int count = 0;
				if(filenames.length == 0)
				{
				%>
					<p>
					There are no files in the image directory.
					</p>
				<%
				}
				else
				{
					
				%>
				<p>
				Select the file to be deleted from the IceCreamery image directory, then click the 
				Remove File button. 
				</p>
					<select name="deleteFilename" id="deleteFilename" onchange="this.options[this.selectedIndex].value">
						<option value="">Select file...</option>
						<%
						for(count = 0; count < filenames.length; count++)
						{
							if(filenames[count].isFile())
							{
								String filename = filenames[count].getName();
						%>
							<option value="<%= filename %>"><%= filename %></option>
						<%
							}
						}
						%>
					</select>
					<input type="submit" name="removeImageSubmit" id="removeImageSubmit" value="Remove File"/>
					<div class="text_note">
					Removal from the repository occurs immediately upon clicking the Remove button and is 
					non-recoverable except by uploading the file again. 
					</div>
				<%
				}
				%>
				
			</div><!-- deleteImageControl -->
			
			</form>
		</div>
	</div> <!-- contentDisplayArea -->
	
	<div class="send_back">
		<div class="send_back_pair">
			<div class="send_back_label">Go to </div>
			<div class="send_back_target"><a href="recipeSelect.jsp">Test Kitchen</a></div>
		</div>
		<div class="send_back_pair">
			<div class="send_back_label"></div>
			<div class="send_back_target"><a href="../testKitchen.jsp">Admin</a></div>
		</div> <!--  send_back_pair -->
		
	</div> <!-- send_back -->
	
	</body>
</html>

