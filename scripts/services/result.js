'use strict';

angular.module('kanjiServices', [])

.service('Kanji', ['$http', '$q', function($http, $q) {

    this.search = function(keyword) {
        
        var result = $q.defer();
        
        var config = {
            params: {
                 keyword: keyword
            }
        };
          
        $http.get("/kanjiServer/getKanji.php", config).then(function(data){
            var results =  data.data;
            result.resolve(results);      
        });
         return result.promise;
    };

    this.getDetails = function(character) {
        var result = $q.defer();
        var config = {
            params: {
                character: character
            }
        };
        $http.get('/kanjiServer/getKanjiDetails.php', config).then(function(data){
            var results = data.data;
            result.resolve(results);
        });
        return result.promise;
    };
}])