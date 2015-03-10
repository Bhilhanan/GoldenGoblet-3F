app.service('gapiService',["$q",function($q){
	
	//gapi.client.load('subjectendpoint', 'v1',populateSubjectList(), 'http://1-dot-golden-goblet.appspot.com/_ah/api');
		
	
	var getSubjectList=function(){
		var p=$q.defer();		
		gapi.client.subjectendpoint.listSubject().execute(function(resp){
			if(!resp.code){
				console.log("resp "+resp.items);
				p.resolve(resp.items);
				
				
			}
			else{
				p.reject("Code monkey typed something wrong");
			}
		});
		return p.promise;
	};
	
	
	return{
		getSubjectList:getSubjectList
	};
}]);