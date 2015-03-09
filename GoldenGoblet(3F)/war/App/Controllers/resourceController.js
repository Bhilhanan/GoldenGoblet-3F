app.service('gapiService',function($q){
	
	//gapi.client.load('subjectendpoint', 'v1',populateSubjectList(), 'http://1-dot-golden-goblet.appspot.com/_ah/api');
		
	
	var getSubjectList=function($scope){
			gapi.client.subjectendpoint.listSubject().execute(function(resp){
			if(!resp.code){
				$scope.subjects=resp.items;
				console.log("scope"+$scope.subjects);
				console.log("resp"+resp.items);
				
			}
		});
	};
	
	
	return{
		getSubjectList:getSubjectList
	};
});