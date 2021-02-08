function d3main (jsonStr) {
var width = 900,
    height = 600;
var one = true;
var start = true;
var svg = d3.select("#d3-area-chart").append("svg")
    .attr("width", width)
    .attr("height", height);

var tooltip = d3.select("#d3-area-chart").append("div")
		.attr("class", "tooltip")
		.style("opacity", 0);

var force = d3.layout.force()
    .gravity(.06)
    .linkDistance(100)
    .charge(-500)
    .friction(0.7)
    .size([width, height]);

//String jsonStr= request.getParameter("result");
// var json1 = JSON.parse(jsonStr, function(key, value) {
//});
//var json = eval('(' + jsonStr + ')');
// console.log("yogita : " );
if( jsonStr !== ".null" ) {
d3.json(jsonStr, function(error, json) {

  var nodeMap = {};
	var nodes = [];
	var links = [];

    json.graphs.forEach(function(graphx) {
    graphx.paths.forEach(function(path) {

    path.nodes.forEach(function(x) { 
		if(!(x.id in nodeMap) ) {
			nodeMap[x.id] = x; 
			nodes.push(x);
		}
	});

   path.links.forEach(function(x) {
		links.push(x);
   });
  });
});
    links = links.map(function(x) {
      return {
        source: nodeMap[x.source],
        target: nodeMap[x.target],
		value : x.value
      };
    });
 //  console.log(links);
  force
      .nodes(nodes)
      .links(links)
      .start();

svg.append("defs").selectAll("marker")
    .data(["knows"])
  .enter().append("marker")
    .attr("id", function(d) { return d; })
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 25)
    .attr("refY", -1.5)
    .attr("markerWidth", 6)
    .attr("markerHeight", 6)
    .attr("orient", "auto")
  .append("path")
    .attr("d", "M0,-5L10,0L0,5");

  var path = svg.append("g").selectAll("path")
      .data(force.links())
    .enter().append("path")
      .attr("class", "link")
	.attr("marker-end", "url(#knows)");

  /*	
	path.append("text")
      .attr("dy", ".35em")
      .text(function(d) { return "knows" });
*/
/*
  var link = svg.selectAll(".link")
      .data(links)
    .enter().append("line")
      .attr("class", "link");
*/
  var node = svg.selectAll(".node")
      .data(nodes)
    .enter().append("g")
      .attr("class", "node")

	.on("dblclick", dblclick)
	.on("click", function (d) {
/*
	d3.select(this).select("text").transition()
		.duration(750)
		.attr("x", 22)
		.style("fill", "steelblue")
		.style("stroke", "lightsteelblue")
		.style("stroke-width", ".5px")
		.style("font", "20px sans-serif");
*/
	d3.select(this).select("circle").transition()
		.duration(750)
		.attr("r", 16)
		.style("fill", "lavenderblush");

	//console.log(d.name);
	var str1 = d.name;
	
	//console.log($('#uploadTabs .active > a').attr('href') ) ;	
	if($('#uploadTabs .active > a').attr('href') == "#collapseOne") {	
	var str2=  (one) ?":" :";";
	var str3=  str1 + str2 ;
	//console.log(str3);
	document.getElementById("onePairs").value += str3;
		if(one) {
			one = false;
		} else {
			one = true;
		}
	} else {
		if( document.getElementById("one").value == "" ) {
			document.getElementById("one").value += str1;
		} else {
			var str4 = str1 + ";" ;
			document.getElementById("personList").value += str4;
		}
	}
})

      .call(force.drag);
/*
  node.append("image")
      .attr("xlink:href", "https://github.com/favicon.ico")
      .attr("x", -8)
      .attr("y", -8)
      .attr("width", 16)
      .attr("height", 16);
*/

  node.append("circle")
      .attr("r", 16 )
      .style("fill", color)
	.on("mouseover", function (d) {
	tooltip.transition()
		.duration(200)
		.style("opacity", .9);
	tooltip.html(d.name)
		.style("left", d3.event.pageX + "px")
		.style("top", d3.event.pageY - 30 + "px");
 
})
	.on("mouseout", function (d) {
	tooltip.transition()
		.duration(500)
		.style("opacity", 0);
});

  node.append("text")
      .attr("dx", 12)
      .attr("dy", ".35em")
      .text(function(d) { return d.name });
/*
  node.select("circle")
      .style("fill", color);
*/
  force.on("tick", function() {
/*
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });
*/

	path.attr("d", function(d) {

  	var dx = d.target.x - d.source.x,
      	dy = d.target.y - d.source.y,
      	dr = Math.sqrt(dx * dx + dy * dy);
  	return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
	});

    node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
  });
});
}

}

function color(d) {
  return (d.label == "knows") ? "lightskyblue" 
      : (d.label == "one") ? "lightskyblue" 
      : "lightskyblue"; 
}


function dblclick() {
		
	d3.select(this).select("circle").transition()
		.duration(750)
		.attr("r", 16)
		.style("fill", "lightskyblue");
/*
	d3.select(this).select("text").transition()
		.duration(750)
		.attr("x", 12)
		.style("fill", "black")
		.style("stroke", "none")
		.style("font", "10px sans-serif");
*/
}
/*
function reset() {
	${cookie.skip.value}="";
}
*/
	//tooltip.html('<a href= "http://gmail.com">' + d.email + "</a>"  )
