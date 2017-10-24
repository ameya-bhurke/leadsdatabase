var app = angular.module('app',[]);

app.controller('LeadsCtrl', ['$scope','LeadsService', function ($scope,LeadsService) {

    $scope.getLeads = function() {
        var customerId = $scope.leads.customerId;
        var month = $scope.leads.month;
        var year = $scope.leads.year;
        LeadsService.getLeads(customerId, month, year)
            .then(function success(response) {
                $scope.leads.response = response.data;
                $scope.message='';
                $scope.errorMessage = '';
            },
            function error (response ){
                $scope.message = '';
                if (response.status === 404){
                    $scope.errorMessage = 'Leads not found!';
                }
                else {
                    $scope.errorMessage = "Error getting leads!";
                }
            });
    }

    $scope.months = [1,2,3,4,5,6,7,8,9,10,12];

    $scope.years = [2010,2011,2012,2013,2014,2015,2016,2017];

}]);

app.service('LeadsService',['$http', function ($http) {

    this.getLeads = function getLeads(customerId, month, year){
        return $http({
          method: 'GET',
          url: 'leads?customer_id='+customerId+'&month='+month+'&year='+year
        });
	}

}]);