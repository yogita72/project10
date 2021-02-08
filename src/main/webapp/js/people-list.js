
function personListOne (jsonStr) {

if( jsonStr !== ".null" ) {
d3.json(jsonStr, function(error, json) {

  	var nodeMap = {};
	var nodes = [];
	var links = [];

	var targetMap = {};
	var targetSortOrder = [];

    json.graphs.forEach(function(graphx) {
    graphx.paths.forEach(function(path) {

    path.nodes.forEach(function(x) { 
		if(!(x.id in nodeMap) ) {
			nodeMap[x.id] = x; 
			nodes.push(x);
		//console.log(x.name);
		}
	});

   path.links.forEach(function(y) {
		
		if(!(y.target in targetMap) ) {
			var newTarget = []; 
			targetMap[y.target]= newTarget;
			//console.log(y.source);
			//console.log(y.target);
			if(y.source in targetMap) { 
				//console.log(targetSortOrder);
				var index = indexOf(targetSortOrder, y.source); 
				//console.log("adding " + y.target + " at " + index);
				targetSortOrder = insertAtIndex(targetSortOrder, index,  y.target);	
				//console.log(targetSortOrder);
			} else {
				// check if x.target is already a source of some target
				// so that it can be added in targetSortOrder right after its parent 
				var found = false;
				for( var key  in targetMap) {
					if(  indexOf(targetMap[key], y.target) != -1 ) {
						//console.log(targetSortOrder);
						index = indexOf(targetSortOrder, key) +1; 
						//console.log("adding " + y.target + " at " + index + ", key " + key);
						targetSortOrder = insertAtIndex(targetSortOrder,index,  y.target);	
						//console.log(targetSortOrder);
						// note: break will not work if a target 
						// has more than one parent
						found = true;
						break;
					}
				} 
				if( found == false) {

					//console.log(targetSortOrder);
					targetSortOrder.push(y.target);
					//console.log(targetSortOrder);
				}
			}	
		}
		
		if( indexOf(targetMap[y.target], y.source) == -1)	
			targetMap[y.target].push(y.source);
   });
  });
});

	// now iterate through targetSourceOrder and add from targetMap to linkList
	var targetLists = {};
	var linkList = document.getElementById("linkList");
	var str, btn, dList;
	//console.log( targetSortOrder);	
	//console.log( targetMap);	
	for( var j=targetSortOrder.length-1 ; j > -1 ; j--) {

		key = targetSortOrder[j];	
		//console.log(key);
		var currList = document.createElement("UL"); 
		for(var i=0; i < targetMap[key].length; i++) {
			dList = document.createElement("LI");
			str = nodeMap[ targetMap[key][i]].name;
			//console.log(str);
			btn = createPersonButton(str);
			dList.appendChild(btn);
			currList.appendChild(dList); 
			var index =  indexOf(targetSortOrder, targetMap[key][i]);
			if( index > j ) {
				currList.appendChild(targetLists[index]);	
			}
		}
	
		if( j == 0) {

			dList = document.createElement("LI");
			str = nodeMap[key].name;
			//console.log(str);
			btn = createPersonButton(str);
			dList.appendChild(btn);
			linkList.appendChild(dList);
			linkList.appendChild(currList);
		} else {
			targetLists[j] = currList;	
		}
	}
});
}
}


function personListConnect (jsonStr) {

if( jsonStr !== ".null" ) {
d3.json(jsonStr, function(error, json) {

  var nodeMap = {};
	var nodes = [];
	var links = [];

	var aList = document.createElement("UL"); 
	var bList = document.createElement("UL"); 
	var first = true;
	var linkList = document.getElementById("linkList");
    json.graphs.forEach(function(graphx) {
    graphx.paths.forEach(function(path) {

    path.nodes.forEach(function(x) { 
		if(!(x.id in nodeMap) ) {
			nodeMap[x.id] = x; 
			nodes.push(x);
		//console.log(x.name);
		var str = x.name;
		var y;
		if( first == true ) {
			first = false;
			
			var cList = document.createElement("LI");
/*
			y = document.createTextNode(str);
			btn.appendChild(y);
*/
	//		aList.appendChild(btn); 
			var btn = createPersonButton(str);
			cList.appendChild(btn);
			linkList.appendChild(cList);
		} else {
			var dList = document.createElement("LI");
/*			y = document.createTextNode(str);
			btn.appendChild(y);
*/
			var btn = createPersonButton(str);
//			dList.setAttribute("class", "push-right");
			dList.appendChild(btn);
			bList.appendChild(dList); 
			
		}
		}
	});

   path.links.forEach(function(x) {
   	//	console.log(x);
		links.push(x);
   });
  });
});
//	console.log("Adding to button list");	
	//aList.appendChild(bList);
	linkList.appendChild(bList);
});
}
}

function createPersonButton(str) {

        var btn  = document.createElement("BUTTON");
        btn.setAttribute("type", "button");
        btn.setAttribute("class", "btn-custom1");
        btn.onclick = function() {myonclick(btn);};
        btn.innerHTML =  str;

        return btn;
}

function myonclick(btn) {
	
	//console.log("in myonclick");
	var str1 = btn.innerHTML;
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
	
}

function indexOf(a, obj) {
	//console.log("indexOf " + obj );
    for (var i = 0; i < a.length; i++) {
        if (obj ==  a[i] ) {
		//console.log("returning " + a[i]);
            return i;
        }
    }
    return -1;
}

function insertAtIndex(inputArray, index, element) {

	var newArray = [] ;
	var i = 0;

	for( i=0; i< index; i++)
		newArray.push(inputArray[i]);

	newArray.push(element);

	for( ; i< inputArray.length; i++) {
		newArray.push(inputArray[i]);
	} 
	return newArray;
}

function getNodesFromRootJson(rootJson) {

	var nodes = [];

	//console.log(rootJson);
	if( rootJson !== ".null" ) {
	d3.json(rootJson, function(error, json) {

  	var nodeMap = {};

	//console.log(json);
    	json.paths.forEach(function(path) {

    	path.nodes.forEach(function(x) { 
		if(!(x.id in nodeMap) ) {
			nodeMap[x.id] = x; 
			nodes.push(x.name);
		//console.log(x.name);
		}
	});
	});

	});
	}

	nodes.sort();
	nodes.reverse();
	return nodes;
}
