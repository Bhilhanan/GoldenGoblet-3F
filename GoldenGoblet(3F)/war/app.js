'use strict';

var app=angular.module('app',['ngRoute']);

//configure the routes
app.config(function($routeProvider) {
    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : 'App/Views/home.html',
            controller  : 'homeController'
        })
.when('/home', {
            templateUrl : 'App/Views/home.html',
            controller  : 'homeController'
        })
        // route for the add page
        .when('/add', {
            templateUrl : 'App/Views/add.html',
            controller  : 'addController'
        })
        .otherwise({redirectTo:'/home'});
});

var mainController=function($scope){
	
};

app.controller('mainController',mainController);

app.factory('cloudendpoint',function($q){
	return{
		init:function(){
			var ROOT="//"+window.location.host+"/_ah/api";
			var hwdefer=$q.defer();
			console.log(window.location.host);
			gapi.client.load('subjectendpoint','v1',function(){
				hwdefer.resolve(gapi);
			}, ROOT);
			
			var chain=$q.all([hwdefer.promise]);
			return chain;
		}
		
	}
});