<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Start Bootstrap - SB Admin Version 2.0 Demo</title>

    <!-- Core CSS - Include with every page -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">


    <!-- SB Admin CSS - Include with every page -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/people-graph.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

        <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="padding-top: 2px; margin-bottom: 0">


            <ul class="nav navbar-top-links  navbar-left">

                <li class="col-xs-2"><h5><a href="index.jsp">One Graph</a></h5></li>
		<li class="col-xs-7"> 

				<!-- start form Username -->
        			<form method="GET" action="data" enctype="multipart/form-data">
                                      <div class="form-group input-group">
            				<input type="hidden" name="action" value="onegraph"/>
                                        <input type="text" name="body" class="form-control" placeholder="Username">
                                      <span class="input-group-addon">@</span>
                                       </div>
					
				</form>
				<!-- end form Username -->
		</li>
				
                                        
		<li> </li>
                <!-- /.dropdown -->
                <li class="dropdown pull-right ">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-gear fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
                         <li class="pull-right" >               <a href="auth?source=google" class="btn btn-social-icon btn-google-plus-square"><i class="fa fa-google-plus"></i></a> </li>
                	<li class="pull-right" >                        <a href="auth?source=linkedin" class="btn btn-social-icon btn-linkedin-square"><i class="fa fa-linkedin"></i></a> </li>
            </ul>

		</div>
		</div>
            <!-- /.navbar-top-links -->

        </nav>

        <div id="page-wrapper">
		
            <!-- /.row -->
                <div class="col-xs-32 show-grid">
                            <ul class="nav nav-tabs" id="viewTabs">
                                 <li class="active"> <a data-toggle="tab" href="#GraphArea" ><i class="fa fa-sitemap fa-fw"></i> </a>
				    </li>

                                 <li > <a data-toggle="tab" href="#ListArea" ><i class="fa fa-list-ul fa-fw"></i> </a>
				  </li>
				</ul>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id= "GraphArea" >
                            	<div id="d3-area-chart"></div>
				</div>
				
                                <div class="tab-pane fade" id= "ListArea" >
				 <ul  id="linkList" name="linkListName">
				</ul>
				</div>
			    </div>
                        </div>

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

	<jsp:useBean id="graphJson" 
                    class="com.ProjectOne.app.GraphJson" />
    <!-- Core Scripts - Include with every page -->
<!--	d3 include goes here-->
	<script src="http://d3js.org/d3.v3.min.js"></script>
 	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<!--	vivagraph include goes here-->
    <script src="bootstrap/js/bootstrap.min.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="js/sb-admin.js"></script>

    <!-- Page-Level Demo Scripts - Dashboard - Use for reference -->
	<script src="js/people-graph.js"></script>
	<script src="js/people-list.js"></script>
	<script>
	var one = true;
	var jsonStr = "." + '<%= (String) request.getAttribute("inputJson") %>';
	//alert(<jsp:getProperty  name="graphJson" property="json"/>);	
//	var jsonStr1 = <jsp:getProperty  name="graphJson" property="json"/>;	
//	console.log("in jsp");
//	console.log(jsonStr1);
	d3main(jsonStr);
	personListOne(jsonStr);
	</script>
</body>

</html>
