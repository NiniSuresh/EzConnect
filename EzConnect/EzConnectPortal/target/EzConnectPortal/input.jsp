<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="pojo.UserMaster" %>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>EzConnect</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/creative.css" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body id="page-top">
<%-- <% UserMaster userMaster=(UserMaster)session.getAttribute("userMaster");%> --%>
<%
    	if(request.getAttribute("userMaster")!=null)
    	session.setAttribute("userMaster", request.getAttribute("userMaster"));
    %>
    <%
    	if(request.getAttribute("userMasterList")!=null)
    	session.setAttribute("userMasterList", request.getAttribute("userMasterList"));
    %>
    	 <% UserMaster userMaster=(UserMaster)session.getAttribute("userMaster");%>
 <% List<UserMaster> userMasterList=(List<UserMaster>)session.getAttribute("userMasterList"); %>
  <% 
		if(session.getAttribute("userMaster")==null)
		{
			response.sendRedirect("index.jsp");
		}
		else{%>
    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <!-- <a class="navbar-brand page-scroll" href="input.jsp">Welcome !!!</a> -->
                <a class="navbar-brand page-scroll" href="input.jsp" >Welcome  ${userMaster.getName()} !!</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a class="page-scroll" href="about.jsp">About</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="report">Report</a>
                    </li>
                   
                    <li>
                        <a class="page-scroll" href="logout">Logout</a>
                    </li> 
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
  <header>

  <div class="header-content">
            <div class="header-content-inner">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-lg-offset-2 text-center">
                <form class="form-signin" id="form1" method="post" action="connect">
                <div class="service-box">
                   <%-- Contact Number 1: <input name="ContactNumber1" type="tel" pattern="[789][0-9]{9}" class="form-control"
				placeholder="telephone number" required autofocus> 
				</div>
				 <div class="service-box">Contact Number 2: <input
				name="ContactNumber2" type="tel" pattern="[789][0-9]{9}" class="form-control"
				placeholder="telephone number" required autofocus> </div>
				 <div class="service-box">Security Code: <input
				name="securityCode" type="password" class="form-control"
				placeholder="eg.AXyio67" required autofocus><font color="red">${errorMessage}</font>	</div> <div class="service-box"><input
				name="Connect" type="submit" class="btn btn-default btn-xl"
				 value="Connect"> --%>
				 Interviewer Mobile Number <input id="numberForName" name="contactNumber1" type="tel" pattern="[789][0-9]{9}" class="form-control"
				placeholder="telephone number" required autofocus> 
				</div> 
				 <div class="service-box">Or select an interviewer
				 <% if(userMasterList==null){ System.out.println("dfskdjfskdf");} %>
				<select id="nameSelection"  class="form-control" >
				 <option value=" ">-------</option>
				 
			<% for(UserMaster i:userMasterList){ %>
 
  <option value="<%out.println(i.getPhoneNumber());%>"><%out.println(i.getName());%>
  	<%} %>
</select>
<script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript">
    $("#nameSelection").change(function () {
        var selectedValue = $(this).val();
        $("#numberForName").val($(this).find("option:selected").attr("value"))
    });
</script> 
</div>
			 <div class="service-box">Interviewee Mobile Number <input
				name="contactNumber2" type="tel" pattern="[789][0-9]{9}" class="form-control"
				placeholder="telephone number" required autofocus> </div>
				<font color="red"> ${errorMessage}</font> <div class="service-box"><input
				name="Connect" type="submit" class="btn btn-default btn-xl"
				 value="Connect">
				 </div>
                  </form> 
                </div>
            </div>
        </div>
        </div>
        </div>
      
    </header>
<%} %>
</body>