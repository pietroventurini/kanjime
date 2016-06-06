'use strict'

angular.module('markovControllers', [])
    .controller('MarkovCtrl', function($scope, Markov){
        
        $scope.content = null;
        $scope.titles = null;
        $scope.result = null;
        
        $scope.getTitles = function() {
            if(!$scope.titles) {
                Markov.getTitles().then(function(response){
                    $scope.titles = response;
                });
            }    
            
        }
        
        $scope.getContent = function(title) {
            Markov.getContent(title.id).then(function(response){
                $scope.content = response;
            });
        }
        
        $scope.markovChain = function(content) {
            if($scope.content) {
                 Markov.markovChain(content).then(function(response){
                    $scope.result = response;
                }, function(response){
                    $scope.result = response;
                });
            }
        }
    })