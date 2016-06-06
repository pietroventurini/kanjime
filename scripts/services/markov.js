'use strict'

angular.module('markovServices', [])
    .service('Markov', function($http, $q) {

        this.getTitles = function() {
            var result = $q.defer();
            $http.get('/api/titles').then(function(response){
                result.resolve(response.data);
            });
            return result.promise;
        }
        
        this.getContent = function(title_id) {
            var result = $q.defer();
            $http.get('/api/titles?id=' + title_id).then(function(response){
                result.resolve(response.data);
            });
            return result.promise;
        }
        
        this.markovChain = function(content) {
            var result = $q.defer();
            if (content) {
                $http.post('/api/markov', content).then(function(response){
                    result.resolve(response.data);
                }, function(response){
                    result.reject("Error ")
                });
                return result.promise;
            }
        }
    });