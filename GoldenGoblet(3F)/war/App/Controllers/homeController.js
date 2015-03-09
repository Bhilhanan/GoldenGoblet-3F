'use strict';

function initialize(){
	window.init();
}
app.controller('homeController',["$scope","cloudendpoint","$window","gapiService",function($scope,cloudendpoint,$window,gapiService){
	$scope.msg="Home";
	 $window.init= function() {
		    		$scope.$apply($scope.initgapi);
		  	};
	
	$scope.initgapi=function(){
		cloudendpoint.init().then(function(){
			console.log('initiated');
			getSubjectList();
			},
			function(){
				console.log('not initiated');
				});
	};
	
	
	var getSubjectList=function(){
		gapiService.getSubjectList($scope);
		$scope.subjects=[{"id":1,"subject":"a"}];
		console.log($scope.subjects);
	}
	
	
	
	
}]);