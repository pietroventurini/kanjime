(function() {
    
    angular.module('kanjiControllers', [])
        .controller('KanjiFinderCtrl', ['$scope', 'Kanji', '$state', '$stateParams', '$filter', function($scope, Kanji, $state, $stateParams, $filter) {
            $scope.searchKanji = function(keyword) {
                $state.go('root.search.result', { keyword: keyword });
            };
            $scope.reset = function() {
                    $scope.keyword = "";
                };
        }])
        
        .controller('KanjiResultCtrl', ['$scope', 'Kanji', '$state', '$stateParams', function($scope, Kanji, $state, $stateParams) {
            $scope.keyword = $stateParams.keyword;
            Kanji.search($scope.keyword).then(function(result) {
                $scope.words = result;
            });
            
            $scope.getDetails = function(character) {
                $state.go('root.search.result.details', { character: character });
            };
        }])
        
        .controller('KanjiDetailsCtrl', ['$scope', '$state', 'Kanji', '$stateParams', function($scope, $state, Kanji, $stateParams) {
            $scope.character = $stateParams.character;
            Kanji.getDetails($scope.character).then(function(result) {
                $scope.details = result;
            });
        }]);
})();