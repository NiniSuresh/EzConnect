<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="pojo.UserMaster" %>
    <%@ page import="pojo.CallRequest"%>
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
<%
    	if(request.getAttribute("userMaster")!=null)
    	session.setAttribute("userMaster", request.getAttribute("userMaster"));
if(request.getAttribute("callRequest")!=null)
	session.setAttribute("callRequest", request.getAttribute("callRequest"));

    %>
    <% UserMaster userMaster=(UserMaster)session.getAttribute("userMaster");%>
     <% CallRequest callRequest=(CallRequest)session.getAttribute("callRequest");%>
     <% 
		if((session.getAttribute("userMaster")==null)&&(session.getAttribute("callRequest")==null))
		{
			response.sendRedirect("input.jsp");
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
                <a class="navbar-brand page-scroll" href="input.jsp">Welcome  ${userMaster.getName()} !!</a>
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
               
                <div class="service-box">
                   <h2><p id="message"></p>
                   </h2>
                   
				 </div>
                
                </div>
            </div>
        </div>
        </div>
        </div>
      
    </header>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	setTimeout(sendForm,2000);
    	setInterval(sendForm,10000);
    });
  
      function sendForm() {
    var ezconnectId = '${callRequest.getEzconnectId()}';
  
    $.ajax({
     url : '/EzConnectPortal/status_update?ezconnectId='+ezconnectId,
     success:function(response){
    	 document.getElementById("message").innerHTML =response;
    	
      //$('#divID').html('');
     },
     error:function(r){
    	 document.getElementById("message").innerHTML ="call connecting..";
     }
     }
    );
   } 
  </script>
<%} %>
    


</body>
</html>