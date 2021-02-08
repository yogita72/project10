<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Start Bootstrap - SB Admin Version 2.0 Demo</title>

    <!-- Core CSS - Include with every page -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Page-Level Plugin CSS - Dashboard -->
    <link href="css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
    <link href="css/plugins/timeline/timeline.css" rel="stylesheet">

    <!-- SB Admin CSS - Include with every page -->
    <link href="css/sb-admin.css" rel="stylesheet">
    <link href="css/people-graph.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">

        <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
	   <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">One Graph</a>
            </div>
            <!-- /.navbar-header -->

	 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                       <i class="fa fa-upload fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
		
                    <ul class="dropdown-menu dropdown-upload">
				
                            <div class="text-center">
                                        <a href="auth?source=linkedin" class="btn btn-info btn-social-icon btn-linkedin"><i class="fa fa-linkedin-square"></i></a>
                                        <a href="auth?source=facebook" class="btn btn-primary  btn-social-icon btn-facebook"><i class="fa fa-facebook-square"></i></a>
                                        
                                        <a href="auth?source=google" class="btn btn-danger btn-social-icon btn-google-plus"><i class="fa fa-google-plus-sqaure"></i></a>
                            </div>            
                                    </ul>
		</li>
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
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

	</ul>
            <ul class="nav navbar-nav navbar-right">

		<li>
				<!-- start of Search Form -->
       			<form class="navbar-form" method="GET" action="data" enctype="multipart/form-data">
                                      <div class="form-group input-group">
					<div class="col-xs-16">
            				<input type="hidden" name="action" value="connectgraph"/>
            				<input type="hidden" name="skip" id="skip" value="100"/>
                                        <input type="text" name="body" class="form-control" placeholder="Username">
					</div>
                                	<span class="input-group-btn">
                                	<button class="btn btn-default" type="submit">
                                	<i class="fa fa-search fa-fw"></i></button>
                            		</span>
					
                                       </div>
			</form>
				<!-- end of Search Form -->
		</li>

	</ul>
		</div>


            <!-- /.navbar-top-links -->
		</div>

        </nav>

        <div id="page-wrapper">
		
            <!-- /.row -->
		<div class="row-offcanvas row-offcanvas-left show-grid">
			<div id="sidebar" class="col-xs-4 sidebar-offcanvas">
                             <label>Upload Data</label>
                            <ul class="nav nav-tabs nav-justified" id="uploadTabs">
                                 <li class="active"> <a data-toggle="tab" href="#collapseTwo" class="btn btn-primary btn-xs "><i class="fa fa-list-ul fa-fw"></i> </a>
				  </li>
                                 <li > <a data-toggle="tab" href="#collapseOne" class="btn btn-primary btn-xs "><i class="fa fa-link fa-fw"></i> </a>
				    </li>
				</ul>

                            <div class="tab-content">
                                <div class="tab-pane fade" id= "collapseOne" >
        				<form method="GET" action="data" enctype="multipart/form-data">
                            		<div class="form-group">
            				<input type="hidden" name="action" value="one"/>
                             		<textarea  name="body" id="onePairs" class="form-control" rows="24"></textarea>
                                	<button class="btn btn-default" type="submit">
                                	Upload</button>
                        		</div>
					</form>
                        	</div>

                               	<div class="tab-pane fade in active" id= "collapseTwo" >
        				<form method="GET" action="data" enctype="multipart/form-data">
                            		<div class="form-group">
            				<input type="hidden" name="action" value="one"/>
                                	<input type="text" name="name" id="one" class="form-control" placeholder="One">
                             		<textarea  name="body" id="personList" class="form-control" rows="20"></textarea>
                                	<button class="btn btn-default" type="submit">
                                	Upload</button>
                        		</div>
					</form>
				</div>

				</div>
			</div>
			
                        <div id="main" class="col-xs-8">
			<p class="visible-xs visible-sm visible-md visible-lg">
            			<button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas"><i class="glyphicon glyphicon-chevron-left"></i></button>
          		</p>
                             <label>    </label>
                             <label>    </label>
                            <ul class="nav nav-tabs" id="viewTabs">
                                 <li class="active"> <a data-toggle="tab" href="#GraphArea" class="btn btn-primary"><i class="fa fa-sitemap fa-fw"></i> </a>
				    </li>

                                 <li > <a data-toggle="tab" href="#ListArea" class="btn btn-primary "><i class="fa fa-list-ul fa-fw"></i> </a>
				  </li>
				</ul>
                            <div class="tab-content show-grid">
                                <div class="tab-pane fade in active" id= "GraphArea" >
				<div class="push-right">
                            	<div id="d3-area-chart"></div>
				</div>
				</div>
				
                                <div class="tab-pane fade" id= "ListArea" >
				 <ul  class="show-grid" id="linkList" name="linkListName">
				</ul>
				</div>
			    </div>
                        </div>
               </div>
		<!-- row -->
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
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="js/sb-admin.js"></script>

    <!-- Page-Level Demo Scripts - Dashboard - Use for reference -->
	<script src="js/people-graph.js"></script>
	<script src="js/people-list.js"></script>
	<script src="js/offCanvas.js"></script>

	<script>
	var one = true;
	var jsonStr = "." + '<%= (String) request.getAttribute("inputJson") %>';
//	alert(<jsp:getProperty  name="graphJson" property="json"/>);	
//	var jsonStr1 = <jsp:getProperty  name="graphJson" property="json"/>;	
//	console.log("in jsp");
//	console.log(jsonStr1);
	d3main(jsonStr);
	personListConnect(jsonStr);
	</script>
</body>

</html>
