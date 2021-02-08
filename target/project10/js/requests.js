function linkedinRequest() {

	console.log("before linkedinRequest");
	$.ajax({
        	type: 'GET',
        	url: 'auth?source=linkedin'
		});

	console.log("after linkedinRequest");
}
function googleRequest() {

	console.log("before googleRequest");
	$.ajax({
        	type: 'GET',
        	url: 'auth?source=google'
		});

	console.log("after googleRequest");
}

function uploadOnePairs() {


	console.log("uploadOnePairs");
	var urlStr;

	var body = document.getElementById("onePairs").value;
	urlStr = "data?action=one&body=" + body;

	$.ajax({
        	type: 'GET',
        	url: urlStr,
            	success: function(data){
			console.log(data);
		}
		});

}
function uploadOneWithList() {

	console.log("uploadOneWithList");
	var urlStr;

	var body = document.getElementById("personList").value;
	var name = document.getElementById("one").value;
	urlStr = "data?action=one&body=" + body + "&name=" + name ;

	$.ajax({
        	type: 'GET',
        	url: urlStr,
            	success: function(data){
			console.log(data);
		}
		
		});

}

function connectGraphWithSkipFalse() {

	graph("connectgraph", "false");
}

function connectGraphWithSkipTrue() {

	graph("connectgraph", "true");
}


function oneGraphWithSkipFalse() {

	graph("onegraph", "false");
}

function oneGraphWithSkipTrue() {

	graph("onegraph", "true");
}

function graph(graphType, skip) {

	var urlStr;

	var body = document.getElementById("Username").value;
	if(body !== "" || body != undefined) {
	urlStr = "/data?action=" + graphType + "&body=" + body + "&skip=" + skip ;

	$.ajax({
        	type: 'GET',
        	url: urlStr,
            	success: function(data){

			var jsonStr = "." + data;
			d3main(jsonStr);
			personListConnect(jsonStr);
		}
		});
	}
}
/*
$('#my-form').submit( function(e) {
    e.preventDefault();

    var data = new FormData(this); // <-- 'this' is your form element

    $.ajax({
            url: '/my_URL/',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',     
            success: function(data){
            ...

*/
