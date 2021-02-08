<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Start Bootstrap - SB Admin Version 2.0 Demo</title>

    <!-- Core CSS - Include with every page -->
 <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
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
                                        
                         <li class="pull-right" >               <a href=update.jsp class="btn" ><i class="fa fa-upload fa-fw"></i></a> </li>
                         <li class="pull-right" >               <a class="btn btn-social-icon btn-google-plus-square" onclick="googleRequest()"><i class="fa fa-google-plus-square"></i></a> </li>
                	<li class="pull-right" >                        <a class="btn btn-social-icon btn-linkedin-square" onclick="linkedinRequest()"><i class="fa fa-linkedin-square"></i></a> </li>
            </ul>

            <!-- /.navbar-top-links -->

            <ul class="nav navbar-top-links  navbar-main">

	    </ul>
        </nav>

        <div id="page-wrapper" style="padding-top: 5px">
		
		<div class="col-xs-11"> 

				<!-- start form Username -->
        			<form >
                                      <div class="form-group input-group">
            				<input type="hidden" name="action" value="connectgraph"/>
                                        <input type="text" id="Username" name="body" class="form-control" placeholder="Username">

                                	<span class="input-group-btn">
                                	<button class="btn btn-default" type="submit" name="skipFalse" onclick="connectGraphWithSkipFalse()" >
                                	<i class="fa fa-search fa-fw"></i>
					</button>
                                	<button class="btn btn-default" type="submit" name="skipTrue" onclick="connectGraphWithSkipTrue()" >
                                	Next 100</button>
                            		</span>
                                       </div>
					
				</form>
				<!-- end form Username -->
		</div>
		<div class="row-offcanvas row-offcanvas-left show-grid">
			<div id="sidebar" class="col-xs-4 sidebar-offcanvas" style "padding-top:30px" >
                             <label>Upload Data</label>
                            <ul class="nav nav-tabs nav-justified" id="uploadTabs">
                                 <li class="active"> <a data-toggle="tab" href="#collapseTwo" class="btn btn-primary btn-xs "><i class="fa fa-list-ul fa-fw"></i> </a>
				  </li>
                                 <li > <a data-toggle="tab" href="#collapseOne" class="btn btn-primary btn-xs "><i class="fa fa-link fa-fw"></i> </a>
				    </li>
				</ul>

                            <div class="tab-content">
                                <div class="tab-pane fade" id= "collapseOne" >
        				<form onsubmit="uploadOnePairs(); return f:false;">
                            		<div >
                             		<textarea  name="body" id="onePairs" class="form-control" rows="24"></textarea>
                                	<button class="btn btn-default" type="submit" >
                                	Upload</button>
                        		</div>
					</form>
                        	</div>

                               	<div class="tab-pane fade in active" id= "collapseTwo" >
        				<form onsubmit="uploadOneWithList(); return false;">
                            		<div >
                                	<input type="text" name="name" id="one" class="form-control" placeholder="One">
                             		<textarea  name="body" id="personList" class="form-control" rows="20"></textarea>
                                	<button class="btn btn-default" type="submit" >
                                	Upload</button>
                        		</div>
					</form>
				</div>

				</div>
			</div>
                <div id="main" class="col-xs-8 show-grid">
			<p >
            			<button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas"><i class="glyphicon glyphicon-chevron-left"></i></button>
          		</p>
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

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- Core Scripts - Include with every page -->
<!--	d3 include goes here-->
	<script src="http://d3js.org/d3.v3.min.js"></script>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<!--	vivagraph include goes here-->
    <script src="bootstrap/js/bootstrap.min.js"></script>

    <!-- SB Admin Scripts - Include with every page -->
    <script src="js/sb-admin.js"></script>

    <!-- Page-Level Demo Scripts - Dashboard - Use for reference -->
	<script src="js/people-graph.js"></script>
	<script src="js/people-list.js"></script>
	<script src="js/offCanvas.js"></script>
	<script src="js/requests.js"></script>
	<script>
	var one = true;
	var jsonStr = "." + '<%= (String) request.getSession(false).getAttribute("ConnectGraphJson") %>';
	
	var userName = '<%= (String) request.getSession(false).getAttribute("Username") %>';

	if( !((userName === "null") 
			|| (userName === null)
			|| (userName === undefined)
			|| (userName === "")) ) {
		document.getElementById("Username").value = userName;
	}

	d3main(jsonStr);
	personListConnect(jsonStr);

	var rootNodes = [];
	var rootFile ;
	rootFile = '<%= (String) request.getSession(false).getAttribute("RootJson") %>';
  
	if( ((rootFile === "null") 
			|| (rootFile === null)
			|| (rootFile === undefined)
			|| (rootFile === "")) ) {

		$.ajax({
        	type: 'GET',
        	url: 'data?action=query&body=match (n:root) return n',
		success : function(data) {

		//console.log(data);
		var rootFilePath = "." + data;
		//console.log(rootFilePath);
		rootNodes = getNodesFromRootJson(rootFilePath);	
		//console.log(rootNodes);
    		$( "#Username" ).autocomplete({
        		source: rootNodes
    		});
		}
        	});

	} else {

	var rootFilePath = "." + rootFile;
	//console.log(rootFilePath);
	rootNodes = getNodesFromRootJson(rootFilePath);	
	
	//console.log(rootNodes);
	
    	$( "#Username" ).autocomplete({
        source: rootNodes
    	});
	}

	</script>
</body>

</html>
