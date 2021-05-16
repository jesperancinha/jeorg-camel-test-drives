var stonesApp = angular.module("AtmsApp", []);

stonesApp.controller('AtmsController', ['$scope', '$http', '$interval', '$window',
   function($scope, $http, $interval, $window) {

   $scope.reload = function (city) {
           $http.get('/atm/rest/provider/' + city + '/atms').
               success(function (data) {
                   $scope.atmDashBoard = data;
               });
   };
 }
]);
